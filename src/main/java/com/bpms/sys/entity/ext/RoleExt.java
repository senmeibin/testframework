package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Role;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "sys_role")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleExt extends Role {
    private static final long serialVersionUID = -20160403134345529L;

    /**
     * 应用名称
     */
    @Transient
    private String appName;
    /**
     * 角色权限
     */
    @Transient
    private List<PermissionExt> permissionList;

    /**
     * 父级角色UID
     */
    @Transient
    private String parentRoleUid;

    /**
     * 父级角色名称
     */
    @Transient
    private String parentRoleName;

    /**
     * 具有角色的用户列表
     */
    @Transient
    private List<UserRoleExt> userRoleList;

    /**
     * 具有角色的用户UID
     */
    @Transient
    private String userUids;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<PermissionExt> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<PermissionExt> permissionList) {
        this.permissionList = permissionList;
    }

    public String getParentRoleUid() {
        return parentRoleUid;
    }

    public void setParentRoleUid(String parentRoleUid) {
        this.parentRoleUid = parentRoleUid;
    }

    public String getParentRoleName() {
        return parentRoleName;
    }

    public void setParentRoleName(String parentRoleName) {
        this.parentRoleName = parentRoleName;
    }

    public List<UserRoleExt> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRoleExt> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String getUserUids() {
        return userUids;
    }

    public void setUserUids(String userUids) {
        this.userUids = userUids;
    }
}