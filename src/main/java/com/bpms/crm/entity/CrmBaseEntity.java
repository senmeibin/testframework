package com.bpms.crm.entity;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CRM模块BaseEntity
 */
@MappedSuperclass
public class CrmBaseEntity extends BaseEntity {
    private static final long serialVersionUID = -20160000111133333L;

    /**
     * 性别
     */
    @Transient
    private String studentGenderCd;

    /**
     * 性别
     */
    @Transient
    private String studentGenderName;

    /**
     * 照片
     */
    @Transient
    private String studentPicture;

    /**
     * 证件类型
     */
    @Transient
    private String studentCardTypeCd;

    /**
     * 证件类型
     */
    @Transient
    private String studentCardTypeName;

    /**
     * 证件号码
     */
    @Transient
    private String studentCardNumber;

    /**
     * 学员年龄
     */
    @Transient
    private BigDecimal studentAge;

    /**
     * 出生年月
     */
    @Transient
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date studentBirthday;

    /**
     * 围棋基础
     */
    @Transient
    private String studentBaseLevelCd;

    /**
     * 围棋基础
     */
    @Transient
    private String studentBaseLevelName;

    /**
     * 咨询方式
     */
    @Transient
    private String studentConsultMethodCd;

    /**
     * 咨询方式
     */
    @Transient
    private String studentConsultMethodName;

    /**
     * 信息来源
     */
    @Transient
    private String studentSourceTypeCd;

    /**
     * 信息来源
     */
    @Transient
    private String studentSourceTypeName;

    /**
     * 家长姓名
     */
    @Transient
    private String studentParentName;

    /**
     * 与学员关系
     */
    @Transient
    private String studentRelationshipTypeCd;

    /**
     * 与学员关系
     */
    @Transient
    private String studentRelationshipTypeName;

    /**
     * 手机号码
     */
    @Transient
    private String studentMobile;

    /**
     * 电子邮件
     */
    @Transient
    private String studentEmail;

    /**
     * 所在省
     */
    @Transient
    private String studentProvinceUid;

    /**
     * 所在省
     */
    @Transient
    private String studentProvinceName;

    /**
     * 所在市
     */
    @Transient
    private String studentCityUid;

    /**
     * 所在市
     */
    @Transient
    private String studentCityName;

    /**
     * 所在区
     */
    @Transient
    private String studentRegionUid;

    /**
     * 所在区
     */
    @Transient
    private String studentRegionName;

    /**
     * 家庭住址
     */
    @Transient
    private String studentHomeAddress;

    /**
     * 邮政编码
     */
    @Transient
    private String studentZipCode;

    /**
     * 之前学校
     */
    @Transient
    private String studentBeforeSchool;

    /**
     * 学员状态
     */
    @Transient
    private String studentStatusCd;

    /**
     * 学员状态
     */
    @Transient
    private String studentStatusName;

    /**
     * 首次咨询时间
     */
    @Transient
    private Date studentFirstConsultTime;

    /**
     * 最近咨询时间
     */
    @Transient
    private Date studentRecentConsultTime;

    /**
     * 跟进次数
     */
    @Transient
    private Integer studentFollowupCount;

    /**
     * 学员备注
     */
    @Transient
    private String studentRemark;

    /**
     * 学员所属校区
     */
    @Transient
    private String studentBelongCampusName;

    /**
     * 学员课程顾问
     */
    @Transient
    private String studentBelongConsultantUserName;

    public String getStudentGenderCd() {
        return studentGenderCd;
    }

    public void setStudentGenderCd(String studentGenderCd) {
        this.studentGenderCd = studentGenderCd;
    }

    public String getStudentGenderName() {
        return studentGenderName;
    }

    public void setStudentGenderName(String studentGenderName) {
        this.studentGenderName = studentGenderName;
    }

    public String getStudentPicture() {
        return studentPicture;
    }

    public void setStudentPicture(String studentPicture) {
        this.studentPicture = studentPicture;
    }

    public String getStudentCardTypeCd() {
        return studentCardTypeCd;
    }

    public void setStudentCardTypeCd(String studentCardTypeCd) {
        this.studentCardTypeCd = studentCardTypeCd;
    }

    public String getStudentCardTypeName() {
        return studentCardTypeName;
    }

    public void setStudentCardTypeName(String studentCardTypeName) {
        this.studentCardTypeName = studentCardTypeName;
    }

    public String getStudentCardNumber() {
        return studentCardNumber;
    }

    public void setStudentCardNumber(String studentCardNumber) {
        this.studentCardNumber = studentCardNumber;
    }

    public BigDecimal getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(BigDecimal studentAge) {
        this.studentAge = studentAge;
    }

