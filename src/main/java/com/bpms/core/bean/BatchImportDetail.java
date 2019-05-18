package com.bpms.core.bean;

import com.bpms.core.excel.ExcelColumn;

import java.io.Serializable;

/**
 * 导入结果明细
 */
public class BatchImportDetail implements Serializable {
    //导入结果
    @ExcelColumn(name = "导入结果")
    private String importResult;

    /**
     * 0：成功/-1 ：失败
     */
    private int importResultFlag = 0;

    public String getImportResult() {
        return importResult;
    }

    public void setImportResult(String importResult) {
        this.importResult = importResult;
    }

    public int getImportResultFlag() {
        return importResultFlag;
    }

    public void setImportResultFlag(int importResultFlag) {
        this.importResultFlag = importResultFlag;
    }
}
