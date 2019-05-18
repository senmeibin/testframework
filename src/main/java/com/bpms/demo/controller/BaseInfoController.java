package com.bpms.demo.controller;

import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.BaseInfoExt;
import com.bpms.demo.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 基地信息控制器类
 */
@Controller
@RequestMapping("/demo/baseinfo")
public class BaseInfoController extends DemoBaseController<BaseInfoExt> {
    /**
     * Service对象
     */
    @Autowired
    private BaseInfoService baseInfoService;

    /**
     * 取得Service对象
     */
    @Override
    public BaseInfoService getService() {
        return baseInfoService;
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
            this.getInputEntity().setUid(UniqueUtils.getUID());
        }
    }
}