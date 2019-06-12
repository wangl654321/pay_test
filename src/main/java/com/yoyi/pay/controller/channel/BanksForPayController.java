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
import java.util.List;
import java.util.Map;

/***
 *
 *
 * 描    述：获取付款通道
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
public class BanksForPayController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 获取付款通道跳转
     *
     * @return
     */
    @RequestMapping(value = "/getBanksForPay")
    public String toJsp() {
        return "channel/channel_banks_for_pay";
    }


    /**
     * 清空
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/clear")
    public String clear() {
        BaseUtils.list.clear();
        return "1";
    }

    /**
     * 获取付款通道,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/toBanksForPay")
    public String registered(HttpServletRequest request) {

        Map<String, String> params = new HashMap<>(3);
        logger.info("获取付款通道,将参数封装成tranData,开始");
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //获取付款通道 商户退款订单流水号merRefundOrderFlowNo
        String remark = request.getParameter("remark");
        params.put("remark", remark);
        //接口名称
        String interfaceName = request.getParameter("interfaceName");
        String regUrl = request.getParameter("regUrl");
        //版本号
        String version = request.getParameter("version");
        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            logger.info("获取付款通道,将参数封装成tranData,xml,{}", xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil httpsUtil = HttpsUtil.getInstance();
            Map<String, String> sendParams = new HashMap<>(8);
            logger.info("获取付款通道,将参数封装成tranData,处理中");
            sendParams.put("merSignMsg", merSignMsg);
            sendParams.put("merchantId", merchantId);
            sendParams.put("interfaceName", interfaceName);
            sendParams.put("tranData", tranData);
            sendParams.put("version", version);
            String value = httpsUtil.post(regUrl, sendParams, null);

            logger.info("获取付款通道,调取接口返回密文,{}", value);
            String decode = Base64Utils.decode(value);
            logger.info("获取付款通道,返回,{}", decode);
            List<Map<String, String>> listByQuery = BaseUtils.list;

            List<Map<String, String>> list = XmlUtils.xmlParse(decode);
            if (listByQuery.isEmpty()) {
                for (Map<String, String> map : list) {
                    listByQuery.add(map);
                }
            }
            result = json.toJson(list);
            logger.info("获取付款通道,调取接口返回明文,{}", result);
        } catch (Exception e) {
            logger.error("获取付款通道,将参数封装成tranData,异常{}", e);
        }
        return result;
    }
}
