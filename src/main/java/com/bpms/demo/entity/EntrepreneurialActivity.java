package com.bpms.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 创业活动实体类
 */
@MappedSuperclass
public class EntrepreneurialActivity extends DemoBaseEntity {
    private static final long serialVersionUID = -20170420173948209L;

    /**
     * 活动编号
     */
    @Length(max = 32, message = "请在活动编号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String activityNo;

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
     * 活动主题
     */
    @Length(max = 256, message = "请在活动主题中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String activityTopic;

    /**
     * 会务角色
     */
    @Length(max = 8, message = "请在会务角色中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String conferenceRoleCd;

    /**
     * 会务角色
     */
    @Transient
    private String conferenceRoleName;

    /**
     * 服务类型
     */
    @Length(max = 8, message = "请在服务类型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String serviceTypeCd;

    /**
     * 服务类型
     */
    @Transient
    private String serviceTypeName;

    /**
     * 活动地点
     */
    @Length(max = 64, message = "请在活动地点中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String activityLocation;

    /**
     * 服务内容
     */
    @Length(max = 8, message = "请在服务内容中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String serviceContentCd;

    /**
     * 服务内容
     */
    @Transient
    private String serviceContentName;

    /**
     * 活动类型
     */
    @Length(max = 8, message = "请在活动类型中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String activityTypeCd;

    /**
     * 活动类型
     */
    @Transient
    private String activityTypeName;

    /**
     * 活动人数
     */
    @Range(min = 0, max = 99999999, message = "请在活动人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer activityNumber;

    /**
     * 活动纪要
     */
    @Length(max = 256, message = "请在活动纪要中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String activitySummary;

    /**
     * 活动开始时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date endTime;

    /**
     * 周次
     */
    @Range(min = 0, max = 99999999, message = "请在周次中输入[0-99999999]范围内的数值。")
    @Column
    private Integer weeks;

    /**
     * 负责人
     */
    @Column(length = 32)
    private String personInChargeUid;

    /**
     * 负责人
     */
    @Transient
    private String personInChargeName;

    /**
     * 活动金额
     */
    @Range(min = 0, max = 99999999, message = "请在活动金额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal activityAmount;

    public EntrepreneurialActivity() {
    }

    public String getActivityNo() {
        return this.activityNo;
    }

    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo;
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

    public String getActivityTopic() {
        return this.activityTopic;
    }

    public void setActivityTopic(String activityTopic) {
        this.activityTopic = activityTopic;
    }

    public String getConferenceRoleCd() {
        return this.conferenceRoleCd;
    }

    public void setConferenceRoleCd(String conferenceRoleCd) {
        this.conferenceRoleCd = conferenceRoleCd;
    }

    public String getConferenceRoleName() {
        return this.conferenceRoleName;
    }

    public void setConferenceRoleName(String conferenceRoleName) {
        this.conferenceRoleName = conferenceRoleName;
    }

    public String getServiceTypeCd() {
        return this.serviceTypeCd;
    }

    public void setServiceTypeCd(String serviceTypeCd) {
        this.serviceTypeCd = serviceTypeCd;
    }

    public String getServiceTypeName() {
        return this.serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getActivityLocation() {
        return this.activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getServiceContentCd() {
        return this.serviceContentCd;
    }

    public void setServiceContentCd(String serviceContentCd) {
        this.serviceContentCd = serviceContentCd;
    }

    public String getServiceContentName() {
        return this.serviceContentName;
    }

    public void setServiceContentName(String serviceContentName) {
        this.serviceContentName = serviceContentName;
    }

    public String getActivityTypeCd() {
        return this.activityTypeCd;
    }

    public void setActivityTypeCd(String activityTypeCd) {
        this.activityTypeCd = activityTypeCd;
    }

    public String getActivityTypeName() {
        return this.activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public Integer getActivityNumber() {
        return this.activityNumber;
    }

    public void setActivityNumber(Integer activityNumber) {
        this.activityNumber = activityNumber;
    }

    public String getActivitySummary() {
        return this.activitySummary;
    }

    public void setActivitySummary(String activitySummary) {
        this.activitySummary = activitySummary;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getWeeks() {
        return this.weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    public String getPersonInChargeUid() {
        return this.personInChargeUid;
    }

    public void setPersonInChargeUid(String personInChargeUid) {
        this.personInChargeUid = personInChargeUid;
    }

    public String getPersonInChargeName() {
        return this.personInChargeName;
    }

    public void setPersonInChargeName(String personInChargeName) {
        this.personInChargeName = personInChargeName;
    }

    public BigDecimal getActivityAmount() {
        return this.activityAmount;
    }

    public void setActivityAmount(BigDecimal activityAmount) {
        this.activityAmount = activityAmount;
    }

}