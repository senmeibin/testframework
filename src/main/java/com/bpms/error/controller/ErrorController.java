package com.bpms.error.controller;

import com.bpms.sys.entity.ext.ApplicationExt;
import com.bpms.sys.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "accessForbidden", method = RequestMethod.GET)
    public String accessForbidden(@RequestParam(required = false) String appCd, Model model) {
        model.addAttribute("appCd", appCd);
        return "error/AccessForbidden";
    }

    @RequestMapping(value = "applicationMaintenance", method = RequestMethod.GET)
    public String applicationMaintenance(@RequestParam(required = false) String appCd, Model model) {
        ApplicationExt application = applicationService.findByAppCode(appCd);
        model.addAttribute("model", application);
        return "error/ApplicationMaintenance";
    }

    /**
     * 401 页面
     */
    @RequestMapping(value = "401", method = RequestMethod.GET)
    public String noAuthPage(Model model) {
        return "error/401";
    }

    /**
     * 404 页面
     */
    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String noPage(Model model) {
        return "error/404";
    }

    /**
     * 505 页面
     */
    @RequestMapping(value = "505", method = RequestMethod.GET)
    public String sysError(Model model) {
        return "error/405";
    }
}