package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.MeetingRecord;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_meeting_record")
public class MeetingRecordExt extends MeetingRecord {
    private static final long serialVersionUID = -20170522181344638L;
}