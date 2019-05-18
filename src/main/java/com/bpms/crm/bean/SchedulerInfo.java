package com.bpms.crm.bean;

import com.bpms.core.consts.CmnConsts;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SchedulerInfo {
    /**
     * 关联类别（1：跟进/2：预约）
     */
    private String relationType;

    /**
     * 关联UID
     */
    private String relationUid;

    /**
     * 预定日期
     */
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date scheduleDate;

    /**
     * 学员UID
     */
    private String studentUid;

    /**
     * 学员姓名
     */
    private String studentName;

    /**
     * 内容
     */
    private String contents;

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getRelationUid() {
        return relationUid;
    }

    public void setRelationUid(String relationUid) {
        this.relationUid = relationUid;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getStudentUid() {
        return studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
