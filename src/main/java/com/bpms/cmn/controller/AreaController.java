package com.bpms.cmn.controller;

import com.bpms.cmn.entity.ext.AreaExt;
import com.bpms.cmn.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 大区信息控制器类
 */
@Controller(value = "CmnAreaController")
@RequestMapping("/cmn/area")
public class AreaController extends CmnBaseController<AreaExt> {
    /**
     * Service对象
     */
    @Autowired
    private AreaService areaService;

    /**
     * 取得Service对象
     */
    @Override
    public AreaService getService() {
        return areaService;
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
        //API同步接口URL
        this.initDataImportFlag(model);
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
        //大区映射关联
        Map<String, Object> condition = new HashMap<>();
        //01:大区
        condition.put("dept_class", "1");
        condition.put("record_status", "1");
        dicMap.put("deptUid", this.getDropdownList("sys_dept", "uid", "dept_name", condition));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
