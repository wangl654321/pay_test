package com.yoyi.pay.controller.settle;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *
 *
 * 描    述：(结算通)确认收货
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
@RequestMapping("/settle")
public class SecOrderConfirmController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * (结算通)确认收货跳转
     *
     * @return
     */
    @RequestMapping(value = "/secOrderConfirm")
    public String toJsp() {
        return "settle/sec_order_confirm";
    }

    /**
     * (结算通)确认收货,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/toSecOrderConfirm/api")
    public String registered(HttpServletRequest request) {

        Map<String, String> params = new HashMap<>(3);
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //订单号
        String orderNo = request.getParameter("orderNo");
        String backup = request.getParameter("backup");
        String skipFlag = request.getParameter("skipFlag");


        params.put("backup", backup);
        params.put("orderNo", orderNo);
        logger.info("(结算通)确认收货,将参数封装成tranData,请求商户号,{}", merchantId);

        String regUrl = request.getParameter("regUrl");
        //接口名称
        String interfaceName = request.getParameter("interfaceName");
        //版本号
        String version = request.getParameter("version");

        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil httpsUtil = HttpsUtil.getInstance();

            logger.info("(结算通)确认收货,将参数封装成tranData,xml,{}", xml);
            Map<String, String> sendParams = getStringStringMap(merchantId, interfaceName, version, tranData, merSignMsg,skipFlag);

            String value = httpsUtil.post(regUrl, sendParams, null);
            String decode = Base64Utils.decode(value);

            logger.info("(结算通)确认收货,将参数封装成tranData,返回明文,{}", decode);
            Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(decode);
            result = json.toJson(stringStringMap);
            logger.info("(结算通)确认收货,返回结果,{}", value);
        } catch (Exception e) {
            logger.error("(结算通)确认收货,将参数封装成tranData,异常{}", e);
        }
        return result;
    }

    /**
     * (结算通)确认收货,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/toSecOrderConfirm/web")
    public String registeredWeb(HttpServletRequest request, Model model) {

        Map<String, String> params = new HashMap<>(3);
        //商户代码
        String regUrl = request.getParameter("regUrl");
        model.addAttribute("regUrl",regUrl);

        String skipFlag = request.getParameter("skipFlag");
        String merchantId = request.getParameter("merchantId");
        //订单号
        String orderNo = request.getParameter("orderNo");
        String backup = request.getParameter("backup");
        params.put("backup", backup);
        params.put("orderNo", orderNo);
        logger.info("(结算通)确认收货web,将参数封装成tranData,请求商户号,{}", merchantId);
        //接口名称
        String interfaceName = request.getParameter("interfaceName");
        //版本号
        String version = request.getParameter("version");

        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            logger.info("(结算通)确认收货web,将参数封装成tranData,xml,{}", xml);
            Map<String, String> sendParams = getStringStringMap(merchantId, interfaceName, version, tranData, merSignMsg,skipFlag);

            List<Map<String,String>> list = new ArrayList<>();
            for (String key : sendParams.keySet()) {
                Map<String,String> mp= new HashMap<>(2);
                mp.put("key",key);
                mp.put("value",sendParams.get(key));
                list.add(mp);
            }
            model.addAttribute("list",list);
        } catch (Exception e) {
            logger.error("(结算通)确认收货,将参数封装成tranData,异常{}", e);
        }
        return "settle/sec_order_confirm_web";
    }



    /**
     * 封装参数
     *
     * @param merchantId
     * @param interfaceName
     * @param version
     * @param tranData
     * @param merSignMsg
     * @return
     */
    private Map<String, String> getStringStringMap(String merchantId, String interfaceName, String version,
                                                   String tranData, String merSignMsg,String skipFlag) {

        Map<String, String> sendParams = new HashMap<>(8);
        sendParams.put("interfaceName", interfaceName);
        sendParams.put("merchantId", merchantId);
        sendParams.put("version", version);

        logger.info("(结算通)确认收货,封装参数,{}", merchantId);
        sendParams.put("merSignMsg", merSignMsg);
        sendParams.put("tranData", tranData);
        sendParams.put("skipFlag", skipFlag);
        return sendParams;
    }
}