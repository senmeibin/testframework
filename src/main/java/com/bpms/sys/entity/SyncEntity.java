package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 同步实体类实体类
 */
@MappedSuperclass
public class SyncEntity extends SysBaseEntity {
    private static final long serialVersionUID = -20161221104948957L;

    /**
     * 类名称
     */
    @Length(max = 32, message = "请在类名称中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入类名称。")
    @Column(length = 32, nullable = false)
    private String entityClassName;

    /**
     * 类路径
     */
    @Length(max = 256, message = "请在类路径中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入类路径。")
    @Column(length = 256, nullable = false)
    private String entityClassPath;

    public SyncEntity() {
    }

    public String getEntityClassName() {
        return this.entityClassName;
    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public String getEntityClassPath() {
        return this.entityClassPath;
    }

    public void setEntityClassPath(String entityClassPath) {
        this.entityClassPath = entityClassPath;
    }

}