package com.bpms.sys.service;

import com.bpms.core.utils.HostUtils;
import com.bpms.sys.dao.ExecuteLogDao;
import com.bpms.sys.entity.bean.ExecuteResultDetail;
import com.bpms.sys.entity.ext.ExecuteLogExt;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 执行日志表服务类
 */
@Service
public class ExecuteLogService extends SysBaseService<ExecuteLogExt> {
    @Autowired
    private ExecuteLogDao executeLogDao;

    @Override
    public ExecuteLogDao getDao() {
        return executeLogDao;
    }

    @Override
    public String getSearchSQLPath(Map<String, Object> condition) {
        return "sys/ExecuteLog/search";
    }

    /**
     * 新增执行日志
     *
     * @param executeRecord       执行记录实体类
     * @param executeJson         要同步的内容
     * @param count               同步的结果条数
     * @param executeResultDetail 返回的json映射实体对象
     */
    public void insertExecuteLog(ExecuteRecordExt executeRecord, String executeJson, int count, ExecuteResultDetail executeResultDetail) {
        //服务请求失败的情况处理 保存同步日志记录 同步记录表中数据不变
        //返回的结果code  0:秘钥验证未通过 ;1:同步成功 ;2:参数为空或者json数据格式错误 ;3:部分成功,部分失败
        String code = executeResultDetail.getResult();
        //返回的结果message
        String message = executeResultDetail.getMsg();
        //成功失败标志  成功/失败标志(-1:失败、1：成功)
        int result = -1;
        if (StringUtils.equals(code, "1")) {
            // 如果返回code为1 则表示同步成功 保存同步日志记录 同步记录表中更新最后同步时间 处理成功
            result = 1;
        }
        else if (StringUtils.equals(code, "0")) {
            //如果返回code为0 则表示验证未通过 保存同步日志记录
            count = 0;
            result = -1;
            message = "验证未通过";
        }
        else if (StringUtils.equals(code, "2")) {
            //参数为空或者json数据格式错误
            count = 0;
            result = -1;
            message = "参数为空或者json数据格式错误";
        }
        else if (StringUtils.equals(code, "3")) {
            //部分成功/部分失败
            count = 0;
            result = -1;
            message = "部分成功/部分失败:" + message;
        }
        //如果返回code其他 则表示同步失败 保存同步日志记录
        else {
            count = 0;
        }
        this.insertExecuteLog(executeRecord, executeJson, result, code, message, count);
    }

    /**
     * 新增执行日志
     *
     * @param executeRecord 执行记录信息
     * @param jsonList      执行内容
     * @param result        返回结果【成功/失败标志(-1:失败、1：成功)】
     * @param resultCode    结果编码
     * @param message       结果消息
     * @param executeCount  执行成功记录数
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertExecuteLog(ExecuteRecordExt executeRecord, String jsonList, Integer result, String resultCode, String message, int executeCount) {
        //创建执行日志对象
        ExecuteLogExt executeLog = new ExecuteLogExt();
        //功能编码
        executeLog.setFunctionCode(executeRecord.getFunctionCode());
        //功能名称
        executeLog.setFunctionName(executeRecord.getFunctionName());
        //功能类型
        executeLog.setFunctionTypeCd(executeRecord.getFunctionTypeCd());
        //功能方法
        executeLog.setFunctionMethod(executeRecord.getFunctionMethod());
        //目标系统
        executeLog.setDestSystemName(executeRecord.getDestSystemName());
        //来源系统
        executeLog.setSourceSystemName(executeRecord.getSourceSystemName());
        //执行数据内容
        executeLog.setExecuteContent(jsonList);
        //执行记录UID
        executeLog.setExecuteRecordUid(executeRecord.getUid());

        //返回结果
        executeLog.setResult(result);
        //执行成功数
        executeLog.setExecuteRecordCount(executeCount);
        //结果编码
        executeLog.setResultCode(resultCode);
        //结果消息
        if (StringUtils.isNotEmpty(message) && message.length() > 256) {
            message = message.substring(0, 256);
        }
        executeLog.setResultMessage(message);

        //执行开始时间
        executeLog.setExecuteStartDate(executeRecord.getLastExecuteStartDate());

        //执行结束时间
        executeLog.setExecuteEndDate(new Date());

        //执行机器
        executeLog.setExecuteMachine(HostUtils.getHostAddress());

        save(executeLog);
    }
}