package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.Campus;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "crm_campus")
public class CampusExt extends Campus {
    private static final long serialVersionUID = -20180319191308237L;

    /**
     * 校区顾问UIDS
     */
    @Transient
    private String consultantUserUids;

    /**
     * 校区顾问名称
     */
    @Transient
    private String consultantUserNames;

    public String getConsultantUserUids() {
        return consultantUserUids;
    }

    public void setConsultantUserUids(String consultantUserUids) {
        this.consultantUserUids = consultantUserUids;
    }

    public String getConsultantUserNames() {
        return consultantUserNames;
    }

    public void setConsultantUserNames(String consultantUserNames) {
        this.consultantUserNames = consultantUserNames;
    }
}