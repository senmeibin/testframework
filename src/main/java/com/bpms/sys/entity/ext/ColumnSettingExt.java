package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.ColumnSetting;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_column_setting")
public class ColumnSettingExt extends ColumnSetting {
    private static final long serialVersionUID = -20160427112917829L;
}