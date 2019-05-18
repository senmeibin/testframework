package com.bpms.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.demo.service.DemoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器类
 */
@Controller(value = "DemoUserController")
@RequestMapping("/demo/user")
public class UserController extends DemoBaseController<UserExt> {
    /**
     * Service对象
     */
    @Autowired
    private DemoUserService userService;

    /**
     * 取得Service对象
     */
    @Override
    public DemoUserService getService() {
        return userService;
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

    @Override
    protected String getJsListInstance() {
        return "SysApp.Demo.UserListIns";
    }

    /**
     * 通过角色取得用户部门树
     *
     * @return 招聘专员列表JSON字符串
     */
    @RequestMapping(value = "getUserTreeByRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> getUserTreeByRole() throws JsonProcessingException {
        List<DeptExt> recruitUserList = this.userService.getUserTreeByRole(this.getRequest().getParameter("roleCode"));
        return recruitUserList;
    }

    /**
     * 获取所有有效用户的用户部门树
     *
     * @return 招聘专员列表JSON字符串
     */
    @RequestMapping(value = "getAllUserTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> getAllUserTree() throws JsonProcessingException {
        List<DeptExt> recruitUserList = this.userService.getAllUserTree(this.getRequest().getParameter("roleCode"));
        return recruitUserList;
    }

}
