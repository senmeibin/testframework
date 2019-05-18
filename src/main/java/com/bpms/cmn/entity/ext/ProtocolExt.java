package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.Protocol;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cmn_protocol")
public class ProtocolExt extends Protocol {
    private static final long serialVersionUID = -20180329100006151L;
}