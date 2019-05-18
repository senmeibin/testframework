package com.bpms.core.api;

import com.bpms.core.sql.SqlReader;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import com.bpms.sys.service.ExecuteLogService;
import com.bpms.sys.service.ExecuteRecordService;
import com.bpms.sys.service.ParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 外部API基础类
 */
public class BaseApi {
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
    public ExecuteRecordExt getExecute(String functionCode, String functionMethod, String functionName, String sourceSystem, String destSystem, String functionType, String remark) {
        return this.executeRecordService.getExecute(functionCode, functionMethod, functionName, sourceSystem, destSystem, functionType, remark);
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
}