package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.DatabaseProcessOperationLog;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_database_process_operation_log")
public class DatabaseProcessOperationLogExt extends DatabaseProcessOperationLog {
    private static final long serialVersionUID = -20161219182229882L;

    /**
     * 执行的状态
     */
    @Transient
    private String executeState;
    /**
     * 执行语句类型
     */
    @Transient
    private String executeCommand;

    public String getExecuteState() {
        return executeState;
    }

    public void setExecuteState(String executeState) {
        this.executeState = executeState;
    }

    public String getExecuteCommand() {
        return executeCommand;
    }

    public void setExecuteCommand(String executeCommand) {
        this.executeCommand = executeCommand;
    }
}