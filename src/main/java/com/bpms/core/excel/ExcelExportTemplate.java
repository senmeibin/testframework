package com.bpms.core.excel;

import java.util.List;

public class ExcelExportTemplate<T> {
    private String sheetName;

    private List<ExcelColInfo> colList;

    private List<T> contents;

    public List<ExcelColInfo> getColList() {
        return colList;
    }

    public void setColList(List<ExcelColInfo> colList) {
        this.colList = colList;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
