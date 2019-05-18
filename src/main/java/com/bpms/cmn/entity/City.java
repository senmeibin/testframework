package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 城市信息实体类
 */
@MappedSuperclass
public class City extends CmnBaseEntity {
    private static final long serialVersionUID = -20160413172542702L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 城市编号
     */
    @Length(max = 8, message = "请在城市编号中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String cityCd;

    /**
     * 城市名称
     */
    @Length(max = 32, message = "请在城市名称中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入城市名称。")
    @Column(length = 32, nullable = false)
    private String cityName;

    /**
     * 表示顺序
     */
    @Range(min = 0, max = 99999999, message = "请在表示顺序中输入[0-99999999]范围内的数值。")
    @Column
    private Integer dispSeq = 1;

    /**
     * 所属大区
     */
    @Column(length = 32)
    private String areaUid;

    /**
     * 所属大区
     */
    @Transient
    private String areaName;

    /**
     * 关联分公司
     */
    @Length(max = 32, message = "请在关联分公司中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String deptUid;

    /**
     * 关联分公司
     */
    @Transient
    private String deptName;

    /**
     * 上级城市
     */
    @Column(length = 32)
    private String parentCityUid;

    /**
     * 上级城市
     */
    @Transient
    private String parentCityName;

    public City() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getCityCd() {
        return this.cityCd;
    }

    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    public String getAreaUid() {
        return this.areaUid;
    }

    public void setAreaUid(String areaUid) {
        this.areaUid = areaUid;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getParentCityUid() {
        return this.parentCityUid;
    }

    public void setParentCityUid(String parentCityUid) {
        this.parentCityUid = parentCityUid;
    }

    public String getParentCityName() {
        return this.parentCityName;
    }

    public void setParentCityName(String parentCityName) {
        this.parentCityName = parentCityName;
    }

    public String getDeptUid() {
        return deptUid;
    }

    public void setDeptUid(String deptUid) {
        this.deptUid = deptUid;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}