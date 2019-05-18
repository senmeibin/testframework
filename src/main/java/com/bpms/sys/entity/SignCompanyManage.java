package com.bpms.sys.entity;

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
import com.bpms.sys.entity.SysBaseEntity;

/**
 * 管辖签单公司实体类
 */
@MappedSuperclass
public class SignCompanyManage extends SysBaseEntity {
    private static final long serialVersionUID = -20180206091011712L;

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
     * 签单公司UID
     */
    @Column(name = "sign_company_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择签单公司。")
    private String signCompanyUid;

    /**
     * 签单公司
     */
    @Transient
    private String signCompanyName;

    /**
     * 用户UID
     */
    @Column(name = "user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择用户。")
    private String userUid;

    /**
     * 用户
     */
    @Transient
    private String userName;

    public SignCompanyManage() {
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

    public void setSignCompanyUid(String signCompanyUid) {
        this.signCompanyUid = signCompanyUid;
    }

    public void setSignCompanyName(String signCompanyName) {
        this.signCompanyName = signCompanyName;
    }

    public String getSignCompanyUid() {
        return this.signCompanyUid;
    }

    public String getSignCompanyName() {
        return this.signCompanyName;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUid() {
        return this.userUid;
    }

    public String getUserName() {
        return this.userName;
    }

}