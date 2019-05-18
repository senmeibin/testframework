package com.bpms.core.consts;

public final class CmnConsts {
    /**
     * 驼峰字段标识位
     */
    public static final String CAMEL_FIELD_FLAG = "^C";

    /**
     * 系统平台数据同步接口
     */
    public static final String DATA_IMPORT_SYS_API = "DATA_IMPORT_SYS_API";

    /**
     * 系统共通数据同步接口
     */
    public static final String DATA_IMPORT_CMN_API = "DATA_IMPORT_CMN_API";

    /**
     * 分页大小
     */
    public static final int PAGE_SIZE = 10;

    /**
     * 日期格式
     */
    public static final String DATE_FORMAT = "yyyy/MM/dd";

    /**
     * 日期格式(短格式)
     */
    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";

    /**
     * 日期时间格式("/"连接)
     */
    public final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

    /**
     * 日期时间格式("-"连接)
     * 注：系统日期默认"/"连接，本静态变量外部方法慎用
     */
    public final static String DATE_TIME_FORMAT_BAR = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期时间格式(短格式)
     */
    public final static String DATE_TIME_FORMAT_SHORT = "yyyyMMddHHmmss";

    /**
     * 记录状态 1:有效
     */
    public static final String RECORD_STATUS_ACTIVE = "1";

    /**
     * 记录状态8:停止
     */
    public static final String RECORD_STATUS_STOP = "8";

    /**
     * 记录状态 9:删除
     */
    public static final String RECORD_STATUS_DELETE = "9";

    /**
     * 日志级别：普通
     */
    public final static Integer SYS_LOGS_LEVEL_LOG = 1;

    /**
     * 日志级别：通知
     */
    public final static Integer SYS_LOGS_LEVEL_INFO = 2;

    /**
     * 日志级别：警告
     */
    public final static Integer SYS_LOGS_LEVEL_WARNING = 3;

    /**
     * 日志级别：危险
     */
    public final static Integer SYS_LOGS_LEVEL_DANGER = 4;

    /**
     * 系统管理员UID
     */
    public final static String ADMIN_USER_UID = "20160101010000000333000000000001";

    /**
     * API用户UID
     */
    public final static String API_USER_UID = "20160101010000000333000000000002";

    /**
     * 未知用户UID
     */
    public final static String UNKNOWN_USER_UID = "20160101010000000333000000000003";

    /**
     * 任务用户UID
     */
    public final static String TASK_USER_UID = "20160101010000000333000000000004";

    /**
     * 默认根部门UID
     */
    public final static String ROOT_DEPT_UID = "20160101010000000222000000000001";

    /**
     * 操作手册关联UID
     */
    public final static String MANUAL_RELATION_UID = "20160101010000000111000000000001";

    /**
     * 账号锁定
     */
    public static final Integer ACCOUNT_LOCK = 1;
    /**
     * 登录失败
     */
    public static final Integer LOGIN_RESULT_FAIL = 0;
    /**
     * 登录成功
     */
    public static final Integer LOGIN_RESULT_SUCCESS = 1;
    /**
     * 登录类型:登录
     */
    public static final Integer LOGIN_TYPE_IN = 0;
    /**
     * 强制密码变更:需要
     */
    public static final Integer FORCE_CHANGE_PSWD_YES = 1;
    /**
     * 强制密码变更:不需要
     */
    public static final Integer FORCE_CHANGE_PSWD_NO = 0;

    /**
     * 设置类型:页面
     */
    public static final Integer COLUMN_SETTING_TYPE_PAGE = 1;

    /**
     * 数据保存类型：保存
     */
    public static final String SAVE_TYPE_SAVE = "1";

    /**
     * 数据保存类型：保存&提交
     */
    public static final String SAVE_TYPE_SUBMIT = "2";

    /**
     * 部门层级：分公司
     */
    public static final Integer DEPT_CLASS_COMPANY = 2;

    /**
     * 部门层级：大区
     */
    public static final Integer DEPT_CLASS_AREA = 1;
}
