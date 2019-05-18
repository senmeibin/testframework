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
import java.util.Date;

/**
 * 预约记录实体类
 */
@MappedSuperclass
public class Reservation extends CrmBaseEntity {
    private static final long serialVersionUID = -20180407161811795L;

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
     * 跟进记录UID
     */
    @Column(name = "followup_uid", length = 32)
    private String followupUid;

    /**
     * 跟进记录
     */
    @Transient
    private String followupName;

    /**
     * 所属校区UID
     */
    @Column(name = "reservation_belong_campus_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择所属校区。")
    private String reservationBelongCampusUid;

    /**
     * 所属校区
     */
    @Transient
    private String reservationBelongCampusName;

    /**
     * 课程顾问UID
     */
    @Column(name = "reservation_belong_consultant_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择课程顾问。")
    private String reservationBelongConsultantUserUid;

    /**
     * 课程顾问
     */
    @Transient
    private String reservationBelongConsultantUserName;

    /**
     * 预约日期
     */
    @Column(name = "reservation_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date reservationDate;

    /**
     * 预约校区UID
     */
    @Column(name = "reservation_campus_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择预约校区。")
    private String reservationCampusUid;

    /**
     * 预约校区
     */
    @Transient
    private String reservationCampusName;

    /**
     * 预约班级UID
     */
    @Column(name = "reservation_campus_class_uid", length = 32)
    private String reservationCampusClassUid;

    /**
     * 预约班级
     */
    @Transient
    private String reservationCampusClassName;

    /**
     * 预约目的
     */
    @Length(max = 8, message = "请在预约目的中输入[0-8]位以内的文字。")
    @Column(name = "purpose_cd", length = 8)
    private String purposeCd;

    /**
     * 预约目的
     */
    @Transient
    private String purposeName;

    /**
     * 预约内容
     */
    @Length(max = 256, message = "请在预约内容中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入预约内容。")
    @Column(name = "contents", length = 256, nullable = false)
    private String contents;

    /**
     * 是否到访
     */
    @Range(min = 0L, max = 99999999L, message = "请在是否到访中输入[0-99999999]范围内的数值。")
    @Column(name = "is_visited")
    private Integer isVisited = 0;

    /**
     * 是否报名
     */
    @Range(min = 0L, max = 99999999L, message = "请在是否报名中输入[0-99999999]范围内的数值。")
    @Column(name = "is_registration")
    private Integer isRegistration = 0;

    /**
     * 报名UID
     */
    @Column(name = "registration_uid", length = 32)
    private String registrationUid;

    /**
     * 报名
     */
    @Transient
    private String registrationName;

    /**
     * 记录新增模式
     */
    @Range(min = 0L, max = 99999999L, message = "请在记录新增模式中输入[0-99999999]范围内的数值。")
    @Column(name = "record_mode")
    private Integer recordMode = 0;

    public Reservation() {
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

    public String getFollowupUid() {
        return this.followupUid;
    }

    public void setFollowupUid(String followupUid) {
        this.followupUid = followupUid;
    }

    public String getFollowupName() {
        return this.followupName;
    }

    public void setFollowupName(String followupName) {
        this.followupName = followupName;
    }

    public String getReservationBelongCampusUid() {
        return this.reservationBelongCampusUid;
    }

    public void setReservationBelongCampusUid(String reservationBelongCampusUid) {
        this.reservationBelongCampusUid = reservationBelongCampusUid;
    }

    public String getReservationBelongCampusName() {
        return this.reservationBelongCampusName;
    }

    public void setReservationBelongCampusName(String reservationBelongCampusName) {
        this.reservationBelongCampusName = reservationBelongCampusName;
    }

    public String getReservationBelongConsultantUserUid() {
        return this.reservationBelongConsultantUserUid;
    }

    public void setReservationBelongConsultantUserUid(String reservationBelongConsultantUserUid) {
        this.reservationBelongConsultantUserUid = reservationBelongConsultantUserUid;
    }

    public String getReservationBelongConsultantUserName() {
        return this.reservationBelongConsultantUserName;
    }

    public void setReservationBelongConsultantUserName(String reservationBelongConsultantUserName) {
        this.reservationBelongConsultantUserName = reservationBelongConsultantUserName;
    }

    public Date getReservationDate() {
        return this.reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationCampusUid() {
        return this.reservationCampusUid;
    }

    public void setReservationCampusUid(String reservationCampusUid) {
        this.reservationCampusUid = reservationCampusUid;
    }

    public String getReservationCampusName() {
        return this.reservationCampusName;
    }

    public void setReservationCampusName(String reservationCampusName) {
        this.reservationCampusName = reservationCampusName;
    }

    public String getReservationCampusClassUid() {
        return this.reservationCampusClassUid;
    }

    public void setReservationCampusClassUid(String reservationCampusClassUid) {
        this.reservationCampusClassUid = reservationCampusClassUid;
    }

    public String getReservationCampusClassName() {
        return this.reservationCampusClassName;
    }

    public void setReservationCampusClassName(String reservationCampusClassName) {
        this.reservationCampusClassName = reservationCampusClassName;
    }

    public String getPurposeCd() {
        return this.purposeCd;
    }

    public void setPurposeCd(String purposeCd) {
        this.purposeCd = purposeCd;
    }

    public String getPurposeName() {
        return this.purposeName;
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getIsVisited() {
        return this.isVisited;
    }

    public void setIsVisited(Integer isVisited) {
        this.isVisited = isVisited;
    }

    public Integer getIsRegistration() {
        return this.isRegistration;
    }

    public void setIsRegistration(Integer isRegistration) {
        this.isRegistration = isRegistration;
    }

    public String getRegistrationUid() {
        return this.registrationUid;
    }

    public void setRegistrationUid(String registrationUid) {
        this.registrationUid = registrationUid;
    }

    public String getRegistrationName() {
        return this.registrationName;
    }

    public void setRegistrationName(String registrationName) {
        this.registrationName = registrationName;
    }

    public Integer getRecordMode() {
        return this.recordMode;
    }

    public void setRecordMode(Integer recordMode) {
        this.recordMode = recordMode;
    }
}