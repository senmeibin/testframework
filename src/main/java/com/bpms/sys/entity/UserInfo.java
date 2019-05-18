package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 用户扩展实体类
 */
@MappedSuperclass
public class UserInfo extends SysBaseEntity {
    private static final long serialVersionUID = -20180226104919459L;

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
     * 邮箱密码
     */
    @Length(max = 256, message = "请在邮箱密码中输入[0-256]位以内的文字。")
    @Column(name = "mail_password", length = 256)
    private String mailPassword;

    /**
     * 微信号
     */
    @Length(max = 32, message = "请在微信号中输入[0-32]位以内的文字。")
    @Column(name = "weixin", length = 32)
    private String weixin;

    /**
     * QQ号
     */
    @Length(max = 16, message = "请在QQ号中输入[0-16]位以内的文字。")
    @Column(name = "qq", length = 16)
    private String qq;

    /**
     * 头像URL
     */
    @Length(max = 512, message = "请在头像URL中输入[0-512]位以内的文字。")
    @Column(name = "head_image_url", length = 512)
    private String headImageUrl;

    /**
     * 外部系统名称(一)
     */
    @Length(max = 32, message = "请在外部系统名称(一)中输入[0-32]位以内的文字。")
    @Column(name = "ext_system_name1", length = 32)
    private String extSystemName1;

    /**
     * 外部系统账号(一)
     */
    @Length(max = 64, message = "请在外部系统账号(一)中输入[0-64]位以内的文字。")
    @Column(name = "ext_system_account1", length = 64)
    private String extSystemAccount1;

    /**
     * 外部系统密码(一)
     */
    @Length(max = 256, message = "请在外部系统密码(一)中输入[0-256]位以内的文字。")
    @Column(name = "ext_system_password1", length = 256)
    private String extSystemPassword1;

    /**
     * 外部系统名称(二)
     */
    @Length(max = 32, message = "请在外部系统名称(二)中输入[0-32]位以内的文字。")
    @Column(name = "ext_system_name2", length = 32)
    private String extSystemName2;

    /**
     * 外部系统账号(二)
     */
    @Length(max = 64, message = "请在外部系统账号(二)中输入[0-64]位以内的文字。")
    @Column(name = "ext_system_account2", length = 64)
    private String extSystemAccount2;

    /**
     * 外部系统密码(二)
     */
    @Length(max = 256, message = "请在外部系统密码(二)中输入[0-256]位以内的文字。")
    @Column(name = "ext_system_password2", length = 256)
    private String extSystemPassword2;

    /**
     * 外部系统名称(三)
     */
    @Length(max = 32, message = "请在外部系统名称(三)中输入[0-32]位以内的文字。")
    @Column(name = "ext_system_name3", length = 32)
    private String extSystemName3;

    /**
     * 外部系统账号(三)
     */
    @Length(max = 64, message = "请在外部系统账号(三)中输入[0-64]位以内的文字。")
    @Column(name = "ext_system_account3", length = 64)
    private String extSystemAccount3;

    /**
     * 外部系统密码(三)
     */
    @Length(max = 256, message = "请在外部系统密码(三)中输入[0-256]位以内的文字。")
    @Column(name = "ext_system_password3", length = 256)
    private String extSystemPassword3;

    /**
     * 外部系统名称(四)
     */
    @Length(max = 32, message = "请在外部系统名称(四)中输入[0-32]位以内的文字。")
    @Column(name = "ext_system_name4", length = 32)
    private String extSystemName4;

    /**
     * 外部系统账号(四)
     */
    @Length(max = 64, message = "请在外部系统账号(四)中输入[0-64]位以内的文字。")
    @Column(name = "ext_system_account4", length = 64)
    private String extSystemAccount4;

    /**
     * 外部系统密码(四)
     */
    @Length(max = 256, message = "请在外部系统密码(四)中输入[0-256]位以内的文字。")
    @Column(name = "ext_system_password4", length = 256)
    private String extSystemPassword4;

