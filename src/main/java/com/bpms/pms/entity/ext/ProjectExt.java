package com.bpms.pms.entity.ext;

import javax.persistence.*;
import com.bpms.pms.entity.Project;

@Entity
@Table(name = "pms_project")
public class ProjectExt extends Project{
    private static final long serialVersionUID = -20180510212515269L;
}