package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 邮件日志历史实体类
 */
@MappedSuperclass
public class MailLogHistory extends SysBaseEntity {
    private static final long serialVersionUID = -20160922102005789L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 应用编号
     */
    @Length(max = 16, message = "请在应用编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入应用编号。")
    @Column(length = 16, nullable = false)
    private String appCode;

    /**
     * 邮件主题
     */
    @Length(max = 128, message = "请在邮件主题中输入[0-128]位以内的文字。")
    @NotEmpty(message = "请输入邮件主题。")
    @Column(length = 128, nullable = false)
    private String subject;

    /**
     * 邮件内容
     */
    @Column(nullable = false)
    @NotNull(message = "请输入邮件内容。")
    private String message;

    /**
     * 发送邮件地址
     */
    @Length(max = 64, message = "请在发送邮件地址中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String mailFrom;

    /**
     * 接受邮件地址(To)
     */
    @Length(max = 512, message = "请在接受邮件地址(To)中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String mailTo;

    /**
     * 接受邮件地址(Cc)
     */
    @Length(max = 512, message = "请在接受邮件地址(Cc)中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String mailCc;

    /**
     * 接受邮件地址(Bcc)
     */
    @Length(max = 512, message = "请在接受邮件地址(Bcc)中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String mailBcc;

    /**
     * 发送重试次数
     */
    @Range(min = 0, max = 99999999, message = "请在发送重试次数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer retryCount = 0;

    /**
     * 发送时间
     */
    @Column(nullable = false)
    @NotNull(message = "请输入发送时间。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date sendDate;

    /**
     * 发送结果
     */
    @Range(min = -99999999, max = 99999999, message = "请在发送结果中输入[-99999999-99999999]范围内的数值。")
    @Column
    private Integer sendResult = 1;

    /**
     * 错误消息
     */
    @Length(max = 256, message = "请在错误消息中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String errorMessage;

    /**
     * 附件名称
     */
    @Column
    private String attachmentName;


    /**
     * 数据迁移时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date migrationDate;

    public MailLogHistory() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        //邮件主题长度切取
        if (StringUtils.isNotEmpty(subject) && subject.length() > 128) {
            subject = subject.substring(0, 128);
        }
        this.subject = subject;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMailFrom() {
        return this.mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return this.mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailCc() {
        return this.mailCc;
    }

    public void setMailCc(String mailCc) {
        this.mailCc = mailCc;
    }

    public String getMailBcc() {
        return this.mailBcc;
    }

    public void setMailBcc(String mailBcc) {
        this.mailBcc = mailBcc;
    }

    public Integer getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
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

    public Date getMigrationDate() {
        return migrationDate;
    }

    public void setMigrationDate(Date migrationDate) {
        this.migrationDate = migrationDate;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
}