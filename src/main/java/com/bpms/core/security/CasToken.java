package com.bpms.core.security;

import org.apache.shiro.authc.HostAuthenticationToken;


/**
 * 自定义CasToken可存放IP等信息
 */
public class CasToken extends org.apache.shiro.cas.CasToken implements HostAuthenticationToken {
    /**
     * 登录IP
     */
    private String host;

    public CasToken(String ticket) {
        super(ticket);
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}