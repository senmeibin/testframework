package com.bpms.crm.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 班级学员实体类
 */
@MappedSuperclass
public class ClassStudent extends CrmBaseEntity {
    private static final long serialVersionUID = -20180325104933180L;

    /**
     * 班级UID
     */
    @Column(name = "campus_class_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择班级。")
    private String campusClassUid;

    /**
     * 班级
     */
    @Transient
    private String campusClassName;

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

    public ClassStudent() {
    }

    public String getCampusClassUid() {
        return this.campusClassUid;
    }

    public void setCampusClassUid(String campusClassUid) {
        this.campusClassUid = campusClassUid;
    }

    public String getCampusClassName() {
        return this.campusClassName;
    }

    public void setCampusClassName(String campusClassName) {
        this.campusClassName = campusClassName;
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

}