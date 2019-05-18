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
 * 企业服务跟踪实体类
 */
@MappedSuperclass
public class ServiceTracking extends DemoBaseEntity {
    private static final long serialVersionUID = -20170418160144685L;

    /**
     * 企业UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择企业。")
    private String companyUid;

    /**
     * 走访企业
     */
    @Transient
    private String companyName;

    /**
     * 走访人
     */
    @Column(length = 32)
    private String visitUserUid;

    /**
     * 走访人
     */
    @Transient
    private String visitUserName;

    /**
     * 企业拜访人
     */
    @Length(max = 32, message = "请在企业拜访人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String visitor;

    /**
     * 下次跟踪时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date nextTrackingTime;

    /**
     * 下次跟踪服务人
     */
    @Column(length = 32)
    private String nextTrackingUserUid;

    /**
     * 下次跟踪服务人
     */
    @Transient
    private String nextTrackingUserName;

    /**
     * 企业需求
     */
    @Length(max = 512, message = "请在企业需求中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String businessRequirements;

    /**
     * 走访时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date visitTime;

    /**
     * 走访状态
     */
    @Length(max = 8, message = "请在走访状态中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String visitStatusCd;

    /**
     * 走访状态
     */
    @Transient
    private String visitStatusName;

    public ServiceTracking() {
    }

    public String getCompanyUid() {
        return this.companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getVisitUserUid() {
        return visitUserUid;
    }

    public void setVisitUserUid(String visitUserUid) {
        this.visitUserUid = visitUserUid;
    }

    public String getVisitUserName() {
        return visitUserName;
    }

    public void setVisitUserName(String visitUserName) {
        this.visitUserName = visitUserName;
    }

    public String getVisitor() {
        return this.visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public Date getNextTrackingTime() {
        return this.nextTrackingTime;
    }

    public void setNextTrackingTime(Date nextTrackingTime) {
        this.nextTrackingTime = nextTrackingTime;
    }

    public String getNextTrackingUserUid() {
        return nextTrackingUserUid;
    }

    public void setNextTrackingUserUid(String nextTrackingUserUid) {
        this.nextTrackingUserUid = nextTrackingUserUid;
    }

    public String getNextTrackingUserName() {
        return nextTrackingUserName;
    }

    public void setNextTrackingUserName(String nextTrackingUserName) {
        this.nextTrackingUserName = nextTrackingUserName;
    }

    public String getBusinessRequirements() {
        return this.businessRequirements;
    }

    public void setBusinessRequirements(String businessRequirements) {
        this.businessRequirements = businessRequirements;
    }

    public Date getVisitTime() {
        return this.visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitStatusCd() {
        return visitStatusCd;
    }

    public void setVisitStatusCd(String visitStatusCd) {
        this.visitStatusCd = visitStatusCd;
    }

    public String getVisitStatusName() {
        return visitStatusName;
    }

    public void setVisitStatusName(String visitStatusName) {
        this.visitStatusName = visitStatusName;
    }
}