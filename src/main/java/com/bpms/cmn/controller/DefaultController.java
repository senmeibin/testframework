package com.bpms.cmn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制面板控制器类
 */
@Controller("MDefaultController")
@RequestMapping("/{sysCode}/default")
public class DefaultController {
    @RequestMapping
    public String index(@PathVariable String sysCode) {
        return "/" + sysCode + "/Default";
    }
}