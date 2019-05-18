package com.bpms.core.security;

import com.bpms.core.utils.DesUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) {
        //需要加密属性
        //String[] needEncryptProperties = {"jdbc.master.password", "mail.password"};
        String[] needEncryptProperties = {"test"};
        for (String key : needEncryptProperties) {
            String value = props.getProperty(key);
            if (value != null) {
                props.setProperty(key, DesUtils.decrypt(value));
            }
        }
        //调用父类处理方法
        super.processProperties(beanFactory, props);
    }
}