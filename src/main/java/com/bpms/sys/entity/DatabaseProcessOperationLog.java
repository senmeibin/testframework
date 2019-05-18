package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 数据库进程操作日志实体类
 */
@MappedSuperclass
public class DatabaseProcessOperationLog extends SysBaseEntity {
    private static final long serialVersionUID = -20161219182229778L;

    /**
     * 进程号
     */
    @Range(min = 0, max = 99999999, message = "请在进程号中输入[0-99999999]范围内的数值。")
    @NotNull(message = "请输入进程号。")
    @Column(nullable = false)
    private BigDecimal processId;

    /**
     * 用户
     */
    @Length(max = 64, message = "请在用户中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入用户。")
    @Column(length = 64, nullable = false)
    private String userName;

    /**
     * 主机地址
     */
    @Length(max = 64, message = "请在主机地址中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入主机地址。")
    @Column(length = 64, nullable = false)
    private String hostName;

    /**
     * 数据库名
     */
    @Length(max = 64, message = "请在数据库名中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入数据库名。")
    @Column(length = 64, nullable = false)
    private String databaseName;

    /**
     * 执行等待时间
     */
    @Range(min = 0, max = 99999999, message = "请在执行等待时间中输入[0-99999999]范围内的数值。")
    @NotNull(message = "请输入执行等待时间。")
    @Column(nullable = false)
    private Integer executeWaitTime = 0;

    /**
     * 执行信息
     */
    @Column
    private String executeInformation;

    public DatabaseProcessOperationLog() {
    }

    public BigDecimal getProcessId() {
        return this.processId;
    }

    public void setProcessId(BigDecimal processId) {
        this.processId = processId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Integer getExecuteWaitTime() {
        return this.executeWaitTime;
    }

    public void setExecuteWaitTime(Integer executeWaitTime) {
        this.executeWaitTime = executeWaitTime;
    }

    public String getExecuteInformation() {
        return this.executeInformation;
    }

    public void setExecuteInformation(String executeInformation) {
        this.executeInformation = executeInformation;
    }

}