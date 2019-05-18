package com.bpms.demo.controller;

import com.bpms.demo.entity.ext.CompanyIntellectualExt;
import com.bpms.demo.service.CompanyIntellectualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 知识产权控制器类
 */
@Controller
@RequestMapping("/demo/companyintellectual")
public class CompanyIntellectualController extends CompanyExtendedBaseController<CompanyIntellectualExt> {
    /**
     * Service对象
     */
    @Autowired
    private CompanyIntellectualService companyIntellectualService;

    /**
     * 取得Service对象
     */
    @Override
    public CompanyIntellectualService getService() {
        return companyIntellectualService;
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
        //知识产权类型
        dicMap.put("intellectualPropertyCd", this.getDictionaryList("DEMO_011"));
        //状态
        dicMap.put("stateCd", this.getDictionaryList("DEMO_012"));
        //是否有效
        dicMap.put("effectiveCd", this.getDictionaryList("SYS010"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
