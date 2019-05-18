package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 签单公司实体类
 */
@MappedSuperclass
public class SignCompany extends CmnBaseEntity {
    private static final long serialVersionUID = -20160331100453012L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 公司名称
     */
    @Length(max = 64, message = "请在公司名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入公司名称。")
    @Column(length = 64, nullable = false)
    private String companyName;

    /**
     * 公司地址
     */
    @Length(max = 128, message = "请在公司地址中输入[0-128]位以内的文字。")
    @NotEmpty(message = "请输入公司地址。")
    @Column(length = 128, nullable = false)
    private String companyAddr;

    /**
     * 公司电话
     */
    @Length(max = 32, message = "请在公司电话中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入公司电话。")
    @Column(length = 32, nullable = false)
    private String companyTelephone;

    /**
     * 开户行
     */
    @Length(max = 64, message = "请在开户行中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入开户行。")
    @Column(length = 64, nullable = false)
    private String bankName;

    /**
     * 账号
     */
    @Length(max = 32, message = "请在账号中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入账号。")
    @Column(length = 32, nullable = false)
    private String bankNumber;

    /**
     * 纳税人识别号
     */
    @Length(max = 64, message = "请在纳税人识别号中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String identificationNumber;

    /**
     * 表示顺序
     */
    @Range(min = 0, max = 99999999, message = "请在表示顺序中输入[0-99999999999]范围内的数值。")
    @Column
    private Integer dispSeq = 1;

    /**
     * 开票分公司UID
     */
    @Length(max = 32, message = "请在开户行中输入[0-32]位以内的文字。")
    @Column(length = 32, nullable = false)
    private String invoiceCompanyUid;


    /**
     * 开票分公司
     */
    @Transient
    private String invoiceCompanyName;


    /**
     * 公司编码
     */
    @Length(max = 32, message = "请在公司编码中输入[0-32]位以内的文字。")
    @Column(length = 32, nullable = false)
    private String companyCode;


    /**
     * 公司编号
     */
    @Length(max = 32, message = "请在公司编号中输入[0-32]位以内的文字。")
    @Column(length = 32, nullable = false)
    private String companyNumber;

    public SignCompany() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return this.companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyTelephone() {
        return this.companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return this.bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    public String getInvoiceCompanyUid() {
        return invoiceCompanyUid;
    }

    public void setInvoiceCompanyUid(String invoiceCompanyUid) {
        this.invoiceCompanyUid = invoiceCompanyUid;
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public void setInvoiceCompanyName(String invoiceCompanyName) {
        this.invoiceCompanyName = invoiceCompanyName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }
}