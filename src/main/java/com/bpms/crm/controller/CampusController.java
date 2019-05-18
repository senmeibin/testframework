package com.bpms.crm.controller;

import com.bpms.cmn.service.CmnUserService;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.crm.entity.ext.CampusExt;
import com.bpms.crm.service.CampusService;
import com.bpms.sys.service.DeptService;
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
 * 校区信息控制器类
 */
@Controller
@RequestMapping("/crm/campus")
public class CampusController extends CrmBaseController<CampusExt> {
    /**
     * Service对象
     */
    @Autowired
    private CampusService campusService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private CmnUserService cmnUserService;

    /**
     * 取得Service对象
     */
    @Override
    public CampusService getService() {
        return campusService;
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
        //校区分类
        dicMap.put("categoryCd", this.getDictionaryList("CRM001"));

        //所属部门
        dicMap.put("deptUid", deptService.getDropdownDept(CmnConsts.ROOT_DEPT_UID, 3));

        //门店店长/门店副店长/门店助理
        List<DropdownEntity> userList = this.cmnUserService.getUserSelect(DropdownEntity.class, false, null);
        //门店店长
        dicMap.put("managerUserUid", userList);
        //门店副店长
        dicMap.put("assistantManagerUserUid", userList);
        //门店助理
        dicMap.put("assistantUserUid", userList);
        //校区顾问
        dicMap.put("consultantUserUids", userList);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
