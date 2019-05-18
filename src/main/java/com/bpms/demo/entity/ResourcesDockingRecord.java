package com.bpms.demo.entity;

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
 * 入孵企业资源对接记录实体类
 */
@MappedSuperclass
public class ResourcesDockingRecord extends DemoBaseEntity {
    private static final long serialVersionUID = -20170510174729267L;

    /**
     * 入孵企业
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择入孵企业。")
    private String companyUid;

    /**
     * 入孵企业
     */
    @Transient
    private String companyName;

    /**
     * 对接内容
     */
    @Length(max = 512, message = "请在对接内容中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String dockingContent;

    /**
     * 对接时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date dockingDate;

    /**
     * 数量
     */
    @Range(min = 0, max = 99999999, message = "请在数量中输入[0-99999999]范围内的数值。")
    @Column
    private Integer quantity;

    /**
     * 对接人
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择对接人。")
    private String dockingPersonUid;

    /**
     * 对接人
     */
    @Transient
    private String dockingPersonName;

    /**
     * 对接机构
     */
    @Column(length = 32)
    private String thirdPartyServiceUid;

    /**
     * 对接机构
     */
    @Transient
    private String thirdPartyServiceName;

    /**
     * 对接机构联系人
     */
    @Column(length = 32)
    private String thirdPartyServiceContactsUid;

    /**
     * 对接机构联系人
     */
    @Transient
    private String thirdPartyServiceContactsName;

    /**
     * 跟踪
     */
    @Length(max = 512, message = "请在跟踪中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String following;

    public ResourcesDockingRecord() {
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

    public String getDockingContent() {
        return this.dockingContent;
    }

    public void setDockingContent(String dockingContent) {
        this.dockingContent = dockingContent;
    }

    public Date getDockingDate() {
        return this.dockingDate;
    }

    public void setDockingDate(Date dockingDate) {
        this.dockingDate = dockingDate;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDockingPersonUid() {
        return this.dockingPersonUid;
    }

    public void setDockingPersonUid(String dockingPersonUid) {
        this.dockingPersonUid = dockingPersonUid;
    }

    public String getDockingPersonName() {
        return this.dockingPersonName;
    }

    public void setDockingPersonName(String dockingPersonName) {
        this.dockingPersonName = dockingPersonName;
    }

    public String getThirdPartyServiceUid() {
        return this.thirdPartyServiceUid;
    }

    public void setThirdPartyServiceUid(String thirdPartyServiceUid) {
        this.thirdPartyServiceUid = thirdPartyServiceUid;
    }

    public String getThirdPartyServiceName() {
        return this.thirdPartyServiceName;
    }

    public void setThirdPartyServiceName(String thirdPartyServiceName) {
        this.thirdPartyServiceName = thirdPartyServiceName;
    }

    public String getThirdPartyServiceContactsUid() {
        return this.thirdPartyServiceContactsUid;
    }

    public void setThirdPartyServiceContactsUid(String thirdPartyServiceContactsUid) {
        this.thirdPartyServiceContactsUid = thirdPartyServiceContactsUid;
    }

    public String getThirdPartyServiceContactsName() {
        return this.thirdPartyServiceContactsName;
    }

    public void setThirdPartyServiceContactsName(String thirdPartyServiceContactsName) {
        this.thirdPartyServiceContactsName = thirdPartyServiceContactsName;
    }

    public String getFollowing() {
        return this.following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

}