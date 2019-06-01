package com.yoyi.pay.controller.notify;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.controller.Notify;
import com.yoyi.pay.controller.RegisterNotify;
import com.yoyi.pay.utils.JsonMapperUtil;
import com.yoyi.pay.utils.ProcessMessage;
import com.yoyi.pay.utils.YoYiPayUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;

/***
 *
 *
 * 描    述：企业注册接口
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
@RequestMapping("/test/register")
public class RegisterNotifyController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 企业注册接口回调
     *
     * @param notify
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/notify")
    public String notify(@RequestBody RegisterNotify notify) {

        logger.info("甬易,企业注册接口=======>开始");
        // 甬易对通知结果的签名数据
        logger.info("企业注册接口,signData,{}", notify.getMerSignMsg());
        try {
            String signData = URLDecoder.decode(notify.getMerSignMsg(),"UTF-8");
            // 通知结果数据
            String tranData = URLDecoder.decode(notify.getTranData(),"UTF-8");
            // 通知base64解密用GBK
            String tranDataGBK = new String(ProcessMessage.Base64Decode(tranData), "GBK");
            logger.info("企业注册接口,解密xml,{}", tranDataGBK);
            // 只保留证书方式。
            boolean flag = ProcessMessage.verifyMessage(tranDataGBK, signData, BaseUtils.getPath(BaseUtils.TEST_NN_SING));
            logger.info("企业注册接口,解密结果,{}", flag);
            if (flag) {
                logger.info("企业注册接口,解密成功");
                Map<String, String> paramMap = YoYiPayUtil.xmlParse(tranDataGBK);
                String value = json.toJson(paramMap);
                logger.info("企业注册接口,明文,{}", value);
            } else {
                logger.info("企业注册接口,解密失败");
            }
        } catch (Exception e) {
            logger.error("企业注册接口,解密异常,", e);
        }
        logger.info("甬易,企业注册接口=======>结束");
        return "ok";
    }


    /**
     * 企业账户打款认证回调
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/notify/manage")
    public String notifyManage(Notify notify, HttpServletRequest request) {

        logger.info("甬易,企业账户打款认证回调=======>开始");
        // 甬易对通知结果的签名数据
        String signData = request.getParameter("signData");
        logger.info("企业账户打款认证回调,signData,{}", signData);
        try {
            // 通知结果数据
            String tranData = notify.getTranData();
            // 通知base64解密用GBK
            String tranDataGBK = new String(ProcessMessage.Base64Decode(tranData), "GBK");
            logger.info("企业账户打款认证回调,解密xml,{}", tranDataGBK);
            // 只保留证书方式。
            boolean flag = ProcessMessage.verifyMessage(tranDataGBK, signData, BaseUtils.getPath(BaseUtils.TEST_NN_SING));
            logger.info("企业账户打款认证回调,解密结果,{}", flag);
            if (flag) {
                logger.info("企业账户打款认证回调,解密成功");
                Map<String, String> paramMap = YoYiPayUtil.xmlParse(tranDataGBK);
                String value = json.toJson(paramMap);
                logger.info("企业账户打款认证回调,明文,{}", value);
            } else {
                logger.info("企业账户打款认证回调,解密失败");
            }
        } catch (Exception e) {
            logger.error("企业账户打款认证回调,解密异常,", e);
        }
        logger.info("甬易,企业注册接口=======>结束");
        return "ok";
    }
}
