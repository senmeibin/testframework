package com.bpms.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 企业信息属性明细实体类
 */
@MappedSuperclass
public class CompanyAttributeDetails extends DemoBaseEntity {
    private static final long serialVersionUID = -20170418143722850L;

    /**
     * 企业UID
     */
    @Column(length = 64, nullable = false)
    @NotEmpty(message = "请选择企业。")
    private String companyUid;

    /**
     * 企业
     */
    @Transient
    private String companyName;

    /**
     * 企业信息区分大类
     */
    @Length(max = 8, message = "请在企业信息区分大类中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String mainCd;

    /**
     * 企业信息区分大类
     */
    @Transient
    private String mainName;

    /**
     * 企业信息区分小类
     */
    @Length(max = 8, message = "请在企业信息区分小类中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String subCd;

    /**
     * 企业信息区分小类
     */
    @Transient
    private String subName;

    public CompanyAttributeDetails() {
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

    public String getMainCd() {
        return this.mainCd;
    }

    public void setMainCd(String mainCd) {
        this.mainCd = mainCd;
    }

    public String getMainName() {
        return this.mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getSubCd() {
        return this.subCd;
    }

    public void setSubCd(String subCd) {
        this.subCd = subCd;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

}