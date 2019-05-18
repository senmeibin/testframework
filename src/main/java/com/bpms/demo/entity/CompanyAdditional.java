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
 * 附加信息实体类
 */
@MappedSuperclass
public class CompanyAdditional extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511162341338L;

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
     * 证券代码
     */
    @Length(max = 32, message = "请在证券代码中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String stockCode;

    /**
     * 上市时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date listedTime;

    /**
     * 上市市场
     */
    @Length(max = 8, message = "请在上市市场中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String listedTypeCd;

    /**
     * 上市市场
     */
    @Transient
    private String listedTypeName;

    /**
     * 总股本
     */
    @Length(max = 32, message = "请在总股本中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String totalShare;

    /**
     * 年底市值
     */
    @Range(min = 0, max = 99999999, message = "请在年底市值中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal yearEndMarketValue = new BigDecimal(0);

    /**
     * 退市时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date delistingDate;

    public CompanyAdditional() {
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

    public String getStockCode() {
        return this.stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Date getListedTime() {
        return this.listedTime;
    }

    public void setListedTime(Date listedTime) {
        this.listedTime = listedTime;
    }

    public String getListedTypeCd() {
        return this.listedTypeCd;
    }

    public void setListedTypeCd(String listedTypeCd) {
        this.listedTypeCd = listedTypeCd;
    }

    public String getListedTypeName() {
        return this.listedTypeName;
    }

    public void setListedTypeName(String listedTypeName) {
        this.listedTypeName = listedTypeName;
    }

    public String getTotalShare() {
        return this.totalShare;
    }

    public void setTotalShare(String totalShare) {
        this.totalShare = totalShare;
    }

    public BigDecimal getYearEndMarketValue() {
        return this.yearEndMarketValue;
    }

    public void setYearEndMarketValue(BigDecimal yearEndMarketValue) {
        this.yearEndMarketValue = yearEndMarketValue;
    }

    public Date getDelistingDate() {
        return this.delistingDate;
    }

    public void setDelistingDate(Date delistingDate) {
        this.delistingDate = delistingDate;
    }

}