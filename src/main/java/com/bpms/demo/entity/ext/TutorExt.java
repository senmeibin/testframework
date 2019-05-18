package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.Tutor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_tutor")
public class TutorExt extends Tutor {
    private static final long serialVersionUID = -20170420113044200L;
}