package com.bpms.sys.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 管辖部门实体类
 */
@MappedSuperclass
public class DeptManage extends SysBaseEntity {
    private static final long serialVersionUID = -20161129144250347L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 部门UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择部门。")
    private String deptUid;

    /**
     * 部门
     */
    @Transient
    private String deptName;

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

    public DeptManage() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getDeptUid() {
        return this.deptUid;
    }

    public void setDeptUid(String deptUid) {
        this.deptUid = deptUid;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

}