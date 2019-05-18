package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyQualification;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_qualification")
public class CompanyQualificationExt extends CompanyQualification {
    private static final long serialVersionUID = -20170511162716547L;
}