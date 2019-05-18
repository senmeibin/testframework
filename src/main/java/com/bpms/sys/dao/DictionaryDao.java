package com.bpms.sys.dao;

import com.bpms.core.dao.BaseDao;
import com.bpms.sys.entity.ext.DictionaryExt;


/**
 * 数据字典数据访问类
 */
public interface DictionaryDao extends BaseDao<DictionaryExt, String> {
    /**
     * 根据大区分cd和小区分cd查找数据字典
     *
     * @param mainCd 大区分CD
     * @param subCd  小区分CD
     * @return 数据字典实体对象
     */
    DictionaryExt findByMainCdAndSubCd(String mainCd, String subCd);
}