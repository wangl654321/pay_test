package com.yoyi.pay.controller.ecs;

import com.yoyi.pay.controller.BaseUtils;
import com.yoyi.pay.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * 描    述：汇款凭证
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
@RequestMapping("/ecs")
public class RemitCertificateController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    private static String base = "";

    /**
     * 汇款凭证,跳转
     *
     * @return
     */
    @RequestMapping(value = "/remitCertificate")
    public String toJsp() {
        return "ecs/remit_certificate";
    }

    /**
     * 汇款凭证,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/toRemitCertificate")
    public String registered(HttpServletRequest request, Model model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(8);
        //商户代码
        String merchantId = request.getParameter("merchantId");
        //请求地址
        String regUrl = request.getParameter("regUrl");
        logger.info("汇款凭证,将参数封装成tranData,请求地址,{}", regUrl);

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
            logger.info("汇款凭证,将参数封装成tranData,xml,{}", xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil instance = HttpsUtil.getInstance();
            Map<String, String> sendParams = new HashMap<>(8);

            logger.info("汇款凭证,将参数封装成tranData,处理中");
            sendParams.put("merchantId", merchantId);
            sendParams.put("tranData", tranData);
            sendParams.put("merSignMsg", merSignMsg);
            String result  = instance.post(regUrl, sendParams, null);
            Map<String,String> map = json.fromJson(result, Map.class);

            String data = map.get("tranData");
            String tranDataGBK = new String(ProcessMessage.Base64Decode(data), "GBK");
            String signData = map.get("merSignMsg");
            boolean flag = ProcessMessage.verifyMessage(tranDataGBK, signData, BaseUtils.getPath(BaseUtils.TEST_NN_SING));
            if(flag){
                String decode = Base64Utils.decode(data);
                Map<String, String> stringStringMap = YoYiPayUtil.xmlParse(decode);

                model.addAttribute("msg",map.get("errorMsg"));
                base = stringStringMap.get("imgData");
            }

            //将xml转换为map
            logger.info("汇款凭证,调取接口返回明文,{}", result);
        } catch (Exception e) {
            logger.error("汇款凭证,将参数封装成tranData,异常{}", e);
        }
        return "ecs/remit_certificate_code";
    }

    /**
     * 图片显示
     * @param response
     */
    @RequestMapping(value = "/imgData")
    public void code ( HttpServletResponse response) {
        try {
            logger.info("汇款凭证,图片转换");
            byte[] bytes = Base64Utils.decodes(base);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            base = "";
        }catch (Exception e){
            logger.error("汇款凭证,图片转换异常", e);
        }
    }

    public static void decoderBase64File(String base64Code,String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
}