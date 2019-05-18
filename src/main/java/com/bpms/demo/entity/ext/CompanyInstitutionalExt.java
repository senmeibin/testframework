package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyInstitutional;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "demo_company_institutional")
public class CompanyInstitutionalExt extends CompanyInstitutional {
    private static final long serialVersionUID = -20170511162739454L;

    /**
     * 明细列表
     */
    @Transient
    private List<CompanyInstitutionalDetailExt> companyInstitutionalDetailList;

    public List<CompanyInstitutionalDetailExt> getCompanyInstitutionalDetailList() {
        return companyInstitutionalDetailList;
    }

    public void setCompanyInstitutionalDetailList(List<CompanyInstitutionalDetailExt> companyInstitutionalDetailList) {
        this.companyInstitutionalDetailList = companyInstitutionalDetailList;
    }
}