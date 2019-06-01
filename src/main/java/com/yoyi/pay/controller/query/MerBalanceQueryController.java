package com.yoyi.pay.controller.query;

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
 * 描    述：商户款余额查询
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
@RequestMapping("/query")
public class MerBalanceQueryController {

    private static Logger logger = LogManager.getLogger();

    private static JsonMapperUtil json = new JsonMapperUtil();

    /**
     * 商户款余额查询跳转
     *
     * @return
     */
    @RequestMapping(value = "/merBalanceQuery")
    public String toJsp() {
        return "query/mer_balance_query";
    }

    /**
     * 商户款余额查询,将参数封装成tranData
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveMerBalanceQuery")
    public String registered(HttpServletRequest request) {

        Map<String, String> params = new HashMap<>(3);
        //商户代码
        String merchantId = request.getParameter("merchantId");
        String bankId = request.getParameter("bankId");
        params.put("merchantId", merchantId);
        params.put("bankId", bankId);

        logger.info("商户款余额查询,将参数封装成tranData,请求商户号,{}", merchantId);
        String regUrl = request.getParameter("regUrl");
        //接口名称
        String interfaceName = request.getParameter("interfaceName");
        //版本号
        String version = request.getParameter("version");
        String result = "";
        try {
            String xml = YoYiPayUtil.parseXMLIsBlank(params);
            String tranData = Base64Utils.encode(xml);
            //订单签名数据
            String merSignMsg = ProcessMessage.sign(xml, BaseUtils.getPath(BaseUtils.TEST_SING), BaseUtils.TEST_PASSWORD);
            HttpsUtil httpsUtil = HttpsUtil.getInstance();

            logger.info("商户款余额查询,将参数封装成tranData,xml,{}", xml);
            Map<String, String> sendParams = new HashMap<>(8);
            sendParams.put("merSignMsg", merSignMsg);
            sendParams.put("merchantId", merchantId);
            sendParams.put("interfaceName", interfaceName);
            sendParams.put("tranData", tranData);
            sendParams.put("version", version);
            sendParams.put("bankId", bankId);

            String value = httpsUtil.post(regUrl, sendParams, "UTF-8");
            Map<String, String> map = json.fromJson(value, Map.class);
            String resultTranData = map.get("tranData");
            String resultMerSignMsg = map.get("merSignMsg");
            String tranDataGBK = new String(ProcessMessage.Base64Decode(resultTranData), "GBK");
            //证书签名验证
            boolean flag = ProcessMessage.verifyMessage(tranDataGBK, resultMerSignMsg, BaseUtils.getPath(BaseUtils.TEST_NN_SING));
            if (flag) {
                logger.info("商户款余额查询,解密成功");
                //将xml转为map
                Map<String, String> paramMap = YoYiPayUtil.xmlParse(tranDataGBK);
                //将xml转为对象
                BalanceQuery reqParam = YoYiPayUtil.getReqParam(tranDataGBK, new BalanceQuery());

                System.out.println(YoYiPayUtil.parseXML(reqParam));
                result = json.toJson(paramMap);
                logger.info("商户款余额查询,明文,{}", result);
            } else {
                logger.info("商户款余额查询,解密失败");
            }
        } catch (Exception e) {
            logger.error("商户款余额查询,异常{}", e);
        }
        return result;
    }
}