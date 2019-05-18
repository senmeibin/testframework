package com.bpms.cmn.controller;

import com.bpms.cmn.service.ProgressService;
import com.bpms.core.entity.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 进度条控制器类
 */
@Controller
@RequestMapping("/cmn/progress")
public class ProgressController {
    /**
     * Service对象
     */
    @Autowired
    private ProgressService progressService;

    /**
     * 获取进度详情
     *
     * @param progressId 进度ID
     * @return AjaxResult
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult getProcessInfoDetail(@RequestParam String progressId) {
        //直接返回进度信息
        return AjaxResult.createSuccessResult("获取进度信息成功。", progressService.show(progressId));
    }

    /**
     * 移除进度
     *
     * @param progressId 进度ID
     * @return AjaxResult
     */
    @RequestMapping(value = "/removeProgress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult removeProgress(@RequestParam String progressId) {
        //移除进度
        this.progressService.removeProgress(progressId);
        return AjaxResult.createSuccessResult("进度已移除。");
    }
}
