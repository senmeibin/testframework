package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 基地信息实体类
 */
@MappedSuperclass
public class BaseInfo extends DemoBaseEntity {
    private static final long serialVersionUID = -20170420113130995L;

    /**
     * 基地名称
     */
    @Length(max = 32, message = "请在基地名称中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String baseName;

    /**
     * 地点
     */
    @Length(max = 64, message = "请在地点中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String baseAddress;

    /**
     * 负责人
     */
    @Length(max = 32, message = "请在负责人中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String principal;

    /**
     * 成立时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date foundingTime;

    /**
     * 人数
     */
    @Range(min = 0, max = 99999999, message = "请在人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfPeople;

    /**
     * 联系电话
     */
    @Length(max = 32, message = "请在联系电话中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String contactNumber;

    public BaseInfo() {
    }

    public String getBaseName() {
        return this.baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getBaseAddress() {
        return this.baseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        this.baseAddress = baseAddress;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Date getFoundingTime() {
        return this.foundingTime;
    }

    public void setFoundingTime(Date foundingTime) {
        this.foundingTime = foundingTime;
    }

    public Integer getNumberOfPeople() {
        return this.numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

}