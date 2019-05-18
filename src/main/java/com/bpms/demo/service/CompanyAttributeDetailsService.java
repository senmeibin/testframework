package com.bpms.demo.service;

import com.bpms.core.utils.StringUtils;
import com.bpms.demo.dao.CompanyAttributeDetailsDao;
import com.bpms.demo.entity.ext.CompanyAttributeDetailsExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 企业信息属性明细服务类
 */
@Service
public class CompanyAttributeDetailsService extends DemoBaseService<CompanyAttributeDetailsExt> {
    @Autowired
    private CompanyAttributeDetailsDao companyAttributeDetailsDao;

    @Override
    public CompanyAttributeDetailsDao getDao() {
        return companyAttributeDetailsDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CompanyAttributeDetailsExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CompanyAttributeDetailsExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CompanyAttributeDetailsExt saveBefore(CompanyAttributeDetailsExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CompanyAttributeDetailsExt saveAfter(CompanyAttributeDetailsExt entity) {
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
    protected Boolean deleteBefore(CompanyAttributeDetailsExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CompanyAttributeDetailsExt entity) {
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
     * 保存企业信息属性明细
     *
     * @param companyUid 企业UID
     * @param values     企业信息区分小类值
     * @param mainCd     企业信息区分大类
     */
    @Transactional
    public void saveCompanyAttributeDetails(String companyUid, String values, String mainCd) {
        //先按照公司UID清除数据
        this.getDao().delete("company_uid", companyUid);

        if (StringUtils.isNotEmpty(values)) {
            String[] list = values.split(",");
            for (String value : list) {
                CompanyAttributeDetailsExt entity = new CompanyAttributeDetailsExt();
                //企业UID
                entity.setCompanyUid(companyUid);
                //企业信息区分大类
                entity.setMainCd(mainCd);
                //企业信息区分小类
                entity.setSubCd(value);

                //保存
                this.getDao().save(entity);
            }
        }
    }
}