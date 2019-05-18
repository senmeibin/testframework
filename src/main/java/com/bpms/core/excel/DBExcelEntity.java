package com.bpms.core.excel;

@ExcelEntity(titleRow = 6)
public class DBExcelEntity {
    @ExcelColumn(name = "行号", required = true)
    private String rowNo;

    @ExcelColumn(name = "字段名称")
    private String fieldName;

    @ExcelColumn(name = "列名称")
    private String columnName;

    @ExcelColumn(name = "数据类型")
    private String dataType;

    @ExcelColumn(name = "大小")
    private String size;

    @ExcelColumn(name = "必须")
    private String required;

    @ExcelColumn(name = "是否主键")
    private String isPk;

    @ExcelColumn(name = "是否自增")
    private String isAuto;

    @ExcelColumn(name = "默认值")
    private String defaultValue;

    @ExcelColumn(name = "备注说明")
    private String remark;

    public String getRowNo() {
        return rowNo;
    }

    public void setRowNo(String rowNo) {
        this.rowNo = rowNo;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getIsPk() {
        return isPk;
    }

    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }

    public String getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(String isAuto) {
        this.isAuto = isAuto;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
