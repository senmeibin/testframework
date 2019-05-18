package com.bpms.cmn.dao;

import com.bpms.cmn.entity.ext.MessageExt;
import com.bpms.core.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * 消息推送管理表数据访问类
 */
@Component("CmnMessageDao")
public interface MessageDao extends BaseDao<MessageExt, String> {

}