package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.ActivityCampus;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crm_activity_campus")
public class ActivityCampusExt extends ActivityCampus {
    private static final long serialVersionUID = -20180322180818126L;
}