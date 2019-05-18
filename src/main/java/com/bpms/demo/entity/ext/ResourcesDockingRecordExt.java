package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.ResourcesDockingRecord;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_resources_docking_record")
public class ResourcesDockingRecordExt extends ResourcesDockingRecord {
    private static final long serialVersionUID = -20170510174729367L;
}