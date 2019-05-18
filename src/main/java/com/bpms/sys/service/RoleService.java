package com.bpms.sys.service;

import com.google.common.collect.Maps;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.EntityUtils;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.dao.RoleDao;
import com.bpms.sys.dao.UserDao;
import com.bpms.sys.dao.UserRoleDao;
import com.bpms.sys.entity.ext.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色服务类
 */
@Service
public class RoleService extends SysBaseService<RoleExt> {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public RoleDao getDao() {
        return roleDao;
    }

    @Override
    public Page<RoleExt> search(Class<RoleExt> cls, String sql, Map<String, Object> condition, PageRequest pageRequest) {
        Page<RoleExt> result = super.search(cls, sql, condition, pageRequest);


        for (RoleExt role : result) {
            //补全角色权限
            role.setPermissionList(permissionService.findByRole(role.getUid()));

            //补全用户列表
            role.setUserRoleList(userRoleService.findByRoleUid(role.getUid()));
        }

        return result;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(String ids) {
        // 分割UID
        String[] uids = ids.split(",");

        for (String uid : uids) {
            String roleName = "";
            List<UserRoleExt> userRoles = userRoleService.findByRoleUid(uid);
            if (userRoles != null && userRoles.size() > 0) {
                List userName = new ArrayList();
                for (UserRoleExt userRole : userRoles) {
                    userName.add(userRole.getUserName());
                    roleName = userRole.getRoleName();
                }
                throw new ServiceValidationException(String.format("您所操作的角色 [%s] 存在使用人员，无法删除。<br/>%s", roleName, StringUtils.join(userName.toArray(), "，")));
            }
        }
        return true;
    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected RoleExt saveBefore(RoleExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected RoleExt saveAfter(RoleExt entity) {
        //权限列表存在的场合
        if (entity.getPermissionList() != null) {
            //根据角色UID删除所有权限关联数据
            this.rolePermissionService.delete("role_uid", entity.getUid());

            //循环写入权限列表
            for (PermissionExt permission : entity.getPermissionList()) {
                RolePermissionExt rolePermission = new RolePermissionExt();

                //角色UID
                rolePermission.setRoleUid(entity.getUid());

                //权限UID
                rolePermission.setPermissionUid(permission.getUid());

                rolePermission.setRecordStatus(1);

                this.rolePermissionService.save(rolePermission);
            }
        }

        //删除原有的用户角色关联信息
        this.userRoleService.delete("role_uid", entity.getUid());

        //用户非空场合
        if (StringUtils.isNotEmpty(entity.getUserUids())) {
            for (String userUid : entity.getUserUids().split(",")) {
                //第一个是请选择 或者全部的跳过
                if (StringUtils.isEmpty(userUid)) {
                    continue;
                }
                UserRoleExt userRole = new UserRoleExt();
                //角色UID
                userRole.setRoleUid(entity.getUid());
                //用户UID
                userRole.setUserUid(userUid);
                this.userRoleService.save(userRole);
            }
        }
        return super.saveAfter(entity);
    }

    /**
     * 根据用户UID取得用户所属角色一览
     *
     * @param userUid 用户UID
     * @return 角色一览
     */
    public List<RoleExt> findByUserUid(String userUid) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("userUid", userUid);
        return search(RoleExt.class, getSQL("sys/role/findByUserUid"), condition);
    }

    @Override
    protected Boolean deleteAfter(String uid) {
        rolePermissionService.delete("role_uid", uid);
        return super.deleteAfter(uid);
    }

    /**
     * 取得指定排序条件的角色一览
     *
     * @return 角色一览
     */
    public List<RoleExt> getRoleList() {
        return this.getRoleList(null);
    }

    /**
     * 取得指定排序条件的角色一览
     *
     * @param appCodes 应用编码（多个编号使用逗号分隔）
     * @return 角色一览
     */
    public List<RoleExt> getRoleList(String appCodes) {
        Sort sort = new Sort(Sort.Direction.ASC, "app.dispSeq,appCode,dispSeq,roleCode");
        PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE, sort);
        Map<String, Object> condition = new HashMap<>();
        if (StringUtils.isNotEmpty(appCodes)) {
            condition.put("main.app_code$in", appCodes);
        }
        return search(RoleExt.class, getSearchSQL(condition), condition, pageRequest).getContent();
    }

    /**
     * 取所有角色信息
     *
     * @return 按APP_CODE分组后的角色列表
     */
    public List<RoleExt> getAllRoleList() {
        List<RoleExt> list = new ArrayList<>();
        List<RoleExt> roleList = this.getRoleList();
        Map<String, String> appMap = new HashMap<>();
        //处理父级角色
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (RoleExt role : roleList) {
                role.setParentRoleUid(role.getAppCode());
                //还没包含就加上本appcode
                if (!appMap.containsKey(role.getAppCode())) {
                    appMap.put(role.getAppCode(), role.getAppName());
                }
            }
        }
        for (String appCode : appMap.keySet()) {
            RoleExt roleExt = new RoleExt();
            roleExt.setUid(appCode);
            roleExt.setRoleName(appMap.get(appCode));
            list.add(roleExt);
        }

        //加入所有子节点
        list.addAll(roleList);
        return list;
    }

    /**
     * 接口数据同步处理
     *
     * @param tableName   表名称
     * @param entityClass 实体类名
     * @return true:同步成功/false:同步失败
     */
    @Transactional
    @Override
    public boolean syncData(String tableName, Class entityClass) {
        boolean result = super.syncData(tableName, entityClass);

        //角色基础信息同步成功的场合，同步用户角色关系数据
        if (result) {
            String resultUserRole = this.getTableList("sys_user_role");
            AjaxResult ajaxResult = JsonUtils.parseJSON(resultUserRole, AjaxResult.class);

            if (ajaxResult.isSuccess()) {
                List<UserRoleExt> list = EntityUtils.getEntityListByMap((List<HashMap>) ajaxResult.getContent(), UserRoleExt.class);
                if (list == null || list.size() == 0) {
                    return true;
                }
                for (UserRoleExt userRole : list) {
                    //数据覆盖保存
                    this.userRoleDao.save(userRole);
                }
                return true;
            }
            log.error("角色信息同步接口调用失败,失败描述：" + ajaxResult.getMessage());
            return false;
        }
        else {
            return result;
        }
    }

    /**
     * 用户下拉选择专用方法
     *
     * @param condition 检索条件
     * @return 用户列表
     */
    public List<DropdownEntity> getUserSelect(Map<String, Object> condition) {
        List<UserExt> list = this.userDao.search(UserExt.class, this.getSQL("cmn/user/getUserSelect"), condition);
        List<DropdownEntity> dropdownEntityList = new ArrayList<>();
        for (UserExt userExt : list) {
            int recordStatus = userExt.getRecordStatus();
            String userStatus = StringUtils.EMPTY;
            //如果是停用的人， 名字后面加上停用
            if (recordStatus == 8) {
                userStatus = "【停用】";
            }
            DropdownEntity dropdownEntity = new DropdownEntity();
            dropdownEntity.setSubCd(userExt.getUid());
            dropdownEntity.setSubName(userExt.getUserName() + userStatus + "-" + userExt.getDeptFullName());
            dropdownEntityList.add(dropdownEntity);
        }

        return dropdownEntityList;
    }
}