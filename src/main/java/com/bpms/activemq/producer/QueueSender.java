package com.bpms.activemq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;
import java.util.UUID;

/**
 * @description 队列消息生产者，发送消息到队列
 */
@Component("queueSender")
public class QueueSender {

    /**
     * 通过@Qualifier修饰符来注入对应的bean
     */
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 发送一条消息到指定的队列（目标）
     *
     * @param queueName 队列名称
     * @param message   消息内容
     * @return
     */
    public void send(String queueName, final Serializable message) {
        //使用MessageConverter
        jmsTemplate.convertAndSend(queueName, message);
    }

    /**
     * 发送一条消息到指定的队列（目标）
     *
     * @param queueName 队列名称
     * @param message   消息内容
     * @return
     */
    public void send(String queueName, final String message) {
        //使用MessageConverter
        jmsTemplate.convertAndSend(queueName, message);
    }

    /**
     * 发送一条消息到指定的队列（目标）
     *
     * @param queueName
     * @param message
     * @return
     */
    public Message sendAndReceive(String queueName, final Serializable message) {
        Message replyMsg = jmsTemplate.sendAndReceive(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objMsg = session.createObjectMessage(message);
                objMsg.setJMSCorrelationID(UUID.randomUUID().toString());
                return objMsg;
            }
        });
        return replyMsg;
    }
}