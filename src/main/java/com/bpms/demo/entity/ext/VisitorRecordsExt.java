package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.VisitorRecords;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_visitor_records")
public class VisitorRecordsExt extends VisitorRecords {
    private static final long serialVersionUID = -20170421164346560L;
}