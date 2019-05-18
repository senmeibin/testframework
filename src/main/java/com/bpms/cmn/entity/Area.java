package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 大区信息实体类
 */
@MappedSuperclass
public class Area extends CmnBaseEntity {
    private static final long serialVersionUID = -20160413164502953L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 大区编号
     */
    @Length(max = 8, message = "请在大区编号中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入大区编号。")
    @Column(length = 8, nullable = false)
    private String areaCd;

    /**
     * 大区名称
     */
    @Length(max = 32, message = "请在大区名称中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入大区名称。")
    @Column(length = 32, nullable = false)
    private String areaName;

    /**
     * 大区映射关联
     */
    @Length(max = 32, message = "请在大区映射关联中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入大区映射关联。")
    @Column(length = 32)
    private String deptUid;

    /**
     * 大区映射关联
     */
    @Transient
    private String deptName;

    /**
     * 表示顺序
     */
    @Range(min = 0, max = 99999999, message = "请在表示顺序中输入[0-99999999999]范围内的数值。")
    @NotNull(message = "请输入表示顺序。")
    @Column(nullable = false)
    private Integer dispSeq = 1;

    public Area() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getAreaCd() {
        return this.areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
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