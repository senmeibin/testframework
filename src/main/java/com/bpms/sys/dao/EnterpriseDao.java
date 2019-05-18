package com.bpms.sys.dao;

import com.bpms.core.dao.BaseDao;
import com.bpms.sys.entity.ext.EnterpriseExt;
import org.springframework.stereotype.Component;

/**
 * 企业信息数据访问类
 */
@Component(value = "SysEnterpriseDao")
public interface EnterpriseDao extends BaseDao<EnterpriseExt, String> {

}