package com.bpms.activemq.consumer;

import com.bpms.core.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSessionListener extends BaseMessageListener {
    /**
     * 在线用户会话状态Service
     */
    @Autowired
    private SessionService sessionService;

    @Override
    public void doMessageListener(String jsonString) {
        sessionService.syncUpdateSession(jsonString);
    }
}