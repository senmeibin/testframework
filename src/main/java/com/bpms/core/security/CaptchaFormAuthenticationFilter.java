package com.bpms.core.security;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.Digests;
import com.bpms.core.utils.HttpUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.LoginLogService;
import com.bpms.sys.service.OnlineUserService;
import com.bpms.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class CaptchaFormAuthenticationFilter extends CasFilter {
    private static final Logger log = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

    private static final String TICKET_PARAMETER = "ticket";

    /**
     * 是否启用CAS统一认证中心
     */
    @Value("${shiro.cas.enable:false}")
    protected boolean shiroCasEnable;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private OnlineUserService onlineUserService;

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        //检查用户是否存在
        String userUid = (String) token.getPrincipal();
        if (StringUtils.isNotEmpty(userUid)) {
            UserExt user = userService.findByUserCd(userUid);
            if (user != null) {
                userUid = user.getUid();
            }
            // 保存错误日志
            loginLogService.saveError(userUid, (String) token.getPrincipal(), request.getRemoteHost());
        }
        if (shiroCasEnable) {
            return super.onLoginFailure(token, e, request, response);
        }
        //页面显示错误信息
        if (e instanceof IncorrectCredentialsException) {
            ((HttpServletRequest) request).getSession().setAttribute("error", "用户名或密码错误。");
        }
        else {
            ((HttpServletRequest) request).getSession().setAttribute("error", e.getMessage());
        }
        return true;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (user != null) {
            //记录登录日志
            loginLogService.saveSuccess(user.getUserUid(), user.getUserCd(), user.getUserName(), user.getIp());

            //更新在线用户
            String sessionId = ((HttpServletRequest) request).getSession().getId();
            onlineUserService.updateSession(sessionId, user.getUserUid(), user.getUserCd(), user.getUserName(), user.getIp());

            //强制密码变更
            if (Objects.equals(user.getForceChangePswd(), CmnConsts.FORCE_CHANGE_PSWD_YES)) {
                WebUtils.getAndClearSavedRequest(request);
                WebUtils.redirectToSavedRequest(request, response, "/auth/forcechangepassword");
                return false;
            }
        }
        ((HttpServletRequest) request).getSession().removeAttribute("error");
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        CasToken casToken;
        //登录IP
        String host = getHost(request);

        if (shiroCasEnable) {
            casToken = new CasToken(request.getParameter(TICKET_PARAMETER));
            //多条件登录判断在ShiroDbRealm里面处理
        }
        else {
            String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
            String password = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM);

            UserExt user = userService.findByUserCd(username);
            //用户账号不存在的场合，判断是否是使用手机号码登录
            if (user == null && StringUtils.length(username) == 11 && StringUtils.isNumeric(username) && username.startsWith("1")) {
                //根据手机号码查找用户、并使用该手机号码对应的用户账号进行登录
                user = userService.findByMobile(username);
                if (user != null) {
                    username = user.getUserCd();
                }
            }

            casToken = new CasToken(Digests.shaPassword256(username) + Digests.shaPassword256(password));
            casToken.setUserId(username);
        }

        try {
            host = HttpUtils.getRemoteIp((HttpServletRequest) request);
        } catch (Exception e) {
            log.warn("取得用户登录ip失败:" + e.getMessage());
        }

        casToken.setHost(host);

        casToken.setRememberMe(isRememberMe(request));

        return casToken;
    }

    /**
     * 判断如果为CAS则直接请求登录否则转到login页面
     *
     * @param request ServletRequest
     * @param response ServletResponse
     * @return 
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (shiroCasEnable) {
            //判断是否存在CAS票据
            if (StringUtils.isNotEmpty(request.getParameter(TICKET_PARAMETER))) {
                return super.onAccessDenied(request, response);
            }
            else {
                //没有登录则转到登录页面
                this.redirectToLogin(request, response);
                return false;
            }
        }
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            }
            else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                return true;
            }
        }
        else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    /**
     * 如果请求是HTTP POST，则此默认实现仅返回true，否则为false。 可以通过子类覆盖自定义登录提交检测行为。
     *
     * @param request
     * @param response
     * @return
     */
    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
    }

    /**
     * 跳转至登录页面，重写此方法可以在跳转时加入url参数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        String loginUrl = getLoginUrl();
        //参数
        StringBuilder parameter = new StringBuilder();
        //获取参数列表
        Map<String, String[]> map = request.getParameterMap();
        if (CollectionUtils.isNotEmpty(map)) {
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                //参数值
                String[] values = entry.getValue();
                if (values == null || values.length == 0) {
                    continue;
                }
                for (String value : values) {
                    parameter.append("&").append(entry.getKey()).append("=").append(value);
                }
            }
        }
        //添加参数后，跳转页面
        WebUtils.issueRedirect(request, response, loginUrl + parameter.toString());
    }
}