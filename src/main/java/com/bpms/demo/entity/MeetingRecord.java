package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 会议记录实体类
 */
@MappedSuperclass
public class MeetingRecord extends DemoBaseEntity {
    private static final long serialVersionUID = -20170522181344538L;

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
     * 会议主题
     */
    @Length(max = 64, message = "请在会议主题中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String subject;

    /**
     * 会议时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date meetingTime;

    /**
     * 会议地点
     */
    @Length(max = 128, message = "请在会议地点中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String meetingPlace;

    /**
     * 与会人员
     */
    @Length(max = 256, message = "请在与会人员中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String attendees;

    /**
     * 记录人
     */
    @Column(length = 32)
    private String recorderUid;

    /**
     * 记录人
     */
    @Transient
    private String recorderName;

    /**
     * 主要内容
     */
    @Length(max = 512, message = "请在主要内容中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String meetingContent;

    /**
     * 会议议题及结论
     */
    @Length(max = 512, message = "请在会议议题及结论中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String conferencesConclusions;

    public MeetingRecord() {
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

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getMeetingTime() {
        return this.meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingPlace() {
        return this.meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public String getAttendees() {
        return this.attendees;
    }

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    public String getRecorderUid() {
        return this.recorderUid;
    }

    public void setRecorderUid(String recorderUid) {
        this.recorderUid = recorderUid;
    }

    public String getRecorderName() {
        return this.recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName;
    }

    public String getMeetingContent() {
        return this.meetingContent;
    }

    public void setMeetingContent(String meetingContent) {
        this.meetingContent = meetingContent;
    }

    public String getConferencesConclusions() {
        return this.conferencesConclusions;
    }

    public void setConferencesConclusions(String conferencesConclusions) {
        this.conferencesConclusions = conferencesConclusions;
    }

}