package com.bpms.sys.scheduler;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.scheduler.BaseTask;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.SchedulerUtils;
import com.bpms.sys.entity.SysBaseEntity;
import com.bpms.sys.entity.ext.*;
import com.bpms.sys.service.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时迁移日志数据
 */
@Component
public class DataMigrationTask extends BaseTask {
    /**
     * 短信日志保存天数
     */
    private static final String HISTORY_SMS_LOG_KEEP_DAYS = "HISTORY_SMS_LOG_KEEP_DAYS";

    /**
     * 邮件日志保存天数
     */
    private static final String HISTORY_MAIL_LOG_KEEP_DAYS = "HISTORY_MAIL_LOG_KEEP_DAYS";

    /**
     * 操作日志保存天数
     */
    private static final String HISTORY_OPERATION_LOG_KEEP_DAYS = "HISTORY_OPERATION_LOG_KEEP_DAYS";

    /**
     * 登录日志保存天数
     */
    private static final String HISTORY_LOGIN_LOG_KEEP_DAYS = "HISTORY_LOGIN_LOG_KEEP_DAYS";

    /**
     * 定时任务执行日志保存天数
     */
    private static final String HISTORY_TASK_EXECUTE_LOG_KEEP_DAYS = "HISTORY_TASK_EXECUTE_LOG_KEEP_DAYS";

    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SmsLogService smsLogService;

    @Autowired
    SmsLogHistoryService smsLogHistoryService;

    @Autowired
    MailLogService mailLogService;

    @Autowired
    MailLogHistoryService mailLogHistoryService;

    @Autowired
    OperationLogService operationLogService;

    @Autowired
    OperationLogHistoryService operationLogHistoryService;

    @Autowired
    LoginLogService loginLogService;

    @Autowired
    LoginLogHistoryService loginLogHistoryService;

    @Autowired
    ExecuteLogService executeLogService;

    @Autowired
    ExecuteLogHistoryService executeLogHistoryService;

    /**
     * 定时迁移短信日志
     */
    @Transactional
    public void migrationSmsLog() {
        migration("migrationSmsLog", "定时迁移短信日志", HISTORY_SMS_LOG_KEEP_DAYS, smsLogService, SmsLogExt.class, smsLogHistoryService, SmsLogHistoryExt.class);
    }

    /**
     * 定时迁移邮件日志
     */
    @Transactional
    public void migrationMailLog() {
        migration("migrationMailLog", "定时迁移邮件日志", HISTORY_MAIL_LOG_KEEP_DAYS, mailLogService, MailLogExt.class, mailLogHistoryService, MailLogHistoryExt.class);
    }

    /**
     * 定时迁移操作日志
     */
    @Transactional
    public void migrationOperationLog() {
        migration("migrationOperationLog", "定时迁移操作日志", HISTORY_OPERATION_LOG_KEEP_DAYS, operationLogService, OperationLogExt.class, loginLogHistoryService, OperationLogHistoryExt.class);
    }

    /**
     * 定时迁移登录日志
     */
    @Transactional
    public void migrationLoginLog() {
        migration("migrationLoginLog", "定时迁移登录日志", HISTORY_LOGIN_LOG_KEEP_DAYS, loginLogService, LoginLogExt.class, loginLogHistoryService, LoginLogHistoryExt.class);
    }

    /**
     * 定时迁移定时任务执行日志
     */
    @Transactional
    public void migrationTaskExecuteLog() {
        migration("migrationTaskExecuteLog", "定时迁移定时任务执行日志", HISTORY_TASK_EXECUTE_LOG_KEEP_DAYS, executeLogService, ExecuteLogExt.class, executeLogHistoryService, ExecuteLogHistoryExt.class);
    }

    /**
     * 日志数据迁移
     *
     * @param key            参数关键字
     * @param logService     日志service
     * @param logCls         日志实体类
     * @param historyService 历史日志service
     * @param historyCls     历史日志实体类
     * @param <S>            日志service
     * @param <H>            历史日志service
     * @param <T>            日志实体类
     * @param <TT>           历史日志实体类
     */
    private <S extends SysBaseService, H extends SysBaseService, T extends SysBaseEntity, TT extends SysBaseEntity> void migration(String functionMethod, String functionName, String key, S logService, Class<T> logCls, H historyService, Class<TT> historyCls) {
        //非任务处理服务器的场合、处理中止
        if (!this.isTaskServer) {
            return;
        }

        //锁定执行记录
        ExecuteRecordExt executeRecord = this.lockExecute(this.getClass().getName(), functionMethod, functionName, "系统管理", "系统管理", SchedulerUtils.FUNCTION_TYPE_TASK, "每天夜间系统空闲期间定时迁移指定期间前的日志数据");

        //锁定失败的场合、处理中止
        if (executeRecord == null || !executeRecord.isLockSuccess()) {
            return;
        }

        long startTime = System.currentTimeMillis();
        log.info("定时迁移日志数据({})[开始]", historyCls);

        boolean isException = false;
        try {
            //获取保持时间
            int days = parameterService.getIntValue(AppCodeConsts.APP_SYS, key, 365);

            //获取指定日期的数据
            Map<String, Object> condition = new HashMap<>();
            condition.put("main.insert_date$<=", DateUtils.format(DateUtils.addDate(DateUtils.getNowDate(), -days)));
            List<T> list = logService.search(logCls, logService.getSearchSQL(condition), condition);

            int success = 0;
            Long start = System.currentTimeMillis();
            //存在指定的数据，插入
            if (CollectionUtils.isNotEmpty(list)) {
                for (T entity : list) {
                    //创建历史日志实体
                    TT historyEntity;
                    try {
                        historyEntity = historyCls.newInstance();
                    } catch (Exception e) {
                        logger.error("初始化实体类失败", e);
                        return;
                    }
                    //拷贝数据
                    try {
                        PropertyUtils.copyProperties(historyEntity, entity);
                    } catch (Exception e) {
                        logger.error("拷贝实体类数据异常", e);
                        continue;
                    }
                    try {
                        PropertyUtils.setProperty(historyEntity, "migrationDate", new Date());
                    } catch (Exception e) {
                        logger.error("设置属性migrationDate异常", e);
                    }
                    //保存历史日志数据
                    historyService.getDao().save(historyEntity);
                    //删除日志数据
                    logService.delete("uid", entity.getUid());
                    success++;
                }
            }

            //新增执行日志
            this.insertExecuteLog(executeRecord, this.getExecuteJson(list), 1, null, null, list.size());

            if (logger.isDebugEnabled()) {
                logger.debug("实体类{}本次迁移{}条数据，耗时{}ms", logCls.getName(), success, System.currentTimeMillis() - start);
            }
        } catch (Exception e) {
            isException = true;
            //新增执行异常日志
            this.insertExecuteLog(executeRecord, null, -1, "EXCEPTION", e.getMessage(), 0);
        } finally {
            log.info("定时迁移日志数据({})[结束]-耗时：{}毫秒", historyCls, System.currentTimeMillis() - startTime);

            //释放执行记录上的锁
            this.unlockExecute(executeRecord, isException);
        }
    }
}