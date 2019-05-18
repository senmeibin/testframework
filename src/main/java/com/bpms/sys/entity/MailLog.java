package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 邮件日志实体类
 */
@MappedSuperclass
public class MailLog extends SysBaseEntity {
    private static final long serialVersionUID = -20160504171243845L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 应用编号
     */
    @Column(length = 16, nullable = false)
    private String appCode;

    /**
     * 邮件主题
     */
    @Column(length = 128, nullable = false)
    private String subject;

    /**
     * 邮件内容
     */
    @Column(nullable = false)
    private String message;

    /**
     * 发送邮件地址
     */
    @Column(length = 64)
    private String mailFrom;

    /**
     * 接受邮件地址(To)
     */
    @Column(length = 512)
    private String mailTo;

    /**
     * 接受邮件地址(Cc)
     */
    @Column(length = 512)
    private String mailCc;

    /**
     * 接受邮件地址(Bcc)
     */
    @Column(length = 512)
    private String mailBcc;

    /**
     * 发送重试次数
     */
    @Column
    private Integer retryCount = 0;

    /**
     * 附件名称
     */
    @Column
    private String attachmentName;

    /**
     * 发送时间
     */
    @Column(nullable = false)
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date sendDate;

    /**
     * 发送结果
     */
    @Column
    private Integer sendResult = 1;

    /**
     * 错误消息
     */
    @Column(length = 256)
    private String errorMessage;

    public MailLog() {
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

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
}