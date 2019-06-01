package com.yoyi.pay.utils;


import org.springframework.core.codec.CodecException;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import java.nio.charset.Charset;

/***
 *
 *
 * 描    述：Base64加解密
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/1/23 14:47
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
public class Base64Utils {

    /**
     * Base64编码 加密
     *
     * @param data 待加密字节数组
     * @return 加密后字符串
     */
    public static String encode(String data) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        String tranData = new BASE64Encoder().encode(data.getBytes(Charset.forName("GBK")));
        return tranData;
    }


    /**
     * Base64编码解密
     *
     * @param data 待解密字符串
     * @return 解密后字节数组
     * @throws CodecException 异常
     */
    public static String decode(String data) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        //对消息进行Base64解码
        String sourceData = new String(ProcessMessage.Base64Decode(data));
        return sourceData;
    }


    /**
     * Base64编码解密
     *
     * @param data        参数
     * @param charsetName 字符编码
     * @return
     * @throws Exception
     */
    public static String decode(String data, String charsetName) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        //对消息进行Base64解码
        String sourceData = new String(ProcessMessage.Base64Decode(data), charsetName);
        return sourceData;
    }


    public static void main(String[] args) throws Exception {
        String xml = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0JLIiA/PjxCMkNSZXM+PHN0YXR1cz42PC9zdGF0dXM+PGN1c3RvbWVySWQ+QzEwMDAxMzYyMzwvY3VzdG9tZXJJZD48cmVmdXNlUmVhc29uPiA8L3JlZnVzZVJlYXNvbj48Y29tcGFueT6348HWze2x9rndNDwvY29tcGFueT48YmluZFN0cz4wPC9iaW5kU3RzPjwvQjJDUmVzPg==";
        System.out.println(decode(xml,"GBK"));
    }
}