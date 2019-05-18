package com.bpms.sys.entity;

import com.bpms.cmn.entity.CmnBaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 自定义设置实体类
 */
@MappedSuperclass
public class CustomSetting extends CmnBaseEntity {
    private static final long serialVersionUID = -20160913105431661L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 用户UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择用户。")
    private String userUid;

    /**
     * 用户
     */
    @Transient
    private String userName;

    /**
     * 画面标识
     */
    @Length(max = 64, message = "请在画面标识中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入画面标识。")
    @Column(length = 64, nullable = false)
    private String pageInstance;

    /**
     * 设置类型
     */
    @Range(min = 0, max = 99999999, message = "请在设置类型中输入[0-99999999]范围内的数值。")
    @NotNull(message = "请输入设置类型。")
    @Column(nullable = false)
    private Integer settingType = 1;

    /**
     * 设置标题
     */
    @Length(max = 64, message = "请在设置标题中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String settingTitle;

    /**
     * 配置参数
     */
    @Column(nullable = false)
    @NotNull(message = "请输入配置参数。")
    private String parameters;

    public CustomSetting() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getUserUid() {
        return this.userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPageInstance() {
        return this.pageInstance;
    }

    public void setPageInstance(String pageInstance) {
        this.pageInstance = pageInstance;
    }

    public Integer getSettingType() {
        return this.settingType;
    }

    public void setSettingType(Integer settingType) {
        this.settingType = settingType;
    }

    public String getSettingTitle() {
        return this.settingTitle;
    }

    public void setSettingTitle(String settingTitle) {
        this.settingTitle = settingTitle;
    }

    public String getParameters() {
        return this.parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

}