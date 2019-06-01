package com.yoyi.pay.controller.ent;

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
 * 描    述：账号设置
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
@RequestMapping("/ent")
public class UserSettingApiController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 账号设置,跳转
     *
     * @return
     */
    @RequestMapping(value = "/userSettingApi")
    public String toJsp() {
        return "ent/user_setting_api";
    }

    /**
     * 账号设置,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveUserSettingApi")
    public String registered(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(8);
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //请求地址
        String regUrl = request.getParameter("regUrl");
        logger.info("账号设置,将参数封装成tranData,请求地址,{}", regUrl);

        //接收页面参数封装xml在base64加密
        for (String keys : parameterMap.keySet()) {
            if ("regUrl".equals(keys) || "merchantId".equals(keys)) {
                continue;
            }
            params.put(keys, parameterMap.get(keys)[0]);
        }
        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            logger.info("账号设置,将参数封装成tranData,xml,{}", xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil instance = HttpsUtil.getInstance();
            Map<String, String> sendParams = new HashMap<>(8);

            logger.info("账号设置,将参数封装成tranData,处理中");
            sendParams.put("merchantId", merchantId);
            sendParams.put("tranData", tranData);
            sendParams.put("merSignMsg", merSignMsg);
            String value = instance.get(regUrl, sendParams, null);

            logger.info("账号设置,调取接口返回密文,{}", value);
            String decode = Base64Utils.decode(value);
            logger.info("账号设置,返回,{}", decode);

            Map<String, String> map = YoYiPayUtil.xmlParse(decode);
            result = json.toJson(map);
            logger.info("账号设置,调取接口返回明文,{}", result);
        } catch (Exception e) {
            logger.error("账号设置,将参数封装成tranData,异常{}", e);
        }
        return result;
    }
}
