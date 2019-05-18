package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.CampusConsultant;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crm_campus_consultant")
public class CampusConsultantExt extends CampusConsultant {
    private static final long serialVersionUID = -20180320085525231L;
}