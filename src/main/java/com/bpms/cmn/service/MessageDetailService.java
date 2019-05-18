package com.bpms.cmn.service;

import com.bpms.cmn.dao.MessageDetailDao;
import com.bpms.cmn.entity.ext.MessageDetailExt;
import com.bpms.cmn.entity.ext.MessageExt;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息推送管理明细表服务类
 */
@Service(value = "CmnMessageDetailService")
public class MessageDetailService extends CmnBaseService<MessageDetailExt> {
    @Autowired
    private MessageDetailDao messageDetailDao;

    @Autowired
    private MessageService messageService;

    @Override
    public MessageDetailDao getDao() {
        return messageDetailDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(MessageDetailExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(MessageDetailExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected MessageDetailExt saveBefore(MessageDetailExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected MessageDetailExt saveAfter(MessageDetailExt entity) {
        return entity;
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
    protected Boolean deleteBefore(MessageDetailExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(MessageDetailExt entity) {
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
     * 数据已读处理
     *
     * @param uids 消息推送管理明细表UID集合
     */
    @Transactional
    public void setMessageReaded(String uids) {
        //参数验证
        if (StringUtils.isEmpty(uids)) {
            throw new ServiceException("参数错误，设置消息已读失败，请稍后重试。");
        }

        String[] messageDetailUids = uids.split(",");
        //循环标记已读
        for (String messageDetailUid : messageDetailUids) {
            MessageDetailExt entity = this.findOne(messageDetailUid);
            //根据消息明细UID进行已读设置的场合
            if (entity != null) {
                //02：已读
                entity.setReadTypeCd("02");
                //保存数据
                this.getDao().save(entity);

                //修改未读人数
                MessageExt messageExt = messageService.findOne(entity.getMessageUid());
                if (messageExt.getUnreadCount() < 1) {
                    messageExt.setUnreadCount(0);
                }
                else {
                    messageExt.setUnreadCount(messageExt.getUnreadCount() - 1);
                }
                this.messageService.getDao().save(messageExt);
            }
            //根据消息UID进行已读设置的场合
            else {
                List<MessageDetailExt> list = this.messageDetailDao.getMessageDetailListByMessageUid(messageDetailUid);
                if (CollectionUtils.isEmpty(list)) {
                    return;
                }

                for (MessageDetailExt detail : list) {
                    //02：已读
                    detail.setReadTypeCd("02");
                    //保存数据
                    this.getDao().save(detail);
                }

                //修改未读人数
                MessageExt messageExt = messageService.findOne(messageDetailUid);
                if (messageExt.getUnreadCount() < list.size()) {
                    messageExt.setUnreadCount(0);
                }
                else {
                    messageExt.setUnreadCount(messageExt.getUnreadCount() - list.size());
                }
                this.messageService.getDao().save(messageExt);
            }
        }
    }

    /**
     * 获取指定用户所有信息数
     *
     * @param userUid 用户UID
     * @return 信息数
     */
    public int getMessageCountByUserUid(String userUid) {
        return this.getDao().getMessageCountByUserUid(userUid);
    }
}