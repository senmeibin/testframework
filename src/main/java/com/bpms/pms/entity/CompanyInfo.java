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
 * 公司资讯实体类
 */
@MappedSuperclass
public class CompanyInfo extends PmsBaseEntity {
    private static final long serialVersionUID = -20180428060243553L;

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
    @Column(name = "company_info_cd", length = 8, nullable = false)
    private String companyInfoCd;

    /**
     * 类别
     */
    @Transient
    private String companyInfoName;

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
     * 负责人
     */
    @Column(name = "charge_user_uid", length = 32)
    private String chargeUserUid;

    /**
     * 负责人
     */
    @Transient
    private String chargeUserName;

    /**
     * 负责部门
     */
    @Column(name = "charge_dept_uid", length = 32)
    private String chargeDeptUid;

    /**
     * 负责部门
     */
    @Transient
    private String chargeDeptName;

    public CompanyInfo() {
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

    public void setCompanyInfoCd(String companyInfoCd) {
        this.companyInfoCd = companyInfoCd;
    }

    public void setCompanyInfoName(String companyInfoName) {
        this.companyInfoName = companyInfoName;
    }

    public String getCompanyInfoCd() {
        return this.companyInfoCd;
    }

    public String getCompanyInfoName() {
        return this.companyInfoName;
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

    public void setChargeUserUid(String chargeUserUid) {
        this.chargeUserUid = chargeUserUid;
    }

    public void setChargeUserName(String chargeUserName) {
        this.chargeUserName = chargeUserName;
    }

    public String getChargeUserUid() {
        return this.chargeUserUid;
    }

    public String getChargeUserName() {
        return this.chargeUserName;
    }

    public void setChargeDeptUid(String chargeDeptUid) {
        this.chargeDeptUid = chargeDeptUid;
    }

    public void setChargeDeptName(String chargeDeptName) {
        this.chargeDeptName = chargeDeptName;
    }

    public String getChargeDeptUid() {
        return this.chargeDeptUid;
    }

    public String getChargeDeptName() {
        return this.chargeDeptName;
    }

}