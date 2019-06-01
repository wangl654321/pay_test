package com.yoyi.pay.controller.alipay;

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
 * 描    述：(微信支付宝)退款订单查询
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
@RequestMapping("/aliPayAndWeChat")
public class AliPayAndWeChatRefundQueryController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * (微信支付宝)退款订单查询跳转
     *
     * @return
     */
    @RequestMapping(value = "/QueryRefundOrder")
    public String toJsp() {
        return "aliPayAndWeChat/aliPay_weChat_refund_query";
    }

    /**
     * (微信支付宝)退款订单查询,将参数封装成tranData
     *
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/saveQueryRefundOrder")
    public String registered(HttpServletRequest request) {

        Map<String, String> params = new HashMap<>(3);
        logger.info("(微信支付宝)退款订单查询,将参数封装成tranData,开始");
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //订单号 商户退款订单流水号merRefundOrderFlowNo
        String tranSerialNo = request.getParameter("tranSerialNo");
        params.put("tranSerialNo", tranSerialNo);
        params.put("merchantId", merchantId);
        //接口名称
        String interfaceName = request.getParameter("interfaceName");
        String regUrl = request.getParameter("regUrl");
        //版本号
        String version = request.getParameter("version");
        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);

            logger.info("(微信支付宝)退款订单查询,将参数封装成tranData,xml,{}", xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil instance = HttpsUtil.getInstance();
            Map<String, String> sendParams = new HashMap<>(8);
            sendParams.put("tranData", tranData);
            sendParams.put("version", version);
            sendParams.put("merchantId", merchantId);
            sendParams.put("merSignMsg", merSignMsg);
            sendParams.put("interfaceName", interfaceName);
            String value = instance.get(regUrl, sendParams, null);

            logger.info("(微信支付宝)退款订单查询,调取接口返回密文,{}", value);
            String decode = Base64Utils.decode(value);
            Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(decode);
            result = json.toJson(stringStringMap);
            logger.info("(微信支付宝)退款订单查询,调取接口返回明文,{}", result);
            logger.info(decode);
        } catch (Exception e) {
            logger.error("(微信支付宝)退款订单查询,将参数封装成tranData,异常{}", e);
        }
        return result;
    }

}
