package com.bpms.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 企业资质实体类
 */
@MappedSuperclass
public class CompanyQualification extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511162716447L;

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
     * 认定机构
     */
    @Length(max = 32, message = "请在认定机构中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String certificationAuthority;

    /**
     * 编号
     */
    @Length(max = 32, message = "请在编号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String serialNumber;

    /**
     * 认定年份
     */
    @Length(max = 4, message = "请在认定年份中输入[0-4]位以内的文字。")
    @Column(length = 4)
    private String certificationYear;

    /**
     * 复核/验收年份
     */
    @Length(max = 4, message = "请在复核/验收年份中输入[0-4]位以内的文字。")
    @Column(length = 4)
    private String acceptanceYear;

    /**
     * 资助金额
     */
    @Range(min = 0, max = 99999999, message = "请在资助金额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal subsidyAmount = new BigDecimal(0);

    public CompanyQualification() {
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

    public String getCertificationAuthority() {
        return this.certificationAuthority;
    }

    public void setCertificationAuthority(String certificationAuthority) {
        this.certificationAuthority = certificationAuthority;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCertificationYear() {
        return this.certificationYear;
    }

    public void setCertificationYear(String certificationYear) {
        this.certificationYear = certificationYear;
    }

    public String getAcceptanceYear() {
        return this.acceptanceYear;
    }

    public void setAcceptanceYear(String acceptanceYear) {
        this.acceptanceYear = acceptanceYear;
    }

    public BigDecimal getSubsidyAmount() {
        return this.subsidyAmount;
    }

    public void setSubsidyAmount(BigDecimal subsidyAmount) {
        this.subsidyAmount = subsidyAmount;
    }

}