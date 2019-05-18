package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyInvestment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_investment")
public class CompanyInvestmentExt extends CompanyInvestment {
    private static final long serialVersionUID = -20170511162559532L;
}