    public Date getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(Date studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    public String getStudentBaseLevelCd() {
        return studentBaseLevelCd;
    }

    public void setStudentBaseLevelCd(String studentBaseLevelCd) {
        this.studentBaseLevelCd = studentBaseLevelCd;
    }

    public String getStudentBaseLevelName() {
        return studentBaseLevelName;
    }

    public void setStudentBaseLevelName(String studentBaseLevelName) {
        this.studentBaseLevelName = studentBaseLevelName;
    }

    public String getStudentConsultMethodCd() {
        return studentConsultMethodCd;
    }

    public void setStudentConsultMethodCd(String studentConsultMethodCd) {
        this.studentConsultMethodCd = studentConsultMethodCd;
    }

    public String getStudentConsultMethodName() {
        return studentConsultMethodName;
    }

    public void setStudentConsultMethodName(String studentConsultMethodName) {
        this.studentConsultMethodName = studentConsultMethodName;
    }

    public String getStudentSourceTypeCd() {
        return studentSourceTypeCd;
    }

    public void setStudentSourceTypeCd(String studentSourceTypeCd) {
        this.studentSourceTypeCd = studentSourceTypeCd;
    }

    public String getStudentSourceTypeName() {
        return studentSourceTypeName;
    }

    public void setStudentSourceTypeName(String studentSourceTypeName) {
        this.studentSourceTypeName = studentSourceTypeName;
    }

    public String getStudentParentName() {
        return studentParentName;
    }

    public void setStudentParentName(String studentParentName) {
        this.studentParentName = studentParentName;
    }

    public String getStudentRelationshipTypeCd() {
        return studentRelationshipTypeCd;
    }

    public void setStudentRelationshipTypeCd(String studentRelationshipTypeCd) {
        this.studentRelationshipTypeCd = studentRelationshipTypeCd;
    }

    public String getStudentRelationshipTypeName() {
        return studentRelationshipTypeName;
    }

    public void setStudentRelationshipTypeName(String studentRelationshipTypeName) {
        this.studentRelationshipTypeName = studentRelationshipTypeName;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentProvinceUid() {
        return studentProvinceUid;
    }

    public void setStudentProvinceUid(String studentProvinceUid) {
        this.studentProvinceUid = studentProvinceUid;
    }

    public String getStudentProvinceName() {
        return studentProvinceName;
    }

    public void setStudentProvinceName(String studentProvinceName) {
        this.studentProvinceName = studentProvinceName;
    }

    public String getStudentCityUid() {
        return studentCityUid;
    }

    public void setStudentCityUid(String studentCityUid) {
        this.studentCityUid = studentCityUid;
    }

    public String getStudentCityName() {
        return studentCityName;
    }

    public void setStudentCityName(String studentCityName) {
        this.studentCityName = studentCityName;
    }

    public String getStudentRegionUid() {
        return studentRegionUid;
    }

    public void setStudentRegionUid(String studentRegionUid) {
        this.studentRegionUid = studentRegionUid;
    }

    public String getStudentRegionName() {
        return studentRegionName;
    }

    public void setStudentRegionName(String studentRegionName) {
        this.studentRegionName = studentRegionName;
    }

    public String getStudentHomeAddress() {
        return studentHomeAddress;
    }

    public void setStudentHomeAddress(String studentHomeAddress) {
        this.studentHomeAddress = studentHomeAddress;
    }

    public String getStudentZipCode() {
        return studentZipCode;
    }

    public void setStudentZipCode(String studentZipCode) {
        this.studentZipCode = studentZipCode;
    }

    public String getStudentBeforeSchool() {
        return studentBeforeSchool;
    }

    public void setStudentBeforeSchool(String studentBeforeSchool) {
        this.studentBeforeSchool = studentBeforeSchool;
    }

    public String getStudentStatusCd() {
        return studentStatusCd;
    }

    public void setStudentStatusCd(String studentStatusCd) {
        this.studentStatusCd = studentStatusCd;
    }

    public String getStudentStatusName() {
        return studentStatusName;
    }

    public void setStudentStatusName(String studentStatusName) {
        this.studentStatusName = studentStatusName;
    }

    public Date getStudentFirstConsultTime() {
        return studentFirstConsultTime;
    }

    public void setStudentFirstConsultTime(Date studentFirstConsultTime) {
        this.studentFirstConsultTime = studentFirstConsultTime;
    }

    public Date getStudentRecentConsultTime() {
        return studentRecentConsultTime;
    }

    public void setStudentRecentConsultTime(Date studentRecentConsultTime) {
        this.studentRecentConsultTime = studentRecentConsultTime;
    }

    public Integer getStudentFollowupCount() {
        return studentFollowupCount;
    }

    public void setStudentFollowupCount(Integer studentFollowupCount) {
        this.studentFollowupCount = studentFollowupCount;
    }

    public String getStudentRemark() {
        return studentRemark;
    }

    public void setStudentRemark(String studentRemark) {
        this.studentRemark = studentRemark;
    }

    public String getStudentBelongCampusName() {
        return studentBelongCampusName;
    }

    public void setStudentBelongCampusName(String studentBelongCampusName) {
        this.studentBelongCampusName = studentBelongCampusName;
    }

    public String getStudentBelongConsultantUserName() {
        return studentBelongConsultantUserName;
    }

    public void setStudentBelongConsultantUserName(String studentBelongConsultantUserName) {
        this.studentBelongConsultantUserName = studentBelongConsultantUserName;
    }
}