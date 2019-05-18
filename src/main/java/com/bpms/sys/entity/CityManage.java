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
 * 管辖城市实体类
 */
@MappedSuperclass
public class CityManage extends SysBaseEntity {
    private static final long serialVersionUID = -20180111171008129L;

    /**
     * 城市UID
     */
    @Column(name = "city_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择城市。")
    private String cityUid;

    /**
     * 城市
     */
    @Transient
    private String cityName;

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

    public CityManage() {
    }

    public void setCityUid(String cityUid) {
        this.cityUid = cityUid;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityUid() {
        return this.cityUid;
    }

    public String getCityName() {
        return this.cityName;
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