package com.bpms.cmn.controller;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.controller.BaseController;
import com.bpms.core.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;

/**
 * 共通管理BaseController基类
 */
public abstract class CmnBaseController<T extends BaseEntity> extends BaseController<T> {
    /**
     * 初始化数据导入标志位
     *
     * @param model Model
     */
    public void initDataImportFlag(Model model) {
        //API同步接口URL
        String url = parameterService.getValue(AppCodeConsts.APP_COMMON, CmnConsts.DATA_IMPORT_CMN_API);
        model.addAttribute("dataImportApiExist", !StringUtils.isEmpty(url));
    }
}
