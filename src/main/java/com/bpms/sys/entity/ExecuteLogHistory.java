package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 执行日志历史实体类
 */
@MappedSuperclass
public class ExecuteLogHistory extends SysBaseEntity {
    private static final long serialVersionUID = -20161129182841865L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 执行记录UID
     */
    @Column(length = 32)
    private String executeRecordUid;

    /**
     * 功能编码
     */
    @Length(max = 256, message = "请在功能编码中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String functionCode;

    /**
     * 功能名称
     */
    @Length(max = 256, message = "请在功能名称中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String functionName;

    /**
     * 功能类型
     */
    @Length(max = 8, message = "请在功能类型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String functionTypeCd;

    /**
     * 功能类型
     */
    @Transient
    private String functionTypeName;

    /**
     * 功能方法
     */
    @Length(max = 64, message = "请在功能方法中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String functionMethod;

    /**
     * 执行开始时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date executeStartDate;

    /**
     * 执行结束时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date executeEndDate;

    /**
     * 执行机器
     */
    @Length(max = 64, message = "请在执行机器中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String executeMachine;

    /**
     * 来源系统
     */
    @Length(max = 32, message = "请在来源系统中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String sourceSystemName;

    /**
     * 目标系统
     */
    @Length(max = 32, message = "请在目标系统中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String destSystemName;

    /**
     * 执行内容
     */
    @Column
    private String executeContent;

    /**
     * 执行成功数
     */
    @Length(max = 16, message = "请在执行成功数中输入[0-16]位以内的文字。")
    @Column(length = 16)
    private String executeResultSum;

    /**
     * 返回结果
     */
    @Column
    private Integer result;

    /**
     * 结果编码
     */
    @Length(max = 64, message = "请在结果编码中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String resultCode;

    /**
     * 结果消息
     */
    @Length(max = 256, message = "请在结果消息中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String resultMessage;

    /**
     * 数据迁移时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date migrationDate;

    public ExecuteLogHistory() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getExecuteRecordUid() {
        return this.executeRecordUid;
    }

    public void setExecuteRecordUid(String executeRecordUid) {
        this.executeRecordUid = executeRecordUid;
    }

    public String getFunctionCode() {
        return this.functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionTypeCd() {
        return this.functionTypeCd;
    }

    public void setFunctionTypeCd(String functionTypeCd) {
        this.functionTypeCd = functionTypeCd;
    }

    public String getFunctionTypeName() {
        return this.functionTypeName;
    }

    public void setFunctionTypeName(String functionTypeName) {
        this.functionTypeName = functionTypeName;
    }

    public String getFunctionMethod() {
        return this.functionMethod;
    }

    public void setFunctionMethod(String functionMethod) {
        this.functionMethod = functionMethod;
    }

    public String getExecuteMachine() {
        return executeMachine;
    }

    public void setExecuteMachine(String executeMachine) {
        this.executeMachine = executeMachine;
    }

    public Date getExecuteEndDate() {
        return executeEndDate;
    }

    public void setExecuteEndDate(Date executeEndDate) {
        this.executeEndDate = executeEndDate;
    }

    public Date getExecuteStartDate() {
        return executeStartDate;
    }

    public void setExecuteStartDate(Date executeStartDate) {
        this.executeStartDate = executeStartDate;
    }

    public String getSourceSystemName() {
        return this.sourceSystemName;
    }

    public void setSourceSystemName(String sourceSystemName) {
        this.sourceSystemName = sourceSystemName;
    }

    public String getDestSystemName() {
        return this.destSystemName;
    }

    public void setDestSystemName(String destSystemName) {
        this.destSystemName = destSystemName;
    }

    public String getExecuteContent() {
        return this.executeContent;
    }

    public void setExecuteContent(String executeContent) {
        this.executeContent = executeContent;
    }

    public String getExecuteResultSum() {
        return this.executeResultSum;
    }

    public void setExecuteResultSum(String executeResultSum) {
        this.executeResultSum = executeResultSum;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Date getMigrationDate() {
        return this.migrationDate;
    }

    public void setMigrationDate(Date migrationDate) {
        this.migrationDate = migrationDate;
    }
}