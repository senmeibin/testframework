package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyFinancial;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_financial")
public class CompanyFinancialExt extends CompanyFinancial {
    private static final long serialVersionUID = -20170511162419860L;
}