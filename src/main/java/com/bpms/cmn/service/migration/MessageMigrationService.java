package com.bpms.cmn.service.migration;

import com.bpms.cmn.dao.MessageDao;
import com.bpms.cmn.entity.ext.MessageExt;
import com.bpms.cmn.service.CmnBaseService;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息迁移服务类
 */
@Service
public class MessageMigrationService extends CmnBaseService<MessageExt> {
    @Autowired
    private MessageDao messageDao;

    @Override
    public MessageDao getDao() {
        return messageDao;
    }

    /**
     * 迁移X天前的消息
     *
     * @return 迁移记录数
     */
    @Transactional
    public int migrationMessage() {
        // 迁移X天以前的消息
        int migrationBeforeDays = parameterService.getIntValue(AppCodeConsts.APP_COMMON, "MIGRATION_MESSAGE_RECORD_BEFORE_DAYS", 30);

        // 计算迁移时间
        String insertDate = DateUtils.format(DateUtils.addDate(new Date(), (-migrationBeforeDays)), CmnConsts.DATE_FORMAT) + " 23:59:59";

        Map<String, Object> condition = new HashMap<>();
        condition.put("insertDate", insertDate);

        // 迁移数据
        int executeRecordCount = this.getDao().executeUpdate(this.getSQL("cmn/migration/MessageMigration/migrationMessage"), condition);
        this.getDao().executeUpdate(this.getSQL("cmn/migration/MessageMigration/migrationMessageDetail"), condition);

        // 删除已迁移过的数据
        this.getDao().executeUpdate(this.getSQL("cmn/migration/MessageMigration/deleteMessage"), condition);
        this.getDao().executeUpdate(this.getSQL("cmn/migration/MessageMigration/deleteMessageDetail"), condition);

        return executeRecordCount;
    }
}