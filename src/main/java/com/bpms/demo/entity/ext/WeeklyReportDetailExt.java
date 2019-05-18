package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.WeeklyReportDetail;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_weekly_report_detail")
public class WeeklyReportDetailExt extends WeeklyReportDetail {
    private static final long serialVersionUID = -20170421100841650L;
}