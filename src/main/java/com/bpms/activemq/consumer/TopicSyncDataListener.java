package com.bpms.activemq.consumer;

import com.bpms.core.sync.SyncDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSyncDataListener extends BaseMessageListener {
    @Autowired
    private SyncDataService syncDataService;

    @Override
    public void doMessageListener(String jsonString) {
        if (logger.isDebugEnabled()) {
            logger.debug("同步数据开始(JSON数据：{})。", jsonString);
        }
        syncDataService.syncData(jsonString);
    }
}