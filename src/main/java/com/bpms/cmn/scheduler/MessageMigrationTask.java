package com.bpms.cmn.scheduler;

import com.bpms.cmn.service.migration.MessageMigrationService;
import com.bpms.core.scheduler.IBaseTask;
import com.bpms.core.utils.SchedulerUtils;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时迁移消息推送数据
 */
@Component
public class MessageMigrationTask extends CmnBaseTask implements IBaseTask {
    @Autowired
    private MessageMigrationService messageMigrationService;

    /**
     * 定时迁移消息
     */
    @Override
    public void execute() {
        //非任务处理服务器的场合、处理中止
        if (!this.isTaskServer) {
            return;
        }

        String functionName = "定时迁移消息推送记录";
        //锁定执行记录
        ExecuteRecordExt executeRecord = this.lockExecute(this.getClass().getName(), "execute", functionName, "系统共通", "系统共通", SchedulerUtils.FUNCTION_TYPE_MIGRATION, "定时迁移消息推送记录");

        //锁定失败的场合、处理中止
        if (executeRecord == null || !executeRecord.isLockSuccess()) {
            return;
        }

        long startTime = System.currentTimeMillis();
        log.info("{}[开始]", functionName);

        //返回结果
        int result = SchedulerUtils.RESULT_SUCCESS;

        //消息推送定时迁移条数
        int executeRecordCount = 0;

        //执行内容
        String executeContent = "消息推送记录定时迁移成功";
        boolean isException = false;
        try {
            //消息推送定时迁移
            executeRecordCount = messageMigrationService.migrationMessage();
        } catch (Exception ex) {
            log.error("{}[异常]：", functionName, ex);
            //执行内容设置
            executeContent = "消息推送记录定时迁移失败：" + ex.getMessage();
            //失败的情况
            result = SchedulerUtils.RESULT_FALSE;
            isException = true;
        } finally {
            //释放执行记录上的锁
            this.unlockExecute(executeRecord, isException);

            //新增执行日志
            this.insertExecuteLog(executeRecord, executeContent, result, null, null, executeRecordCount);

            log.info("{}[结束]-耗时：{}毫秒", functionName, System.currentTimeMillis() - startTime);
        }
    }
}