package com.bpms.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 第三方服务联系人实体类
 */
@MappedSuperclass
public class ThirdPartyServiceContacts extends DemoBaseEntity {
    private static final long serialVersionUID = -20170509141338782L;

    /**
     * 第三方服务UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择第三方服务。")
    private String thirdPartyServiceUid;

    /**
     * 第三方服务企业名
     */
    @Transient
    private String thirdPartyServiceName;

    /**
     * 联系人
     */
    @Length(max = 32, message = "请在联系人中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入联系人。")
    @Column(length = 32, nullable = false)
    private String contactName;

    /**
     * 联系电话
     */
    @Length(max = 32, message = "请在联系电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactNumber;

    /**
     * 邮箱
     */
    @Length(max = 32, message = "请在邮箱中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactMail;

    /**
     * 部门职务
     */
    @Length(max = 128, message = "请在部门职务中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String positionDept;

    public ThirdPartyServiceContacts() {
    }

    public String getThirdPartyServiceUid() {
        return this.thirdPartyServiceUid;
    }

    public void setThirdPartyServiceUid(String thirdPartyServiceUid) {
        this.thirdPartyServiceUid = thirdPartyServiceUid;
    }

    public String getThirdPartyServiceName() {
        return thirdPartyServiceName;
    }

    public void setThirdPartyServiceName(String thirdPartyServiceName) {
        this.thirdPartyServiceName = thirdPartyServiceName;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactMail() {
        return this.contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getPositionDept() {
        return this.positionDept;
    }

    public void setPositionDept(String positionDept) {
        this.positionDept = positionDept;
    }

}