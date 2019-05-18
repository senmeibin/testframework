package com.bpms.cmn.service;

import com.bpms.cmn.dao.MessageReadDao;
import com.bpms.cmn.entity.ext.MessageReadExt;
import com.bpms.core.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息既读服务类
 */
@Service
public class MessageReadService extends CmnBaseService<MessageReadExt> {
    @Autowired
    private MessageReadDao messageReadDao;

    @Override
    public MessageReadDao getDao() {
        return messageReadDao;
    }

    /**
     * 保存消息既读记录
     *
     * @param relationUid 关联UID
     */
    public void saveMessageRead(String relationUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.relationUid", relationUid);
        condition.put("main.userUid", this.getCurrentUserId());
        List<MessageReadExt> messageReadList = this.search(condition);

        //消息既读记录不存在的场合，补全消息既读记录
        if (CollectionUtils.isEmpty(messageReadList)) {
            MessageReadExt messageRead = new MessageReadExt();
            //关联UID
            messageRead.setRelationUid(relationUid);
            //用户UID
            messageRead.setUserUid(this.getCurrentUserId());
            this.save(messageRead);
        }
        else {
            this.save(messageReadList.get(0));
        }
    }
}