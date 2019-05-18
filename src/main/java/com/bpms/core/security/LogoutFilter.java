package com.bpms.core.security;

import org.springframework.beans.factory.annotation.Value;

public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    /**
     * 是否启用CAS统一认证中心
     */
    @Value("${shiro.cas.enable:false}")
    protected boolean shiroCasEnable;

    @Override
    public String getRedirectUrl() {
        if (shiroCasEnable) {
            return super.getRedirectUrl();
        }
        return "/login";
    }
}