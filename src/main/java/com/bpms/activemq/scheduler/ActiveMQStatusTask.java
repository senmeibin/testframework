package com.bpms.activemq.scheduler;

import com.bpms.activemq.consts.JMSConsts;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.mail.SendMailService;
import com.bpms.core.scheduler.BaseTask;
import com.bpms.core.scheduler.IBaseTask;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.SchedulerUtils;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActiveMQStatusTask extends BaseTask implements IBaseTask {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 发生邮件service
     */
    @Autowired
    private SendMailService sendMailService;

    /**
     * ActiveMQ发送队列Service
     */
    @Autowired
    private ActiveMQSenderService activeMQSenderService;

    /**
     * 每10分钟执行一次（cron format说明 : 秒 分钟 小时 日 月(*) 星期（不指定 ?) 年（空）)
     */
    @Override
    public void execute() {
        //非任务处理服务器的场合、处理中止
        if (!this.isTaskServer) {
            return;
        }

        //没有启用mq服务直接退出
        if (!this.activeMQSenderService.activeMQEnabled()) {
            return;
        }

        long start = System.currentTimeMillis();

        //锁定执行记录
        ExecuteRecordExt executeRecord = this.lockExecute(this.getClass().getName(), "execute", "监听消息队列定时任务", "系统管理", "系统管理", SchedulerUtils.FUNCTION_TYPE_TASK, "每10-30分钟定时任务监听消息队列处理是否正常");
        //锁定失败的场合、处理中止
        if (executeRecord == null || !executeRecord.isLockSuccess()) {
            return;
        }
        boolean isException = false;

        try {
            //查询MQ状态任务
            this.queryActiveMQStatusTask(executeRecord);

            if (logger.isDebugEnabled()) {
                logger.debug("获取ActiveMQ状态信息耗时{}ms", System.currentTimeMillis() - start);
            }
        } catch (Exception e) {
            isException = true;
            throw e;
        } finally {
            //释放执行记录上的锁
            this.unlockExecute(executeRecord, isException);
        }
    }

    /**
     * 执行查询MQ状态任务
     *
     * @param executeRecord 执行记录
     */
    private void queryActiveMQStatusTask(ExecuteRecordExt executeRecord) {
        Map<String, DestinationViewMBean> allViewBeanMap = new HashMap<>();
        //加入所有Queue队列数据
        allViewBeanMap.putAll(activeMQSenderService.getQueueViewMBeanMap());
        //加入所有Topic队列数据
        allViewBeanMap.putAll(activeMQSenderService.getTopicViewMBeanMap());
        //获取待处理队列消息预警数值
        int queueWarningSize = Integer.parseInt(this.parameterService.getValue(AppCodeConsts.APP_SYS, JMSConsts.JMX_QUEUE_WARNING_SIZE, "0"));
        //处理队列数据
        for (Map.Entry<String, DestinationViewMBean> entry : allViewBeanMap.entrySet()) {
            DestinationViewMBean destinationMBean = entry.getValue();
            //超过预警值发送邮件提醒
            if (destinationMBean.getQueueSize() - queueWarningSize > 0) {
                this.sendMail(destinationMBean.getName(), destinationMBean.getQueueSize(), destinationMBean.getConsumerCount(), destinationMBean.getDequeueCount());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("消息队列{}中待处理的消息{}条，消费者{}个，已出队{}条。", destinationMBean.getName(), destinationMBean.getQueueSize(), destinationMBean.getConsumerCount(), destinationMBean.getDequeueCount());
            }
        }

        //新增执行日志
        this.insertExecuteLog(executeRecord, this.getExecuteJson(allViewBeanMap), 1, null, null, allViewBeanMap.size());
    }

    /**
     * 发送邮件通知
     *
     * @param queueSize     队列中数
     * @param queueName     队列名称
     * @param consumerCount 消费者数
     * @param dequeueCount  已出队数
     */
    private void sendMail(String queueName, Long queueSize, Long consumerCount, Long dequeueCount) {
        try {
            sendMailService.syncSend(AppCodeConsts.APP_SYS, String.format("队列%s待消费数超过预警值", queueName),
                    String.format("队列%s待消费数已超过预警值%s，目前有%s条待处理数据，有%s个消费者，已处理%s条数据。", queueName, this.parameterService.getValue(AppCodeConsts.APP_SYS, JMSConsts.JMX_QUEUE_WARNING_SIZE), queueSize, consumerCount, dequeueCount),
                    this.parameterService.getValue(AppCodeConsts.APP_SYS, JMSConsts.JMX_ADMIN_EMAIL), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}