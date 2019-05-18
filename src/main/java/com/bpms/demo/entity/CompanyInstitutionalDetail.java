package com.bpms.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 分支机构实体类
 */
@MappedSuperclass
public class CompanyInstitutionalDetail extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511162801775L;
    /**
     * 机构UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择机构。")
    private String institutionalUid;

    /**
     * 机构
     */
    @Transient
    private String institutionalName;

    /**
     * 名称
     */
    @Length(max = 32, message = "请在名称中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String name;

    /**
     * 地址
     */
    @Length(max = 64, message = "请在地址中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String address;

    public CompanyInstitutionalDetail() {
    }

    public String getInstitutionalUid() {
        return this.institutionalUid;
    }

    public void setInstitutionalUid(String institutionalUid) {
        this.institutionalUid = institutionalUid;
    }

    public String getInstitutionalName() {
        return this.institutionalName;
    }

    public void setInstitutionalName(String institutionalName) {
        this.institutionalName = institutionalName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}