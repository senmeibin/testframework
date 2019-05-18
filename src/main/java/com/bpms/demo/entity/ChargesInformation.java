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
 * 企业收费信息实体类
 */
@MappedSuperclass
public class ChargesInformation extends DemoBaseEntity {
    private static final long serialVersionUID = -20170418164256788L;

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
     * 单位
     */
    @Length(max = 8, message = "请在单位中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String unitCd;

    /**
     * 单位
     */
    @Transient
    private String unitName;

    /**
     * 数量
     */
    @Range(min = 0, max = 99999999, message = "请在数量中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal quantity;

    /**
     * 工位位置
     */
    @Length(max = 64, message = "请在工位位置中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String stationPosition;

    /**
     * 工位租金
     */
    @Range(min = 0, max = 99999999, message = "请在工位租金中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal stationRent;

    /**
     * 收租方式
     */
    @Length(max = 8, message = "请在收租方式中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String rentWayCd;

    /**
     * 收租方式
     */
    @Transient
    private String rentWayName;

    /**
     * 押金方式
     */
    @Length(max = 8, message = "请在押金方式中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String depositMethodCd;

    /**
     * 押金方式
     */
    @Transient
    private String depositMethodName;

    /**
     * 企业押金
     */
    @Range(min = 0, max = 99999999, message = "请在企业押金中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal businessDeposit = new BigDecimal(0);

    /**
     * 收费方式
     */
    @Length(max = 8, message = "请在收费方式中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String chargesTypeCd;

    /**
     * 收费方式
     */
    @Transient
    private String chargesTypeName;

    /**
     * 第一次收费日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date firstChargeDate;

    /**
     * 每期收费金额
     */
    @Range(min = 0, max = 99999999, message = "请在每期收费金额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal eachChargeAmount = new BigDecimal(0);

    /**
     * 下次收款日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date nextReceiptDate;

    public ChargesInformation() {
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

    public String getUnitCd() {
        return unitCd;
    }

    public void setUnitCd(String unitCd) {
        this.unitCd = unitCd;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getStationPosition() {
        return stationPosition;
    }

    public void setStationPosition(String stationPosition) {
        this.stationPosition = stationPosition;
    }

    public BigDecimal getStationRent() {
        return stationRent;
    }

    public void setStationRent(BigDecimal stationRent) {
        this.stationRent = stationRent;
    }

    public String getRentWayCd() {
        return rentWayCd;
    }

    public void setRentWayCd(String rentWayCd) {
        this.rentWayCd = rentWayCd;
    }

    public String getRentWayName() {
        return rentWayName;
    }

    public void setRentWayName(String rentWayName) {
        this.rentWayName = rentWayName;
    }

    public String getDepositMethodCd() {
        return depositMethodCd;
    }

    public void setDepositMethodCd(String depositMethodCd) {
        this.depositMethodCd = depositMethodCd;
    }

    public String getDepositMethodName() {
        return depositMethodName;
    }

    public void setDepositMethodName(String depositMethodName) {
        this.depositMethodName = depositMethodName;
    }

    public BigDecimal getBusinessDeposit() {
        return this.businessDeposit;
    }

    public void setBusinessDeposit(BigDecimal businessDeposit) {
        this.businessDeposit = businessDeposit;
    }

    public String getChargesTypeCd() {
        return this.chargesTypeCd;
    }

    public void setChargesTypeCd(String chargesTypeCd) {
        this.chargesTypeCd = chargesTypeCd;
    }

    public String getChargesTypeName() {
        return this.chargesTypeName;
    }

    public void setChargesTypeName(String chargesTypeName) {
        this.chargesTypeName = chargesTypeName;
    }

    public Date getFirstChargeDate() {
        return this.firstChargeDate;
    }

    public void setFirstChargeDate(Date firstChargeDate) {
        this.firstChargeDate = firstChargeDate;
    }

    public BigDecimal getEachChargeAmount() {
        return this.eachChargeAmount;
    }

    public void setEachChargeAmount(BigDecimal eachChargeAmount) {
        this.eachChargeAmount = eachChargeAmount;
    }

    public Date getNextReceiptDate() {
        return this.nextReceiptDate;
    }

    public void setNextReceiptDate(Date nextReceiptDate) {
        this.nextReceiptDate = nextReceiptDate;
    }

}