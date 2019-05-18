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
 * 市场调研实体类
 */
@MappedSuperclass
public class ResearchInfo extends PmsBaseEntity {
    private static final long serialVersionUID = -20180501150041378L;

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
     * 类别
     */
    @Length(max = 8, message = "请在类别中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入类别。")
    @Column(name = "research_info_cd", length = 8, nullable = false)
    private String researchInfoCd;

    /**
     * 类别
     */
    @Transient
    private String researchInfoName;

    /**
     * 标题
     */
    @Length(max = 32, message = "请在标题中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入标题。")
    @Column(name = "title", length = 32, nullable = false)
    private String title;

    /**
     * 内容
     */ 
    @Column(name = "content")
    private String content;

    /**
     * 有效开始日期
     */
    @Column(name = "effect_start_date", nullable = false)
    @NotNull(message = "请输入有效开始日期。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date effectStartDate;

    /**
     * 有效终了日期
     */
    @Column(name = "effect_end_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date effectEndDate;

    /**
     * 流程节点
     */
    @Length(max = 32, message = "请在流程节点中输入[0-32]位以内的文字。")
    @Column(name = "workflow_node", length = 32)
    private String workflowNode;

    /**
     * 评论次数
     */
    @Range(min = 0L, max = 99999999L, message = "请在评论次数中输入[0-99999999]范围内的数值。")
    @Column(name = "comment_count")
    private Integer commentCount = 0;

    /**
     * 项目UID
     */
    @Column(name = "project_uid", length = 32)
    private String projectUid;

    /**
     * 项目
     */
    @Transient
    private String projectName;

    public ResearchInfo() {
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

    public void setResearchInfoCd(String researchInfoCd) {
        this.researchInfoCd = researchInfoCd;
    }

    public void setResearchInfoName(String researchInfoName) {
        this.researchInfoName = researchInfoName;
    }

    public String getResearchInfoCd() {
        return this.researchInfoCd;
    }

    public String getResearchInfoName() {
        return this.researchInfoName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setEffectStartDate(Date effectStartDate) {
        this.effectStartDate = effectStartDate;
    }

    public Date getEffectStartDate() {
        return this.effectStartDate;
    }

    public void setEffectEndDate(Date effectEndDate) {
        this.effectEndDate = effectEndDate;
    }

    public Date getEffectEndDate() {
        return this.effectEndDate;
    }

    public void setWorkflowNode(String workflowNode) {
        this.workflowNode = workflowNode;
    }

    public String getWorkflowNode() {
        return this.workflowNode;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getCommentCount() {
        return this.commentCount;
    }

    public void setProjectUid(String projectUid) {
        this.projectUid = projectUid;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectUid() {
        return this.projectUid;
    }

    public String getProjectName() {
        return this.projectName;
    }

}