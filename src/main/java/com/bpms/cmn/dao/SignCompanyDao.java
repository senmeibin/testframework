package com.bpms.cmn.dao;

import com.bpms.cmn.entity.ext.SignCompanyExt;
import com.bpms.core.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * 签单公司数据访问类
 */
@Component("CmnSignCompanyDao")
public interface SignCompanyDao extends BaseDao<SignCompanyExt, String> {

}