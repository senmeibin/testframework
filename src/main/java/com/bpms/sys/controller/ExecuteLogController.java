package com.bpms.sys.controller;

import com.bpms.sys.entity.ext.ExecuteLogExt;
import com.bpms.sys.service.ExecuteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 执行日志表控制器类
 */
@Controller
@RequestMapping("/sys/executelog")
public class ExecuteLogController extends SysBaseController<ExecuteLogExt> {
    /**
     * Service对象
     */
    @Autowired
    private ExecuteLogService executeLogService;

    /**
     * 取得Service对象
     */
    @Override
    public ExecuteLogService getService() {
        return executeLogService;
    }

    /**
     * 取得Entity类
     */
    @Override
    protected Class<ExecuteLogExt> getEntityClass() {
        return ExecuteLogExt.class;
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
        //功能类型
        dicMap.put("functionTypeCd", this.getDictionaryList("SYS011"));
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
        return new Sort(Sort.Direction.DESC, "insertDate");
    }
}
