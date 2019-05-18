package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 附件导入实体类
 */
@MappedSuperclass
public class AttachmentImport extends SysBaseEntity {
    private static final long serialVersionUID = -20171110152214426L;

    /**
     * 企业UID
     */
    @Column(name = "enterprise_uid", length = 8)
    private Integer enterpriseUid;

    /**
     * 企业
     */
    @Transient
    private String enterpriseName;

    /**
     * 应用编号
     */
    @Length(max = 16, message = "请在应用编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入应用编号。")
    @Column(name = "app_code", length = 16, nullable = false)
    private String appCode;

    /**
     * 模块名称
     */
    @Length(max = 64, message = "请在模块名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入模块名称。")
    @Column(name = "module_name", length = 64, nullable = false)
    private String moduleName;

    /**
     * 文件名称
     */
    @Length(max = 256, message = "请在文件名称中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入文件名称。")
    @Column(name = "file_name", length = 256, nullable = false)
    private String fileName;

    /**
     * 文件路径
     */
    @Length(max = 512, message = "请在文件路径中输入[0-512]位以内的文字。")
    @NotEmpty(message = "请输入文件路径。")
    @Column(name = "file_path", length = 512, nullable = false)
    private String filePath;

    /**
     * 文件大小
     */
    @Length(max = 32, message = "请在文件大小中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入文件大小。")
    @Column(name = "file_size", length = 32, nullable = false)
    private String fileSize;

    /**
     * MIME类型
     */
    @Length(max = 32, message = "请在MIME类型中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入MIME类型。")
    @Column(name = "mime_type", length = 32, nullable = false)
    private String mimeType;

    /**
     * 导入批次号
     */
    @Length(max = 32, message = "请在导入批次号中输入[0-32]位以内的文字。")
    @Column(name = "batch_no", length = 32)
    private String batchNo;

    /**
     * 导入属性名1
     */
    @Length(max = 32, message = "请在导入属性名1中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name01", length = 32)
    private String importAttributeName01;

    /**
     * 导入属性名2
     */
    @Length(max = 32, message = "请在导入属性名2中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name02", length = 32)
    private String importAttributeName02;

    /**
     * 导入属性名3
     */
    @Length(max = 32, message = "请在导入属性名3中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name03", length = 32)
    private String importAttributeName03;

    /**
     * 导入属性名4
     */
    @Length(max = 32, message = "请在导入属性名4中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name04", length = 32)
    private String importAttributeName04;

    /**
     * 导入属性名5
     */
    @Length(max = 32, message = "请在导入属性名5中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name05", length = 32)
    private String importAttributeName05;

    /**
     * 导入属性名6
     */
    @Length(max = 32, message = "请在导入属性名6中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name06", length = 32)
    private String importAttributeName06;

    /**
     * 导入属性名7
     */
    @Length(max = 32, message = "请在导入属性名7中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name07", length = 32)
    private String importAttributeName07;

    /**
     * 导入属性名8
     */
    @Length(max = 32, message = "请在导入属性名8中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name08", length = 32)
    private String importAttributeName08;

    /**
     * 导入属性名9
     */
    @Length(max = 32, message = "请在导入属性名9中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name09", length = 32)
    private String importAttributeName09;

    /**
     * 导入属性名10
     */
    @Length(max = 32, message = "请在导入属性名10中输入[0-32]位以内的文字。")
    @Column(name = "import_attribute_name10", length = 32)
    private String importAttributeName10;

    /**
     * 导入属性值1
     */
    @Length(max = 256, message = "请在导入属性值1中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value01", length = 256)
    private String importAttributeValue01;

    /**
     * 导入属性值2
     */
    @Length(max = 256, message = "请在导入属性值2中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value02", length = 256)
    private String importAttributeValue02;

    /**
     * 导入属性值3
     */
    @Length(max = 256, message = "请在导入属性值3中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value03", length = 256)
    private String importAttributeValue03;

    /**
     * 导入属性值4
     */
    @Length(max = 256, message = "请在导入属性值4中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value04", length = 256)
    private String importAttributeValue04;

    /**
     * 导入属性值5
     */
    @Length(max = 256, message = "请在导入属性值5中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value05", length = 256)
    private String importAttributeValue05;

    /**
     * 导入属性值6
     */
    @Length(max = 256, message = "请在导入属性值6中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value06", length = 256)
    private String importAttributeValue06;

    /**
     * 导入属性值7
     */
    @Length(max = 256, message = "请在导入属性值7中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value07", length = 256)
    private String importAttributeValue07;

    /**
     * 导入属性值8
     */
    @Length(max = 256, message = "请在导入属性值8中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value08", length = 256)
    private String importAttributeValue08;

    /**
     * 导入属性值9
     */
    @Length(max = 256, message = "请在导入属性值9中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value09", length = 256)
    private String importAttributeValue09;

    /**
     * 导入属性值10
     */
    @Length(max = 256, message = "请在导入属性值10中输入[0-256]位以内的文字。")
    @Column(name = "import_attribute_value10", length = 256)
    private String importAttributeValue10;

