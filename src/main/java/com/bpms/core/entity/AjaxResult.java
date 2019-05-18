package com.bpms.core.entity;

import org.apache.commons.lang3.StringUtils;

public class AjaxResult {
    /**
     * 处理结果
     */
    private int result = 1;

    /**
     * 主键值
     */
    private Object pk = "";

    /**
     * 消息内容
     */
    private String message = "";

    /**
     * 处理结果内容（用于存放列表数据及其他相关处理结果数据）
     */
    private Object content;

    /**
     * 系统时间
     */
    private Long systemTime;

    public AjaxResult() {

    }

    public AjaxResult(int result, Object pk, String message, Object content) {
        this.result = result;
        this.pk = pk;
        this.message = message;
        this.content = content;
        this.systemTime = System.currentTimeMillis();
    }

    public static AjaxResult createFailResult(String message) {
        return new AjaxResult(-1, StringUtils.EMPTY, message, null);
    }

    public static AjaxResult createSuccessResult(Object pk, String message, Object contents) {
        return new AjaxResult(1, pk, message, contents);
    }

    public static AjaxResult createSuccessResult(String message, Object contents) {
        return createSuccessResult(StringUtils.EMPTY, message, contents);
    }

    public static AjaxResult createSuccessResult(String message) {
        return createSuccessResult(message, null);
    }

    public static AjaxResult createSuccessResult() {
        return createSuccessResult(StringUtils.EMPTY);
    }

    /**
     * Ajax处理是否成功
     *
     * @return true：成功/false：失败
     */
    public Boolean isSuccess() {
        if (result > 0) {
            return true;
        }
        return false;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getPk() {
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

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Long getSystemTime() {
        this.systemTime = System.currentTimeMillis();
        return this.systemTime;
    }
}
