package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 短信验证码实体类
 */
@MappedSuperclass
public class SmsAuthCode extends SysBaseEntity {
    private static final long serialVersionUID = -20171018160938932L;

    /**
     * 手机号码
     */
    @Length(max = 16, message = "请在手机号码中输入[0-16]位以内的文字。")
    @Column(name = "mobile", length = 16)
    private String mobile;

    /**
     * 验证码
     */
    @Length(max = 8, message = "请在验证码中输入[0-8]位以内的文字。")
    @Column(name = "auth_code", length = 8)
    private String authCode;

    /**
     * 验证码类型
     */
    @Length(max = 8, message = "请在验证码类型中输入[0-8]位以内的文字。")
    @Column(name = "auth_code_type", length = 8)
    private String authCodeType;

    /**
     * 验证码类型
     */
    @Transient
    private String authCodeTypeName;

    /**
     * 有效期限
     */
    @Column(name = "valid_time")
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date validTime;

    /**
     * 是否已验证
     */
    @Column(name = "validated", length = 8)
    private Integer validated = 0;

    /**
     * 是否已验证
     */
    @Transient
    private String validatedName;

    public SmsAuthCode() {
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCodeType() {
        return authCodeType;
    }

    public void setAuthCodeType(String authCodeType) {
        this.authCodeType = authCodeType;
    }

    public String getAuthCodeTypeName() {
        return authCodeTypeName;
    }

    public void setAuthCodeTypeName(String authCodeTypeName) {
        this.authCodeTypeName = authCodeTypeName;
    }

    public Date getValidTime() {
        return this.validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Integer getValidated() {
        return validated;
    }

    public void setValidated(Integer validated) {
        this.validated = validated;
    }

    public String getValidatedName() {
        return validatedName;
    }

    public void setValidatedName(String validatedName) {
        this.validatedName = validatedName;
    }
}