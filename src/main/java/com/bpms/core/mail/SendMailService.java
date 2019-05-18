package com.bpms.core.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.activemq.consts.JMSConsts;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.JsonUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.entity.ext.MailLogExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMailService {
    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 发送邮件的服务类
     */
    @Autowired
    private SendMailCoreService sendMailCoreService;

    /**
     * ActiveMQ发送队列Service
     */
    @Autowired
    private ActiveMQSenderService activeMQSenderService;

    /**
     * 发送邮件
     *
     * @param appCode  应用编号
     * @param subject  邮件主题
     * @param contents 邮件内容
     * @param to       TO
     * @return 送信结果 true：成功
     */
    public boolean send(String appCode, String subject, String contents, String to) {
        return this.send(appCode, subject, contents, to, null, null, null, 0, null);
    }

    /**
     * 发送邮件
     *
     * @param appCode  应用编号
     * @param subject  邮件主题
     * @param contents 邮件内容
     * @param to       TO
     * @param cc       CC
     * @param bcc      BCC
     * @return 送信结果 true：成功
     */
    public boolean send(String appCode, String subject, String contents, String to, String cc, String bcc) {
        return this.send(appCode, subject, contents, to, cc, bcc, null, 0, null);
    }

    /**
     * 发送邮件
     *
     * @param appCode         应用编号
     * @param subject         邮件主题
     * @param contents        邮件内容
     * @param to              TO
     * @param cc              CC
     * @param bcc             BCC
     * @param mailLogUid      邮件日志UID
     * @param retryCount      发送重试次数
     * @param attachmentNames 附件名称列表（文件名绝对路径+ 文件名）
     * @return 送信结果 true：成功
     */
    public boolean send(String appCode, String subject, String contents, String to, String cc, String bcc, String mailLogUid, int retryCount, List<String> attachmentNames) {
        return this.send(appCode, subject, contents, to, cc, bcc, mailLogUid, retryCount, null, null, attachmentNames);
    }


    /**
     * 发送邮件
     *
     * @param appCode         应用编号
     * @param subject         邮件主题
     * @param contents        邮件内容
     * @param to              TO
     * @param cc              CC
     * @param bcc             BCC
     * @param mailLogUid      邮件日志UID
     * @param retryCount      发送重试次数
     * @param from            邮件发送人
     * @param fromPassword    邮件发送密码（DES加密后的密码）
     * @param attachmentNames 附件名称列表（文件名绝对路径+ 文件名）
     * @return 送信结果 true：成功
     */
    public boolean send(String appCode, String subject, String contents, String to, String cc, String bcc, String mailLogUid, int retryCount, String from, String fromPassword, List<String> attachmentNames) {
        //启用ActiveMQ
        if (activeMQSenderService.activeMQEnabled()) {
            if (logger.isDebugEnabled()) {
                logger.debug("已启用ActiveMQ服务，向邮件队列推送邮件消息。");
            }
            MailLogExt mailLog = new MailLogExt();
            mailLog.setAppCode(appCode);
            mailLog.setSubject(subject);
            mailLog.setMessage(contents);
            mailLog.setMailTo(to);
            mailLog.setMailCc(cc);
            mailLog.setMailBcc(bcc);
            mailLog.setMailLogUid(mailLogUid);
            mailLog.setRetryCount(retryCount);

            //邮件from （特定情况需要指定发送用户时使用）
            mailLog.setMailFrom(from);
            mailLog.setMailFromPassword(fromPassword);
            //附件追加
            if (CollectionUtils.isNotEmpty(attachmentNames)) {
                //以半角冒号（:）作为分隔符， 因为文件名和路径都不能包含半角冒号
                mailLog.setAttachmentName(StringUtils.join(attachmentNames, "$#"));
            }
            String mailLogJSON;
            try {
                mailLogJSON = JsonUtils.toJSON(mailLog);
            } catch (JsonProcessingException e) {
                logger.error("邮件JSON格式转换失败，请检查数据!appCode:{},subject:{},contents:{},to:{},cc:{},bcc:{}", appCode, subject, contents, to, cc, bcc, attachmentNames);
                return false;
            }
            this.activeMQSenderService.pushQueue(JMSConsts.JMS_QUEUE_NAME_EMAIL, mailLogJSON);

            return true;
        }
        //不启用ActiveMQ
        else {
            if (logger.isDebugEnabled()) {
                logger.debug("没有启用ActiveMQ服务，直接使用邮件接口发送邮件。");
            }
            return this.syncSend(appCode, subject, contents, to, cc, bcc, mailLogUid, retryCount, from, fromPassword, attachmentNames);
        }
    }

    /**
     * 同步发送邮件
     *
     * @param appCode  应用编号
     * @param subject  邮件主题
     * @param contents 邮件内容
     * @param to       TO
     * @return 送信结果 true：成功
     */
    public boolean syncSend(String appCode, String subject, String contents, String to) {
        return this.sendMailCoreService.send(appCode, subject, contents, to, null, null, null, 0, null, null, null);
    }

    /**
     * 同步发送邮件
     *
     * @param appCode  应用编号
     * @param subject  邮件主题
     * @param contents 邮件内容
     * @param to       TO
     * @param cc       CC
     * @param bcc      BCC
     * @return 送信结果 true：成功
     */
    public boolean syncSend(String appCode, String subject, String contents, String to, String cc, String bcc) {
        return this.sendMailCoreService.send(appCode, subject, contents, to, cc, bcc, null, 0, null, null, null);
    }

    /**
     * 同步发送邮件
     *
     * @param appCode    应用编号
     * @param subject    邮件主题
     * @param contents   邮件内容
     * @param to         TO
     * @param cc         CC
     * @param bcc        BCC
     * @param mailLogUid 邮件日志UID
     * @param retryCount 发送重试次数
     * @return 送信结果 true：成功
     */
    public boolean syncSend(String appCode, String subject, String contents, String to, String cc, String bcc, String mailLogUid, int retryCount) {
        return this.sendMailCoreService.send(appCode, subject, contents, to, cc, bcc, mailLogUid, retryCount, null, null, null);
    }

    /**
     * 同步发送邮件
     *
     * @param appCode         应用编号
     * @param subject         邮件主题
     * @param contents        邮件内容
     * @param to              TO
     * @param cc              CC
     * @param bcc             BCC
     * @param mailLogUid      邮件日志UID
     * @param retryCount      发送重试次数
     * @param from            邮件发送人
     * @param fromPassword    邮件发送密码（DES加密后的密码）
     * @param attachmentNames 附件名称列表（文件名绝对路径+ 文件名）
     * @return 送信结果 true：成功
     */
    public boolean syncSend(String appCode, String subject, String contents, String to, String cc, String bcc, String mailLogUid, int retryCount, String from, String fromPassword, List<String> attachmentNames) {
        return this.sendMailCoreService.send(appCode, subject, contents, to, cc, bcc, mailLogUid, retryCount, from, fromPassword, attachmentNames);
    }
}