package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 来往单位实体类
 */
@MappedSuperclass
public class Company extends SysBaseEntity {
    private static final long serialVersionUID = -20160405144317217L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 单位编号
     */
    @Length(max = 32, message = "请在单位编号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String code;

    /**
     * 单位名称
     */
    @Length(max = 256, message = "请在单位名称中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入单位名称。")
    @Column(length = 256, nullable = false)
    private String name;

    /**
     * 简称
     */
    @Length(max = 32, message = "请在简称中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String abbreviation;

    /**
     * 性质
     */
    @Length(max = 8, message = "请在性质中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String propertyCd;

    /**
     * 性质
     */
    @Transient
    private String propertyName;

    /**
     * 规模
     */
    @Length(max = 8, message = "请在规模中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String scaleCd;

    /**
     * 规模
     */
    @Transient
    private String scaleName;

    /**
     * 地址
     */
    @Length(max = 256, message = "请在地址中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String address;

    /**
     * 邮编
     */
    @Length(max = 6, message = "请在邮编中输入[0-6]位以内的文字。")
    @Column(length = 6)
    private String zipcode;

    /**
     * 电话
     */
    @Length(max = 32, message = "请在电话中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入电话。")
    @Column(length = 32, nullable = false)
    private String telephone;

    /**
     * 传真
     */
    @Length(max = 32, message = "请在传真中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String fax;

    /**
     * 邮箱
     */
    @Length(max = 64, message = "请在邮箱中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String email;

    /**
     * 网址
     */
    @Length(max = 256, message = "请在网址中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String url;

    /**
     * 所属部门
     */
    @Column(length = 32)
    private String belongDeptUid;

    /**
     * 所属部门
     */
    @Transient
    private String belongDeptName;

    /**
     * 所属业务员
     */
    @Column(length = 32)
    private String belongUserUid;

    /**
     * 所属业务员
     */
    @Transient
    private String belongUserName;

    /**
     * 联系人
     */
    @Length(max = 32, message = "请在联系人中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入联系人。")
    @Column(length = 32, nullable = false)
    private String contactName;

    /**
     * 附件关联ID
     */
    @Length(max = 64, message = "请在附件关联ID中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String fileRelationUid;

    public Company() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getPropertyCd() {
        return this.propertyCd;
    }

    public void setPropertyCd(String propertyCd) {
        this.propertyCd = propertyCd;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getScaleCd() {
        return this.scaleCd;
    }

    public void setScaleCd(String scaleCd) {
        this.scaleCd = scaleCd;
    }

    public String getScaleName() {
        return this.scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBelongDeptUid() {
        return this.belongDeptUid;
    }

    public void setBelongDeptUid(String belongDeptUid) {
        this.belongDeptUid = belongDeptUid;
    }

    public String getBelongDeptName() {
        return this.belongDeptName;
    }

    public void setBelongDeptName(String belongDeptName) {
        this.belongDeptName = belongDeptName;
    }

    public String getBelongUserUid() {
        return this.belongUserUid;
    }

    public void setBelongUserUid(String belongUserUid) {
        this.belongUserUid = belongUserUid;
    }

    public String getBelongUserName() {
        return this.belongUserName;
    }

    public void setBelongUserName(String belongUserName) {
        this.belongUserName = belongUserName;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getFileRelationUid() {
        return this.fileRelationUid;
    }

    public void setFileRelationUid(String fileRelationUid) {
        this.fileRelationUid = fileRelationUid;
    }

}