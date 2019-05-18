package com.bpms.cmn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bpms.cmn.dao.SubwayDao;
import com.bpms.cmn.entity.ext.RegionExt;
import com.bpms.cmn.entity.ext.SubwayExt;
import com.bpms.cmn.entity.ext.SubwayStationExt;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.HttpUtils;
import com.bpms.core.utils.JsonUtils;
import com.bpms.core.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * 地铁线路信息服务类
 */
@Service
public class SubwayService extends CmnBaseService<SubwayExt> {
    @Autowired
    private SubwayDao subwayDao;

    @Autowired
    private RegionService regionService;

    @Autowired
    private SubwayStationService subwayStationService;

    @Override
    public SubwayDao getDao() {
        return subwayDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(SubwayExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(SubwayExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SubwayExt saveBefore(SubwayExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SubwayExt saveAfter(SubwayExt entity) {
        return entity;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(String ids) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(String ids) {
        return true;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(SubwayExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(SubwayExt entity) {
        return true;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        return true;
    }

    /**
     * 根据城市UID取得对应的地铁线路信息
     *
     * @param cityUid 城市UID
     * @return 地铁线路信息集合
     */
    public List<SubwayExt> getSubwayInfoByCityUid(String cityUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.city_uid", cityUid);
        return this.search(condition, new Sort(Sort.Direction.ASC, "dispSeq"));
    }

    /**
     * 取得地铁线路信息
     *
     * @return 处理结果消息
     */
    public String initSubwayStationInfo() {
        String result = "地铁线路信息更新成功。";
        try {
            if (StringUtils.equals(this.parameterService.getValue(AppCodeConsts.APP_COMMON, "UPDATE_OR_INSERT_SUBWAY_STATION_INFO", "NO"), "NO")) {
                result = String.format("系统参数设置不接受地铁线路和地铁站信息的更新或者新增，如需更新或者新增，请调整参数【%s】配置为YES。", "UPDATE_OR_INSERT_SUBWAY_STATION_INFO");
                log.info(result);
                return result;
            }
            //取出所有的城市
            Map<String, Object> map = new HashMap<>();
            map.put("main.region_grade", 2);
            List<RegionExt> regionList = this.regionService.search(map);

            String subwayInfo = HttpUtils.doPost(this.parameterService.getValue(AppCodeConsts.APP_COMMON, "SYNC_API_SUBWAY_URL"), new HashMap<String, Object>());
            if (StringUtils.isEmpty(subwayInfo)) {
                result = "系统参数SYNC_API_SUBWAY_URL未设置。";
                log.info(result);
                return result;
            }

            JSONArray objectCity = new JSONArray(subwayInfo);
            Iterator<Object> itCity = objectCity.iterator();

            while (itCity.hasNext()) {
                JSONObject object = (JSONObject) itCity.next();
                Iterator keys = object.keys();
                while (keys.hasNext()) {
                    //xx城市
                    String cityKey = keys.next().toString();
                    log.info("城市：" + cityKey);
                    Object value = object.get(cityKey);
                    JSONArray objectSubway = new JSONArray(value.toString());
                    Iterator<Object> itSubway = objectSubway.iterator();
                    int subwaySeq = 1;

                    while (itSubway.hasNext()) {
                        JSONObject objectStation = (JSONObject) itSubway.next();
                        Iterator keySub = objectStation.keys();

                        while (keySub.hasNext()) {
                            //xx号线
                            SubwayExt subway = null;
                            String subwayName = keySub.next().toString();
                            log.info("线路：" + subwayName);
                            int stationSeq = 1;
                            Object station = objectStation.get(subwayName);
                            JSONArray stationArr = new JSONArray(station.toString());
                            for (Object stationName : stationArr) {
                                //xxx地铁站
                                log.info("地铁站名：" + stationName);
                                SubwayStationExt subwayStation = null;
                                //根据地铁线路信息
                                for (RegionExt region : regionList) {
                                    String regionName = region.getRegionName();
                                    if (regionName.length() > 2 && regionName.contains("市")) {
                                        regionName = regionName.substring(0, regionName.length() - 1);
                                    }
                                    if (StringUtils.equals(regionName, cityKey)) {
                                        if (subway == null) {
                                            map.clear();
                                            map.put("main.subway_no", subwayName);
                                            map.put("region_city.region_name", region.getRegionName());
                                            List<SubwayExt> subwayList = this.search(map);
                                            if (CollectionUtils.isNotEmpty(subwayList)) {
                                                subway = subwayList.get(0);
                                                log.info(cityKey + "地铁线路信息存在[忽略]");
                                            }
                                            else {
                                                subway = new SubwayExt();
                                                subway.setRemark(cityKey + "地铁线路信息维护[新增]");
                                                subway.setCityUid(region.getUid());
                                                subway.setSubwayNo(subwayName);
                                                subway.setDispSeq(subwaySeq);
                                                subwaySeq++;
                                                this.save(subway);
                                                log.info(cityKey + "地铁线路信息维护[新增]");
                                            }
                                        }
                                        if (subwayStation == null) {
                                            map.clear();
                                            map.put("main.station_name", stationName.toString());
                                            map.put("main.subway_uid", subway.getUid());
                                            List<SubwayStationExt> subwayList = this.subwayStationService.search(map);
                                            if (CollectionUtils.isNotEmpty(subwayList)) {
                                                subwayStation = subwayList.get(0);
                                                log.info(cityKey + subwayName + "地铁站名维护存在[忽略]");
                                            }
                                            else {
                                                subwayStation = new SubwayStationExt();
                                                subwayStation.setRemark(cityKey + subwayName + "地铁站名维护[新增]");
                                                subwayStation.setSubwayNo(subwayName);
                                                subwayStation.setStationName(stationName.toString());
                                                subwayStation.setSubwayUid(subway.getUid());
                                                subwayStation.setDispSeq(stationSeq);
                                                stationSeq++;
                                                this.subwayStationService.save(subwayStation);
                                                log.info(cityKey + subwayName + "地铁站名维护[新增]");
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "地铁线路信息取得失败：" + e.getMessage();
            log.error("地铁线路信息取得失败", e);
        }
        return result;
    }

    /**
     * 取得所有地铁站点
     *
     * @param cityNameList 城市名称列表(指定城市名称列表，为请的情况下检索以数据库中维护的数据为准)
     * @return 城市集合的地铁站
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, List<String>>> getAllSubwayMap(String cityNameList) {

        String cacheKey = MessageFormat.format("CACHE_CMN_SUBWAY_STATION_{0}", cityNameList);
        String subwayMap = redisCacheManager.get(String.class, cacheKey);
        Map<String, Map<String, List<String>>> cityMap;
        if (subwayMap != null) {
            cityMap = JsonUtils.parseJSON(subwayMap, LinkedHashMap.class);
        }
        else {
            //城市map
            cityMap = Maps.newLinkedHashMap();
            Map<String, Object> condition = new HashMap<>();
            if (StringUtils.isNotEmpty(cityNameList)) {
                condition.put("city.region_name$in", cityNameList);
            }
            List<SubwayStationExt> stationList = search(SubwayStationExt.class, getSQL("cmn/Subway/searchSubwayStation"), condition);
            for (SubwayStationExt station : stationList) {
                if (!cityMap.containsKey(station.getCityName())) {
                    cityMap.put(station.getCityName(), Maps.newLinkedHashMap());
                }
                //地铁
                Map<String, List<String>> subwayNoMap = cityMap.get(station.getCityName());
                if (!subwayNoMap.containsKey(station.getSubwayNo())) {
                    subwayNoMap.put(station.getSubwayNo(), Lists.newArrayList());
                }
                subwayNoMap.get(station.getSubwayNo()).add(station.getStationName());
            }
            try {
                redisCacheManager.set(cacheKey, JsonUtils.toJSON(cityMap, false));
            } catch (JsonProcessingException e) {
                log.warn("保存地铁站点缓存发生JSON转换异常。");
            }
        }
        return cityMap;
    }
}