package com.bpms.sys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bpms.sys.dao.UserRoleDao;
import com.bpms.sys.entity.ext.UserRoleExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户角色服务类
 */
@Service
public class UserRoleService extends SysBaseService<UserRoleExt> {
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRoleDao getDao() {
        return userRoleDao;
    }

    /**
     * 取得指定角色下的所属用户列表
     *
     * @param roleUid 角色UID
     * @return 所属用户列表
     */
    public List<UserRoleExt> findByRoleUid(String roleUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.roleUid", roleUid);
        return this.search(UserRoleExt.class, this.getSearchSQL(condition), condition);
    }

    /**
     * 取得指定角色下的所属用户UID集合
     *
     * @param roleUid 角色UID
     * @return 所属用户UID集合
     */
    public List<String> findByRole(String roleUid) {
        Map<String, Object> searchCondition = Maps.newHashMap();
        searchCondition.put("main.roleUid", roleUid);
        return this.search(String.class, getSQL("/sys/UserRole/findByRole"), searchCondition);
    }
}