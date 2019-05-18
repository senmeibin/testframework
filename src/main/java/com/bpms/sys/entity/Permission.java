package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 权限实体类
 */
@MappedSuperclass
public class Permission extends SysBaseEntity {
    private static final long serialVersionUID = -20160402225459341L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * APP编号
     */
    @Length(max = 16, message = "请在APP编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入APP编号。")
    @Column(length = 16, nullable = false)
    private String appCode;

    /**
     * 权限类型
     */
    @Length(max = 32, message = "请在权限类型中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入权限类型。")
    @Column(length = 32, nullable = false)
    private String typeCd;

    /**
     * 权限类型
     */
    @Transient
    private String typeName;

    /**
     * 权限名称
     */
    @Length(max = 32, message = "请在权限名称中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入权限名称。")
    @Column(length = 32, nullable = false)
    private String permissionName;

    /**
     * 域
     */
    @Length(max = 64, message = "请在域中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String domain = "*";

    /**
     * 行为集合
     */
    @Length(max = 64, message = "请在行为集合中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String actions = "*";

    /**
     * 目标集合
     */
    @Length(max = 64, message = "请在目标集合中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String targets = "*";

    public Permission() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getTypeCd() {
        return this.typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getActions() {
        return this.actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getTargets() {
        return this.targets;
    }

    public void setTargets(String targets) {
        this.targets = targets;
    }

}