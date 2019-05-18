package com.bpms.sys.controller;

import com.bpms.sys.entity.ext.MailLogExt;
import com.bpms.sys.service.MailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件日志控制器类
 */
@Controller
@RequestMapping("/sys/maillog")
public class MailLogController extends SysBaseController<MailLogExt> {
    /**
     * Service对象
     */
    @Autowired
    private MailLogService mailLogService;

    /**
     * 取得Service对象
     */
    @Override
    public MailLogService getService() {
        return mailLogService;
    }

    /**
     * 取得默认排序字段[子类可覆盖]
     *
     * @return 默认排序字段
     */
    @Override
    public Sort getDefaultSort() {
        // 默认按登录日期排序
        return new Sort(Sort.Direction.DESC, "insertDate");
    }

    /**
     * 数据一览画面初期化
     *
     * @param model Model对象
     * @return 数据一览JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException {
        return super.list(model);
    }

    /**
     * 初始化下拉框选项内容
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    @Override
    public void initOptionList(Model model) throws IllegalAccessException, IOException {
        Map<String, Object> dicMap = new HashMap<>();

        //应用名称
        dicMap.put("appCode", this.getDropdownList("sys_application", "app_code", "app_name"));

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
