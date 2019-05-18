package com.bpms.cmn.controller;

import com.bpms.cmn.entity.ext.CityExt;
import com.bpms.cmn.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 城市信息控制器类
 */
@Controller(value = "CmnCityController")
@RequestMapping("/cmn/city")
public class CityController extends CmnBaseController<CityExt> {
    /**
     * Service对象
     */
    @Autowired
    private CityService cityService;

    /**
     * 取得Service对象
     */
    @Override
    public CityService getService() {
        return cityService;
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
        //同步接口URL
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
        //所属大区
        dicMap.put("areaUid", this.getDropdownList("cmn_area", "uid", "area_name"));
        //上级城市
        dicMap.put("parentCityUid", this.getDropdownList("cmn_city", "uid", "city_name"));
        //关联分公司
        Map<String, Object> condition = new HashMap<>();
        //02:公司
        condition.put("dept_class", "2");
        condition.put("record_status", "1");
        dicMap.put("deptUid", this.getDropdownList("sys_dept", "uid", "dept_name", condition));

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}