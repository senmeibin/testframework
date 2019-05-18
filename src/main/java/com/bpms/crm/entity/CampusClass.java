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
 * 校区班级实体类
 */
@MappedSuperclass
public class CampusClass extends CrmBaseEntity {
    private static final long serialVersionUID = -20180414095547779L;

    /**
     * 所属校区
     */
    @Column(name = "campus_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择所属校区。")
    private String campusUid;

    /**
     * 所属校区
     */
    @Transient
    private String campusName;

    /**
     * 班级分类
     */
    @Length(max = 8, message = "请在班级分类中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入班级分类。")
    @Column(name = "category_cd", length = 8, nullable = false)
    private String categoryCd;

    /**
     * 班级分类
     */
    @Transient
    private String categoryName;

    /**
     * 班级级别
     */
    @Length(max = 8, message = "请在班级级别中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入班级级别。")
    @Column(name = "class_level_cd", length = 8, nullable = false)
    private String classLevelCd;

    /**
     * 班级级别
     */
    @Transient
    private String classLevelName;

    /**
     * 开班年月
     */
    @Length(max = 6, message = "请在开班年月中输入[0-6]位以内的文字。")
    @NotEmpty(message = "请输入开班年月。")
    @Column(name = "class_year_month", length = 6, nullable = false)
    private String classYearMonth;

    /**
     * 开班日期
     */
    @Column(name = "class_start_date", nullable = false)
    @NotNull(message = "请输入开班日期。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date classStartDate;

    /**
     * 结束日期
     */
    @Column(name = "class_end_date", nullable = false)
    @NotNull(message = "请输入结束日期。")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date classEndDate;

    /**
     * 开班期数
     */
    @Range(min = 0L, max = 99999999L, message = "请在开班期数中输入[0-99999999]范围内的数值。")
    @NotNull(message = "请输入开班期数。")
    @Column(name = "class_seq", nullable = false)
    private Integer classSeq = 1;

    /**
     * 上课时间
     */
    @Length(max = 32, message = "请在上课时间中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入上课时间。")
    @Column(name = "class_time", length = 32, nullable = false)
    private String classTime;

    /**
     * 班级编号
     */
    @Length(max = 16, message = "请在班级编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入班级编号。")
    @Column(name = "class_number", length = 16, nullable = false)
    private String classNumber;

    /**
     * 班级名称
     */
    @Length(max = 32, message = "请在班级名称中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入班级名称。")
    @Column(name = "class_name", length = 32, nullable = false)
    private String className;

    /**
     * 班级教室
     */
    @Length(max = 32, message = "请在班级教室中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入班级教室。")
    @Column(name = "classroom", length = 32, nullable = false)
    private String classroom;

    /**
     * 授课讲师
     */
    @Column(name = "teacher_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择授课讲师。")
    private String teacherUserUid;

    /**
     * 授课讲师
     */
    @Transient
    private String teacherUserName;

    /**
     * 课程顾问
     */
    @Column(name = "consultant_user_uid", length = 32)
    private String consultantUserUid;

    /**
     * 课程顾问
     */
    @Transient
    private String consultantUserName;

    /**
     * 学员限制
     */
    @Range(min = 0L, max = 99999999L, message = "请在学员限制中输入[0-99999999]范围内的数值。")
    @Column(name = "student_limit")
    private Integer studentLimit = 0;

    public CampusClass() {
    }

    public String getCampusUid() {
        return this.campusUid;
    }

    public void setCampusUid(String campusUid) {
        this.campusUid = campusUid;
    }

    public String getCampusName() {
        return this.campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
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

    public String getClassLevelCd() {
        return this.classLevelCd;
    }

    public void setClassLevelCd(String classLevelCd) {
        this.classLevelCd = classLevelCd;
    }

    public String getClassLevelName() {
        return this.classLevelName;
    }

    public void setClassLevelName(String classLevelName) {
        this.classLevelName = classLevelName;
    }

    public String getClassYearMonth() {
        return this.classYearMonth;
    }

    public void setClassYearMonth(String classYearMonth) {
        this.classYearMonth = classYearMonth;
    }

    public Date getClassStartDate() {
        return this.classStartDate;
    }

    public void setClassStartDate(Date classStartDate) {
        this.classStartDate = classStartDate;
    }

    public Date getClassEndDate() {
        return this.classEndDate;
    }

    public void setClassEndDate(Date classEndDate) {
        this.classEndDate = classEndDate;
    }

    public Integer getClassSeq() {
        return this.classSeq;
    }

    public void setClassSeq(Integer classSeq) {
        this.classSeq = classSeq;
    }

    public String getClassTime() {
        return this.classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getClassNumber() {
        return this.classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassroom() {
        return this.classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTeacherUserUid() {
        return this.teacherUserUid;
    }

    public void setTeacherUserUid(String teacherUserUid) {
        this.teacherUserUid = teacherUserUid;
    }

    public String getTeacherUserName() {
        return this.teacherUserName;
    }

    public void setTeacherUserName(String teacherUserName) {
        this.teacherUserName = teacherUserName;
    }

    public String getConsultantUserUid() {
        return this.consultantUserUid;
    }

    public void setConsultantUserUid(String consultantUserUid) {
        this.consultantUserUid = consultantUserUid;
    }

    public String getConsultantUserName() {
        return this.consultantUserName;
    }

    public void setConsultantUserName(String consultantUserName) {
        this.consultantUserName = consultantUserName;
    }

    public Integer getStudentLimit() {
        return this.studentLimit;
    }

    public void setStudentLimit(Integer studentLimit) {
        this.studentLimit = studentLimit;
    }

}