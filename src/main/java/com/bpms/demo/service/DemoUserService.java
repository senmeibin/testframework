package com.bpms.demo.service;

import com.bpms.cmn.service.CmnUserService;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.dao.UserDao;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户服务类
 */
@Service(value = "HrUserService")
public class DemoUserService extends DemoBaseService<UserExt> {
    @Autowired
    private UserDao userDao;

    @Autowired
    private DeptService deptService;

    @Autowired
    private CmnUserService cmnUserService;

    @Override
    public UserDao getDao() {
        return userDao;
    }

    /**
     * 取得标准查询SQL节点路径
     *
     * @return 查询SQL节点路径
     */
    @Override
    public String getSearchSQLPath(Map<String, Object> condition) {
        return "sys/user/search";
    }

    /**
     * 根据权限获取人员下拉树
     *
     * @return 用户列表
     */
    public List<DeptExt> getUserTreeByRole(String RoleCode) {
        List<DeptExt> deptList = new ArrayList<>();
        Map<String, Object> condition = new HashMap<>();
        //获取招聘专员
        List<UserExt> userList = this.getUserByRole(condition, RoleCode);

        //获取所有部门信息
        List<DeptExt> allDeptList = this.deptService.search(DeptExt.class, this.deptService.getSearchSQL(null));

        Map<String, DeptExt> map = new HashMap<>();
        for (UserExt user : userList) {
            //获取用户所属组织以及上级组织
            getParentDeptList(allDeptList, user.getDeptUid(), map);
            //将用户拼装成符合组织树形的结构
            DeptExt dept = new DeptExt();
            dept.setUid(user.getUid());
            dept.setDeptName(user.getUserName());
            dept.setParentDeptUid(user.getDeptUid());
            dept.setParentDeptName(user.getDeptName());
            dept.setIsPerson(true);
            map.put(user.getUid(), dept);
        }

        //map转list
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            deptList.add(map.get(key));
        }

        return deptList;
    }

    /**
     * 获取所有的有效用户(根据权限获取人员下拉树)
     *
     * @return 用户列表
     */
    public List<DeptExt> getAllUserTree(String RoleCode) {
        List<DeptExt> deptList = new ArrayList<>();
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.record_status", "1");
        //获取所有的有效用户
        List<UserExt> userList = this.search(condition);

        //获取所有部门信息
        List<DeptExt> allDeptList = this.deptService.search(DeptExt.class, this.deptService.getSearchSQL(null));

        Map<String, DeptExt> map = new HashMap<>();
        for (UserExt user : userList) {
            //获取用户所属组织以及上级组织
            getParentDeptList(allDeptList, user.getDeptUid(), map);
            //将用户拼装成符合组织树形的结构
            DeptExt dept = new DeptExt();
            dept.setUid(user.getUid());
            dept.setDeptName(user.getUserName());
            dept.setParentDeptUid(user.getDeptUid());
            dept.setParentDeptName(user.getDeptName());
            dept.setIsPerson(true);
            map.put(user.getUid(), dept);
        }

        //map转list
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            deptList.add(map.get(key));
        }

        return deptList;
    }

    /**
     * 递归获取上级组织
     *
     * @param allDept 所有组织
     * @param deptUid 部门UID
     * @return 上级部门Map集合
     */
    public Map<String, DeptExt> getParentDeptList(List<DeptExt> allDept, String deptUid, Map<String, DeptExt> map) {
        //防止db数据遭到恶意破坏后出现死循环
        if (map.size() > 1000) return map;
        for (DeptExt dept : allDept) {
            //遍历出UID等于参数UID，添加进集合
            if (deptUid.equals(dept.getUid())) {
                //map中不存在 放入map中
                if (!map.containsKey(dept.getUid())) {
                    map.put(dept.getUid(), dept);
                }
                //存在上级部门
                if (StringUtils.isNotEmpty(dept.getParentDeptUid())) {
                    //递归遍历出上级部门
                    getParentDeptList(allDept, dept.getParentDeptUid(), map);
                }
            }
        }
        return map;
    }

    /**
     * 按照权限获取人员
     *
     * @param condition 检索条件
     * @return 用户列表
     */
    public List<UserExt> getUserByRole(Map<String, Object> condition, String RoleCode) {
        return this.cmnUserService.getUserSelect(UserExt.class, true, RoleCode, condition);
    }
}
