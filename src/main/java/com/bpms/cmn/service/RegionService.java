package com.bpms.cmn.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bpms.cmn.dao.RegionDao;
import com.bpms.cmn.entity.ext.CityExt;
import com.bpms.cmn.entity.ext.RegionExt;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 地区信息服务类
 */
@Service(value = "CmnRegionService")
public class RegionService extends CmnBaseService<RegionExt> {
    /**
     * 缓存KEY前缀
     */
    public static final String CACHE_PREFIX_KEY = "CACHE_CMN_REGION_LIST";

    /**
     * 缓存城市KEY前缀
     */
    private static final String CACHE_PREFIX_KEY_GRADE_TWO = "CACHE_CMN_REGION_LIST_GRADE_TWO";

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionDao regionDao;

    @Override
    public RegionDao getDao() {
        return regionDao;
    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected RegionExt saveBefore(RegionExt entity) {
        if (entity.getDispSeq() == null) {
            entity.setDispSeq(9999);
        }

        //新增场景
        if (StringUtils.isEmpty(entity.getUid())) {
            //UID设定
            entity.setUid(UniqueUtils.getUID());
        }

        //查询上级地区
        RegionExt parentRegion = this.findOne(entity.getParentUid());

        //更新地区路径
        if (parentRegion == null) {
            entity.setRegionPath("," + entity.getUid() + ",");
        }
        else {
            entity.setRegionPath(parentRegion.getRegionPath() + entity.getUid() + ",");
        }

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
        // 分割UID
        String[] uids = ids.split(",");

        for (String uid : uids) {
            RegionExt region = regionDao.findOne(uid);
            //地区不存在
            if (region == null) {
                throw new ServiceValidationException("您所操作的地区不存在。");
            }
            //下属地区存在的场合
            else if (regionDao.findByParentUid(uid).size() > 0) {
                throw new ServiceValidationException(String.format("您所操作的地区 [%s] 存在下级地区，无法删除。", region.getRegionName()));
            }
        }
        return true;
    }

    /**
     * 复写检索方法
     */
    @Override
    public Page<RegionExt> search(Class<RegionExt> cls, String sql, Map<String, Object> condition, PageRequest pageRequest) {
        //注：SQL中已添加默认排序字段，此处无需传递排序对象将pageRequest对象的Sort对象置空
        Page<RegionExt> page = super.search(cls, sql, condition, new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), null));

        List<RegionExt> regions = page.getContent();

        //空数据 直接返回
        if (regions.size() < 0) {
            return page;
        }
        //排序后集合
        List<RegionExt> result = Lists.newArrayList();

        //取得所有顶级地区
        List<RegionExt> topRegions = getTopRegions(regions);