    /**
     * 外部系统名称(五)
     */
    @Length(max = 32, message = "请在外部系统名称(五)中输入[0-32]位以内的文字。")
    @Column(name = "ext_system_name5", length = 32)
    private String extSystemName5;

    /**
     * 外部系统账号(五)
     */
    @Length(max = 64, message = "请在外部系统账号(五)中输入[0-64]位以内的文字。")
    @Column(name = "ext_system_account5", length = 64)
    private String extSystemAccount5;

    /**
     * 外部系统密码(五)
     */
    @Length(max = 256, message = "请在外部系统密码(五)中输入[0-256]位以内的文字。")
    @Column(name = "ext_system_password5", length = 256)
    private String extSystemPassword5;

    public UserInfo() {
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

    public String getMailPassword() {
        return this.mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getWeixin() {
        return this.weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getHeadImageUrl() {
        return this.headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getExtSystemName1() {
        return this.extSystemName1;
    }

    public void setExtSystemName1(String extSystemName1) {
        this.extSystemName1 = extSystemName1;
    }

    public String getExtSystemAccount1() {
        return this.extSystemAccount1;
    }

    public void setExtSystemAccount1(String extSystemAccount1) {
        this.extSystemAccount1 = extSystemAccount1;
    }

    public String getExtSystemPassword1() {
        return this.extSystemPassword1;
    }

    public void setExtSystemPassword1(String extSystemPassword1) {
        this.extSystemPassword1 = extSystemPassword1;
    }

    public String getExtSystemName2() {
        return this.extSystemName2;
    }

    public void setExtSystemName2(String extSystemName2) {
        this.extSystemName2 = extSystemName2;
    }

    public String getExtSystemAccount2() {
        return this.extSystemAccount2;
    }

    public void setExtSystemAccount2(String extSystemAccount2) {
        this.extSystemAccount2 = extSystemAccount2;
    }

    public String getExtSystemPassword2() {
        return this.extSystemPassword2;
    }

    public void setExtSystemPassword2(String extSystemPassword2) {
        this.extSystemPassword2 = extSystemPassword2;
    }

    public String getExtSystemName3() {
        return this.extSystemName3;
    }

    public void setExtSystemName3(String extSystemName3) {
        this.extSystemName3 = extSystemName3;
    }

    public String getExtSystemAccount3() {
        return this.extSystemAccount3;
    }

    public void setExtSystemAccount3(String extSystemAccount3) {
        this.extSystemAccount3 = extSystemAccount3;
    }

    public String getExtSystemPassword3() {
        return this.extSystemPassword3;
    }

    public void setExtSystemPassword3(String extSystemPassword3) {
        this.extSystemPassword3 = extSystemPassword3;
    }

    public String getExtSystemName4() {
        return this.extSystemName4;
    }

    public void setExtSystemName4(String extSystemName4) {
        this.extSystemName4 = extSystemName4;
    }

    public String getExtSystemAccount4() {
        return this.extSystemAccount4;
    }

    public void setExtSystemAccount4(String extSystemAccount4) {
        this.extSystemAccount4 = extSystemAccount4;
    }

    public String getExtSystemPassword4() {
        return this.extSystemPassword4;
    }

    public void setExtSystemPassword4(String extSystemPassword4) {
        this.extSystemPassword4 = extSystemPassword4;
    }

    public String getExtSystemName5() {
        return this.extSystemName5;
    }

    public void setExtSystemName5(String extSystemName5) {
        this.extSystemName5 = extSystemName5;
    }

    public String getExtSystemAccount5() {
        return this.extSystemAccount5;
    }

    public void setExtSystemAccount5(String extSystemAccount5) {
        this.extSystemAccount5 = extSystemAccount5;
    }

    public String getExtSystemPassword5() {
        return this.extSystemPassword5;
    }

    public void setExtSystemPassword5(String extSystemPassword5) {
        this.extSystemPassword5 = extSystemPassword5;
    }
}