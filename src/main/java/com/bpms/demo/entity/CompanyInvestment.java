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
 * 投融资实体类
 */
@MappedSuperclass
public class CompanyInvestment extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511162559432L;

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
     * 接收投资时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date investmentDate;

    /**
     * 投资人
     */
    @Length(max = 32, message = "请在投资人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String investor;

    /**
     * 投资金额
     */
    @Range(min = 0, max = 99999999, message = "请在投资金额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal investmentAmount = new BigDecimal(0);

    /**
     * 占股权比例
     */
    @Range(min = 0, max = 99999999, message = "请在占股权比例中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal proportionOfStock = new BigDecimal(0);

    /**
     * 账面估值
     */
    @Range(min = 0, max = 99999999, message = "请在账面估值中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal bookValuation = new BigDecimal(0);

    /**
     * 是否属于孵化基金
     */
    @Length(max = 8, message = "请在是否属于孵化基金中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String incubationFundCd;

    /**
     * 是否属于孵化基金
     */
    @Transient
    private String incubationFundName;

    public CompanyInvestment() {
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

    public Date getInvestmentDate() {
        return this.investmentDate;
    }

    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
    }

    public String getInvestor() {
        return this.investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor;
    }

    public BigDecimal getInvestmentAmount() {
        return this.investmentAmount;
    }

    public void setInvestmentAmount(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public BigDecimal getProportionOfStock() {
        return this.proportionOfStock;
    }

    public void setProportionOfStock(BigDecimal proportionOfStock) {
        this.proportionOfStock = proportionOfStock;
    }

    public BigDecimal getBookValuation() {
        return this.bookValuation;
    }

    public void setBookValuation(BigDecimal bookValuation) {
        this.bookValuation = bookValuation;
    }

    public String getIncubationFundCd() {
        return this.incubationFundCd;
    }

    public void setIncubationFundCd(String incubationFundCd) {
        this.incubationFundCd = incubationFundCd;
    }

    public String getIncubationFundName() {
        return this.incubationFundName;
    }

    public void setIncubationFundName(String incubationFundName) {
        this.incubationFundName = incubationFundName;
    }

}