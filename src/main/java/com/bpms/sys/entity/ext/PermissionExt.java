package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Permission;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_permission")
public class PermissionExt extends Permission {
    private static final long serialVersionUID = -20160402225459442L;

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