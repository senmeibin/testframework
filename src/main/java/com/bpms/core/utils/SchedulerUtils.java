package com.bpms.core.utils;

/**
 * 定时任务工具类
 */
public class SchedulerUtils {
    /**
     * 执行记录 功能类型
     * 01：接口同步 Migration
     */
    public static final String FUNCTION_TYPE_SYNC = "01";

    /**
     * 执行记录 功能类型
     * 02：数据迁移
     */
    public static final String FUNCTION_TYPE_MIGRATION = "02";

    /**
     * 执行记录 功能类型
     * 03:接口调用
     */
    public static final String FUNCTION_TYPE_API = "03";

    /**
     * 执行记录 功能类型
     * 04：自动任务
     */
    public static final String FUNCTION_TYPE_TASK = "04";

    /**
     * 返回结果 成功
     */
    public static final int RESULT_SUCCESS = 1;

    /**
     * 返回结果 失败
     */
    public static final int RESULT_FALSE = -1;
}
