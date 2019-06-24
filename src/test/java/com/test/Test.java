package com.test;

import cn.miludeer.jsoncode.JsonCode;

/***
 *
 *
 * 描    述：
 *
 * 创 建 者：@author wl
 * 创建时间：2019/6/2320:02
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
public class Test {

    private static String json = "{\"json\":{\"a\":{\"rrr\":[\"v1\",\"v2\"],\"www\":\"ff\"},\"b\":{\"rrr\":[\"v1\",\"v2\"],\"www\":\"4567ttt\"}}}";

    public static void main(String[] args){
        String value = JsonCode.getValue(json, "$.json.b.www");
        System.out.println(value);
    }
}
