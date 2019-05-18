package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.EntrepreneurialActivity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "demo_entrepreneurial_activity")
public class EntrepreneurialActivityExt extends EntrepreneurialActivity {
    private static final long serialVersionUID = -20170420173948309L;

    /**
     * 参与导师UID
     */
    @Transient
    private String tutorUid;

    /**
     * 参与导师
     */
    @Transient
    private String tutorName;

    public String getTutorUid() {
        return tutorUid;
    }

    public void setTutorUid(String tutorUid) {
        this.tutorUid = tutorUid;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }
}