package com.bpms.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@MappedSuperclass
@EntityListeners(BaseEntityAuditingListener.class)
public class BaseEntity extends CoreEntity {
    private static final long serialVersionUID = 6720864657040231966L;

    /**
     * InsertDate强制设定标志位
     */
    @Transient
    protected boolean insertDateForceSetting = true;

    /**
     * UID
     * 补充org.springframework.data.annotation.Id 用于非JPA类型的ID描述
     * 例如mango，solr，ElasticSearch
     */
    @Id
    @org.springframework.data.annotation.Id
    @Column(length = 32, nullable = false)
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.bpms.core.entity.IdGenerator")
    protected String uid;

    /**
     * 添加者
     */
    @CreatedBy
    @Column(length = 32, nullable = false, updatable = false)
    protected String insertUser;

    /**
     * 添加者
     */
    @Transient
    protected String insertUserName;

    /**
     * 添加日期
     */
    @CreatedDate
    @Column(nullable = false)
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    protected Date insertDate = Calendar.getInstance().getTime();

    /**
     * 更新者
     */
    @LastModifiedBy
    @Column(length = 32, nullable = false)
    protected String updateUser;

    /**
     * 更新者
     */
    @Transient
    protected String updateUserName;

    /**
     * 更新日期
     */
    @LastModifiedDate
    @Column(nullable = false)
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    protected Date updateDate = Calendar.getInstance().getTime();

    /**
     * 备注
     */
    @Length(max = 256, message = "请在备注中输入0-256位以内的文字。")
    @Column(length = 256)
    protected String remark;

    /**
     * 记录状态
     */
    @Column(nullable = false)
    protected Integer recordStatus = 1;

    public boolean isInsertDateForceSetting() {
        return insertDateForceSetting;
    }

    public void setInsertDateForceSetting(boolean insertDateForceSetting) {
        this.insertDateForceSetting = insertDateForceSetting;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInsertUserName() {
        return insertUserName;
    }

    public void setInsertUserName(String insertUserName) {
        this.insertUserName = insertUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
}
