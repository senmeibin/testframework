package com.bpms.cmn.utility;


import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * ApplicationPropertiesUtils 工具类
 */
public class ApplicationPropertiesUtils {
    /**
     * 资源文件名
     */
    private static final String RESOURCES_PATH = "application.properties";
    /**
     * 配置文件
     */
    private static Properties properties;

    /**
     * 取得指定的属性值
     *
     * @param key 属性名称
     * @return 属性值
     */
    public static String getValue(String key) {
        return getValue(key, StringUtils.EMPTY);
    }

    /**
     * 取得指定的属性值
     *
     * @param key          属性名称
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static String getValue(String key, String defaultValue) {
        properties = PropertiesUtils.getProperties(RESOURCES_PATH);
        String value = properties.getProperty(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 取得指定的属性值
     *
     * @param key 属性名称
     * @return 属性值
     */
    public static int getIntValue(String key) {
        int defaultValue = 0;
        try {
            return Integer.parseInt(getValue(key, String.valueOf(defaultValue)));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * 取得指定的属性值
     *
     * @param key 属性名称
     * @return 属性值
     */
    public static int getIntValue(String key, int defaultValue) {
        try {
            return Integer.parseInt(getValue(key, String.valueOf(defaultValue)));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * 判断指定的属性是否启用
     *
     * @param key 属性名称
     * @return true：启用/false：未启用[默认]
     */
    public static boolean isEnable(String key) {
        return StringUtils.equalsIgnoreCase(getValue(key, "false"), "true") ? true : false;
    }
}