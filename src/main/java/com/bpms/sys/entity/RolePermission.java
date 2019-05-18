package com.bpms.sys.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 角色权限实体类
 */
@MappedSuperclass
public class RolePermission extends SysBaseEntity {
    private static final long serialVersionUID = -20160420122128185L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 角色UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择角色。")
    private String roleUid;

    /**
     * 角色
     */
    @Transient
    private String roleName;

    /**
     * 权限UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择权限。")
    private String permissionUid;

    /**
     * 权限
     */
    @Transient
    private String permissionName;

    public RolePermission() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getRoleUid() {
        return this.roleUid;
    }

    public void setRoleUid(String roleUid) {
        this.roleUid = roleUid;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPermissionUid() {
        return this.permissionUid;
    }

    public void setPermissionUid(String permissionUid) {
        this.permissionUid = permissionUid;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

}