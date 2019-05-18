package com.bpms.crm.service;

import com.bpms.crm.dao.ActivityCampusDao;
import com.bpms.crm.dao.ActivityDao;
import com.bpms.crm.entity.ext.ActivityCampusExt;
import com.bpms.crm.entity.ext.ActivityExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 活动信息服务类
 */
@Service
public class ActivityService extends CrmBaseService<ActivityExt> {
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ActivityCampusDao activityCampusDao;

    @Override
    public ActivityDao getDao() {
        return activityDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ActivityExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ActivityExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ActivityExt saveBefore(ActivityExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ActivityExt saveAfter(ActivityExt entity) {
        //删除既有数据
        this.activityCampusDao.delete("activity_uid", entity.getUid());

        //活动参与校区UID集合
        String[] ids = entity.getCampusUids().split(",");
        for (String id : ids) {
            ActivityCampusExt e = new ActivityCampusExt();
            e.setActivityUid(entity.getUid());
            e.setCampusUid(id);
            this.activityCampusDao.save(e);
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
    protected Boolean deleteBefore(ActivityExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ActivityExt entity) {
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