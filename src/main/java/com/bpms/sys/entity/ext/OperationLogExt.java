package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.OperationLog;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_operation_log")
public class OperationLogExt extends OperationLog {
    private static final long serialVersionUID = -20160414152527270L;

    /**
     * 应用名称
     */
    @Transient
    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}