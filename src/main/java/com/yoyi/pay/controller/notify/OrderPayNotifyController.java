package com.yoyi.pay.controller.notify;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.controller.Notify;
import com.yoyi.pay.utils.JsonMapperUtil;
import com.yoyi.pay.utils.ProcessMessage;
import com.yoyi.pay.utils.YoYiPayUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 *
 *
 * 描    述：支付请求
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
@RequestMapping("/order/pay")
public class OrderPayNotifyController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 支付结果通知
     *
     * @param notify
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/notify")
    public String registered(Notify notify) {

        String interfaceName = notify.getInterfaceName();
        logger.info("甬易,支付结果通知=======>开始,{}", interfaceName);
        String log = "";

        String PayOrderNotify = "PayOrderNotify";
        String RefundOrderNotify = "RefundOrderNotify";
        String SendGoodsNotify = "SendGoodsNotify";
        //支付结果通知
        if (PayOrderNotify.equals(interfaceName)) {
            log = "支付结果通知";
        } else if (RefundOrderNotify.equals(interfaceName)) {
            log = "退款审核通知";
        } else if (SendGoodsNotify.equals(interfaceName)) {
            log = "发货通知";
        }
        //甬易对通知结果的签名数据
        String signData = notify.getSignData();
        logger.info(log + ",signData,{}", signData);
        try {
            // 通知结果数据
            String tranData = notify.getTranData();
            // 通知base64解密用GBK
            String tranDataGBK = new String(ProcessMessage.Base64Decode(tranData), "GBK");
            logger.info(log + ",解密xml,{}", tranDataGBK);
            // 只保留证书方式
            boolean flag = ProcessMessage.verifyMessage(tranDataGBK, signData, BaseUtils.getPath(BaseUtils.TEST_NN_SING));
            logger.info(log + ",解密结果,{}", flag);
            if (flag) {
                logger.info(log + ",解密成功");
                Map<String, String> paramMap = YoYiPayUtil.xmlParse(tranDataGBK);
                String value = json.toJson(paramMap);
                logger.info(log + ",明文,{}", value);
            } else {
                logger.info(log + ",解密失败");
            }
        } catch (Exception e) {
            logger.error(log + ",解密异常,", e);
        }
        logger.info("甬易,支付结果通知=======>结束");
        return "ok";
    }

    /**
     * 获取结果
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/result")
    public String result(HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String value = "";
            //接收页面参数封装xml在base64加密
            for (String keys : parameterMap.keySet()) {
                value = keys;
            }
            Map<String, String> params = json.fromJson(value, Map.class);
            String merSignMsg = params.get("tranData");
            String tranDataGBK = new String(ProcessMessage.Base64Decode(merSignMsg));

            Map<String, String> paramMap = YoYiPayUtil.xmlParse(tranDataGBK);
            System.out.println(paramMap);
        } catch (Exception e) {

        }
        return "ok";
    }
}
