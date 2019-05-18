package com.bpms.activemq.consumer;


import com.bpms.activemq.producer.QueueSender;
import com.bpms.activemq.producer.TopicSender;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.sys.service.ParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 消息监听
 */
public abstract class BaseMessageListener implements SessionAwareMessageListener<Message> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 消息发送器(P2P)
     */
    @Resource
    protected QueueSender queueSender;

    /**
     * 消息发送器(pub-sub)
     */
    @Resource
    protected TopicSender topicSender;

    /**
     * 参数配置服务service
     */
    @Autowired
    protected ParameterService parameterService;

    /**
     * ActiveMQ发送队列Service
     */
    @Autowired
    protected ActiveMQSenderService activeMQSenderService;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        String jsonString = null;

        //string对象
        if (message instanceof TextMessage) {
            jsonString = ((TextMessage) message).getText();
        }
        else {
            throw new JMSException("非法消息队列（系统只支持String消息队列）。");
        }

        //消息执行
        this.doMessageListener(jsonString);
    }

    /**
     * 执行监听队列
     *
     * @param jsonString Json字符串
     */
    public abstract void doMessageListener(String jsonString);
}