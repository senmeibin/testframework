package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 来访记录实体类
 */
@MappedSuperclass
public class VisitorRecords extends DemoBaseEntity {
    private static final long serialVersionUID = -20170421164346460L;

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
     * 来访单位
     */
    @Length(max = 64, message = "请在来访单位中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String visitingUnits;

    /**
     * 来访时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date visitingTime;

    /**
     * 来访人数
     */
    @Range(min = 0, max = 99999999, message = "请在来访人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfVisitors;

    /**
     * 接待人
     */
    @Column(length = 32)
    private String receptionistUid;

    /**
     * 接待人
     */
    @Transient
    private String receptionistName;

    /**
     * 来访事由
     */
    @Length(max = 256, message = "请在来访事由中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String visitingReasons;

    public VisitorRecords() {
    }

    public String getBaseUid() {
        return this.baseUid;
    }

    public void setBaseUid(String baseUid) {
        this.baseUid = baseUid;
    }

    public String getBaseName() {
        return this.baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getVisitingUnits() {
        return this.visitingUnits;
    }

    public void setVisitingUnits(String visitingUnits) {
        this.visitingUnits = visitingUnits;
    }

    public Date getVisitingTime() {
        return this.visitingTime;
    }

    public void setVisitingTime(Date visitingTime) {
        this.visitingTime = visitingTime;
    }

    public Integer getNumberOfVisitors() {
        return this.numberOfVisitors;
    }

    public void setNumberOfVisitors(Integer numberOfVisitors) {
        this.numberOfVisitors = numberOfVisitors;
    }

    public String getReceptionistUid() {
        return receptionistUid;
    }

    public void setReceptionistUid(String receptionistUid) {
        this.receptionistUid = receptionistUid;
    }

    public String getReceptionistName() {
        return receptionistName;
    }

    public void setReceptionistName(String receptionistName) {
        this.receptionistName = receptionistName;
    }

    public String getVisitingReasons() {
        return this.visitingReasons;
    }

    public void setVisitingReasons(String visitingReasons) {
        this.visitingReasons = visitingReasons;
    }

}