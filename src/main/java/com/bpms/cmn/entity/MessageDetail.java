package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 消息推送管理明细表实体类
 */
@MappedSuperclass
public class MessageDetail extends CmnBaseEntity {
    private static final long serialVersionUID = -20160822171743243L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 消息UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择消息。")
    private String messageUid;

    /**
     * 消息
     */
    @Transient
    private String messageName;

    /**
     * 人员uid
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择人员。")
    private String userUid;

    /**
     * 人员
     */
    @Transient
    private String userName;

    /**
     * 是否已读
     */
    @Length(max = 8, message = "请在是否已读中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String readTypeCd = "01";

    /**
     * 是否已读
     */
    @Transient
    private String readTypeName;

    public MessageDetail() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getMessageUid() {
        return this.messageUid;
    }

    public void setMessageUid(String messageUid) {
        this.messageUid = messageUid;
    }

    public String getMessageName() {
        return this.messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getUserUid() {
        return this.userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReadTypeCd() {
        return this.readTypeCd;
    }

    public void setReadTypeCd(String readTypeCd) {
        this.readTypeCd = readTypeCd;
    }

    public String getReadTypeName() {
        return this.readTypeName;
    }

    public void setReadTypeName(String readTypeName) {
        this.readTypeName = readTypeName;
    }

}