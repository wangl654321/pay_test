package com.yoyi.pay.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Decoder;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * 描    述：提供对消息进行签名和验签的方法
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/1/23 14:32
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
public class ProcessMessage {

    private static Logger logger = LogManager.getLogger();

    public static Map<String, PrivateKey> privateKeyMap = new HashMap();

    public static Map<String, PublicKey> publicKeyMap = new HashMap();

    public static PublicKey publicKey = null;

    public static String signDate = null;

    /**
     * 修改static形式的公私钥（找到每回重启服务器后 支付代付不能同时验签成功的问题）
     */
    public ProcessMessage() {}

    /**
     * 对消息进行签名
     *
     * @param srcMessage 原始消息
     * @param certPath   签名证书路径
     * @param password   获取密钥的密码
     * @return b64SignMsg 签名之后的消息
     */
    public static byte[] signMessage(String srcMessage, String certPath, String password) throws Exception {

        System.out.println("甬易签名=============srcMessage:{},certPath:{},password:{}");
        String b64SignMsg = null;
        try {
            if (privateKeyMap.get(certPath) == null) {
                PrivateKey privateKey = CertSignUtil.getRSACertPrivateKey(certPath, password, CertSignUtil.CERT_TYPE_PKCS12);
                privateKeyMap.put(certPath, privateKey);
            }
            b64SignMsg = new String(Base64.encode(CertSignUtil.digitalSign(srcMessage.getBytes("GBK"), privateKeyMap.get(certPath), CertSignUtil.RSA_SHA1_SIGN)));
        } catch (Exception e) {
            throw new Exception("签名异常：" + e.getMessage());
        }
        return b64SignMsg.replaceAll("\r", "").replaceAll("\n", "").getBytes();
    }


    /**
     * 对消息进行签名
     *
     * @param srcMessage 原始消息
     * @param certPath   签名证书路径
     * @param password   获取密钥的密码
     * @return b64SignMsg 签名之后的消息
     */
    public static String sign(String srcMessage, String certPath, String password) throws Exception {

        logger.info("对消息进行签名srcMessage:{},certPath:{}", srcMessage, certPath);
        String b64SignMsg = null;
        try {
            if (privateKeyMap.get(certPath) == null) {
                logger.info("对消息进行签名,开始");
                PrivateKey privateKey = CertSignUtil.getRSACertPrivateKey(certPath, password, CertSignUtil.CERT_TYPE_JKS);
                privateKeyMap.put(certPath, privateKey);
            }
            b64SignMsg = new String(Base64.encode(CertSignUtil.digitalSign(srcMessage.getBytes("GBK"), privateKeyMap.get(certPath), CertSignUtil.RSA_SHA1_SIGN)));
        } catch (Exception e) {
            logger.error("对消息进行签名,签名异常", e);
        }
        return b64SignMsg;
    }

    /**
     * 对消息进行验签
     *
     * @param srcMessage 原始消息
     * @param resMessage 签名之后的消息
     * @param certPath   证书路径
     * @return true表示验签成功；false表示验签失败
     */
    public static boolean verifyMessage(String srcMessage, String resMessage, String certPath) {

        try {
            if (publicKeyMap.get(certPath) == null) {
                PublicKey publicKey = CertSignUtil.getRSAPublicKeyByFile(certPath);
                publicKeyMap.put(certPath, publicKey);
            }
            byte[] signBytes = Base64.decode(resMessage.getBytes());
            boolean verify = CertSignUtil.verifyDigitalSign(srcMessage.getBytes("GBK"), signBytes, publicKeyMap.get(certPath), CertSignUtil.RSA_SHA1_SIGN);
            if (!verify) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 对消息进行验签
     *
     * @param srcMessage 原始消息
     * @param resMessage 签名之后的消息
     * @param certPath   证书路径
     * @return 0=验签成功；1=验签失败；2=证书已过期；3=证书未生效；4=其他错误导致验签失败
     */
    public static boolean verifyUnSign(String srcMessage, String resMessage, String certPath) {

        try {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            if (publicKey == null || !date.equals(signDate)) {
                publicKey = CertSignUtil.getRSAPublicKeyByFile(certPath);
                signDate = date;
            }
            boolean verify = CertSignUtil.verifyDigitalSign(srcMessage.getBytes("GBK"), new BASE64Decoder().decodeBuffer(resMessage), publicKey, CertSignUtil.RSA_SHA1_SIGN);
            if (!verify) {
                return false;
            }
        } catch (Exception e) {
            logger.error("对消息进行验签,", e);
            return false;
        }
        return true;
    }

    /**
     * 对消息进行验签
     *
     * @param srcMessage 原始消息
     * @param resMessage 签名之后的消息
     * @param bCert      证书
     * @return true表示验签成功；false表示验签失败
     */
    public static boolean verifyMessage(String srcMessage, String resMessage, byte[] bCert) {

        try {
            if (publicKeyMap.get(new String(bCert)) == null) {
                PublicKey publicKey = CertSignUtil.getRSAPublicKeyByString(new String(bCert));
                publicKeyMap.put(new String(bCert), publicKey);
            }
            boolean verify = CertSignUtil.verifyDigitalSign(srcMessage.getBytes("GBK"), Base64.decode(resMessage.getBytes()), publicKeyMap.get(new String(bCert)), CertSignUtil.RSA_SHA1_SIGN);
            if (!verify) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 对消息进行Base64编码
     *
     * @param content 原始消息
     * @return 编码后的Base64字符串
     */
    public static String Base64Encode(byte[] content) {
        // Base64编码
        return new String(Base64.encode(content));
    }


    /**
     * 对消息进行Base64解码
     *
     * @param content 原始消息
     * @return 解码后的Base64字节数组
     */
    public static byte[] Base64Decode(String content) {
        //解码
        byte[] decData = null;
        try {
            decData =  new BASE64Decoder().decodeBuffer(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decData;
    }

}