package com.bpms.cmn.controller;

import com.bpms.cmn.entity.ext.RegionExt;
import com.bpms.cmn.service.RegionService;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.utils.AccessUtils;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.SearchConditionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地区信息控制器类
 */
@Controller(value = "CmnRegionController")
@RequestMapping("/cmn/region")
public class RegionController extends CmnBaseController<RegionExt> {
    /**
     * Service对象
     */
    @Autowired
    private RegionService regionService;

    /**
     * 取得Service对象
     */
    @Override
    public RegionService getService() {
        return regionService;
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
     * 初始化下拉框选项内容
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    @Override
    public void initOptionList(Model model) throws IllegalAccessException, IOException {
        Map<String, Object> dicMap = new HashMap<>();
        //父节点
        dicMap.put("parentUid", this.getDropdownList("cmn_region", "uid", "region_name"));

        Map<String, Object> condition = new HashMap<>();
        condition.put("parent_uid", "0");
        //顶级区域名称
        dicMap.put("uid", this.getDropdownList("cmn_region", "uid", "region_name", condition));

        Map<String, Object> grade1 = new HashMap<>();
        grade1.put("regionGrade", "1");
        //层级为1的
        dicMap.put("grade1", this.getDropdownList("cmn_region", "uid", "region_name", grade1));

        Map<String, Object> grade2 = new HashMap<>();
        grade2.put("regionGrade", "2");
        //层级为2的
        dicMap.put("grade2", this.getDropdownList("cmn_region", "uid", "region_name", grade2));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 上移/下移操作
     *
     * @param sourceUid 源地区UID
     * @param targetUid 目标地区UID
     * @return AjaxResult
     */
    @RequestMapping(value = "moveData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult moveData(@RequestParam String sourceUid, @RequestParam String targetUid) {
        this.regionService.switchRegionSeq(sourceUid, targetUid);
        return AjaxResult.createSuccessResult();
    }

    /**
     * 获取管辖城市列表
     *
     * @return 部门列表JSON字符串
     */
    @RequestMapping(value = "getManageCityList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RegionExt> getManageCityList() {

        //系统管理员、技术支持、集团权限的场合，无需任何条件限制
        if (AccessUtils.isSystemManagement() || AccessUtils.isItSupport()) {
            return getAllRegion(2, "");
        }

        Map<String, Object> condition = new HashMap<>();
        condition.put("city_manage.user_uid", getCurrentUserId());
        List<RegionExt> cityList = this.regionService.search(this.regionService.getSQL("cmn/Region/getManageCityList"), condition);
        if (CollectionUtils.isEmpty(cityList)) {
            return getAllRegion(2, "");
        }
        return cityList;
    }

    /**
     * 根据条件取得地区信息
     *
     * @param regionGrade 地区等级(1：省+直辖市+特别行政区+自治区+台湾/2：城市/3：区县)
     * @param parentUids  上级地区列表 逗号分隔
     * @return 地区信息
     */
    @RequestMapping(value = "getAllRegion/{regionGrade}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RegionExt> getAllRegion(@PathVariable Integer regionGrade, @RequestParam(required = false, defaultValue = "") String parentUids) {
        return getService().getAllRegion(regionGrade, parentUids);
    }

    /**
     * 修改区域记录状态
     *
     * @param uid          区域UID
     * @param recordStatus 记录状态 1：启用/8：停用
     * @return AjaxResult
     */
    @RequestMapping(value = "updateRegionRecordStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult updateRegionRecordStatus(@RequestParam String uid, @RequestParam String recordStatus) {
        this.regionService.updateRegionRecordStatus(uid, recordStatus);
        return AjaxResult.createSuccessResult();
    }

    /**
     * 获取区域信息列表
     *
     * @param pagerJson     页面JSON
     * @param conditionJson 条件
     * @return Page 区域分页
     */
    @ResponseBody
    @RequestMapping(value = "/getRegionSettingList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "查询区域列表")
    public Page<RegionExt> getRegionSettingList(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) {
        // 取得查询条件
        Map<String, Object> condition = this.getCondition(conditionJson);

        // 保存最新的检索条件
        this.setSessionSearchCondition(conditionJson);
        this.setSessionSearchPager(pagerJson);
        // 查询sql
        String sql = this.getService().getSQL("/cmn/Region/getRegionSettingList");

        //获取查询条件
        String provinceUid = (String) SearchConditionUtils.getConditionValue(condition, "main.uid$=");
        //查询全部省份场合
        if (StringUtils.isEmpty(provinceUid)) {
            sql += " WHERE main.city_uid IS NULL AND main.region_uid IS NULL";
        }
        //查询省份城市场合
        else {
            sql += " WHERE main.province_uid = '" + provinceUid + "' AND main.city_uid IS NOT NULL AND main.region_uid IS NULL";
        }

        //获取页面显示条件
        Map map = this.getPager(pagerJson);
        //设置默认排序 表示顺序 升序
        if (StringUtils.isEmpty((String) map.get("sortColumn"))) {
            map.put("sortColumn", "dispSeq");
        }
        // 数据查询
        return this.regionService.getDao().search(RegionExt.class, sql, new HashMap<String, Object>(), this.getPageRequest(map));
    }

    /**
     * 批量保存
     *
     * @param regionRows 待修改记录行记录信息
     * @return AjaxResult
     */
    @RequestMapping(value = "batchSave", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult batchSave(@RequestParam String regionRows) {
        //批量保存记录
        this.regionService.batchSave(regionRows);
        return AjaxResult.createSuccessResult();
    }
}
