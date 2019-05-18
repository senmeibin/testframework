package com.bpms.crm.service;

import com.bpms.crm.dao.CampusConsultantDao;
import com.bpms.crm.dao.CampusDao;
import com.bpms.crm.entity.ext.CampusConsultantExt;
import com.bpms.crm.entity.ext.CampusExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 校区信息服务类
 */
@Service
public class CampusService extends CrmBaseService<CampusExt> {
    @Autowired
    private CampusDao campusDao;

    @Autowired
    private CampusConsultantDao campusConsultantDao;

    @Override
    public CampusDao getDao() {
        return campusDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CampusExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CampusExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CampusExt saveBefore(CampusExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CampusExt saveAfter(CampusExt entity) {
        //删除既有数据
        this.campusConsultantDao.delete("campus_uid", entity.getUid());

        //校区顾问UID集合
        String[] ids = entity.getConsultantUserUids().split(",");
        for (String id : ids) {
            CampusConsultantExt e = new CampusConsultantExt();
            e.setCampusUid(entity.getUid());
            e.setConsultantUserUid(id);
            this.campusConsultantDao.save(e);
        }

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
    protected Boolean deleteBefore(CampusExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CampusExt entity) {
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
}