package com.bpms.crm.controller;

import com.bpms.cmn.service.CmnUserService;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.crm.entity.ext.ActivityExt;
import com.bpms.crm.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动信息控制器类
 */
@Controller
@RequestMapping("/crm/activity")
public class ActivityController extends CrmBaseController<ActivityExt> {
    /**
     * Service对象
     */
    @Autowired
    private ActivityService activityService;

    @Autowired
    private CmnUserService cmnUserService;

    /**
     * 取得Service对象
     */
    @Override
    public ActivityService getService() {
        return activityService;
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
     * 初始化input页面
     *
     * @param model Model对象
     * @param uid   主键UID
     * @return
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = "input", method = RequestMethod.GET)
    public String input(Model model, @RequestParam(required = false) String uid) throws IllegalAccessException, IOException, InstantiationException {
        this.setUseUEditor(true, "/static/plugins/ueditor/ueditor.config.crm.js");
        String path = super.input(model, uid);
        return path;
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
        //活动分类
        dicMap.put("categoryCd", this.getDictionaryList("CRM002"));
        //活动种类
        dicMap.put("typeCd", this.getDictionaryList("CRM003"));
        //审批状态
        dicMap.put("auditStatusCd", this.getDictionaryList("CRM004"));

        List<DropdownEntity> userList = this.cmnUserService.getUserSelect(DropdownEntity.class, false, null);
        //负责人
        dicMap.put("responsibleUserUid", userList);
        //申请人
        dicMap.put("applyUserUid", userList);
        //审批人
        dicMap.put("auditUserUid", userList);

        List<DropdownEntity> campusList = this.getDropdownList("crm_campus", "uid", "name");
        //申请校区
        dicMap.put("applyCampusUid", campusList);
        //参与校区
        dicMap.put("campusUids", campusList);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
