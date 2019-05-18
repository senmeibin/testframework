package com.bpms.cmn.dao;

import com.bpms.cmn.entity.ext.MainCustomerExt;
import com.bpms.core.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * 主客户数据访问类
 */
@Component("CmnMainCustomerDao")
public interface MainCustomerDao extends BaseDao<MainCustomerExt, String> {
}