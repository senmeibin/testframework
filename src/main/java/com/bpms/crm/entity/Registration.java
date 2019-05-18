package com.bpms.crm.entity;

import com.bpms.core.consts.CmnConsts;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 报名记录实体类
 */
@MappedSuperclass
public class Registration extends CrmBaseEntity {
    private static final long serialVersionUID = -20180407161905214L;

    /**
     * 学员UID
     */
    @Column(name = "student_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择学员。")
    private String studentUid;

    /**
     * 学员
     */
    @Transient
    private String studentName;

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
     * 所属校区UID
     */
    @Column(name = "registration_belong_campus_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择所属校区。")
    private String registrationBelongCampusUid;

    /**
     * 所属校区
     */
    @Transient
    private String registrationBelongCampusName;

    /**
     * 课程顾问UID
     */
    @Column(name = "registration_belong_consultant_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择课程顾问。")
    private String registrationBelongConsultantUserUid;

    /**
     * 课程顾问
     */
    @Transient
    private String registrationBelongConsultantUserName;

    /**
     * 报名时间
     */
    @Column(name = "registration_date", nullable = false)
    @NotNull(message = "请输入报名时间。")
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date registrationDate;

    /**
     * 报名校区UID
     */
    @Column(name = "registration_campus_uid", length = 32)
    private String registrationCampusUid;

    /**
     * 报名校区
     */
    @Transient
    private String registrationCampusName;

    /**
     * 报名班级
     */
    @Column(name = "registration_campus_class_uid", length = 32)
    private String registrationCampusClassUid;

    /**
     * 报名班级
     */
    @Transient
    private String registrationCampusClassName;

    /**
     * 新老学员
     */
    @Range(min = 0L, max = 99999999L, message = "请在新老学员中输入[0-99999999]范围内的数值。")
    @Column(name = "is_new_student")
    private Integer isNewStudent;

    public Registration() {
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

    public String getRegistrationBelongCampusUid() {
        return this.registrationBelongCampusUid;
    }

    public void setRegistrationBelongCampusUid(String registrationBelongCampusUid) {
        this.registrationBelongCampusUid = registrationBelongCampusUid;
    }

    public String getRegistrationBelongCampusName() {
        return this.registrationBelongCampusName;
    }

    public void setRegistrationBelongCampusName(String registrationBelongCampusName) {
        this.registrationBelongCampusName = registrationBelongCampusName;
    }

    public String getRegistrationBelongConsultantUserUid() {
        return this.registrationBelongConsultantUserUid;
    }

    public void setRegistrationBelongConsultantUserUid(String registrationBelongConsultantUserUid) {
        this.registrationBelongConsultantUserUid = registrationBelongConsultantUserUid;
    }

    public String getRegistrationBelongConsultantUserName() {
        return this.registrationBelongConsultantUserName;
    }

    public void setRegistrationBelongConsultantUserName(String registrationBelongConsultantUserName) {
        this.registrationBelongConsultantUserName = registrationBelongConsultantUserName;
    }

    public Date getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationCampusUid() {
        return this.registrationCampusUid;
    }

    public void setRegistrationCampusUid(String registrationCampusUid) {
        this.registrationCampusUid = registrationCampusUid;
    }

    public String getRegistrationCampusName() {
        return this.registrationCampusName;
    }

    public void setRegistrationCampusName(String registrationCampusName) {
        this.registrationCampusName = registrationCampusName;
    }

    public String getRegistrationCampusClassUid() {
        return this.registrationCampusClassUid;
    }

    public void setRegistrationCampusClassUid(String registrationCampusClassUid) {
        this.registrationCampusClassUid = registrationCampusClassUid;
    }

    public String getRegistrationCampusClassName() {
        return this.registrationCampusClassName;
    }

    public void setRegistrationCampusClassName(String registrationCampusClassName) {
        this.registrationCampusClassName = registrationCampusClassName;
    }

    public Integer getIsNewStudent() {
        return this.isNewStudent;
    }

    public void setIsNewStudent(Integer isNewStudent) {
        this.isNewStudent = isNewStudent;
    }
}