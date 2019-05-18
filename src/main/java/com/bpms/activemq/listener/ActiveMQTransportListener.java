package com.bpms.activemq.listener;

import com.bpms.activemq.consts.JMSConsts;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.mail.SendMailService;
import com.bpms.sys.service.ParameterService;
import org.apache.activemq.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ActiveMQ服务器的连接状态监听
 */
@Component
public class ActiveMQTransportListener implements TransportListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 发送邮件服务service
     */
    @Autowired
    private SendMailService sendMailService;

    /**
     * 参数配置服务service
     */
    @Autowired
    private ParameterService parameterService;

    /**
     * JMX broker名称
     */
    @Value("${activemq.brokerURL}")
    private String brokerURL;

    /**
     * 系统编号[作为activemq前缀，保证不同应用唯一读取]
     */
    @Value("${activemq.systemCode}")
    private String systemCode;


    /**
     * 对消息传输命令进行监控
     *
     * @param command
     */
    @Override
    public void onCommand(Object command) {

    }

    /**
     * 对监控到的异常进行触发
     *
     * @param error
     */
    @Override
    public void onException(IOException error) {
        logger.error("onException -> 消息服务器连接错误......");
        this.sendMail("【系统通知】ActiveMQ消息服务器连接错误", "错误消息：" + error.getMessage() +  " [系统编号]：" + systemCode + " || brokerURL：" + brokerURL);
    }

    /**
     * 当故障时触发
     */
    @Override
    public void transportInterupted() {
        logger.error("transportInterrupted -> 消息服务器连接发生中断......");
        this.sendMail("【系统通知】ActiveMQ消息服务器连接发生中断", "消息服务器连接发生中断" +  " [系统编号]：" + systemCode + " || brokerURL：" + brokerURL);
    }

    /**
     * 监控到故障恢复后进行触发
     */
    @Override
    public void transportResumed() {
        logger.info("transportResumed -> 消息服务器连接已恢复......");
        this.sendMail("【系统通知】ActiveMQ消息服务器连接已恢复", "消息服务器连接已恢复" + " [系统编号]：" + systemCode + " || brokerURL：" + brokerURL);
    }

    private void sendMail(String subject, String contents) {
        try {
            sendMailService.syncSend(AppCodeConsts.APP_SYS, subject, contents, this.parameterService.getValue(AppCodeConsts.APP_SYS, JMSConsts.JMX_ADMIN_EMAIL), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}