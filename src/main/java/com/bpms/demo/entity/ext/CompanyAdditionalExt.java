package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyAdditional;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_additional")
public class CompanyAdditionalExt extends CompanyAdditional {
    private static final long serialVersionUID = -20170511162341438L;
}