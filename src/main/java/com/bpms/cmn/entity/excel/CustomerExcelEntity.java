package com.bpms.cmn.entity.excel;

import com.bpms.core.excel.ExcelColumn;
import com.bpms.core.excel.ExcelEntity;

/**
 * 合同客户excel实体类
 */
@ExcelEntity
public class CustomerExcelEntity {
    /**
     * 客户名称
     */
    @ExcelColumn(name = "客户名称", required = true)
    private String customerName;
    /**
     * 客户简称
     */
    @ExcelColumn(name = "客户简称")
    private String customerAbbreviation;
    /**
     * 客户地址
     */
    @ExcelColumn(name = "客户地址")
    private String address;
    /**
     * 联系电话
     */
    @ExcelColumn(name = "联系电话")
    private String telephone;
    /**
     * 开票抬头
     */
    @ExcelColumn(name = "开票抬头")
    private String invoiceTitle;
    /**
     * 开票地址
     */
    @ExcelColumn(name = "开票地址")
    private String invoiceRegisteredAddress;

    /**
     * 开票电话
     */
    @ExcelColumn(name = "开票电话")
    private String invoiceTelephone;
    /**
     * 开户行
     */
    @ExcelColumn(name = "开户行")
    private String invoiceBank;
    /**
     * 开户账号
     */
    @ExcelColumn(name = "开户账号")
    private String invoiceAccountNo;
    /**
     * 开户税号
     */
    @ExcelColumn(name = "开户税号")
    private String invoiceTaxNo;
    /**
     * 所属行业
     */
    @ExcelColumn(name = "所属行业")
    private String industryName;
    /**
     * 所属行业代码
     */
    @ExcelColumn(name = "行业代码")
    private String industryCd;
    /**
     * 备注
     */
    @ExcelColumn(name = "备注")
    private String remark;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAbbreviation() {
        return customerAbbreviation;
    }

    public void setCustomerAbbreviation(String customerAbbreviation) {
        this.customerAbbreviation = customerAbbreviation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceRegisteredAddress() {
        return invoiceRegisteredAddress;
    }

    public void setInvoiceRegisteredAddress(String invoiceRegisteredAddress) {
        this.invoiceRegisteredAddress = invoiceRegisteredAddress;
    }

    public String getInvoiceTelephone() {
        return invoiceTelephone;
    }

    public void setInvoiceTelephone(String invoiceTelephone) {
        this.invoiceTelephone = invoiceTelephone;
    }

    public String getInvoiceBank() {
        return invoiceBank;
    }

    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank;
    }

    public String getInvoiceAccountNo() {
        return invoiceAccountNo;
    }

    public void setInvoiceAccountNo(String invoiceAccountNo) {
        this.invoiceAccountNo = invoiceAccountNo;
    }

    public String getInvoiceTaxNo() {
        return invoiceTaxNo;
    }

    public void setInvoiceTaxNo(String invoiceTaxNo) {
        this.invoiceTaxNo = invoiceTaxNo;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryCd() {
        return industryCd;
    }

    public void setIndustryCd(String industryCd) {
        this.industryCd = industryCd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}