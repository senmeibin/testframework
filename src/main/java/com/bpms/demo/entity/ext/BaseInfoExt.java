package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.BaseInfo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_base_info")
public class BaseInfoExt extends BaseInfo {
    private static final long serialVersionUID = -20170420113131095L;
}