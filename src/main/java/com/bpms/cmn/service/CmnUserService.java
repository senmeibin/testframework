package com.bpms.cmn.service;

import com.google.common.collect.Maps;
import com.bpms.core.dao.BaseDao;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.utils.AccessUtils;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.sys.dao.UserDao;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.DeptService;
import com.bpms.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 共通用户服务类
 */
@Service(value = "CmnUserService")
public class CmnUserService extends CmnBaseService<UserExt> {
    @Autowired
    protected UserDao userDao;

    @Autowired
    protected DeptService deptService;

    @Autowired
    protected UserService userService;

    @Override
    public BaseDao getDao() {
        return this.userDao;
    }

    /**
     * 用户下拉选择专用方法
     *
     * @return 用户列表
     */
    public <T> List<T> getUserSelect(Class<T> cls) {
        return this.getUserSelect(cls, false, null, null);
    }

    /**
     * 用户下拉选择专用方法
     *
     * @param condition 检索条件
     * @return 用户列表
     */
    public <T> List<T> getUserSelect(Class<T> cls, Map<String, Object> condition) {
        return this.getUserSelect(cls, false, null, condition);
    }

    /**
     * 用户下拉选择专用方法
     *
     * @param cls 返回class
     * @param useDataRange 是否使用数据范围管理权限
     * @param roleCode     角色编号
     * @return 用户列表
     */
    public <T> List<T> getUserSelect(Class<T> cls, boolean useDataRange, String roleCode) {
        return this.getUserSelect(cls, useDataRange, roleCode, null);
    }

    /**
     * 用户下拉选择专用方法
     *
     * @param useDataRange 是否使用数据范围管理权限
     * @param roleCode     角色编号
     * @param condition    检索条件
     * @return 用户列表
     */
    public <T> List<T> getUserSelect(Class<T> cls, boolean useDataRange, String roleCode, Map<String, Object> condition) {
        if (condition == null) {
            condition = new HashMap<>();
        }
        //使用数据范围管理权限的场合
        if (useDataRange) {
            //系统管理员的场合 || 集团数据权限的场合【查看所有用户】
            if (AccessUtils.isSystemManagement() || AccessUtils.isItSupport() || AccessUtils.isGroupDataRange()) {
            }
            //大区数据权限的场合【查看大区用户】
            else if (AccessUtils.isAreaDataRange()) {
                //取得大区的下属分公司的部门
                Map<String, Object> map = new HashMap<>();
                map.put("main.parentDeptUid", getCurrentUser().getBelongAreaDeptUid());
                List<DeptExt> subDeptList = deptService.search(DeptExt.class, map);
                List<String> subDeptUidList = new ArrayList<>();
                for (DeptExt dept : subDeptList) {
                    subDeptUidList.addAll(deptService.getSubDeptList(dept.getUid()));
                }

                //管辖分公司UID
                String deptManageUids = getCurrentUser().getDeptManageUids();
                if (StringUtils.isNotEmpty(deptManageUids)) {
                    //获取管辖分公司的部门
                    for (String companyUid : deptManageUids.split(",")) {
                        subDeptUidList.addAll(deptService.getSubDeptList(companyUid));
                    }
                }

                condition.put("users.dept_uid$in", subDeptUidList);
            }
            //分公司数据权限的场合【查看分公司用户】
            else if (AccessUtils.isCompanyDataRange()) {
                //获取所属分公司下级部门【包含自部门】
                List<String> subDeptUidList = deptService.getSubDeptList(this.getCurrentUser().getBelongCompanyDeptUid());
                //管辖分公司UID
                String deptManageUids = getCurrentUser().getDeptManageUids();
                if (StringUtils.isNotEmpty(deptManageUids)) {
                    //获取管辖分公司的部门
                    for (String companyUid : deptManageUids.split(",")) {
                        subDeptUidList.addAll(deptService.getSubDeptList(companyUid));
                    }
                }
                condition.put("users.dept_uid$in", subDeptUidList);
            }
            //部门数据权限的场合【查看部门用户】
            else if (AccessUtils.isDeptDataRange()) {
                String parentDeptUid = this.getCurrentUser().getDeptUid();
                //获取下级部门【包含自部门】
                List<String> subDeptList = deptService.getSubDeptList(parentDeptUid);
                condition.put("users.dept_uid$in", subDeptList);
            }
            //其他数据权限的场合【查看自己】
            else {
                condition.put("users.uid", this.getCurrentUser().getUserUid());
            }
        }

        List<UserExt> list;
        //获取排序字段(类似 ORDER BY aliasTable.Column1 DESC) ，多个排序按sql 规范指定
        String orderBy = (String) SearchConditionUtils.getConditionValue(condition, "orderBy");
        //角色指定的场合
        if (StringUtils.isNotEmpty(roleCode)) {
            //多角色情况下用IN
            if (roleCode.contains(",")) {
                condition.put("role.role_code$in", roleCode);
            }
            else {
                condition.put("role.role_code", roleCode);
            }
            String sql = this.getSQL("cmn/user/getUserSelectByRole");
            if (StringUtils.isNotEmpty(orderBy)) {
                sql += " " + orderBy;
            }
            list = this.userDao.search(UserExt.class, sql, condition);
        }
        else {
            //只查询内部员工 排除系统账号
            condition.put("users.reg_system$=", 0);
            String sql = this.getSQL("cmn/user/getUserSelect");
            if (StringUtils.isNotEmpty(orderBy)) {
                sql += " " + orderBy;
            }
            list = this.userDao.search(UserExt.class, sql, condition);
        }

        //用户下拉选择字典数据结构的场合
        if (cls == DropdownEntity.class) {
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

            return (List<T>) dropdownEntityList;
        }
        else {
            for (UserExt userExt : list) {
                int recordStatus = userExt.getRecordStatus();
                String userStatus = StringUtils.EMPTY;
                //如果是停用的人， 名字后面加上停用
                if (recordStatus == 8) {
                    userStatus = "【停用】";
                }
                userExt.setUserName(userExt.getUserName() + userStatus);
            }
            return (List<T>) list;
        }
    }

