package com.bpms.pms.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.BaseEntity;
import com.bpms.pms.entity.PmsBaseEntity;

/**
 * 项目实体类
 */
@MappedSuperclass
public class Project extends PmsBaseEntity {
    private static final long serialVersionUID = -20180510212515154L;

    /**
     * 企业UID
     */
    @Column(name = "enterprise_uid", length = 8)
    private Integer enterpriseUid;

    /**
     * 企业
     */
    @Transient
    private String enterpriseName;

    /**
     * 名称
     */
    @Length(max = 256, message = "请在名称中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入名称。")
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 地址
     */
    @Length(max = 256, message = "请在地址中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入地址。")
    @Column(name = "address", length = 256, nullable = false)
    private String address;

    /**
     * 报名开始时间
     */
    @Column(name = "entry_start_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date entryStartDate;

    /**
     * 报名终了时间
     */
    @Column(name = "entry_end_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date entryEndDate;

    /**
     * 踏勘开始时间
     */
    @Column(name = "exploration_start_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date explorationStartDate;

    /**
     * 开标时间
     */
    @Column(name = "bid_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date bidDate;

    /**
     * 进场时间
     */
    @Column(name = "project_start_date", nullable = false)
    @NotNull(message = "请输入进场时间。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date projectStartDate;

    /**
     * 实施能力
     */ 
    @Column(name = "exe_capability")
    private String exeCapability;

    /**
     * 预算价格
     */ 
    @Column(name = "budgetary_price")
    private String budgetaryPrice;

    /**
     * 价格评估
     */ 
    @Column(name = "price_evaluation")
    private String priceEvaluation;

    /**
     * 流程节点
     */
    @Length(max = 32, message = "请在流程节点中输入[0-32]位以内的文字。")
    @Column(name = "workflow_node", length = 32)
    private String workflowNode;

    /**
     * 子流程节点
     */
    @Length(max = 32, message = "请在子流程节点中输入[0-32]位以内的文字。")
    @Column(name = "sub_workflow_node", length = 32)
    private String subWorkflowNode;

    /**
     * 前项目UID
     */
    @Column(name = "pre_project_uid", length = 32)
    private String preProjectUid;

    /**
     * 前项目
     */
    @Transient
    private String preProjectName;

    public Project() {
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Integer getEnterpriseUid() {
        return this.enterpriseUid;
    }

    public String getEnterpriseName() {
        return this.enterpriseName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setEntryStartDate(Date entryStartDate) {
        this.entryStartDate = entryStartDate;
    }

    public Date getEntryStartDate() {
        return this.entryStartDate;
    }

    public void setEntryEndDate(Date entryEndDate) {
        this.entryEndDate = entryEndDate;
    }

    public Date getEntryEndDate() {
        return this.entryEndDate;
    }

    public void setExplorationStartDate(Date explorationStartDate) {
        this.explorationStartDate = explorationStartDate;
    }

    public Date getExplorationStartDate() {
        return this.explorationStartDate;
    }

    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    public Date getBidDate() {
        return this.bidDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public Date getProjectStartDate() {
        return this.projectStartDate;
    }

    public void setExeCapability(String exeCapability) {
        this.exeCapability = exeCapability;
    }

    public String getExeCapability() {
        return this.exeCapability;
    }

    public void setBudgetaryPrice(String budgetaryPrice) {
        this.budgetaryPrice = budgetaryPrice;
    }

    public String getBudgetaryPrice() {
        return this.budgetaryPrice;
    }

    public void setPriceEvaluation(String priceEvaluation) {
        this.priceEvaluation = priceEvaluation;
    }

    public String getPriceEvaluation() {
        return this.priceEvaluation;
    }

    public void setWorkflowNode(String workflowNode) {
        this.workflowNode = workflowNode;
    }

    public String getWorkflowNode() {
        return this.workflowNode;
    }

    public void setSubWorkflowNode(String subWorkflowNode) {
        this.subWorkflowNode = subWorkflowNode;
    }

    public String getSubWorkflowNode() {
        return this.subWorkflowNode;
    }

    public void setPreProjectUid(String preProjectUid) {
        this.preProjectUid = preProjectUid;
    }

    public void setPreProjectName(String preProjectName) {
        this.preProjectName = preProjectName;
    }

    public String getPreProjectUid() {
        return this.preProjectUid;
    }

    public String getPreProjectName() {
        return this.preProjectName;
    }

}