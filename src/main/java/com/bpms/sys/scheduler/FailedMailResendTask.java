package com.bpms.sys.scheduler;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.mail.SendMailService;
import com.bpms.core.scheduler.BaseTask;
import com.bpms.core.scheduler.IBaseTask;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.SchedulerUtils;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import com.bpms.sys.entity.ext.MailLogExt;
import com.bpms.sys.service.MailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时重发失败的邮件
 */
@Component
public class FailedMailResendTask extends BaseTask implements IBaseTask {
    /**
     * 发送失败的邮件最大天数限制
     */
    private static final String SEND_FAILED_MAIL_MAX_DAYS_LIMIT = "SEND_FAILED_MAIL_MAX_DAYS_LIMIT";
    /**
     * 邮件发送最大重试次数限制
     */
    private static final String SEND_MAIL_MAX_RETRY_COUNT_LIMIT = "SEND_MAIL_MAX_RETRY_COUNT_LIMIT";
    /**
     * 邮件日志服务类
     */
    @Autowired
    private MailLogService mailLogService;
    /**
     * 发送邮件服务类
     */
    @Autowired
    private SendMailService sendMailService;

    @Transactional
    @Override
    public void execute() {
        //非任务处理服务器的场合、处理中止
        if (!this.isTaskServer) {
            return;
        }

        //锁定执行记录
        ExecuteRecordExt executeRecord = this.lockExecute(this.getClass().getName(), "execute", "重发失败的邮件", "系统管理", "系统管理", SchedulerUtils.FUNCTION_TYPE_TASK, "每小时定时重发失败的邮件");

        //锁定失败的场合、处理中止
        if (executeRecord == null || !executeRecord.isLockSuccess()) {
            return;
        }

        long startTime = System.currentTimeMillis();
        log.info("定时重发失败的邮件[开始]");

        boolean isException = false;
        try {
            //获取查询天数
            int days = parameterService.getIntValue(AppCodeConsts.APP_SYS, SEND_FAILED_MAIL_MAX_DAYS_LIMIT, 3);

            //获取邮件发送最大重试次数
            int maxRetryCount = parameterService.getIntValue(AppCodeConsts.APP_SYS, SEND_MAIL_MAX_RETRY_COUNT_LIMIT, 3);

            //获取指定日期的发送失败的邮件
            Map<String, Object> condition = new HashMap<>();
            condition.put("main.insert_date$>=", DateUtils.format(DateUtils.addDate(DateUtils.getNowDate(), -days)));
            condition.put("main.send_result", "-1");
            condition.put("main.retry_count$<", maxRetryCount);
            List<MailLogExt> list = mailLogService.search(MailLogExt.class, mailLogService.getSearchSQL(condition), condition);

            int success = 0;
            //重新发送邮件
            for (MailLogExt mailLog : list) {
                int retryCount = mailLog.getRetryCount();
                boolean isSuccess = sendMailService.send(mailLog.getAppCode(), mailLog.getSubject(), mailLog.getMessage(), mailLog.getMailTo(), mailLog.getMailCc(), mailLog.getMailBcc(), mailLog.getUid(), ++retryCount, mailLog.getAttachmentNameList());
                if (isSuccess) {
                    success++;
                }
                //写保存执行日志时，去掉邮件本体内容
                mailLog.setMessage("");
            }

            //新增执行日志
            this.insertExecuteLog(executeRecord, this.getExecuteJson(list), 1, null, String.format("成功发送%s条", success), list.size());
        } catch (Exception e) {
            isException = true;
            //新增执行异常日志
            this.insertExecuteLog(executeRecord, null, -1, "EXCEPTION", e.getMessage(), 0);
        } finally {
            //释放执行记录上的锁
            this.unlockExecute(executeRecord, isException);
            log.info("定时重发失败的邮件[结束]-耗时：{}毫秒", System.currentTimeMillis() - startTime);
        }
    }
}