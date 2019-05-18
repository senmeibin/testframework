package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.CourseSchedule;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crm_course_schedule")
public class CourseScheduleExt extends CourseSchedule {
    private static final long serialVersionUID = -20180413173352748L;
}