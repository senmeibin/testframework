package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 参数配置实体类
 */
@MappedSuperclass
public class Parameter extends SysBaseEntity {
    private static final long serialVersionUID = -20160411092121421L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 系统编号
     */
    @Length(max = 16, message = "请在系统编号中输入[0-16]位以内的文字。")
    @Column(length = 16)
    private String systemCode;

    /**
     * 应用编号
     */
    @Length(max = 16, message = "请在应用编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入应用编号。")
    @Column(length = 16, nullable = false)
    private String appCode;

    /**
     * 参数描述
     */
    @Length(max = 64, message = "请在参数描述中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入参数描述。")
    @Column(length = 64, nullable = false)
    private String description;

    /**
     * 参数名称
     */
    @Length(max = 64, message = "请在参数名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入参数名称。")
    @Column(length = 64, nullable = false)
    private String name;

    /**
     * 参数值
     */
    @Length(max = 16777215, message = "请在参数值中输入[0-16777215]位以内的文字。")
    @NotEmpty(message = "请输入参数值。")
    @Column(length = 16777215, nullable = false)
    private String value;

    /**
     * 系统注册
     */
    @Column
    private Integer regSystem = 0;

    public Parameter() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRegSystem() {
        return regSystem;
    }

    public void setRegSystem(Integer regSystem) {
        this.regSystem = regSystem;
    }
}