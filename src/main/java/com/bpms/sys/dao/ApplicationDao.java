package com.bpms.sys.dao;

import com.bpms.core.dao.BaseDao;
import com.bpms.sys.entity.ext.ApplicationExt;

/**
 * 应用模块数据访问类
 */
public interface ApplicationDao extends BaseDao<ApplicationExt, String> {

    ApplicationExt findByAppCode(String appCode);
}