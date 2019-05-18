package com.bpms.auth.controller;

import com.bpms.core.AppContext;
import com.bpms.core.entity.AjaxResult;
import com.bpms.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.IOException;

@Controller
@RequestMapping(value = "/auth/retrievepassword")
public class RetrievePasswordController {
    @Autowired
    private UserService userService;

    /**
     * 页面
     */
    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String init(Model model) {
        return "auth/RetrievePassword";
    }

    /**
     * 重置用户密码并发送邮件
     *
     * @return AjaxResult
     * @throws IOException
     * @throws MessagingException
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult resetPassword(@RequestParam String userCd, @RequestParam(required = false) String userMail) throws Exception {
        //邮件通知的场合
        if (!StringUtils.isEmpty(userMail)) {
            int result = this.userService.resetPassword(AppContext.getRequest().getServletContext().getRealPath("/"), userCd, userMail);

            //重置用户密码并发送邮件处理成功的场合
            if (result == 1) {
                return AjaxResult.createSuccessResult(String.format("密码重置成功，新密码已发送到您填写的邮箱地址中。<br />请进入邮箱（%s）查收新密码。", userMail));
            }
            else if (result == -1) {
                return AjaxResult.createFailResult("邮件发送失败，请与系统管理员联系!");
            }
            else {
                return AjaxResult.createFailResult("您输入的用户账号不存在或与邮箱地址不匹配，请查证后重新填写!");
            }
        }
        //SMS短信通知的场合
        else {
            int result = this.userService.resetPassword(userCd);

            if (result == 1) {
                return AjaxResult.createSuccessResult("密码重置成功，新密码已通过短信方式发送到账号绑定的手机。");
            }
            else if (result == -1) {
                return AjaxResult.createFailResult("短信发送失败，请与系统管理员联系。");
            }
            else {
                return AjaxResult.createFailResult("您输入的用户账号不存在或不匹配，请查证后重新填写!");
            }
        }
    }
}
