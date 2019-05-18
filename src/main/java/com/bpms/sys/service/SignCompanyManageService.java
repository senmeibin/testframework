package com.bpms.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bpms.core.service.BaseService;
import com.bpms.sys.dao.SignCompanyManageDao;
import com.bpms.sys.entity.ext.SignCompanyManageExt;
import com.bpms.sys.service.SysBaseService;

/**
 * 管辖签单公司服务类
 */
@Service
public class SignCompanyManageService extends SysBaseService<SignCompanyManageExt> {
    @Autowired
    private SignCompanyManageDao signCompanyManageDao;

    @Override
    public SignCompanyManageDao getDao() {
        return signCompanyManageDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(SignCompanyManageExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(SignCompanyManageExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected SignCompanyManageExt saveBefore(SignCompanyManageExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected SignCompanyManageExt saveAfter(SignCompanyManageExt entity) {
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
    protected Boolean deleteBefore(SignCompanyManageExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     * 
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(SignCompanyManageExt entity) {
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