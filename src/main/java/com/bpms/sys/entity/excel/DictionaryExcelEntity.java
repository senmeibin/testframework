package com.bpms.sys.entity.excel;

import com.bpms.core.excel.ExcelColumn;
import com.bpms.core.excel.ExcelEntity;

/**
 * 数据字典实体类
 */
@ExcelEntity
public class DictionaryExcelEntity {
    /**
     * 备注
     */
    @ExcelColumn(name = "备注")
    private String remark;
    /**
     * 表示顺序
     */
    @ExcelColumn(name = "表示顺序")
    private Integer dispSeq;
    /**
     * 小区分CD
     */
    @ExcelColumn(name = "小区分CD", required = true)
    private String subCd;
    /**
     * 大区分CD
     */
    @ExcelColumn(name = "大区分CD", required = true)
    private String mainCd;
    /**
     * 小区分名称
     */
    @ExcelColumn(name = "小区分名称", required = true)
    private String subName;
    /**
     * 大区分名称
     */
    @ExcelColumn(name = "大区分名称", required = true)
    private String mainName;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getMainCd() {
        return mainCd;
    }

    public void setMainCd(String mainCd) {
        this.mainCd = mainCd;
    }

    public String getSubCd() {
        return subCd;
    }

    public void setSubCd(String subCd) {
        this.subCd = subCd;
    }
}