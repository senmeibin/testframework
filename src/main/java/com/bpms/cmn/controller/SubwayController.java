package com.bpms.cmn.controller;

import com.bpms.cmn.entity.ext.SubwayExt;
import com.bpms.cmn.entity.ext.SubwayStationExt;
import com.bpms.cmn.service.SubwayService;
import com.bpms.cmn.service.SubwayStationService;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.entity.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
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
 * 地铁线路信息控制器类
 */
@Controller
@RequestMapping("/cmn/subway")
public class SubwayController extends CmnBaseController<SubwayExt> {
    /**
     * Service对象
     */
    @Autowired
    private SubwayService subwayService;
    /**
     * Service对象
     */
    @Autowired
    private SubwayStationService subwayStationService;

    /**
     * 取得Service对象
     */
    @Override
    public SubwayService getService() {
        return subwayService;
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
        //城市UID
        dicMap.put("cityUid", this.getDropdownList("cmn_region", "uid", "region_name"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 地铁线路信息更新
     */
    @RequestMapping(value = "initSubwayStationInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult initSubwayStationInfo() {
        return AjaxResult.createSuccessResult(subwayService.initSubwayStationInfo());
    }

    /**
     * 根据地铁站点名称获取地铁站点一览
     * [注：AutoComplete专用]
     *
     * @param stationName 地铁站点名称
     * @return 带Value的地铁站点一览
     */
    @ResponseBody
    @RequestMapping(value = "autoCompleteStationList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult autoCompleteStationList(@RequestParam String stationName) {
        AjaxResult ajaxResult = new AjaxResult();

        if (StringUtils.isEmpty(stationName) || StringUtils.isEmpty(stationName.trim())) {
            return ajaxResult;
        }
        String cityNameList = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "SUBWAY_CITY_NAME_LIST", "北京市,上海市,成都市,广州市,深圳市,天津市,重庆市,南京市,杭州市,合肥市");
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.stationName$partial_search", stationName.trim());
        condition.put("region_city.region_name$in", cityNameList);

        List<SubwayStationExt> list = subwayStationService.search(condition);
        List<Map<String, String>> listMap = new ArrayList<>();
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        for (SubwayStationExt station : list) {
            Map<String, String> map = new HashMap<>();
            beanMapper.map(station, map);
            map.put("label", station.getCityName() + "-" + station.getSubwayNo() + "-" + station.getStationName());
            listMap.add(map);
        }
        ajaxResult.setContent(listMap);
        return ajaxResult;
    }

    /**
     * 城市地铁线路信息取得
     */
    @RequestMapping(value = "getSubwayStationInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult getSubwayStationInfo() {
        return AjaxResult.createSuccessResult("城市地铁线路信息取得成功", subwayService.getAllSubwayMap(StringUtils.EMPTY));
    }
}