    /**
     * 获取所有在职用户
     *
     * @return 用户列表
     */
    public List<UserExt> getAllServingUser() {
        Map condition = new HashMap<>();
        //只查询内部员工和外部员工 排除系统账号
        condition.put("users.reg_system$in", "0,2");
        condition.put("users.recordStatus", "1");
        //读取所有的人员
        return this.userService.getDao().search(UserExt.class, this.getSQL("cmn/user/getUserSelect"), condition);
    }

    /**
     * 根据特定用户，得到他所在部门(看参数设定）的特定权限 特定职位的在职用户
     *
     * @param roleCodes    权限 （复数时， 逗号分隔）
     * @param userPosition 用户职务
     * @param userUid      用户UID
     * @param deptClass    部门登记（0：集团本部/1：大区/2：分公司/3：部门）
     * @return 用户列表
     */
    public List<UserExt> getUsersByRoleAndPosition(String roleCodes, String userPosition, String userUid, String deptClass) {
        List<UserExt> userList = new ArrayList<UserExt>();
        Map<String, Object> condition = new HashMap<>();
        condition.put("users.recordStatus", "1");
        StringBuilder sb = new StringBuilder();
        //指定权限不为空时
        if (StringUtils.isNotEmpty(roleCodes)) {
            String[] roleCodeArray = roleCodes.split(",");
            for (int i = 0; i < roleCodeArray.length; i++) {
                sb.append(" AND LOCATE('" + roleCodeArray[i] + "', user_roles.role_codes) > 0");
            }
        }
        //指定职位不为空时
        if (StringUtils.isNotEmpty(userPosition)) {
            condition.put("sys_position.position_name", userPosition);
        }
        UserExt userExt;
        if (StringUtils.isNotEmpty(userUid)) {
            userExt = this.userService.findOne(userUid);
            if (userExt == null) {
                return userList;
            }
            //初始化指定用户的组织信息
            this.userService.initDeptInfo(userExt);
        }
        else {
            return userList;
        }

        String deptUid = "";
        //得大区有特定权限或者职位的人 (如果是 0 ， 集团class ， 就不设定条件）
        if (StringUtils.equals("1", deptClass)) {
            deptUid = userExt.getBelongAreaDept().getUid();
        }
        //分公司的
        else if (StringUtils.equals("2", deptClass)) {
            deptUid = userExt.getBelongCompanyDept().getUid();
        }
        //部门的
        else if (StringUtils.equals("3", deptClass)) {
            deptUid = userExt.getDeptUid();
        }
        if (StringUtils.isNotEmpty(deptUid)) {
            //获取下级部门【包含自部门】
            List<String> subDeptList = this.deptService.getSubDeptList(deptUid);
            condition.put("users.dept_uid$in", subDeptList);
        }

        //获取查询字符串， 设定特殊条件
        String sql = this.getSQL("/cmn/user/getUsersByRoleAndPosition");
        sql = String.format(sql, sb.toString());
        userList = this.userDao.search(UserExt.class, sql, condition);
        return userList;
    }

