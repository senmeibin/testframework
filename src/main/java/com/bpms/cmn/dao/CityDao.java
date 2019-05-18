package com.bpms.cmn.dao;

import com.bpms.cmn.entity.ext.CityExt;
import com.bpms.core.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * 城市信息数据访问类
 */
@Component("CmnCityDao")
public interface CityDao extends BaseDao<CityExt, String> {

}