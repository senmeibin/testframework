package com.bpms.sys.entity.ext;

/**
 * 短信设定类
 */
public class SmsAccount {
    /**
     * 短信供应商
     */
    private String vendor;

    /**
     * 短信URL
     */
    private String url;

    /**
     * 短信账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
