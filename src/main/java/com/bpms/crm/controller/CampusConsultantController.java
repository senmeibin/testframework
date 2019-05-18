package com.bpms.crm.controller;

import com.bpms.crm.entity.ext.CampusConsultantExt;
import com.bpms.crm.service.CampusConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 校区顾问控制器类
 */
@Controller
@RequestMapping("/crm/campusconsultant")
public class CampusConsultantController extends CrmBaseController<CampusConsultantExt> {
    /**
     * Service对象
     */
    @Autowired
    private CampusConsultantService campusConsultantService;

    /**
     * 取得Service对象
     */
    @Override
    public CampusConsultantService getService() {
        return campusConsultantService;
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
