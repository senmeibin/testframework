package com.bpms.activemq.consumer;

import com.bpms.core.mail.SendMailService;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.ext.MailLogExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueMailListener extends BaseMessageListener {
    /**
     * 日志输出对象
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 发送邮件service
     */
    @Autowired
    private SendMailService sendMailService;


    @Override
    public void doMessageListener(String jsonString) {
        MailLogExt mail = JsonUtils.parseJSON(jsonString, MailLogExt.class);
        if (log.isDebugEnabled()) {
            log.debug("通过消息队列发送邮件开始。(系统编号：{}, 邮件标题：{}, 邮件内容：{}, 收件人：{}， 邮件日志Uid：{},重复回数：{}, 附件列表：{})", mail.getAppCode(), mail.getSubject(), mail.getMessage(), mail.getMailTo(), mail.getMailLogUid(), mail.getRetryCount(), mail.getAttachmentNameList());
        }
        sendMailService.syncSend(mail.getAppCode(), mail.getSubject(), mail.getMessage(), mail.getMailTo(), mail.getMailCc(), mail.getMailBcc(), mail.getMailLogUid(), mail.getRetryCount(), mail.getMailFrom(), mail.getMailFromPassword(), mail.getAttachmentNameList());
    }
}