    /**
     * 角色对应的用户树形选择方法（按组织结构展示)
     *
     * @param condition           检索条件
     * @param roleCode            角色编码（多角色逗号分隔）
     * @param ignoreDataRangeFlag 是否忽略数据权限过滤 true：忽略，false：不忽略
     * @return 用户列表
     */
    public List<UserExt> getUserByRole(Map<String, Object> condition, String roleCode, Boolean ignoreDataRangeFlag) {
        return this.getUserSelect(UserExt.class, !ignoreDataRangeFlag, roleCode, condition);
    }

    /**
     * 根据角色获取对应的用户
     *
     * @param condition 检索条件
     * @param roleCode  角色编码（多角色逗号分隔）
     * @return 用户列表
     */
    public List<DropdownEntity> getDropdownUserByRole(Map<String, Object> condition, String roleCode) {
        return this.getUserSelect(DropdownEntity.class, true, roleCode, condition);
    }

    /**
     * 递归获取上级组织
     *
     * @param allDepts 所有组织
     * @param deptUid  部门UID
     * @return 上级部门Map集合
     */
    public Map<String, DeptExt> getParentDeptList(List<DeptExt> allDepts, String deptUid, Map<String, DeptExt> map) {
        for (DeptExt dept : allDepts) {
            //遍历出UID等于参数UID，添加进集合
            if (deptUid.equals(dept.getUid())) {
                //map中不存在 放入map中
                if (!map.containsKey(dept.getUid())) {
                    map.put(dept.getUid(), dept);
                }
                //存在上级部门 && 父部门和自身部门不相同的场合
                if (StringUtils.isNotEmpty(dept.getParentDeptUid()) && !StringUtils.equals(dept.getUid(), dept.getParentDeptUid())) {
                    //递归遍历出上级部门
                    getParentDeptList(allDepts, dept.getParentDeptUid(), map);
                }
            }
        }
        return map;
    }

    /**
     * 指定角色的用户树形选择专用方法（按组织结构展示)
     *
     * @return 用户列表
     */
    public List<DeptExt> getUserTreeByRole(String roleCode) {
        //获取指定角色的人员列表
        List<UserExt> userList = this.getUserSelect(UserExt.class, true, roleCode, null);

        //获取所有部门信息
        List<DeptExt> allDeptList = this.deptService.search(DeptExt.class, this.deptService.getSearchSQL(null));

        Map<String, DeptExt> map = new HashMap<>();
        for (UserExt user : userList) {
            //获取用户所属组织以及上级组织
            this.getParentDeptList(allDeptList, user.getDeptUid(), map);
            //将用户拼装成符合组织树形的结构
            this.setUserDeptToMap(user, map);
        }

        //map转list
        List<DeptExt> deptList = new ArrayList<>();
        this.convertMapToDeptList(map, deptList);
        return deptList;
    }

