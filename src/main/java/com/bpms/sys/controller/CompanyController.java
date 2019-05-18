package com.bpms.sys.controller;

import com.bpms.core.service.BaseService;
import com.bpms.core.utils.UniqueUtils;
import com.bpms.sys.entity.ext.CompanyExt;
import com.bpms.sys.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 来往单位控制器类
 */
@Controller(value = "SysCompanyController")
@RequestMapping("/sys/company")
public class CompanyController extends SysBaseController<CompanyExt> {
    /**
     * Service对象
     */
    @Autowired
    private CompanyService companyService;

    /**
     * 联系人控制器
     */
    @Autowired
    private ContactController contactController;

    /**
     * 取得Service对象
     */
    @Override
    public BaseService<CompanyExt> getService() {
        return companyService;
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
     * 数据输入画面初期化
     *
     * @param model Model对象
     * @param uid   主键UID
     * @return 数据输入JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = "input", method = RequestMethod.GET)
    public String input(Model model, @RequestParam(required = false) String uid) throws IllegalAccessException, IOException, InstantiationException {
        String path = super.input(model, uid);

        //添加模式的场合
        if (this.isAddMode()) {
            // UID预设置
            this.getInputEntity().setUid(UniqueUtils.getUID());

            //设置数据模型JSON字符
            this.setInputEntityToModel(model);
        }

        //初期化从表数据模型JSON字符
        this.contactController.initInputPage(model);

        //设置外键关联字段信息
        this.contactController.initCompanyInfo(model, this.getInputEntity().getUid());

        return path;
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
        //性质
        dicMap.put("propertyCd", this.getDictionaryList("SYS100"));
        //规模
        dicMap.put("scaleCd", this.getDictionaryList("SYS101"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
