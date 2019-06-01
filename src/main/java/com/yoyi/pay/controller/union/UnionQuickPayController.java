package com.yoyi.pay.controller.union;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * 描    述：银联协议支付
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
@RequestMapping("/union")
public class UnionQuickPayController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 银联协议支付跳转
     *
     * @return
     */
    @RequestMapping(value = "/unionQuickPay")
    public String toJsp() {
        return "union/union_quick_pay";
    }

    /**
     * 银联协议支付,将参数封装成tranData
     *
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/saveUnionQuickPay")
    public String registered(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(6);

        //商户代码
        String merchantId = "";
        //请求地址
        String regUrl = "";

        String version = "";
        String transIP = "";
        String interfaceName = "";

        //接收页面参数封装xml在base64加密
        for (String keys : parameterMap.keySet()) {
            if ("merchantId".equals(keys)) {
                merchantId = parameterMap.get(keys)[0];
                continue;
            }
            if ("interfaceName".equals(keys)) {
                interfaceName = parameterMap.get(keys)[0];
                continue;
            }
            if ("transIP".equals(keys)) {
                transIP = parameterMap.get(keys)[0];
                continue;
            }
            if ("regUrl".equals(keys)) {
                regUrl = parameterMap.get(keys)[0];
                continue;
            }
            if ("version".equals(keys)) {
                version = parameterMap.get(keys)[0];
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

            logger.info("银联协议支付,将参数封装成tranData,xml,{}", xml);
            Map<String, String> sendParams = new HashMap<>(16);
            sendParams.put("interfaceName", interfaceName);
            sendParams.put("merchantId", merchantId);
            sendParams.put("tranData", tranData);
            sendParams.put("version", version);
            sendParams.put("merSignMsg", merSignMsg);
            sendParams.put("transIP", transIP);

            String value = instance.get(regUrl, sendParams, null);
            String decode = Base64Utils.decode(value,"UTF-8");

            logger.info("银联协议支付,返回结果,{}",decode);
            Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(decode);
            result = json.toJson(stringStringMap);
            logger.info("银联协议支付,返回明文,{}",decode);
        } catch (Exception e) {
            logger.error("银联协议支付,将参数封装成tranData,异常{}", e);
        }
        return result;
    }
}