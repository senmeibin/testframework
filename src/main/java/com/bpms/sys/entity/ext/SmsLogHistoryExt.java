package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.SmsLogHistory;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_sms_log_history")
public class SmsLogHistoryExt extends SmsLogHistory {
    private static final long serialVersionUID = -20160922102225108L;
}