package com.bpms.cmn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.cmn.service.CmnUserService;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.entity.ext.UserExt;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户对外接口控制器类
 */
@Controller
@RequestMapping("/cmn/user")
public class CmnUserController extends CmnBaseController<UserExt> {
    /**
     * UserService对象
     */
    @Autowired
    private CmnUserService userService;

    @Override
    public CmnUserService getService() {
        return this.userService;
    }

    /**
     * 通过角色取得用户部门树
     *
     * @return 招聘专员列表JSON字符串
     */
    @ResponseBody
    @RequestMapping(value = "getUserTreeByRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeptExt> getUserTreeByRole() throws JsonProcessingException {
        Boolean isFilterDisableAccount = Boolean.parseBoolean(this.getRequest().getParameter("isFilterDisableAccount"));
        Map<String, Object> condition = new HashMap<>();
        //过滤停用账号场合
        if (isFilterDisableAccount) {
            condition.put("users.record_status", 1);
        }
        List<DeptExt> recruitUserList = this.userService.getUserTreeByRole(this.getRequest().getParameter("roleCode"), Boolean.parseBoolean(this.getRequest().getParameter("ignoreDataRangeFlag")), condition);
        return recruitUserList;
    }

    /**
     * 根据用户名称获取用户信息一览
     * [注：AutoComplete专用]
     *
     * @param userName 用户名称
     * @return 带Value的用户一览
     */
    @ResponseBody
    @RequestMapping(value = "autoCompleteUserList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult autoCompleteUserList(@RequestParam String userName) {
        AjaxResult ajaxResult = new AjaxResult();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userName.trim())) {
            return ajaxResult;
        }

        Map<String, Object> condition = new HashMap<>();
        condition.put("main.userName$partial_search", userName.trim());

        List<UserExt> list = this.userService.search(condition);
        List<Map<String, String>> listMap = new ArrayList<>();
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        for (UserExt user : list) {
            Map<String, String> map = new HashMap<>();
            beanMapper.map(user, map);
            map.put("label", user.getUserName());
            listMap.add(map);
        }
        ajaxResult.setContent(listMap);
        return ajaxResult;
    }
}
