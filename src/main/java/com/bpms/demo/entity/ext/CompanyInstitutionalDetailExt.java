package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyInstitutionalDetail;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "demo_company_institutional_detail")
public class CompanyInstitutionalDetailExt extends CompanyInstitutionalDetail {
    private static final long serialVersionUID = -20170511162801875L;


    /**
     * 企业UID
     */
    @Transient
    private String companyUid;

    /**
     * 企业
     */
    @Transient
    private String companyName;

    public String getCompanyUid() {
        return companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}