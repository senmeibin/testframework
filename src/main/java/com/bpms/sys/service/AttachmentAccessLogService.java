package com.bpms.sys.service;

import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.DateUtils;
import com.bpms.sys.dao.AttachmentAccessLogDao;
import com.bpms.sys.entity.ext.AttachmentAccessLogExt;
import com.bpms.sys.entity.ext.AttachmentExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 附件访问日志服务类
 */
@Service
public class AttachmentAccessLogService extends SysBaseService<AttachmentAccessLogExt> {
    @Autowired
    private AttachmentAccessLogDao attachmentAccessLogDao;

    @Autowired
    private AttachmentService attachmentService;

    @Override
    public AttachmentAccessLogDao getDao() {
        return attachmentAccessLogDao;
    }

    /**
     * 写入附件访问日志
     *
     * @param uid        附件UID
     * @param accessType 访问区分(0 ： 附件预览 / 1 ： 附件下载)
     * @return 附件实体信息
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized AttachmentExt writeAttachmentAccessLog(String uid, int accessType) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.uid", uid);
        //锁表操作
        condition.put("forUpdate$ignore_search", true);
        List<AttachmentExt> list = this.attachmentService.search(condition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        //取得附件文件实体
        AttachmentExt attachment = list.get(0);

        //登录信息为空的场合，直接返回附件实体信息
        if (this.getCurrentUser() == null) {
            return attachment;
        }

        Map<String, Object> columnSetting = new HashMap<>();
        if (accessType == 0) {
            //预览次数累加
            if (attachment.getPreviewCount() == null) {
                attachment.setPreviewCount(0);
            }
            attachment.setPreviewCount(attachment.getPreviewCount() + 1);
            columnSetting.put("preview_count", attachment.getPreviewCount());
        }
        else {
            //下载次数累加
            if (attachment.getDownloadCount() == null) {
                attachment.setDownloadCount(0);
            }
            attachment.setDownloadCount(attachment.getDownloadCount() + 1);
            columnSetting.put("download_count", attachment.getDownloadCount());
        }
        columnSetting.put("update_date", DateUtils.getNowDate());
        condition.clear();
        condition.put("uid", attachment.getUid());
        this.attachmentService.getDao().executeUpdate(columnSetting, condition);

        //写入附件访问日志
        AttachmentAccessLogExt attachmentAccessLog = new AttachmentAccessLogExt();
        attachmentAccessLog.setEnterpriseUid(this.getCurrentUser().getEnterpriseUid());
        attachmentAccessLog.setAttachmentUid(attachment.getUid());
        attachmentAccessLog.setUserUid(this.getCurrentUserId());
        //0：附件预览/1：附件下载
        attachmentAccessLog.setAccessType(accessType);
        this.attachmentAccessLogDao.save(attachmentAccessLog);
        return attachment;
    }
}