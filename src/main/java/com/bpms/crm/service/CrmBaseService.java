package com.bpms.crm.service;

import com.bpms.core.entity.BaseEntity;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.service.BaseService;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.StringUtils;

import java.util.List;

/**
 * 系统管理BaseService基类
 */
public abstract class CrmBaseService<T extends BaseEntity> extends BaseService<T> {
    /**
     * 根据子分类名称返回对应的子分类CD
     *
     * @param dropdownEntityList 大分类对应的数据字典List
     * @param subName            子分类名称
     * @return 子分类名称对应的子分类CD
     */
    protected String getDictionarySubCdByName(List<DropdownEntity> dropdownEntityList, String subName) {
        return this.getDictionarySubCdByName(dropdownEntityList, subName, StringUtils.EMPTY);
    }

    /**
     * 根据子分类名称返回对应的子分类CD
     *
     * @param dropdownEntityList 大分类对应的数据字典List
     * @param subName            子分类名称
     * @param notFoundDefault    没有匹配项的默认字符串
     * @return 子分类名称对应的子分类CD
     */
    protected String getDictionarySubCdByName(List<DropdownEntity> dropdownEntityList, String subName, String notFoundDefault) {
        if (CollectionUtils.isEmpty(dropdownEntityList) || StringUtils.isEmpty(subName)) {
            return notFoundDefault;
        }
        for (DropdownEntity dropdownEntity : dropdownEntityList) {
            if (StringUtils.equals(subName, dropdownEntity.getSubName())) {
                return dropdownEntity.getSubCd();
            }
        }
        //返回默认字符串
        return notFoundDefault;
    }
}