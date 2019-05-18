package com.bpms.core.interceptor;

import com.bpms.core.security.ShiroUser;
import com.bpms.core.utils.HttpUtils;
import com.bpms.sys.entity.ext.EnterpriseSettingExt;
import com.bpms.sys.service.EnterpriseSettingService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统拦截器
 */
@Component
public class SaasSystemInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 企业设置Service
     */
    @Autowired
    private EnterpriseSettingService enterpriseSettingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求URI
        String requestUri = request.getRequestURI().replaceFirst(request.getContextPath(), StringUtils.EMPTY);

        //静态内容访问的场合
        if (HttpUtils.isStaticContentsAccess(requestUri)) {
            return true;
        }

        //初期化登录者信息
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //用户登录的场合
        if (loginUser != null && loginUser.getEnterpriseUid() != null && loginUser.getEnterpriseUid() != 0) {
            EnterpriseSettingExt setting = this.enterpriseSettingService.getDetail(loginUser.getEnterpriseUid());

            //设置登录用户企业设置新
            request.setAttribute("enterpriseSetting", setting);
        }
        return true;
    }
}
