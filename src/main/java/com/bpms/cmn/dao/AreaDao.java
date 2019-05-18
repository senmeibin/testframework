package com.bpms.cmn.dao;

import com.bpms.cmn.entity.ext.AreaExt;
import com.bpms.core.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * 大区信息数据访问类
 */
@Component("CmnAreaDao")
public interface AreaDao extends BaseDao<AreaExt, String> {

}