package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.AttachmentImport;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_attachment_import")
public class AttachmentImportExt extends AttachmentImport {
    private static final long serialVersionUID = -20171110152214526L;

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