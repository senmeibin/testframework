package com.bpms.cmn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 协议管理实体类
 */
@MappedSuperclass
public class Protocol extends CmnBaseEntity {
    private static final long serialVersionUID = -20180329100006047L;

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
     * 协议名称
     */
    @Length(max = 64, message = "请在协议名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入协议名称。")
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    /**
     * 协议分类
     */
    @Length(max = 8, message = "请在协议分类中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入协议分类。")
    @Column(name = "category_cd", length = 8, nullable = false)
    private String categoryCd;

    /**
     * 协议分类
     */
    @Transient
    private String categoryName;

    /**
     * 协议版本
     */
    @Length(max = 16, message = "请在协议版本中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入协议版本。")
    @Column(name = "version", length = 16, nullable = false)
    private String version = "V1.0";

    /**
     * 协议内容
     */
    @Column(name = "contents", nullable = false)
    @NotNull(message = "请输入协议内容。")
    private String contents;

    /**
     * 使用状态
     */
    @Length(max = 8, message = "请在使用状态中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入使用状态。")
    @Column(name = "use_status_cd", length = 8, nullable = false)
    private String useStatusCd = "01";

    /**
     * 使用状态
     */
    @Transient
    private String useStatusName;

    /**
     * 有效开始日期
     */
    @Column(name = "start_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date startDate;

    /**
     * 有效结束日期
     */
    @Column(name = "end_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date endDate;

    public Protocol() {
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Integer getEnterpriseUid() {
        return this.enterpriseUid;
    }

    public String getEnterpriseName() {
        return this.enterpriseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryCd(String categoryCd) {
        this.categoryCd = categoryCd;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCd() {
        return this.categoryCd;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return this.contents;
    }

    public String getUseStatusCd() {
        return useStatusCd;
    }

    public void setUseStatusCd(String useStatusCd) {
        this.useStatusCd = useStatusCd;
    }

    public String getUseStatusName() {
        return useStatusName;
    }

    public void setUseStatusName(String useStatusName) {
        this.useStatusName = useStatusName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

}