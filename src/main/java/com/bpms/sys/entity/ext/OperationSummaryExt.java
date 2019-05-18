package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.OperationSummary;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_operation_summary")
public class OperationSummaryExt extends OperationSummary {
    private static final long serialVersionUID = -20180424145158937L;

    /**
     * 是否为忽略路径 0 否 非0 是
     */
    @Transient
    private Integer isIgnore = 0;

    /**
     * 画面浏览平均量
     */
    @Transient
    private Integer averageCount = 0;

    public Integer getAverageCount() {
        return averageCount;
    }

    public void setAverageCount(Integer averageCount) {
        this.averageCount = averageCount;
    }

    public Integer getIsIgnore() {
        return isIgnore;
    }

    public void setIsIgnore(Integer isIgnore) {
        this.isIgnore = isIgnore;
    }
}