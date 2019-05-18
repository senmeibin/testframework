package com.bpms.core.security;

import com.bpms.core.utils.HttpUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class UserFilter extends org.apache.shiro.web.filter.authc.UserFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        //session失效且为ajax时，返回SESSION_TIME_OUT标志位
        if (subject.getPrincipal() == null && HttpUtils.isAjaxRequest((HttpServletRequest) request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().write("SESSION_TIME_OUT");
            return false;
        }
        return super.onAccessDenied(request, response);
    }
}