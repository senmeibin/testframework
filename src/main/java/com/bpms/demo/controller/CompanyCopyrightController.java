package com.bpms.demo.controller;

import com.bpms.demo.entity.ext.CompanyCopyrightExt;
import com.bpms.demo.service.CompanyCopyrightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 专利版权控制器类
 */
@Controller
@RequestMapping("/demo/companycopyright")
public class CompanyCopyrightController extends CompanyExtendedBaseController<CompanyCopyrightExt> {
    /**
     * Service对象
     */
    @Autowired
    private CompanyCopyrightService companyCopyrightService;

    /**
     * 取得Service对象
     */
    @Override
    public CompanyCopyrightService getService() {
        return companyCopyrightService;
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
}
