package com.bpms.crm.entity;

import com.bpms.core.consts.CmnConsts;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 跟进记录实体类
 */
@MappedSuperclass
public class Followup extends CrmBaseEntity {
    private static final long serialVersionUID = -20180331115336280L;

    /**
     * 学员UID
     */
    @Column(name = "student_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择学员。")
    private String studentUid;

    /**
     * 学员姓名
     */
    @Transient
    private String studentName;

    /**
     * 所属校区UID
     */
    @Column(name = "followup_belong_campus_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择所属校区。")
    private String followupBelongCampusUid;

    /**
     * 所属校区
     */
    @Transient
    private String followupBelongCampusName;

    /**
     * 课程顾问UID
     */
    @Column(name = "followup_belong_consultant_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择课程顾问。")
    private String followupBelongConsultantUserUid;

    /**
     * 课程顾问
     */
    @Transient
    private String followupBelongConsultantUserName;

    /**
     * 跟进时间
     */
    @Column(name = "followup_date", nullable = false)
    @NotNull(message = "请输入跟进时间。")
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date followupDate;

    /**
     * 跟进方式
     */
    @Length(max = 8, message = "请在跟进方式中输入[0-8]位以内的文字。")
    @Column(name = "followup_method_cd", length = 8)
    private String followupMethodCd;

    /**
     * 跟进方式
     */
    @Transient
    private String followupMethodName;

    /**
     * 跟进内容
     */
    @Column(name = "contents", nullable = false)
    @NotNull(message = "请输入跟进内容。")
    private String contents;

    /**
     * 下次跟进时间
     */
    @Column(name = "next_followup_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date nextFollowupDate;

    /**
     * 销售进程
     */
    @Length(max = 8, message = "请在销售进程中输入[0-8]位以内的文字。")
    @Column(name = "sale_process_cd", length = 8)
    private String saleProcessCd;

    /**
     * 销售进程
     */
    @Transient
    private String saleProcessName;

    /**
     * 是否预约
     */
    @Length(max = 16, message = "请在是否预约中输入[0-16]位以内的文字。")
    @Column(name = "accept_shift_cd", length = 16)
    private String acceptShiftCd;

    /**
     * 是否预约
     */
    @Transient
    private String acceptShiftName;

    /**
     * 预约UID
     */
    @Column(name = "reservation_uid", length = 32)
    private String reservationUid;

    /**
     * 预约
     */
    @Transient
    private String reservationName;

    /**
     * 记录新增模式
     */
    @Range(min = 0L, max = 99999999L, message = "请在记录新增模式中输入[0-99999999]范围内的数值。")
    @Column(name = "record_mode")
    private Integer recordMode = 0;

    public Followup() {
    }

    public String getStudentUid() {
        return this.studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFollowupBelongCampusUid() {
        return this.followupBelongCampusUid;
    }

    public void setFollowupBelongCampusUid(String followupBelongCampusUid) {
        this.followupBelongCampusUid = followupBelongCampusUid;
    }

    public String getFollowupBelongCampusName() {
        return this.followupBelongCampusName;
    }

    public void setFollowupBelongCampusName(String followupBelongCampusName) {
        this.followupBelongCampusName = followupBelongCampusName;
    }

    public String getFollowupBelongConsultantUserUid() {
        return this.followupBelongConsultantUserUid;
    }

    public void setFollowupBelongConsultantUserUid(String followupBelongConsultantUserUid) {
        this.followupBelongConsultantUserUid = followupBelongConsultantUserUid;
    }

    public String getFollowupBelongConsultantUserName() {
        return this.followupBelongConsultantUserName;
    }

    public void setFollowupBelongConsultantUserName(String followupBelongConsultantUserName) {
        this.followupBelongConsultantUserName = followupBelongConsultantUserName;
    }

    public Date getFollowupDate() {
        return this.followupDate;
    }

    public void setFollowupDate(Date followupDate) {
        this.followupDate = followupDate;
    }

    public String getFollowupMethodCd() {
        return this.followupMethodCd;
    }

    public void setFollowupMethodCd(String followupMethodCd) {
        this.followupMethodCd = followupMethodCd;
    }

    public String getFollowupMethodName() {
        return this.followupMethodName;
    }

    public void setFollowupMethodName(String followupMethodName) {
        this.followupMethodName = followupMethodName;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getNextFollowupDate() {
        return this.nextFollowupDate;
    }

    public void setNextFollowupDate(Date nextFollowupDate) {
        this.nextFollowupDate = nextFollowupDate;
    }

    public String getSaleProcessCd() {
        return this.saleProcessCd;
    }

    public void setSaleProcessCd(String saleProcessCd) {
        this.saleProcessCd = saleProcessCd;
    }

    public String getSaleProcessName() {
        return this.saleProcessName;
    }

    public void setSaleProcessName(String saleProcessName) {
        this.saleProcessName = saleProcessName;
    }

    public String getAcceptShiftCd() {
        return this.acceptShiftCd;
    }

    public void setAcceptShiftCd(String acceptShiftCd) {
        this.acceptShiftCd = acceptShiftCd;
    }

    public String getAcceptShiftName() {
        return this.acceptShiftName;
    }

    public void setAcceptShiftName(String acceptShiftName) {
        this.acceptShiftName = acceptShiftName;
    }

    public String getReservationUid() {
        return this.reservationUid;
    }

    public void setReservationUid(String reservationUid) {
        this.reservationUid = reservationUid;
    }

    public String getReservationName() {
        return this.reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public Integer getRecordMode() {
        return this.recordMode;
    }

    public void setRecordMode(Integer recordMode) {
        this.recordMode = recordMode;
    }

}