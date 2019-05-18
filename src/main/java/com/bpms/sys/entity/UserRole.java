package com.bpms.sys.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 用户角色实体类
 */
@MappedSuperclass
public class UserRole extends SysBaseEntity {
    private static final long serialVersionUID = -20160420132118924L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 用户UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择用户。")
    private String userUid;

    /**
     * 用户
     */
    @Transient
    private String userName;

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

    public UserRole() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getUserUid() {
        return this.userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

}