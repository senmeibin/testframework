package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.SyncEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_sync_entity")
public class SyncEntityExt extends SyncEntity {
    private static final long serialVersionUID = -20161221104949100L;
}