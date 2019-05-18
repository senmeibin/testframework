package com.bpms.core.scheduler;

/**
 * 定时任务接口定义
 */
public interface IBaseTask {
    /**
     * 定时任务处理入口
     */
    void execute();
}
