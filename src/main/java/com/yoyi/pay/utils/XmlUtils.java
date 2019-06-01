package com.yoyi.pay.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 *
 *
 * 描    述：获取付款通道特殊转换工具
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/3/29 15:49
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
public class XmlUtils {

    private static Logger logger = LogManager.getLogger();

    /**
     * 获取付款通道特殊转换工具
     *
     * @param xmlString
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> xmlParse(String xmlString) throws Exception {

        SAXReader reader = new SAXReader();
        List<Map<String, String>> list = new ArrayList<>();
        try {
            Document document = reader.read(new InputSource(new StringReader(xmlString)));
            Element root = document.getRootElement();
            List<Element> childElements = root.elements();
            for (Element child : childElements) {
                List<Element> elementList = child.elements();
                for (Element ele : elementList) {
                    Map<String, String> map = new HashMap<>(8);
                    List<Element> elements = ele.elements();
                    for (Element attr : elements) {
                        map.put(attr.getName(), attr.getStringValue());
                    }
                    list.add(map);
                }
            }
        } catch (Exception e) {
            logger.error("获取付款通道特殊转换工具,转换异常,{}", e);
        }
        return list;
    }
}