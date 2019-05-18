package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.TableSpaceOptimizeLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_table_space_optimize_log")
public class TableSpaceOptimizeLogExt extends TableSpaceOptimizeLog {
    private static final long serialVersionUID = -20161214162424073L;
}