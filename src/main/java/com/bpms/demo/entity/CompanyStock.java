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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 股权信息实体类
 */
@MappedSuperclass
public class CompanyStock extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511161824304L;

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
     * 股东类型
     */
    @Length(max = 8, message = "请在股东类型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String shareholderTypeCd;

    /**
     * 股东类型
     */
    @Transient
    private String shareholderTypeName;

    /**
     * 股东名称
     */
    @Length(max = 32, message = "请在股东名称中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String shareholderName;

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
     * 是否上市公司
     */
    @Length(max = 8, message = "请在是否上市公司中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String listedCompanyCd;

    /**
     * 是否上市公司
     */
    @Transient
    private String listedCompanyName;

    /**
     * 是否入选千人计划
     */
    @Length(max = 8, message = "请在是否入选千人计划中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String thousandsPeoplePlanCd;

    /**
     * 是否入选千人计划
     */
    @Transient
    private String thousandsPeoplePlanName;

    /**
     * 所占股份
     */
    @Length(max = 32, message = "请在所占股份中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String sharesProportion;

    /**
     * 投资总额
     */
    @Range(min = 0, max = 99999999, message = "请在投资总额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal totalInvestment = new BigDecimal(0);

    /**
     * 是否境外公司或外籍
     */
    @Length(max = 8, message = "请在是否境外公司或外籍中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String overseasCompanyCd;

    /**
     * 是否境外公司或外籍
     */
    @Transient
    private String overseasCompanyName;

    /**
     * 外资部门所占股份总和
     */
    @Range(min = 0, max = 99999999, message = "请在外资部门所占股份总和中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal totalShareForeignCapital = new BigDecimal(0);

    /**
     * 上市企业所占股份总和
     */
    @Range(min = 0, max = 99999999, message = "请在上市企业所占股份总和中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal totalSharesListedCompany = new BigDecimal(0);

    /**
     * 是否风险投资（基金）公司
     */
    @Length(max = 8, message = "请在是否风险投资（基金）公司中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String fundCompanyCd;

    /**
     * 是否风险投资（基金）公司
     */
    @Transient
    private String fundCompanyName;

    /**
     * 第一投资时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date firstInvestmentDate;

    public CompanyStock() {
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

    public String getShareholderTypeCd() {
        return this.shareholderTypeCd;
    }

    public void setShareholderTypeCd(String shareholderTypeCd) {
        this.shareholderTypeCd = shareholderTypeCd;
    }

    public String getShareholderTypeName() {
        return this.shareholderTypeName;
    }

    public void setShareholderTypeName(String shareholderTypeName) {
        this.shareholderTypeName = shareholderTypeName;
    }

    public String getShareholderName() {
        return this.shareholderName;
    }

    public void setShareholderName(String shareholderName) {
        this.shareholderName = shareholderName;
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

    public String getListedCompanyCd() {
        return this.listedCompanyCd;
    }

    public void setListedCompanyCd(String listedCompanyCd) {
        this.listedCompanyCd = listedCompanyCd;
    }

    public String getListedCompanyName() {
        return this.listedCompanyName;
    }

    public void setListedCompanyName(String listedCompanyName) {
        this.listedCompanyName = listedCompanyName;
    }

    public String getThousandsPeoplePlanCd() {
        return this.thousandsPeoplePlanCd;
    }

    public void setThousandsPeoplePlanCd(String thousandsPeoplePlanCd) {
        this.thousandsPeoplePlanCd = thousandsPeoplePlanCd;
    }

    public String getThousandsPeoplePlanName() {
        return this.thousandsPeoplePlanName;
    }

    public void setThousandsPeoplePlanName(String thousandsPeoplePlanName) {
        this.thousandsPeoplePlanName = thousandsPeoplePlanName;
    }

    public String getSharesProportion() {
        return this.sharesProportion;
    }

    public void setSharesProportion(String sharesProportion) {
        this.sharesProportion = sharesProportion;
    }

    public BigDecimal getTotalInvestment() {
        return this.totalInvestment;
    }

    public void setTotalInvestment(BigDecimal totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getOverseasCompanyCd() {
        return this.overseasCompanyCd;
    }

    public void setOverseasCompanyCd(String overseasCompanyCd) {
        this.overseasCompanyCd = overseasCompanyCd;
    }

    public String getOverseasCompanyName() {
        return this.overseasCompanyName;
    }

    public void setOverseasCompanyName(String overseasCompanyName) {
        this.overseasCompanyName = overseasCompanyName;
    }

    public BigDecimal getTotalShareForeignCapital() {
        return this.totalShareForeignCapital;
    }

    public void setTotalShareForeignCapital(BigDecimal totalShareForeignCapital) {
        this.totalShareForeignCapital = totalShareForeignCapital;
    }

    public BigDecimal getTotalSharesListedCompany() {
        return this.totalSharesListedCompany;
    }

    public void setTotalSharesListedCompany(BigDecimal totalSharesListedCompany) {
        this.totalSharesListedCompany = totalSharesListedCompany;
    }

    public String getFundCompanyCd() {
        return this.fundCompanyCd;
    }

    public void setFundCompanyCd(String fundCompanyCd) {
        this.fundCompanyCd = fundCompanyCd;
    }

    public String getFundCompanyName() {
        return this.fundCompanyName;
    }

    public void setFundCompanyName(String fundCompanyName) {
        this.fundCompanyName = fundCompanyName;
    }

    public Date getFirstInvestmentDate() {
        return this.firstInvestmentDate;
    }

    public void setFirstInvestmentDate(Date firstInvestmentDate) {
        this.firstInvestmentDate = firstInvestmentDate;
    }

}