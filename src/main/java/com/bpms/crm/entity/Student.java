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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 学员信息实体类
 */
@MappedSuperclass
public class Student extends CrmBaseEntity {
    private static final long serialVersionUID = -20180325114920900L;

    /**
     * 学员姓名
     */
    @Length(max = 32, message = "请在学员姓名中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入学员姓名。")
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    /**
     * 性别
     */
    @Length(max = 8, message = "请在性别中输入[0-8]位以内的文字。")
    @Column(name = "gender_cd", length = 8)
    private String genderCd;

    /**
     * 性别
     */
    @Transient
    private String genderName;

    /**
     * 照片
     */
    @Length(max = 256, message = "请在照片中输入[0-256]位以内的文字。")
    @Column(name = "picture", length = 256)
    private String picture;

    /**
     * 证件类型
     */
    @Length(max = 8, message = "请在证件类型中输入[0-8]位以内的文字。")
    @Column(name = "card_type_cd", length = 8)
    private String cardTypeCd = "01";

    /**
     * 证件类型
     */
    @Transient
    private String cardTypeName;

    /**
     * 证件号码
     */
    @Length(max = 32, message = "请在证件号码中输入[0-32]位以内的文字。")
    @Column(name = "card_number", length = 32)
    private String cardNumber;

    /**
     * 学员年龄
     */
    @Range(min = -99L, max = 99L, message = "请在学员年龄中输入[-99-99]范围内的数值。")
    @Column(name = "student_age")
    private BigDecimal studentAge;

    /**
     * 出生年月
     */
    @Column(name = "birthday")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date birthday;

    /**
     * 围棋基础
     */
    @Length(max = 8, message = "请在围棋基础中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入围棋基础。")
    @Column(name = "base_level_cd", length = 8, nullable = false)
    private String baseLevelCd = "999";

    /**
     * 围棋基础
     */
    @Transient
    private String baseLevelName;

    /**
     * 所属校区UID
     */
    @Column(name = "student_belong_campus_uid", length = 32)
    private String studentBelongCampusUid;

    /**
     * 所属校区
     */
    @Transient
    private String studentBelongCampusName;

    /**
     * 课程顾问UID
     */
    @Column(name = "student_belong_consultant_user_uid", length = 32)
    private String studentBelongConsultantUserUid;

    /**
     * 课程顾问
     */
    @Transient
    private String studentBelongConsultantUserName;

    /**
     * 咨询方式
     */
    @Length(max = 8, message = "请在咨询方式中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入咨询方式。")
    @Column(name = "consult_method_cd", length = 8, nullable = false)
    private String consultMethodCd = "99";

    /**
     * 咨询方式
     */
    @Transient
    private String consultMethodName;

    /**
     * 信息来源
     */
    @Length(max = 8, message = "请在信息来源中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入信息来源。")
    @Column(name = "source_type_cd", length = 8, nullable = false)
    private String sourceTypeCd = "99";

    /**
     * 信息来源
     */
    @Transient
    private String sourceTypeName;

    /**
     * 家长姓名
     */
    @Length(max = 32, message = "请在家长姓名中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入家长姓名。")
    @Column(name = "parent_name", length = 32, nullable = false)
    private String parentName;

    /**
     * 与学员关系
     */
    @Length(max = 8, message = "请在与学员关系中输入[0-8]位以内的文字。")
    @Column(name = "relationship_type_cd", length = 8)
    private String relationshipTypeCd;

    /**
     * 与学员关系
     */
    @Transient
    private String relationshipTypeName;

    /**
     * 手机号码
     */
    @Length(max = 11, message = "请在手机号码中输入[0-11]位以内的文字。")
    @NotEmpty(message = "请输入手机号码。")
    @Column(name = "mobile", length = 11, nullable = false)
    private String mobile;

    /**
     * 电子邮件
     */
    @Length(max = 64, message = "请在电子邮件中输入[0-64]位以内的文字。")
    @Column(name = "email", length = 64)
    private String email;

    /**
     * 所在省
     */
    @Column(name = "province_uid", length = 32)
    private String provinceUid;

    /**
     * 所在省
     */
    @Transient
    private String provinceName;

    /**
     * 所在市
     */
    @Column(name = "city_uid", length = 32)
    private String cityUid;

