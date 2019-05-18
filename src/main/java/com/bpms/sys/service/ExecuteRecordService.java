package com.bpms.sys.service;

import com.google.common.collect.Maps;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.HostUtils;
import com.bpms.core.utils.SpringUtils;
import com.bpms.sys.consts.SysConsts;
import com.bpms.sys.dao.ExecuteRecordDao;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.config.ContextLifecycleScheduledTaskRegistrar;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行记录表服务类
 */
@Service
public class ExecuteRecordService extends SysBaseService<ExecuteRecordExt> {
    /**
     * 上下文生命周期计划任务注册器
     */
    @Autowired
    protected ContextLifecycleScheduledTaskRegistrar[] contextLifecycleScheduledTaskRegistrarArray;

    /**
     * 是否是任务处理服务器
     */
    @Value("${task.server.enable:false}")
    protected boolean isTaskServer;

    @Autowired
    private ExecuteRecordDao executeRecordDao;

    @Override
    public ExecuteRecordDao getDao() {
        return executeRecordDao;
    }

    @Override
    public String getSearchSQLPath(Map<String, Object> condition) {
        return "sys/ExecuteRecord/search";
    }

    /**
     * 释放当前机器异常锁定的执行记录
     */
    @PostConstruct
    private void releaseExceptionLock() {
        //非任务处理服务器的场合、处理中止
        if (!this.isTaskServer) {
            return;
        }

        try {
            synchronized (this) {
                Map<String, Object> condition = new HashMap<>();
                //最后执行机器 本机的场合
                condition.put("lastExecuteMachine", HostUtils.getHostAddress());
                this.getDao().executeUpdate(this.getSQL("sys/ExecuteRecord/releaseExceptionLock"), condition);
            }
        } catch (Exception ex) {
            log.error("释放当前机器异常锁定的执行记录失败：" + ex);
        }
    }

    @Override
    public ExecuteRecordExt save(ExecuteRecordExt entity) {
        //多线程同步保存
        synchronized (this) {
            return super.save(entity);
        }
    }

