package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 第三方服务实体类
 */
@MappedSuperclass
public class ThirdPartyServiceContact extends DemoBaseEntity {
    private static final long serialVersionUID = -20170509135543351L;

    /**
     * 所属基地
     */
    @Column(length = 32)
    private String baseUid;

    /**
     * 所属基地
     */
    @Transient
    private String baseName;

    /**
     * 企业名称
     */
    @Length(max = 64, message = "请在企业名称中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String companyName;

    /**
     * 服务内容
     */
    @Length(max = 256, message = "请在服务内容中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String serviceContent;

    /**
     * 服务类别
     */
    @Length(max = 64, message = "请在服务类别中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String serviceType;

    /**
     * 标签
     */
    @Length(max = 512, message = "请在标签中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String thirdPartyServiceTag;

    /**
     * 评价
     */
    @Length(max = 256, message = "请在评价中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String evaluate;

    /**
     * 服务时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date serviceDate;

    public ThirdPartyServiceContact() {
    }

    public String getBaseUid() {
        return this.baseUid;
    }

    public void setBaseUid(String baseUid) {
        this.baseUid = baseUid;
    }

    public String getBaseName() {
        return this.baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getServiceContent() {
        return this.serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getThirdPartyServiceTag() {
        return this.thirdPartyServiceTag;
    }

    public void setThirdPartyServiceTag(String thirdPartyServiceTag) {
        this.thirdPartyServiceTag = thirdPartyServiceTag;
    }

    public String getEvaluate() {
        return this.evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public Date getServiceDate() {
        return this.serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }
}