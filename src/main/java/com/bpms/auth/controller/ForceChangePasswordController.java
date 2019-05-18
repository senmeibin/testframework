package com.bpms.auth.controller;

import com.bpms.core.entity.AjaxResult;
import com.bpms.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 密码强制变更控制器
 */
@Controller
@RequestMapping(value = "/auth/forcechangepassword")
public class ForceChangePasswordController {
    @Autowired
    private UserService userService;

    /**
     * 密码强制变更页面
     */
    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String forceChangePassword(Model model) throws IllegalAccessException, IOException, InstantiationException {
        return "auth/ForceChangePassword";
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public AjaxResult changePassword(@RequestParam String oldPwd, String newPwd, String confirmPwd) {
        if (this.userService.changePassword(oldPwd, newPwd, confirmPwd)) {
            return AjaxResult.createSuccessResult("密码修改成功。");
        }
        else {
            return AjaxResult.createSuccessResult("密码修改失败。");
        }
    }
}