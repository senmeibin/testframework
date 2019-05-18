package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.ExecuteLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_execute_log")
public class ExecuteLogExt extends ExecuteLog {
    private static final long serialVersionUID = -20161114134550098L;
}