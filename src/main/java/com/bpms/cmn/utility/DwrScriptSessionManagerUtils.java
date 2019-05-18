package com.bpms.cmn.utility;

import org.apache.commons.lang3.StringUtils;
import org.directwebremoting.Container;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.servlet.DwrServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 * 消息推送session管理類
 */
public class DwrScriptSessionManagerUtils extends DwrServlet {
    /**
     * 日志输出对象
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        //容器取得
        Container container = ServerContextFactory.get().getContainer();

        ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);

        ScriptSessionListener listener = new ScriptSessionListener() {
            @Override
            public void sessionCreated(ScriptSessionEvent ev) {
                HttpSession session = WebContextFactory.get().getSession();
                //用戶UID取得
                String userId = (String) session.getAttribute("userId");

                if (StringUtils.isNotEmpty(userId)) {
                    ev.getSession().setAttribute("userId", userId);

                    if (log.isDebugEnabled()) {
                        log.debug("a script session is created! 用戶UID：" + userId);
                    }
                }
            }

            @Override
            public void sessionDestroyed(ScriptSessionEvent ev) {
                if (log.isDebugEnabled()) {
                    //log.debug("a script session is destroyed!");
                }
            }
        };

        manager.addScriptSessionListener(listener);
    }
}
