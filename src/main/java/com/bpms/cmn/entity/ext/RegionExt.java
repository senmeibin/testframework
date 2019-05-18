package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.Region;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

//注：防止共通模块与业务模块Entity同名问题，在共通模块的Entity中加入带前缀的name属性
@Entity(name = "CmnRegion")
@Table(name = "cmn_region")
public class RegionExt extends Region {
    private static final long serialVersionUID = -20160810090703332L;

    /**
     * 区域所属省份uid
     */
    @Transient
    private String provinceUid;

    /**
     * 区域所属省份名称
     */
    @Transient
    private String provinceName;

    /**
     * 区域所属城市uid
     */
    @Transient
    private String cityUid;

    /**
     * 区域所属城市名称
     */
    @Transient
    private String cityName;

    /**
     * 字典关键词
     */
    @Transient
    private String regionFullName;

    @Transient
    private String regionUid;

    public String getProvinceUid() {
        return provinceUid;
    }

    public void setProvinceUid(String provinceUid) {
        this.provinceUid = provinceUid;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityUid() {
        return cityUid;
    }

    public void setCityUid(String cityUid) {
        this.cityUid = cityUid;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionFullName() {
        return regionFullName;
    }

    public void setRegionFullName(String regionFullName) {
        this.regionFullName = regionFullName;
    }

    public String getRegionUid() {
        return regionUid;
    }

    public void setRegionUid(String regionUid) {
        this.regionUid = regionUid;
    }
}