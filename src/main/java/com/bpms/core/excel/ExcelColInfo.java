package com.bpms.core.excel;

public class ExcelColInfo {
    /**
     * 标题
     */
    private String title;

    /**
     * 属性
     */
    private String colName;

    /**
     * 宽度
     */
    private String width;

    /**
     * 对齐
     */
    private ALIGN cellAlign;

    /**
     * 仅供数据导出列
     */
    private Boolean onlyForExport;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public ALIGN getCellAlign() {
        return cellAlign;
    }

    public void setCellAlign(ALIGN cellAlign) {
        this.cellAlign = cellAlign;
    }

    public Boolean getOnlyForExport() {
        return onlyForExport;
    }

    public void setOnlyForExport(Boolean onlyForExport) {
        this.onlyForExport = onlyForExport;
    }

    enum ALIGN {
        left(0x0),
        center(0x2),
        right(0x3);

        private short value;

        ALIGN(int value) {
            this.value = (short) value;
        }

        public short getValue() {
            return value;
        }

        public void setValue(short value) {
            this.value = value;
        }
    }
}
