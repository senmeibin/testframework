package com.bpms.sys.controller;

import com.bpms.core.controller.BaseController;
import com.bpms.sys.entity.ext.EnterpriseExt;
import com.bpms.sys.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业信息控制器类
 */
@Controller(value = "SysEnterpriseController")
@RequestMapping("/sys/enterprise")
public class EnterpriseController extends BaseController<EnterpriseExt> {
    /**
     * Service对象
     */
    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 取得Service对象
     */
    @Override
    public EnterpriseService getService() {
        return enterpriseService;
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

        //所属行业
        dicMap.put("industryCd", this.getDictionaryList("SYS013"));

        //企业性质
        dicMap.put("propertyCd", this.getDictionaryList("SYS014"));

        //企业规模
        dicMap.put("scaleCd", this.getDictionaryList("SYS015"));

        dicMap.put("uid", this.enterpriseService.getUserSelect());

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
