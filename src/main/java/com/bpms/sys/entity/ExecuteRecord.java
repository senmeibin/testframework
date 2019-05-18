package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 执行记录实体类
 */
@MappedSuperclass
public class ExecuteRecord extends SysBaseEntity {
    private static final long serialVersionUID = -20161129181709557L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 功能编码
     */
    @Length(max = 128, message = "请在功能编码中输入[0-128]位以内的文字。")
    @Column(length = 128)
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
     * 任务状态
     */
    @Length(max = 8, message = "请在任务状态中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String functionStatusCd;

    /**
     * 任务状态
     */
    @Transient
    private String functionStatusName;

    /**
     * 最后执行开始时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date lastExecuteStartDate;

    /**
     * 最后执行结束时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date lastExecuteEndDate;

    /**
     * 最后执行机器
     */
    @Length(max = 64, message = "请在最后执行机器中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String lastExecuteMachine;


    /**
     * 业务数据最后更新时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date lastRecordUpdateDate;

    /**
     * 业务数据最后添加时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date lastRecordInsertDate;

    /**
     * 业务数据最后更新UID
     */
    @Column(length = 32)
    private String lastRecordUpdateUid;

    /**
     * 累计执行时间
     */
    @Column
    private BigDecimal totalExecuteTime = new BigDecimal(0);

    /**
     * 累计执行次数
     */
    @Column
    private BigDecimal totalExecuteCount = new BigDecimal(0);

    /**
     * 下次执行时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date nextExecuteDate;

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

    public ExecuteRecord() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
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

    public String getFunctionStatusCd() {
        return this.functionStatusCd;
    }

    public void setFunctionStatusCd(String functionStatusCd) {
        this.functionStatusCd = functionStatusCd;
    }

    public String getFunctionStatusName() {
        return this.functionStatusName;
    }

    public void setFunctionStatusName(String functionStatusName) {
        this.functionStatusName = functionStatusName;
    }

    public Date getLastExecuteStartDate() {
        return this.lastExecuteStartDate;
    }

    public void setLastExecuteStartDate(Date lastExecuteStartDate) {
        this.lastExecuteStartDate = lastExecuteStartDate;
    }

    public Date getLastExecuteEndDate() {
        return this.lastExecuteEndDate;
    }

    public void setLastExecuteEndDate(Date lastExecuteEndDate) {
        this.lastExecuteEndDate = lastExecuteEndDate;
    }

    public String getLastExecuteMachine() {
        return this.lastExecuteMachine;
    }

    public void setLastExecuteMachine(String lastExecuteMachine) {
        this.lastExecuteMachine = lastExecuteMachine;
    }

    public BigDecimal getTotalExecuteTime() {
        return this.totalExecuteTime;
    }

    public void setTotalExecuteTime(BigDecimal totalExecuteTime) {
        this.totalExecuteTime = totalExecuteTime;
    }

    public Date getLastRecordUpdateDate() {
        return lastRecordUpdateDate;
    }

    public void setLastRecordUpdateDate(Date lastRecordUpdateDate) {
        this.lastRecordUpdateDate = lastRecordUpdateDate;
    }

    public Date getLastRecordInsertDate() {
        return lastRecordInsertDate;
    }

    public void setLastRecordInsertDate(Date lastRecordInsertDate) {
        this.lastRecordInsertDate = lastRecordInsertDate;
    }


    public String getLastRecordUpdateUid() {
        return lastRecordUpdateUid;
    }

    public void setLastRecordUpdateUid(String lastRecordUpdateUid) {
        this.lastRecordUpdateUid = lastRecordUpdateUid;
    }

    public BigDecimal getTotalExecuteCount() {
        return this.totalExecuteCount;
    }

    public void setTotalExecuteCount(BigDecimal totalExecuteCount) {
        this.totalExecuteCount = totalExecuteCount;
    }

    public Date getNextExecuteDate() {
        return this.nextExecuteDate;
    }

    public void setNextExecuteDate(Date nextExecuteDate) {
        this.nextExecuteDate = nextExecuteDate;
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

}