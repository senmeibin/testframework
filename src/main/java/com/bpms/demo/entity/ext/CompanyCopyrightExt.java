package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyCopyright;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_copyright")
public class CompanyCopyrightExt extends CompanyCopyright {
    private static final long serialVersionUID = -20170511162516579L;
}