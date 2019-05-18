package com.bpms.sys.entity.excel;

import com.bpms.core.excel.ExcelColumn;
import com.bpms.core.excel.ExcelEntity;

/**
 * 参数配置excel实体类
 */
@ExcelEntity
public class ParameterExcelEntity {

    /**
     * 应用编号
     */
    @ExcelColumn(name = "应用编号", required = true)
    private String appCode;

    /**
     * 参数描述
     */
    @ExcelColumn(name = "参数描述", required = true)
    private String description;

    /**
     * 参数名称
     */
    @ExcelColumn(name = "参数名称", required = true)
    private String name;

    /**
     * 参数值
     */
    @ExcelColumn(name = "参数值", required = true)
    private String value;

    /**
     * 备注
     */
    @ExcelColumn(name = "备注")
    private String remark;

    public ParameterExcelEntity() {
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}