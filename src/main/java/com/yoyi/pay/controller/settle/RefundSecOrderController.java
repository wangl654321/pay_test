package com.yoyi.pay.controller.settle;

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
 * 描    述：(结算通)退款
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
public class RefundSecOrderController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * (结算通)退款跳转
     *
     * @return
     */
    @RequestMapping(value = "/refundSecOrder")
    public String toJsp() {
        return "settle/refund_sec_order";
    }

    /**
     * (结算通)退款,将参数封装成tranData
     *
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/saveRefundSecOrder")
    public String registered(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(4);
        //接口名称
        String interfaceName = "";
        //版本号
        String version = "";
        //商户代码
        String merchantId = "";
        String regUrl = "";
        for (String keys : parameterMap.keySet()) {
            if ("regUrl".equals(keys)) {
                regUrl = parameterMap.get(keys)[0];
                continue;
            }
            if ("interfaceName".equals(keys)) {
                interfaceName = parameterMap.get(keys)[0];
                continue;
            }

            logger.info("(结算通)退款,将参数封装成tranData,接口名称,{}", interfaceName);
            if ("merchantId".equals(keys)) {
                merchantId = parameterMap.get(keys)[0];
                continue;
            }
            if ("version".equals(keys)) {
                version = parameterMap.get(keys)[0];
                continue;
            }
            params.put(keys, parameterMap.get(keys)[0]);
        }
        logger.info("(结算通)退款,将参数封装成tranData,请求商户号,{}", merchantId);
        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil instance = HttpsUtil.getInstance();

            logger.info("(结算通)退款,将参数封装成tranData,xml,{}", xml);
            Map<String, String> sendParams = new HashMap<>(8);
            sendParams.put("merSignMsg", merSignMsg);
            sendParams.put("merchantId", merchantId);
            sendParams.put("tranData", tranData);
            sendParams.put("interfaceName", interfaceName);
            sendParams.put("version", version);

            String value = instance.post(regUrl, sendParams, null);
            logger.info("(结算通)退款,将参数封装成tranData,返回密文,{}", value);
            String decode = Base64Utils.decode(value);
            logger.info("(结算通)退款,将参数封装成tranData,返回明文,{}", decode);
            Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(decode);
            result = json.toJson(stringStringMap);
            logger.info("(结算通)退款,返回结果,{}", value);
        } catch (Exception e) {
            logger.error("(结算通)退款,将参数封装成tranData,异常{}", e);
        }
        return result;
    }
}