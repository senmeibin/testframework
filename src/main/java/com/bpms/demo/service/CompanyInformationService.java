package com.bpms.demo.service;

import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.demo.dao.CompanyInformationDao;
import com.bpms.demo.entity.ext.CompanyInformationExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 企业基本信息服务类
 */
@Service
public class CompanyInformationService extends DemoBaseService<CompanyInformationExt> {
    @Autowired
    private CompanyInformationDao companyInformationDao;

    /**
     * 企业信息属性明细服务类
     */
    @Autowired
    private CompanyAttributeDetailsService companyAttributeDetailsService;

    @Override
    public CompanyInformationDao getDao() {
        return companyInformationDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CompanyInformationExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CompanyInformationExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CompanyInformationExt saveBefore(CompanyInformationExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CompanyInformationExt saveAfter(CompanyInformationExt entity) {
        //01:创业企业特征
        companyAttributeDetailsService.saveCompanyAttributeDetails(entity.getUid(), entity.getEnterpriseCharacteristics(), "01");
        //02:市场分类
        companyAttributeDetailsService.saveCompanyAttributeDetails(entity.getUid(), entity.getMarketClassification(), "02");
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
    protected Boolean deleteBefore(CompanyInformationExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CompanyInformationExt entity) {
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
     * SQL条件自定义拼接处理
     *
     * @param sql       SQL文
     * @param condition 检索条件
     * @return SQL文
     */
    protected String prepareSQL(String sql, Map<String, Object> condition) {
        StringBuilder sb = new StringBuilder();
        //过滤企业收费信息中的公司数据
        String filterChargesInformationCompany = (String) SearchConditionUtils.getConditionValue(condition, "filterChargesInformationCompany");
        if (StringUtils.equals("TRUE", filterChargesInformationCompany)) {
            sb.append("AND NOT EXISTS (SELECT 1 FROM demo_charges_information a  WHERE  a.record_status = 1 AND a.company_uid = main.uid)");
        }
        sql = sql.replace("${dynamicCondition}", sb.toString());
        return sql;
    }
}