package com.bpms.pms.controller;

import com.bpms.core.entity.BaseEntity;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * 控制面板控制器类
 */
@Controller("PmsDefaultController")
@RequestMapping("/pms/default")
public class DefaultController extends PmsBaseController<BaseEntity> {

    /**
     * 取得Service对象
     */
    @Override
    public BaseService getService() {
        return null;
    }

    @RequestMapping
    public String index() {
        return "/pms/Default";
    }

    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    @Override
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException, ServiceException {
        return "/crm/Default";
    }
}