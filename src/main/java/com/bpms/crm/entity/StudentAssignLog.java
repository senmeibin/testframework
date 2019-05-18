package com.bpms.crm.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 学员分配日志实体类
 */
@MappedSuperclass
public class StudentAssignLog extends CrmBaseEntity {
    private static final long serialVersionUID = -20180407210742112L;

    /**
     * 学员UID
     */
    @Column(name = "student_uid", length = 32)
    private String studentUid;

    /**
     * 学员姓名
     */
    @Transient
    private String studentName;

    /**
     * 划转类型
     */
    @Length(max = 8, message = "请在划转类型中输入[0-8]位以内的文字。")
    @Column(name = "assign_type_cd", length = 8)
    private String assignTypeCd = "01";

    /**
     * 划转类型
     */
    @Transient
    private String assignTypeName;

    /**
     * 变更前课程顾问
     */
    @Column(name = "before_user_uid", length = 32)
    private String beforeUserUid;

    /**
     * 变更前课程顾问
     */
    @Transient
    private String beforeUserName;

    /**
     * 变更后课程顾问
     */
    @Column(name = "after_user_uid", length = 32)
    private String afterUserUid;

    /**
     * 变更后课程顾问
     */
    @Transient
    private String afterUserName;

    public StudentAssignLog() {
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

    public String getAssignTypeCd() {
        return this.assignTypeCd;
    }

    public void setAssignTypeCd(String assignTypeCd) {
        this.assignTypeCd = assignTypeCd;
    }

    public String getAssignTypeName() {
        return this.assignTypeName;
    }

    public void setAssignTypeName(String assignTypeName) {
        this.assignTypeName = assignTypeName;
    }

    public String getBeforeUserUid() {
        return this.beforeUserUid;
    }

    public void setBeforeUserUid(String beforeUserUid) {
        this.beforeUserUid = beforeUserUid;
    }

    public String getBeforeUserName() {
        return this.beforeUserName;
    }

    public void setBeforeUserName(String beforeUserName) {
        this.beforeUserName = beforeUserName;
    }

    public String getAfterUserUid() {
        return this.afterUserUid;
    }

    public void setAfterUserUid(String afterUserUid) {
        this.afterUserUid = afterUserUid;
    }

    public String getAfterUserName() {
        return this.afterUserName;
    }

    public void setAfterUserName(String afterUserName) {
        this.afterUserName = afterUserName;
    }

}