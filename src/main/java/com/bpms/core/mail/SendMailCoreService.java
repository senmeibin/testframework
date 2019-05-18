package com.bpms.core.mail;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.consts.MailConsts;
import com.bpms.core.sync.SyncDataService;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.DesUtils;
import com.bpms.sys.dao.MailLogDao;
import com.bpms.sys.entity.ext.MailLogExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.ParameterService;
import com.bpms.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.*;

@Service
class SendMailCoreService {
    /**
     * 邮件地址分隔符
     */
    private final static String MAIL_ADDRESS_SEPARATOR = ";";

    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private UserService userService;

    /**
     * 同步数据服务类
     */
    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private MailLogDao mailLogDao;

    /**
     * 邮件SMTP服务地址
     */
    private String mailSmtp;

    /**
     * 邮件发送账号
     */
    private String mailUserAccount;

    /**
     * 邮件发送账号密码
     */
    private String mailUserPassword;

    /**
     * 邮件发件人
     */
    private String mailSendUser;

    /**
     * 邮件服务器端口
     */
    private String mailPort;

    /**
     * 邮件发送服务相关参数初期化
     */
    private synchronized void initParameter() {
        //邮件SMTP服务器
        this.mailSmtp = this.parameterService.getValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_SMTP);
        //邮件发送账号
        this.mailUserAccount = this.parameterService.getValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_USER_ACCOUNT);
        //邮件发件人(未设定默认邮件账号)
        this.mailSendUser = this.parameterService.getValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_SEND_USER, mailUserAccount);
        //密码进行解密处理
        this.mailUserPassword = DesUtils.decrypt(this.parameterService.getValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_USER_PASSWORD));
        //未设定 默认为25
        this.mailPort = this.parameterService.getValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_PORT, "25");
    }

    /**
     * 发送邮件
     *
     * @param appCode             应用编号
     * @param subject             邮件主题
     * @param contents            邮件内容
     * @param to                  TO
     * @param cc                  CC
     * @param bcc                 BCC
     * @param mailLogUid          邮件日志UID
     * @param retryCount          发送重试次数
     * @param specifyFrom         邮件发送人
     * @param specifyFromPassword 指定邮件发送人的邮箱密码（DES加密后的密码）
     * @param attachmentNames     附件名称列表（文件名绝对路径+ 文件名）
     * @return true：发送成功/false：发送失败
     */
    public synchronized boolean send(String appCode, String subject, String contents, String to, String cc, String bcc, String mailLogUid, int retryCount, String specifyFrom, String specifyFromPassword, List<String> attachmentNames) {
        String mailFunctionEnable = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "MAIL_FUNCTION_ENABLE", "ON");

        //邮件功能未开启的场合，忽略邮件发送
        if (!StringUtils.equals(mailFunctionEnable, "ON")) {
            return true;
        }

        //接收人为空或者邮件内容为空 忽略发送
        if ((StringUtils.isEmpty(to) && StringUtils.isEmpty(cc)) || StringUtils.isEmpty(contents)) {
            return true;
        }

        //邮件发送服务相关参数初期化
        this.initParameter();
        //附件长度名限制
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        Properties props = System.getProperties();
        props.put("mail.smtp.host", this.mailSmtp);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", this.mailPort);

        Session session;
        String sendFromUserAccount = this.mailUserAccount;
        String sendMailSendUser = this.mailSendUser;
        String sendMailPassword = this.mailUserPassword;
        //加密的邮件password 解密
        String decryptFromPassword = DesUtils.decrypt(specifyFromPassword);
        //指定发送邮件账户和密码时
        if (StringUtils.isNotEmpty(specifyFrom) && StringUtils.isNotEmpty(decryptFromPassword)) {
            final String authMailUserAccount = specifyFrom;
            final String authMailUserPassword = decryptFromPassword;
            sendFromUserAccount = authMailUserAccount;
            sendMailSendUser = authMailUserAccount;
            sendMailPassword = authMailUserPassword;
            session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(authMailUserAccount, authMailUserPassword);
                }
            });
            //未指定时用系统默认的发送账号
        }
        else {
            session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailUserAccount, mailUserPassword);
                }
            });
        }

        session.setDebug(false);
        MimeMessage message = new MimeMessage(session);

        try {
            //设置发件人
            InternetAddress from = new InternetAddress(sendFromUserAccount, sendMailSendUser);
            message.setFrom(from);

            //设置收件人
            message.setRecipients(Message.RecipientType.TO, this.getInternetAddress(to));

            if (!StringUtils.isEmpty(cc)) {
                message.setRecipients(Message.RecipientType.CC, this.getInternetAddress(cc));
            }

            if (!StringUtils.isEmpty(bcc)) {
                message.setRecipients(Message.RecipientType.BCC, this.getInternetAddress(bcc));
            }

            //设置邮件标题
            message.setSubject(subject);

            //设置邮件正文
            BodyPart bp = new MimeBodyPart();

            bp.setContent(contents, "text/html;charset=utf-8;");

            MimeMultipart mp = new MimeMultipart();

            //把正文内容添加到mp里
            mp.addBodyPart(bp);
            int index = 0;
            //如果有附件， 处理附件
            if (CollectionUtils.isNotEmpty(attachmentNames)) {
                for (String attachmentName : attachmentNames) {
                    //文件名为空时吗， 继续下一条
                    if (StringUtils.isEmpty(attachmentName)) {
                        continue;
                    }
                    if (logger.isInfoEnabled()) {
                        logger.info("发送邮件名：{}", attachmentName);
                    }
                    index++;
                    //把附件加入到 mp 里
                    File attachmentFile = new File(attachmentName);
                    MimeBodyPart attachmentBody = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachmentFile);
                    attachmentBody.setDataHandler(new DataHandler(source));
                    //防止中文名乱码
                    attachmentBody.setFileName(MimeUtility.encodeText(attachmentFile.getName()));
                    mp.addBodyPart(attachmentBody);
                }
            }

            message.setContent(mp);
            message.saveChanges();

            // smtp验证，就是你用来发邮件的邮箱用户名密码
            Transport transport = session.getTransport("smtp");

            if (!transport.isConnected()) {
                transport.connect(this.mailSmtp, Integer.parseInt(this.mailPort), sendFromUserAccount, sendMailPassword);
            }
            //如果没有连上mail服务器
            if (!transport.isConnected()) {
                logger.error("邮件服务器连接失败，邮件参数 this.mailSmtp：{}，  appCode:{}, subject: {} , to ：{}， cc: {} , bcc : {} ", this.mailSmtp, appCode, subject, to, cc, bcc);
                //保存邮件发送日志 (由于连接出错， 不停用账号）
                this.saveMailLog(mailLogUid, appCode, subject, contents, sendMailSendUser, to, cc, bcc, retryCount, -1, "邮件服务器连接失败", false);
                return false;
            }
            else {
                if (logger.isInfoEnabled()) {
                    logger.info("邮件服务器连接成功，开始发送邮件，邮件参数 this.mailSmtp：{}，  appCode:{}, subject: {} , to ：{}， cc: {} , bcc : {} ", this.mailSmtp, appCode, subject, to, cc, bcc);
                }

                // 发送
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

                //保存邮件发送日志
                this.saveMailLog(mailLogUid, appCode, subject, contents, sendMailSendUser, to, cc, bcc, retryCount, 1, StringUtils.EMPTY, false);
            }

            return true;
        } catch (Exception ex) {
            boolean isNeedLock = false;
            //送信地址Invalid Addresses 自动停用账号  550 Mailbox not found or access denied
            if (ex instanceof SendFailedException && StringUtils.equals("Invalid Addresses", ex.getMessage())) {
                isNeedLock = true;
            }
            //保存邮件发送日志
            this.saveMailLog(mailLogUid, appCode, subject, contents, sendMailSendUser, to, cc, bcc, retryCount, -1, ex.getMessage(), isNeedLock);
            logger.error("邮件参数 appCode:{}, subject: {} , to ：{}， cc: {} , bcc : {} ", appCode, subject, to, cc, bcc);
            logger.error("邮件发送异常：", ex);
            return false;
        }
    }

    /**
     * 保存邮件发送日志
     *
     * @param uid        UID
     * @param appCode    应用编号
     * @param subject    邮件主题
     * @param contents   邮件内容
     * @param from       发件人
     * @param to         TO
     * @param cc         CC
     * @param bcc        BCC
     * @param result     发送结果
     * @param retryCount 发送重试次数
     * @param error      错误消息
     * @param isNeedLock 是否需要自动停用账号（true:是， false：不需要）
     */
    private void saveMailLog(String uid, String appCode, String subject, String contents, String from, String to, String cc, String bcc, int retryCount, int result, String error, boolean isNeedLock) {
        MailLogExt mailLogExt;
        if (StringUtils.isNotEmpty(uid)) {
            mailLogExt = this.mailLogDao.findOne(uid);
            if (mailLogExt == null) {
                mailLogExt = new MailLogExt();
                mailLogExt.setUid(uid);
            }
        }
        else {
            mailLogExt = new MailLogExt();
        }
        mailLogExt.setAppCode(appCode);
        mailLogExt.setSubject(subject);
        mailLogExt.setMessage(contents);
        mailLogExt.setMailFrom(from);
        mailLogExt.setMailTo(to);
        mailLogExt.setMailCc(cc);
        mailLogExt.setMailBcc(bcc);
        mailLogExt.setSendDate(new Date());
        mailLogExt.setRetryCount(retryCount);
        mailLogExt.setSendResult(result);
        mailLogExt.setErrorMessage(error);
        mailLogExt.setInsertUser(CmnConsts.ADMIN_USER_UID);
        mailLogExt.setUpdateUser(CmnConsts.ADMIN_USER_UID);
        mailLogExt.setRecordStatus(1);

        this.mailLogDao.save(mailLogExt);

        //如果重发次数达到 邮件发送最大重试次数 (并且to 账号不为空） 则 停用该账号
        if (StringUtils.equalsIgnoreCase("TRUE", parameterService.getValue(AppCodeConsts.APP_SYS, "AUTO_STOP_ACCOUNT", "FALSE")) && isNeedLock && (StringUtils.isNotEmpty(mailLogExt.getMailTo()))) {
            this.autoStopAccount(mailLogExt.getRetryCount(), mailLogExt.getMailTo());
        }
    }

    /**
     * 如果重发次数达到 邮件发送最大重试次数 则 停用该账号
     *
     * @param retryCount 重发次数
     */
    private void autoStopAccount(int retryCount, String mailAddress) {
        //获取邮件发送最大重试次数
        int maxRetryCount = parameterService.getIntValue(AppCodeConsts.APP_SYS, "SEND_MAIL_MAX_RETRY_COUNT_LIMIT", 3);
        if (retryCount >= maxRetryCount) {
            //根据邮箱查找用户
            Map<String, Object> condition = new HashMap<>();
            condition.put("main.user_mail", mailAddress);
            List<UserExt> userList = this.userService.search(UserExt.class, this.userService.getSearchSQL(condition), condition);
            if (CollectionUtils.isNotEmpty(userList)) {
                for (UserExt userExt : userList) {
                    //如果原来启用的用户， 设置为8：停用
                    if (userExt.getRecordStatus() == 1) {
                        //停用
                        userExt.setRecordStatus(8);
                        //停用处理时，清空分机号码、用户坐席、用户坐席密码字段信息
                        userExt.setUserExtensionNumber(null);
                        userExt.setUserAgent(null);
                        userExt.setUserAgentPassword(null);
                        String remark;
                        if (StringUtils.isEmpty(userExt.getRemark())) {
                            remark = "邮件发送失败次数超上限自动停用。";
                        }
                        else {
                            remark = userExt.getRemark() + " 邮件发送失败次数超上限自动停用。";
                            if (remark.length() > 256) {
                                remark = remark.substring(0, 256);
                            }
                        }
                        userExt.setRemark(remark);
                        this.userService.getDao().save(userExt);
                        //直接推送同步用户数据
                        syncDataService.directPushSyncDataTopic(userExt);
                    }
                }

            }
        }
    }

    /**
     * 分割多个邮件地址
     *
     * @param address 邮箱地址
     * @return 邮件地址数组列表
     */
    private InternetAddress[] getInternetAddress(String address) {
        List<InternetAddress> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(address)) {
            String[] addressArray = address.split(MAIL_ADDRESS_SEPARATOR);
            for (String add : addressArray) {
                try {
                    list.add(new InternetAddress(add));
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
        }
        return list.toArray(new InternetAddress[list.size()]);
    }
}