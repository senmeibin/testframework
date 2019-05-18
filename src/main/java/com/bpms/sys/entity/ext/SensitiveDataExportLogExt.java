package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.SensitiveDataExportLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_sensitive_data_export_log")
public class SensitiveDataExportLogExt extends SensitiveDataExportLog {
    private static final long serialVersionUID = -20180313084115801L;
}