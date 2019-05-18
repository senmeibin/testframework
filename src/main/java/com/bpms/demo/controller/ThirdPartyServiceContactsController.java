package com.bpms.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.demo.entity.ext.ThirdPartyServiceContactsExt;
import com.bpms.demo.service.ThirdPartyServiceContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三方服务联系人控制器类
 */
@Controller
@RequestMapping("/demo/thirdpartyservicecontacts")
public class ThirdPartyServiceContactsController extends DemoBaseController<ThirdPartyServiceContactsExt> {
    /**
     * Service对象
     */
    @Autowired
    private ThirdPartyServiceContactsService thirdPartyServiceContactsService;

    /**
     * 取得Service对象
     */
    @Override
    public ThirdPartyServiceContactsService getService() {
        return thirdPartyServiceContactsService;
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

    /**
     * 设置默认的列（新增时，默认有一列空行）
     *
     * @param model Model对象
     * @throws IOException
     */
    public void setDefaultColumnData(Model model) throws IOException {
        List<ThirdPartyServiceContactsExt> listData = new ArrayList<>();

        listData.add(new ThirdPartyServiceContactsExt());

        //设置一览JSON数据
        model.addAttribute("jsonDataList_" + this.getEntityName(), this.toJSON(listData, true));
    }

    /**
     * 查询第三方服务联系人数据
     *
     * @param parentUids 第三方服务UID
     * @return 查询第三方服务联系人列表JSON字符串
     */
    @RequestMapping(value = "getContactsTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ThirdPartyServiceContactsExt> getContactsTree(@RequestParam(required = false) String parentUids) throws JsonProcessingException {
        return this.thirdPartyServiceContactsService.getContactsTree(parentUids);
    }
}
