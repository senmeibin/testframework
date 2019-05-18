package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 标签实体类
 */
@MappedSuperclass
public class UrlRole extends SysBaseEntity {
    private static final long serialVersionUID = -20161028160714458L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 访问路径
     */
    @Length(max = 128, message = "请在访问路径中输入[0-128]位以内的文字。")
    @NotEmpty(message = "请输入访问路径。")
    @Column(length = 128, nullable = false)
    private String url;

    /**
     * 角色集合
     */
    @Length(max = 2048, message = "单个访问路径请勿超过60个角色权限")
    @NotEmpty(message = "请选择角色权限。")
    @Column(length = 2048, nullable = false)
    private String roles;

    /**
     * 描述
     */
    @Length(max = 256, message = "请在描述中输入[0-256]位以内的文字。")
    @Column(length = 256)
    private String description = "1";

    public UrlRole() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoles() {
        return this.roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}