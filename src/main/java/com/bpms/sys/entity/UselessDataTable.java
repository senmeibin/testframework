package com.bpms.sys.entity;

import javax.persistence.MappedSuperclass;

/**
 * 无用数据表实体类
 */
@MappedSuperclass
public class UselessDataTable extends SysBaseEntity {
    private static final long serialVersionUID = -20170119114431660L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表名称
     */
    private String tableComment;

    /**
     * 表记录条数
     */
    private Integer recordCount;

    /**
     * 逻辑删除记录数
     */
    private Integer logicDeleteRecordCount;

    public UselessDataTable() {
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getLogicDeleteRecordCount() {
        return this.logicDeleteRecordCount;
    }

    public void setLogicDeleteRecordCount(Integer logicDeleteRecordCount) {
        this.logicDeleteRecordCount = logicDeleteRecordCount;
    }

}