package com.bpms.crm.controller;

import com.bpms.crm.entity.ext.ActivityCampusExt;
import com.bpms.crm.service.ActivityCampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动参与校区控制器类
 */
@Controller
@RequestMapping("/crm/activitycampus")
public class ActivityCampusController extends CrmBaseController<ActivityCampusExt> {
    /**
     * Service对象
     */
    @Autowired
    private ActivityCampusService activityCampusService;

    /**
     * 取得Service对象
     */
    @Override
    public ActivityCampusService getService() {
        return activityCampusService;
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
}
