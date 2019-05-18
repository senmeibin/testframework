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
 * 市场调研评论实体类
 */
@MappedSuperclass
public class ResearchInfoComment extends PmsBaseEntity {
    private static final long serialVersionUID = -20180502191820708L;

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
     * 市场评论UID
     */
    @Column(name = "research_info_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择市场评论。")
    private String researchInfoUid;

    /**
     * 市场评论
     */
    @Transient
    private String researchInfoName;

    /**
     * 内容
     */ 
    @Column(name = "content")
    private String content;

    public ResearchInfoComment() {
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

    public void setResearchInfoUid(String researchInfoUid) {
        this.researchInfoUid = researchInfoUid;
    }

    public void setResearchInfoName(String researchInfoName) {
        this.researchInfoName = researchInfoName;
    }

    public String getResearchInfoUid() {
        return this.researchInfoUid;
    }

    public String getResearchInfoName() {
        return this.researchInfoName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

}