package com.bpms.core.bean;

import com.bpms.core.utils.StringUtils;

import java.io.Serializable;

/**
 * 批量文件导入结果反馈专用Entity
 */
public class BatchImportResult implements Serializable {
    /**
     * 成功记录数
     */
    private Integer successCount;

    /**
     * 失败记录数
     */
    private Integer errorCount;

    /**
     * 执行花费时间(hh:mm:ss)
     */
    private String spendTime;

    /**
     * 汇总合计
     */
    private String totalSummary;

    /**
     * 导入记录集合
     */
    private Object batchImportDetailList;

    /**
     * 根据起止时间计算耗费时间（返回HH:mm:ss格式)
     *
     * @param beginTime 开始毫秒值
     * @param endTime   结束毫秒值
     */
    public void setSpendTime(long beginTime, long endTime) {
        this.spendTime = StringUtils.EMPTY;
        long totalMilliSeconds = endTime - beginTime;
        long totalSeconds = totalMilliSeconds / 1000;
        //求出现在的秒
        long currentSecond = totalSeconds % 60;
        //求出现在的分
        long totalMinutes = totalSeconds / 60;
        long currentMinute = totalMinutes % 60;
        long totalHour = totalMinutes / 60;
        long currentHour = totalHour % 24;
        //小时数补齐两位
        if (currentHour < 10) {
            this.spendTime += "0";
        }
        this.spendTime += currentHour + ":";
        //分钟数补齐两位
        if (currentMinute < 10) {
            this.spendTime += "0";
        }
        this.spendTime += currentMinute + ":";
        //秒数补齐两位
        if (currentSecond < 10) {
            this.spendTime += "0";
        }
        this.spendTime += currentSecond;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getErrorCount() {
        return errorCount == null ? 0 : errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public String getSpendTime() {
        return spendTime;
    }

    public String getTotalSummary() {
        return totalSummary;
    }

    public void setTotalSummary(String totalSummary) {
        this.totalSummary = totalSummary;
    }

    public Object getBatchImportDetailList() {
        return batchImportDetailList;
    }

    public void setBatchImportDetailList(Object batchImportDetailList) {
        this.batchImportDetailList = batchImportDetailList;
    }
}
