package com.yoyi.pay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 *
 *
 * 描    述：支付请求公共常量类
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
@Configuration
@PropertySource(value = "classpath:${spring.profiles.active}/resource.properties")
public class BaseUtils {

    private static Logger logger = LogManager.getLogger();

    public static final List<Map<String, String>> list = new ArrayList<>();


    /**
     * 证书密码
     */
    public static String TEST_PASSWORD;

    @Value("${password}")
    public void setPassword(String password) {
        TEST_PASSWORD = password;
    }


    /**
     * 私钥地址
     */
    public static String TEST_SING;

    @Value("${pfx}")
    public void setTestSing(String pfx) {
        TEST_SING = pfx;
    }


    /**
     * 公钥地址
     */
    public static String TEST_NN_SING;

    @Value("${cer}")
    public void setTestNnSing(String cer) {
        TEST_NN_SING = cer;
    }

    /**
     * 商户号
     */
    public static String MERCHANT_ID;

    @Value("${yMerchantId}")
    public void setMerchantId(String yMerchantId) {
        MERCHANT_ID = yMerchantId;
    }

    /**
     * 请求地址
     */
    public static String URL;

    @Value("${url}")
    public void setUrl(String url) {
        URL = url;
    }


    /**
     * 获取证书文件绝对路径
     *
     * @param path
     * @return
     */
    public static String getPath(String path) {

        String substring = "";
        try {
            Resource resource = new ClassPathResource(path);
            String pathFile = resource.getURL().toString();
            substring = pathFile.substring(6);
            logger.info("证书地址,{}", substring);
        } catch (Exception e) {
            logger.error("证书地址异常,{}", e);
        }
        return substring;
    }
}
