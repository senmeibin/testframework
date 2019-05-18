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
 * 招商潜在投资企业实体类
 */
@MappedSuperclass
public class PotentialCustomer extends DemoBaseEntity {
    private static final long serialVersionUID = -20170420132743993L;

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
     * 注册地区（县）
     */
    @Column(length = 32)
    private String registeredAreaUid;

    /**
     * 注册地区（县）
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
     * 注册资金
     */
    @Range(min = 0, max = 99999999, message = "请在注册资金中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal registeredCapital;

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
     * 企业简介
     */
    @Length(max = 512, message = "请在企业简介中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String companyProfile;

    /**
     * 联络内容
     */
    @Length(max = 512, message = "请在联络内容中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String contactContent;

    /**
     * 负责人
     */
    @Length(max = 32, message = "请在负责人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String principal;

    /**
     * 后续对接方案
     */
    @Length(max = 512, message = "请在后续对接方案中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String followUpScheme;

    /**
     * 企业类型
     */
    @Length(max = 8, message = "请在企业类型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String companyTypeCd;

    /**
     * 企业类型
     */
    @Transient
    private String companyTypeName;

    /**
     * 入驻概率
     */
    @Length(max = 8, message = "请在入驻概率中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String entryProbabilityCd;

    /**
     * 入驻概率
     */
    @Transient
    private String entryProbabilityName;

    /**
     * 招商渠道
     */
    @Length(max = 8, message = "请在招商渠道中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String investmentChannelsCd;

    /**
     * 招商渠道
     */
    @Transient
    private String investmentChannelsName;

    public PotentialCustomer() {
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

    public BigDecimal getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getBaseUid() {
        return baseUid;
    }

    public void setBaseUid(String baseUid) {
        this.baseUid = baseUid;
    }

    public String getBaseName() {
        return baseName;
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

    public String getCompanyProfile() {
        return this.companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getContactContent() {
        return this.contactContent;
    }

    public void setContactContent(String contactContent) {
        this.contactContent = contactContent;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getFollowUpScheme() {
        return this.followUpScheme;
    }

    public void setFollowUpScheme(String followUpScheme) {
        this.followUpScheme = followUpScheme;
    }

    public String getCompanyTypeCd() {
        return this.companyTypeCd;
    }

    public void setCompanyTypeCd(String companyTypeCd) {
        this.companyTypeCd = companyTypeCd;
    }

    public String getCompanyTypeName() {
        return this.companyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        this.companyTypeName = companyTypeName;
    }

    public String getEntryProbabilityCd() {
        return this.entryProbabilityCd;
    }

    public void setEntryProbabilityCd(String entryProbabilityCd) {
        this.entryProbabilityCd = entryProbabilityCd;
    }

    public String getEntryProbabilityName() {
        return this.entryProbabilityName;
    }

    public void setEntryProbabilityName(String entryProbabilityName) {
        this.entryProbabilityName = entryProbabilityName;
    }

    public String getInvestmentChannelsCd() {
        return investmentChannelsCd;
    }

    public void setInvestmentChannelsCd(String investmentChannelsCd) {
        this.investmentChannelsCd = investmentChannelsCd;
    }

    public String getInvestmentChannelsName() {
        return investmentChannelsName;
    }

    public void setInvestmentChannelsName(String investmentChannelsName) {
        this.investmentChannelsName = investmentChannelsName;
    }
}