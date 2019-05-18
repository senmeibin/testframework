package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyTeam;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_team")
public class CompanyTeamExt extends CompanyTeam {
    private static final long serialVersionUID = -20170511162206047L;
}