package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.Message;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

//注：防止共通模块与业务模块Entity同名问题，在共通模块的Entity中加入带前缀的name属性
@Entity(name = "CmnMessage")
@Table(name = "cmn_message")
public class MessageExt extends Message {
    private static final long serialVersionUID = -20160822140215027L;

    /**
     * 指定人员的UIDs
     */
    @Transient
    private String sendUserUids;

    /**
     * 当前用户未读信息数
     */
    @Transient
    private Integer currentUserUnreadCount;

    public String getSendUserUids() {
        return sendUserUids;
    }

    public void setSendUserUids(String sendUserUids) {
        this.sendUserUids = sendUserUids;
    }

    public Integer getCurrentUserUnreadCount() {
        return currentUserUnreadCount;
    }

    public void setCurrentUserUnreadCount(Integer currentUserUnreadCount) {
        this.currentUserUnreadCount = currentUserUnreadCount;
    }
}