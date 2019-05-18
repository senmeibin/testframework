package com.bpms.cmn.entity;

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
import com.bpms.cmn.entity.CmnBaseEntity;

/**
 * 客户用户所属实体类
 */
@MappedSuperclass
public class CustomerUser extends CmnBaseEntity {
    private static final long serialVersionUID = -20180109152220665L;

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
     * 客户UID
     */
    @Column(name = "customer_uid", length = 32)
    private String customerUid;

    /**
     * 客户
     */
    @Transient
    private String customerName;

    /**
     * 用户UID
     */
    @Column(name = "user_uid", length = 32)
    private String userUid;

    /**
     * 用户
     */
    @Transient
    private String userName;

    public CustomerUser() {
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

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerUid() {
        return this.customerUid;
    }

    public String getCustomerName() {
        return this.customerName;
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