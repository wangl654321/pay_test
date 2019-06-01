package com.yoyi.pay.controller.union;

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
 * 描    述：收单查询
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
public class UnionApiOrderQueryController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 收单查询跳转
     *
     * @return
     */
    @RequestMapping(value = "/unionApiOrderQuery")
    public String toJsp() {
        return "union/union_api_order_query";
    }

    /**
     * 收单查询,将参数封装成tranData
     *
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/saveUnionApiOrderQuery", produces = "text/html;charset=GBK")
    public String registered(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(1);

        String interfaceName = "";
        //请求地址
        String regUrl = "";
        String version = "";
        String transIP = "";
        //商户代码
        String merchantId = "";
        //接收页面参数封装xml在base64加密
        for (String keys : parameterMap.keySet()) {
            if ("interfaceName".equals(keys)) {
                interfaceName = parameterMap.get(keys)[0];
                continue;
            }
            if ("transIP".equals(keys)) {
                transIP = parameterMap.get(keys)[0];
                continue;
            }

            if ("merchantId".equals(keys)) {
                merchantId = parameterMap.get(keys)[0];
                continue;
            }
            if ("regUrl".equals(keys)) {
                regUrl = parameterMap.get(keys)[0];
                continue;
            }
            if ("version".equals(keys)) {
                version = parameterMap.get(keys)[0];
                continue;
            }
            params.put(keys, parameterMap.get(keys)[0]);
        }

        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil instance = HttpsUtil.getInstance();

            logger.info("收单查询,将参数封装成tranData,xml,{}", xml);
            Map<String, String> sendParams = new HashMap<>(8);
            sendParams.put("interfaceName", interfaceName);
            sendParams.put("merchantId", merchantId);
            sendParams.put("tranData", tranData);
            sendParams.put("merSignMsg", merSignMsg);
            sendParams.put("version", version);
            sendParams.put("transIP", transIP);

            String value = instance.get(regUrl, sendParams, null);
            String decode = Base64Utils.decode(value,"UTF-8");
            logger.info("收单查询,返回密文,{}", decode);

            Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(decode);
            result = json.toJson(stringStringMap);
            logger.info("收单查询,返回明文,{}", result);
        } catch (Exception e) {
            logger.error("收单查询,将参数封装成tranData,异常{}", e);
        }
        return result;
    }
}