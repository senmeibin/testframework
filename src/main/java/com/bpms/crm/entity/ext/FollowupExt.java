package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.Followup;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crm_followup")
public class FollowupExt extends Followup {
    private static final long serialVersionUID = -20180331115336380L;
}