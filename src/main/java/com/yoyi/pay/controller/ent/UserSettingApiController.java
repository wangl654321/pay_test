package com.yoyi.pay.controller.ent;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.utils.Base64Utils;
import com.yoyi.pay.utils.JsonMapperUtil;
import com.yoyi.pay.utils.ProcessMessage;
import com.yoyi.pay.utils.YoYiPayUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @RequestMapping(value = "/saveUserSettingApi")
    public String registered(HttpServletRequest request, Model model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(8);
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //请求地址
        String regUrl = request.getParameter("regUrl");
        logger.info("账号设置,将参数封装成tranData,请求地址,{}", regUrl);
        model.addAttribute("regUrl", regUrl);
        //接收页面参数封装xml在base64加密
        for (String keys : parameterMap.keySet()) {
            if ("regUrl".equals(keys) || "merchantId".equals(keys)) {
                continue;
            }
            params.put(keys, parameterMap.get(keys)[0]);
        }

        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            logger.info("账号设置,将参数封装成tranData,xml,{}", xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            Map<String, String> sendParams = new HashMap<>(8);

            sendParams.put("tranData", tranData);
            sendParams.put("merchantId", merchantId);
            sendParams.put("merSignMsg", merSignMsg);
            logger.info("账号设置,将参数封装成tranData,处理中");
            List<Map<String, String>> list = new ArrayList<>();
            logger.info("账号设置,处理中");
            fromParam(model, sendParams, list);
        } catch (Exception e) {
            logger.error("账号设置,将参数封装成tranData,异常{}", e);
        }
        return "settle/sec_order_confirm_web";
    }


    /**
     * 表单参数封装
     *
     * @param model
     * @param sendParams
     * @param list
     */
    public static void fromParam(Model model, Map<String, String> sendParams, List<Map<String, String>> list) {
        for (String key : sendParams.keySet()) {
            Map<String, String> mp = new HashMap<>(4);
            mp.put("key", key);
            mp.put("value", sendParams.get(key));
            list.add(mp);
        }
        model.addAttribute("list", list);
    }
}
