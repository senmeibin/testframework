package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 附件文件实体类
 */
@MappedSuperclass
public class Attachment extends SysBaseEntity {
    private static final long serialVersionUID = -20160414163519044L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 应用编号
     */
    @Length(max = 16, message = "请在应用编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入应用编号。")
    @Column(length = 16, nullable = false)
    private String appCode;

    /**
     * 关联UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择关联。")
    private String relationUid;

    /**
     * 附件分类
     */
    @Column(length = 8)
    private String categoryCd;

    /**
     * 关联
     */
    @Transient
    private String relationName;

    /**
     * 文件名称
     */
    @Length(max = 256, message = "请在文件名称中输入[0-256]位以内的文字。")
    @NotEmpty(message = "请输入文件名称。")
    @Column(length = 256, nullable = false)
    private String fileName;

    /**
     * 文件路径
     */
    @Length(max = 512, message = "请在文件路径中输入[0-512]位以内的文字。")
    @NotEmpty(message = "请输入文件路径。")
    @Column(length = 512, nullable = false)
    private String filePath;

    /**
     * 大图路径
     */
    @Length(max = 512, message = "请在大图路径中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String bigImagePath;

    /**
     * 小图路径
     */
    @Length(max = 512, message = "请在小图路径中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String smallImagePath;

    /**
     * 文件大小
     */
    @Length(max = 32, message = "请在文件大小中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入文件大小。")
    @Column(length = 32, nullable = false)
    private String fileSize;

    /**
     * MIME类型
     */
    @Length(max = 32, message = "请在MIME类型中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入MIME类型。")
    @Column(length = 32, nullable = false)
    private String mimeType;

    /**
     * 模块名称
     */
    @Length(max = 64, message = "请在模块名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入模块名称。")
    @Column(length = 64, nullable = false)
    private String moduleName;

    /**
     * 预览次数
     */
    @Range(min = 0L, max = 99999999L, message = "请在预览次数中输入[0-99999999]范围内的数值。")
    @Column(name = "preview_count")
    private Integer previewCount = 0;

    /**
     * 下载次数
     */
    @Range(min = 0L, max = 99999999L, message = "请在下载次数中输入[0-99999999]范围内的数值。")
    @Column(name = "download_count")
    private Integer downloadCount = 0;

    public Attachment() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getRelationUid() {
        return this.relationUid;
    }

    public void setRelationUid(String relationUid) {
        this.relationUid = relationUid;
    }

    public String getCategoryCd() {
        return categoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        this.categoryCd = categoryCd;
    }

    public String getRelationName() {
        return this.relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
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

    public String getBigImagePath() {
        return this.bigImagePath;
    }

    public void setBigImagePath(String bigImagePath) {
        this.bigImagePath = bigImagePath;
    }

    public String getSmallImagePath() {
        return this.smallImagePath;
    }

    public void setSmallImagePath(String smallImagePath) {
        this.smallImagePath = smallImagePath;
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

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getPreviewCount() {
        return previewCount;
    }

    public void setPreviewCount(Integer previewCount) {
        this.previewCount = previewCount;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}