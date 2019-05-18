package com.bpms.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;

/**
 * 控制面板控制器类
 */
@Controller
@RequestMapping("/portal/dashboard")
public class DashboardController {
    @RequestMapping
    public String index(ServletRequest req, Model model) {
        String s = req.getParameter("tenant");
        model.addAttribute("tenant", s);
        return "/portal/Dashboard";
    }
}
