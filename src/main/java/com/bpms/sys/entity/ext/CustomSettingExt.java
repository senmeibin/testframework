package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.CustomSetting;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_custom_setting")
public class CustomSettingExt extends CustomSetting {
    private static final long serialVersionUID = -20160913105431762L;
}