package com.bpms.sys.controller;

import com.bpms.core.entity.AjaxResult;
import com.bpms.sys.entity.ext.UselessDataTableExt;
import com.bpms.sys.service.TableSpaceOptimizeLogService;
import com.bpms.sys.service.UselessDataTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 无用数据表控制器类
 */
@Controller
@RequestMapping("/sys/uselessdatatable")
public class UselessDataTableController extends SysBaseController<UselessDataTableExt> {
    /**
     * Service对象
     */
    @Autowired
    private UselessDataTableService uselessDataTableService;
    @Autowired
    private TableSpaceOptimizeLogService tableSpaceOptimizeLogService;

    /**
     * 取得Service对象
     */
    @Override
    public UselessDataTableService getService() {
        return uselessDataTableService;
    }

    /**
     * 数据一览画面初期化
     *
     * @param model Model对象
     * @return 数据一览JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException {
        return super.list(model);
    }

    /**
     * 初始化下拉框选项内容
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    @Override
    public void initOptionList(Model model) throws IllegalAccessException, IOException {
        Map<String, Object> dicMap = new HashMap<>();

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    @Override
    public AjaxResult delete(@RequestParam String tableName) {
        int result = this.uselessDataTableService.uselessDataDelete(tableName);
        return AjaxResult.createSuccessResult(String.format("成功清除%s条记录。", result));
    }

    /**
     * 表空间优化处理
     *
     * @param tableName 表名称
     * @return AjaxResult
     */
    @ResponseBody
    @RequestMapping(value = "optimizeData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "优化")
    public AjaxResult optimizeData(@RequestParam String tableName) {
        this.tableSpaceOptimizeLogService.optimizeTable(tableName);
        return AjaxResult.createSuccessResult(String.format("表空间优化处理成功。"));
    }
}
