package com.bpms.pms.service;

import com.bpms.pms.dao.ResearchInfoCommentDao;
import com.bpms.sys.entity.ext.AttachmentExt;
import com.bpms.sys.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bpms.core.service.BaseService;
import com.bpms.pms.dao.ResearchInfoDao;
import com.bpms.pms.entity.ext.ResearchInfoExt;
import com.bpms.pms.service.PmsBaseService;
import com.bpms.pms.dao.ResearchInfoCommentDao;
import com.bpms.pms.entity.ext.ResearchInfoCommentExt;

import java.util.List;

/**
 * 市场调研服务类
 */
@Service
public class ResearchInfoService extends PmsBaseService<ResearchInfoExt> {
    @Autowired
    private ResearchInfoDao researchInfoDao;

    @Autowired
    private ResearchInfoCommentDao researchInfoCommentDao;

    @Autowired
    private AttachmentService attachmentService;

    @Override
    public ResearchInfoDao getDao() {
        return researchInfoDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ResearchInfoExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ResearchInfoExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected ResearchInfoExt saveBefore(ResearchInfoExt entity) {    return entity;   }

    /**
     * 数据保存后处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected ResearchInfoExt saveAfter(ResearchInfoExt entity) {
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
     this.researchInfoCommentDao.delete("research_info_uid",this.findOne(ids).getUid());
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
        List<AttachmentExt> list = attachmentService.findByRelationUid(ids);
        for(AttachmentExt ae:list) {
            attachmentService.delete(ae.getUid());
        }
        return true;
    }

    /**
     * 数据删除前处理[子类覆盖]
     * 
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(ResearchInfoExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     * 
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ResearchInfoExt entity) {
        return true;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     * 
     * @param uid 主键UID
     * @param recordStatus 记录状态
     * 
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     * 
     * @param uid 主键UID
     * @param recordStatus 记录状态
     * 
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        return true;
    }
}