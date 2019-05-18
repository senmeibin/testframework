package com.bpms.sys.controller;

import com.bpms.cmn.utility.ApplicationPropertiesUtils;
import com.bpms.core.entity.AjaxResult;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import com.bpms.sys.service.ExecuteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
 * 执行记录表控制器类
 */
@Controller
@RequestMapping("/sys/executerecord")
public class ExecuteRecordController extends SysBaseController<ExecuteRecordExt> {
    /**
     * Service对象
     */
    @Autowired
    private ExecuteRecordService executeRecordService;

    /**
     * 取得Service对象
     */
    @Override
    public ExecuteRecordService getService() {
        return executeRecordService;
    }

    /**
     * 取得Entity类
     */
    @Override
    protected Class<ExecuteRecordExt> getEntityClass() {
        return ExecuteRecordExt.class;
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
        boolean isTaskServer = Boolean.parseBoolean(ApplicationPropertiesUtils.getValue("task.server.enable", "false"));
        if (isTaskServer) {
            model.addAttribute("taskServerRemark", "全体任务处于可执行状态，可手动执行任务。");
            model.addAttribute("isTaskServer", "on");
        }
        else {
            model.addAttribute("taskServerRemark", "全体任务处于关闭状态，手动执行任务无效。");
            model.addAttribute("isTaskServer", "off");
        }
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
        //功能类型
        dicMap.put("functionTypeCd", this.getDictionaryList("SYS011"));
        //任务状态
        dicMap.put("functionStatusCd", this.getDictionaryList("SYS012"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 取得默认排序字段[子类可覆盖]
     *
     * @return 默认排序字段
     */
    @Override
    public Sort getDefaultSort() {
        // 默认按UID排序
        return new Sort(Sort.Direction.DESC, "lastExecuteStartDate");
    }

    /**
     * 任务状态变更
     *
     * @param uid              任务uid
     * @param functionStatusCd 任务状态(01：待执行、02：执行中、09：停止)
     * @return
     */
    @RequestMapping(value = "updateTaskStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult updateTaskStatus(@RequestParam String uid, @RequestParam String functionStatusCd) {
        executeRecordService.updateTaskStatus(uid, functionStatusCd);
        // 返回数据输入JSP页面
        return AjaxResult.createSuccessResult();
    }

    /**
     * 手动执行任务
     *
     * @param uid 任务uid
     * @return AjaxResult
     */
    @RequestMapping(value = "manuallyExecuteTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult manuallyExecuteTask(@RequestParam String uid) {
        return executeRecordService.manuallyExecuteTask(uid);
    }
}
