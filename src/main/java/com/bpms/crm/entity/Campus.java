package com.bpms.crm.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 校区信息实体类
 */
@MappedSuperclass
public class Campus extends CrmBaseEntity {
    private static final long serialVersionUID = -20180319191308137L;

    /**
     * 部门UID
     */
    @Column(name = "dept_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择部门。")
    private String deptUid;

    /**
     * 部门
     */
    @Transient
    private String deptName;

    /**
     * 校区名称
     */
    @Length(max = 64, message = "请在校区名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入校区名称。")
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    /**
     * 校区代号
     */
    @Length(max = 8, message = "请在校区代号中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入校区代号。")
    @Column(name = "code", length = 8, nullable = false)
    private String code;

    /**
     * 校区分类
     */
    @Length(max = 8, message = "请在校区分类中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入校区分类。")
    @Column(name = "category_cd", length = 8, nullable = false)
    private String categoryCd = "01";

    /**
     * 校区分类
     */
    @Transient
    private String categoryName;

    /**
     * 校区介绍
     */
    @Column(name = "introduction", nullable = false)
    @NotNull(message = "请输入校区介绍。")
    private String introduction;

    /**
     * 校区省
     */
    @Column(name = "province_uid", length = 32)
    private String provinceUid;

    /**
     * 校区省
     */
    @Transient
    private String provinceName;

    /**
     * 校区市
     */
    @Column(name = "city_uid", length = 32)
    private String cityUid;

    /**
     * 校区市
     */
    @Transient
    private String cityName;

    /**
     * 校区区
     */
    @Column(name = "region_uid", length = 32)
    private String regionUid;

    /**
     * 校区区
     */
    @Transient
    private String regionName;

    /**
     * 校区地址
     */
    @Length(max = 256, message = "请在校区地址中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入校区地址。")
    @Column(name = "address", length = 256, nullable = false)
    private String address;

    /**
     * 邮政编码
     */
    @Length(max = 6, message = "请在邮政编码中输入[0-6]位以内的文字。")
    @Column(name = "zip_code", length = 6)
    private String zipCode;

    /**
     * 联系电话
     */
    @Length(max = 32, message = "请在联系电话中输入[0-32]位以内的文字。")
    @Column(name = "telephone", length = 32)
    private String telephone;

    /**
     * 传真
     */
    @Length(max = 32, message = "请在传真中输入[0-32]位以内的文字。")
    @Column(name = "fax", length = 32)
    private String fax;

    /**
     * 邮箱
     */
    @Length(max = 64, message = "请在邮箱中输入[0-64]位以内的文字。")
    @Column(name = "email", length = 64)
    private String email;

    /**
     * 门店店长
     */
    @Column(name = "manager_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择门店店长。")
    private String managerUserUid;

    /**
     * 门店店长
     */
    @Transient
    private String managerUserName;

    /**
     * 门店副店长
     */
    @Column(name = "assistant_manager_user_uid", length = 32)
    private String assistantManagerUserUid;

    /**
     * 门店副店长
     */
    @Transient
    private String assistantManagerUserName;

    /**
     * 门店助理
     */
    @Column(name = "assistant_user_uid", length = 32)
    private String assistantUserUid;

    /**
     * 门店助理
     */
    @Transient
    private String assistantUserName;

    /**
     * 表示顺序
     */
    @Range(min = 0L, max = 99999999L, message = "请在表示顺序中输入[0-99999999]范围内的数值。")
    @Column(name = "disp_seq")
    private Integer dispSeq = 1;

    public Campus() {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategoryCd() {
        return this.categoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        this.categoryCd = categoryCd;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getProvinceUid() {
        return this.provinceUid;
    }

    public void setProvinceUid(String provinceUid) {
        this.provinceUid = provinceUid;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityUid() {
        return this.cityUid;
    }

    public void setCityUid(String cityUid) {
        this.cityUid = cityUid;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionUid() {
        return this.regionUid;
    }

    public void setRegionUid(String regionUid) {
        this.regionUid = regionUid;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManagerUserUid() {
        return this.managerUserUid;
    }

    public void setManagerUserUid(String managerUserUid) {
        this.managerUserUid = managerUserUid;
    }

    public String getManagerUserName() {
        return this.managerUserName;
    }

    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
    }

    public String getAssistantManagerUserUid() {
        return this.assistantManagerUserUid;
    }

    public void setAssistantManagerUserUid(String assistantManagerUserUid) {
        this.assistantManagerUserUid = assistantManagerUserUid;
    }

    public String getAssistantManagerUserName() {
        return this.assistantManagerUserName;
    }

    public void setAssistantManagerUserName(String assistantManagerUserName) {
        this.assistantManagerUserName = assistantManagerUserName;
    }

    public String getAssistantUserUid() {
        return this.assistantUserUid;
    }

    public void setAssistantUserUid(String assistantUserUid) {
        this.assistantUserUid = assistantUserUid;
    }

    public String getAssistantUserName() {
        return this.assistantUserName;
    }

    public void setAssistantUserName(String assistantUserName) {
        this.assistantUserName = assistantUserName;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

}