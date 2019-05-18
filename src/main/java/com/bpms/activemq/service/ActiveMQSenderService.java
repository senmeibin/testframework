package com.bpms.activemq.service;

import com.google.common.collect.Maps;
import com.bpms.activemq.producer.QueueSender;
import com.bpms.activemq.producer.TopicSender;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.DateUtils;
import com.bpms.sys.service.ParameterService;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.broker.jmx.TopicViewMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ActiveMQ发送队列Service
 */
@Service
public class ActiveMQSenderService implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 参数配置服务service
     */
    @Autowired
    private ParameterService parameterService;

    @Resource
    private QueueSender queueSender;

    @Resource
    private TopicSender topicSender;

    /**
     * JMX服务开关
     */
    @Value("${activemq.enable:false}")
    private boolean enabled;

    /**
     * JMX服务地址（mq地址）
     */
    @Value("${activemq.jmx.url:'service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi'}")
    private String jmxUrl;

    @Value("${activemq.jmx.userName:admin}")
    private String jmxUserName;

    @Value("${activemq.jmx.password:admin}")
    private String jmxPassword;

    /**
     * JMX broker名称
     */
    @Value("${activemq.jmx.brokerName:localhost}")
    private String brokerName;

    /**
     * JMX 域名
     */
    @Value("${activemq.jmx.jmxDomainName:org.apache.activemq}")
    private String jmxDomainName;

    @Value("${activemq.systemCode:RR}")
    private String systemCode;

    /**
     * JMX连接器
     */
    private JMXConnector connector;

    public boolean activeMQEnabled() {
        return enabled;
    }

    /**
     * 相关参数初期化
     */
    @Override
    public void afterPropertiesSet() {
        //没有启用mq服务直接退出
        if (!enabled) {
            return;
        }
        //连接JMX服务器
        try {
            this.connectJMXServer();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 连接JMX服务器
     *
     * @throws Exception
     */
    public void connectJMXServer() throws Exception {
        long startTime = System.currentTimeMillis();
        //权限验证
        Map<String, Object> env = Maps.newHashMap();
        env.put(JMXConnector.CREDENTIALS, new String[]{jmxUserName, jmxPassword});
        //连接JMX服务器
        this.connector = JMXConnectorFactory.connect(new JMXServiceURL(jmxUrl), env);
        if (logger.isDebugEnabled()) {
            logger.debug("连接JMX服务器({})成功，耗时{}ms", jmxUrl, System.currentTimeMillis() - startTime);
        }
    }

    /**
     * 发送指定队列数据
     *
     * @param queueName  消息队列名
     * @param jsonObject json字符串
     */
    public void pushQueue(String queueName, String jsonObject) {
        long startTime = System.currentTimeMillis();
        queueSender.send(systemCode + "." + queueName, jsonObject);
        if (logger.isDebugEnabled()) {
            logger.debug("MQ消息推送处理时间：{}ms", (System.currentTimeMillis() - startTime));
        }
    }

    /**
     * 发送指定队列数据
     *
     * @param queueName  消息队列名
     * @param jsonObject json字符串
     */
    public void pushTopic(String queueName, String jsonObject) {
        long startTime = System.currentTimeMillis();
        topicSender.send(queueName, jsonObject);
        if (logger.isDebugEnabled()) {
            logger.debug("MQ消息推送处理时间：{}ms", (System.currentTimeMillis() - startTime));
        }
    }

    /**
     * 获取所有的Queue消息队列Bean
     *
     * @return 消息队列Map
     */
    public Map<String, QueueViewMBean> getQueueViewMBeanMap() {
        Map<String, QueueViewMBean> map = new HashMap<>();
        try {
            //远程的MBean服务器
            MBeanServerConnection connection = this.connector.getMBeanServerConnection();
            BrokerViewMBean mBean = this.getBrokerViewMBean(connection);

            //处理所有的队列数据
            for (ObjectName queueName : mBean.getQueues()) {
                QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);
                if (queueMBean.getName().indexOf(systemCode) > -1) {
                    map.put(queueMBean.getName(), queueMBean);
                }
            }
        } catch (IOException e) {
            logger.error("JMX服务器连接失败，重新连接...");
            try {
                this.connectJMXServer();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("获取ActiveMQ状态信息失败", e);
        }
        return map;
    }

    /**
     * 获取所有Topic的消息队列Bean
     *
     * @return 消息队列Map
     */
    public Map<String, TopicViewMBean> getTopicViewMBeanMap() {
        Map<String, TopicViewMBean> map = new HashMap<>();
        try {
            //远程的MBean服务器
            MBeanServerConnection connection = this.connector.getMBeanServerConnection();
            BrokerViewMBean mBean = this.getBrokerViewMBean(connection);

            //处理所有的队列数据[Topic]
            for (ObjectName topicName : mBean.getTopics()) {
                TopicViewMBean topicViewMBean = MBeanServerInvocationHandler.newProxyInstance(connection, topicName, TopicViewMBean.class, true);
                if (topicViewMBean.getName().indexOf(systemCode) > -1) {
                    map.put(topicViewMBean.getName(), topicViewMBean);
                }
            }
        } catch (IOException e) {
            logger.error("JMX服务器连接失败，重新连接...");
            try {
                this.connectJMXServer();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("获取ActiveMQ状态信息失败", e);
        }
        return map;
    }

    /**
     * 获取代理BrokerViewMBean
     *
     * @param connection 远程服务器链接
     * @return
     */
    public BrokerViewMBean getBrokerViewMBean(MBeanServerConnection connection) throws MalformedObjectNameException {
        ObjectName name = new ObjectName(this.jmxDomainName + ":type=Broker,brokerName=" + this.brokerName);
        return MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
    }

    /**
     * 获取代理BrokerViewMBean
     *
     * @return
     */
    public BrokerViewMBean getBrokerViewMBean() {
        try {
            MBeanServerConnection connection = this.getMBeanServerConnection();

            return this.getBrokerViewMBean(connection);
        } catch (IOException e) {
            throw new ServiceException("JMX服务器连接失败", e);
        } catch (Exception e) {
            throw new ServiceException("获取ActiveMQ状态信息失败", e);
        }
    }

    /**
     * 获取MBean服务连接
     *
     * @return
     * @throws IOException
     */
    public MBeanServerConnection getMBeanServerConnection() throws IOException {
        try {
            if (this.connector == null) {
                this.connectJMXServer();
            }
        } catch (Exception e) {
            throw new ServiceException("JMX服务器连接失败", e);
        }
        return this.connector.getMBeanServerConnection();
    }

    /**
     * 获取系统编号
     *
     * @return
     */
    public String getActiveMQSystemCode() {
        return systemCode;
    }
}