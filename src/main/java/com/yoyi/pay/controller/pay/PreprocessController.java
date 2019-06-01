package com.yoyi.pay.controller.pay;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.utils.Base64Utils;
import com.yoyi.pay.utils.ProcessMessage;
import com.yoyi.pay.utils.YoYiPayUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
@RequestMapping("/toYoYi")
public class PreprocessController {

    private static Logger logger = LogManager.getLogger();

    /**
     * 支付请求跳转
     *
     * @return
     */
    @RequestMapping(value = "/preprocess")
    public String toJsp() {

        return "pay/preprocess";
    }

    /**
     * 支付请求将参数封装成tranData
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/savePreprocess")
    public String registered(HttpServletRequest request, Model model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(16);

        String merchantId = request.getParameter("merchantId");
        model.addAttribute("merchantId", merchantId);
        //接收页面参数封装xml在base64加密
        for (String keys : parameterMap.keySet()) {
            params.put(keys, parameterMap.get(keys)[0]);
        }
        try {
            String value = YoYiPayUtil.parseXMLIsBlank(params);
            if (!"".equals(value)) {
                String encode = Base64Utils.encode(value);
                model.addAttribute("tranData", encode);
            }
            String sign = ProcessMessage.sign(value, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            model.addAttribute("merSignMsg", sign);
        } catch (Exception e) {
            logger.error("支付请求查询将参数封装成tranData,异常{}", e);
        }
        return "pay/preprocess_config";
    }
}