package com.bpms.core.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@MappedSuperclass
public class CoreEntity implements Serializable {
    private static final long serialVersionUID = 6720864657040231965L;

    /**
     * 数据保存方式
     * 1：保存
     * 2：保存并提交
     */
    @Transient
    protected String saveType;

    /**
     * 编辑模式（0：新增[默认]/1：编辑）
     */
    @Transient
    protected int editMode = 0;

    /**
     * 实体类class(需要UDC同步推送的class)
     */
    @Transient
    private String entityClass;

    /**
     * 是否APP直接推送到UDC的数据
     */
    @Transient
    private boolean isDirectPush = false;

    public boolean isEditMode() {
        return this.editMode == 1 ? true : false;
    }

    public boolean isAddMode() {
        return this.editMode == 0 ? true : false;
    }

    public int getEditMode() {
        return editMode;
    }

    public void setEditMode(int editMode) {
        this.editMode = editMode;
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public boolean isDirectPush() {
        return isDirectPush;
    }

    public void setDirectPush(boolean directPush) {
        isDirectPush = directPush;
    }
}
