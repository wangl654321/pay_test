package com.yoyi.pay.controller.union;

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
 * 描    述：发送短信
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
@RequestMapping("/union")
public class SendMobileController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 发送短信跳转
     *
     * @return
     */
    @RequestMapping(value = "/getMobileCode")
    public String toJsp(Model model) {
        //若果BaseUtils.list 为空,请先获取付款通道
        model.addAttribute("list", BaseUtils.list);
        return "union/send_mobile";
    }

    /**
     * 发送短信,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveMobileCode")
    public String registered(HttpServletRequest request, Model model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(16);
        logger.info("发送短信,将参数封装成tranData,开始");

        //接收页面参数封装xml在base64加密
        for (String keys : parameterMap.keySet()) {
            params.put(keys, parameterMap.get(keys)[0]);
        }
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            logger.info("发送短信,将参数封装成tranData,xml,{}", xml);
            if (!"".equals(xml)) {
                String encode = Base64Utils.encode(xml);
                model.addAttribute("tranData", encode);
            }

            logger.info("发送短信,将参数封装成tranData,处理中");
            String sign = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            model.addAttribute("merSignMsg", sign);
        } catch (Exception e) {
            logger.error("实名认证,异常{}", e);
        }
        return "union/send_mobile_config";
    }


    /**
     * 发送短信
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/toGetMobileCode",produces = "text/html;charset=UTF-8")
    public String toPreprocessRefund(HttpServletRequest request, Model model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(16);
        logger.info("发送短信,开始");

        String regUrl = "";
        String merchantId = request.getParameter("merchantId");
        model.addAttribute("merchantId", merchantId);
        logger.info("发送短信,操作商户,{}", merchantId);
        //接收页面参数封装xml在base64加密
        for (String keys : parameterMap.keySet()) {
            if ("regUrl".equals(keys)) {
                regUrl = parameterMap.get(keys)[0];
                continue;
            }
            params.put(keys, parameterMap.get(keys)[0]);
        }
        String result = "";
        try {
            //接口名称
            String value = HttpsUtil.getInstance().get(regUrl, params, null);
            String string = Base64Utils.decode(value,"UTF-8");

            logger.info("发送短信,返回明文,{}", string);
            Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(string);
            result = json.toJson(stringStringMap);
        } catch (Exception e) {
            logger.error("发送短信,异常{}", e);
        }
        return result;
    }
}
