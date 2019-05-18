package com.bpms.auth.controller;

import com.bpms.core.AppContext;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.security.ShiroUser;
import com.bpms.core.utils.AmountUtils;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.ext.EnterpriseExt;
import com.bpms.sys.entity.ext.AttachmentExt;
import com.bpms.sys.service.ParameterService;
import com.bpms.sys.service.EnterpriseService;
import com.bpms.sys.service.AttachmentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.HostUnauthorizedException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * <p>
 * 真正登录的POST请求由Filter完成,
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {
    /**
     * 日志输出对象
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * ParameterService对象
     */
    @Autowired
    protected ParameterService parameterService;

    /**
     * EnterpriseService对象
     */
    @Autowired
    protected EnterpriseService enterpriseService;

    /**
     * AttachmentService对象
     */
    @Autowired
    protected AttachmentService attachmentService;

    /**
     * 登录失败的场合
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,
                       @RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password,
                       ServletRequest req, Model model) {

        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        String error;
        if (StringUtils.isEmpty(exceptionClassName)) {
            // 已经授权
            return "redirect:/";
        }
        else {
            log.error("login exception:" + exceptionClassName);
        }
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM, password);

        if (UnknownAccountException.class.getName().equals(exceptionClassName) || IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名或密码错误。";
        }
        else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "您的登录太频繁，请10分钟之后再试。";
        }
        else if (HostUnauthorizedException.class.getName().equals(exceptionClassName)) {
            error = "IP限制，不允许访问系统。";
        }
        else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
            error = "账号被锁定或被停用，请与系统管理员联系。";
        }
        else if (DisabledAccountException.class.getName().equals(exceptionClassName)) {
            error = "账号不在登录许可时间范围内，请与系统管理员联系。";
        }
        else {
            error = "系统异常，请与系统管理员联系。";
        }

        model.addAttribute("error", error);

        return "auth/Login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(ServletRequest req,Model model) throws IOException {
        //会话用户存在的场合，先做退出处理
        if (this.getCurrentUser() != null && !StringUtils.isEmpty(this.getCurrentUserId())) {
            AppContext.getResponse().sendRedirect("/logout");
        }
        //企业信息
        String s = req.getParameter("tenant");
        if( s != null && AmountUtils.isNumeric(s)) {
            EnterpriseExt enterprise = enterpriseService.findOne(Integer.parseInt(s));
            model.addAttribute("enterprise", enterprise);

            // 企业图片
            List<AttachmentExt> attachmentExtList= attachmentService.findByRelationUid(s);
            model.addAttribute("attachmentExtList", attachmentExtList);
        }

        String theme = this.parameterService.getValue(AppCodeConsts.APP_AUTH, "LOGIN_THEME", "LoginBlue");
        return "auth/" + theme;
    }

    /**
     * 取出Shiro中的当前用户信息
     *
     * @return 当前用户信息
     */
    public ShiroUser getCurrentUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 取出Shiro中的当前用户UID.
     *
     * @return 当前用户UID
     */
    public String getCurrentUserId() {
        return getCurrentUser().getUserUid();
    }

    @RequestMapping(value = {"success", "login/success"}, method = RequestMethod.GET)
    public String success(ServletRequest req,Model model) {
        String s = req.getParameter("tenant");
        model.addAttribute("tenant", s);
        return "/portal/Dashboard";
    }
}