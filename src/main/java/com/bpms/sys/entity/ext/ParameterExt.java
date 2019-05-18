package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Parameter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_parameter")
public class ParameterExt extends Parameter {
    private static final long serialVersionUID = -20160411092121525L;

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