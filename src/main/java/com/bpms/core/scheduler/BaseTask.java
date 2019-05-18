package com.bpms.core.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.core.sql.SqlReader;
import com.bpms.core.utils.JsonUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import com.bpms.sys.service.ExecuteLogService;
import com.bpms.sys.service.ExecuteRecordService;
import com.bpms.sys.service.ParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 定时任务基础类
 */
public class BaseTask {
    /**
     * 日志处理实例
     */
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * SqlReader对象实例
     */
    @Autowired
    protected SqlReader sqlReader;

    /**
     * 参数配置服务实例
     */
    @Autowired
    protected ParameterService parameterService;

    /**
     * 执行记录service 实例
     */
    @Autowired
    protected ExecuteRecordService executeRecordService;

    /**
     * 执行日志service 实例
     */
    @Autowired
    protected ExecuteLogService executeLogService;

    /**
     * 是否是任务处理服务器
     */
    @Value("${task.server.enable:false}")
    protected boolean isTaskServer;

    /**
     * 释放执行记录上的锁
     *
     * @param executeRecord 执行记录实体对象
     * @param isException   异常信息发生  true:发生异常情况的解锁；false:正常情况下的解锁
     * @return true:释放成功 false;释放失败
     */
    public boolean unlockExecute(ExecuteRecordExt executeRecord, boolean isException) {
        return this.executeRecordService.unlockExecute(executeRecord, isException);
    }

    /**
     * 任务状态变更
     *
     * @param uid              任务uid
     * @param functionStatusCd 任务状态(01：待执行、02：执行中、09：停止)
     */
    public void updateTaskStatus(String uid, String functionStatusCd) {
        this.executeRecordService.updateTaskStatus(uid, functionStatusCd);
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
    public ExecuteRecordExt lockExecute(String functionCode, String functionMethod, String functionName, String sourceSystem, String destSystem, String functionType, String remark) {
        return this.executeRecordService.lockExecute(functionCode, functionMethod, functionName, sourceSystem, destSystem, functionType, remark);
    }

    /**
     * 新增执行日志
     *
     * @param executeRecord 执行记录信息
     * @param jsonList      执行内容
     * @param result        返回结果【成功/失败标志(-1:失败、1：成功)】
     * @param resultCode    接口返回的结果code
     * @param message       接口返回的结果message
     * @param executeCount  执行成功记录数
     */
    public void insertExecuteLog(ExecuteRecordExt executeRecord, String jsonList, Integer result, String resultCode, String message, int executeCount) {
        //功能编码赋值
        executeLogService.insertExecuteLog(executeRecord, jsonList, result, resultCode, message, executeCount);
    }

    /**
     * 取得执行内容Json字符串
     *
     * @param object 需要转换的数据
     * @return Json字符串
     */
    protected String getExecuteJson(Object object) {
        try {
            String executeJson = JsonUtils.toJSON(object);

            //Mysql MEDIUMTEXT最大长度截取(限定为5M）
            if (StringUtils.isNotEmpty(executeJson) && executeJson.length() > 1024 * 1024 * 5) {
                executeJson = executeJson.substring(0, 1024 * 1024 * 5);
            }
            return executeJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("转换json报错" + e);
        }
        return StringUtils.EMPTY;
    }
}