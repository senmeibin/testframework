package com.bpms.sys.service;

import com.bpms.activemq.consts.JMSConsts;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.sync.SyncDataService;
import com.bpms.sys.entity.DurableTopicSubscribers;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.SubscriptionViewMBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 同步项目管理服务类
 */
@Service
public class SyncAppService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ActiveMQ发送队列服务类
     */
    @Autowired
    ActiveMQSenderService activeMQSenderService;

    /**
     * 同步数据服务类
     */
    @Autowired
    SyncDataService syncDataService;

    /**
     * 获取持久化主题订阅者实体列表
     *
     * @return 持久化主题订阅者实体列表
     */
    public List<DurableTopicSubscribers> getDurableTopicSubscribersList() {
        List<DurableTopicSubscribers> list = new ArrayList<>();
        try {
            //获取代理BrokerViewMBean
            BrokerViewMBean brokerViewMBean = activeMQSenderService.getBrokerViewMBean();
            if (brokerViewMBean == null || !activeMQSenderService.activeMQEnabled()) {
                return Collections.EMPTY_LIST;
            }

            //获取MBean服务连接
            MBeanServerConnection mBeanServerConnection = activeMQSenderService.getMBeanServerConnection();

            //获取所有在线的持久订阅者
            ObjectName[] durableTopicSubscribers = brokerViewMBean.getDurableTopicSubscribers();

            list.addAll(this.createDurableTopicSubscribersList(durableTopicSubscribers, mBeanServerConnection, true));

            //获取非活动的持久订阅者
            ObjectName[] inactiveDurableTopicSubscribers = brokerViewMBean.getInactiveDurableTopicSubscribers();

            list.addAll(this.createDurableTopicSubscribersList(inactiveDurableTopicSubscribers, mBeanServerConnection, false));

        } catch (Exception e) {
            logger.error("getDurableTopicSubscribersList异常", e);
        }

        return list;
    }

    /**
     * 创建持久订阅者
     *
     * @param entity 持久化主题订阅者实体
     * @return 处理结果
     */
    public AjaxResult createDurableSubscriber(DurableTopicSubscribers entity) {
        //获取代理BrokerViewMBean
        BrokerViewMBean brokerViewMBean = activeMQSenderService.getBrokerViewMBean();
        if (brokerViewMBean == null || !activeMQSenderService.activeMQEnabled()) {
            return AjaxResult.createFailResult("创建失败，ActiveMQ服务器连接异常或没有启用ActiveMQ服务。");
        }

        try {
            brokerViewMBean.createDurableSubscriber(entity.getClientId(), entity.getSubscriberName(), JMSConsts.JMS_TOPIC_NAME_SYNC, null);
        } catch (Exception e) {
            logger.error("创建持久订阅者", e);
            return AjaxResult.createFailResult(String.format("创建失败，错误信息：%s", e.getMessage()));
        }
        return AjaxResult.createSuccessResult("数据保存成功。", entity);
    }

    /**
     * 销毁持久订阅者
     *
     * @param clientId       订阅者UID
     * @param subscriberName 订阅者名称
     * @return 处理结果
     */
    public AjaxResult destroyDurableSubscriber(String clientId, String subscriberName) {
        //获取代理BrokerViewMBean
        BrokerViewMBean brokerViewMBean = activeMQSenderService.getBrokerViewMBean();
        if (brokerViewMBean == null || !activeMQSenderService.activeMQEnabled()) {
            return AjaxResult.createFailResult("创建失败，ActiveMQ服务器连接异常或没有启用ActiveMQ服务。");
        }
        try {
            brokerViewMBean.destroyDurableSubscriber(clientId, subscriberName);
        } catch (Exception e) {
            logger.error("销毁持久订阅者异常", e);
            return AjaxResult.createFailResult(String.format("创建失败，错误信息：%s", e.getMessage()));
        }
        return AjaxResult.createSuccessResult("删除成功。");
    }

    /**
     * 创建持久化主题订阅者实体列表
     *
     * @param objectNames           持久订阅者KEYs
     * @param mBeanServerConnection MBean服务连接
     * @param isOnline              是否在线
     * @return 持久化主题订阅者实体列表
     */
    private List<DurableTopicSubscribers> createDurableTopicSubscribersList(ObjectName[] objectNames, MBeanServerConnection mBeanServerConnection, Boolean isOnline) {
        List<DurableTopicSubscribers> list = new ArrayList<>();
        for (ObjectName objectName : objectNames) {
            //获取持久订阅者Mbean
            SubscriptionViewMBean subscriptionViewMBean = MBeanServerInvocationHandler.newProxyInstance(mBeanServerConnection, objectName, SubscriptionViewMBean.class, true);

            //忽略不是同步队列的订阅者
            if (!StringUtils.equals(JMSConsts.JMS_TOPIC_NAME_SYNC, subscriptionViewMBean.getDestinationName())) {
                continue;
            }

            DurableTopicSubscribers entity = new DurableTopicSubscribers();
            //UID
            entity.setUid(subscriptionViewMBean.getClientId());
            //JMS客户端ID
            entity.setClientId(subscriptionViewMBean.getClientId());
            //持久订者名称
            entity.setSubscriberName(subscriptionViewMBean.getSubscriptionName());
            //订阅主题名称
            entity.setTopicName(subscriptionViewMBean.getDestinationName());
            //是否在线
            entity.setOnLine(isOnline);
            //待消费队列大小
            entity.setPendingQueueSize(subscriptionViewMBean.getPendingQueueSize());
            //已入队数
            entity.setEnqueueSize(subscriptionViewMBean.getEnqueueCounter());
            //已消费的数量
            entity.setDequeueSize(subscriptionViewMBean.getDequeueCounter());
            //连接订阅的ID
            entity.setConnectionId(subscriptionViewMBean.getConnectionId());
            //是否UDC服务器
            entity.setUdcServer(StringUtils.equals(entity.getClientId(), syncDataService.getUdcServerClientId()));
            //entity.setUdcServer(StringUtils.equals(entity.getClientId(), parameterService.getValue(AppCodeConsts.APP_SYS,UDC_SERVER_CLIENT_ID,"TOPIC_SERVER_UDC")));

            list.add(entity);

        }
        return list;
    }
}