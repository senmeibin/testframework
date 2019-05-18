package com.bpms.sys.service;

import com.bpms.core.utils.DesUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.dao.UserInfoDao;
import com.bpms.sys.entity.ext.UserInfoExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户扩展服务类
 */
@Service
public class UserInfoService extends SysBaseService<UserInfoExt> {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfoDao getDao() {
        return userInfoDao;
    }

    /**
     * 取得邮箱密码
     *
     * @param uid   用户UID
     * @param isDes 是否DES加密
     * @return 邮箱密码
     */
    public String getMailPassword(String uid, boolean isDes) {
        UserInfoExt userInfo = this.findOne(uid);

        if (userInfo == null) {
            return StringUtils.EMPTY;
        }

        if (isDes) {
            return userInfo.getMailPassword();
        }
        else {
            return DesUtils.decrypt(userInfo.getMailPassword());
        }
    }

    /**
     * 取得指定UID的详细信息
     *
     * @param pkValue 主键值
     * @return 实体详细
     */
    @Override
    public UserInfoExt getDetail(Object pkValue) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.uid", pkValue);
        //根据查询条件获取List
        List<UserInfoExt> list = this.search(this.getSearchSQL(condition), condition);
        if (list.size() > 0) {
            UserInfoExt userInfo = list.get(0);

            userInfo.setMailPassword(DesUtils.decrypt(userInfo.getMailPassword()));

            userInfo.setExtSystemPassword1(DesUtils.decrypt(userInfo.getExtSystemPassword1()));
            userInfo.setExtSystemPassword2(DesUtils.decrypt(userInfo.getExtSystemPassword2()));
            userInfo.setExtSystemPassword3(DesUtils.decrypt(userInfo.getExtSystemPassword3()));
            userInfo.setExtSystemPassword4(DesUtils.decrypt(userInfo.getExtSystemPassword4()));
            userInfo.setExtSystemPassword5(DesUtils.decrypt(userInfo.getExtSystemPassword5()));

            return userInfo;
        }
        else {
            //补全用户扩展信息
            UserInfoExt userInfo = new UserInfoExt();
            userInfo.setUid((String) pkValue);
            return userInfo;
        }
    }
}