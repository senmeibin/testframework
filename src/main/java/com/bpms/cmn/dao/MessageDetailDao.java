package com.bpms.cmn.dao;

import com.bpms.cmn.entity.ext.MessageDetailExt;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.dao.BaseDao;
import com.bpms.sys.entity.ext.UserExt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息推送管理明细表数据访问类
 */
@Component("CmnMessageDetailDao")
public interface MessageDetailDao extends BaseDao<MessageDetailExt, String> {
    /**
     * 获取指定用户所有信息数
     *
     * @param userUid 用户UID
     * @return 信息数
     */
    @Query("SELECT COUNT(1) FROM CmnMessageDetail t WHERE t.userUid = ?1")
    public int getMessageCountByUserUid(String userUid);

    /**
     * 获取指定用户未读信息数
     *
     * @param userUid 用户UID
     * @return 用户未读信息数
     */
    @Query("SELECT COUNT(1) FROM CmnMessageDetail t WHERE t.readTypeCd = '01' AND t.userUid = ?1 AND t.recordStatus = " + CmnConsts.RECORD_STATUS_ACTIVE + "")
    public int getUnreadMessageCountByUserUid(String userUid);

    /**
     * 获取所有的用户未读信息数
     *
     * @return 用户未读信息数
     */
    @Query("SELECT t.userUid, COUNT(1) AS recordCount FROM CmnMessageDetail t WHERE t.readTypeCd = '01' AND t.recordStatus = " + CmnConsts.RECORD_STATUS_ACTIVE + " GROUP BY t.userUid ")
    public List<Object[]> getAllUserUnreadMessageCount();

    /**
     * 获取指定用户未读信息数
     *
     * @return 用户未读信息数
     */
    @Query("SELECT t.userUid, COUNT(1) AS recordCount FROM CmnMessageDetail t WHERE t.readTypeCd = '01' AND t.userUid = ?1 AND t.recordStatus = " + CmnConsts.RECORD_STATUS_ACTIVE + " GROUP BY t.userUid ")
    public List<Object[]> getUserUnreadMessageCount(String userUid);

    /**
     * 获取指定用户未读信息数
     *
     * @return 用户未读信息数
     */
    @Query("SELECT t.userUid, COUNT(1) AS recordCount FROM CmnMessageDetail t WHERE t.readTypeCd = '01' AND t.userUid IN (?1) AND t.recordStatus = " + CmnConsts.RECORD_STATUS_ACTIVE + " GROUP BY t.userUid ")
    public List<Object[]> getUserUnreadMessageCount(List<String> userUids);

    /**
     * 根据消息推送管理表UID获取发送消息的用户信息
     *
     * @param messageUid 消息推送管理表UID
     * @return 用户一览
     */
    @Query("SELECT t FROM UserExt t WHERE t.uid in (SELECT m.userUid FROM CmnMessageDetail m WHERE m.messageUid = ?1 AND t.recordStatus = " + CmnConsts.RECORD_STATUS_ACTIVE + ")")
    public List<UserExt> getUserByMessageUid(String messageUid);

    /**
     * 根据消息推送管理表UID获取消息明细信息
     *
     * @param messageUid 消息推送管理表UID
     * @return 消息明细一览
     */
    @Query("SELECT t FROM CmnMessageDetail t WHERE t.messageUid = ?1 AND t.recordStatus = " + CmnConsts.RECORD_STATUS_ACTIVE + ")")
    public List<MessageDetailExt> getMessageDetailListByMessageUid(String messageUid);
}