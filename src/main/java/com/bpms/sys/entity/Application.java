package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 应用模块实体类
 */
@MappedSuperclass
public class Application extends SysBaseEntity {
    private static final long serialVersionUID = -20160408124507095L;

    /**
     * 应用编号
     */
    @Length(max = 16, message = "请在应用编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入应用编号。")
    @Column(length = 16, nullable = false)
    private String appCode;

    /**
     * 应用名称
     */
    @Length(max = 32, message = "请在应用名称中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入应用名称。")
    @Column(length = 32, nullable = false)
    private String appName;

    /**
     * 有效日期
     */
    @Column(nullable = false)
    @NotNull(message = "请输入有效日期。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date validDate;

    /**
     * 表示顺序
     */
    @Range(min = 0, max = 99999, message = "请在表示顺序中输入[0-99999]范围内的数值。")
    @Column
    private Integer dispSeq = 0;

    /**
     * 应用种类
     */
    @Range(min = 0, max = 99999, message = "请在应用种类中输入[0-99999]范围内的数值。")
    @Column
    private Integer appType = 0;

    /**
     * 认证种类
     */
    @Range(min = 0, max = 99999, message = "请在认证种类中输入[0-99999]范围内的数值。")
    @Column
    private Integer authType = 0;

    /**
     * 应用版本
     */
    @Length(max = 16, message = "请在应用版本中输入[0-16]位以内的文字。")
    @Column(length = 16)
    private String version = "1.0";

    /**
     * 维护开始日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date mainteStartDate;

    /**
     * 维护结束日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date mainteEndDate;

    /**
     * 维护内容
     */
    @Length(max = 512, message = "请在维护内容中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String mainteContent;

    /**
     * 系统注册
     */
    @Range(min = 0, max = 99999, message = "请在系统注册中输入[0-99999]范围内的数值。")
    @Column
    private Integer regSystem = 0;

    public Application() {
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getValidDate() {
        return this.validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    public Integer getAppType() {
        return this.appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Integer getAuthType() {
        return this.authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getMainteStartDate() {
        return this.mainteStartDate;
    }

    public void setMainteStartDate(Date mainteStartDate) {
        this.mainteStartDate = mainteStartDate;
    }

    public Date getMainteEndDate() {
        return this.mainteEndDate;
    }

    public void setMainteEndDate(Date mainteEndDate) {
        this.mainteEndDate = mainteEndDate;
    }

    public String getMainteContent() {
        return this.mainteContent;
    }

    public void setMainteContent(String mainteContent) {
        this.mainteContent = mainteContent;
    }

    public Integer getRegSystem() {
        return this.regSystem;
    }

    public void setRegSystem(Integer regSystem) {
        this.regSystem = regSystem;
    }

}