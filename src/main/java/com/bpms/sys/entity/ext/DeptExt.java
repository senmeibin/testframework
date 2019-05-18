package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Dept;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigInteger;

@Entity
@Table(name = "sys_dept")
public class DeptExt extends Dept {
    private static final long serialVersionUID = -20160407092534781L;

    /**
     * 所属大区
     */
    @Transient
    private String areaDeptUid;

    /**
     * 部门层级
     */
    @Transient
    private String deptClassName;

    @Transient
    private BigInteger parentDeptClass;

    /**
     * 树形节点 人形图标（true：显示人型，false：不显示人型）
     */
    @Transient
    private boolean isPerson = false;

    public String getAreaDeptUid() {
        return areaDeptUid;
    }

    public void setAreaDeptUid(String areaDeptUid) {
        this.areaDeptUid = areaDeptUid;
    }

    public String getDeptClassName() {
        return deptClassName;
    }

    public void setDeptClassName(String deptClassName) {
        this.deptClassName = deptClassName;
    }

    public BigInteger getParentDeptClass() {
        return parentDeptClass;
    }

    public void setParentDeptClass(BigInteger parentDeptClass) {
        this.parentDeptClass = parentDeptClass;
    }

    public boolean getIsPerson() {
        return this.isPerson;
    }

    public void setIsPerson(boolean isPerson) {
        this.isPerson = isPerson;
    }
}