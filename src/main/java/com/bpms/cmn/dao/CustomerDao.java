package com.bpms.cmn.dao;

import com.bpms.cmn.entity.ext.CustomerExt;
import com.bpms.core.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * 合同客户数据访问类
 */
@Component("CmnCustomerDao")
public interface CustomerDao extends BaseDao<CustomerExt, String> {

}