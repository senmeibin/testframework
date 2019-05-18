package com.bpms.core.security;

import org.springframework.beans.factory.annotation.Value;

public class ShiroFilterFactoryBean extends org.apache.shiro.spring.web.ShiroFilterFactoryBean {

    /**
     * 是否启用CAS统一认证中心
     */
    @Value("${shiro.cas.enable:false}")
    protected boolean shiroCasEnable;

    /**
     * 自定义登录认证跳转URL
     */
    @Value("${shiro.login.url:/login}")
    protected String shiroLoginUrl;

    @Override
    public String getLoginUrl() {
        if (shiroCasEnable) {
            return super.getLoginUrl();
        }
        return this.shiroLoginUrl;
    }
}