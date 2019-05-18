package com.bpms.core.session;

import com.bpms.activemq.consts.JMSConsts;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.sys.service.OnlineUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * session服务类
 */
@Service
public class SessionService {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 在线用户service
     */
    @Autowired
    private OnlineUserService onlineUserService;

    /**
     * ActiveMQ发送队列Service
     */
    @Autowired
    private ActiveMQSenderService activeMQSenderService;

    /**
     * 在线用户状态更新
     *
     * @param userUid 用户UID
     */
    public void updateSession(String userUid) {
        //启用ActiveMQ
        if (activeMQSenderService.activeMQEnabled()) {
            if (logger.isDebugEnabled()) {
                logger.debug("已启用ActiveMQ服务，向Session队列推送消息。");
            }
            activeMQSenderService.pushQueue(JMSConsts.JMS_QUEUE_NAME_SESSION, userUid);
        }
        else {
            if (logger.isDebugEnabled()) {
                logger.debug("没有启用ActiveMQ服务，直接更新用户在线状态。");
            }
            onlineUserService.updateSession(userUid);
        }
    }

    /**
     * 在线用户状态更新
     *
     * @param userUid 用户UID
     */
    public void syncUpdateSession(String userUid) {
        onlineUserService.updateSession(userUid);
    }
}