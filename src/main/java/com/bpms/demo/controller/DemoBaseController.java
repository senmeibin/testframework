package com.bpms.demo.controller;

import com.bpms.core.controller.BaseController;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.entity.BaseEntity;
import com.bpms.core.utils.UniqueUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * BPMS演示BaseController基类
 */
public abstract class DemoBaseController<T extends BaseEntity> extends BaseController<T> {

    /**
     * 获取新的UID
     *
     * @return 新的UID
     */
    @RequestMapping(value = "/loadNewUid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult loadNewUid() {
        return AjaxResult.createSuccessResult(UniqueUtils.getUID());
    }
}