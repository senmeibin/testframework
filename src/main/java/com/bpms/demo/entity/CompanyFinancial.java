package com.bpms.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 财务信息实体类
 */
@MappedSuperclass
public class CompanyFinancial extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511162419760L;

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
     * 年末总资产
     */
    @Range(min = 0, max = 99999999, message = "请在年末总资产中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal totalAssets = new BigDecimal(0);

    /**
     * 年末负债总额
     */
    @Range(min = 0, max = 99999999, message = "请在年末负债总额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal totalLiabilities = new BigDecimal(0);

    /**
     * 年末固定资产总额
     */
    @Range(min = 0, max = 99999999, message = "请在年末固定资产总额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal totalFixedAssets = new BigDecimal(0);

    /**
     * 年末完成固定资产投资额
     */
    @Range(min = 0, max = 99999999, message = "请在年末完成固定资产投资额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal totalCompletedAssets = new BigDecimal(0);

    /**
     * 年份
     */
    @Length(max = 4, message = "请在年份中输入[0-4]位以内的文字。")
    @Column(length = 4)
    private String particularYear;

    /**
     * 营业收入
     */
    @Range(min = 0, max = 99999999, message = "请在营业收入中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal businessIncome = new BigDecimal(0);

    /**
     * 主营业收入
     */
    @Range(min = 0, max = 99999999, message = "请在主营业收入中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal mainBusinessIncome = new BigDecimal(0);

    /**
     * 高新技术产品销售收入
     */
    @Range(min = 0, max = 99999999, message = "请在高新技术产品销售收入中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal highTechIncome = new BigDecimal(0);

    /**
     * 软件产品及服务收入
     */
    @Range(min = 0, max = 99999999, message = "请在软件产品及服务收入中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal softwareServicesIncome = new BigDecimal(0);

    /**
     * 工业总产值
     */
    @Range(min = 0, max = 99999999, message = "请在工业总产值中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal grossOutput = new BigDecimal(0);

    /**
     * 高新技术产品产值
     */
    @Range(min = 0, max = 99999999, message = "请在高新技术产品产值中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal highTechOutput = new BigDecimal(0);

    /**
     * 自主知识产权产品产值
     */
    @Range(min = 0, max = 99999999, message = "请在自主知识产权产品产值中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal independentOutput = new BigDecimal(0);

    /**
     * 增加值
     */
    @Range(min = 0, max = 99999999, message = "请在增加值中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal increaseValue = new BigDecimal(0);

    /**
     * 出口创汇
     */
    @Range(min = 0, max = 99999999, message = "请在出口创汇中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal exportEarnings = new BigDecimal(0);

    /**
     * 当年R&D投入
     */
    @Range(min = 0, max = 99999999, message = "请在当年R&D投入中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal rdInvestment = new BigDecimal(0);

    /**
     * 高新技术产品出口额
     */
    @Range(min = 0, max = 99999999, message = "请在高新技术产品出口额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal highTechProductsOutlet = new BigDecimal(0);

    /**
     * 税后利润
     */
    @Range(min = 0, max = 99999999, message = "请在税后利润中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal afterTaxProfits = new BigDecimal(0);

    /**
     * 实际上缴税费
     */
    @Range(min = 0, max = 99999999, message = "请在实际上缴税费中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal totalTax = new BigDecimal(0);

    /**
     * 增值税
     */
    @Range(min = 0, max = 99999999, message = "请在增值税中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal addedValueTax = new BigDecimal(0);

    /**
     * 营业税
     */
    @Range(min = 0, max = 99999999, message = "请在营业税中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal businessTax = new BigDecimal(0);

    /**
     * 所得税
     */
    @Range(min = 0, max = 99999999, message = "请在所得税中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal incomeTax = new BigDecimal(0);

    /**
     * 个人所得税
     */
    @Range(min = 0, max = 99999999, message = "请在个人所得税中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal personalIncomeTax = new BigDecimal(0);

    /**
     * 其他税
     */
    @Range(min = 0, max = 99999999, message = "请在其他税中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal otherTax = new BigDecimal(0);

    /**
     * 实际减免税费总额
     */
    @Range(min = 0, max = 99999999, message = "请在实际减免税费总额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal totalDeductionsTax = new BigDecimal(0);

    /**
     * 减免增值税
     */
    @Range(min = 0, max = 99999999, message = "请在减免增值税中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal deductionsAddedValueTax = new BigDecimal(0);

    /**
     * 减免营业额
     */
    @Range(min = 0, max = 99999999, message = "请在减免营业额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal deductionsBusinessTax = new BigDecimal(0);

    /**
     * 减免所得税
     */
    @Range(min = 0, max = 99999999, message = "请在减免所得税中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal deductionsIncomeTax = new BigDecimal(0);

    /**
     * 研发加计扣除减免
     */
    @Range(min = 0, max = 99999999, message = "请在研发加计扣除减免中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal deductionsRdTax = new BigDecimal(0);

    /**
     * 高新技术企业专项减免
     */
    @Range(min = 0, max = 99999999, message = "请在高新技术企业专项减免中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal deductionsHighTechTax = new BigDecimal(0);

    public CompanyFinancial() {
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

    public BigDecimal getTotalAssets() {
        return this.totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getTotalLiabilities() {
        return this.totalLiabilities;
    }

    public void setTotalLiabilities(BigDecimal totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public BigDecimal getTotalFixedAssets() {
        return this.totalFixedAssets;
    }

    public void setTotalFixedAssets(BigDecimal totalFixedAssets) {
        this.totalFixedAssets = totalFixedAssets;
    }

    public BigDecimal getTotalCompletedAssets() {
        return this.totalCompletedAssets;
    }

    public void setTotalCompletedAssets(BigDecimal totalCompletedAssets) {
        this.totalCompletedAssets = totalCompletedAssets;
    }

    public String getParticularYear() {
        return this.particularYear;
    }

    public void setParticularYear(String particularYear) {
        this.particularYear = particularYear;
    }

    public BigDecimal getBusinessIncome() {
        return this.businessIncome;
    }

    public void setBusinessIncome(BigDecimal businessIncome) {
        this.businessIncome = businessIncome;
    }

    public BigDecimal getMainBusinessIncome() {
        return this.mainBusinessIncome;
    }

    public void setMainBusinessIncome(BigDecimal mainBusinessIncome) {
        this.mainBusinessIncome = mainBusinessIncome;
    }

    public BigDecimal getHighTechIncome() {
        return this.highTechIncome;
    }

    public void setHighTechIncome(BigDecimal highTechIncome) {
        this.highTechIncome = highTechIncome;
    }

    public BigDecimal getSoftwareServicesIncome() {
        return this.softwareServicesIncome;
    }

    public void setSoftwareServicesIncome(BigDecimal softwareServicesIncome) {
        this.softwareServicesIncome = softwareServicesIncome;
    }

    public BigDecimal getGrossOutput() {
        return this.grossOutput;
    }

    public void setGrossOutput(BigDecimal grossOutput) {
        this.grossOutput = grossOutput;
    }

    public BigDecimal getHighTechOutput() {
        return this.highTechOutput;
    }

    public void setHighTechOutput(BigDecimal highTechOutput) {
        this.highTechOutput = highTechOutput;
    }

    public BigDecimal getIndependentOutput() {
        return this.independentOutput;
    }

    public void setIndependentOutput(BigDecimal independentOutput) {
        this.independentOutput = independentOutput;
    }

    public BigDecimal getIncreaseValue() {
        return this.increaseValue;
    }

    public void setIncreaseValue(BigDecimal increaseValue) {
        this.increaseValue = increaseValue;
    }

    public BigDecimal getExportEarnings() {
        return this.exportEarnings;
    }

    public void setExportEarnings(BigDecimal exportEarnings) {
        this.exportEarnings = exportEarnings;
    }

    public BigDecimal getRdInvestment() {
        return this.rdInvestment;
    }

    public void setRdInvestment(BigDecimal rdInvestment) {
        this.rdInvestment = rdInvestment;
    }

    public BigDecimal getHighTechProductsOutlet() {
        return this.highTechProductsOutlet;
    }

    public void setHighTechProductsOutlet(BigDecimal highTechProductsOutlet) {
        this.highTechProductsOutlet = highTechProductsOutlet;
    }

    public BigDecimal getAfterTaxProfits() {
        return this.afterTaxProfits;
    }

    public void setAfterTaxProfits(BigDecimal afterTaxProfits) {
        this.afterTaxProfits = afterTaxProfits;
    }

    public BigDecimal getTotalTax() {
        return this.totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getAddedValueTax() {
        return this.addedValueTax;
    }

    public void setAddedValueTax(BigDecimal addedValueTax) {
        this.addedValueTax = addedValueTax;
    }

    public BigDecimal getBusinessTax() {
        return this.businessTax;
    }

    public void setBusinessTax(BigDecimal businessTax) {
        this.businessTax = businessTax;
    }

    public BigDecimal getIncomeTax() {
        return this.incomeTax;
    }

    public void setIncomeTax(BigDecimal incomeTax) {
        this.incomeTax = incomeTax;
    }

    public BigDecimal getPersonalIncomeTax() {
        return this.personalIncomeTax;
    }

    public void setPersonalIncomeTax(BigDecimal personalIncomeTax) {
        this.personalIncomeTax = personalIncomeTax;
    }

    public BigDecimal getOtherTax() {
        return this.otherTax;
    }

    public void setOtherTax(BigDecimal otherTax) {
        this.otherTax = otherTax;
    }

    public BigDecimal getTotalDeductionsTax() {
        return this.totalDeductionsTax;
    }

    public void setTotalDeductionsTax(BigDecimal totalDeductionsTax) {
        this.totalDeductionsTax = totalDeductionsTax;
    }

    public BigDecimal getDeductionsAddedValueTax() {
        return this.deductionsAddedValueTax;
    }

    public void setDeductionsAddedValueTax(BigDecimal deductionsAddedValueTax) {
        this.deductionsAddedValueTax = deductionsAddedValueTax;
    }

    public BigDecimal getDeductionsBusinessTax() {
        return this.deductionsBusinessTax;
    }

    public void setDeductionsBusinessTax(BigDecimal deductionsBusinessTax) {
        this.deductionsBusinessTax = deductionsBusinessTax;
    }

    public BigDecimal getDeductionsIncomeTax() {
        return this.deductionsIncomeTax;
    }

    public void setDeductionsIncomeTax(BigDecimal deductionsIncomeTax) {
        this.deductionsIncomeTax = deductionsIncomeTax;
    }

    public BigDecimal getDeductionsRdTax() {
        return this.deductionsRdTax;
    }

    public void setDeductionsRdTax(BigDecimal deductionsRdTax) {
        this.deductionsRdTax = deductionsRdTax;
    }

    public BigDecimal getDeductionsHighTechTax() {
        return this.deductionsHighTechTax;
    }

    public void setDeductionsHighTechTax(BigDecimal deductionsHighTechTax) {
        this.deductionsHighTechTax = deductionsHighTechTax;
    }

}