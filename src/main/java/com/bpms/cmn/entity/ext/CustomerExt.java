package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.Customer;
import com.bpms.sys.entity.ext.UserExt;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

//注：防止共通模块与业务模块Entity同名问题，在共通模块的Entity中加入带前缀的name属性
@Entity(name = "CmnCustomer")
@Table(name = "cmn_customer")
public class CustomerExt extends Customer {
    private static final long serialVersionUID = -20160524114526761L;
    /**
     * 合同新老客户
     */
    @Transient
    private String oldNewCustomerCd;
    /**
     * 所属行业名称
     */
    @Transient
    private String industryName;

    /**
     * 所属大区
     */
    @Transient
    private String areaName;

    /**
     * 省份
     */
    @Transient
    private String provinceName;

    /**
     * 城市
     */
    @Transient
    private String cityName;

    /**
     * 区域
     */
    @Transient
    private String regionName;

    /**
     * 视频链接
     */
    @Transient
    private String videoUrl;

    /**
     * 经度
     */
    @Transient
    private String lng;

    /**
     * 纬度
     */
    @Transient
    private String lat;

    /**
     * 可以查询客户的用户UID
     */
    @Transient
    private String userUids;

    /**
     * 可以查询客户的用户列表
     */
    @Transient
    private List<UserExt> userList;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOldNewCustomerCd() {
        return oldNewCustomerCd;
    }

    public void setOldNewCustomerCd(String oldNewCustomerCd) {
        this.oldNewCustomerCd = oldNewCustomerCd;
    }

    @Override
    public String getIndustryName() {
        return industryName;
    }

    @Override
    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getUserUids() {
        return userUids;
    }

    public void setUserUids(String userUids) {
        this.userUids = userUids;
    }

    public List<UserExt> getUserList() {
        return userList;
    }

    public void setUserList(List<UserExt> userList) {
        this.userList = userList;
    }
}