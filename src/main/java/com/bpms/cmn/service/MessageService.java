package com.bpms.cmn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.activemq.consts.JMSConsts;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.cmn.dao.MessageDao;
import com.bpms.cmn.entity.ext.MessageDetailExt;
import com.bpms.cmn.entity.ext.MessageExt;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.*;
import com.bpms.sys.entity.MailLog;
import com.bpms.sys.entity.SmsLog;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.entity.ext.OnlineUserExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.entity.ext.UserRoleExt;
import com.bpms.sys.service.DeptService;
import com.bpms.sys.service.OnlineUserService;
import com.bpms.sys.service.UserRoleService;
import com.bpms.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 消息推送管理表服务类
 */
@Service(value = "CmnMessageService")
public class MessageService extends CmnBaseService<MessageExt> {

    @Autowired
    private MessageDao messageDao;

    /**
     * Service对象
     */
    @Autowired
    private UserService userService;

    /**
     * 用户共通Service对象
     */
    @Autowired
    private CmnUserService cmnUserService;

    /**
     * 消息推送明细Service对象
     */
    @Autowired
    private MessageDetailService messageDetailService;

    /**
     * 用户角色Service对象
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 部门Service对象
     */
    @Autowired
    private DeptService deptService;

    /**
     * 在线用户Service对象
     */
    @Autowired
    private OnlineUserService onlineUserService;

