package com.bpms.sys.service;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.sys.dao.EnterpriseSettingDao;
import com.bpms.sys.entity.ext.EnterpriseExt;
import com.bpms.sys.entity.ext.EnterpriseSettingExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业设置服务类
 */
@Service
public class EnterpriseSettingService extends SysBaseService<EnterpriseSettingExt> {
    /**
     * 缓存KEY前缀
     */
    public static final String CACHE_PREFIX_KEY = "ENTERPRISE_SETTING_CACHE";

    @Autowired
    private EnterpriseSettingDao enterpriseSettingDao;

    @Override
    public EnterpriseSettingDao getDao() {
        return enterpriseSettingDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(EnterpriseSettingExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(EnterpriseSettingExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected EnterpriseSettingExt saveBefore(EnterpriseSettingExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected EnterpriseSettingExt saveAfter(EnterpriseSettingExt entity) {
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
    protected Boolean deleteBefore(EnterpriseSettingExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(EnterpriseSettingExt entity) {
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
     * 删除缓存数据
     *
     * @param entity 参数实体对象
     */
    private void deleteCache(EnterpriseSettingExt entity) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, entity.getUid());

        //删除redis数据
        redisCacheManager.delete(cacheKey);
    }

    /**
     * 取得企业设置详细信息
     *
     * @return 实体详细
     */
    public EnterpriseSettingExt getDetail() {
        return this.getSetting(this.getCurrentUser().getEnterpriseUid(), true);
    }

    /**
     * 取得企业设置详细信息
     *
     * @param uid 企业UID
     * @return 实体详细
     */
    @Override
    public EnterpriseSettingExt getDetail(Object uid) {
        if (uid == null) {
            return new EnterpriseSettingExt();
        }

        if (uid instanceof String) {
            return this.getSetting(Integer.parseInt((String) uid), true);
        }
        else if (uid instanceof Integer) {
            return this.getSetting((Integer) uid, true);
        }
        return new EnterpriseSettingExt();
    }

    /**
     * 取得企业设置详细信息
     *
     * @param uid      企业UID
     * @param useCache 是否使用缓存
     * @return 实体详细
     */
    public EnterpriseSettingExt getDetail(Integer uid, boolean useCache) {
        return this.getSetting(uid, useCache);
    }

    /**
     * 从Redis取得企业设置实体信息
     *
     * @param uid      企业UID
     * @param useCache 是否使用缓存
     * @return 实体详细
     */
    private EnterpriseSettingExt getSetting(Integer uid, boolean useCache) {
        if (uid == null || uid == 0) {
            return new EnterpriseSettingExt();
        }

        if (useCache) {
            String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, uid);
            EnterpriseSettingExt entity = redisCacheManager.get(EnterpriseSettingExt.class, cacheKey);

            //Redis服务中不存在的场合，从DB取得后存入Redis服务器
            if (entity == null) {
                //根据UID从DB中取得简历详细
                entity = super.getDetail(uid);

                if (entity != null) {
                    redisCacheManager.set(cacheKey, entity);
                }
                else {
                    entity = new EnterpriseSettingExt();
                }
            }
            return entity;
        }
        else {
            return super.getDetail(uid);
        }
    }

    /**
     * 保存默认的企业设置
     *
     * @param enterprise 企业
     * @return 企业设置信息
     */
    public EnterpriseSettingExt saveDefaultEnterpriseSetting(EnterpriseExt enterprise) {
        EnterpriseSettingExt enterpriseSetting = new EnterpriseSettingExt();
        enterpriseSetting.setUid(String.valueOf(enterprise.getUid()));
        //设置企业子账号个数 默认10个
        enterpriseSetting.setSetting001(this.parameterService.getIntValue(AppCodeConsts.APP_SYS, "ENTERPRISE_ACCOUNT_NUM", 10));
        //设置企业是否允许多公司招聘 默认0
        enterpriseSetting.setSetting002(this.parameterService.getIntValue(AppCodeConsts.APP_SYS, "ALLOW_MULTIPLE_COMPANY", 0));
        //设置企业微信账号个数 默认1个
        enterpriseSetting.setSetting003(this.parameterService.getIntValue(AppCodeConsts.APP_SYS, "ENTERPRISE_WEIXIN_ACCOUNT_NUM", 1));
        return this.getDao().save(enterpriseSetting);
    }
}