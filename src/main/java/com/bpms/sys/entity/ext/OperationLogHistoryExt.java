package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.OperationLogHistory;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_operation_log_history")
public class OperationLogHistoryExt extends OperationLogHistory {
    private static final long serialVersionUID = -20160922101032202L;
}