package com.bpms.sys.service;

import com.bpms.core.exception.ServiceValidationException;
import com.bpms.sys.dao.ApplicationDao;
import com.bpms.sys.entity.ext.ApplicationExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 应用模块服务类
 */
@Service
public class ApplicationService extends SysBaseService<ApplicationExt> {
    /**
     * 缓存KEY前缀
     */
    public static final String CACHE_PREFIX_KEY = "APPLICATION_CACHE";

    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public ApplicationDao getDao() {
        return applicationDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ApplicationExt entity) {
        boolean isSysApp = StringUtils.equals(StringUtils.lowerCase(entity.getAppCode()), "sys");
        boolean isMainte = (entity.getMainteStartDate() != null || entity.getMainteEndDate() != null);

        if (isSysApp && isMainte) {
            throw new ServiceValidationException("系统管理模块不能设置维护期间。");
        }
    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ApplicationExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ApplicationExt saveBefore(ApplicationExt entity) {
        //应用编号重复性校验
        if (this.isDuplication("uid", entity.getUid(), "appCode", entity.getAppCode())) {
            throw new ServiceValidationException(String.format("指定的应用编号(%s)已存在，请重新输入。", entity.getAppCode()));
        }
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ApplicationExt saveAfter(ApplicationExt entity) {
        //删除缓存数据
        this.deleteCache(entity);

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
        String[] uids = ids.split(",");

        // 循环删除缓存
        for (String uid : uids) {
            this.deleteCache(this.findOne(uid));
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
    protected Boolean deleteBefore(ApplicationExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ApplicationExt entity) {
        //删除缓存数据
        this.deleteCache(entity);

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
        //删除缓存数据
        this.deleteCache(this.findOne(uid));

        return true;
    }

    /**
     * 根据应用编号查找应用实体对象
     *
     * @param appCode 应用编号
     * @return 实体对象
     */
    public ApplicationExt findByAppCode(String appCode) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, appCode);
        ApplicationExt value = redisCacheManager.get(ApplicationExt.class, cacheKey);

        if (value == null) {
            value = applicationDao.findByAppCode(appCode);
            redisCacheManager.set(cacheKey, value);
        }

        return value;
    }

    /**
     * 删除缓存数据
     *
     * @param entity 实体对象
     */
    private void deleteCache(ApplicationExt entity) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, entity.getAppCode());

        //删除redis数据
        redisCacheManager.delete(cacheKey);
    }
}