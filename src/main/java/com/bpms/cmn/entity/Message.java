package com.bpms.cmn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 消息推送管理表实体类
 */
@MappedSuperclass
public class Message extends CmnBaseEntity {
    private static final long serialVersionUID = -20160822140214924L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 消息类型
     */
    @Length(max = 8, message = "请在消息类型中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入消息类型。")
    @Column(length = 8, nullable = false)
    private String messageTypeCd;

    /**
     * 消息类型
     */
    @Transient
    private String messageTypeName;

    /**
     * 消息标题
     */
    @Length(max = 64, message = "请在消息标题中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入消息标题。")
    @Column(length = 64, nullable = false)
    private String messageTitle;

    /**
     * 消息内容
     */
    @Column
    private String messageContent;

    /**
     * 重要度
     */
    @Length(max = 8, message = "请在重要度中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String importanceDegreeCd = "01";

    /**
     * 重要度
     */
    @Transient
    private String importanceDegreeName;

    /**
     * 发送区分
     */
    @Length(max = 8, message = "请在发送区分中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String sendTypeCd;

    /**
     * 发送区分
     */
    @Transient
    private String sendTypeName;

    /**
     * 发送人员所属部门uids
     */
    @Length(max = 1024, message = "请在发送人员所属部门uids中输入[0-1024]位以内的文字。")
    @Column(length = 1024)
    private String sendDeptUids;

    /**
     * 发送人员所属角色uids
     */
    @Length(max = 1024, message = "请在发送人员所属角色uids中输入[0-1024]位以内的文字。")
    @Column(length = 1024)
    private String sendRoleUids;

    /**
     * 消息提醒开始时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date messageStartDate;

    /**
     * 消息提醒结束时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date messageEndDate;

    /**
     * 提示总人数
     */
    @Range(min = 0, max = 99999999, message = "请在提示总人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer allCount;

    /**
     * 未读总人数
     */
    @Range(min = 0, max = 99999999, message = "请在未读总人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer unreadCount;

    /**
     * 是否邮件通知
     */
    @Range(min = 0, max = 99999999, message = "请在是否邮件通知中输入[0-99999999]范围内的数值。")
    @Column
    private Integer isMailNotice = 0;

    /**
     * 是否短信通知
     */
    @Range(min = 0, max = 99999999, message = "请在是否短信通知中输入[0-99999999]范围内的数值。")
    @Column
    private Integer isSmsNotice = 0;

    /**
     * 是否富文本消息
     */
    @Range(min = 0, max = 99999999, message = "请在是否富文本消息中输入[0-99999999]范围内的数值。")
    @Column
    private Integer isRichMessage = 0;

    public Message() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getMessageTypeCd() {
        return this.messageTypeCd;
    }

    public void setMessageTypeCd(String messageTypeCd) {
        this.messageTypeCd = messageTypeCd;
    }

    public String getMessageTypeName() {
        return this.messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
    }

    public String getMessageTitle() {
        return this.messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return this.messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getImportanceDegreeCd() {
        return this.importanceDegreeCd;
    }

    public void setImportanceDegreeCd(String importanceDegreeCd) {
        this.importanceDegreeCd = importanceDegreeCd;
    }

    public String getImportanceDegreeName() {
        return this.importanceDegreeName;
    }

    public void setImportanceDegreeName(String importanceDegreeName) {
        this.importanceDegreeName = importanceDegreeName;
    }

    public String getSendTypeCd() {
        return this.sendTypeCd;
    }

    public void setSendTypeCd(String sendTypeCd) {
        this.sendTypeCd = sendTypeCd;
    }

    public String getSendTypeName() {
        return this.sendTypeName;
    }

    public void setSendTypeName(String sendTypeName) {
        this.sendTypeName = sendTypeName;
    }

    public String getSendDeptUids() {
        return this.sendDeptUids;
    }

    public void setSendDeptUids(String sendDeptUids) {
        this.sendDeptUids = sendDeptUids;
    }

    public String getSendRoleUids() {
        return this.sendRoleUids;
    }

    public void setSendRoleUids(String sendRoleUids) {
        this.sendRoleUids = sendRoleUids;
    }

    public Date getMessageStartDate() {
        return this.messageStartDate;
    }

    public void setMessageStartDate(Date messageStartDate) {
        this.messageStartDate = messageStartDate;
    }

    public Date getMessageEndDate() {
        return this.messageEndDate;
    }

    public void setMessageEndDate(Date messageEndDate) {
        this.messageEndDate = messageEndDate;
    }

    public Integer getAllCount() {
        return this.allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getUnreadCount() {
        return this.unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public Integer getIsMailNotice() {
        return isMailNotice;
    }

    public void setIsMailNotice(Integer isMailNotice) {
        this.isMailNotice = isMailNotice;
    }

    public Integer getIsSmsNotice() {
        return isSmsNotice;
    }

    public void setIsSmsNotice(Integer isSmsNotice) {
        this.isSmsNotice = isSmsNotice;
    }

    public Integer getIsRichMessage() {
        return isRichMessage;
    }

    public void setIsRichMessage(Integer isRichMessage) {
        this.isRichMessage = isRichMessage;
    }
}