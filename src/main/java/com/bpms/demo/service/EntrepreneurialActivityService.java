package com.bpms.demo.service;

import com.bpms.core.utils.StringUtils;
import com.bpms.demo.dao.EntrepreneurialActivityDao;
import com.bpms.demo.dao.EntrepreneurialActivityTutorDao;
import com.bpms.demo.entity.ext.EntrepreneurialActivityExt;
import com.bpms.demo.entity.ext.EntrepreneurialActivityTutorExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创业活动服务类
 */
@Service
public class EntrepreneurialActivityService extends DemoBaseService<EntrepreneurialActivityExt> {
    @Autowired
    private EntrepreneurialActivityDao entrepreneurialActivityDao;

    /**
     * 创业活动导师数据访问类
     */
    @Autowired
    private EntrepreneurialActivityTutorDao entrepreneurialActivityTutorDao;

    @Override
    public EntrepreneurialActivityDao getDao() {
        return entrepreneurialActivityDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(EntrepreneurialActivityExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(EntrepreneurialActivityExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected EntrepreneurialActivityExt saveBefore(EntrepreneurialActivityExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected EntrepreneurialActivityExt saveAfter(EntrepreneurialActivityExt entity) {
        //删除旧数据
        this.entrepreneurialActivityTutorDao.delete("demo_entrepreneurial_activity_tutor", "entrepreneurial_activity_uid", entity.getUid());

        if (StringUtils.isNotEmpty(entity.getTutorUid())) {
            String[] tutors = entity.getTutorUid().split(",");
            for (String tutor : tutors) {
                EntrepreneurialActivityTutorExt activityTutorExt = new EntrepreneurialActivityTutorExt();
                //创业活动UID
                activityTutorExt.setEntrepreneurialActivityUid(entity.getUid());
                //导师UID
                activityTutorExt.setTutorUid(tutor);
                //保存
                this.entrepreneurialActivityTutorDao.save(activityTutorExt);
            }
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
    protected Boolean deleteBefore(EntrepreneurialActivityExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(EntrepreneurialActivityExt entity) {
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