    /**
     * 导入结果
     */
    @Length(max = 32, message = "请在导入结果中输入[0-32]位以内的文字。")
    @Column(name = "import_result", length = 32)
    private String importResult;

    /**
     * 导入详细结果
     */
    @Column(name = "import_result_detail_message")
    private String importResultDetailMessage;

    public AttachmentImport() {
    }

    public Integer getEnterpriseUid() {
        return this.enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getEnterpriseName() {
        return this.enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getImportAttributeName01() {
        return this.importAttributeName01;
    }

    public void setImportAttributeName01(String importAttributeName01) {
        this.importAttributeName01 = importAttributeName01;
    }

    public String getImportAttributeName02() {
        return this.importAttributeName02;
    }

    public void setImportAttributeName02(String importAttributeName02) {
        this.importAttributeName02 = importAttributeName02;
    }

    public String getImportAttributeName03() {
        return this.importAttributeName03;
    }

    public void setImportAttributeName03(String importAttributeName03) {
        this.importAttributeName03 = importAttributeName03;
    }

    public String getImportAttributeName04() {
        return this.importAttributeName04;
    }

    public void setImportAttributeName04(String importAttributeName04) {
        this.importAttributeName04 = importAttributeName04;
    }

    public String getImportAttributeName05() {
        return this.importAttributeName05;
    }

    public void setImportAttributeName05(String importAttributeName05) {
        this.importAttributeName05 = importAttributeName05;
    }

    public String getImportAttributeName06() {
        return this.importAttributeName06;
    }

    public void setImportAttributeName06(String importAttributeName06) {
        this.importAttributeName06 = importAttributeName06;
    }

    public String getImportAttributeName07() {
        return this.importAttributeName07;
    }

    public void setImportAttributeName07(String importAttributeName07) {
        this.importAttributeName07 = importAttributeName07;
    }

    public String getImportAttributeName08() {
        return this.importAttributeName08;
    }

    public void setImportAttributeName08(String importAttributeName08) {
        this.importAttributeName08 = importAttributeName08;
    }

    public String getImportAttributeName09() {
        return this.importAttributeName09;
    }

    public void setImportAttributeName09(String importAttributeName09) {
        this.importAttributeName09 = importAttributeName09;
    }

    public String getImportAttributeName10() {
        return this.importAttributeName10;
    }

    public void setImportAttributeName10(String importAttributeName10) {
        this.importAttributeName10 = importAttributeName10;
    }

    public String getImportAttributeValue01() {
        return this.importAttributeValue01;
    }

    public void setImportAttributeValue01(String importAttributeValue01) {
        this.importAttributeValue01 = importAttributeValue01;
    }

    public String getImportAttributeValue02() {
        return this.importAttributeValue02;
    }

    public void setImportAttributeValue02(String importAttributeValue02) {
        this.importAttributeValue02 = importAttributeValue02;
    }

    public String getImportAttributeValue03() {
        return this.importAttributeValue03;
    }

    public void setImportAttributeValue03(String importAttributeValue03) {
        this.importAttributeValue03 = importAttributeValue03;
    }

    public String getImportAttributeValue04() {
        return this.importAttributeValue04;
    }

    public void setImportAttributeValue04(String importAttributeValue04) {
        this.importAttributeValue04 = importAttributeValue04;
    }

    public String getImportAttributeValue05() {
        return this.importAttributeValue05;
    }

    public void setImportAttributeValue05(String importAttributeValue05) {
        this.importAttributeValue05 = importAttributeValue05;
    }

    public String getImportAttributeValue06() {
        return this.importAttributeValue06;
    }

    public void setImportAttributeValue06(String importAttributeValue06) {
        this.importAttributeValue06 = importAttributeValue06;
    }

    public String getImportAttributeValue07() {
        return this.importAttributeValue07;
    }

    public void setImportAttributeValue07(String importAttributeValue07) {
        this.importAttributeValue07 = importAttributeValue07;
    }

    public String getImportAttributeValue08() {
        return this.importAttributeValue08;
    }

    public void setImportAttributeValue08(String importAttributeValue08) {
        this.importAttributeValue08 = importAttributeValue08;
    }

    public String getImportAttributeValue09() {
        return this.importAttributeValue09;
    }

    public void setImportAttributeValue09(String importAttributeValue09) {
        this.importAttributeValue09 = importAttributeValue09;
    }

    public String getImportAttributeValue10() {
        return this.importAttributeValue10;
    }

    public void setImportAttributeValue10(String importAttributeValue10) {
        this.importAttributeValue10 = importAttributeValue10;
    }

    public String getImportResult() {
        return this.importResult;
    }

    public void setImportResult(String importResult) {
        this.importResult = importResult;
    }

    public String getImportResultDetailMessage() {
        return this.importResultDetailMessage;
    }

    public void setImportResultDetailMessage(String importResultDetailMessage) {
        this.importResultDetailMessage = importResultDetailMessage;
    }

}