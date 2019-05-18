package com.bpms.sys.controller;

import com.bpms.sys.entity.ext.SmsLogHistoryExt;
import com.bpms.sys.service.SmsLogHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信日志历史控制器类
 */
@Controller
@RequestMapping("/sys/smsloghistory")
public class SmsLogHistoryController extends SysBaseController<SmsLogHistoryExt> {
    /**
     * Service对象
     */
    @Autowired
    private SmsLogHistoryService smsLogHistoryService;

    /**
     * 取得Service对象
     */
    @Override
    public SmsLogHistoryService getService() {
        return smsLogHistoryService;
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

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
