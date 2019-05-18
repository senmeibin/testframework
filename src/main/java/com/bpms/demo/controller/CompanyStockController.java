package com.bpms.demo.controller;

import com.bpms.demo.entity.ext.CompanyStockExt;
import com.bpms.demo.service.CompanyStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 股权信息控制器类
 */
@Controller
@RequestMapping("/demo/companystock")
public class CompanyStockController extends CompanyExtendedBaseController<CompanyStockExt> {
    /**
     * Service对象
     */
    @Autowired
    private CompanyStockService companyStockService;

    /**
     * 取得Service对象
     */
    @Override
    public CompanyStockService getService() {
        return companyStockService;
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
        //股东类型
        dicMap.put("shareholderTypeCd", this.getDictionaryList("DEMO_008"));
        //证件类型
        dicMap.put("certificateTypeCd", this.getDictionaryList("DEMO_009"));
        //是否上市公司
        dicMap.put("listedCompanyCd", this.getDictionaryList("SYS010"));
        //是否入选千人计划
        dicMap.put("thousandsPeoplePlanCd", this.getDictionaryList("SYS010"));
        //是否境外公司或外籍
        dicMap.put("overseasCompanyCd", this.getDictionaryList("SYS010"));
        //是否风险投资（基金）公司
        dicMap.put("fundCompanyCd", this.getDictionaryList("SYS010"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
