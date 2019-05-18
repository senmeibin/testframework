package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 招商客户联络情况实体类
 */
@MappedSuperclass
public class PotentialCustomerContact extends DemoBaseEntity {
    private static final long serialVersionUID = -20170420163649465L;

    /**
     * 客户
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择客户。")
    private String companyUid;

    /**
     * 客户
     */
    @Transient
    private String companyName;

    /**
     * 联络人（职位）
     */
    @Length(max = 32, message = "请在联络人（职位）中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactPerson;

    /**
     * 联络方式
     */
    @Length(max = 32, message = "请在联络方式中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactInfo;

    /**
     * 填表人
     */
    @Column(length = 32)
    private String fillFormUserUid;

    /**
     * 填表人
     */
    @Transient
    private String fillFormUserName;

    /**
     * 项目简介
     */
    @Length(max = 512, message = "请在项目简介中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String projectDescription;

    /**
     * 公司需求
     */
    @Length(max = 512, message = "请在公司需求中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String companyDemand;

    /**
     * 建议及判断
     */
    @Length(max = 512, message = "请在建议及判断中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String recommendationsJudgments;

    /**
     * 项目时间节点规划
     */
    @Length(max = 512, message = "请在项目时间节点规划中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String projectTimeNode;

    /**
     * 计划
     */
    @Length(max = 512, message = "请在计划中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String plan;

    /**
     * 联络时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date contactTime;

    /**
     * 结果
     */
    @Length(max = 512, message = "请在结果中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String result;

    public PotentialCustomerContact() {
    }

    public String getCompanyUid() {
        return this.companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactInfo() {
        return this.contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getFillFormUserUid() {
        return fillFormUserUid;
    }

    public void setFillFormUserUid(String fillFormUserUid) {
        this.fillFormUserUid = fillFormUserUid;
    }

    public String getFillFormUserName() {
        return fillFormUserName;
    }

    public void setFillFormUserName(String fillFormUserName) {
        this.fillFormUserName = fillFormUserName;
    }

    public String getProjectDescription() {
        return this.projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getCompanyDemand() {
        return this.companyDemand;
    }

    public void setCompanyDemand(String companyDemand) {
        this.companyDemand = companyDemand;
    }

    public String getRecommendationsJudgments() {
        return this.recommendationsJudgments;
    }

    public void setRecommendationsJudgments(String recommendationsJudgments) {
        this.recommendationsJudgments = recommendationsJudgments;
    }

    public String getProjectTimeNode() {
        return this.projectTimeNode;
    }

    public void setProjectTimeNode(String projectTimeNode) {
        this.projectTimeNode = projectTimeNode;
    }

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Date getContactTime() {
        return this.contactTime;
    }

    public void setContactTime(Date contactTime) {
        this.contactTime = contactTime;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}