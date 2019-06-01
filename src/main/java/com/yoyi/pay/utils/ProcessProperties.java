package com.yoyi.pay.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/***
 *
 *
 * 描    述：properties工具类，通过key获取value
 *
 * 创 建 者： @author W.X
 * 创建时间： 2016年10月14日 16:51
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
public class ProcessProperties extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap;

    @Override
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    /**
     * @param name
     * @return
     */
    public static String getContextProperty(String name) {
        if (ctxPropertiesMap.get(name) == null) {
            return "";
        }
        return ctxPropertiesMap.get(name).toString();
    }

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        return super.convertProperty(propertyName, propertyValue);
    }

}
