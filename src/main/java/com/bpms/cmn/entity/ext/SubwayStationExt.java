package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.SubwayStation;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cmn_subway_station")
public class SubwayStationExt extends SubwayStation {
    private static final long serialVersionUID = -20170724144814781L;
}