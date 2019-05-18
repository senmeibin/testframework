package com.bpms.demo.controller;

import com.bpms.demo.entity.ext.ChargesInformationDetailExt;
import com.bpms.demo.service.ChargesInformationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业收费明细控制器类
 */
@Controller
@RequestMapping("/demo/chargesinformationdetail")
public class ChargesInformationDetailController extends DemoBaseController<ChargesInformationDetailExt> {
    /**
     * Service对象
     */
    @Autowired
    private ChargesInformationDetailService chargesInformationDetailService;

    /**
     * 取得Service对象
     */
    @Override
    public ChargesInformationDetailService getService() {
        return chargesInformationDetailService;
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
        //收款方式
        dicMap.put("chargesTypeCd", this.getDictionaryList("DEMO_014"));
        //收款状态
        dicMap.put("chargesCd", this.getDictionaryList("DEMO_015"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 设置默认的列（新增时，默认有一列空行）
     *
     * @param model Model对象
     * @throws IOException
     */
    public void setDefaultColumnData(Model model) throws IOException {
        List<ChargesInformationDetailExt> listData = new ArrayList<>();

        listData.add(new ChargesInformationDetailExt());

        //设置一览JSON数据
        model.addAttribute("jsonDataList_" + this.getEntityName(), this.toJSON(listData, true));
    }
}
