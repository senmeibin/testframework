package com.bpms.crm.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 活动参与校区实体类
 */
@MappedSuperclass
public class ActivityCampus extends CrmBaseEntity {
    private static final long serialVersionUID = -20180322180818026L;

    /**
     * 活动UID
     */
    @Column(name = "activity_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择活动。")
    private String activityUid;

    /**
     * 活动
     */
    @Transient
    private String activityName;

    /**
     * 校区UID
     */
    @Column(name = "campus_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择校区。")
    private String campusUid;

    /**
     * 校区
     */
    @Transient
    private String campusName;

    public ActivityCampus() {
    }

    public String getActivityUid() {
        return this.activityUid;
    }

    public void setActivityUid(String activityUid) {
        this.activityUid = activityUid;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCampusUid() {
        return this.campusUid;
    }

    public void setCampusUid(String campusUid) {
        this.campusUid = campusUid;
    }

    public String getCampusName() {
        return this.campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

}