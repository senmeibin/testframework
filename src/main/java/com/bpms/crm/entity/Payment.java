package com.bpms.crm.entity;

import com.bpms.core.consts.CmnConsts;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 缴费记录实体类
 */
@MappedSuperclass
public class Payment extends CrmBaseEntity {
    private static final long serialVersionUID = -20180407162122502L;

    /**
     * 学员UID
     */
    @Column(name = "student_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择学员。")
    private String studentUid;

    /**
     * 学员姓名
     */
    @Transient
    private String studentName;

    /**
     * 报名记录UID
     */
    @Column(name = "registration_uid", length = 32)
    private String registrationUid;

    /**
     * 报名记录
     */
    @Transient
    private String registrationName;

    /**
     * 所属校区UID
     */
    @Column(name = "payment_belong_campus_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择所属校区。")
    private String paymentBelongCampusUid;

    /**
     * 所属校区
     */
    @Transient
    private String paymentBelongCampusName;

    /**
     * 课程顾问UID
     */
    @Column(name = "payment_belong_consultant_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择课程顾问。")
    private String paymentBelongConsultantUserUid;

    /**
     * 课程顾问
     */
    @Transient
    private String paymentBelongConsultantUserName;

    /**
     * 缴费日期
     */
    @Column(name = "payment_date", nullable = false)
    @NotNull(message = "请输入缴费日期。")
    @DateTimeFormat(pattern = CmnConsts.DATE_TIME_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date paymentDate;

    /**
     * 收据序号
     */
    @Length(max = 32, message = "请在收据序号中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入收据序号。")
    @Column(name = "receipt_number", length = 32, nullable = false)
    private String receiptNumber;

    /**
     * 付款人
     */
    @Length(max = 32, message = "请在付款人中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入付款人。")
    @Column(name = "payer", length = 32, nullable = false)
    private String payer;

    /**
     * 付款方式
     */
    @Length(max = 8, message = "请在付款方式中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入付款方式。")
    @Column(name = "payment_method_cd", length = 8, nullable = false)
    private String paymentMethodCd;

    /**
     * 付款方式
     */
    @Transient
    private String paymentMethodName;

    /**
     * 费用科目
     */
    @Length(max = 8, message = "请在费用科目中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入费用科目。")
    @Column(name = "item_type_cd", length = 8, nullable = false)
    private String itemTypeCd;

    /**
     * 费用科目
     */
    @Transient
    private String itemTypeName;

    /**
     * 应付金额
     */
    @Range(min = -9999999999L, max = 9999999999L, message = "请在应付金额中输入[-9999999999-9999999999]范围内的数值。")
    @Column(name = "payable_amount")
    private BigDecimal payableAmount;

    /**
     * 实收金额
     */
    @Range(min = -9999999999L, max = 9999999999L, message = "请在实收金额中输入[-9999999999-9999999999]范围内的数值。")
    @NotNull(message = "请输入实收金额。")
    @Column(name = "payment_amount", nullable = false)
    private BigDecimal paymentAmount;

    /**
     * 收费情况
     */
    @Length(max = 256, message = "请在收费情况中输入[0-256]位以内的文字。")
    @Column(name = "payment_details", length = 256)
    private String paymentDetails;

    /**
     * 收款人
     */
    @Column(name = "payee_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择收款人。")
    private String payeeUserUid;

    /**
     * 收款人
     */
    @Transient
    private String payeeUserName;

    /**
     * 退款金额
     */
    @Range(min = -9999999999L, max = 9999999999L, message = "请在退款金额中输入[-9999999999-9999999999]范围内的数值。")
    @Column(name = "refund_amount")
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    @Length(max = 256, message = "请在退款原因中输入[0-256]位以内的文字。")
    @Column(name = "refund_reason", length = 256)
    private String refundReason;

    /**
     * 退款人
     */
    @Column(name = "refund_user_uid", length = 32)
    private String refundUserUid;

    /**
     * 退款人
     */
    @Transient
    private String refundUserName;

    public Payment() {
    }

    public String getStudentUid() {
        return this.studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRegistrationUid() {
        return this.registrationUid;
    }

    public void setRegistrationUid(String registrationUid) {
        this.registrationUid = registrationUid;
    }

    public String getRegistrationName() {
        return this.registrationName;
    }

    public void setRegistrationName(String registrationName) {
        this.registrationName = registrationName;
    }

    public String getPaymentBelongCampusUid() {
        return this.paymentBelongCampusUid;
    }

    public void setPaymentBelongCampusUid(String paymentBelongCampusUid) {
        this.paymentBelongCampusUid = paymentBelongCampusUid;
    }

    public String getPaymentBelongCampusName() {
        return this.paymentBelongCampusName;
    }

    public void setPaymentBelongCampusName(String paymentBelongCampusName) {
        this.paymentBelongCampusName = paymentBelongCampusName;
    }

    public String getPaymentBelongConsultantUserUid() {
        return this.paymentBelongConsultantUserUid;
    }

    public void setPaymentBelongConsultantUserUid(String paymentBelongConsultantUserUid) {
        this.paymentBelongConsultantUserUid = paymentBelongConsultantUserUid;
    }

    public String getPaymentBelongConsultantUserName() {
        return this.paymentBelongConsultantUserName;
    }

    public void setPaymentBelongConsultantUserName(String paymentBelongConsultantUserName) {
        this.paymentBelongConsultantUserName = paymentBelongConsultantUserName;
    }

    public Date getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getReceiptNumber() {
        return this.receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getPayer() {
        return this.payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPaymentMethodCd() {
        return this.paymentMethodCd;
    }

    public void setPaymentMethodCd(String paymentMethodCd) {
        this.paymentMethodCd = paymentMethodCd;
    }

    public String getPaymentMethodName() {
        return this.paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getItemTypeCd() {
        return this.itemTypeCd;
    }

    public void setItemTypeCd(String itemTypeCd) {
        this.itemTypeCd = itemTypeCd;
    }

    public String getItemTypeName() {
        return this.itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public BigDecimal getPayableAmount() {
        return this.payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    public BigDecimal getPaymentAmount() {
        return this.paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDetails() {
        return this.paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getPayeeUserUid() {
        return this.payeeUserUid;
    }

    public void setPayeeUserUid(String payeeUserUid) {
        this.payeeUserUid = payeeUserUid;
    }

    public String getPayeeUserName() {
        return this.payeeUserName;
    }

    public void setPayeeUserName(String payeeUserName) {
        this.payeeUserName = payeeUserName;
    }

    public BigDecimal getRefundAmount() {
        return this.refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReason() {
        return this.refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundUserUid() {
        return refundUserUid;
    }

    public void setRefundUserUid(String refundUserUid) {
        this.refundUserUid = refundUserUid;
    }

    public String getRefundUserName() {
        return this.refundUserName;
    }

    public void setRefundUserName(String refundUserName) {
        this.refundUserName = refundUserName;
    }
}