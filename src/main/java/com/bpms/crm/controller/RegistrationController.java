package com.bpms.crm.controller;

import com.bpms.cmn.service.CmnUserService;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.crm.entity.ext.RegistrationExt;
import com.bpms.crm.service.CampusClassService;
import com.bpms.crm.service.RegistrationService;
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
 * 报名记录控制器类
 */
@Controller
@RequestMapping("/crm/registration")
public class RegistrationController extends CrmBaseController<RegistrationExt> {
    /**
     * Service对象
     */
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CampusClassService campusClassService;

    @Autowired
    private CmnUserService cmnUserService;

    /**
     * 取得Service对象
     */
    @Override
    public RegistrationService getService() {
        return registrationService;
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

        List<DropdownEntity> campusList = this.getDropdownList("crm_campus", "uid", "name");
        //所属校区
        dicMap.put("registrationBelongCampusUid", campusList);
        //报名校区
        dicMap.put("registrationCampusUid", campusList);

        List<DropdownEntity> userList = this.cmnUserService.getUserSelect(DropdownEntity.class, false, null);
        //课程顾问
        dicMap.put("registrationBelongConsultantUserUid", userList);

        List<DropdownEntity> classList = this.getDropdownList("crm_campus_class", "uid", "class_number|class_name");
        //报名班级
        dicMap.put("registrationCampusClassUid", classList);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
