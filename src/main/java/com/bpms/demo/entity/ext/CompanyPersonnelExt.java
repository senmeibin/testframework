package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyPersonnel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_personnel")
public class CompanyPersonnelExt extends CompanyPersonnel {
    private static final long serialVersionUID = -20170511161940328L;
}