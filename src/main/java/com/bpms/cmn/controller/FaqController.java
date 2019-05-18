package com.bpms.cmn.controller;

import com.bpms.cmn.entity.ext.FaqExt;
import com.bpms.cmn.service.FaqService;
import com.bpms.core.utils.UniqueUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
 * 常见问题控制器类
 */
@Controller
@RequestMapping("/cmn/faq")
public class FaqController extends CmnBaseController<FaqExt> {
    /**
     * Service对象
     */
    @Autowired
    private FaqService faqService;

    /**
     * 取得Service对象
     */
    @Override
    public FaqService getService() {
        return faqService;
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
        super.list(model);

        //注：各个模块动态加载，返回固定的JSP路径
        return "/cmn/faq/FaqList";
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
    @RequestMapping(value = "input", method = RequestMethod.GET, name = "新增/编辑")
    @RequiresRoles({"SystemManagement", "ItSupport"})
    public String input(Model model, @RequestParam(required = false) String uid) throws IllegalAccessException, IOException, InstantiationException {
        super.input(model, uid);

        //使用UEditor富文本编辑
        this.setUseUEditor(true);

        //注：各个模块动态加载，返回固定的JSP路径
        return "/cmn/faq/FaqInput";
    }

    /**
     * 初始化InputEntity实体对象[后处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    @Override
    protected void initInputEntityAfter(Model model, String uid) {
        if (this.getInputEntity().isAddMode()) {
            this.getInputEntity().setUid(UniqueUtils.getUID());
            model.addAttribute("uid", this.getInputEntity().getUid());
        }
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
        //应用名称
        dicMap.put("appCode", this.getDropdownList("sys_application", "app_code", "app_name"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
