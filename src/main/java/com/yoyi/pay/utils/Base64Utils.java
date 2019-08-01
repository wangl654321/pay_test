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
        String sourceData = "";
        try {
            if (StringUtils.isEmpty(data)) {
                return null;
            }
            //对消息进行Base64解码
            sourceData = new String(ProcessMessage.Base64Decode(data), "GBK");
        } catch (Exception e) {
            System.out.println("解码异常" + e);
        }
        return sourceData;
    }


    /**
     * Base64编码解密
     *
     * @param data 待解密字符串
     * @return 解密后字节数组
     * @throws CodecException 异常
     */
    public static byte[] decodes(String data) {
        try {
            byte[] bytes = ProcessMessage.Base64Decode(data);
            return bytes;
        } catch (Exception e) {
            System.out.println("解码异常" + e);
        }
        return null;
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
        String xml = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0JLIiA/PjxCMkNSZXA+PGJhbmtDb3VudD4yMDwvYmFua0NvdW50PjxiYW5rTGlzdD48YmFua1Jvdz48YmFua05hbWU+0vjBqtDCzt6/qL/svd08L2JhbmtOYW1lPjxiYW5rSUQ+MDAxUTwvYmFua0lEPjxvdGhlckJhbmtJRD4wMDFRPC9vdGhlckJhbmtJRD48Y2FyZFR5cGU+WDwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7S+MGq0MLO3r+ouaTQ0Na7veg8L2JhbmtOYW1lPjxiYW5rSUQ+NTExQzwvYmFua0lEPjxvdGhlckJhbmtJRD4xMDI8L290aGVyQmFua0lEPjxjYXJkVHlwZT4wMTwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7S+MGqycy7p7LgvbvQ0Na7veg8L2JhbmtOYW1lPjxiYW5rSUQ+NTUxQzwvYmFua0lEPjxvdGhlckJhbmtJRD4zMDE8L290aGVyQmFua0lEPjxjYXJkVHlwZT4wMTwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7S+MGqycy7p7Lg1dDQ0Na7veg8L2JhbmtOYW1lPjxiYW5rSUQ+NTUyQzwvYmFua0lEPjxvdGhlckJhbmtJRD4zMDg8L290aGVyQmFua0lEPjxjYXJkVHlwZT4wMTwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7S+MGqycy7p7Lgxr2wsta7tPs8L2JhbmtOYW1lPjxiYW5rSUQ+NTU1QzwvYmFua0lEPjxvdGhlckJhbmtJRD4zMDc8L290aGVyQmFua0lEPjxjYXJkVHlwZT4wMjwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7S+MGqycy7p7LguaTQ0Na7veg8L2JhbmtOYW1lPjxiYW5rSUQ+NTY4QzwvYmFua0lEPjxvdGhlckJhbmtJRD4xMDI8L290aGVyQmFua0lEPjxjYXJkVHlwZT4wMTwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7N+MGqvajJ6NL40NDN+LnYQjJCPC9iYW5rTmFtZT48YmFua0lEPjU5NUI8L2JhbmtJRD48b3RoZXJCYW5rSUQ+MTA1PC9vdGhlckJhbmtJRD48Y2FyZFR5cGU+WDwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7N+MGqvajJ6NL40NDN+LnYveg8L2JhbmtOYW1lPjxiYW5rSUQ+NTk1QzwvYmFua0lEPjxvdGhlckJhbmtJRD4xMDU8L290aGVyQmFua0lEPjxjYXJkVHlwZT5YPC9jYXJkVHlwZT48L2JhbmtSb3c+PGJhbmtSb3c+PGJhbmtOYW1lPs34warW0Ln60vjQ0M34udi96DwvYmFua05hbWU+PGJhbmtJRD41OTZDPC9iYW5rSUQ+PG90aGVyQmFua0lEPjEwNDwvb3RoZXJCYW5rSUQ+PGNhcmRUeXBlPlg8L2NhcmRUeXBlPjwvYmFua1Jvdz48YmFua1Jvdz48YmFua05hbWU+zfjBqsWp0NDN+LnYQjJCPC9iYW5rTmFtZT48YmFua0lEPjU5N0I8L2JhbmtJRD48b3RoZXJCYW5rSUQ+MTAzPC9vdGhlckJhbmtJRD48Y2FyZFR5cGU+WDwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7N+MGqv+y93b2oyejS+NDQveg8L2JhbmtOYW1lPjxiYW5rSUQ+NjA0UTwvYmFua0lEPjxvdGhlckJhbmtJRD4xMDU8L290aGVyQmFua0lEPjxjYXJkVHlwZT5YPC9jYXJkVHlwZT48L2JhbmtSb3c+PGJhbmtSb3c+PGJhbmtOYW1lPs34waq/7L3dvajJ6NL40NC0+zwvYmFua05hbWU+PGJhbmtJRD42MDVRPC9iYW5rSUQ+PG90aGVyQmFua0lEPjEwNTwvb3RoZXJCYW5rSUQ+PGNhcmRUeXBlPlg8L2NhcmRUeXBlPjwvYmFua1Jvdz48YmFua1Jvdz48YmFua05hbWU+zfjBqr/svd25pMnM0vjQ0L3oPC9iYW5rTmFtZT48YmFua0lEPjYxNlE8L2JhbmtJRD48b3RoZXJCYW5rSUQ+MTAyPC9vdGhlckJhbmtJRD48Y2FyZFR5cGU+WDwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7N+MGqv+y93bmkyczS+NDQtPs8L2JhbmtOYW1lPjxiYW5rSUQ+NjE3UTwvYmFua0lEPjxvdGhlckJhbmtJRD4xMDI8L290aGVyQmFua0lEPjxjYXJkVHlwZT5YPC9jYXJkVHlwZT48L2JhbmtSb3c+PGJhbmtSb3c+PGJhbmtOYW1lPtL4warIq8f+tcA8L2JhbmtOYW1lPjxiYW5rSUQ+ODg4QjwvYmFua0lEPjxvdGhlckJhbmtJRD44ODhCPC9vdGhlckJhbmtJRD48Y2FyZFR5cGU+WDwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7S+MGqyKvH/rXAPC9iYW5rTmFtZT48YmFua0lEPjg4OEM8L2JhbmtJRD48b3RoZXJCYW5rSUQ+ODg4Qzwvb3RoZXJCYW5rSUQ+PGNhcmRUeXBlPlg8L2NhcmRUeXBlPjwvYmFua1Jvdz48YmFua1Jvdz48YmFua05hbWU+0vjBqs6i0MXJqMLr1qe4ti1YWDwvYmFua05hbWU+PGJhbmtJRD45MDNDPC9iYW5rSUQ+PG90aGVyQmFua0lEPjkwM0M8L290aGVyQmFua0lEPjxjYXJkVHlwZT5YPC9jYXJkVHlwZT48L2JhbmtSb3c+PGJhbmtSb3c+PGJhbmtOYW1lPtL4warWp7i2sabJqMLr1qe4ti2x6te8wOA8L2JhbmtOYW1lPjxiYW5rSUQ+OTExQzwvYmFua0lEPjxvdGhlckJhbmtJRD45MTFDPC9vdGhlckJhbmtJRD48Y2FyZFR5cGU+WDwvY2FyZFR5cGU+PC9iYW5rUm93PjxiYW5rUm93PjxiYW5rTmFtZT7S+MGqzqLQxcmowuvWp7i2LVhTPC9iYW5rTmFtZT48YmFua0lEPjk4MkM8L2JhbmtJRD48b3RoZXJCYW5rSUQ+OTgyQzwvb3RoZXJCYW5rSUQ+PGNhcmRUeXBlPlg8L2NhcmRUeXBlPjwvYmFua1Jvdz48YmFua1Jvdz48YmFua05hbWU+0vjBqsirx/61wNa7veg8L2JhbmtOYW1lPjxiYW5rSUQ+OTg4QzwvYmFua0lEPjxvdGhlckJhbmtJRD45ODhDPC9vdGhlckJhbmtJRD48Y2FyZFR5cGU+MDE8L2NhcmRUeXBlPjwvYmFua1Jvdz48L2JhbmtMaXN0PjwvQjJDUmVwPg==";
        System.out.println(decode(xml, "GBK"));
    }
}