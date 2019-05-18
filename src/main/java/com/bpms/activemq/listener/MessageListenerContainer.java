package com.bpms.activemq.listener;

import com.bpms.activemq.service.ActiveMQSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.JMSException;

public class MessageListenerContainer extends DefaultMessageListenerContainer {
    @Autowired
    private ActiveMQSenderService senderService;

    /**
     * 启动ActiveMQ服务
     *
     * @throws JMSException
     */
    @Override
    protected void doStart() throws JMSException {
        //是否启用mq
        if (senderService.activeMQEnabled()) {
            super.doStart();
        }
    }
}