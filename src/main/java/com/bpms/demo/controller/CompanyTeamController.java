package com.bpms.demo.controller;

import com.bpms.demo.entity.ext.CompanyTeamExt;
import com.bpms.demo.service.CompanyTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 核心团队人员控制器类
 */
@Controller
@RequestMapping("/demo/companyteam")
public class CompanyTeamController extends CompanyExtendedBaseController<CompanyTeamExt> {
    /**
     * Service对象
     */
    @Autowired
    private CompanyTeamService companyTeamService;

    /**
     * 取得Service对象
     */
    @Override
    public CompanyTeamService getService() {
        return companyTeamService;
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
        //证件类型
        dicMap.put("certificateTypeCd", this.getDictionaryList("DEMO_009"));
        //学历
        dicMap.put("educationCd", this.getDictionaryList("DEMO_010"));
        //是否本公司股东
        dicMap.put("shareholdersCd", this.getDictionaryList("SYS010"));
        //是否实际负责人
        dicMap.put("actualPersonCd", this.getDictionaryList("SYS010"));
        //是否连续创业
        dicMap.put("continuousBusinessCd", this.getDictionaryList("SYS010"));
        //是否千人计划
        dicMap.put("thousandsPlansCd", this.getDictionaryList("SYS010"));
        //是否大学生科技企业
        dicMap.put("collegeTechnologyEnterprisesCd", this.getDictionaryList("SYS010"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
