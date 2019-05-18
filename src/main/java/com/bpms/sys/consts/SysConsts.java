package com.bpms.sys.consts;

public class SysConsts {
    /**
     * 部门层级：集团
     */
    public static final int DEPT_TYPE_GROUP = 0;

    /**
     * 部门层级：大区
     */
    public static final int DEPT_TYPE_AREA = 1;

    /**
     * 部门层级：分公司
     */
    public static final int DEPT_TYPE_COMPANY = 2;

    /**
     * 部门层级：部门
     */
    public static final int DEPT_TYPE_DEPARTMENT = 3;

    /**
     * 数据权限
     */
    public static final String PERMISSION_TYPE_DATA = "02";

    /**
     * 功能权限
     */
    public static final String PERMISSION_TYPE_RESOURCE = "01";

    /**
     * 任务执行状态 - 待执行
     */
    public static final String TASK_STATUS_WAITING = "01";

    /**
     * 任务执行状态 - 执行中
     */
    public static final String TASK_STATUS_RUNNING = "02";

    /**
     * 任务执行状态 - 已停止
     */
    public static final String TASK_STATUS_STOPPED = "09";
}
