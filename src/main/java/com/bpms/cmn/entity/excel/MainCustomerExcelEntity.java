package com.bpms.cmn.entity.excel;

import com.bpms.core.excel.ExcelColumn;
import com.bpms.core.excel.ExcelEntity;

/**
 * 主客户excel实体类
 */
@ExcelEntity
public class MainCustomerExcelEntity {
    /**
     * 主客户名称
     */
    @ExcelColumn(name = "主客户名称", required = true)
    private String mainCustomerName;
    /**
     * 主客户简称
     */
    @ExcelColumn(name = "主客户简称")
    private String mainCustomerAbbreviation;
    /**
     * 联系人
     */
    @ExcelColumn(name = "联系人")
    private String contactName;
    /**
     * 联系电话
     */
    @ExcelColumn(name = "联系电话")
    private String contactTelephone;
    /**
     * 客户概况
     */
    @ExcelColumn(name = "客户概况")
    private String introduction;
    /**
     * 备注
     */
    @ExcelColumn(name = "备注")
    private String remark;

    public String getMainCustomerName() {
        return mainCustomerName;
    }

    public void setMainCustomerName(String mainCustomerName) {
        this.mainCustomerName = mainCustomerName;
    }

    public String getMainCustomerAbbreviation() {
        return mainCustomerAbbreviation;
    }

    public void setMainCustomerAbbreviation(String mainCustomerAbbreviation) {
        this.mainCustomerAbbreviation = mainCustomerAbbreviation;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}