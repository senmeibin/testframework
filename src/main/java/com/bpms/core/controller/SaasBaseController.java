package com.bpms.core.controller;

import com.bpms.core.entity.CoreEntity;

import java.util.Map;

/***
 * Saas平台Controller基类
 */
@SuppressWarnings("unchecked")
public abstract class SaasBaseController<T extends CoreEntity> extends BaseController<T> {
    /**
     * 客户化自定义检索条件
     *
     * @param condition 检索条件
     */
    @Override
    protected void customSearchCondition(Map<String, Object> condition) {
        this.initDataRangeCondition(condition);
    }

    /**
     * 数据范围检索条件初期化
     *
     * @param condition 检索条件
     */
    protected void initDataRangeCondition(Map<String, Object> condition) {
        condition.put("main.enterpriseUid", this.getCurrentUser().getEnterpriseUid());
    }
}