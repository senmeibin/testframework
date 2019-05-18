package com.bpms.cmn.controller;

import com.bpms.cmn.entity.ext.SubwayStationExt;
import com.bpms.cmn.service.SubwayStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 地铁站信息控制器类
 */
@Controller
@RequestMapping("/cmn/subwaystation")
public class SubwayStationController extends CmnBaseController<SubwayStationExt> {
    /**
     * Service对象
     */
    @Autowired
    private SubwayStationService subwayStationService;

    /**
     * 取得Service对象
     */
    @Override
    public SubwayStationService getService() {
        return subwayStationService;
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
        //地铁线路UID
        dicMap.put("subwayUid", this.getDropdownList("cmn_subway", "uid", "subway_no"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
