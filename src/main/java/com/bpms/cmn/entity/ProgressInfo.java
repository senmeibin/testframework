package com.bpms.cmn.entity;

/**
 * 进度条信息
 */
public class ProgressInfo {
    /**
     * 正常进度提醒
     */
    public static int MESSAGE_TYPE_INFO = 1;

    /**
     * 警告提醒
     */
    public static int MESSAGE_TYPE_WARNING = 2;

    /**
     * 错误提醒
     */
    public static int MESSAGE_TYPE_ERROR = 3;

    /**
     * 进度状态(0：进行中)
     */
    public static int PROGRESS_STATUS_RUNNING = 0;

    /**
     * 进度状态(1：已完成)
     */
    public static int PROGRESS_STATUS_SUCCESS = 1;

    /**
     * 当前进度
     */
    private Integer currentNum;

    /**
     * 总进度（如：总记录数）
     */
    private Integer totalNum;

    /**
     * 进度条上显示的文本内容
     */
    private String progressText;


    /**
     * 消息类型(1：INFO/2：WARNING/3：ERROR)
     */
    private int messageType = 1;

    /**
     * 进度条下方提示消息内容
     */
    private String messageContent;

    /**
     * 进度状态
     * 0：进行中 1：已完成
     */
    private Integer progressStatus = 0;

    public Integer getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getProgressText() {
        return progressText;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(Integer progressStatus) {
        this.progressStatus = progressStatus;
    }
}
