package com.bpms.demo.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 创业活动导师实体类
 */
@MappedSuperclass
public class EntrepreneurialActivityTutor extends DemoBaseEntity {
    private static final long serialVersionUID = -20170426183452542L;

    /**
     * 创业活动
     */
    @Column(length = 32)
    private String entrepreneurialActivityUid;

    /**
     * 创业活动
     */
    @Transient
    private String entrepreneurialActivityName;

    /**
     * 参与导师
     */
    @Column(length = 32)
    private String tutorUid;

    /**
     * 参与导师
     */
    @Transient
    private String tutorName;

    public EntrepreneurialActivityTutor() {
    }

    public String getEntrepreneurialActivityUid() {
        return this.entrepreneurialActivityUid;
    }

    public void setEntrepreneurialActivityUid(String entrepreneurialActivityUid) {
        this.entrepreneurialActivityUid = entrepreneurialActivityUid;
    }

    public String getEntrepreneurialActivityName() {
        return this.entrepreneurialActivityName;
    }

    public void setEntrepreneurialActivityName(String entrepreneurialActivityName) {
        this.entrepreneurialActivityName = entrepreneurialActivityName;
    }

    public String getTutorUid() {
        return this.tutorUid;
    }

    public void setTutorUid(String tutorUid) {
        this.tutorUid = tutorUid;
    }

    public String getTutorName() {
        return this.tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

}