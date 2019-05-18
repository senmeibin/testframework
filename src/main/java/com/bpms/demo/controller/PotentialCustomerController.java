package com.bpms.demo.controller;

import com.bpms.core.entity.AjaxResult;
import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.PotentialCustomerExt;
import com.bpms.demo.service.PotentialCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 招商潜在投资企业控制器类
 */
@Controller
@RequestMapping("/demo/potentialcustomer")
public class PotentialCustomerController extends DemoBaseController<PotentialCustomerExt> {
    /**
     * Service对象
     */
    @Autowired
    private PotentialCustomerService potentialCustomerService;

    /**
     * 取得Service对象
     */
    @Override
    public PotentialCustomerService getService() {
        return potentialCustomerService;
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
        //企业类型
        dicMap.put("companyTypeCd", this.getDictionaryList("DEMO_016"));
        //入驻概率
        dicMap.put("entryProbabilityCd", this.getDictionaryList("DEMO_017"));
        //招商渠道
        dicMap.put("investmentChannelsCd", this.getDictionaryList("DEMO_035"));
        //基地信息
        dicMap.put("baseUid", this.getDropdownList("demo_base_info", "uid", "base_name"));
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
        model.addAttribute("potentialCustomerUid", this.getInputEntity().getUid());
    }

    /**
     * 转入孵化企业信息中
     *
     * @param uid 工资发放UID
     * @return
     */
    @RequestMapping(value = "turnCompany", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult turnCompany(@RequestParam String uid) {
        return this.potentialCustomerService.turnCompany(uid);
    }
}
