package com.bpms.pms.service;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.CoreEntity;
import com.bpms.core.service.BaseService;
import com.bpms.core.utils.HttpUtils;

/**
 * 系统管理BaseService基类
 */
public abstract class PmsBaseService<T extends CoreEntity> extends BaseService<T> {
    /**
     * 获取指定表中的数据
     *
     * @param tableName 逻辑表名称
     * @return JSON字符串
     */
    @Override
    protected String getTableList(String tableName) {
        //接口URL
        String url = this.parameterService.getValue(AppCodeConsts.APP_COMMON, CmnConsts.DATA_IMPORT_SYS_API);

        return HttpUtils.doGet(url + "getTableList?tableName=" + tableName);
    }
}