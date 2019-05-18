package com.bpms.demo.controller;

import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.EntrepreneurialActivityExt;
import com.bpms.demo.service.EntrepreneurialActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创业活动控制器类
 */
@Controller
@RequestMapping("/demo/entrepreneurialactivity")
public class EntrepreneurialActivityController extends DemoBaseController<EntrepreneurialActivityExt> {
    /**
     * Service对象
     */
    @Autowired
    private EntrepreneurialActivityService entrepreneurialActivityService;

    /**
     * 取得Service对象
     */
    @Override
    public EntrepreneurialActivityService getService() {
        return entrepreneurialActivityService;
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
        //会务角色
        dicMap.put("conferenceRoleCd", this.getDictionaryList("DEMO_018"));
        //服务类型
        dicMap.put("serviceTypeCd", this.getDictionaryList("DEMO_019"));
        //服务内容
        dicMap.put("serviceContentCd", this.getDictionaryList("DEMO_020"));
        //活动类型
        dicMap.put("activityTypeCd", this.getDictionaryList("DEMO_021"));
        //导师
        dicMap.put("tutorUid", this.getDropdownList("demo_tutor", "uid", "tutor_name"));
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
            this.getInputEntity().setUid(UniqueUtils.getUID());
        }
        model.addAttribute("entrepreneurialActivityUid", this.getInputEntity().getUid());
    }
}
