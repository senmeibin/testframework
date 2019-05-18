package com.bpms.cmn.controller;

import com.bpms.cmn.entity.ext.SignCompanyExt;
import com.bpms.cmn.service.SignCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签单公司控制器类
 */
@Controller(value = "CmnSignCompanyController")
@RequestMapping("/cmn/signcompany")
public class SignCompanyController extends CmnBaseController<SignCompanyExt> {
    /**
     * Service对象
     */
    @Autowired
    private SignCompanyService signCompanyService;

    /**
     * 取得Service对象
     */
    @Override
    public SignCompanyService getService() {
        return signCompanyService;
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
        //初始化数据导入标志位
        this.initDataImportFlag(model);
        return super.list(model);
    }

    /**
     * 取所有签单公司
     *
     * @return 签单公司列表
     */
    @RequestMapping(value = "getAllCompanyList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<SignCompanyExt> getAllCompanyList() {
        return signCompanyService.getAllCompanyList();
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
