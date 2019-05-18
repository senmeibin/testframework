package com.bpms.core.security;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * {@link org.apache.shiro.mgt.SubjectFactory Subject} implementation to be used in CAS-enabled applications.
 *
 * @see org.apache.shiro.cas.CasSubjectFactory
 * @since 1.2
 */
public class CasSubjectFactory extends DefaultWebSubjectFactory {
    /**
     * 是否启用CAS统一认证中心
     */
    @Value("${shiro.cas.enable:false}")
    protected boolean shiroCasEnable;

    @Override
    public Subject createSubject(SubjectContext context) {
        if (shiroCasEnable) {

            //the authenticated flag is only set by the SecurityManager after a successful authentication attempt.
            boolean authenticated = context.isAuthenticated();

            //although the SecurityManager 'sees' the submission as a successful authentication, in reality, the
            //login might have been just a CAS rememberMe login.  If so, set the authenticated flag appropriately:
            if (authenticated) {

                AuthenticationToken token = context.getAuthenticationToken();

                if (token != null && token instanceof org.apache.shiro.cas.CasToken) {
                    org.apache.shiro.cas.CasToken casToken = (CasToken) token;
                    // set the authenticated flag of the context to true only if the CAS subject is not in a remember me mode
                    if (casToken.isRememberMe()) {
                        context.setAuthenticated(false);
                    }
                }
            }
        }

        return super.createSubject(context);
    }
}