    /**
     * 所在市
     */
    @Transient
    private String cityName;

    /**
     * 所在区
     */
    @Column(name = "region_uid", length = 32)
    private String regionUid;

    /**
     * 所在区
     */
    @Transient
    private String regionName;

    /**
     * 家庭住址
     */
    @Length(max = 256, message = "请在家庭住址中输入[0-256]位以内的文字。")
    @Column(name = "home_address", length = 256)
    private String homeAddress;

    /**
     * 邮政编码
     */
    @Length(max = 6, message = "请在邮政编码中输入[0-6]位以内的文字。")
    @Column(name = "zip_code", length = 6)
    private String zipCode;

    /**
     * 之前学校
     */
    @Length(max = 64, message = "请在之前学校中输入[0-64]位以内的文字。")
    @Column(name = "before_school", length = 64)
    private String beforeSchool;

    /**
     * 学员状态
     */
    @Length(max = 8, message = "请在学员状态中输入[0-8]位以内的文字。")
    @Column(name = "student_status_cd", length = 8)
    private String studentStatusCd = "01";

    /**
     * 学员状态
     */
    @Transient
    private String studentStatusName;

    /**
     * 最近跟进UID
     */
    @Column(name = "last_followup_uid", length = 32)
    private String lastFollowupUid;

    /**
     * 最近跟进
     */
    @Transient
    private String lastFollowupName;

    /**
     * 首次咨询时间
     */
    @Column(name = "first_consult_time")
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date firstConsultTime;

    /**
     * 最近咨询时间
     */
    @Column(name = "recent_consult_time")
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date recentConsultTime;

    /**
     * 记录新增模式
     */
    @Range(min = 0L, max = 99999999L, message = "请在记录新增模式中输入[0-99999999]范围内的数值。")
    @Column(name = "record_mode")
    private Integer recordMode = 0;

    /**
     * 跟进次数
     */
    @Range(min = 0L, max = 99999999L, message = "请在跟进次数中输入[0-99999999]范围内的数值。")
    @Column(name = "followup_count")
    private Integer followupCount = 0;

    /**
     * 预约次数
     */
    @Range(min = 0L, max = 99999999L, message = "请在预约次数中输入[0-99999999]范围内的数值。")
    @Column(name = "reservation_count")
    private Integer reservationCount = 0;

    /**
     * 报名次数
     */
    @Range(min = 0L, max = 99999999L, message = "请在报名次数中输入[0-99999999]范围内的数值。")
    @Column(name = "registration_count")
    private Integer registrationCount = 0;

    /**
     * 缴费次数
     */
    @Range(min = 0L, max = 99999999L, message = "请在缴费次数中输入[0-99999999]范围内的数值。")
    @Column(name = "payment_count")
    private Integer paymentCount = 0;

    public Student() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenderCd() {
        return this.genderCd;
    }

    public void setGenderCd(String genderCd) {
        this.genderCd = genderCd;
    }

