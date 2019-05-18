package com.bpms.demo.controller;

import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.ResourcesDockingRecordExt;
import com.bpms.demo.service.ResourcesDockingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 入孵企业资源对接记录控制器类
 */
@Controller
@RequestMapping("/demo/resourcesdockingrecord")
public class ResourcesDockingRecordController extends DemoBaseController<ResourcesDockingRecordExt> {
    /**
     * Service对象
     */
    @Autowired
    private ResourcesDockingRecordService resourcesDockingRecordService;

    /**
     * 取得Service对象
     */
    @Override
    public ResourcesDockingRecordService getService() {
        return resourcesDockingRecordService;
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
        //对接机构-第三方
        dicMap.put("thirdPartyServiceUid", this.getDropdownList("demo_third_party_service_contact", "uid", "company_name"));
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
        ResourcesDockingRecordExt entity = this.getInputEntity();
        if (entity.isAddMode()) {
            //主键
            entity.setUid(UniqueUtils.getUID());
            //公司UID
            entity.setCompanyUid(this.getRequest().getParameter("companyUid"));
        }
        model.addAttribute("companyUid", this.getRequest().getParameter("companyUid"));
        model.addAttribute("resourcesDockingRecordUid", entity.getUid());
    }
}