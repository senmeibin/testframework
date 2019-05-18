package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Enterprise;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "SysEnterprise")
@Table(name = "sys_enterprise")
public class EnterpriseExt extends Enterprise {
    private static final long serialVersionUID = -20171018161240182L;
}