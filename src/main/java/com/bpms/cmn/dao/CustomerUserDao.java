package com.bpms.cmn.dao;

import com.bpms.core.dao.BaseDao;
import com.bpms.cmn.entity.ext.CustomerUserExt;
import org.springframework.stereotype.Component;

/**
 * 客户用户所属数据访问类
 */
@Component(value = "CmnCustomerUserDao")
public interface CustomerUserDao extends BaseDao<CustomerUserExt, String> {

}