package com.yoyi.pay.controller.channel;

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
 * 描    述：接口支付查询
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
public class AnonymousPayOrderQueryController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 接口支付查询跳转
     *
     * @return
     */
    @RequestMapping(value = "/pay_query/orderQuery")
    public String toJsp() {
        return "channel/anonymous_pay_query";
    }

    /**
     * 接口支付查询,将参数封装成tranData
     *
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/save/pay_query/orderQuery")
    public String registered(HttpServletRequest request) {

        Map<String, String> params = new HashMap<>(3);
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //订单号
        String orderNo = request.getParameter("orderNo");
        //随机数
        String ramdon = request.getParameter("ramdon");
        params.put("merchantId", merchantId);
        params.put("orderNo", orderNo);
        params.put("ramdon", ramdon);
        logger.info("接口支付查询,将参数封装成tranData,商户号,{}", merchantId);

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
            HttpsUtil instance = HttpsUtil.getInstance();

            logger.info("接口支付查询,将参数封装成tranData,xml,{}", xml);
            Map<String, String> sendParams = new HashMap<>(8);
            sendParams.put("merchantId", merchantId);
            sendParams.put("interfaceName", interfaceName);
            sendParams.put("version", version);
            sendParams.put("tranData", tranData);
            sendParams.put("merSignMsg", merSignMsg);
            String value = instance.get(regUrl, sendParams, null);
            String decode = Base64Utils.decode(value,"UTF-8");

            logger.info("接口支付查询,将参数封装成tranData,返回明文,{}", decode);
            Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(decode);
            result = json.toJson(stringStringMap);
            logger.info("接口支付查询,返回结果",decode);
        } catch (Exception e) {
            logger.error("接口支付查询将参数封装成tranData,异常{}", e);
        }
        return result;
    }

}
