package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.StudentAssignLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crm_student_assign_log")
public class StudentAssignLogExt extends StudentAssignLog {
    private static final long serialVersionUID = -20180407210742212L;
}