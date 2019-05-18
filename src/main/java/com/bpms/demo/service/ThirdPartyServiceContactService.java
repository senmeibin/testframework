package com.bpms.demo.service;

import com.bpms.core.utils.CollectionUtils;
import com.bpms.demo.dao.ThirdPartyServiceContactDao;
import com.bpms.demo.entity.ext.ThirdPartyServiceContactExt;
import com.bpms.demo.entity.ext.ThirdPartyServiceContactsExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 第三方服务联系表服务类
 */
@Service
public class ThirdPartyServiceContactService extends DemoBaseService<ThirdPartyServiceContactExt> {
    @Autowired
    private ThirdPartyServiceContactDao thirdPartyServiceContactDao;

    @Autowired
    private ThirdPartyServiceContactsService thirdPartyServiceContactsService;

    @Override
    public ThirdPartyServiceContactDao getDao() {
        return thirdPartyServiceContactDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ThirdPartyServiceContactExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ThirdPartyServiceContactExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ThirdPartyServiceContactExt saveBefore(ThirdPartyServiceContactExt entity) {
        List<ThirdPartyServiceContactsExt> thirdPartyServiceContactsList = entity.getThirdPartyServiceContactsList();

        //删除旧数据
        this.thirdPartyServiceContactsService.getDao().delete("third_party_service_uid", entity.getUid());

        //保存联系人数据
        if (CollectionUtils.isNotEmpty(thirdPartyServiceContactsList)) {
            for (ThirdPartyServiceContactsExt contactsExt : thirdPartyServiceContactsList) {
                this.thirdPartyServiceContactsService.getDao().save(contactsExt);
            }
        }

        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ThirdPartyServiceContactExt saveAfter(ThirdPartyServiceContactExt entity) {
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
    protected Boolean deleteBefore(ThirdPartyServiceContactExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ThirdPartyServiceContactExt entity) {
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