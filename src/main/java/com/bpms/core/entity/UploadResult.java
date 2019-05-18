package com.bpms.core.entity;

public class UploadResult {
    private static final long serialVersionUID = 2016090657040231966L;

    /**
     * 処理結果
     */
    private int result = 1;

    /**
     * キー
     */
    private String pk = "";

    /**
     * メッセージ
     */
    private String message = "";

    /**
     * 処理内容
     */
    private Object contents;

    /**
     * 原图URL(图片上传有效)
     */
    private String pictureUrl;

    /**
     * 大图URL(图片上传有效)
     */
    private String bigPictureUrl;

    /**
     * 小图图URL(图片上传有效)
     */
    private String smallPictureUrl;

    /**
     * size
     */
    private String fileSize;

    /**
     * mimeType
     */
    private String mimeType;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * moduleName
     */
    private String moduleName;


    public UploadResult() {

    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContents() {
        return contents;
    }

    public void setContents(Object contents) {
        this.contents = contents;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getBigPictureUrl() {
        return bigPictureUrl;
    }

    public void setBigPictureUrl(String bigPictureUrl) {
        this.bigPictureUrl = bigPictureUrl;
    }

    public String getSmallPictureUrl() {
        return smallPictureUrl;
    }

    public void setSmallPictureUrl(String smallPictureUrl) {
        this.smallPictureUrl = smallPictureUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
