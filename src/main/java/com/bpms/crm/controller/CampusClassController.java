package com.bpms.crm.controller;

import com.bpms.cmn.service.CmnUserService;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.crm.entity.ext.CampusClassExt;
import com.bpms.crm.service.CampusClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.*;

/**
 * 校区班级控制器类
 */
@Controller
@RequestMapping("/crm/campusclass")
public class CampusClassController extends CrmBaseController<CampusClassExt> {
    /**
     * Service对象
     */
    @Autowired
    private CampusClassService campusClassService;

    @Autowired
    private CmnUserService cmnUserService;

    /**
     * 取得Service对象
     */
    @Override
    public CampusClassService getService() {
        return campusClassService;
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
        //班级级别
        dicMap.put("classLevelCd", this.getDictionaryList("CRM005"));

        //班级分类
        dicMap.put("categoryCd", this.getDictionaryList("CRM020"));

        List<DropdownEntity> userList = this.cmnUserService.getUserSelect(DropdownEntity.class, false, null);
        //授课讲师
        dicMap.put("teacherUserUid", userList);
        //课程顾问
        dicMap.put("consultantUserUid", userList);

        List<DropdownEntity> campusList = this.getDropdownList("crm_campus", "uid", "name|code");
        for (DropdownEntity entity : campusList) {
            entity.setSubName(entity.getSubName().replace("|", "(") + ")");
        }
        //所属校区
        dicMap.put("campusUid", campusList);

        List<DropdownEntity> yearList = new ArrayList<>();
        for (int i = 2017; i <= Calendar.getInstance().get(Calendar.YEAR) + 1; i++) {
            DropdownEntity entity = new DropdownEntity();
            entity.setSubCd(String.valueOf(i));
            entity.setSubName(String.valueOf(i));
            yearList.add(entity);
        }
        //开班年度
        dicMap.put("classYear", yearList);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
