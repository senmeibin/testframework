package com.bpms.demo.controller;

import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.CompanyInformationExt;
import com.bpms.demo.service.CompanyInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业基本信息控制器类
 */
@Controller
@RequestMapping("/demo/companyinformation")
public class CompanyInformationController extends DemoBaseController<CompanyInformationExt> {
    /**
     * Service对象
     */
    @Autowired
    private CompanyInformationService companyInformationService;

    @Autowired
    private CompanyAdditionalController companyAdditionalController;

    @Autowired
    private CompanyTeamController companyTeamController;

    @Autowired
    private CompanyStockController companyStockController;

    @Autowired
    private CompanyInvestmentController companyInvestmentController;

    @Autowired
    private CompanyIntellectualController companyIntellectualController;

    /**
     * 取得Service对象
     */
    @Override
    public CompanyInformationService getService() {
        return companyInformationService;
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
        //登记注册类型
        dicMap.put("registrationTypeCd", this.getDictionaryList("DEMO_001"));
        //专业技术方向
        dicMap.put("professionalTechnicalCd", this.getDictionaryList("DEMO_002"));
        //孵化状态
        dicMap.put("incubationStateCd", this.getDictionaryList("DEMO_003"));
        //创业企业特征
        dicMap.put("enterpriseCharacteristics", this.getDictionaryList("DEMO_005"));
        //市场分类
        dicMap.put("marketClassification", this.getDictionaryList("DEMO_006"));
        //基地信息
        dicMap.put("baseUid", this.getDropdownList("demo_base_info", "uid", "base_name"));

        //附加信息 下拉
        companyAdditionalController.initOptionList(model);
        //股权信息 下拉
        companyStockController.initOptionList(model);
        //核心团队成员 下拉
        companyTeamController.initOptionList(model);
        //投融资 下拉
        companyInvestmentController.initOptionList(model);
        //知识产权 下拉
        companyIntellectualController.initOptionList(model);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 初始化InputEntity实体对象[后处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    @Override
    protected void initInputEntityAfter(Model model, String uid) {
        if (this.isAddMode()) {
            //主键
            this.getInputEntity().setUid(UniqueUtils.getUID());
        }
        model.addAttribute("companyInformationUid", this.getInputEntity().getUid());
    }
}