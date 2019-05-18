package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.DeptManage;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_dept_manage")
public class DeptManageExt extends DeptManage {
    private static final long serialVersionUID = -20161129144250447L;
}