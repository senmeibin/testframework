package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 人员信息表实体类
 */
@MappedSuperclass
public class Employee extends DemoBaseEntity {
    private static final long serialVersionUID = -20170510135355318L;

    /**
     * 登录账号
     */
    @Length(max = 64, message = "请在登录账号中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入登录账号。")
    @Column(length = 64, nullable = false)
    private String userCd;

    /**
     * 姓名
     */
    @Length(max = 32, message = "请在姓名中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入姓名。")
    @Column(length = 32, nullable = false)
    private String userName;

    /**
     * 性别
     */
    @Length(max = 8, message = "请在性别中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String sexCd;

    /**
     * 性别
     */
    @Transient
    private String sexName;

    /**
     * 出生日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date birthday;

    /**
     * 身份证号
     */
    @Length(max = 32, message = "请在身份证号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String idCardNo;

    /**
     * 籍贯
     */
    @Length(max = 32, message = "请在籍贯中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String ancestralNativePlace;

    /**
     * 民族
     */
    @Length(max = 8, message = "请在民族中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String ethnicityCd;

    /**
     * 民族
     */
    @Transient
    private String ethnicityName;

    /**
     * 工号
     */
    @Length(max = 32, message = "请在工号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String userNumber;

    /**
     * 户口所在地
     */
    @Length(max = 128, message = "请在户口所在地中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String registeredResidence;

    /**
     * 政治面貌
     */
    @Length(max = 8, message = "请在政治面貌中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String policyCd;

    /**
     * 政治面貌
     */
    @Transient
    private String policyName;

    /**
     * 学历
     */
    @Length(max = 32, message = "请在学历中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String educationCd;

    /**
     * 学历
     */
    @Transient
    private String educationName;

    /**
     * 学位
     */
    @Length(max = 32, message = "请在学位中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String educationalDegreeCd;

    /**
     * 学位
     */
    @Transient
    private String educationalDegreeName;

    /**
     * 身高
     */
    @Range(min = 0, max = 99999999, message = "请在身高中输入[0-99999999]范围内的数值。")
    @Column
    private Integer stature;

    /**
     * 体重
     */
    @Range(min = 0, max = 99999999, message = "请在体重中输入[0-99999999]范围内的数值。")
    @Column
    private Integer weight;

    /**
     * 血型
     */
    @Length(max = 8, message = "请在血型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String bloodTypeCd;

    /**
     * 血型
     */
    @Transient
    private String bloodTypeName;

    /**
     * 婚姻状况
     */
    @Length(max = 32, message = "请在婚姻状况中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String maritalStatusCd;

    /**
     * 婚姻状况
     */
    @Transient
    private String maritalStatusName;

    /**
     * 国籍
     */
    @Length(max = 32, message = "请在国籍中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String nationality;

    /**
     * 电子邮件
     */
    @Length(max = 32, message = "请在电子邮件中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String email;

    /**
     * 固定电话
     */
    @Length(max = 32, message = "请在固定电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String telephone;

    /**
     * 手机
     */
    @Length(max = 32, message = "请在手机中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String mobile;

    /**
     * 传值
     */
    @Length(max = 32, message = "请在传值中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String fax;

    /**
     * 家庭住址
     */
    @Length(max = 128, message = "请在家庭住址中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String homeAddress;

    /**
     * 毕业院校
     */
    @Length(max = 128, message = "请在毕业院校中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String graduateInstitutions;

    /**
     * 所学专业
     */
    @Length(max = 128, message = "请在所学专业中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String major;

    /**
     * 户口类型
     */
    @Length(max = 32, message = "请在户口类型中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String residenceType;

    /**
     * 出生地
     */
    @Length(max = 32, message = "请在出生地中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String birthPlace;

    /**
     * 所在省
     */
    @Column(length = 32)
    private String contactProvinceUid;

    /**
     * 所在省
     */
    @Transient
    private String contactProvinceName;

    /**
     * 所在市
     */
    @Column(length = 32)
    private String contactCityUid;

    /**
     * 所在市
     */
    @Transient
    private String contactCityName;

    /**
     * 所在区县
     */
    @Column(length = 32)
    private String contactCountyUid;

    /**
     * 所在区县
     */
    @Transient
    private String contactCountyName;

    /**
     * 联系地址
     */
    @Length(max = 128, message = "请在联系地址中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String contactAddress;

    /**
     * 健康状况
     */
    @Length(max = 32, message = "请在健康状况中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String healthInfo;

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
     * 所属部门
     */
    @Column(length = 32)
    private String deptUid;

    /**
     * 所属部门
     */
    @Transient
    private String deptName;

    /**
     * 岗位
     */
    @Column(length = 32)
    private String positionUid;

    /**
     * 岗位
     */
    @Transient
    private String positionName;

    /**
     * 职等
     */
    @Length(max = 32, message = "请在职等中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String positionsGrade;

    /**
     * 参加工作日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date firstJobDate;

    /**
     * 入职日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date entryDate;

    /**
     * 试用期结束日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date probationEndDate;

    /**
     * 转正日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date positiveDate;

    /**
     * 离职日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date retireDate;

    /**
     * 用工方式
     */
    @Length(max = 32, message = "请在用工方式中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String employmentType;

    /**
     * 合同开始日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date contractStartDate;

    /**
     * 合同结束日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date contractEndDate;

    /**
     * 公积金帐号
     */
    @Length(max = 32, message = "请在公积金帐号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String accumulationFundAccount;

    /**
     * 开户银行
     */
    @Length(max = 32, message = "请在开户银行中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String bankName;

    /**
     * 工资卡号
     */
    @Length(max = 32, message = "请在工资卡号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String bankAccountNumber;

    /**
     * 办公地点
     */
    @Column(length = 32)
    private String locationUid;

    /**
     * 办公地点
     */
    @Transient
    private String locationName;

    /**
     * 人员状态
     */
    @Length(max = 8, message = "请在人员状态中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入人员状态。")
    @Column(length = 8, nullable = false)
    private String statusCd;

    /**
     * 人员状态
     */
    @Transient
    private String statusName;

    /**
     * 星座
     */
    @Length(max = 32, message = "请在星座中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String constellation;

    public Employee() {
    }

    public String getUserCd() {
        return this.userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSexCd() {
        return this.sexCd;
    }

    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }

    public String getSexName() {
        return this.sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdCardNo() {
        return this.idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getAncestralNativePlace() {
        return this.ancestralNativePlace;
    }

    public void setAncestralNativePlace(String ancestralNativePlace) {
        this.ancestralNativePlace = ancestralNativePlace;
    }

    public String getEthnicityCd() {
        return this.ethnicityCd;
    }

    public void setEthnicityCd(String ethnicityCd) {
        this.ethnicityCd = ethnicityCd;
    }

    public String getEthnicityName() {
        return this.ethnicityName;
    }

    public void setEthnicityName(String ethnicityName) {
        this.ethnicityName = ethnicityName;
    }

    public String getUserNumber() {
        return this.userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getRegisteredResidence() {
        return this.registeredResidence;
    }

    public void setRegisteredResidence(String registeredResidence) {
        this.registeredResidence = registeredResidence;
    }

    public String getPolicyCd() {
        return this.policyCd;
    }

    public void setPolicyCd(String policyCd) {
        this.policyCd = policyCd;
    }

    public String getPolicyName() {
        return this.policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
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

    public String getEducationalDegreeCd() {
        return this.educationalDegreeCd;
    }

    public void setEducationalDegreeCd(String educationalDegreeCd) {
        this.educationalDegreeCd = educationalDegreeCd;
    }

    public String getEducationalDegreeName() {
        return this.educationalDegreeName;
    }

    public void setEducationalDegreeName(String educationalDegreeName) {
        this.educationalDegreeName = educationalDegreeName;
    }

    public Integer getStature() {
        return this.stature;
    }

    public void setStature(Integer stature) {
        this.stature = stature;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getBloodTypeCd() {
        return this.bloodTypeCd;
    }

    public void setBloodTypeCd(String bloodTypeCd) {
        this.bloodTypeCd = bloodTypeCd;
    }

    public String getBloodTypeName() {
        return this.bloodTypeName;
    }

    public void setBloodTypeName(String bloodTypeName) {
        this.bloodTypeName = bloodTypeName;
    }

    public String getMaritalStatusCd() {
        return this.maritalStatusCd;
    }

    public void setMaritalStatusCd(String maritalStatusCd) {
        this.maritalStatusCd = maritalStatusCd;
    }

    public String getMaritalStatusName() {
        return this.maritalStatusName;
    }

    public void setMaritalStatusName(String maritalStatusName) {
        this.maritalStatusName = maritalStatusName;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getGraduateInstitutions() {
        return this.graduateInstitutions;
    }

    public void setGraduateInstitutions(String graduateInstitutions) {
        this.graduateInstitutions = graduateInstitutions;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    public String getBirthPlace() {
        return this.birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getContactProvinceUid() {
        return this.contactProvinceUid;
    }

    public void setContactProvinceUid(String contactProvinceUid) {
        this.contactProvinceUid = contactProvinceUid;
    }

    public String getContactProvinceName() {
        return this.contactProvinceName;
    }

    public void setContactProvinceName(String contactProvinceName) {
        this.contactProvinceName = contactProvinceName;
    }

    public String getContactCityUid() {
        return this.contactCityUid;
    }

    public void setContactCityUid(String contactCityUid) {
        this.contactCityUid = contactCityUid;
    }

    public String getContactCityName() {
        return this.contactCityName;
    }

    public void setContactCityName(String contactCityName) {
        this.contactCityName = contactCityName;
    }

    public String getContactCountyUid() {
        return this.contactCountyUid;
    }

    public void setContactCountyUid(String contactCountyUid) {
        this.contactCountyUid = contactCountyUid;
    }

    public String getContactCountyName() {
        return this.contactCountyName;
    }

    public void setContactCountyName(String contactCountyName) {
        this.contactCountyName = contactCountyName;
    }

    public String getContactAddress() {
        return this.contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getHealthInfo() {
        return this.healthInfo;
    }

    public void setHealthInfo(String healthInfo) {
        this.healthInfo = healthInfo;
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

    public String getDeptUid() {
        return this.deptUid;
    }

    public void setDeptUid(String deptUid) {
        this.deptUid = deptUid;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionUid() {
        return this.positionUid;
    }

    public void setPositionUid(String positionUid) {
        this.positionUid = positionUid;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionsGrade() {
        return this.positionsGrade;
    }

    public void setPositionsGrade(String positionsGrade) {
        this.positionsGrade = positionsGrade;
    }

    public Date getFirstJobDate() {
        return this.firstJobDate;
    }

    public void setFirstJobDate(Date firstJobDate) {
        this.firstJobDate = firstJobDate;
    }

    public Date getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getProbationEndDate() {
        return this.probationEndDate;
    }

    public void setProbationEndDate(Date probationEndDate) {
        this.probationEndDate = probationEndDate;
    }

    public Date getPositiveDate() {
        return this.positiveDate;
    }

    public void setPositiveDate(Date positiveDate) {
        this.positiveDate = positiveDate;
    }

    public Date getRetireDate() {
        return this.retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

    public String getEmploymentType() {
        return this.employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public Date getContractStartDate() {
        return this.contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate() {
        return this.contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getAccumulationFundAccount() {
        return this.accumulationFundAccount;
    }

    public void setAccumulationFundAccount(String accumulationFundAccount) {
        this.accumulationFundAccount = accumulationFundAccount;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return this.bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getLocationUid() {
        return this.locationUid;
    }

    public void setLocationUid(String locationUid) {
        this.locationUid = locationUid;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStatusCd() {
        return this.statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }
}