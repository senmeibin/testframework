package com.bpms.activemq.consumer;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.sms.SendSmsService;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.SmsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSmsListener extends BaseMessageListener {
    /**
     * 发送短信service
     */
    @Autowired
    private SendSmsService sendSmsService;

    @Override
    public void doMessageListener(String jsonString) {
        SmsLog sms = JsonUtils.parseJSON(jsonString, SmsLog.class);
        boolean success = false;
        try {
            success = sendSmsService.syncSend(AppCodeConsts.APP_SYS, sms.getMobile(), sms.getMessage(), sms.getRemark());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!success) {
            //TODO:重发尝试
        }
    }
}