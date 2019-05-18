package com.bpms.cmn.service;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.BaseEntity;
import com.bpms.core.service.BaseService;
import com.bpms.core.utils.HttpUtils;

/**
 * 共通管理BaseService基类
 */
public abstract class CmnBaseService<T extends BaseEntity> extends BaseService<T> {
    /**
     * 获取指定表中的数据
     *
     * @param tableName 逻辑表名称
     * @return JSON字符串
     */
    @Override
    protected String getTableList(String tableName) {
        //接口URL
        String url = this.parameterService.getValue(AppCodeConsts.APP_COMMON, CmnConsts.DATA_IMPORT_CMN_API);

        return HttpUtils.doGet(url + "getTableList?tableName=" + tableName);
    }
}