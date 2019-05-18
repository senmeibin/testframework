package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.ClassStudent;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crm_class_student")
public class ClassStudentExt extends ClassStudent {
    private static final long serialVersionUID = -20180325104933280L;
}