        //生成树
        for (RegionExt first : topRegions) {
            //如果等于
            if (StringUtils.equals((String) SearchConditionUtils.getConditionValue(condition, "main.uid$ignore_search"), first.getUid())) {
                List<RegionExt> subRegions = Lists.newArrayList();
                result.add(first);
                result.addAll(findSubRegion(first.getUid(), regions, subRegions, first.getRegionGrade()));
            }
        }
        return new PageImpl<>(result, pageRequest, page.getTotalElements());
    }

    /**
     * 递归获取子地区信息
     *
     * @param parentUid  父地区ID
     * @param allRegions 所有地区集合
     * @param subRegions 子地区集合
     * @param level      层级
     * @return 子地区集合
     */
    private List<RegionExt> findSubRegion(String parentUid, List<RegionExt> allRegions, List<RegionExt> subRegions, Integer level) {
        for (RegionExt region : allRegions) {
            //遍历出父UID等于参数UID，添加进集合
            if (parentUid.equals(region.getParentUid())) {
                region.setRegionName(StringUtils.repeat("#", level * 2) + region.getRegionName());
                subRegions.add(region);
                //递归遍历下级部门
                findSubRegion(region.getUid(), allRegions, subRegions, region.getRegionGrade());
            }
        }
        return subRegions;
    }

    /**
     * 无上级地区的地区为顶级地区
     *
     * @param regions 地区集合
     * @return 所有顶级地区
     */
    private List<RegionExt> getTopRegions(List<RegionExt> regions) {
        List<RegionExt> result = Lists.newArrayList();

        //顶级节点的parentUid为0
        result.addAll(regions.stream().filter(region -> StringUtils.equals("0", region.getParentUid())).collect(Collectors.toList()));
        return result;
    }

    /**
     * 交换两条数据的排序字段
     */
    @Transactional
    public void switchRegionSeq(String sourceUid, String targetUid) {
        //取出数据
        RegionExt source = findOne(sourceUid);
        RegionExt target = findOne(targetUid);

        if (!Objects.equals(source.getParentUid(), target.getParentUid())) {
            throw new ServiceValidationException("非同级地区，无法移动。");
        }

        //重建指定地区下所有子地区的表示顺序
        reIndexDispSeq(source.getParentUid());

        source = findOne(sourceUid);
        target = findOne(targetUid);
        if (log.isDebugEnabled()) {
            log.debug("索引交换前 src {} : {}", source.getRegionName(), source.getDispSeq());
            log.debug("索引交换前 target {} : {}", target.getRegionName(), target.getDispSeq());
        }

        //交换src和target的dispSeq
        int temp = source.getDispSeq();
        source.setDispSeq(target.getDispSeq());
        target.setDispSeq(temp);
        if (log.isDebugEnabled()) {
            log.debug("索引交换后 src {} : {}", source.getRegionName(), source.getDispSeq());
            log.debug("索引交换后 target {} : {}", target.getRegionName(), target.getDispSeq());
        }

        //保存
        save(source);
        save(target);
    }

    /**
     * 重建指定地区下所有子地区的表示顺序
     *
     * @param parentUid 父地区UID
     */
    private void reIndexDispSeq(String parentUid) {
        //取得所有同级目录下的地区后重建表示顺序
        List<RegionExt> regionList = this.regionDao.findByParentUid(parentUid);

        for (int i = 0; i < regionList.size(); i++) {
            RegionExt region = regionList.get(i);
            region.setDispSeq(i * 10);
        }

        this.regionDao.save(regionList);
    }

    /**
     * 修改区域记录状态
     *
     * @param uid          区域UID
     * @param recordStatus 记录状态 1：启用/8：停用
     */
    public void updateRegionRecordStatus(String uid, String recordStatus) {
        String sql = String.format(this.getSQL("cmn/region/updateRegionRecordStatus"), recordStatus, "'%," + uid + ",%'");
        this.regionDao.executeUpdate(sql, null);
    }

    /**
     * 根据一组地址词组找出最相近的地区信息
     *
     * @param words 地址串
     * @return 最相近的地区信息
     */
    public RegionExt findRegionByWords(String words) {
        //去掉"省", "市", "县", "区" "-"
        words = StringUtils.replaceEach(words, new String[]{"省", "市", "县", "区", "-", " "}, new String[]{"", "", "", "", "", ""});

        if (StringUtils.length(words) < 2 || StringUtils.length(words) > 20) {
            String message = MessageFormat.format("参数不符合规范，长度区间{0}-{1}，{2}", 2, 20, words);
            log.warn(message);
            return new RegionExt();
        }

        int wordsLength = StringUtils.length(words);
        List<RegionExt> result = redisCacheManager.getList(RegionExt.class, CACHE_PREFIX_KEY + "_" + words);

        if (CollectionUtils.isEmpty(result)) {
            result = new ArrayList<>();
            //取出标准地址词典
            List<RegionExt> regionList = redisCacheManager.getList(RegionExt.class, CACHE_PREFIX_KEY);
            if (CollectionUtils.isEmpty(regionList)) {
                regionList = search(RegionExt.class, getSQL("cmn/region/getRegionList"), 20000);
                redisCacheManager.set(CACHE_PREFIX_KEY, regionList);
                if (log.isDebugEnabled()) {
                    log.debug("区域服务列表-数据库取得：当前取得地域词典{}条", regionList.size());
                }
            }
            else {
                if (log.isDebugEnabled()) {
                    log.debug("区域服务列表-缓存取得：当前取得地域词典{}条", regionList.size());
                }
            }

            //取词指针从开始前两个字开始
            /*
             * 目前算法为第一个指针始终保持0不动 第二个指针每次移动一个汉字 数据机械匹配法
             * 优化可排列组合多个词 同时去字典去查 找出最优结果
             */
            final int[] point = {0, 2};
            while (point[1] <= wordsLength + 1) {
                String key = StringUtils.substring(words, point[0], point[1]);
                for (RegionExt region : regionList) {
                    if (StringUtils.equals(StringUtils.replaceEach(region.getRegionFullName(),
                            new String[]{"省", "市", "县", "区"}, new String[]{"", "", "", ""}), key)) {
                        result.add(region);
                    }
                }
                point[1] = point[1] + 1;
            }

            redisCacheManager.set(CACHE_PREFIX_KEY + "_" + words, result);
        }

        RegionExt region = result.isEmpty() ? null : result.get(result.size() - 1);
        if (region != null) {
            //有城市没区域的信息 补全区域信息为当前城市未设定的区县
            if (StringUtils.isEmpty(region.getCityUid()) && StringUtils.isEmpty(region.getUid())) {
                Map<String, Object> condition = Maps.newHashMap();
                condition.put("parent_uid", region.getCityUid());
                condition.put("region_name", "未设定");
                List<RegionExt> regions = search(RegionExt.class, this.getSearchSQL(null), condition);
                if (regions.size() > 0) {
                    region.setUid(regions.get(0).getUid());
                    region.setRegionName(regions.get(0).getRegionName());
                }
            }
        }
        return result.isEmpty() ? null : result.get(result.size() - 1);
    }

    /**
     * 根据城市UID匹配2级区域CityUid
     *
     * @param cityUid 城市UID
     * @return 返回的cityUid或者空串
     */
    public String findRegionCityUidByCityUid(String cityUid) {
        String regionCityUid = redisCacheManager.get(String.class, CACHE_PREFIX_KEY_GRADE_TWO + "_" + cityUid);
        //缓存存在场合
        if (StringUtils.isNotEmpty(regionCityUid)) {
            return regionCityUid;
        }
        //查询数据库
        CityExt city = this.cityService.findOne(cityUid);
        if (city == null) {
            return StringUtils.EMPTY;
        }

        regionCityUid = findRegionCityUid(city.getCityName());
        //缓存城市
        redisCacheManager.set(CACHE_PREFIX_KEY_GRADE_TWO + "_" + cityUid, regionCityUid);

        return regionCityUid;
    }

    /**
     * 根据城市CD匹配2级区域CityUid
     *
     * @param cityCd 城市CD
     * @return 返回的cityUid或者空串
     */
    public String findRegionCityUidByCityCd(String cityCd) {
        String regionCityUid = redisCacheManager.get(String.class, CACHE_PREFIX_KEY_GRADE_TWO + "_" + cityCd);

        //缓存存在场合
        if (StringUtils.isNotEmpty(regionCityUid)) {
            return regionCityUid;
        }

        Map<String, Object> condition = new HashMap<>();
        condition.put("main.cityCd", cityCd);
        List<CityExt> cityList = cityService.search(CityExt.class, this.cityService.getSQL("cmn/City/search"), condition);

        if (CollectionUtils.isEmpty(cityList)) {
            return StringUtils.EMPTY;
        }

        CityExt city = cityList.get(0);

        regionCityUid = findRegionCityUid(city.getCityName());
        //缓存城市
        redisCacheManager.set(CACHE_PREFIX_KEY_GRADE_TWO + "_" + cityCd, regionCityUid);

        return regionCityUid;
    }

    /**
     * 根据城市名称匹配2级区域CityUid
     *
     * @param cityName 城市名称
     * @return 返回的cityUid
     */
    public String findRegionCityUid(String cityName) {
        //空的城市名字直接返回空
        cityName = StringUtils.trimToEmpty(cityName);
        if (StringUtils.isEmpty(cityName)) {
            return null;
        }

        //注：特例处理
        if (StringUtils.contains(cityName, "九龙坡")) {
            //返回重庆市的UID
            return "63";
        }

        //Region表中的CityUid
        String cityUid = StringUtils.EMPTY;
        Date startDate = DateUtils.getNowDate();
        try {
            cityUid = redisCacheManager.get(String.class, CACHE_PREFIX_KEY_GRADE_TWO + "_" + cityName);
            //按城市名称缓存城市UID存在的场合，直接返回
            if (StringUtils.isNotEmpty(cityUid)) {
                return cityUid;
            }

            List<RegionExt> regionList = redisCacheManager.getList(RegionExt.class, CACHE_PREFIX_KEY_GRADE_TWO);

            //缓存不存在的场合
            if (CollectionUtils.isEmpty(regionList)) {
                Map<String, Object> condition = Maps.newHashMap();
                condition.put("region_grade", "2");
                regionList = this.search(RegionExt.class, this.getSearchSQL(condition), condition);
                //去掉城市名称的后缀，用于比对匹配传入的城市名称
                for (RegionExt region : regionList) {
                    if (StringUtils.isNotEmpty(region.getRegionName())) {
                        //去掉城市名称的后缀
                        String shortCityName = StringUtils.trim(region.getRegionName());
                        if (shortCityName.length() >= 3) {
                            shortCityName = StringUtils.replaceEach(region.getRegionName(), new String[]{"市", "县", "地区", "自治区", "自治州", " "}, new String[]{"", "", "", "", "", ""});
                        }
                        if (shortCityName.endsWith("林区")) {
                            shortCityName = StringUtils.removeEnd(shortCityName, "区");
                        }
                        region.setCityName(shortCityName);
                        region.setCityUid(region.getUid());
                    }
                }
                redisCacheManager.set(CACHE_PREFIX_KEY_GRADE_TWO, regionList);
                if (log.isDebugEnabled()) {
                    log.debug("城市列表-数据库取得：当前取得城市词典{}条", regionList.size());
                }
            }
            else {
                if (log.isDebugEnabled()) {
                    log.debug("城市列表-缓存取得：当前取得城市词典{}条", regionList.size());
                }
            }

            for (RegionExt region : regionList) {
                //无效地域数据的场合
                if (StringUtils.isAnyEmpty(region.getCityName(), region.getCityUid())) {
                    continue;
                }

                //判断城市名称中是否包含Region表中的城市名称
                if (cityName.contains(region.getCityName())) {
                    cityUid = region.getCityUid();
                    break;
                }
            }

            for (RegionExt region : regionList) {
                if (StringUtils.isNotEmpty(cityUid)) {
                    break;
                }

                //无效地域数据的场合
                if (StringUtils.isAnyEmpty(region.getCityName(), region.getCityUid())) {
                    continue;
                }

                cityName = StringUtils.replaceEach(cityName, new String[]{" "}, new String[]{"-"});
                String[] names = cityName.split("-");
                for (String name : names) {
                    //判断Region表中的城市名称是否包含分割后的城市名称
                    if (region.getCityName().contains(name)) {
                        cityUid = region.getCityUid();
                        break;
                    }
                }
            }

            if (StringUtils.isNotEmpty(cityUid)) {
                //按城市名称缓存城市UID
                redisCacheManager.set(CACHE_PREFIX_KEY_GRADE_TWO + "_" + cityName, cityUid);
            }

            return cityUid;
        } finally {
            log.info(String.format("根据城市名称匹配2级城市UID[%s/%s](耗时：%s毫秒)", cityName, cityUid, (System.currentTimeMillis() - startDate.getTime())));
        }
    }

    /**
     * 批量保存
     *
     * @param regionRows 待修改记录行记录信息
     */
    public void batchSave(String regionRows) {
        //验证参数
        if (StringUtils.isEmpty(regionRows)) {
            throw new ServiceException("参数不完整，请稍后重试。");
        }
        List<RegionExt> regionList = JsonUtils.parseJSON(regionRows, new TypeReference<List<RegionExt>>() {
        });

        //保存区域信息并同步UDC
        for (RegionExt region : regionList) {
            this.save(region);
        }
    }

    /**
     * 根据条件取得地区信息
     *
     * @param regionGrade 地区等级(1：省+直辖市+特别行政区+自治区+台湾/2：城市/3：区县)
     * @param parentUids  上级地区列表 逗号分隔
     * @return 地区信息
     */
    public List<RegionExt> getAllRegion(Integer regionGrade, String parentUids) {
        //缓存
        String cacheKey = MessageFormat.format("CACHE_CMN_REGION_{0}_{1}", regionGrade, parentUids);
        List<RegionExt> result = redisCacheManager.getList(RegionExt.class, cacheKey);
        if (CollectionUtils.isEmpty(result)) {
            log.info("取得地区信息未使用缓存查询。缓存KEY：{}", cacheKey);
            result = Lists.newArrayList();
            Map<String, Object> condition = Maps.newHashMap();
            //用小于等于检索 用于带上上级地区
            condition.put("regionGrade$<=", regionGrade);
            if (StringUtils.isNotEmpty(parentUids)) {
                condition.put("main.parentUid$in", parentUids);
                Map<String, Object> map = Maps.newHashMap();
                map.put("main.uid$in", parentUids);
                //把上级地区也放入结果
                result.addAll(search(RegionExt.class, getSQL("cmn/region/searchValidRecord"), map));
            }
            result.addAll(search(RegionExt.class, getSQL("cmn/region/searchValidRecord"), condition));
            redisCacheManager.set(cacheKey, result);
        }
        return result;
    }
}