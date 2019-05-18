package com.bpms.sys.controller;

import com.bpms.sys.entity.UrlRoleTree;
import com.bpms.sys.entity.ext.RoleExt;
import com.bpms.sys.entity.ext.UrlRoleExt;
import com.bpms.sys.service.RoleService;
import com.bpms.sys.service.UrlRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签控制器类
 */
@Controller
@RequestMapping("/sys/urlrole")
public class UrlRoleController extends SysBaseController<UrlRoleExt> {
    /**
     * Service对象
     */
    @Autowired
    private UrlRoleService urlRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 取得Service对象
     */
    @Override
    public UrlRoleService getService() {
        return urlRoleService;
    }

    /**
     * 权限配置树形一览
     *
     * @param model Model对象
     * @return 部门树形JSP页面
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String urlRoleTreeList(Model model) throws IllegalAccessException, IOException, InstantiationException {
        super.list(model);

        return "sys/urlrole/UrlRoleTreeList";
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
        List<RoleExt> list = this.roleService.getRoleList();

        //角色列表
        dicMap.put("roleList", list);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 取所有URL
     *
     * @return URL列表JSON字符串
     */
    @RequestMapping(value = "getAllUrlList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UrlRoleTree> getAllUrlList() {
        return this.getService().getAllUrlList();
    }

    /**
     * 获取url对应的权限配置
     */
    @RequestMapping(value = "getUrlRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UrlRoleExt getUrlRole(@RequestParam String url) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("url", url);
        List<UrlRoleExt> urlRoles = this.getService().search(UrlRoleExt.class, this.getService().getSearchSQL(condition), condition);
        if (urlRoles.size() > 0) {
            return urlRoles.get(0);
        }
        return null;
    }
}
