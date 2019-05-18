package com.bpms.sys.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 路径权限树形实体类
 */
@MappedSuperclass
public class UrlRoleTree implements Serializable {
    private static final long serialVersionUID = -20161028160714458L;

    /**
     * 访问路径
     */
    private String urlStr;

    /**
     * 父模块
     */
    private String parentModule;

    /**
     * 描述
     */
    private String name;

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public String getParentModule() {
        return parentModule;
    }

    public void setParentModule(String parentModule) {
        this.parentModule = parentModule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}