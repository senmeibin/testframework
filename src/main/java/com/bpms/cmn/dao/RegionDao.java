package com.bpms.cmn.dao;

import com.bpms.cmn.entity.ext.RegionExt;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 地区信息数据访问类
 */
@Component("CmnRegionDao")
public interface RegionDao extends BaseDao<RegionExt, String> {
    /**
     * 查找所有下级地区
     *
     * @param uid 父级地区UID
     * @return 地区列表
     */
    @Query("SELECT e FROM CmnRegion e WHERE e.parentUid = ?1 AND e.recordStatus = " + CmnConsts.RECORD_STATUS_ACTIVE + " ORDER BY e.dispSeq ASC ")
    List<RegionExt> findByParentUid(String uid);
}