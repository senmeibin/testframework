package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.MessageDetail;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

//注：防止共通模块与业务模块Entity同名问题，在共通模块的Entity中加入带前缀的name属性
@Entity(name = "CmnMessageDetail")
@Table(name = "cmn_message_detail")
public class MessageDetailExt extends MessageDetail {
    private static final long serialVersionUID = -20160822171743343L;

    /**
     * 消息标题
     */
    @Transient
    private String messageTitle;

    /**
     * 消息内容
     */
    @Transient
    private String messageContent;

    /**
     * 是否富文本消息
     */
    @Transient
    private Integer isRichMessage;

    /**
     * 重要度
     */
    @Transient
    private String importanceDegreeName;

    /**
     * 消息类型
     */
    @Transient
    private String messageTypeName;

    /**
     * 部门
     */
    @Transient
    private String deptName;

    /**
     * 职位
     */
    @Transient
    private String positionName;

    /**
     * 用户CD
     */
    @Transient
    private String userCd;

    /**
     * 邮件
     */
    @Transient
    private String userMail;

    /**
     * 电话
     */
    @Transient
    private String userPhone;

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getIsRichMessage() {
        return isRichMessage;
    }

    public void setIsRichMessage(Integer isRichMessage) {
        this.isRichMessage = isRichMessage;
    }

    public String getImportanceDegreeName() {
        return importanceDegreeName;
    }

    public void setImportanceDegreeName(String importanceDegreeName) {
        this.importanceDegreeName = importanceDegreeName;
    }

    public String getMessageTypeName() {
        return messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}