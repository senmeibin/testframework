package com.bpms.sys.controller;

import com.bpms.sys.entity.ext.PermissionExt;
import com.bpms.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限控制器类
 */
@Controller
@RequestMapping("/sys/permission")
public class PermissionController extends SysBaseController<PermissionExt> {
    /**
     * Service对象
     */
    @Autowired
    private PermissionService permissionService;

    /**
     * 取得Service对象
     */
    @Override
    public PermissionService getService() {
        return permissionService;
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
        //APP编号
        dicMap.put("appCode", this.getDropdownList("sys_application", "app_code", "app_name"));
        //权限类型
        dicMap.put("typeCd", this.getDictionaryList("SYS001"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
