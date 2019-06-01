package com.yoyi.pay.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/***
 *
 *
 * 描    述：xml解析工具
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/1/23 15:05
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
public class YoYiPayUtil {

    private static Logger logger = LogManager.getLogger();

    /**
     * 将xml转为对象
     *
     * @param xmlString
     * @param param
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getReqParam(String xmlString, T param) {
        try {
            NodeList nodes = getNodeList(xmlString);
            if (nodes != null) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    if (null != node.getFirstChild()) {
                        String nodeName = node.getNodeName();
                        String nodeValue = node.getFirstChild().getNodeValue();
                        BeanUtils.setProperty(param, nodeName, nodeValue);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("将xml转为对象", e);
        }
        return param;
    }

    /**
     * 对象转xml
     *
     * @param param
     * @return
     */
    public static <T> String parseXML(T param) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq>");
            Field[] fields = param.getClass().getDeclaredFields();
            for (Field field : fields) {
                String k = field.getName();
                String v = BeanUtils.getProperty(param, k);
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
            sb.append("</B2CReq>");
        } catch (Exception e) {
            logger.error("对象转xml", e);
        }
        return sb.toString();
    }

    /**
     * XML字符串转map
     *
     * @param xmlString
     * @return
     */
    public static Map<String, String> xmlParse(String xmlString) {
        Map<String, String> map = new HashMap<>(16);
        try {
            NodeList nodes = getNodeList(xmlString);
            if (nodes != null) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    if (node.getFirstChild() != null) {
                        map.put(node.getNodeName(), node.getFirstChild().getNodeValue());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("XML字符串转map", e);
        }
        return map;
    }


    public static List<BankRow> BanksForPayxmlParse(String xmlString) {

        List<BankRow> bankRows = new ArrayList<>();
        try {
            NodeList repNodes = getNodeList(xmlString);
            if (repNodes != null) {
                String bankCount = repNodes.item(0).getFirstChild().getNodeValue();
                Element bankList = (Element) repNodes.item(1);
                NodeList banks = bankList.getChildNodes();
                if (banks.getLength() != Integer.valueOf(bankCount)) {
                    throw new Exception("网上有名支持付款通道数据不完全");
                }
                for (int j = 0; j < banks.getLength(); j++) {
                    Node bankRow = banks.item(j);
                    Element bankEle = (Element) bankRow;
                    String bankId = bankEle.getElementsByTagName("bankID").item(0).getFirstChild().getNodeValue();
                    String bankName = bankEle.getElementsByTagName("bankName").item(0).getFirstChild().getNodeValue();
                    String otherBankID = null;
                    Node otherBankIDNode = bankEle.getElementsByTagName("otherBankID").item(0).getFirstChild();
                    if (otherBankIDNode != null) {
                        otherBankID = otherBankIDNode.getNodeValue();
                    }
                    String cardType = bankEle.getElementsByTagName("cardType").item(0).getFirstChild().getNodeValue();
                    BankRow bank = new BankRow();
                    bank.setBankID(bankId);
                    bank.setBankName(bankName);
                    bank.setCardType(cardType);
                    bank.setOtherBankID(otherBankID);
                    bankRows.add(bank);
                }
            }
        } catch (Exception e) {
            logger.error("解析付款通道XML字符串异常", e);
        }
        return bankRows;
    }


    /**
     * map转xml(不保留非空)
     *
     * @param parameters
     * @return
     */
    public static String parseXMLIsNotBlank(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (StringUtils.isNotBlank(v)) {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</B2CReq>");
        return sb.toString();
    }

    /**
     * map转xml(保留非空)
     *
     * @param parameters
     * @return
     */
    public static String parseXMLIsBlank(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String v = (String) entry.getValue();
            String k = (String) entry.getKey();
            sb.append("<" + k + ">" + v + "</" + k + ">");
        }
        sb.append("</B2CReq>");
        return sb.toString();
    }

    public static String parseXMLAsync(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append("<" + k + ">" + v + "</" + k + ">");
        }
        sb.append("</B2CReq>");
        return sb.toString();
    }

    /**
     * 读文件
     *
     * @param path     文件路径
     * @param fileName 文件名
     * @return 文件内容
     * @throws Exception
     */
    public static byte[] readFile(String path, String fileName) throws Exception {
        File file = new File(path + "/" + fileName);
        if (file.exists()) {
            FileInputStream fis = null;
            ByteArrayOutputStream bos = null;
            try {
                fis = new FileInputStream(path + "/" + fileName);
                return getBytes(fis);
            } catch (Exception e) {
                throw new Exception("文件解析失败", e);
            }
        } else {
            throw new Exception(path + "/" + fileName + "文件不存在，放弃读文件");
        }
    }


    private static byte[] getBytes(FileInputStream fis) throws IOException {
        ByteArrayOutputStream bos;
        bos = new ByteArrayOutputStream(fis.available());
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, count);
        }
        bos.flush();
        return bos.toByteArray();
    }

    private static NodeList getNodeList(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
        Element root = doc.getDocumentElement();
        return root.getChildNodes();
    }

    public static byte[] readFile(File file) throws Exception {
        if (file.exists()) {
            FileInputStream fis = null;
            ByteArrayOutputStream bos = null;
            try {
                fis = new FileInputStream(file);
                return getBytes(fis);
            } catch (Exception e) {
                throw new Exception("文件解析失败", e);
            }
        } else {
            throw new Exception(file.getPath() + "文件不存在，放弃读文件");
        }
    }

    /**
     * xml中包含list的转换
     *
     * @param xmlString
     * @return
     * @throws Exception
     */
    public static Map<String, Object> xmlParseMap(String xmlString) throws Exception {
        Map<String, Object> mapKey = new HashMap<>(8);
        SAXReader reader = new SAXReader();
        List<Map<String, String>> list = new ArrayList<>();
        try {
            org.dom4j.Document document = reader.read(new InputSource(new StringReader(xmlString)));
            org.dom4j.Element root = document.getRootElement();
            List<org.dom4j.Element> childElements = root.elements();
            for (org.dom4j.Element child : childElements) {
                List<org.dom4j.Element> elementList = child.elements();
                //判断是否包含子节点
                if (elementList.isEmpty()) {
                    mapKey.put(child.getName(), child.getStringValue());
                } else {
                    for (org.dom4j.Element ele : elementList) {
                        Map<String, String> map = new HashMap<>(6);
                        List<org.dom4j.Element> elements = ele.elements();
                        for (org.dom4j.Element attr : elements) {
                            map.put(attr.getName(), attr.getStringValue());
                        }
                        list.add(map);
                    }
                }
                //子节点不为空
                if (!list.isEmpty()) {
                    mapKey.put(child.getName(), list);
                }
            }
        } catch (Exception e) {
            throw new Exception("获取付款通道特殊转换工具,转换异常", e);
        }
        return mapKey;
    }
}
