package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 登录日志历史实体类
 */
@MappedSuperclass
public class LoginLogHistory extends SysBaseEntity {
    private static final long serialVersionUID = -20160922101659211L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 用户UID
     */
    @Column(length = 32)
    private String userUid;

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
     * 登录类型
     */
    @Range(min = 0, max = 99999, message = "请在登录类型中输入[0-99999]范围内的数值。")
    @Column
    private Integer loginType;

    /**
     * 登录结果
     */
    @Range(min = 0, max = 99999, message = "请在登录结果中输入[0-99999]范围内的数值。")
    @Column
    private Integer loginResult;

    /**
     * 路径
     */
    @Length(max = 512, message = "请在路径中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String url;

    /**
     * 参数
     */
    @Column
    private String parameters;

    /**
     * 数据迁移时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date migrationDate;

    public LoginLogHistory() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getUserUid() {
        return this.userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
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

    public Integer getLoginType() {
        return this.loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Integer getLoginResult() {
        return this.loginResult;
    }

    public void setLoginResult(Integer loginResult) {
        this.loginResult = loginResult;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameters() {
        return this.parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Date getMigrationDate() {
        return migrationDate;
    }

    public void setMigrationDate(Date migrationDate) {
        this.migrationDate = migrationDate;
    }
}