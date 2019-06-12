package com.yoyi.pay.controller.ent;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * 描    述：授权绑定
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
public class UserBindApiController {

    private static Logger logger = LogManager.getLogger();

    /**
     * 授权绑定,跳转
     *
     * @return
     */
    @RequestMapping(value = "/userBindApi")
    public String toJsp() {
        return "ent/user_bind_api";
    }

    /**
     * 授权绑定,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveUserBindApi")
    public String registered(HttpServletRequest request, Model model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(8);
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //请求地址
        String regUrl = request.getParameter("regUrl");
        logger.info("授权绑定,将参数封装成tranData,请求地址,{}", regUrl);

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
            logger.info("授权绑定,将参数封装成tranData,xml,{}", xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            logger.info("授权绑定,将参数封装成tranData,处理中");

            model.addAttribute("regUrl",regUrl);
            model.addAttribute("merchantId",merchantId);
            model.addAttribute("tranData",tranData);
            model.addAttribute("merSignMsg",merSignMsg);
        } catch (Exception e) {
            logger.error("授权绑定,将参数封装成tranData,异常{}", e);
        }
        return "ent/user_bind_api_from";
    }
}
