package com.bpms.crm.controller;

import com.bpms.cmn.service.CmnUserService;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.crm.entity.ext.FollowupExt;
import com.bpms.crm.service.FollowupService;
import com.bpms.crm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跟进记录控制器类
 */
@Controller
@RequestMapping("/crm/followup")
public class FollowupController extends CrmBaseController<FollowupExt> {
    /**
     * Service对象
     */
    @Autowired
    private FollowupService followupService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CmnUserService cmnUserService;

    /**
     * 取得Service对象
     */
    @Override
    public FollowupService getService() {
        return followupService;
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
        //跟进方式
        dicMap.put("followupMethodCd", this.getDictionaryList("CRM013"));
        //销售进程
        dicMap.put("saleProcessCd", this.getDictionaryList("CRM014"));
        //是否预约
        dicMap.put("acceptShiftCd", this.getDictionaryList("CRM015"));

        List<DropdownEntity> campusList = this.getDropdownList("crm_campus", "uid", "name");
        //所属校区
        dicMap.put("followupBelongCampusUid", campusList);

        List<DropdownEntity> userList = this.cmnUserService.getUserSelect(DropdownEntity.class, false, null);
        //课程顾问
        dicMap.put("followupBelongConsultantUserUid", userList);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
