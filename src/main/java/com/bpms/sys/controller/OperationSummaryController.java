package com.bpms.sys.controller;

import com.bpms.core.entity.AjaxResult;
import com.bpms.sys.entity.ext.OperationSummaryExt;
import com.bpms.sys.service.OperationSummaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 操作日志汇总控制器类
 */
@Controller
@RequestMapping("/sys/operationsummary")
public class OperationSummaryController extends SysBaseController<OperationSummaryExt> {
    /**
     * Service对象
     */
    @Autowired
    private OperationSummaryService operationSummaryService;

    /**
     * 取得Service对象
     */
    @Override
    public OperationSummaryService getService() {
        return operationSummaryService;
    }

    /**
     * 添加忽略路径
     *
     * @param url 路径
     * @return AjaxResult
     */
    @ResponseBody
    @RequestMapping(value = "addIgnoreUrl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult addIgnoreUrl(@RequestParam String url) {
        if (StringUtils.isEmpty(url)) {
            return AjaxResult.createFailResult("忽略路径不能为空。");
        }
        this.getService().addIgnoreUrl(url);
        return AjaxResult.createSuccessResult("忽略路径添加成功。");
    }

    /**
     * 删除忽略路径
     *
     * @param url 路径
     * @return AjaxResult
     */
    @ResponseBody
    @RequestMapping(value = "removeIgnoreUrl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult removeIgnoreUrl(@RequestParam String url) {
        if (StringUtils.isEmpty(url)) {
            return AjaxResult.createFailResult("忽略路径不能为空。");
        }
        this.getService().removeIgnoreUrl(url);
        return AjaxResult.createSuccessResult("忽略路径删除成功。");
    }

    @Override
    public Sort getDefaultSort() {
        //默认排序
        return new Sort(Sort.Direction.DESC, "count");
    }

    @Override
    public Sort getForceSortField() {
        return new Sort(Sort.Direction.DESC, "count");
    }
}
