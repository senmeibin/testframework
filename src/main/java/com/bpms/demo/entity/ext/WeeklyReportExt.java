package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.WeeklyReport;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "demo_weekly_report")
public class WeeklyReportExt extends WeeklyReport {
    private static final long serialVersionUID = -20170421093624550L;

    /**
     * 周报明细实体类列表
     */
    @Transient
    private List<WeeklyReportDetailExt> weeklyReportDetailList;

    public List<WeeklyReportDetailExt> getWeeklyReportDetailList() {
        return weeklyReportDetailList;
    }

    public void setWeeklyReportDetailList(List<WeeklyReportDetailExt> weeklyReportDetailList) {
        this.weeklyReportDetailList = weeklyReportDetailList;
    }
}