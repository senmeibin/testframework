package com.bpms.crm.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 校区顾问实体类
 */
@MappedSuperclass
public class CampusConsultant extends CrmBaseEntity {
    private static final long serialVersionUID = -20180320085525131L;

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

    /**
     * 课程顾问UID
     */
    @Column(name = "consultant_user_uid", length = 32, nullable = false)
    @NotEmpty(message = "请选择课程顾问。")
    private String consultantUserUid = "01";

    /**
     * 课程顾问
     */
    @Transient
    private String consultantUserName;

    public CampusConsultant() {
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

    public String getConsultantUserUid() {
        return this.consultantUserUid;
    }

    public void setConsultantUserUid(String consultantUserUid) {
        this.consultantUserUid = consultantUserUid;
    }

    public String getConsultantUserName() {
        return this.consultantUserName;
    }

    public void setConsultantUserName(String consultantUserName) {
        this.consultantUserName = consultantUserName;
    }

}