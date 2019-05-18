package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.ProtocolAgreeLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cmn_protocol_agree_log")
public class ProtocolAgreeLogExt extends ProtocolAgreeLog {
    private static final long serialVersionUID = -20180329100121413L;
}