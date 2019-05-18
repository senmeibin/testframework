package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 常见问题实体类
 */
@MappedSuperclass
public class Faq extends CmnBaseEntity {
    private static final long serialVersionUID = -20161121104221464L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 应用编号
     */
    @Length(max = 16, message = "请在应用编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入应用编号。")
    @Column(length = 16, nullable = false)
    private String appCode;

    /**
     * 应用名称
     */
    @Transient
    private String appName;

    /**
     * 模块名称
     */
    @Length(max = 64, message = "请在模块名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入模块名称。")
    @Column(length = 64, nullable = false)
    private String moduleName;

    /**
     * 问题描述
     */
    @Length(max = 256, message = "请在问题描述中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入问题描述。")
    @Column(length = 256, nullable = false)
    private String question;

    /**
     * 问题详细
     */
    @Column
    private String questionDesc;

    /**
     * 问题解答
     */
    @Length(max = 256, message = "请在问题解答中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入问题解答。")
    @Column(length = 256, nullable = false)
    private String answer;

    /**
     * 操作说明
     */
    @Column
    private String answerDesc;

    /**
     * 适用角色
     */
    @Length(max = 256, message = "请在适用角色中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入适用角色。")
    @Column(length = 256, nullable = false)
    private String applicableRole;

    /**
     * 责任人
     */
    @Length(max = 256, message = "请在责任人中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入责任人。")
    @Column(length = 256, nullable = false)
    private String responsibleRole;

    public Faq() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionDesc() {
        return this.questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerDesc() {
        return this.answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public String getApplicableRole() {
        return this.applicableRole;
    }

    public void setApplicableRole(String applicableRole) {
        this.applicableRole = applicableRole;
    }

    public String getResponsibleRole() {
        return this.responsibleRole;
    }

    public void setResponsibleRole(String responsibleRole) {
        this.responsibleRole = responsibleRole;
    }

}