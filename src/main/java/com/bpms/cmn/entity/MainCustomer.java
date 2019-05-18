package com.bpms.cmn.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 主客户实体类
 */
@MappedSuperclass
public class MainCustomer extends CmnBaseEntity {
    private static final long serialVersionUID = -20160331101241556L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 主客户名称
     */
    @Length(max = 64, message = "请在主客户名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入主客户名称。")
    @Column(length = 64, nullable = false)
    private String mainCustomerName;

    /**
     * 主客户简称
     */
    @Length(max = 32, message = "请在主客户简称中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String mainCustomerAbbreviation;

    /**
     * 联系人
     */
    @Length(max = 32, message = "请在联系人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactName;

    /**
     * 联系电话
     */
    @Length(max = 32, message = "请在联系电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactTelephone;

    /**
     * 客户概况
     */
    @Length(max = 256, message = "请在客户概况中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String introduction;

    /**
     * 表示顺序
     */
    @Range(min = 0, max = 99999999, message = "请在表示顺序中输入[0-99999999999]范围内的数值。")
    @Column
    private Integer dispSeq = 1;

    public MainCustomer() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getMainCustomerName() {
        return this.mainCustomerName;
    }

    public void setMainCustomerName(String mainCustomerName) {
        this.mainCustomerName = mainCustomerName;
    }

    public String getMainCustomerAbbreviation() {
        return this.mainCustomerAbbreviation;
    }

    public void setMainCustomerAbbreviation(String mainCustomerAbbreviation) {
        this.mainCustomerAbbreviation = mainCustomerAbbreviation;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelephone() {
        return this.contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

}