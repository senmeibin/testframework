package com.bpms.crm.entity.ext;

import com.bpms.core.consts.CmnConsts;
import com.bpms.crm.entity.Student;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "crm_student")
public class StudentExt extends Student {
    private static final long serialVersionUID = -20180325114921000L;

    /**
     * 跟进时间
     */
    @Transient
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date followupDate;

    /**
     * 跟进方式
     */
    @Transient
    private String followupMethodCd;

    /**
     * 跟进方式
     */
    @Transient
    private String followupMethodName;

    /**
     * 跟进内容
     */
    @Transient
    private String contents;

    /**
     * 下次跟进时间
     */
    @Transient
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date nextFollowupDate;

    /**
     * 销售进程
     */
    @Transient
    private String saleProcessCd;

    /**
     * 销售进程
     */
    @Transient
    private String saleProcessName;

    /**
     * 是否预约
     */
    @Transient
    private String acceptShiftCd;

    /**
     * 是否预约
     */
    @Transient
    private String acceptShiftName;

    /**
     * 跟进记录
     */
    @Transient
    private List<FollowupExt> followupList;

    /**
     * 预约记录
     */
    @Transient
    private List<ReservationExt> reservationList;

    /**
     * 报名记录
     */
    @Transient
    private List<RegistrationExt> registrationList;

    /**
     * 缴费记录
     */
    @Transient
    private List<PaymentExt> paymentList;

    public Date getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(Date followupDate) {
        this.followupDate = followupDate;
    }

    public String getFollowupMethodCd() {
        return followupMethodCd;
    }

    public void setFollowupMethodCd(String followupMethodCd) {
        this.followupMethodCd = followupMethodCd;
    }

    public String getFollowupMethodName() {
        return followupMethodName;
    }

    public void setFollowupMethodName(String followupMethodName) {
        this.followupMethodName = followupMethodName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getNextFollowupDate() {
        return nextFollowupDate;
    }

    public void setNextFollowupDate(Date nextFollowupDate) {
        this.nextFollowupDate = nextFollowupDate;
    }

    public String getSaleProcessCd() {
        return saleProcessCd;
    }

    public void setSaleProcessCd(String saleProcessCd) {
        this.saleProcessCd = saleProcessCd;
    }

    public String getSaleProcessName() {
        return saleProcessName;
    }

    public void setSaleProcessName(String saleProcessName) {
        this.saleProcessName = saleProcessName;
    }

    public String getAcceptShiftCd() {
        return acceptShiftCd;
    }

    public void setAcceptShiftCd(String acceptShiftCd) {
        this.acceptShiftCd = acceptShiftCd;
    }

    public String getAcceptShiftName() {
        return acceptShiftName;
    }

    public void setAcceptShiftName(String acceptShiftName) {
        this.acceptShiftName = acceptShiftName;
    }

    public List<FollowupExt> getFollowupList() {
        return followupList;
    }

    public void setFollowupList(List<FollowupExt> followupList) {
        this.followupList = followupList;
    }

    public List<ReservationExt> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<ReservationExt> reservationList) {
        this.reservationList = reservationList;
    }

    public List<RegistrationExt> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<RegistrationExt> registrationList) {
        this.registrationList = registrationList;
    }

    public List<PaymentExt> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<PaymentExt> paymentList) {
        this.paymentList = paymentList;
    }
}