    /**
     * 获取所有的有效用户
     *
     * @param roleCode 角色（多个逗号分隔）
     * @return 用户列表
     */
    public List<DeptExt> getValidUserTreeByRole(String roleCode) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.record_status", "1");
        //获取所有的有效用户
        List<UserExt> userList = this.search(condition);

        //获取所有部门信息
        List<DeptExt> allDeptList = this.deptService.search(DeptExt.class, this.deptService.getSearchSQL(condition));

        Map<String, DeptExt> map = new HashMap<>();
        for (UserExt user : userList) {
            //获取用户所属组织以及上级组织
            this.getParentDeptList(allDeptList, user.getDeptUid(), map);
            //将用户拼装成符合组织树形的结构
            this.setUserDeptToMap(user, map);
        }

        //map转list
        List<DeptExt> deptList = new ArrayList<>();
        this.convertMapToDeptList(map, deptList);
        return deptList;
    }

    /**
     * 角色对应的用户树形选择方法（按组织结构展示)
     *
     * @param roleCode            角色编码
     * @param ignoreDataRangeFlag 是否数据权限过滤 true：不需要，false：需要
     * @return 用户列表
     */
    public List<DeptExt> getUserTreeByRole(String roleCode, Boolean ignoreDataRangeFlag) {
        return getUserTreeByRole(roleCode, ignoreDataRangeFlag, new HashMap<>());
    }

    /**
     * 角色对应的用户树形选择方法（按组织结构展示)
     *
     * @param roleCode            角色编码（多角色逗号分隔）
     * @param ignoreDataRangeFlag 是否数据权限过滤 true：不需要，false：需要
     * @param condition           检索条件
     * @return 用户列表
     */
    public List<DeptExt> getUserTreeByRole(String roleCode, Boolean ignoreDataRangeFlag, Map<String, Object> condition) {
        //获取角色对应的用户
        List<UserExt> userList = this.getUserByRole(condition, roleCode, ignoreDataRangeFlag);

        //获取所有部门信息
        List<DeptExt> allDeptList = this.deptService.search(DeptExt.class, this.deptService.getSearchSQL(null));

        Map<String, DeptExt> map = new HashMap<>();
        for (UserExt user : userList) {
            //获取用户所属组织以及上级组织
            getParentDeptList(allDeptList, user.getDeptUid(), map);
            //将用户拼装成符合组织树形的结构
            this.setUserDeptToMap(user, map);
        }

        //map转list
        List<DeptExt> deptList = new ArrayList<>();
        this.convertMapToDeptList(map, deptList);
        return deptList;
    }

    /**
     * 将用户拼装成符合组织树形的结构 ，把人员的部门信息放入部门map
     *
     * @param user    用户实体
     * @param mapDept 部门map
     */
    private void setUserDeptToMap(UserExt user, Map<String, DeptExt> mapDept) {
        //用户或者部门map 为null时直接返回
        if (user == null || mapDept == null) {
            return;
        }
        //将用户拼装成符合组织树形的结构
        DeptExt dept = new DeptExt();
        dept.setUid(user.getUid());
        dept.setDeptName(user.getUserName());
        dept.setParentDeptUid(user.getDeptUid());
        dept.setParentDeptName(user.getDeptName());
        dept.setIsPerson(true);
        mapDept.put(user.getUid(), dept);
    }

    /**
     * 将部门map 转为列表
     *
     * @param deptMap     部门map
     * @param deptExtList 部门列表
     */
    private void convertMapToDeptList(Map<String, DeptExt> deptMap, List<DeptExt> deptExtList) {
        //map转list
        Iterator it = deptMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            deptExtList.add(deptMap.get(key));
        }
    }

    /**
     * 根据权限取得分公司员工
     *
     * @param userUid  要取得的分公司某员工UID
     * @param roleCode 权限列表 多个逗号分隔
     * @return 分公司员工列表
     */
    public List<UserExt> findCompanyUserByRole(String userUid, String roleCode) {
        UserExt sourceUser = userService.initDeptInfo(userUid);
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("company.uid", sourceUser.getUserCompanyUid());
        return getUserByRole(condition, roleCode, true);
    }
}