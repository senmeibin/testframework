package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.Employee;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "demo_employee")
public class EmployeeExt extends Employee {
    private static final long serialVersionUID = -20170510135355418L;
    /**
     * 大区名称
     */
    @Transient
    private String areaUid;

    /**
     * 大区名称
     */
    @Transient
    private String areaName;

    /**
     * 分公司
     */
    @Transient
    private String companyUid;

    /**
     * 分公司
     */
    @Transient
    private String companyName;

    /**
     * 部门全称
     */
    @Transient
    private String deptFullName;

    /**
     * 办公地点
     */
    @Transient
    private String locationFullName;

    /**
     * 司龄
     */
    @Transient
    private Integer seniority;

    /**
     * 年龄
     */
    @Transient
    private Integer age;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeptFullName() {
        return deptFullName;
    }

    public void setDeptFullName(String deptFullName) {
        this.deptFullName = deptFullName;
    }

    public String getLocationFullName() {
        return locationFullName;
    }

    public void setLocationFullName(String locationFullName) {
        this.locationFullName = locationFullName;
    }

    public String getAreaUid() {
        return areaUid;
    }

    public void setAreaUid(String areaUid) {
        this.areaUid = areaUid;
    }

    public String getCompanyUid() {
        return companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public Integer getSeniority() {
        return seniority;
    }

    public void setSeniority(Integer seniority) {
        this.seniority = seniority;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}