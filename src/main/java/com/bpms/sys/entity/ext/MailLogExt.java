package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.MailLog;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "sys_mail_log")
public class MailLogExt extends MailLog {
    private static final long serialVersionUID = -20160504171243947L;
    @Transient
    List<String> attachmentNameList;
    /**
     * 应用名称
     */
    @Transient
    private String appName;
    /**
     * 邮件日志UID
     */
    @Transient
    private String mailLogUid;
    /**
     * 邮件password (DES加密）
     */
    @Transient
    private String mailFromPassword;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMailLogUid() {
        return mailLogUid;
    }

    public void setMailLogUid(String mailLogUid) {
        this.mailLogUid = mailLogUid;
    }

    public String getMailFromPassword() {
        return mailFromPassword;
    }

    public void setMailFromPassword(String mailFromPassword) {
        this.mailFromPassword = mailFromPassword;
    }

    public List<String> getAttachmentNameList() {
        List<String> attachmentList = new ArrayList<>();
        if (StringUtils.isNoneBlank(this.getAttachmentName())) {
            attachmentList = Arrays.asList(this.getAttachmentName().split("$#"));
        }
        return attachmentList;
    }

    public void setAttachmentNameList(List<String> attachmentNameList) {
        this.attachmentNameList = attachmentNameList;
    }
}