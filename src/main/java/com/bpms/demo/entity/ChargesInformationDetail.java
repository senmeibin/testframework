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
 * 企业收费明细实体类
 */
@MappedSuperclass
public class ChargesInformationDetail extends DemoBaseEntity {
    private static final long serialVersionUID = -20170418164404380L;

    /**
     * 企业收费信息UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择企业收费信息。")
    private String chargesInformationUid;

    /**
     * 企业收费信息
     */
    @Transient
    private String chargesInformationName;

    /**
     * 应付日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date dueChargesDate;

    /**
     * 收费时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date chargesDate;

    /**
     * 收费人
     */
    @Length(max = 32, message = "请在收费人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String chargersName;

    /**
     * 收款方式
     */
    @Length(max = 8, message = "请在收款方式中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String chargesTypeCd;

    /**
     * 收款方式
     */
    @Transient
    private String chargesTypeName;

    public ChargesInformationDetail() {
    }

    public String getChargesInformationUid() {
        return this.chargesInformationUid;
    }

    public void setChargesInformationUid(String chargesInformationUid) {
        this.chargesInformationUid = chargesInformationUid;
    }

    public String getChargesInformationName() {
        return this.chargesInformationName;
    }

    public void setChargesInformationName(String chargesInformationName) {
        this.chargesInformationName = chargesInformationName;
    }

    public Date getDueChargesDate() {
        return dueChargesDate;
    }

    public void setDueChargesDate(Date dueChargesDate) {
        this.dueChargesDate = dueChargesDate;
    }

    public Date getChargesDate() {
        return this.chargesDate;
    }

    public void setChargesDate(Date chargesDate) {
        this.chargesDate = chargesDate;
    }

    public String getChargersName() {
        return this.chargersName;
    }

    public void setChargersName(String chargersName) {
        this.chargersName = chargersName;
    }

    public String getChargesTypeCd() {
        return this.chargesTypeCd;
    }

    public void setChargesTypeCd(String chargesTypeCd) {
        this.chargesTypeCd = chargesTypeCd;
    }

    public String getChargesTypeName() {
        return this.chargesTypeName;
    }

    public void setChargesTypeName(String chargesTypeName) {
        this.chargesTypeName = chargesTypeName;
    }
}