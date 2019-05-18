package com.bpms.cmn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static org.apache.shiro.SecurityUtils.getSubject;

/**
 * 系统心跳监控专用控制器类
 * 用于判断系统是否正常运行
 * 注意：使用该功能需配置applicationContext-shiro.xml 中 /cmn/heartbeat/** = anon
 */
@Controller(value = "HeartbeatController")
@RequestMapping("/cmn/heartbeat")
public class HeartbeatController {

    /**
     * 用于用户session验证
     *
     * @return true：平台用户session有效；false：平台用户session无效
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    @ResponseBody
    @RequestMapping(value = "/validateSession", method = RequestMethod.GET)
    public boolean validateSession() throws IllegalAccessException, IOException, InstantiationException {
        return getSubject().getSession() != null;
    }
}