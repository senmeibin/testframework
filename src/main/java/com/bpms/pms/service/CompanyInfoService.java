package com.bpms.pms.service;

import com.bpms.sys.entity.ext.AttachmentExt;
import com.bpms.sys.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bpms.core.service.BaseService;
import com.bpms.pms.dao.CompanyInfoDao;
import com.bpms.pms.entity.ext.CompanyInfoExt;
import com.bpms.pms.service.PmsBaseService;

import java.util.List;

/**
 * 公司资讯服务类
 */
@Service
public class CompanyInfoService extends PmsBaseService<CompanyInfoExt> {
    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Override
    public CompanyInfoDao getDao() {
        return companyInfoDao;
    }

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CompanyInfoExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CompanyInfoExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected CompanyInfoExt saveBefore(CompanyInfoExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected CompanyInfoExt saveAfter(CompanyInfoExt entity) {
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
    protected Boolean deleteBefore(CompanyInfoExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     * 
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CompanyInfoExt entity) {
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