package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 执行日志实体类
 */
@MappedSuperclass
public class ExecuteLog extends SysBaseEntity {
    private static final long serialVersionUID = -20161129182138976L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 执行记录UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请设置执行记录UID。")
    private String executeRecordUid;

    /**
     * 功能编码
     */
    @Length(max = 256, message = "请在功能编码中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入功能编码。")
    @Column(length = 256, nullable = false)
    private String functionCode;

    /**
     * 功能名称
     */
    @Length(max = 256, message = "请在功能名称中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入功能名称。")
    @Column(length = 256, nullable = false)
    private String functionName;

    /**
     * 功能类型
     */
    @Length(max = 8, message = "请在功能类型中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入功能类型。")
    @Column(length = 8, nullable = false)
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
    @Range(min = 0, max = 99999999, message = "请在执行成功数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer executeRecordCount;

    /**
     * 返回结果
     */
    @NotNull(message = "请输入返回结果。")
    @Column(nullable = false)
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

    public ExecuteLog() {
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

    public Date getExecuteStartDate() {
        return this.executeStartDate;
    }

    public void setExecuteStartDate(Date executeStartDate) {
        this.executeStartDate = executeStartDate;
    }

    public Date getExecuteEndDate() {
        return this.executeEndDate;
    }

    public void setExecuteEndDate(Date executeEndDate) {
        this.executeEndDate = executeEndDate;
    }

    public String getExecuteMachine() {
        return this.executeMachine;
    }

    public void setExecuteMachine(String executeMachine) {
        this.executeMachine = executeMachine;
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

    public Integer getExecuteRecordCount() {
        return this.executeRecordCount;
    }

    public void setExecuteRecordCount(Integer executeRecordCount) {
        this.executeRecordCount = executeRecordCount;
    }

    public Integer getResult() {
        return this.result;
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

}