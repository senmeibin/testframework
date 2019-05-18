package com.bpms.demo.service;

import com.bpms.demo.dao.ThirdPartyServiceContactsDao;
import com.bpms.demo.entity.ext.ThirdPartyServiceContactsExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三方服务联系人服务类
 */
@Service
public class ThirdPartyServiceContactsService extends DemoBaseService<ThirdPartyServiceContactsExt> {
    @Autowired
    private ThirdPartyServiceContactsDao thirdPartyServiceContactsDao;

    @Override
    public ThirdPartyServiceContactsDao getDao() {
        return thirdPartyServiceContactsDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ThirdPartyServiceContactsExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ThirdPartyServiceContactsExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ThirdPartyServiceContactsExt saveBefore(ThirdPartyServiceContactsExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ThirdPartyServiceContactsExt saveAfter(ThirdPartyServiceContactsExt entity) {
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
    protected Boolean deleteBefore(ThirdPartyServiceContactsExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ThirdPartyServiceContactsExt entity) {
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

    /**
     * 查询第三方服务联系人数据
     *
     * @param thirdPartyServiceUid 第三方服务UID
     * @return 查询第三方服务联系人列表
     */
    public List<ThirdPartyServiceContactsExt> getContactsTree(String thirdPartyServiceUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.thirdPartyServiceUid", thirdPartyServiceUid);
        return this.search(condition);
    }
}