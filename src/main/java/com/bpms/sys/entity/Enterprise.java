package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.CoreEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 企业信息实体类
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Enterprise extends CoreEntity {
    private static final long serialVersionUID = -20171018161240082L;
    /**
     * 添加者
     */
    @CreatedBy
    @Column(length = 32, nullable = false, updatable = false)
    protected String insertUser;
    /**
     * 添加者
     */
    @Transient
    protected String insertUserName;
    /**
     * 添加日期
     */
    @CreatedDate
    @Column(nullable = false)
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    protected Date insertDate = Calendar.getInstance().getTime();
    /**
     * 更新者
     */
    @LastModifiedBy
    @Column(length = 32, nullable = false)
    protected String updateUser;
    /**
     * 更新者
     */
    @Transient
    protected String updateUserName;
    /**
     * 更新日期
     */
    @LastModifiedDate
    @Column(nullable = false)
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    protected Date updateDate = Calendar.getInstance().getTime();
    /**
     * 备注
     */
    @Length(max = 256, message = "请在备注中输入0-256位以内的文字。")
    @Column(length = 256)
    protected String remark;
    /**
     * 记录状态
     */
    @Column(nullable = false)
    protected Integer recordStatus = 1;
    /**
     * uid
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Integer uid;
    /**
     * 企业名称
     */
    @Length(max = 256, message = "请在企业名称中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入企业名称。")
    @Column(name = "enterprise_name", length = 256, nullable = false)
    private String enterpriseName;

    /**
     * 企业简称
     */
    @Length(max = 256, message = "请在企业简称中输入[0-256]位以内的文字。")
    @Column(name = "abbreviation", length = 256)
    private String abbreviation;

    /**
     * 企业LOGO
     */
    @Length(max = 256, message = "请在企业LOGO中输入[0-256]位以内的文字。")
    @Column(name = "logo", length = 256)
    private String logo;

    /**
     * 企业标签
     */
    @Length(max = 256, message = "请在企业标签中输入[0-256]位以内的文字。")
    @Column(name = "enterprise_label", length = 256)
    private String enterpriseLabel;

    /**
     * 法人代表
     */
    @Length(max = 32, message = "请在法人代表中输入[0-32]位以内的文字。")
    @Column(name = "legal_person_name", length = 32)
    private String legalPersonName;

    /**
     * 所属行业
     */
    @Length(max = 8, message = "请在所属行业中输入[0-8]位以内的文字。")
    @Column(name = "industry_cd", length = 8)
    private String industryCd;

    /**
     * 所属行业
     */
    @Transient
    private String industryName;

    /**
     * 企业性质
     */
    @Length(max = 8, message = "请在企业性质中输入[0-8]位以内的文字。")
    @Column(name = "property_cd", length = 8)
    private String propertyCd;

    /**
     * 企业性质
     */
    @Transient
    private String propertyName;

    /**
     * 企业规模
     */
    @Length(max = 8, message = "请在企业规模中输入[0-8]位以内的文字。")
    @Column(name = "scale_cd", length = 8)
    private String scaleCd;

    /**
     * 企业规模
     */
    @Transient
    private String scaleName;

    /**
     * 省
     */
    @Column(name = "province_uid", length = 32)
    private String provinceUid;

    /**
     * 省
     */
    @Transient
    private String provinceName;

    /**
     * 市
     */
    @Column(name = "city_uid", length = 32)
    private String cityUid;

    /**
     * 市
     */
    @Transient
    private String cityName;

    /**
     * 区
     */
    @Column(name = "region_uid", length = 32)
    private String regionUid;

    /**
     * 区
     */
    @Transient
    private String regionName;

    /**
     * 企业地址
     */
    @Length(max = 256, message = "请在企业地址中输入[0-256]位以内的文字。")
    @Column(name = "address", length = 256)
    private String address;

    /**
     * 邮编
     */
    @Length(max = 32, message = "请在邮编中输入[0-32]位以内的文字。")
    @Column(name = "zipcode", length = 32)
    private String zipcode;

    /**
     * 电话
     */
    @Length(max = 32, message = "请在电话中输入[0-32]位以内的文字。")
    @Column(name = "telephone", length = 32)
    private String telephone;

    /**
     * 传真
     */
    @Length(max = 32, message = "请在传真中输入[0-32]位以内的文字。")
    @Column(name = "fax", length = 32)
    private String fax;

    /**
     * 邮箱
     */
    @Length(max = 64, message = "请在邮箱中输入[0-64]位以内的文字。")
    @Column(name = "email", length = 64)
    private String email;

    /**
     * 网址
     */
    @Length(max = 256, message = "请在网址中输入[0-256]位以内的文字。")
    @Column(name = "url", length = 256)
    private String url;

    /**
     * 注册资金
     */
    @Column(name = "registered_capital")
    private Float registeredCapital;

    /**
     * 开票抬头
     */
    @Length(max = 256, message = "请在开票抬头中输入[0-256]位以内的文字。")
    @Column(name = "invoice_title", length = 256)
    private String invoiceTitle;

    /**
     * 开票注册地址
     */
    @Length(max = 256, message = "请在开票注册地址中输入[0-256]位以内的文字。")
    @Column(name = "invoice_registered_address", length = 256)
    private String invoiceRegisteredAddress;

    /**
     * 开票电话
     */
    @Length(max = 32, message = "请在开票电话中输入[0-32]位以内的文字。")
    @Column(name = "invoice_telephone", length = 32)
    private String invoiceTelephone;

    /**
     * 开票开户行
     */
    @Length(max = 64, message = "请在开票开户行中输入[0-64]位以内的文字。")
    @Column(name = "invoice_bank", length = 64)
    private String invoiceBank;

    /**
     * 开票帐号
     */
    @Length(max = 32, message = "请在开票帐号中输入[0-32]位以内的文字。")
    @Column(name = "invoice_account_no", length = 32)
    private String invoiceAccountNo;

    /**
     * 开票税号
     */
    @Length(max = 32, message = "请在开票税号中输入[0-32]位以内的文字。")
    @Column(name = "invoice_tax_no", length = 32)
    private String invoiceTaxNo;

    /**
     * 联系人
     */
    @Length(max = 32, message = "请在联系人中输入[0-32]位以内的文字。")
    @Column(name = "contact_name", length = 32)
    private String contactName;

    /**
     * 联系人电话
     */
    @Length(max = 32, message = "请在联系人电话中输入[0-32]位以内的文字。")
    @Column(name = "contact_telephone", length = 32)
    private String contactTelephone;

    /**
     * 邮箱
     */
    @Length(max = 32, message = "请在邮箱中输入[0-32]位以内的文字。")
    @Column(name = "contact_email", length = 32)
    private String contactEmail;

    /**
     * 业务范围
     */
    @Length(max = 256, message = "请在业务范围中输入[0-256]位以内的文字。")
    @Column(name = "business_scope", length = 256)
    private String businessScope;

    /**
     * 企业简介
     */
    @Length(max = 256, message = "请在企业简介中输入[0-256]位以内的文字。")
    @Column(name = "introduction", length = 256)
    private String introduction;

    public Enterprise() {
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    public String getInsertUserName() {
        return insertUserName;
    }

    public void setInsertUserName(String insertUserName) {
        this.insertUserName = insertUserName;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getEnterpriseName() {
        return this.enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEnterpriseLabel() {
        return this.enterpriseLabel;
    }

    public void setEnterpriseLabel(String enterpriseLabel) {
        this.enterpriseLabel = enterpriseLabel;
    }

    public String getLegalPersonName() {
        return this.legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getIndustryCd() {
        return this.industryCd;
    }

    public void setIndustryCd(String industryCd) {
        this.industryCd = industryCd;
    }

    public String getPropertyCd() {
        return this.propertyCd;
    }

    public void setPropertyCd(String propertyCd) {
        this.propertyCd = propertyCd;
    }

    public String getScaleCd() {
        return this.scaleCd;
    }

    public void setScaleCd(String scaleCd) {
        this.scaleCd = scaleCd;
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
        return regionUid;
    }

    public void setRegionUid(String regionUid) {
        this.regionUid = regionUid;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital(Float registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getInvoiceTitle() {
        return this.invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceRegisteredAddress() {
        return this.invoiceRegisteredAddress;
    }

    public void setInvoiceRegisteredAddress(String invoiceRegisteredAddress) {
        this.invoiceRegisteredAddress = invoiceRegisteredAddress;
    }

    public String getInvoiceTelephone() {
        return this.invoiceTelephone;
    }

    public void setInvoiceTelephone(String invoiceTelephone) {
        this.invoiceTelephone = invoiceTelephone;
    }

    public String getInvoiceBank() {
        return this.invoiceBank;
    }

    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank;
    }

    public String getInvoiceAccountNo() {
        return this.invoiceAccountNo;
    }

    public void setInvoiceAccountNo(String invoiceAccountNo) {
        this.invoiceAccountNo = invoiceAccountNo;
    }

    public String getInvoiceTaxNo() {
        return this.invoiceTaxNo;
    }

    public void setInvoiceTaxNo(String invoiceTaxNo) {
        this.invoiceTaxNo = invoiceTaxNo;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelephone() {
        return this.contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getBusinessScope() {
        return this.businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getScaleName() {
        return scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }
}