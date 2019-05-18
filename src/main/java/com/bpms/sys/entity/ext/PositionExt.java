package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Position;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_position")
public class PositionExt extends Position {
    private static final long serialVersionUID = -635944275330326978L;
}