package com.bpms.sys.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 附件访问日志实体类
 */
@MappedSuperclass
public class AttachmentAccessLog extends SysBaseEntity {
    private static final long serialVersionUID = -20171229141246507L;

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
     * 附件UID
     */
    @Column(name = "attachment_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择附件。")
    private String attachmentUid;

    /**
     * 附件
     */
    @Transient
    private String attachmentName;

    /**
     * 用户UID
     */
    @Column(name = "user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择用户。")
    private String userUid;

    /**
     * 用户
     */
    @Transient
    private String userName;

    /**
     * 访问区分
     */
    @Range(min = 0L, max = 99999999L, message = "请在访问区分中输入[0-99999999]范围内的数值。")
    @Column(name = "access_type")
    private Integer accessType;

    public AttachmentAccessLog() {
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Integer getEnterpriseUid() {
        return this.enterpriseUid;
    }

    public String getEnterpriseName() {
        return this.enterpriseName;
    }

    public void setAttachmentUid(String attachmentUid) {
        this.attachmentUid = attachmentUid;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentUid() {
        return this.attachmentUid;
    }

    public String getAttachmentName() {
        return this.attachmentName;
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

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public Integer getAccessType() {
        return this.accessType;
    }

}