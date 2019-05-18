package com.bpms.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.demo.entity.ext.WeeklyReportDetailExt;
import com.bpms.demo.service.WeeklyReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
 * 周报明细控制器类
 */
@Controller
@RequestMapping("/demo/weeklyreportdetail")
public class WeeklyReportDetailController extends DemoBaseController<WeeklyReportDetailExt> {
    /**
     * Service对象
     */
    @Autowired
    private WeeklyReportDetailService weeklyReportDetailService;

    /**
     * 取得Service对象
     */
    @Override
    public WeeklyReportDetailService getService() {
        return weeklyReportDetailService;
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
     * 新增时初始周报表明细数据列表
     *
     * @param model Model对象
     */
    public void initWeeklyReportDetailList(Model model) {
        List<WeeklyReportDetailExt> listData = new ArrayList<>();
        //周次
        List<DropdownEntity> dictionaryList = this.getDictionaryList("DEMO_022");

        for (DropdownEntity dropdownEntity : dictionaryList) {
            WeeklyReportDetailExt entity = new WeeklyReportDetailExt();

            //周次Cd
            entity.setWeekCd(dropdownEntity.getSubCd());
            //周次名称
            entity.setWeekName(dropdownEntity.getSubName());

            listData.add(entity);
        }

        //设置一览JSON数据
        try {
            model.addAttribute("jsonDataList_" + this.getEntityName(), this.toJSON(listData, true));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sort getDefaultSort() {
        //默认按周次排序
        return new Sort(Sort.Direction.ASC, "week_cd");
    }
}