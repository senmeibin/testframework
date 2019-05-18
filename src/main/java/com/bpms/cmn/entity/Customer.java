package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 合同客户实体类
 */
@MappedSuperclass
public class Customer extends CmnBaseEntity {
    private static final long serialVersionUID = -20160524114526661L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 主客户uid
     */
    @Column(length = 32)
    private String mainCustomerUid;

    /**
     * 主客户
     */
    @Transient
    private String mainCustomerName;

    /**
     * 单位编号
     */
    @Length(max = 32, message = "请在单位编号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String customerCode;

    /**
     * 客户名称
     */
    @Length(max = 128, message = "请在客户名称中输入[0-128]位以内的文字。")
    @NotEmpty(message = "请输入客户名称。")
    @Column(length = 128, nullable = false)
    private String customerName;

    /**
     * 简称
     */
    @Length(max = 32, message = "请在简称中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String customerAbbreviation;

    /**
     * 性质
     */
    @Length(max = 8, message = "请在性质中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String propertyCd;

    /**
     * 性质
     */
    @Transient
    private String propertyName;

    /**
     * 规模
     */
    @Length(max = 8, message = "请在规模中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String scaleCd;

    /**
     * 规模
     */
    @Transient
    private String scaleName;

    /**
     * 地址
     */
    @Length(max = 128, message = "请在地址中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String address;

    /**
     * 邮编
     */
    @Length(max = 6, message = "请在邮编中输入[0-6]位以内的文字。")
    @Column(length = 6)
    private String zipcode;

    /**
     * 电话
     */
    @Length(max = 32, message = "请在电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String telephone;

    /**
     * 传真
     */
    @Length(max = 32, message = "请在传真中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String fax;

    /**
     * 邮箱
     */
    @Length(max = 64, message = "请在邮箱中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String email;

    /**
     * 网址
     */
    @Length(max = 128, message = "请在网址中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String url;

    /**
     * 联系人
     */
    @Length(max = 32, message = "请在联系人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactName;

    /**
     * 法人
     */
    @Length(max = 32, message = "请在法人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String legalPersonName;

    /**
     * 注册资金
     */
    @Range(min = 0, max = 99999999, message = "请在注册资金中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal registeredCapital;

    /**
     * 开票抬头
     */
    @Length(max = 128, message = "请在开票抬头中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String invoiceTitle;

    /**
     * 开票注册地址
     */
    @Length(max = 128, message = "请在开票注册地址中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String invoiceRegisteredAddress;

    /**
     * 开票电话
     */
    @Length(max = 32, message = "请在开票电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String invoiceTelephone;

    /**
     * 开票开户行
     */
    @Length(max = 64, message = "请在开票开户行中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String invoiceBank;

    /**
     * 开票帐号
     */
    @Length(max = 32, message = "请在开票帐号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String invoiceAccountNo;

    /**
     * 开票税号
     */
    @Length(max = 32, message = "请在开票税号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String invoiceTaxNo;

    /**
     * 客户来源
     */
    @Length(max = 8, message = "请在客户来源中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String customerSourceCd;

    /**
     * 客户来源
     */
    @Transient
    private String customerSourceName;

    /**
     * 所属行业
     */
    @Length(max = 8, message = "请在所属行业中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String industryCd;

    /**
     * 所属行业
     */
    @Transient
    private String industryName;

    /**
     * 经营类型
     */
    @Length(max = 8, message = "请在经营类型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String businessTypeCd;

    /**
     * 经营类型
     */
    @Transient
    private String businessTypeName;

    /**
     * 客户等级
     */
    @Length(max = 8, message = "请在客户等级中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String customerLevelCd;

    /**
     * 客户等级
     */
    @Transient
    private String customerLevelName;

    /**
     * 客户状态
     */
    @Length(max = 8, message = "请在客户状态中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String customerStatusCd;

    /**
     * 客户状态
     */
    @Transient
    private String customerStatusName;

    /**
     * 业务范围
     */
    @Length(max = 512, message = "请在业务范围中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String businessScope;

    /**
     * 客户概况
     */
    @Length(max = 512, message = "请在客户概况中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String customerIntroduction;

    public Customer() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getMainCustomerUid() {
        return this.mainCustomerUid;
    }

    public void setMainCustomerUid(String mainCustomerUid) {
        this.mainCustomerUid = mainCustomerUid;
    }

    public String getMainCustomerName() {
        return this.mainCustomerName;
    }

    public void setMainCustomerName(String mainCustomerName) {
        this.mainCustomerName = mainCustomerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAbbreviation() {
        return customerAbbreviation;
    }

    public void setCustomerAbbreviation(String customerAbbreviation) {
        this.customerAbbreviation = customerAbbreviation;
    }

    public String getPropertyCd() {
        return this.propertyCd;
    }

    public void setPropertyCd(String propertyCd) {
        this.propertyCd = propertyCd;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getScaleCd() {
        return this.scaleCd;
    }

    public void setScaleCd(String scaleCd) {
        this.scaleCd = scaleCd;
    }

    public String getScaleName() {
        return this.scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
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

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getLegalPersonName() {
        return this.legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public BigDecimal getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
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

    public String getCustomerSourceCd() {
        return this.customerSourceCd;
    }

    public void setCustomerSourceCd(String customerSourceCd) {
        this.customerSourceCd = customerSourceCd;
    }

    public String getCustomerSourceName() {
        return this.customerSourceName;
    }

    public void setCustomerSourceName(String customerSourceName) {
        this.customerSourceName = customerSourceName;
    }

    public String getIndustryCd() {
        return this.industryCd;
    }

    public void setIndustryCd(String industryCd) {
        this.industryCd = industryCd;
    }

    public String getIndustryName() {
        return this.industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getBusinessTypeCd() {
        return this.businessTypeCd;
    }

    public void setBusinessTypeCd(String businessTypeCd) {
        this.businessTypeCd = businessTypeCd;
    }

    public String getBusinessTypeName() {
        return this.businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getCustomerLevelCd() {
        return this.customerLevelCd;
    }

    public void setCustomerLevelCd(String customerLevelCd) {
        this.customerLevelCd = customerLevelCd;
    }

    public String getCustomerLevelName() {
        return this.customerLevelName;
    }

    public void setCustomerLevelName(String customerLevelName) {
        this.customerLevelName = customerLevelName;
    }

    public String getCustomerStatusCd() {
        return this.customerStatusCd;
    }

    public void setCustomerStatusCd(String customerStatusCd) {
        this.customerStatusCd = customerStatusCd;
    }

    public String getCustomerStatusName() {
        return this.customerStatusName;
    }

    public void setCustomerStatusName(String customerStatusName) {
        this.customerStatusName = customerStatusName;
    }

    public String getBusinessScope() {
        return this.businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getCustomerIntroduction() {
        return this.customerIntroduction;
    }

    public void setCustomerIntroduction(String customerIntroduction) {
        this.customerIntroduction = customerIntroduction;
    }

}