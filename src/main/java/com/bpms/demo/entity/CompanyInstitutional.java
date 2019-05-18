package com.bpms.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 机构信息实体类
 */
@MappedSuperclass
public class CompanyInstitutional extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511162739354L;

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
     * 控股母公司
     */
    @Length(max = 32, message = "请在控股母公司中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String parentCompanyName;

    /**
     * 纳税人识别号
     */
    @Length(max = 32, message = "请在纳税人识别号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String taxpayerIdentificationNo;

    /**
     * 地址
     */
    @Length(max = 64, message = "请在地址中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String address;

    /**
     * 经营范围（营业执照）
     */
    @Length(max = 64, message = "请在经营范围（营业执照）中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String businessScope;

    public CompanyInstitutional() {
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

    public String getParentCompanyName() {
        return this.parentCompanyName;
    }

    public void setParentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public String getTaxpayerIdentificationNo() {
        return this.taxpayerIdentificationNo;
    }

    public void setTaxpayerIdentificationNo(String taxpayerIdentificationNo) {
        this.taxpayerIdentificationNo = taxpayerIdentificationNo;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessScope() {
        return this.businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

}