package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 在线用户实体类
 */
@MappedSuperclass
public class OnlineUser extends SysBaseEntity {
    private static final long serialVersionUID = -20160505095713038L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 会话UID
     */
    @Length(max = 64, message = "请在会话UID中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入会话UID。")
    @Column(length = 64, nullable = false)
    private String sessionId;

    /**
     * 用户名
     */
    @Length(max = 64, message = "请在用户名中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入用户名。")
    @Column(length = 64, nullable = false)
    private String userCd;

    /**
     * 用户名称
     */
    @Length(max = 32, message = "请在用户名称中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String userName;

    /**
     * 登录IP
     */
    @Length(max = 32, message = "请在登录IP中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入登录IP。")
    @Column(length = 32, nullable = false)
    private String remoteIp;

    /**
     * NETBIOS机器名称
     */
    @Length(max = 256, message = "请在NETBIOS机器名称中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String netbiosMachineName;

    public OnlineUser() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserCd() {
        return this.userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemoteIp() {
        return this.remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getNetbiosMachineName() {
        return this.netbiosMachineName;
    }

    public void setNetbiosMachineName(String netbiosMachineName) {
        this.netbiosMachineName = netbiosMachineName;
    }

}