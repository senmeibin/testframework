package com.bpms.crm.controller;

import com.bpms.core.entity.AjaxResult;
import com.bpms.core.service.BaseService;
import com.bpms.crm.bean.SchedulerInfo;
import com.bpms.crm.entity.ext.StudentExt;
import com.bpms.crm.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 我的日程控制器类
 */
@Controller
@RequestMapping("/crm/scheduler")
public class SchedulerController extends CrmBaseController<StudentExt> {
    /**
     * Service对象
     */
    @Autowired
    private SchedulerService schedulerService;

    @Override
    public BaseService<StudentExt> getService() {
        return this.schedulerService;
    }

    /**
     * 检索我的日程数据
     *
     * @param dayOffset 与当前日期偏移量 默认0 今天开始
     * @param dayScope  查询的日期范围 默认5天
     * @return 日程数据
     */
    @RequestMapping(value = "/searchScheduler", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult searchScheduler(@RequestParam(defaultValue = "0") int dayOffset,
                                      @RequestParam(defaultValue = "5") int dayScope) {
        Map<String, List<SchedulerInfo>> result = this.schedulerService.searchScheduler(getCurrentUserId(), dayOffset, dayScope);
        return AjaxResult.createSuccessResult("检索我的日程数据", result);
    }
}