    @Override
    public MessageDao getDao() {
        return messageDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(MessageExt entity) {
        //03：指定部门
        if (StringUtils.equals("03", entity.getSendTypeCd())) {
            String sendDeptUids = entity.getSendDeptUids();
            String[] sendDeptUidsArray = StringUtils.isEmpty(sendDeptUids) ? new String[0] : sendDeptUids.split(",");
            for (String deptUid : sendDeptUidsArray) {
                //获取部门人员
                Map<String, Object> condition = new HashMap<>();
                condition.put("main.deptUid", deptUid);
                List<UserExt> userList = this.userService.search(UserExt.class, userService.getSearchSQL(condition), condition);

                //部门无人员场合
                if (CollectionUtils.isEmpty(userList)) {
                    throw new ServiceException("选定的部门无人员，请重新选择。");
                }
            }
        }
        //04：指定角色
        else if (StringUtils.equals("04", entity.getSendTypeCd())) {
            String sendRoleUids = entity.getSendRoleUids();
            String[] sendRoleUidsArray = StringUtils.isEmpty(sendRoleUids) ? new String[0] : sendRoleUids.split(",");
            //用于去重（用户存在于多个角色的情景）
            Set<String> userUidSet = new HashSet<>();
            for (String roleUid : sendRoleUidsArray) {
                //获取角色人员
                Map<String, Object> condition = new HashMap<>();
                condition.put("main.roleUid", roleUid);
                condition.put("users.recordStatus", "1");
                List<UserRoleExt> userRoleList = this.userRoleService.search(UserRoleExt.class, this.userRoleService.getSearchSQL(condition), condition);

                //角色无人员场合
                if (CollectionUtils.isEmpty(userRoleList)) {
                    throw new ServiceException("选定的角色无人员，请重新选择。");
                }
            }
        }
    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(MessageExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected MessageExt saveBefore(MessageExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected MessageExt saveAfter(MessageExt entity) {
        //删除旧的明细数据
        this.messageDetailService.delete("message_uid", entity.getUid());
        //添加所有明细数据
        List<MessageDetailExt> detailList = new ArrayList<>();
        //01：全部人员
        if (StringUtils.equals("01", entity.getSendTypeCd())) {
            //读取所有的人员
            List<UserExt> userList = this.cmnUserService.getAllServingUser();
            if (CollectionUtils.isNotEmpty(userList)) {
                for (UserExt user : userList) {
                    detailList.add(this.createMessageDetail(entity.getUid(), user.getUid()));
                }
            }
        }
        //02：指定人员
        else if (StringUtils.equals("02", entity.getSendTypeCd())) {
            String selectedUserUids = entity.getSendUserUids();
            String[] selectedUserUidsArray = StringUtils.isEmpty(selectedUserUids) ? new String[0] : selectedUserUids.split(",");
            for (String userUid : selectedUserUidsArray) {
                detailList.add(this.createMessageDetail(entity.getUid(), userUid));
            }
        }
        //03：指定部门
        else if (StringUtils.equals("03", entity.getSendTypeCd())) {
            String sendDeptUids = entity.getSendDeptUids();
            String[] sendDeptUidsArray = StringUtils.isEmpty(sendDeptUids) ? new String[0] : sendDeptUids.split(",");
            for (String deptUid : sendDeptUidsArray) {
                //获取部门人员
                Map<String, Object> condition = new HashMap<>();
                condition.put("main.deptUid", deptUid);
                List<UserExt> userList = this.userService.search(UserExt.class, userService.getSearchSQL(condition), condition);

                if (CollectionUtils.isNotEmpty(userList)) {
                    for (UserExt user : userList) {
                        detailList.add(this.createMessageDetail(entity.getUid(), user.getUid()));
                    }
                }
            }
        }
        //04：指定角色
        else if (StringUtils.equals("04", entity.getSendTypeCd())) {
            String sendRoleUids = entity.getSendRoleUids();
            String[] sendRoleUidsArray = StringUtils.isEmpty(sendRoleUids) ? new String[0] : sendRoleUids.split(",");
            //用于去重（用户存在于多个角色的情景）
            Set<String> userUidSet = new HashSet<>();
            for (String roleUid : sendRoleUidsArray) {
                //获取角色人员
                Map<String, Object> condition = new HashMap<>();
                condition.put("main.roleUid", roleUid);
                condition.put("users.recordStatus", "1");
                List<UserRoleExt> userRoleList = this.userRoleService.search(UserRoleExt.class, this.userRoleService.getSearchSQL(condition), condition);

                if (CollectionUtils.isNotEmpty(userRoleList)) {
                    for (UserRoleExt userRole : userRoleList) {
                        userUidSet.add(userRole.getUserUid());
                    }
                }
            }
            //创建息推送管理明细表实体类
            for (String userUid : userUidSet) {
                detailList.add(this.createMessageDetail(entity.getUid(), userUid));
            }
        }
        //05：在线人员
        else if (StringUtils.equals("05", entity.getSendTypeCd())) {
            //读取在线人员
            // 取得查询条件
            Map<String, Object> condition = new HashMap<>();
            // 会话超时时间
            int sessionTimeout = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "SESSION_TIMOUT", 30);
            // 只取有效的会话
            condition.put("main.updateDate$>=", DateUtils.format(DateUtils.addMinutes(DateUtils.getNowDate(), (0 - sessionTimeout)), CmnConsts.DATE_TIME_FORMAT));
            List<OnlineUserExt> userList = this.onlineUserService.search(OnlineUserExt.class, condition);
            if (CollectionUtils.isNotEmpty(userList)) {
                for (OnlineUserExt user : userList) {
                    detailList.add(this.createMessageDetail(entity.getUid(), user.getUid()));
                }
            }
        }
        //提示总人数
        entity.setAllCount(detailList.size());
        //未读总人数
        entity.setUnreadCount(detailList.size());
        //保存人数信息
        this.getDao().save(entity);
        //保存明细数据
        for (MessageDetailExt detail : detailList) {
            this.messageDetailService.save(detail);
        }

        List<Object[]> allUnreadUserMessageCountList;
        //获取所有的用户未读信息数
        if (detailList.size() > 1) {
            List<String> userUids = new ArrayList<>();
            for (MessageDetailExt detail : detailList) {
                userUids.add(detail.getUserUid());
            }
            allUnreadUserMessageCountList = this.messageDetailService.getDao().getUserUnreadMessageCount(userUids);
        }
        else {
            allUnreadUserMessageCountList = this.messageDetailService.getDao().getUserUnreadMessageCount(detailList.get(0).getUserUid());
        }

        Map<String, Integer> allUnreadUserMessageCountMap = new HashMap<>();
        for (Object[] objects : allUnreadUserMessageCountList) {
            //key:用户UID value:未读数据数量
            allUnreadUserMessageCountMap.put(objects[0].toString(), Integer.valueOf(objects[1].toString()));
        }
        //推送消息
        for (MessageDetailExt detail : detailList) {
            try {
                //设置当前用户未读信息数
                entity.setCurrentUserUnreadCount(allUnreadUserMessageCountMap.get(detail.getUserUid()) == null ? 0 : allUnreadUserMessageCountMap.get(detail.getUserUid()));

                String messagePushEnabled = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "MESSAGE_PUSH_ENABLED", "false");
                if (StringUtils.equals(messagePushEnabled, "true")) {
                    MessagePushService.sendMessage(detail.getUserUid(), JsonUtils.toJSON(entity));
                }
            } catch (JsonProcessingException je) {
                log.error("推送消息转换出错。", je);
            }
        }

        //发送邮件或短信通知
        this.sendMailOrSmsNotice(entity);
        return entity;
    }

    /**
     * 创建消息推送管理明细表实体类
     *
     * @param messageUid 消息推送管理表UID
     * @param userUid    用户UID
     * @return
     */
    private MessageDetailExt createMessageDetail(String messageUid, String userUid) {
        MessageDetailExt detail = new MessageDetailExt();
        //消息推送管理表实体UID
        detail.setMessageUid(messageUid);
        //用户UID
        detail.setUserUid(userUid);
        return detail;
    }

    /**
     * 发送邮件或短信通知
     *
     * @param entity 消息推送管理表实体类
     */
    private void sendMailOrSmsNotice(MessageExt entity) {
        //需要发送邮件
        boolean needSendMail = entity.getIsMailNotice() != null && entity.getIsMailNotice() == 1;
        //需要发送短信
        boolean needSendSms = entity.getIsSmsNotice() != null && entity.getIsSmsNotice() == 1;
        if (needSendMail || needSendSms) {
            ActiveMQSenderService activeMQSenderService;
            try {
                activeMQSenderService = SpringUtils.getBean(ActiveMQSenderService.class);
            } catch (Exception e) {
                log.error("获取activeMQ服务失败。");
                return;
            }
            //获取用户信息
            List<UserExt> userList = this.messageDetailService.getDao().getUserByMessageUid(entity.getUid());
            //发送邮件
            if (needSendMail) {
                //邮件接收地址
                List<String> mailAddressList = new ArrayList<>();
                for (UserExt user : userList) {
                    if (StringUtils.isNotEmpty(user.getUserMail())) {
                        mailAddressList.add(user.getUserMail());
                    }
                }
                //创建发送邮件的实体类
                MailLog mailLog = new MailLog();
                //邮件主题
                mailLog.setSubject(entity.getMessageTitle());
                //邮件内容
                mailLog.setMessage(entity.getMessageContent());
                //收件人
                mailLog.setMailTo(StringUtils.join(mailAddressList.toArray(new String[mailAddressList.size()]), ";"));
                //邮件推送到mq服务器
                try {
                    activeMQSenderService.pushQueue(JMSConsts.JMS_QUEUE_NAME_EMAIL, JsonUtils.toJSON(mailLog));
                } catch (Exception e) {
                    log.error("发送邮件失败。");
                }
            }
            //发送短信
            if (needSendSms) {
                //短信接收地址
                for (UserExt user : userList) {
                    if (StringUtils.isNotEmpty(user.getUserPhone())) {
                        //创建发送短信的实体类
                        SmsLog smsLog = new SmsLog();
                        //短信内容
                        smsLog.setMessage(entity.getMessageContent());
                        //手机号码
                        smsLog.setMobile(user.getUserPhone());
                        //邮件推送到mq服务器
                        try {
                            activeMQSenderService.pushQueue(JMSConsts.JMS_QUEUE_NAME_SMS, JsonUtils.toJSON(smsLog));
                        } catch (Exception e) {
                            log.error("发送短信失败。");
                        }
                    }
                }
            }
        }
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(String ids) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(String ids) {
        return true;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(MessageExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(MessageExt entity) {
        return true;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        return true;
    }

    /**
     * 取所有在职人员信息
     *
     * @return 在职人员一览
     */
    public List<UserExt> getAllUserList() {
        List<UserExt> list = new ArrayList<>();
        //所有在职人员
        List<UserExt> userList = this.cmnUserService.getAllServingUser();
        //所有有人员的部门(UID)
        Set<String> userDeptSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (UserExt user : userList) {
                userDeptSet.add(user.getDeptUid());
                //加入人员
                list.add(user);
            }
        }
        //所有部门信息
        List<DeptExt> deptList = this.deptService.search(DeptExt.class, this.deptService.getSQL("/sys/Dept/getAllDeptList"));
        //所有有人员的部门(UID)及其上级部门
        Set<String> userAllDeptExtSet = new HashSet<>();
        for (String deptUid : userDeptSet) {
            //找到该部门所有上级部门
            List<String> childDeptUidList = new ArrayList<>();
            childDeptUidList = deptService.getParentDeptList(deptList, deptUid, childDeptUidList);
            //加入当前部门
            childDeptUidList.add(deptUid);

            userAllDeptExtSet.addAll(childDeptUidList);
        }
        //将有人员的部门组装成人员类
        if (CollectionUtils.isNotEmpty(deptList)) {
            for (DeptExt dept : deptList) {
                for (String deptUid : userAllDeptExtSet) {
                    if (StringUtils.equals(dept.getUid(), deptUid)) {
                        UserExt user = new UserExt();
                        user.setUid(dept.getUid());
                        user.setUserName(dept.getDeptName());
                        user.setDeptUid(dept.getParentDeptUid());
                        list.add(user);
                        break;
                    }
                }
            }
        }
        return list;
    }

    /**
     * 查询选择的人员Uid列表
     *
     * @param entity 消息推送管理表实体
     * @return 人员Uid列表字符串（逗号分隔）
     */
    public String getSendSelectedUserUids(MessageExt entity) {
        //当发送区分不为 02:指定人员时，不加载
        if (!StringUtils.equals("02", entity.getSendTypeCd())) {
            return null;
        }
        Map<String, Object> condition = new HashMap<>();
        //查询条件:消息UID
        condition.put("messageUid", entity.getUid());
        List<MessageDetailExt> list = this.messageDetailService.search(MessageDetailExt.class, this.messageDetailService.getSearchSQL(condition), condition);
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> messageUidList = new ArrayList<>();
            for (MessageDetailExt detail : list) {
                messageUidList.add(detail.getUserUid());
            }
            String[] messageUidArray = messageUidList.toArray(new String[messageUidList.size()]);
            return StringUtils.join(messageUidArray, ",");
        }
        return null;
    }

    /**
     * 获取当前用户未读信息数
     *
     * @return 未读信息数
     */
    public int getUnreadCount() {
        return this.messageDetailService.getDao().getUnreadMessageCountByUserUid(this.getCurrentUserId());
    }

    /**
     * 复制消息
     *
     * @param messageUid 消息UID
     * @return 消息复制处理结果（AjaxResult）
     */
    public AjaxResult copyMessage(String messageUid) {
        //新的uid
        String newMessageUid = null;
        try {
            //根据uid获取实体数据
            MessageExt messageExt = this.findOne(messageUid);
            if (messageExt == null) {
                throw new ServiceException("被复制的消息已经不存在，请刷新页面。");
            }
            //生成新的uid
            newMessageUid = UniqueUtils.getUID();

            MessageExt newMessageExt = new MessageExt();
            BeanUtils.copyProperties(messageExt, newMessageExt);
            newMessageExt.setUid(newMessageUid);
            //消息提醒开始时间(初始为今天0点)
            newMessageExt.setMessageStartDate(DateUtils.parseDate(DateUtils.getNowDateString() + " 00:00:00"));
            //消息提醒结束时间(初始为今天23点)
            newMessageExt.setMessageEndDate(DateUtils.parseDate(DateUtils.getNowDateString() + " 23:59:59"));
            //保存
            this.getDao().save(newMessageExt);
        } catch (Exception e) {
            throw new ServiceException("复制消息出错了，请联络管理员。");
        }
        return AjaxResult.createSuccessResult("复制消息成功。", newMessageUid);
    }


    /**
     * 发送系统消息通知给指定用户
     *
     * @param title              标题
     * @param content            内容
     * @param userUid            指定用户UID
     * @param importanceDegreeCd 重要度(01:普通/02：重要/03：紧急)
     */
    public void sendMessageNoticeToUser(String title, String content, String userUid, String importanceDegreeCd) {
        //添加消息主信息
        MessageExt message = new MessageExt();
        //手动设置主键
        message.setUid(UniqueUtils.getUID());
        //消息类型 01：手动通知
        message.setMessageTypeCd("01");
        //消息标题
        message.setMessageTitle(title);
        //消息内容
        message.setMessageContent(String.format("<div onclick=\"SysApp.Cmn.MessageDetailPopupListIns.readMessage('%s');\">%s</div>", message.getUid(), content));
        //重要度 01:普通/02：重要/03：紧急
        message.setImportanceDegreeCd(importanceDegreeCd);
        //发送区分 02：指定人员
        message.setSendTypeCd("02");
        //消息开始时间 当前时间
        message.setMessageStartDate(new Date());
        //消息结束时间 当前时间 + 1天
        message.setMessageEndDate(DateUtils.addDate(new Date(), 1));
        //指定消息接收人
        message.setSendUserUids(userUid);
        this.save(message);
    }
}