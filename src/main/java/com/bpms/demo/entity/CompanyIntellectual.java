package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 知识产权实体类
 */
@MappedSuperclass
public class CompanyIntellectual extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511162636963L;

    /**
     * 企业UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择企业。")
    private String companyUid;

    /**
     * 企业
     */
    @Transient
    private String companyName;

    /**
     * 知识产权类型
     */
    @Length(max = 8, message = "请在知识产权类型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String intellectualPropertyCd;

    /**
     * 知识产权类型
     */
    @Transient
    private String intellectualPropertyName;

    /**
     * 发生时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date occurrenceDate;

    /**
     * 编号
     */
    @Length(max = 32, message = "请在编号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String serialNumber;

    /**
     * 名称
     */
    @Length(max = 32, message = "请在名称中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String name;

    /**
     * 状态
     */
    @Length(max = 8, message = "请在状态中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String stateCd;

    /**
     * 状态
     */
    @Transient
    private String stateName;

    /**
     * 是否有效
     */
    @Length(max = 8, message = "请在是否有效中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String effectiveCd;

    /**
     * 是否有效
     */
    @Transient
    private String effectiveName;

    public CompanyIntellectual() {
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

    public String getIntellectualPropertyCd() {
        return this.intellectualPropertyCd;
    }

    public void setIntellectualPropertyCd(String intellectualPropertyCd) {
        this.intellectualPropertyCd = intellectualPropertyCd;
    }

    public String getIntellectualPropertyName() {
        return this.intellectualPropertyName;
    }

    public void setIntellectualPropertyName(String intellectualPropertyName) {
        this.intellectualPropertyName = intellectualPropertyName;
    }

    public Date getOccurrenceDate() {
        return this.occurrenceDate;
    }

    public void setOccurrenceDate(Date occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateCd() {
        return this.stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getEffectiveCd() {
        return this.effectiveCd;
    }

    public void setEffectiveCd(String effectiveCd) {
        this.effectiveCd = effectiveCd;
    }

    public String getEffectiveName() {
        return this.effectiveName;
    }

    public void setEffectiveName(String effectiveName) {
        this.effectiveName = effectiveName;
    }

}