    /**
     * 取得执行记录
     *
     * @param functionCode   功能编码
     * @param functionMethod 功能方法
     * @param functionName   功能名称
     * @param sourceSystem   来源系统
     * @param destSystem     目标系统
     * @param functionType   功能类型
     * @param remark         备注
     * @return 执行记录实体
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized ExecuteRecordExt getExecute(String functionCode, String functionMethod, String functionName, String sourceSystem, String destSystem, String functionType, String remark) {
        //根据功能编码及功能方法取得唯一执行记录
        ExecuteRecordExt executeRecord = this.findByFunctionCodeAndMethod(functionCode, functionMethod);

        //如果查询到有记录 返回该记录 没有则新增
        if (null == executeRecord) {
            executeRecord = this.insertExecuteRecord(functionCode, functionMethod, functionName, sourceSystem, destSystem, functionType, remark);
        }
        else {
            String ip = HostUtils.getHostAddress();
            if (executeRecord != null) {
                //上次任务执行开始日期(【最后执行开始时间】被改写前的备份时间)
                executeRecord.setPreviousExecuteStartDate(executeRecord.getLastExecuteStartDate());

                //最后执行机器
                executeRecord.setLastExecuteMachine(ip);

                //累计执行次数
                if (executeRecord.getTotalExecuteCount() == null) {
                    executeRecord.setTotalExecuteCount(new BigDecimal(0));
                }
                executeRecord.setTotalExecuteCount(executeRecord.getTotalExecuteCount().add(new BigDecimal(1)));

                //保存数据
                save(executeRecord);
            }
        }

        return executeRecord;
    }

    /**
     * 锁定执行记录
     *
     * @param functionCode   功能编码
     * @param functionMethod 功能方法
     * @param functionName   功能名称
     * @param sourceSystem   来源系统
     * @param destSystem     目标系统
     * @param functionType   功能类型
     * @param remark         备注
     * @return 执行记录实体
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized ExecuteRecordExt lockExecute(String functionCode, String functionMethod, String functionName, String sourceSystem, String destSystem, String functionType, String remark) {
        //根据功能编码及功能方法取得唯一执行记录
        ExecuteRecordExt executeRecord = this.findByFunctionCodeAndMethod(functionCode, functionMethod);

        //如果查询到有记录 返回该记录 没有则新增
        if (null == executeRecord) {
            executeRecord = this.insertExecuteRecord(functionCode, functionMethod, functionName, sourceSystem, destSystem, functionType, remark);
            executeRecord.setLockSuccess(true);
            //下次执行时间
            executeRecord.setNextExecuteDate(this.getCurrentTaskNextExecuteDate(executeRecord.getFunctionCode(), executeRecord.getFunctionMethod()));
        }
        else {
            String ip = HostUtils.getHostAddress();
            if (executeRecord != null && executeRecord.isWaiting()) {
                //任务执行中
                executeRecord.setFunctionStatusCd(SysConsts.TASK_STATUS_RUNNING);

                //上次任务执行开始日期(【最后执行开始时间】被改写前的备份时间)
                executeRecord.setPreviousExecuteStartDate(executeRecord.getLastExecuteStartDate());

                //最后执行开始时间
                executeRecord.setLastExecuteStartDate(new Date());

                //最后执行机器
                executeRecord.setLastExecuteMachine(ip);

                //累计执行次数
                if (executeRecord.getTotalExecuteCount() == null) {
                    executeRecord.setTotalExecuteCount(new BigDecimal(0));
                }
                executeRecord.setTotalExecuteCount(executeRecord.getTotalExecuteCount().add(new BigDecimal(1)));

                //下次执行时间
                executeRecord.setNextExecuteDate(this.getCurrentTaskNextExecuteDate(executeRecord.getFunctionCode(), executeRecord.getFunctionMethod()));

                //保存数据
                save(executeRecord);
                executeRecord.setLockSuccess(true);
                if (log.isDebugEnabled()) {
                    log.debug("IP：{} 成功取得任务锁[{}]，class：{}，method：{}", ip, executeRecord.getFunctionName(), executeRecord.getFunctionCode(), executeRecord.getFunctionMethod());
                }
            }
            else if (executeRecord.isRunning()) {
                executeRecord.setLockSuccess(false);
                log.info("IP：{} 未取得任务锁[{}]，class：{}，method：{} 任务执行中，执行锁定机器：{}", ip, executeRecord.getFunctionName(), executeRecord.getFunctionCode(), executeRecord.getFunctionMethod(), executeRecord.getLastExecuteMachine());
            }
            else if (executeRecord.isStopped()) {
                executeRecord.setLockSuccess(false);
                if (log.isDebugEnabled()) {
                    log.debug("IP：{} 未取得任务锁[{}]，class：{}，method：{} 任务已被系统管理员停止", ip, executeRecord.getFunctionName(), executeRecord.getFunctionCode(), executeRecord.getFunctionMethod(), executeRecord.getLastExecuteMachine());
                }
            }
        }

        return executeRecord;
    }

    /**
     * 释放指定功能模块执行记录上的锁
     *
     * @param executeRecord 执行记录实体对象
     * @param isException   异常信息发生  true:发生异常情况的解锁；false:正常情况下的解锁
     * @return true:成功 false:失败
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized boolean unlockExecute(ExecuteRecordExt executeRecord, boolean isException) {
        boolean result = false;
        String ip = HostUtils.getHostAddress();
        //只有锁定机器才可解锁 执行中的任务才可解锁
        //if (executeRecord.isLockSuccess() && executeRecord.isRunning() && StringUtils.equals(executeRecord.getLastExecuteMachine(), ip)) {
        if (executeRecord.isLockSuccess() && executeRecord.isRunning()) {
            //异常情况下的解锁
            if (isException) {
                //最后执行开始时间 值恢复
                executeRecord.setLastExecuteStartDate(executeRecord.getPreviousExecuteStartDate());
            }
            else {
                //最后执行结束时间
                executeRecord.setLastExecuteEndDate(new Date());
            }

            //待执行状态
            executeRecord.setFunctionStatusCd(SysConsts.TASK_STATUS_WAITING);

            //累计执行时间(秒为单位)
            if (executeRecord.getTotalExecuteTime() == null) {
                executeRecord.setTotalExecuteTime(new BigDecimal(0));
            }
            if (null != executeRecord.getLastExecuteEndDate() && null != executeRecord.getLastExecuteStartDate()) {
                BigDecimal time = new BigDecimal(((executeRecord.getLastExecuteEndDate().getTime() - executeRecord.getLastExecuteStartDate().getTime()) / 1000));
                executeRecord.setTotalExecuteTime(executeRecord.getTotalExecuteTime().add(time));
            }
            save(executeRecord);
            if (log.isDebugEnabled()) {
                log.debug("IP：{} 释放任务锁[{}]，class：{}，method：{}", ip, executeRecord.getFunctionName(), executeRecord.getFunctionCode(), executeRecord.getFunctionMethod());
            }
            result = true;
        }
        return result;
    }

    /**
     * 任务状态变更
     *
     * @param uid              任务uid
     * @param functionStatusCd 任务状态(01：待执行、02：执行中、09：停止)
     */
    @Transactional
    @RequiresRoles("SystemManagement")
    public void updateTaskStatus(String uid, String functionStatusCd) {
        ExecuteRecordExt executeRecord = findOne(uid);
        if (executeRecord != null) {
            executeRecord.setFunctionStatusCd(functionStatusCd);
            save(executeRecord);
        }
    }

