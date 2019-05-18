package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 地铁站信息实体类
 */
@MappedSuperclass
public class SubwayStation extends CmnBaseEntity {
    private static final long serialVersionUID = -20170724144814681L;

    /**
     * 地铁线路UID
     */
    @Column(name = "subway_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择地铁线路。")
    private String subwayUid;

    /**
     * 地铁线路
     */
    @Transient
    private String subwayNo;

    /**
     * 地铁站名
     */
    @Length(max = 64, message = "请在地铁站名中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入地铁站名。")
    @Column(name = "station_name", length = 64, nullable = false)
    private String stationName;

    /**
     * 商圈区域
     */
    @Length(max = 64, message = "请在商圈区域中输入[0-64]位以内的文字。")
    @Column(name = "circle_area", length = 64)
    private String circleArea;

    /**
     * 城市
     */
    @Transient
    private String cityName;

    /**
     * 省份
     */
    @Transient
    private String provinceName;

    /**
     * 表示顺序
     */
    @Range(min = 0, max = 99999999, message = "请在表示顺序中输入[0-99999999999]范围内的数值。")
    @NotNull(message = "请输入表示顺序。")
    @Column(nullable = false)
    private Integer dispSeq = 1;

    public SubwayStation() {
    }

    public String getSubwayUid() {
        return this.subwayUid;
    }

    public void setSubwayUid(String subwayUid) {
        this.subwayUid = subwayUid;
    }

    public String getSubwayNo() {
        return this.subwayNo;
    }

    public void setSubwayNo(String subwayNo) {
        this.subwayNo = subwayNo;
    }

    public String getStationName() {
        return this.stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCircleArea() {
        return this.circleArea;
    }

    public void setCircleArea(String circleArea) {
        this.circleArea = circleArea;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }
}