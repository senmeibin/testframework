package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.SmsLog;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_sms_log")
public class SmsLogExt extends SmsLog {
    private static final long serialVersionUID = -20160616103502253L;

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