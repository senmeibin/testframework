package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.MessageRead;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cmn_message_read")
public class MessageReadExt extends MessageRead {
    private static final long serialVersionUID = -20180316160010648L;
}