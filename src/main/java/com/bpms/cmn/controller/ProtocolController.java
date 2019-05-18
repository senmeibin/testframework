package com.bpms.cmn.controller;

import com.bpms.cmn.entity.ext.ProtocolExt;
import com.bpms.cmn.service.ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 协议管理控制器类
 */
@Controller
@RequestMapping("/cmn/protocol")
public class ProtocolController extends CmnBaseController<ProtocolExt> {
    /**
     * Service对象
     */
    @Autowired
    private ProtocolService protocolService;

    /**
     * 取得Service对象
     */
    @Override
    public ProtocolService getService() {
        return protocolService;
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
     * 初始化input页面
     *
     * @param model Model对象
     * @param uid   主键UID
     * @return
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = "input", method = RequestMethod.GET)
    public String input(Model model, @RequestParam(required = false) String uid) throws IllegalAccessException, IOException, InstantiationException {
        this.setUseUEditor(true, "/static/plugins/ueditor/ueditor.config.simple.js");
        return super.input(model, uid);
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
        //协议分类
        dicMap.put("categoryCd", this.getDictionaryList("CMN006"));
        //使用状态
        dicMap.put("useStatusCd", this.getDictionaryList("CMN007"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
