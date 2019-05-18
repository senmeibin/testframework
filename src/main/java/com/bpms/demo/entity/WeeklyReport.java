package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 周报实体类
 */
@MappedSuperclass
public class WeeklyReport extends DemoBaseEntity {
    private static final long serialVersionUID = -20170421093624450L;

    /**
     * 所属基地
     */
    @Column(length = 32)
    private String baseUid;

    /**
     * 所属基地
     */
    @Transient
    private String baseName;

    /**
     * 人员UID
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
     * 填写时间(开始)
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date fillTimeStart;

    /**
     * 填写时间(结束)
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date fillTimeEnd;

    /**
     * 本周总结
     */
    @Length(max = 512, message = "请在本周总结中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String summary;

    public WeeklyReport() {
    }

    public String getBaseUid() {
        return baseUid;
    }

    public void setBaseUid(String baseUid) {
        this.baseUid = baseUid;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
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

    public Date getFillTimeStart() {
        return fillTimeStart;
    }

    public void setFillTimeStart(Date fillTimeStart) {
        this.fillTimeStart = fillTimeStart;
    }

    public Date getFillTimeEnd() {
        return fillTimeEnd;
    }

    public void setFillTimeEnd(Date fillTimeEnd) {
        this.fillTimeEnd = fillTimeEnd;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}