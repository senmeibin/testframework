package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 地铁线路信息实体类
 */
@MappedSuperclass
public class Subway extends CmnBaseEntity {
    private static final long serialVersionUID = -20170724144033413L;

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
     * 省份
     */
    @Transient
    private String provinceName;

    /**
     * 线路号
     */
    @Length(max = 32, message = "请在线路号中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入线路号。")
    @Column(name = "subway_no", length = 32, nullable = false)
    private String subwayNo;

    /**
     * 表示顺序
     */
    @Range(min = 0, max = 99999999, message = "请在表示顺序中输入[0-99999999999]范围内的数值。")
    @NotNull(message = "请输入表示顺序。")
    @Column(nullable = false)
    private Integer dispSeq = 1;

    public Subway() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityUid() {
        return this.cityUid;
    }

    public void setCityUid(String cityUid) {
        this.cityUid = cityUid;
    }

    public String getSubwayNo() {
        return this.subwayNo;
    }

    public void setSubwayNo(String subwayNo) {
        this.subwayNo = subwayNo;
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