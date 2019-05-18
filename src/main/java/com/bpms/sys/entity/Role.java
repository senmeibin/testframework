package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 角色实体类
 */
@MappedSuperclass
public class Role extends SysBaseEntity {
    private static final long serialVersionUID = -20160403134345429L;

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
     * 角色名称
     */
    @Length(max = 32, message = "请在角色名称中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入角色名称。")
    @Column(length = 32, nullable = false)
    private String roleName;

    /**
     * 角色编号
     */
    @Length(max = 32, message = "请在角色编号中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入角色编号。")
    @Column(length = 32, nullable = false)
    private String roleCode;

    /**
     * 角色描述
     */
    @Length(max = 128, message = "请在角色描述中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String description;

    /**
     * 显示顺序
     */
    @Range(min = 0, max = 9999, message = "请在显示顺序中输入[0-9999]范围内的数值。")
    @Column
    private Integer dispSeq;

    /**
     * 系统注册
     */
    @Range(min = 0, max = 99999, message = "请在系统注册中输入[0-99999]范围内的数值。")
    @Column
    private Integer regSystem = 0;

    public Role() {
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

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    public Integer getRegSystem() {
        return regSystem;
    }

    public void setRegSystem(Integer regSystem) {
        this.regSystem = regSystem;
    }
}