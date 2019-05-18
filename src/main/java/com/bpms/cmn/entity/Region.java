package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * 地区信息实体类
 */
@MappedSuperclass
public class Region extends CmnBaseEntity {
    private static final long serialVersionUID = -20160810090703195L;

    /**
     * 区域名称
     */
    @Length(max = 60, message = "请在区域名称中输入[0-60]位以内的文字。")
    @Column(length = 60)
    private String regionName;

    /**
     * 父节点
     */
    @Column(length = 32)
    private String parentUid;

    /**
     * 区域路径
     */
    @Length(max = 256, message = "请在区域路径中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String regionPath;

    /**
     * 区域层级
     */
    @Range(min = 0, max = 99999999, message = "请在区域层级中输入[0-99999999]范围内的数值。")
    @Column
    private Integer regionGrade;

    /**
     * 表示顺序
     */
    @Range(min = 0, max = 99999999, message = "请在表示顺序中输入[0-99999999]范围内的数值。")
    @NotNull(message = "请输入表示顺序。")
    @Column(nullable = false)
    private Integer dispSeq = 1;

    public Region() {
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getParentUid() {
        return this.parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public String getRegionPath() {
        return this.regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public Integer getRegionGrade() {
        return this.regionGrade;
    }

    public void setRegionGrade(Integer regionGrade) {
        this.regionGrade = regionGrade;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

}