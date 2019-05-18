package com.bpms.demo.controller;

import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.ChargesInformationExt;
import com.bpms.demo.service.ChargesInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业收费信息控制器类
 */
@Controller
@RequestMapping("/demo/chargesinformation")
public class ChargesInformationController extends DemoBaseController<ChargesInformationExt> {
    /**
     * Service对象
     */
    @Autowired
    private ChargesInformationService chargesInformationService;

    /**
     * 企业收费明细控制器类
     */
    @Autowired
    private ChargesInformationDetailController chargesInformationDetailController;

    /**
     * 取得Service对象
     */
    @Override
    public ChargesInformationService getService() {
        return chargesInformationService;
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
        //收费方式
        dicMap.put("chargesTypeCd", this.getDictionaryList("DEMO_013"));
        //单位
        dicMap.put("unitCd", this.getDictionaryList("DEMO_025"));
        //收租方式
        dicMap.put("rentWayCd", this.getDictionaryList("DEMO_026"));
        //押金方式
        dicMap.put("depositMethodCd", this.getDictionaryList("DEMO_027"));

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
        //企业收费明细 下拉数据
        chargesInformationDetailController.initOptionList(model);

        //新增时，明细列默认增加一列空行
        if (this.getInputEntity().isAddMode()) {
            chargesInformationDetailController.setDefaultColumnData(model);
        }
    }

    /**
     * 初始化InputEntity实体对象[后处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    @Override
    protected void initInputEntityAfter(Model model, String uid) {
        ChargesInformationExt entity = this.getInputEntity();
        if (entity.isAddMode()) {
            //主键
            entity.setUid(UniqueUtils.getUID());
            //公司UID
            entity.setCompanyUid(this.getRequest().getParameter("companyUid"));
        }
        model.addAttribute("companyUid", this.getRequest().getParameter("companyUid"));
        model.addAttribute("chargesInformationUid", entity.getUid());
    }
}