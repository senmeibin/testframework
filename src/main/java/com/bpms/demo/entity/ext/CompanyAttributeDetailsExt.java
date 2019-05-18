package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyAttributeDetails;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_attribute_details")
public class CompanyAttributeDetailsExt extends CompanyAttributeDetails {
    private static final long serialVersionUID = -20170418143722950L;
}