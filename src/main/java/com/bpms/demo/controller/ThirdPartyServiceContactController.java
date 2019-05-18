package com.bpms.demo.controller;

import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.ThirdPartyServiceContactExt;
import com.bpms.demo.service.ThirdPartyServiceContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方服务联系表控制器类
 */
@Controller
@RequestMapping("/demo/thirdpartyservicecontact")
public class ThirdPartyServiceContactController extends DemoBaseController<ThirdPartyServiceContactExt> {
    /**
     * Service对象
     */
    @Autowired
    private ThirdPartyServiceContactService thirdPartyServiceContactService;

    @Autowired
    private ThirdPartyServiceContactsController thirdPartyServiceContactsController;

    /**
     * 取得Service对象
     */
    @Override
    public ThirdPartyServiceContactService getService() {
        return thirdPartyServiceContactService;
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
        //评价状态
        dicMap.put("evaluateStatusCd", this.getDictionaryList("DEMO_024"));
        //基地信息
        dicMap.put("baseUid", this.getDropdownList("demo_base_info", "uid", "base_name"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));

        //新增时，明细列默认增加一列空行
        if (this.getInputEntity().isAddMode()) {
            thirdPartyServiceContactsController.setDefaultColumnData(model);
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
        ThirdPartyServiceContactExt entity = this.getInputEntity();
        if (this.isAddMode()) {
            //主键
            entity.setUid(UniqueUtils.getUID());
        }
        model.addAttribute("thirdPartyServiceContactUid", entity.getUid());
    }
}