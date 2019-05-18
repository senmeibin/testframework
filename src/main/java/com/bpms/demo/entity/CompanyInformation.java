package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业基本信息实体类
 */
@MappedSuperclass
public class CompanyInformation extends DemoBaseEntity {
    private static final long serialVersionUID = -20170418095508007L;

    /**
     * 入孵编号
     */
    @Length(max = 32, message = "请在入孵编号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String companyNo;

    /**
     * 企业名称
     */
    @Length(max = 64, message = "请在企业名称中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String companyName;

    /**
     * 组织机构代码
     */
    @Length(max = 32, message = "请在组织机构代码中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String organizationNo;

    /**
     * 注册地区（省）
     */
    @Column(length = 32)
    private String registeredProvinceUid;

    /**
     * 注册地区（省）
     */
    @Transient
    private String registeredProvinceName;

    /**
     * 注册地区（市）
     */
    @Column(length = 32)
    private String registeredCityUid;

    /**
     * 注册地区（市）
     */
    @Transient
    private String registeredCityName;

    /**
     * 注册地区（区/县）
     */
    @Column(length = 32)
    private String registeredAreaUid;

    /**
     * 注册地区（区/县）
     */
    @Transient
    private String registeredAreaName;

    /**
     * 注册地址
     */
    @Length(max = 64, message = "请在注册地址中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String registeredAddress;

    /**
     * 成立时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date establishmentDate;

    /**
     * 入驻时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date settlingDate;

    /**
     * 注册资金
     */
    @Range(min = 0, max = 99999999, message = "请在注册资金中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal registeredCapital = new BigDecimal(0);

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
     * 实际负责人
     */
    @Length(max = 32, message = "请在实际负责人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String companyPrincipal;

    /**
     * 负责人电话
     */
    @Length(max = 32, message = "请在负责人电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String principalPhone;

    /**
     * 负责人邮箱
     */
    @Length(max = 32, message = "请在负责人邮箱中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String principalMail;

    /**
     * 联系人
     */
    @Length(max = 32, message = "请在联系人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactPerson;

    /**
     * 联系人电话
     */
    @Length(max = 32, message = "请在联系人电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    @Length(max = 32, message = "请在联系人邮箱中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactMail;

    /**
     * 租赁面积
     */
    @Range(min = 0, max = 99999999, message = "请在租赁面积中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal leaseArea = new BigDecimal(0);

    /**
     * 办公地址
     */
    @Length(max = 32, message = "请在办公地址中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String officeAddress;

    /**
     * 办公地邮政编码
     */
    @Length(max = 8, message = "请在办公地邮政编码中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String officePostalCode;

    /**
     * 登记注册类型
     */
    @Length(max = 8, message = "请在登记注册类型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String registrationTypeCd;

    /**
     * 登记注册类型
     */
    @Transient
    private String registrationTypeName;

    /**
     * 高新技术分类
     */
    @Length(max = 64, message = "请在高新技术分类中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String highTechType;

    /**
     * 行业分类
     */
    @Length(max = 64, message = "请在行业分类中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String industryCategory;

    /**
     * 专业技术方向
     */
    @Length(max = 8, message = "请在专业技术方向中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String professionalTechnicalCd;

    /**
     * 专业技术方向
     */
    @Transient
    private String professionalTechnicalName;

    /**
     * 网站/APP
     */
    @Length(max = 128, message = "请在网站/APP中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String website;

    /**
     * 孵化状态
     */
    @Length(max = 8, message = "请在孵化状态中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String incubationStateCd;

    /**
     * 孵化状态
     */
    @Transient
    private String incubationStateName;

    /**
     * 毕业企业编号
     */
    @Length(max = 32, message = "请在毕业企业编号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String graduationNo;

    /**
     * 主营产品
     */
    @Length(max = 256, message = "请在主营产品中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String mainProduct;

    /**
     * 工位数
     */
    @Range(min = 0, max = 99999999, message = "请在工位数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer stationCounts;

    /**
     * 工位位置
     */
    @Length(max = 64, message = "请在工位位置中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String stationPosition;

    public CompanyInformation() {
    }

    public String getCompanyNo() {
        return this.companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrganizationNo() {
        return this.organizationNo;
    }

    public void setOrganizationNo(String organizationNo) {
        this.organizationNo = organizationNo;
    }

    public String getRegisteredProvinceUid() {
        return this.registeredProvinceUid;
    }

    public void setRegisteredProvinceUid(String registeredProvinceUid) {
        this.registeredProvinceUid = registeredProvinceUid;
    }

    public String getRegisteredProvinceName() {
        return this.registeredProvinceName;
    }

    public void setRegisteredProvinceName(String registeredProvinceName) {
        this.registeredProvinceName = registeredProvinceName;
    }

    public String getRegisteredCityUid() {
        return this.registeredCityUid;
    }

    public void setRegisteredCityUid(String registeredCityUid) {
        this.registeredCityUid = registeredCityUid;
    }

    public String getRegisteredCityName() {
        return this.registeredCityName;
    }

    public void setRegisteredCityName(String registeredCityName) {
        this.registeredCityName = registeredCityName;
    }

    public String getRegisteredAreaUid() {
        return this.registeredAreaUid;
    }

    public void setRegisteredAreaUid(String registeredAreaUid) {
        this.registeredAreaUid = registeredAreaUid;
    }

    public String getRegisteredAreaName() {
        return this.registeredAreaName;
    }

    public void setRegisteredAreaName(String registeredAreaName) {
        this.registeredAreaName = registeredAreaName;
    }

    public String getRegisteredAddress() {
        return this.registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public Date getEstablishmentDate() {
        return this.establishmentDate;
    }

    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public Date getSettlingDate() {
        return this.settlingDate;
    }

    public void setSettlingDate(Date settlingDate) {
        this.settlingDate = settlingDate;
    }

    public BigDecimal getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
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

    public String getCompanyPrincipal() {
        return this.companyPrincipal;
    }

    public void setCompanyPrincipal(String companyPrincipal) {
        this.companyPrincipal = companyPrincipal;
    }

    public String getPrincipalPhone() {
        return this.principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone;
    }

    public String getPrincipalMail() {
        return this.principalMail;
    }

    public void setPrincipalMail(String principalMail) {
        this.principalMail = principalMail;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactMail() {
        return this.contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public BigDecimal getLeaseArea() {
        return this.leaseArea;
    }

    public void setLeaseArea(BigDecimal leaseArea) {
        this.leaseArea = leaseArea;
    }

    public String getOfficeAddress() {
        return this.officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getOfficePostalCode() {
        return this.officePostalCode;
    }

    public void setOfficePostalCode(String officePostalCode) {
        this.officePostalCode = officePostalCode;
    }

    public String getRegistrationTypeCd() {
        return this.registrationTypeCd;
    }

    public void setRegistrationTypeCd(String registrationTypeCd) {
        this.registrationTypeCd = registrationTypeCd;
    }

    public String getRegistrationTypeName() {
        return this.registrationTypeName;
    }

    public void setRegistrationTypeName(String registrationTypeName) {
        this.registrationTypeName = registrationTypeName;
    }

    public String getHighTechType() {
        return this.highTechType;
    }

    public void setHighTechType(String highTechType) {
        this.highTechType = highTechType;
    }

    public String getIndustryCategory() {
        return this.industryCategory;
    }

    public void setIndustryCategory(String industryCategory) {
        this.industryCategory = industryCategory;
    }

    public String getProfessionalTechnicalCd() {
        return this.professionalTechnicalCd;
    }

    public void setProfessionalTechnicalCd(String professionalTechnicalCd) {
        this.professionalTechnicalCd = professionalTechnicalCd;
    }

    public String getProfessionalTechnicalName() {
        return this.professionalTechnicalName;
    }

    public void setProfessionalTechnicalName(String professionalTechnicalName) {
        this.professionalTechnicalName = professionalTechnicalName;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIncubationStateCd() {
        return this.incubationStateCd;
    }

    public void setIncubationStateCd(String incubationStateCd) {
        this.incubationStateCd = incubationStateCd;
    }

    public String getIncubationStateName() {
        return this.incubationStateName;
    }

    public void setIncubationStateName(String incubationStateName) {
        this.incubationStateName = incubationStateName;
    }

    public String getGraduationNo() {
        return this.graduationNo;
    }

    public void setGraduationNo(String graduationNo) {
        this.graduationNo = graduationNo;
    }

    public String getMainProduct() {
        return this.mainProduct;
    }

    public void setMainProduct(String mainProduct) {
        this.mainProduct = mainProduct;
    }

    public Integer getStationCounts() {
        return stationCounts;
    }

    public void setStationCounts(Integer stationCounts) {
        this.stationCounts = stationCounts;
    }

    public String getStationPosition() {
        return stationPosition;
    }

    public void setStationPosition(String stationPosition) {
        this.stationPosition = stationPosition;
    }
}