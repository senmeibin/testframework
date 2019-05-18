package com.bpms.cmn.service;

import com.bpms.cmn.dao.SubwayStationDao;
import com.bpms.cmn.entity.ext.SubwayStationExt;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地铁站信息服务类
 */
@Service
public class SubwayStationService extends CmnBaseService<SubwayStationExt> {
    @Autowired
    private SubwayStationDao subwayStationDao;

    @Override
    public SubwayStationDao getDao() {
        return subwayStationDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(SubwayStationExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(SubwayStationExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SubwayStationExt saveBefore(SubwayStationExt entity) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.station_name", entity.getStationName());
        condition.put("main.subway_uid", entity.getSubwayUid());
        List<SubwayStationExt> list = this.search(condition);

        if (StringUtils.isEmpty(entity.getUid())) {
            if (CollectionUtils.isNotEmpty(list)) {
                throw new ServiceException(String.format("地铁线路【%s】下地铁站名【%s】重复。", entity.getSubwayNo(), entity.getStationName()));
            }
        }
        else {
            for (SubwayStationExt subwayStation : list) {
                if (!StringUtils.equals(subwayStation.getUid(), entity.getUid())) {
                    throw new ServiceException(String.format("地铁线路【%s】下地铁站名【%s】重复。", entity.getSubwayNo(), entity.getStationName()));
                }
            }
        }
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SubwayStationExt saveAfter(SubwayStationExt entity) {
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
    protected Boolean deleteBefore(SubwayStationExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(SubwayStationExt entity) {
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
     * 根据地铁线路信息取得对应的地铁站信息
     *
     * @param subwayUid 地铁线路UID
     * @return 地铁站信息集合
     */
    public List<SubwayStationExt> getSubwayStationInfoBySubwayUid(String subwayUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.subway_uid", subwayUid);
        return this.search(condition);
    }
}