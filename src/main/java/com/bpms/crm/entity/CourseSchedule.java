package com.bpms.crm.entity;

import com.bpms.core.consts.CmnConsts;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 课程表实体类
 */
@MappedSuperclass
public class CourseSchedule extends CrmBaseEntity {
    private static final long serialVersionUID = -20180413173352648L;

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
     * 课程次数
     */
    @Range(min = 0L, max = 99999999L, message = "请在课程次数中输入[0-99999999]范围内的数值。")
    @NotNull(message = "请输入课程次数。")
    @Column(name = "course_number", nullable = false)
    private Integer courseNumber;

    /**
     * 课程日期
     */
    @Column(name = "course_date", nullable = false)
    @NotNull(message = "请输入课程日期。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date courseDate;

    /**
     * 课程内容
     */
    @Column(name = "course_content")
    private String courseContent;

    public CourseSchedule() {
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

    public Integer getCourseNumber() {
        return this.courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Date getCourseDate() {
        return this.courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseContent() {
        return this.courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

}