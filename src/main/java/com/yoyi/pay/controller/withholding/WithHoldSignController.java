package com.yoyi.pay.controller.withholding;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.utils.*;
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
 * 描    述：代扣签约
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
@RequestMapping("/withholding")
public class WithHoldSignController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 代扣签约,跳转
     *
     * @return
     */
    @RequestMapping(value = "/withholdSign")
    public String toJsp() {
        return "withholding/withhold_sign";
    }

    /**
     * 代扣签约,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveWithholdSign")
    public String registered(HttpServletRequest request, Model model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(8);
        //商户代码
        String merchantId = request.getParameter("merchantId");

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
            logger.info("代扣签约,将参数封装成tranData,xml,{}", xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil instance = HttpsUtil.getInstance();
            Map<String, String> sendParams = new HashMap<>(8);

            logger.info("代扣签约,将参数封装成tranData,处理中");
            model.addAttribute("merchantId", merchantId);
            model.addAttribute("tranData", tranData);
            model.addAttribute("merSignMsg", merSignMsg);
            //sendParams.put("merchantId", merchantId);
            //sendParams.put("tranData", tranData);
            //sendParams.put("merSignMsg", merSignMsg);
            logger.info("代扣签约,调取接口返回明文,{}", result);
        } catch (Exception e) {
            logger.error("代扣签约,将参数封装成tranData,异常{}", e);
        }
        return "withholding/withhold_sign_config";
    }
}