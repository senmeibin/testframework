package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 短信日志实体类
 */
@MappedSuperclass
public class SmsLog extends SysBaseEntity {
    private static final long serialVersionUID = -20180313080603550L;

    /**
     * 企业UID
     */
    @Column(name = "enterprise_uid", length = 8)
    private Integer enterpriseUid;

    /**
     * 企业
     */
    @Transient
    private String enterpriseName;

    /**
     * 应用编号
     */
    @Length(max = 16, message = "请在应用编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入应用编号。")
    @Column(name = "app_code", length = 16, nullable = false)
    private String appCode;

    /**
     * 短信内容
     */
    @Column(name = "message", nullable = false)
    @NotNull(message = "请输入短信内容。")
    private String message;

    /**
     * 短信供应商
     */
    @Length(max = 8, message = "请在短信供应商中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入短信供应商。")
    @Column(name = "vendor", length = 8, nullable = false)
    private String vendor;

    /**
     * 手机号码
     */
    @Length(max = 16, message = "请在手机号码中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入手机号码。")
    @Column(name = "mobile", length = 16, nullable = false)
    private String mobile;

    /**
     * 发送时间
     */
    @Column(name = "send_date", nullable = false)
    @NotNull(message = "请输入发送时间。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date sendDate;

    /**
     * 发送结果
     */
    @Column(name = "send_result")
    private Integer sendResult = 1;

    /**
     * 错误消息
     */
    @Length(max = 256, message = "请在错误消息中输入[0-256]位以内的文字。")
    @Column(name = "error_message", length = 256)
    private String errorMessage;

    public SmsLog() {
    }

    public Integer getEnterpriseUid() {
        return this.enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getEnterpriseName() {
        return this.enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getSendDate() {
        return this.sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getSendResult() {
        return this.sendResult;
    }

    public void setSendResult(Integer sendResult) {
        this.sendResult = sendResult;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}