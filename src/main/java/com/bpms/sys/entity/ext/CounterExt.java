package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Counter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_counter")
public class CounterExt extends Counter {
    private static final long serialVersionUID = -20160923140753608L;
}