package com.bpms.cmn.service;

import com.bpms.cmn.utility.DwrScriptSessionManagerUtils;
import org.directwebremoting.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.Collection;

/**
 * 消息推送类服务类
 */
public class MessagePushService {
    /**
     * 日志输出对象
     */
    protected static final Logger log = LoggerFactory.getLogger(MessagePushService.class);

    /**
     * 推送消息
     *
     * @param userId  用户uid
     * @param message 消息json对象
     */
    public static void sendMessage(String userId, String message) {
        log.info("消息推送(用户UID：{}/消息内容：{})。", userId, message);
        sendMessage("SysApp.Cmn.HeaderIns.showNoticeMessage", userId, message);
    }

    /**
     * 推送消息
     *
     * @param funcName JS方法名称
     * @param userId   用户uid
     * @param message  消息json對象
     */
    public static void sendMessage(String funcName, String userId, String message) {
        Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
            @Override
            public boolean match(ScriptSession session) {
                String sessionUserId = (String) session.getAttribute("userId");
                if (sessionUserId == null) {
                    return false;
                }
                else {
                    boolean matched = sessionUserId.equals(userId);
                    if (matched) {
                        log.info("消息推送(匹配UID：{}/消息内容：{})。", userId, message);
                    }
                    return matched;
                }
            }
        }, new Runnable() {
            private ScriptBuffer script = new ScriptBuffer();

            //调用客戶端的js方法
            @Override
            public void run() {
                script.appendCall(funcName, message);
                Collection<ScriptSession> sessions = Browser.getTargetSessions();

                if (sessions.size() > 0) {
                    log.info("消息推送(需要推送的消息数：{})。", sessions.size());
                    for (ScriptSession scriptSession : sessions) {
                        log.info("消息推送(推送UID：{}/消息内容：{})。", userId, message);
                        scriptSession.addScript(script);
                    }
                }
            }
        });
    }

    /**
     * 前台JS(用户login成功时调用）
     *
     * @param userId 用户id
     */
    public void onPageLoad(String userId) {
        //session取得
        ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        //把登录用户写入到session中
        scriptSession.setAttribute("userId", userId);

        try {
            //初始化
            new DwrScriptSessionManagerUtils().init();
        } catch (ServletException e) {
            if (this.log.isErrorEnabled()) {
                log.error("dwr（onPageLoad）消息推送初始化失敗", e);
            }
        }
    }
}