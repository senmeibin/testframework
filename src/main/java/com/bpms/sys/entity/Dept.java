package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 部门实体类
 */
@MappedSuperclass
public class Dept extends SysBaseEntity {
    private static final long serialVersionUID = -20160407171803811L;

    /**
     * 企业UID
     */
    @Column(name = "enterprise_uid", length = 8)
    private Integer enterpriseUid;

    /**
     * 企业
     */
    @Transient
    private String enterpriseName;

    /**
     * 部门名称
     */
    @Length(max = 64, message = "请在部门名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入部门名称。")
    @Column(name = "dept_name", length = 64, nullable = false)
    private String deptName;

    /**
     * 部门编号
     */
    @Length(max = 16, message = "请在部门编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入部门编号。")
    @Column(name = "dept_code", length = 16, nullable = false)
    private String deptCode;

    /**
     * 部门别名
     */
    @Length(max = 32, message = "请在部门别名中输入[0-32]位以内的文字。")
    @Column(name = "dept_alias_name", length = 32)
    private String deptAliasName;

    /**
     * 部门全称
     */
    @Length(max = 128, message = "请在部门全称中输入[0-128]位以内的文字。")
    @Column(name = "dept_full_name", length = 128)
    private String deptFullName;

    /**
     * 父级部门
     */
    @Column(name = "parent_dept_uid", length = 32)
    private String parentDeptUid;

    /**
     * 父级部门
     */
    @Transient
    private String parentDeptName;

    /**
     * 部门类别
     */
    @Range(min = 0L, max = 99999999L, message = "请在部门类别中输入[0-99999999]范围内的数值。")
    @Column(name = "dept_class")
    private Integer deptClass = 3;

    /**
     * 部门分类
     */
    @Length(max = 8, message = "请在部门分类中输入[0-8]位以内的文字。")
    @Column(name = "dept_category_cd", length = 8)
    private String deptCategoryCd;

    /**
     * 部门分类
     */
    @Transient
    private String deptCategoryName;

    /**
     * 所属分公司
     */
    @Column(name = "company_dept_uid", length = 32)
    private String companyDeptUid;

    /**
     * 所属分公司
     */
    @Transient
    private String companyDeptName;

    /**
     * 表示顺序
     */
    @Range(min = 0L, max = 99999, message = "请在表示顺序中输入[0-99999999]范围内的数值。")
    @Column(name = "disp_seq")
    private Integer dispSeq;

    public Dept() {
    }

    public Integer getEnterpriseUid() {
        return this.enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getEnterpriseName() {
        return this.enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptAliasName() {
        return this.deptAliasName;
    }

    public void setDeptAliasName(String deptAliasName) {
        this.deptAliasName = deptAliasName;
    }

    public String getDeptFullName() {
        return this.deptFullName;
    }

    public void setDeptFullName(String deptFullName) {
        this.deptFullName = deptFullName;
    }

    public String getParentDeptUid() {
        return this.parentDeptUid;
    }

    public void setParentDeptUid(String parentDeptUid) {
        this.parentDeptUid = parentDeptUid;
    }

    public String getParentDeptName() {
        return this.parentDeptName;
    }

    public void setParentDeptName(String parentDeptName) {
        this.parentDeptName = parentDeptName;
    }

    public Integer getDeptClass() {
        return this.deptClass;
    }

    public void setDeptClass(Integer deptClass) {
        this.deptClass = deptClass;
    }

    public String getDeptCategoryCd() {
        return this.deptCategoryCd;
    }

    public void setDeptCategoryCd(String deptCategoryCd) {
        this.deptCategoryCd = deptCategoryCd;
    }

    public String getDeptCategoryName() {
        return this.deptCategoryName;
    }

    public void setDeptCategoryName(String deptCategoryName) {
        this.deptCategoryName = deptCategoryName;
    }

    public String getCompanyDeptUid() {
        return this.companyDeptUid;
    }

    public void setCompanyDeptUid(String companyDeptUid) {
        this.companyDeptUid = companyDeptUid;
    }

    public String getCompanyDeptName() {
        return this.companyDeptName;
    }

    public void setCompanyDeptName(String companyDeptName) {
        this.companyDeptName = companyDeptName;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

}