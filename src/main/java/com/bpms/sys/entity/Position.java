package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * 职位实体类
 */
@MappedSuperclass
public class Position extends SysBaseEntity {
    static final long serialVersionUID = -635945139330296943L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 职位名称
     */
    @Length(max = 32, message = "请在职位名称中输入0-32位以内的文字。")
    @NotEmpty(message = "请输入职位名称。")
    @Column(length = 32, nullable = false)
    private String positionName;

    /**
     * 职位编号
     */
    @Length(max = 16, message = "请在职位编号中输入0-16位以内的文字。")
    @Column(length = 16)
    private String positionCode;

    /**
     * 职位等级
     */
    @Column(nullable = false)
    @NotNull(message = "请输入职位等级。")
    private Integer dispSeq;

    public Position() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionCode() {
        return this.positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

}