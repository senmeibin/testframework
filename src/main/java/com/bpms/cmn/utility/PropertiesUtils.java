package com.bpms.cmn.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * PropertiesUtils 工具类
 */
class PropertiesUtils {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    /**
     * PROPERTIES_MAP
     */
    private static final Map<String, Properties> PROPERTIES_MAP = new HashMap<>();

    /**
     * 根据文件名获取配置信息
     *
     * @param resourcesPath 配置文件
     * @return
     */
    public static synchronized Properties getProperties(String resourcesPath) {
        Properties prop = PROPERTIES_MAP.get(resourcesPath);

        if (prop == null) {
            Long startTime = System.currentTimeMillis();
            Properties properties = new Properties();
            try {
                properties.load(PropertiesUtils.class.getResourceAsStream("/" + resourcesPath));
            } catch (Exception ex) {
                logger.error("无法加载配置文件" + resourcesPath + "：" + ex.getMessage());
                return properties;
            }

            //放入propertiesMap
            PROPERTIES_MAP.put(resourcesPath, properties);
            if (logger.isDebugEnabled()) {
                logger.debug("成功载配置文件{}，耗时{}ms", resourcesPath, System.currentTimeMillis() - startTime);
            }
            return properties;
        }
        return prop;
    }
}