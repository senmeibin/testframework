package com.bpms.sys.service;

import com.bpms.core.consts.CmnConsts;
import com.bpms.sys.dao.LoginLogDao;
import com.bpms.sys.entity.ext.LoginLogExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录日志服务类
 */
@Service
public class LoginLogService extends SysBaseService<LoginLogExt> {
    @Autowired
    private LoginLogDao loginLogDao;

    @Override
    public LoginLogDao getDao() {
        return loginLogDao;
    }

    @Transactional
    public void saveError(String userUid, String userCd, String ip) {
        LoginLogExt loginLog = new LoginLogExt(userUid, userCd, null, ip, CmnConsts.LOGIN_TYPE_IN, CmnConsts.LOGIN_RESULT_FAIL);
        save(loginLog);
    }

    @Transactional
    public void saveSuccess(String userUid, String userCd, String userName, String ip) {
        LoginLogExt loginLog = new LoginLogExt(userUid, userCd, userName, ip, CmnConsts.LOGIN_TYPE_IN, CmnConsts.LOGIN_RESULT_SUCCESS);
        save(loginLog);
    }
}