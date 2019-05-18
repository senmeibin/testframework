package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyIntellectual;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_intellectual")
public class CompanyIntellectualExt extends CompanyIntellectual {
    private static final long serialVersionUID = -20170511162637063L;
}