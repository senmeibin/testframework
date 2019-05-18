package com.bpms.sys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.sys.entity.ext.ContactExt;
import com.bpms.sys.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人控制器类
 */
@Controller(value = "SysContactController")
@RequestMapping("/sys/contact")
public class ContactController extends SysBaseController<ContactExt> {
    /**
     * Service对象
     */
    @Autowired
    private ContactService contactService;

    /**
     * 取得Service对象
     */
    @Override
    public ContactService getService() {
        return contactService;
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

    public void initCompanyInfo(Model model, String companyUid) throws JsonProcessingException {
        //设置关联属性
        model.addAttribute("companyUid", companyUid);

        //取得明细列表
        Map<String, Object> condition = new HashMap();
        condition.put("main.companyUid", companyUid);
        List<ContactExt> contactList = this.search(contactService.getSearchSQL(condition), condition);

        model.addAttribute("contactList", this.toJSON(contactList, true));
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

        //来往单位UID
        dicMap.put("companyUid", this.getDropdownList("sys_company", "uid", "name"));

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
