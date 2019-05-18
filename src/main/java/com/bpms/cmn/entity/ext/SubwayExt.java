package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.Subway;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cmn_subway")
public class SubwayExt extends Subway {
    private static final long serialVersionUID = -20170724144033528L;
}