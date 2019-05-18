package com.bpms.activemq.consumer;

import com.bpms.core.log.LogService;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueLogListener extends BaseMessageListener {
    /**
     * 发送日志service
     */
    @Autowired
    private LogService logService;

    @Override
    public void doMessageListener(String jsonString) {
        OperationLog log = JsonUtils.parseJSON(jsonString, OperationLog.class);
        logService.syncWriteOperationLog(log.getUserUid(), log.getUserCd(), log.getUserName(), log.getAppCode(), log.getModuleName(), log.getFunctionName(), log.getParameters(), log.getUrl(), log.getLogSource(), log.getLogType());
    }
}