package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.AttachmentAccessLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_attachment_access_log")
public class AttachmentAccessLogExt extends AttachmentAccessLog {
    private static final long serialVersionUID = -20171229141246607L;
}