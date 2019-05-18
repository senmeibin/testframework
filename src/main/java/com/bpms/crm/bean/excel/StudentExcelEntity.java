package com.bpms.crm.bean.excel;

import com.bpms.core.bean.BatchImportDetail;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.excel.ExcelColumn;
import com.bpms.core.excel.ExcelEntity;
import com.bpms.crm.entity.ext.StudentExt;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 学员信息Excel批量导入实体类
 */
@ExcelEntity
public class StudentExcelEntity extends BatchImportDetail implements Serializable {
    /**
     * 订单附属信息实体类
     */
    private StudentExt student;

    /**
     * 姓名
     */
    @ExcelColumn(name = "学员姓名", required = true)
    private String name;

    /**
     * 性别
     */
    private String genderCd;
    @ExcelColumn(name = "性别")
    private String genderName;

    /**
     * 家长姓名
     */
    @ExcelColumn(name = "家长姓名", required = true)
    private String parentName;

    /**
     * 风险等级
     */
    @ExcelColumn(name = "手机号码", required = true)
    private String mobile;

    /**
     * 咨询日期
     */
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    @ExcelColumn(name = "咨询日期", dateFormat = "yyyy/MM/dd")
    private Date consultDate;

    /**
     * 咨询时间
     */
    @ExcelColumn(name = "咨询时间")
    private String consultTime;

    /**
     * 学员年龄
     */
    @ExcelColumn(name = "学员年龄")
    private BigDecimal studentAge;

    /**
     * 与学员关系
     */
    private String relationshipTypeCd;
    @ExcelColumn(name = "与学员关系")
    private String relationshipTypeName;

    /**
     * 围棋基础
     */
    private String baseLevelCd;
    @ExcelColumn(name = "围棋基础")
    private String baseLevelName;

    /**
     * 咨询方式
     */
    private String consultMethodCd;
    @ExcelColumn(name = "咨询方式")
    private String consultMethodName;

    /**
     * 信息来源
     */
    private String sourceTypeCd;
    @ExcelColumn(name = "信息来源")
    private String sourceTypeName;

    /**
     * 所属校区
     */
    private String studentBelongCampusUid;
    @ExcelColumn(name = "所属校区")
    private String studentBelongCampusName;

    /**
     * 学员状态
     */
    private String studentStatusCd;
    @ExcelColumn(name = "学员状态")
    private String studentStatusName;

    /**
     * 课程顾问
     */
    private String studentBelongConsultantUserUid;
    @ExcelColumn(name = "课程顾问")
    private String studentBelongConsultantUserName;

    /**
     * 备注
     */
    @ExcelColumn(name = "备注")
    private String remark;

    public StudentExt getStudent() {
        return student;
    }

    public void setStudent(StudentExt student) {
        this.student = student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenderCd() {
        return genderCd;
    }

    public void setGenderCd(String genderCd) {
        this.genderCd = genderCd;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getConsultDate() {
        return consultDate;
    }

    public void setConsultDate(Date consultDate) {
        this.consultDate = consultDate;
    }

    public String getConsultTime() {
        return consultTime;
    }

    public void setConsultTime(String consultTime) {
        this.consultTime = consultTime;
    }

    public BigDecimal getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(BigDecimal studentAge) {
        this.studentAge = studentAge;
    }

    public String getRelationshipTypeCd() {
        return relationshipTypeCd;
    }

    public void setRelationshipTypeCd(String relationshipTypeCd) {
        this.relationshipTypeCd = relationshipTypeCd;
    }

    public String getRelationshipTypeName() {
        return relationshipTypeName;
    }

    public void setRelationshipTypeName(String relationshipTypeName) {
        this.relationshipTypeName = relationshipTypeName;
    }

    public String getBaseLevelCd() {
        return baseLevelCd;
    }

    public void setBaseLevelCd(String baseLevelCd) {
        this.baseLevelCd = baseLevelCd;
    }

    public String getBaseLevelName() {
        return baseLevelName;
    }

    public void setBaseLevelName(String baseLevelName) {
        this.baseLevelName = baseLevelName;
    }

    public String getConsultMethodCd() {
        return consultMethodCd;
    }

    public void setConsultMethodCd(String consultMethodCd) {
        this.consultMethodCd = consultMethodCd;
    }

    public String getConsultMethodName() {
        return consultMethodName;
    }

    public void setConsultMethodName(String consultMethodName) {
        this.consultMethodName = consultMethodName;
    }

    public String getSourceTypeCd() {
        return sourceTypeCd;
    }

    public void setSourceTypeCd(String sourceTypeCd) {
        this.sourceTypeCd = sourceTypeCd;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public String getStudentBelongCampusUid() {
        return studentBelongCampusUid;
    }

    public void setStudentBelongCampusUid(String studentBelongCampusUid) {
        this.studentBelongCampusUid = studentBelongCampusUid;
    }

    public String getStudentBelongCampusName() {
        return studentBelongCampusName;
    }

    public void setStudentBelongCampusName(String studentBelongCampusName) {
        this.studentBelongCampusName = studentBelongCampusName;
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

    public String getStudentBelongConsultantUserUid() {
        return studentBelongConsultantUserUid;
    }

    public void setStudentBelongConsultantUserUid(String studentBelongConsultantUserUid) {
        this.studentBelongConsultantUserUid = studentBelongConsultantUserUid;
    }

    public String getStudentBelongConsultantUserName() {
        return studentBelongConsultantUserName;
    }

    public void setStudentBelongConsultantUserName(String studentBelongConsultantUserName) {
        this.studentBelongConsultantUserName = studentBelongConsultantUserName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}