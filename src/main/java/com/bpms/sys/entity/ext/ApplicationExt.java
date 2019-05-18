package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Application;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_application")
public class ApplicationExt extends Application {
    private static final long serialVersionUID = -20160408124507195L;
}