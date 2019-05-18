package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.Activity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "crm_activity")
public class ActivityExt extends Activity {
    private static final long serialVersionUID = -20180322073535889L;

    /**
     * 参与校区UIDS
     */
    @Transient
    private String campusUids;

    /**
     * 参与校区名称
     */
    @Transient
    private String campusNames;

    public String getCampusUids() {
        return campusUids;
    }

    public void setCampusUids(String campusUids) {
        this.campusUids = campusUids;
    }

    public String getCampusNames() {
        return campusNames;
    }

    public void setCampusNames(String campusNames) {
        this.campusNames = campusNames;
    }
}