package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.EnterpriseSetting;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_enterprise_setting")
public class EnterpriseSettingExt extends EnterpriseSetting {
    private static final long serialVersionUID = -20171031153123795L;
}