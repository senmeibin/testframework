package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 联系人实体类
 */
@MappedSuperclass
public class Contact extends SysBaseEntity {
    private static final long serialVersionUID = -20160405164130021L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 来往单位UID
     */
    @Column(length = 50, nullable = false)
    @NotEmpty(message = "请选择来往单位。")
    private String companyUid;

    /**
     * 来往单位
     */
    @Transient
    private String companyName;

    /**
     * 姓名
     */
    @Length(max = 32, message = "请在姓名中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入姓名。")
    @Column(length = 32, nullable = false)
    private String name;

    /**
     * 部门
     */
    @Length(max = 64, message = "请在部门中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String department;

    /**
     * 职务
     */
    @Length(max = 32, message = "请在职务中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String post;

    /**
     * 主要职责
     */
    @Length(max = 64, message = "请在主要职责中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String responsibility;

    /**
     * 电话
     */
    @Length(max = 32, message = "请在电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
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
     * 手机
     */
    @Length(max = 32, message = "请在手机中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入手机。")
    @Column(length = 32, nullable = false)
    private String mobile;

    /**
     * 生日
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date birthday;

    /**
     * 家庭地址
     */
    @Length(max = 256, message = "请在家庭地址中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String homeAddress;

    /**
     * 家庭电话
     */
    @Length(max = 32, message = "请在家庭电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String homeTelephone;

    /**
     * 家庭邮编
     */
    @Length(max = 6, message = "请在家庭邮编中输入[0-6]位以内的文字。")
    @Column(length = 6)
    private String homeZipcode;

    /**
     * 爱好
     */
    @Length(max = 256, message = "请在爱好中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String interest;

    /**
     * 是否主要联系人
     */
    @Range(min = 0, max = 99999, message = "请在是否主要联系人中输入[0-99999]范围内的数值。")
    @Column
    private Integer isMainContacts = 0;

    /**
     * 附件关联UID
     */
    @Length(max = 64, message = "请在附件关联UID中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String fileRelationUid;

    public Contact() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getCompanyUid() {
        return this.companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPost() {
        return this.post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getResponsibility() {
        return this.responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
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

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomeTelephone() {
        return this.homeTelephone;
    }

    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }

    public String getHomeZipcode() {
        return this.homeZipcode;
    }

    public void setHomeZipcode(String homeZipcode) {
        this.homeZipcode = homeZipcode;
    }

    public String getInterest() {
        return this.interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public Integer getIsMainContacts() {
        return this.isMainContacts;
    }

    public void setIsMainContacts(Integer isMainContacts) {
        this.isMainContacts = isMainContacts;
    }

    public String getFileRelationUid() {
        return this.fileRelationUid;
    }

    public void setFileRelationUid(String fileRelationUid) {
        this.fileRelationUid = fileRelationUid;
    }

}