package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 核心团队人员实体类
 */
@MappedSuperclass
public class CompanyTeam extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511162205947L;

    /**
     * 企业UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择企业。")
    private String companyUid;

    /**
     * 企业
     */
    @Transient
    private String companyName;

    /**
     * 姓名
     */
    @Length(max = 32, message = "请在姓名中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String name;

    /**
     * 出生日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date birthDate;

    /**
     * 部门
     */
    @Length(max = 32, message = "请在部门中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String dept;

    /**
     * 职务
     */
    @Length(max = 32, message = "请在职务中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String position;

    /**
     * 电话
     */
    @Length(max = 32, message = "请在电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String phone;

    /**
     * 传真
     */
    @Length(max = 32, message = "请在传真中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String fax;

    /**
     * 手机
     */
    @Length(max = 32, message = "请在手机中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String mobile;

    /**
     * 证件类型
     */
    @Length(max = 8, message = "请在证件类型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String certificateTypeCd;

    /**
     * 证件类型
     */
    @Transient
    private String certificateTypeName;

    /**
     * 证件号码
     */
    @Length(max = 32, message = "请在证件号码中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String certificateNo;

    /**
     * 电子邮件
     */
    @Length(max = 32, message = "请在电子邮件中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String mail;

    /**
     * 学历
     */
    @Length(max = 8, message = "请在学历中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String educationCd;

    /**
     * 学历
     */
    @Transient
    private String educationName;

    /**
     * 目前最高职称
     */
    @Length(max = 32, message = "请在目前最高职称中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String highestJobTitle;

    /**
     * 初次工作年份
     */
    @Length(max = 4, message = "请在初次工作年份中输入[0-4]位以内的文字。")
    @Column(length = 4)
    private String firstWorkingYear;

    /**
     * 是否本公司股东
     */
    @Length(max = 8, message = "请在是否本公司股东中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String shareholdersCd;

    /**
     * 是否本公司股东
     */
    @Transient
    private String shareholdersName;

    /**
     * 是否实际负责人
     */
    @Length(max = 8, message = "请在是否实际负责人中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String actualPersonCd;

    /**
     * 是否实际负责人
     */
    @Transient
    private String actualPersonName;

    /**
     * 是否连续创业
     */
    @Length(max = 8, message = "请在是否连续创业中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String continuousBusinessCd;

    /**
     * 是否连续创业
     */
    @Transient
    private String continuousBusinessName;

    /**
     * 创业者特征
     */
    @Length(max = 64, message = "请在创业者特征中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String entrepreneurFeature;

    /**
     * 是否千人计划
     */
    @Length(max = 8, message = "请在是否千人计划中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String thousandsPlansCd;

    /**
     * 是否千人计划
     */
    @Transient
    private String thousandsPlansName;

    /**
     * 千人计划批次号
     */
    @Length(max = 32, message = "请在千人计划批次号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String thousandsPlansNo;

    /**
     * 是否大学生科技企业
     */
    @Length(max = 8, message = "请在是否大学生科技企业中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String collegeTechnologyEnterprisesCd;

    /**
     * 是否大学生科技企业
     */
    @Transient
    private String collegeTechnologyEnterprisesName;

    public CompanyTeam() {
    }

    public String getCompanyUid() {
        return this.companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDept() {
        return this.dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCertificateTypeCd() {
        return this.certificateTypeCd;
    }

    public void setCertificateTypeCd(String certificateTypeCd) {
        this.certificateTypeCd = certificateTypeCd;
    }

    public String getCertificateTypeName() {
        return this.certificateTypeName;
    }

    public void setCertificateTypeName(String certificateTypeName) {
        this.certificateTypeName = certificateTypeName;
    }

    public String getCertificateNo() {
        return this.certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEducationCd() {
        return this.educationCd;
    }

    public void setEducationCd(String educationCd) {
        this.educationCd = educationCd;
    }

    public String getEducationName() {
        return this.educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getHighestJobTitle() {
        return this.highestJobTitle;
    }

    public void setHighestJobTitle(String highestJobTitle) {
        this.highestJobTitle = highestJobTitle;
    }

    public String getFirstWorkingYear() {
        return this.firstWorkingYear;
    }

    public void setFirstWorkingYear(String firstWorkingYear) {
        this.firstWorkingYear = firstWorkingYear;
    }

    public String getShareholdersCd() {
        return this.shareholdersCd;
    }

    public void setShareholdersCd(String shareholdersCd) {
        this.shareholdersCd = shareholdersCd;
    }

    public String getShareholdersName() {
        return this.shareholdersName;
    }

    public void setShareholdersName(String shareholdersName) {
        this.shareholdersName = shareholdersName;
    }

    public String getActualPersonCd() {
        return this.actualPersonCd;
    }

    public void setActualPersonCd(String actualPersonCd) {
        this.actualPersonCd = actualPersonCd;
    }

    public String getActualPersonName() {
        return this.actualPersonName;
    }

    public void setActualPersonName(String actualPersonName) {
        this.actualPersonName = actualPersonName;
    }

    public String getContinuousBusinessCd() {
        return this.continuousBusinessCd;
    }

    public void setContinuousBusinessCd(String continuousBusinessCd) {
        this.continuousBusinessCd = continuousBusinessCd;
    }

    public String getContinuousBusinessName() {
        return this.continuousBusinessName;
    }

    public void setContinuousBusinessName(String continuousBusinessName) {
        this.continuousBusinessName = continuousBusinessName;
    }

    public String getEntrepreneurFeature() {
        return this.entrepreneurFeature;
    }

    public void setEntrepreneurFeature(String entrepreneurFeature) {
        this.entrepreneurFeature = entrepreneurFeature;
    }

    public String getThousandsPlansCd() {
        return this.thousandsPlansCd;
    }

    public void setThousandsPlansCd(String thousandsPlansCd) {
        this.thousandsPlansCd = thousandsPlansCd;
    }

    public String getThousandsPlansName() {
        return this.thousandsPlansName;
    }

    public void setThousandsPlansName(String thousandsPlansName) {
        this.thousandsPlansName = thousandsPlansName;
    }

    public String getThousandsPlansNo() {
        return this.thousandsPlansNo;
    }

    public void setThousandsPlansNo(String thousandsPlansNo) {
        this.thousandsPlansNo = thousandsPlansNo;
    }

    public String getCollegeTechnologyEnterprisesCd() {
        return this.collegeTechnologyEnterprisesCd;
    }

    public void setCollegeTechnologyEnterprisesCd(String collegeTechnologyEnterprisesCd) {
        this.collegeTechnologyEnterprisesCd = collegeTechnologyEnterprisesCd;
    }

    public String getCollegeTechnologyEnterprisesName() {
        return this.collegeTechnologyEnterprisesName;
    }

    public void setCollegeTechnologyEnterprisesName(String collegeTechnologyEnterprisesName) {
        this.collegeTechnologyEnterprisesName = collegeTechnologyEnterprisesName;
    }

}