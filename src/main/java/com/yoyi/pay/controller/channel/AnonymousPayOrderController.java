package com.yoyi.pay.controller.channel;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * 描    述：接口支付
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/3/28 11:30
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Controller
@RequestMapping("/toYoYi")
public class AnonymousPayOrderController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 接口支付跳转
     *
     * @return
     */
    @RequestMapping(value = "/anonymousPayOrder")
    public String toJsp(Model model) {
        //若果BaseUtils.list 为空,请先获取付款通道
        model.addAttribute("list", BaseUtils.list);
        return "channel/anonymous_pay_order";
    }

    /**
     * 接口支付,将参数封装成tranData api方式
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveAnonymousPayOrder/api")
    public String registered(HttpServletRequest request, Model model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(16);

        String merchantId = request.getParameter("merchantId");
        model.addAttribute("merchantId", merchantId);

        logger.info("接口支付,将参数封装成tranData api方式,商户号,{}", merchantId);
        //调用地址
        String regUrl = "";
        //接口名称
        String interfaceName = "";
        //版本
        String version = "";
        //商品名称
        String goodsName = "";
        //接收页面参数封装xml在base64加密
        for (String keys : parameterMap.keySet()) {
            if ("regUrl".equals(keys)) {
                regUrl = parameterMap.get(keys)[0];
                continue;
            }
            if ("interfaceName".equals(keys)) {
                interfaceName = parameterMap.get(keys)[0];
                continue;
            }
            if ("version".equals(keys)) {
                version = parameterMap.get(keys)[0];
                continue;
            }
            if ("goodsName".equals(keys)) {
                goodsName = parameterMap.get(keys)[0];
                continue;
            }
            params.put(keys, parameterMap.get(keys)[0]);
        }
        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil instance = HttpsUtil.getInstance();

            logger.info("接口支付,将参数封装成tranData,xml,{}", xml);
            Map<String, String> sendParams = new HashMap<>(8);
            sendParams.put("interfaceName", interfaceName);
            sendParams.put("merchantId", merchantId);
            sendParams.put("merSignMsg", merSignMsg);
            sendParams.put("version", version);
            sendParams.put("goodsName", goodsName);
            sendParams.put("tranData", tranData);

            String value = instance.post(regUrl, sendParams, null);
            String decode = Base64Utils.decode(value,"UTF-8");
            //将xml转换为map
            Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(decode);
            result = json.toJson(stringStringMap);
            logger.info("接口支付,返回结果,{}", decode);
        } catch (Exception e) {
            logger.error("接口支付查询将参数封装成tranData,异常{}", e);
        }
        return result;
    }

    /**
     * 接口支付,将参数封装成tranData jsp方式
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveAnonymousPayOrder/jsp")
    public String registeredToJsp(HttpServletRequest request, Model model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(16);
        String merchantId = request.getParameter("merchantId");
        model.addAttribute("merchantId", merchantId);

        logger.info("接口支付,将参数封装成tranData jsp方式,商户号,{}", merchantId);
        //接收页面参数封装xml在base64加密
        for (String keys : parameterMap.keySet()) {
            params.put(keys, parameterMap.get(keys)[0]);
        }
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            logger.info("接口支付,将参数封装成tranData,生成xml,{}", xml);
            if (!"".equals(xml)) {
                String encode = Base64Utils.encode(xml);
                model.addAttribute("tranData", encode);
            }
            String sign = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            model.addAttribute("merSignMsg", sign);
        } catch (Exception e) {
            logger.error("接口支付查询将参数封装成tranData,异常{}", e);
        }
        return "channel/anonymous_pay_order_config";
    }
}
