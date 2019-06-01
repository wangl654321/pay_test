package com.yoyi.pay.controller.pay;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.controller.pay.entity.RefundOrder;
import com.yoyi.pay.controller.pay.enums.RefundTranStat;
import com.yoyi.pay.utils.*;
import org.apache.commons.lang3.StringUtils;
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
 * 描    述：支付退款查询
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
public class PreprocessRefundQueryController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 支付退款查询跳转
     *
     * @return
     */
    @RequestMapping(value = "/preprocess/refundOrderQuery")
    public String toJsp() {
        return "pay/preprocess_refund_query";
    }

    /**
     * 支付退款查询,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/savePreprocessRefundOrderQuery")
    public String registered(HttpServletRequest request) {

        Map<String, String> params = new HashMap<>(3);
        logger.info("支付退款查询,将参数封装成tranData,开始");
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //订单号 商户退款订单流水号 merRefundOrderFlowNo
        String tranSerialNo = request.getParameter("tranSerialNo");
        params.put("merchantId", merchantId);
        params.put("tranSerialNo", tranSerialNo);
        //接口名称
        String interfaceName = request.getParameter("interfaceName");
        String regUrl = request.getParameter("regUrl");
        //版本号
        String version = request.getParameter("version");
        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            logger.info("支付退款查询,将参数封装成tranData,xml,{}", xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil instance = HttpsUtil.getInstance();
            Map<String, String> sendParams = new HashMap<>(8);
            sendParams.put("interfaceName", interfaceName);
            sendParams.put("merchantId", merchantId);
            sendParams.put("tranData", tranData);
            sendParams.put("version", version);
            sendParams.put("merSignMsg", merSignMsg);
            String value = instance.post(regUrl, sendParams, null);

            logger.info("支付退款查询,调取接口返回密文,{}", value);
            String decode = Base64Utils.decode(value);
            String code = "error";
            if (decode.contains(code)) {
                Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(decode);
                result = json.toJson(stringStringMap);
            } else {
                RefundOrder reqParam = YoYiPayUtil.getReqParam(decode, new RefundOrder());
                if (StringUtils.isNotEmpty(reqParam.getTranStat())) {
                    reqParam.setTranStat(RefundTranStat.labelOf(reqParam.getTranStat()));
                }
                result = reqParam.toString();
            }
            logger.info("支付退款查询,调取接口返回明文,{}", result);
        } catch (Exception e) {
            logger.error("支付退款查询将参数封装成tranData,异常{}", e);
        }
        return result;
    }
}
