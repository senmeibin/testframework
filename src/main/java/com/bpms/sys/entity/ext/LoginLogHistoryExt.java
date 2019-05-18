package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.LoginLogHistory;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_login_log_history")
public class LoginLogHistoryExt extends LoginLogHistory {
    private static final long serialVersionUID = -20160922101659311L;
}