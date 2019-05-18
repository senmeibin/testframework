package com.bpms.demo.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 导师表实体类
 */
@MappedSuperclass
public class Tutor extends DemoBaseEntity {
    private static final long serialVersionUID = -20170420113044100L;

    /**
     * 所属基地
     */
    @Column(length = 32)
    private String baseUid;

    /**
     * 所属基地
     */
    @Transient
    private String baseName;

    /**
     * 姓名
     */
    @Length(max = 32, message = "请在姓名中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String tutorName;

    /**
     * 单位
     */
    @Length(max = 32, message = "请在单位中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String tutorUnit;

    /**
     * 职务
     */
    @Length(max = 32, message = "请在职务中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String tutorPosition;

    /**
     * 联系方法
     */
    @Length(max = 256, message = "请在联系方法中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String contactInfo;

    /**
     * 方向
     */
    @Length(max = 64, message = "请在方向中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String direction;

    public Tutor() {
    }

    public String getBaseUid() {
        return this.baseUid;
    }

    public void setBaseUid(String baseUid) {
        this.baseUid = baseUid;
    }

    public String getBaseName() {
        return this.baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getTutorName() {
        return this.tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorUnit() {
        return this.tutorUnit;
    }

    public void setTutorUnit(String tutorUnit) {
        this.tutorUnit = tutorUnit;
    }

    public String getTutorPosition() {
        return this.tutorPosition;
    }

    public void setTutorPosition(String tutorPosition) {
        this.tutorPosition = tutorPosition;
    }

    public String getContactInfo() {
        return this.contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}