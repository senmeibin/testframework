package com.bpms.core.consts;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * 系统共通日志定数定义类
 */
public class LogConsts {
    /**
     * API接口日志处理标记
     */
    public final static Marker API_MARKER = MarkerFactory.getMarker("API");

    /**
     * 定时任务日志处理标记
     */
    public final static Marker TASK_MARKER = MarkerFactory.getMarker("TASK");

    /**
     * DAO数据库日志处理标记
     */
    public final static Marker DAO_MARKER = MarkerFactory.getMarker("DAO");
}
