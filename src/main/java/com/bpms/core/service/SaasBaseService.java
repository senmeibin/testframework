package com.bpms.core.service;

import com.google.common.collect.Maps;
import com.bpms.core.entity.BaseEntity;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.service.EnterpriseService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * SaasBaseService基类
 */
public abstract class SaasBaseService<T extends BaseEntity> extends BaseService<T> {
    @Autowired
    protected EnterpriseService enterpriseService;

    /**
     * 按别名分组转换查询条件
     *
     * @param condition 查询条件
     * @return 表别名未key的map集合
     */
    protected Map<String, Map<String, Object>> convertConditionToMapByKey(Map<String, Object> condition) {
        Map result = Maps.newHashMap();
        condition.keySet().stream().filter(key -> StringUtils.isNotEmpty(MapUtils.getString(condition, key))).forEach(key -> {
            String[] s = StringUtils.split(key, ".");
            Map map = MapUtils.getMap(result, s[0], Maps.newHashMap());
            map.put(key, condition.get(key));
            result.put(s[0], map);
        });
        return result;
    }

    /**
     * 检索条件自定义处理
     *
     * @param condition 检索条件
     */
    @Override
    protected void prepareCondition(Map<String, Object> condition) {
        //自动拼装企业UID查询条件
        if (SearchConditionUtils.getConditionValue(condition, "enterpriseUid") == null && SearchConditionUtils.getConditionValue(condition, "enterprise_uid") == null) {
            if (condition == null) {
                condition = new HashMap<>();
            }
            condition.put("main.enterprise_uid", this.getCurrentUser().getEnterpriseUid());
        }
    }

    /**
     * 取得登录用户所属企业的根部门信息
     *
     * @return 企业根部门信息实体
     */
    public DeptExt getEnterpriseRootDept() {
        return this.enterpriseService.getEnterpriseRootDept();
    }

    /**
     * 取得指定企业的根部门信息
     *
     * @param enterpriseUid 企业UID
     * @return 企业根部门信息实体
     */
    public DeptExt getEnterpriseRootDept(Object enterpriseUid) {
        return this.enterpriseService.getEnterpriseRootDept(enterpriseUid);
    }
}