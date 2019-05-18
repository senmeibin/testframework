package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.ExecuteLogHistory;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_execute_log_history")
public class ExecuteLogHistoryExt extends ExecuteLogHistory {
    private static final long serialVersionUID = -20161117094646000L;
}