    /**
     * 新增执行记录
     *
     * @param functionCode   功能编码
     * @param functionMethod 功能方法
     * @param functionName   功能名称
     * @param sourceSystem   来源系统
     * @param destSystem     目标系统
     * @param functionType   功能类型
     * @param remark         备注
     * @return 执行记录实体
     */
    public ExecuteRecordExt insertExecuteRecord(String functionCode, String functionMethod, String functionName, String sourceSystem, String destSystem, String functionType, String remark) {
        ExecuteRecordExt executeRecord = new ExecuteRecordExt();
        //功能编码赋值
        executeRecord.setFunctionCode(functionCode);
        //功能名称赋值
        executeRecord.setFunctionName(functionName);
        //功能方法赋值
        executeRecord.setFunctionMethod(functionMethod);
        //来源系统赋值
        executeRecord.setSourceSystemName(sourceSystem);
        //功能类型 01：接口执行 / 02：数据迁移 / 03:接口调用
        executeRecord.setFunctionTypeCd(functionType);
        //目标系统赋值
        executeRecord.setDestSystemName(destSystem);
        //任务执行中
        executeRecord.setFunctionStatusCd(SysConsts.TASK_STATUS_RUNNING);

        executeRecord.setPreviousExecuteStartDate(DateUtils.parseDate("2010/01/01"));
        //最后执行开始时间
        executeRecord.setLastExecuteStartDate(new Date());
        //最后执行机器
        executeRecord.setLastExecuteMachine(HostUtils.getHostAddress());

        //累计执行时间
        executeRecord.setTotalExecuteTime(new BigDecimal(0));
        //累计执行次数
        executeRecord.setTotalExecuteCount(new BigDecimal(1));

        //备注 （描述实现的具体功能）
        executeRecord.setRemark(remark);
        return this.save(executeRecord);
    }

    /**
     * 根据功能编码及功能方法取得唯一执行记录
     *
     * @param functionCode   功能编码
     * @param functionMethod 功能方法
     * @return 执行记录实体对象
     */
    private ExecuteRecordExt findByFunctionCodeAndMethod(String functionCode, String functionMethod) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("functionCode", functionCode);
        condition.put("functionMethod", functionMethod);
        //锁表操作
        condition.put("forUpdate$ignore_search", true);

        List<ExecuteRecordExt> tasks = search(getSearchSQL(condition), condition);
        return CollectionUtils.isNotEmpty(tasks) ? tasks.get(0) : null;
    }

    /**
     * 手动执行任务
     *
     * @param uid 任务uid
     * @return 执行结果
     */
    public AjaxResult manuallyExecuteTask(String uid) {
        ExecuteRecordExt executeRecord = findOne(uid);
        if (executeRecord == null) {
            return AjaxResult.createFailResult("未取得任务该执行记录，请重试。");
        }

        log.info("IP：{} 手动执行任务[{}]，class：{}，method：{}", HostUtils.getHostAddress(), executeRecord.getFunctionName(), executeRecord.getFunctionCode(), executeRecord.getFunctionMethod());

        try {
            //获取class
            Class taskClass = Class.forName(executeRecord.getFunctionCode());
            //获取方法
            Method method = taskClass.getDeclaredMethod(executeRecord.getFunctionMethod());
            //执行方法
            method.invoke(SpringUtils.getBean(taskClass));
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException:", e);
            return AjaxResult.createFailResult(String.format("获取任务的功能编码(%s)失败。", executeRecord.getFunctionCode()));
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException:", e);
            return AjaxResult.createFailResult(String.format("获取任务的功能方法(%s)失败。", executeRecord.getFunctionMethod()));
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException:", e);
            return AjaxResult.createFailResult("手动执行任务失败。");
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException:", e);
            return AjaxResult.createFailResult("手动执行任务失败。");
        }

        log.info("手动执行任务成功。");
        return AjaxResult.createSuccessResult("手动执行成功。");
    }

    /**
     * 获取当前执行任务
     *
     * @param functionCode   功能编码
     * @param functionMethod 功能方法
     * @return 当前执行任务
     */
    private CronTask getCurrentCronTask(String functionCode, String functionMethod) {
        for (ContextLifecycleScheduledTaskRegistrar contextLifecycleScheduledTaskRegistrar : contextLifecycleScheduledTaskRegistrarArray) {
            //获取cron任务列表
            List<CronTask> cronTaskList = contextLifecycleScheduledTaskRegistrar.getCronTaskList();
            for (CronTask cronTask : cronTaskList) {
                ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) cronTask.getRunnable();
                //根据Task的ClassName和方法名判断
                String targetClassName = scheduledMethodRunnable.getTarget().getClass().getName();
                if (functionCode.length() <= targetClassName.length() && StringUtils.equals(functionCode, targetClassName.substring(0, functionCode.length())) && StringUtils.equals(functionMethod, scheduledMethodRunnable.getMethod().getName())) {
                    return cronTask;
                }
            }
        }
        return null;
    }

    /**
     * 获取当前任务下次执行时间
     *
     * @param functionCode   功能编码
     * @param functionMethod 功能方法
     * @return 当前任务下次执行时间
     */
    private Date getCurrentTaskNextExecuteDate(String functionCode, String functionMethod) {
        //获取当前执行任务
        CronTask cronTask = this.getCurrentCronTask(functionCode, functionMethod);
        if (cronTask == null) {
            return null;
        }
        //Cron表达式
        String cronExpressionStr = cronTask.getExpression();
        CronExpression cronExpression = null;
        try {
            cronExpression = new CronExpression(cronExpressionStr);
        } catch (ParseException e) {
            log.error("Cron表达式异常：", e);
            return null;
        }
        return cronExpression.getNextValidTimeAfter(DateUtils.getNowDate());
    }
}