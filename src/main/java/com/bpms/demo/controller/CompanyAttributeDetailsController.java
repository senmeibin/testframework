package com.bpms.demo.controller;

import com.bpms.demo.entity.ext.CompanyAttributeDetailsExt;
import com.bpms.demo.service.CompanyAttributeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业信息属性明细控制器类
 */
@Controller
@RequestMapping("/demo/companyattributedetails")
public class CompanyAttributeDetailsController extends DemoBaseController<CompanyAttributeDetailsExt> {
    /**
     * Service对象
     */
    @Autowired
    private CompanyAttributeDetailsService companyAttributeDetailsService;

    /**
     * 取得Service对象
     */
    @Override
    public CompanyAttributeDetailsService getService() {
        return companyAttributeDetailsService;
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
        //企业信息区分大类
        dicMap.put("mainCd", this.getDictionaryList("DEMO_005"));
        //企业信息区分小类
        dicMap.put("subCd", this.getDictionaryList("DEMO_006"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
