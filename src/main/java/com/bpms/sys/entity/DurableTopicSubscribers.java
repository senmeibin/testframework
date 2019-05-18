package com.bpms.sys.entity;

import javax.persistence.Entity;

/**
 * 持久化主题订阅者实体
 */
@Entity
public class DurableTopicSubscribers extends SysBaseEntity {
    private static final long serialVersionUID = -20161223090457531L;

    /**
     * JMS客户端ID
     */
    private String clientId;

    /**
     * 持久订者名称
     */
    private String subscriberName;

    /**
     * 订阅主题名称
     */
    private String topicName;

    /**
     * 是否在线
     */
    private boolean onLine;

    /**
     * 待消费队列大小
     */
    private long pendingQueueSize = 0L;

    /**
     * 已入队数
     */
    private long enqueueSize = 0L;

    /**
     * 已出对数
     */
    private long dequeueSize = 0L;

    /**
     * 连接订阅的ID
     */
    private String connectionId;

    /**
     * 是否UDC服务器
     */
    private boolean udcServer;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }

    public long getPendingQueueSize() {
        return pendingQueueSize;
    }

    public void setPendingQueueSize(long pendingQueueSize) {
        this.pendingQueueSize = pendingQueueSize;
    }

    public long getEnqueueSize() {
        return enqueueSize;
    }

    public void setEnqueueSize(long enqueueSize) {
        this.enqueueSize = enqueueSize;
    }

    public long getDequeueSize() {
        return dequeueSize;
    }

    public void setDequeueSize(long dequeueSize) {
        this.dequeueSize = dequeueSize;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public boolean isUdcServer() {
        return udcServer;
    }

    public void setUdcServer(boolean udcServer) {
        this.udcServer = udcServer;
    }
}