package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Attachment;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_attachment")
public class AttachmentExt extends Attachment {
    private static final long serialVersionUID = -20160414163519182L;

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