    public String getGenderName() {
        return this.genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCardTypeCd() {
        return this.cardTypeCd;
    }

    public void setCardTypeCd(String cardTypeCd) {
        this.cardTypeCd = cardTypeCd;
    }

    public String getCardTypeName() {
        return this.cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getStudentAge() {
        return this.studentAge;
    }

    public void setStudentAge(BigDecimal studentAge) {
        this.studentAge = studentAge;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBaseLevelCd() {
        return this.baseLevelCd;
    }

    public void setBaseLevelCd(String baseLevelCd) {
        this.baseLevelCd = baseLevelCd;
    }

    public String getBaseLevelName() {
        return this.baseLevelName;
    }

    public void setBaseLevelName(String baseLevelName) {
        this.baseLevelName = baseLevelName;
    }

    public String getStudentBelongCampusUid() {
        return this.studentBelongCampusUid;
    }

    public void setStudentBelongCampusUid(String studentBelongCampusUid) {
        this.studentBelongCampusUid = studentBelongCampusUid;
    }

    public String getStudentBelongCampusName() {
        return this.studentBelongCampusName;
    }

    public void setStudentBelongCampusName(String studentBelongCampusName) {
        this.studentBelongCampusName = studentBelongCampusName;
    }

    public String getStudentBelongConsultantUserUid() {
        return this.studentBelongConsultantUserUid;
    }

    public void setStudentBelongConsultantUserUid(String studentBelongConsultantUserUid) {
        this.studentBelongConsultantUserUid = studentBelongConsultantUserUid;
    }

    public String getStudentBelongConsultantUserName() {
        return this.studentBelongConsultantUserName;
    }

    public void setStudentBelongConsultantUserName(String studentBelongConsultantUserName) {
        this.studentBelongConsultantUserName = studentBelongConsultantUserName;
    }

    public String getConsultMethodCd() {
        return this.consultMethodCd;
    }

    public void setConsultMethodCd(String consultMethodCd) {
        this.consultMethodCd = consultMethodCd;
    }

    public String getConsultMethodName() {
        return this.consultMethodName;
    }

    public void setConsultMethodName(String consultMethodName) {
        this.consultMethodName = consultMethodName;
    }

    public String getSourceTypeCd() {
        return this.sourceTypeCd;
    }

    public void setSourceTypeCd(String sourceTypeCd) {
        this.sourceTypeCd = sourceTypeCd;
    }

    public String getSourceTypeName() {
        return this.sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getRelationshipTypeCd() {
        return this.relationshipTypeCd;
    }

    public void setRelationshipTypeCd(String relationshipTypeCd) {
        this.relationshipTypeCd = relationshipTypeCd;
    }

    public String getRelationshipTypeName() {
        return this.relationshipTypeName;
    }

    public void setRelationshipTypeName(String relationshipTypeName) {
        this.relationshipTypeName = relationshipTypeName;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvinceUid() {
        return this.provinceUid;
    }

    public void setProvinceUid(String provinceUid) {
        this.provinceUid = provinceUid;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityUid() {
        return this.cityUid;
    }

    public void setCityUid(String cityUid) {
        this.cityUid = cityUid;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionUid() {
        return this.regionUid;
    }

    public void setRegionUid(String regionUid) {
        this.regionUid = regionUid;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBeforeSchool() {
        return this.beforeSchool;
    }

    public void setBeforeSchool(String beforeSchool) {
        this.beforeSchool = beforeSchool;
    }

    public String getStudentStatusCd() {
        return this.studentStatusCd;
    }

    public void setStudentStatusCd(String studentStatusCd) {
        this.studentStatusCd = studentStatusCd;
    }

    public String getStudentStatusName() {
        return this.studentStatusName;
    }

    public void setStudentStatusName(String studentStatusName) {
        this.studentStatusName = studentStatusName;
    }

    public String getLastFollowupUid() {
        return this.lastFollowupUid;
    }

    public void setLastFollowupUid(String lastFollowupUid) {
        this.lastFollowupUid = lastFollowupUid;
    }

    public String getLastFollowupName() {
        return this.lastFollowupName;
    }

    public void setLastFollowupName(String lastFollowupName) {
        this.lastFollowupName = lastFollowupName;
    }

    public Date getFirstConsultTime() {
        return this.firstConsultTime;
    }

    public void setFirstConsultTime(Date firstConsultTime) {
        this.firstConsultTime = firstConsultTime;
    }

    public Date getRecentConsultTime() {
        return this.recentConsultTime;
    }

    public void setRecentConsultTime(Date recentConsultTime) {
        this.recentConsultTime = recentConsultTime;
    }

    public Integer getRecordMode() {
        return this.recordMode;
    }

    public void setRecordMode(Integer recordMode) {
        this.recordMode = recordMode;
    }

    public Integer getFollowupCount() {
        return this.followupCount;
    }

    public void setFollowupCount(Integer followupCount) {
        this.followupCount = followupCount;
    }

    public Integer getReservationCount() {
        return this.reservationCount;
    }

    public void setReservationCount(Integer reservationCount) {
        this.reservationCount = reservationCount;
    }

    public Integer getRegistrationCount() {
        return this.registrationCount;
    }

    public void setRegistrationCount(Integer registrationCount) {
        this.registrationCount = registrationCount;
    }

    public Integer getPaymentCount() {
        return this.paymentCount;
    }

    public void setPaymentCount(Integer paymentCount) {
        this.paymentCount = paymentCount;
    }
}