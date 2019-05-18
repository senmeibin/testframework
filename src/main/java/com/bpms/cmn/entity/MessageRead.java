package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 消息既读实体类
 */
@MappedSuperclass
public class MessageRead extends CmnBaseEntity {
    private static final long serialVersionUID = -20180316160010548L;

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
     * 关联UID
     */
    @Column(name = "relation_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择关联。")
    private String relationUid;

    /**
     * 关联
     */
    @Transient
    private String relationName;

    /**
     * 区分CD
     */
    @Length(max = 8, message = "请在区分CD中输入[0-8]位以内的文字。")
    @Column(name = "type_cd", length = 8)
    private String typeCd;

    public MessageRead() {
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

    public String getRelationUid() {
        return this.relationUid;
    }

    public void setRelationUid(String relationUid) {
        this.relationUid = relationUid;
    }

    public String getRelationName() {
        return this.relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getTypeCd() {
        return this.typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

}