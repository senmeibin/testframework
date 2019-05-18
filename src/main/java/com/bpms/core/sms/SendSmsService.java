package com.bpms.core.sms;

import com.bpms.activemq.consts.JMSConsts;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.SmsLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendSmsService {
    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 短信发送Service
     */
    @Autowired
    private SendSmsCoreService sendSmsCoreService;

    /**
     * ActiveMQ发送队列Service
     */
    @Autowired
    private ActiveMQSenderService activeMQSenderService;

    /**
     * 发送SMS短信【国都短信接口】
     *
     * @param appCode 应用编号
     * @param mobile  手机号码
     * @param message 短信内容
     * @param remark  备注
     * @return 发送结果
     */
    public boolean send(String appCode, String mobile, String message, String remark) throws Exception {
        //启用ActiveMQ
        if (activeMQSenderService.activeMQEnabled()) {
            if (logger.isDebugEnabled()) {
                logger.debug("已启用ActiveMQ服务，向短信队列推送短信消息。");
            }
            SmsLog smsLog = new SmsLog();
            smsLog.setAppCode(appCode);
            smsLog.setMobile(mobile);
            smsLog.setMessage(message);
            smsLog.setRemark(remark);

            this.activeMQSenderService.pushQueue(JMSConsts.JMS_QUEUE_NAME_SMS, JsonUtils.toJSON(smsLog));
            return true;
        }
        else {
            if (logger.isDebugEnabled()) {
                logger.debug("没有启用ActiveMQ服务，直接使用短信接口发送短信。");
            }
            return this.syncSend(appCode, mobile, message, remark);
        }
    }

    /**
     * 同步发送SMS短信【系统平台】
     *
     * @param appCode 应用编号
     * @param mobile  手机号码
     * @param message 短信内容
     * @param extCode 扩展码
     * @return 发送结果
     */
    public boolean syncSend(String appCode, String mobile, String message, String extCode) throws Exception {
        return this.sendSmsCoreService.smsSend(appCode, mobile, message, extCode);
    }

    /**
     * 同步发送SMS短信【指定账号】
     *
     * @param appCode   应用编号
     * @param smsVendor 供应商类型
     * @param url       短信接口URL
     * @param epid      账号唯一对应【鸿联专用】
     * @param account   短信账号
     * @param password  密码
     * @param mobile    手机号码
     * @param message   短信内容
     * @param extCode   扩展码
     * @return 发送结果
     */
    public boolean syncSend(String appCode, String smsVendor, String url, String epid, String account, String password, String mobile, String message, String extCode) throws Exception {
        return this.sendSmsCoreService.smsSend(appCode, smsVendor, url, epid, account, password, mobile, message, extCode);
    }
}
