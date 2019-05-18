package com.bpms.sys.service;

import com.google.common.collect.Maps;
import com.bpms.sys.consts.SysConsts;
import com.bpms.sys.dao.PermissionDao;
import com.bpms.sys.entity.ext.PermissionExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 权限服务类
 */
@Service
public class PermissionService extends SysBaseService<PermissionExt> {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PermissionDao getDao() {
        return permissionDao;
    }

    public List<PermissionExt> findByRole(String roleUid) {
        Map<String, Object> searchCondition = Maps.newHashMap();
        searchCondition.put("main.roleUid", roleUid);
        return permissionDao.search(PermissionExt.class, getSQL("/sys/permission/findByRole"), searchCondition);
    }

    /**
     * 获取所有权限列表
     */
    public List<PermissionExt> findByAppCode(String appCode) {
        Map<String, Object> searchCondition = Maps.newHashMap();
        searchCondition.put("appCode", appCode);
        return search(PermissionExt.class, getSearchSQL(null), searchCondition);
    }

    /**
     * 获取所有权限列表
     */
    List<PermissionExt> findByTypeCd(String typeCd) {
        Map<String, Object> searchCondition = Maps.newHashMap();
        searchCondition.put("typeCd", typeCd);
        return search(PermissionExt.class, getSearchSQL(null), searchCondition);
    }

    public List<PermissionExt> findDateAccess() {
        return findByTypeCd(SysConsts.PERMISSION_TYPE_DATA);
    }

    public List<PermissionExt> findResourceAccess() {
        return findByTypeCd(SysConsts.PERMISSION_TYPE_RESOURCE);
    }
}