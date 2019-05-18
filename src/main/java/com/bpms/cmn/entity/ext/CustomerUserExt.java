package com.bpms.cmn.entity.ext;

import javax.persistence.*;

import com.bpms.cmn.entity.CustomerUser;

@Entity(name = "CmnCustomerUserExt")
@Table(name = "cmn_customer_user")
public class CustomerUserExt extends CustomerUser {
    private static final long serialVersionUID = -20180109152220767L;

    /**
     * 用户名
     */
    @Transient
    private String userCd;

    /**
     * 手机号码
     */
    @Transient
    private String userPhone;

    /**
     * 邮箱
     */
    @Transient
    private String userMail;

    /**
     * 用户状态
     */
    @Transient
    private Integer userRecordStatus;

    /**
     * 客户简称
     */
    @Transient
    private String customerAbbreviation;

    /**
     * 主客户
     */
    @Transient
    private String mainCustomerName;

    /**
     * 地址
     */
    @Transient
    private String address;

    /**
     * 邮编
     */
    @Transient
    private String zipcode;

    /**
     * 电话
     */
    @Transient
    private String telephone;

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Integer getUserRecordStatus() {
        return userRecordStatus;
    }

    public void setUserRecordStatus(Integer userRecordStatus) {
        this.userRecordStatus = userRecordStatus;
    }

    public String getCustomerAbbreviation() {
        return customerAbbreviation;
    }

    public void setCustomerAbbreviation(String customerAbbreviation) {
        this.customerAbbreviation = customerAbbreviation;
    }

    public String getMainCustomerName() {
        return mainCustomerName;
    }

    public void setMainCustomerName(String mainCustomerName) {
        this.mainCustomerName = mainCustomerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}