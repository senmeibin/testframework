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
import java.util.Date;

/**
 * 活动信息实体类
 */
@MappedSuperclass
public class Activity extends CrmBaseEntity {
    private static final long serialVersionUID = -20180322073535789L;

    /**
     * 活动分类
     */
    @Length(max = 8, message = "请在活动分类中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入活动分类。")
    @Column(name = "category_cd", length = 8, nullable = false)
    private String categoryCd;

    /**
     * 活动分类
     */
    @Transient
    private String categoryName;

    /**
     * 活动种类
     */
    @Length(max = 8, message = "请在活动种类中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入活动种类。")
    @Column(name = "type_cd", length = 8, nullable = false)
    private String typeCd = "01";

    /**
     * 活动种类
     */
    @Transient
    private String typeName;

    /**
     * 活动主题
     */
    @Length(max = 128, message = "请在活动主题中输入[0-128]位以内的文字。")
    @NotEmpty(message = "请输入活动主题。")
    @Column(name = "title", length = 128, nullable = false)
    private String title;

    /**
     * 活动目的
     */
    @Length(max = 256, message = "请在活动目的中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入活动目的。")
    @Column(name = "purpose", length = 256, nullable = false)
    private String purpose;

    /**
     * 活动开始日期
     */
    @Column(name = "start_date", nullable = false)
    @NotNull(message = "请输入活动开始日期。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date startDate;

    /**
     * 活动结束日期
     */
    @Column(name = "end_date", nullable = false)
    @NotNull(message = "请输入活动结束日期。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date endDate;

    /**
     * 活动地址
     */
    @Length(max = 256, message = "请在活动地址中输入[0-256]位以内的文字。")
    @Column(name = "address", length = 256)
    private String address;

    /**
     * 活动对象
     */
    @Length(max = 256, message = "请在活动对象中输入[0-256]位以内的文字。")
    @Column(name = "target", length = 256)
    private String target;

    /**
     * 活动内容
     */
    @Column(name = "contents", nullable = false)
    @NotNull(message = "请输入活动内容。")
    private String contents;

    /**
     * 所需物资
     */
    @Column(name = "required_materials")
    private String requiredMaterials;

    /**
     * 负责人
     */
    @Column(name = "responsible_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择负责人。")
    private String responsibleUserUid;

    /**
     * 负责人
     */
    @Transient
    private String responsibleUserName;

    /**
     * 申请校区
     */
    @Column(name = "apply_campus_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择申请校区。")
    private String applyCampusUid;

    /**
     * 申请校区
     */
    @Transient
    private String applyCampusName;

    /**
     * 申请日期
     */
    @Column(name = "apply_date", nullable = false)
    @NotNull(message = "请输入申请日期。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date applyDate;

    /**
     * 申请人
     */
    @Column(name = "apply_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择申请人。")
    private String applyUserUid;

    /**
     * 申请人
     */
    @Transient
    private String applyUserName;

    /**
     * 审批时间
     */
    @Column(name = "audit_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date auditDate;

    /**
     * 审批人
     */
    @Column(name = "audit_user_uid", length = 32)
    private String auditUserUid;

    /**
     * 审批人
     */
    @Transient
    private String auditUserName;

    /**
     * 审批状态
     */
    @Length(max = 8, message = "请在审批状态中输入[0-8]位以内的文字。")
    @Column(name = "audit_status_cd", length = 8)
    private String auditStatusCd;

    /**
     * 审批状态
     */
    @Transient
    private String auditStatusName;

    /**
     * 审批意见
     */
    @Length(max = 256, message = "请在审批意见中输入[0-256]位以内的文字。")
    @Column(name = "audit_comment", length = 256)
    private String auditComment;

    /**
     * 表示顺序
     */
    @Range(min = 0L, max = 99999999L, message = "请在表示顺序中输入[0-99999999]范围内的数值。")
    @Column(name = "disp_seq")
    private Integer dispSeq = 1;

    public Activity() {
    }

    public String getCategoryCd() {
        return this.categoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        this.categoryCd = categoryCd;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTypeCd() {
        return this.typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getRequiredMaterials() {
        return this.requiredMaterials;
    }

    public void setRequiredMaterials(String requiredMaterials) {
        this.requiredMaterials = requiredMaterials;
    }

    public String getResponsibleUserUid() {
        return this.responsibleUserUid;
    }

    public void setResponsibleUserUid(String responsibleUserUid) {
        this.responsibleUserUid = responsibleUserUid;
    }

    public String getResponsibleUserName() {
        return this.responsibleUserName;
    }

    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
    }

    public String getApplyCampusUid() {
        return this.applyCampusUid;
    }

    public void setApplyCampusUid(String applyCampusUid) {
        this.applyCampusUid = applyCampusUid;
    }

    public String getApplyCampusName() {
        return this.applyCampusName;
    }

    public void setApplyCampusName(String applyCampusName) {
        this.applyCampusName = applyCampusName;
    }

    public Date getApplyDate() {
        return this.applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyUserUid() {
        return this.applyUserUid;
    }

    public void setApplyUserUid(String applyUserUid) {
        this.applyUserUid = applyUserUid;
    }

    public String getApplyUserName() {
        return this.applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public Date getAuditDate() {
        return this.auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditUserUid() {
        return this.auditUserUid;
    }

    public void setAuditUserUid(String auditUserUid) {
        this.auditUserUid = auditUserUid;
    }

    public String getAuditUserName() {
        return this.auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public String getAuditStatusCd() {
        return this.auditStatusCd;
    }

    public void setAuditStatusCd(String auditStatusCd) {
        this.auditStatusCd = auditStatusCd;
    }

    public String getAuditStatusName() {
        return this.auditStatusName;
    }

    public void setAuditStatusName(String auditStatusName) {
        this.auditStatusName = auditStatusName;
    }

    public String getAuditComment() {
        return this.auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

}