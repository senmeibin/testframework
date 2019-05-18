package com.bpms.core.entity;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.security.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class UserAuditorAware implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject == null || subject.getPrincipal() == null) {
                return CmnConsts.UNKNOWN_USER_UID;
            }
            return ((ShiroUser) subject.getPrincipal()).getUserUid();
        } catch (Exception e) {
            return CmnConsts.UNKNOWN_USER_UID;
        }
    }
}