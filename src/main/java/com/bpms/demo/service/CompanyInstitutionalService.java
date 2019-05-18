package com.bpms.demo.service;

import com.bpms.core.utils.CollectionUtils;
import com.bpms.demo.dao.CompanyInstitutionalDao;
import com.bpms.demo.entity.ext.CompanyInstitutionalDetailExt;
import com.bpms.demo.entity.ext.CompanyInstitutionalExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构信息服务类
 */
@Service
public class CompanyInstitutionalService extends DemoBaseService<CompanyInstitutionalExt> {
    @Autowired
    private CompanyInstitutionalDao companyInstitutionalDao;

    @Autowired
    private CompanyInstitutionalDetailService companyInstitutionalDetailService;

    @Override
    public CompanyInstitutionalDao getDao() {
        return companyInstitutionalDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CompanyInstitutionalExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CompanyInstitutionalExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CompanyInstitutionalExt saveBefore(CompanyInstitutionalExt entity) {
        List<CompanyInstitutionalDetailExt> detailList = entity.getCompanyInstitutionalDetailList();

        //删除旧数据
        this.companyInstitutionalDetailService.getDao().delete("demo_company_institutional_detail", "institutional_uid", entity.getUid());

        //保存新数据
        if (CollectionUtils.isNotEmpty(detailList)) {
            for (CompanyInstitutionalDetailExt detailExt : detailList) {
                this.companyInstitutionalDetailService.getDao().save(detailExt);
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
    protected CompanyInstitutionalExt saveAfter(CompanyInstitutionalExt entity) {
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
    protected Boolean deleteBefore(CompanyInstitutionalExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CompanyInstitutionalExt entity) {
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