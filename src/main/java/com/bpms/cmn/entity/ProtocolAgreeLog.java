package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 协议同意日志实体类
 */
@MappedSuperclass
public class ProtocolAgreeLog extends CmnBaseEntity {
    private static final long serialVersionUID = -20180329100121313L;

    /**
     * 协议UID
     */
    @Column(name = "protocol_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择协议。")
    private String protocolUid;

    /**
     * 协议
     */
    @Transient
    private String protocolName;

    /**
     * 确认人
     */
    @Column(name = "user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择确认人。")
    private String userUid;

    /**
     * 确认人
     */
    @Transient
    private String userName;

    /**
     * 手机号
     */
    @Length(max = 32, message = "请在手机号中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入手机号。")
    @Column(name = "mobile", length = 32, nullable = false)
    private String mobile;

    /**
     * 确认IP
     */
    @Length(max = 32, message = "请在确认IP中输入[0-32]位以内的文字。")
    @Column(name = "sign_ip", length = 32)
    private String signIp;

    /**
     * 用户代理
     */
    @Length(max = 512, message = "请在用户代理中输入[0-512]位以内的文字。")
    @Column(name = "user_agent", length = 512)
    private String userAgent;

    public ProtocolAgreeLog() {
    }

    public void setProtocolUid(String protocolUid) {
        this.protocolUid = protocolUid;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolUid() {
        return this.protocolUid;
    }

    public String getProtocolName() {
        return this.protocolName;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUid() {
        return this.userUid;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setSignIp(String signIp) {
        this.signIp = signIp;
    }

    public String getSignIp() {
        return this.signIp;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

}