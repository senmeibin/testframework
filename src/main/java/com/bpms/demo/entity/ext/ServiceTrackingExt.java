package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.ServiceTracking;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "demo_service_tracking")
public class ServiceTrackingExt extends ServiceTracking {
    private static final long serialVersionUID = -20170418160144785L;

    /**
     * 所属基地
     */
    @Transient
    private String baseName;

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }
}