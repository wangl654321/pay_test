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
 * 描    述：企业信息查询
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
public class RegisterQueryController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 企业信息查询跳转
     *
     * @return
     */
    @RequestMapping(value = "/corApiQuery")
    public String toJsp() {
        return "ent/user_register_query";
    }

    /**
     * 企业信息查询,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userRegisterQuery")
    public String registered(HttpServletRequest request, Model model) {


        Map<String, String> params = new HashMap<>(3);
        logger.info("企业信息查询,将参数封装成tranData,开始");
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //接口名称
        String regUrl = request.getParameter("regUrl");
        //会员客户号
        String customerId = request.getParameter("customerId");
        //公司名称
        String company = request.getParameter("company");
        params.put("customerId", customerId);
        params.put("company", company);

        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            logger.info("企业信息查询,将参数封装成tranData,xml,{}", xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil instance = HttpsUtil.getInstance();
            Map<String, String> sendParams = new HashMap<>(8);
            logger.info("企业信息查询,将参数封装成tranData,处理中");
            sendParams.put("merchantId", merchantId);
            sendParams.put("tranData", tranData);
            sendParams.put("merSignMsg", merSignMsg);
            String value = instance.get(regUrl, sendParams, null);

            logger.info("企业信息查询,调取接口返回密文,{}", value);
            String decode = Base64Utils.decode(value);
            logger.info("企业信息查询,返回,{}", decode);
            Map<String, String> map = YoYiPayUtil.xmlParse(decode);

            result = json.toJson(map);
            logger.info("企业信息查询,调取接口返回明文,{}", result);
        } catch (Exception e) {
            logger.error("企业信息查询,将参数封装成tranData,异常{}", e);
        }
        return result;
    }
}
