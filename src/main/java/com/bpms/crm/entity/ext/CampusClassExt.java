package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.CampusClass;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "crm_campus_class")
public class CampusClassExt extends CampusClass {
    private static final long serialVersionUID = -20180323174156242L;

    /**
     * 学员UID
     */
    @Transient
    private String studentUids;

    /**
     * 学员姓名
     */
    @Transient
    private String studentNames;

    /**
     * 学员人数
     */
    @Transient
    private Integer studentCount = 0;

    public String getStudentUids() {
        return studentUids;
    }

    public void setStudentUids(String studentUids) {
        this.studentUids = studentUids;
    }

    public String getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(String studentNames) {
        this.studentNames = studentNames;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }
}