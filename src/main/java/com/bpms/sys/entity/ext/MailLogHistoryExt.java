package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.MailLogHistory;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_mail_log_history")
public class MailLogHistoryExt extends MailLogHistory {
    private static final long serialVersionUID = -20160922102005889L;
}