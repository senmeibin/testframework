package com.bpms.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 周报明细实体类
 */
@MappedSuperclass
public class WeeklyReportDetail extends DemoBaseEntity {
    private static final long serialVersionUID = -20170421100841550L;

    /**
     * 周报UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择周报。")
    private String weeklyReportUid;

    /**
     * 周报
     */
    @Transient
    private String weeklyReportName;

    /**
     * 周次
     */
    @Length(max = 8, message = "请在周次中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String weekCd;

    /**
     * 周次
     */
    @Transient
    private String weekName;

    /**
     * 已完成工作
     */
    @Length(max = 512, message = "请在已完成工作中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String completedWork;

    /**
     * 未完成工作
     */
    @Length(max = 512, message = "请在未完成工作中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String unfinishedWork;

    /**
     * 开心的事
     */
    @Length(max = 512, message = "请在开心的事中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String happyThing;

    public WeeklyReportDetail() {
    }

    public String getWeeklyReportUid() {
        return this.weeklyReportUid;
    }

    public void setWeeklyReportUid(String weeklyReportUid) {
        this.weeklyReportUid = weeklyReportUid;
    }

    public String getWeeklyReportName() {
        return this.weeklyReportName;
    }

    public void setWeeklyReportName(String weeklyReportName) {
        this.weeklyReportName = weeklyReportName;
    }

    public String getWeekCd() {
        return this.weekCd;
    }

    public void setWeekCd(String weekCd) {
        this.weekCd = weekCd;
    }

    public String getWeekName() {
        return this.weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public String getCompletedWork() {
        return this.completedWork;
    }

    public void setCompletedWork(String completedWork) {
        this.completedWork = completedWork;
    }

    public String getUnfinishedWork() {
        return this.unfinishedWork;
    }

    public void setUnfinishedWork(String unfinishedWork) {
        this.unfinishedWork = unfinishedWork;
    }

    public String getHappyThing() {
        return this.happyThing;
    }

    public void setHappyThing(String happyThing) {
        this.happyThing = happyThing;
    }

}