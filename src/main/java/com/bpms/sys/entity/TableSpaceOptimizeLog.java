package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 表空间优化日志实体类
 */
@MappedSuperclass
public class TableSpaceOptimizeLog extends SysBaseEntity {
    private static final long serialVersionUID = -20161214162423973L;

    /**
     * 逻辑表名称
     */
    @Length(max = 64, message = "请在逻辑表名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入逻辑表名称。")
    @Column(length = 64, nullable = false)
    private String logicName;

    /**
     * 物理表名称
     */
    @Length(max = 64, message = "请在物理表名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入物理表名称。")
    @Column(length = 64, nullable = false)
    private String physicalName;

    /**
     * 优化前占用大小
     */
    @Range(min = 0, max = 99999999, message = "请在优化前占用大小中输入[0-99999999]范围内的数值。")
    @NotNull(message = "请输入优化前占用大小。")
    @Column(nullable = false)
    private BigDecimal optimizeBeforeSize;

    /**
     * 优化后占用大小
     */
    @Range(min = 0, max = 99999999, message = "请在优化后占用大小中输入[0-99999999]范围内的数值。")
    @NotNull(message = "请输入优化后占用大小。")
    @Column(nullable = false)
    private BigDecimal optimizeAfterSize;

    /**
     * 表记录条数
     */
    @Range(min = 0, max = 99999999, message = "请在表记录条数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer recordCount;

    public TableSpaceOptimizeLog() {
    }

    public String getLogicName() {
        return this.logicName;
    }

    public void setLogicName(String logicName) {
        this.logicName = logicName;
    }

    public String getPhysicalName() {
        return this.physicalName;
    }

    public void setPhysicalName(String physicalName) {
        this.physicalName = physicalName;
    }

    public BigDecimal getOptimizeBeforeSize() {
        return this.optimizeBeforeSize;
    }

    public void setOptimizeBeforeSize(BigDecimal optimizeBeforeSize) {
        this.optimizeBeforeSize = optimizeBeforeSize;
    }

    public BigDecimal getOptimizeAfterSize() {
        return this.optimizeAfterSize;
    }

    public void setOptimizeAfterSize(BigDecimal optimizeAfterSize) {
        this.optimizeAfterSize = optimizeAfterSize;
    }

    public Integer getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

}