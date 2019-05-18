package com.bpms.sys.service;

import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.utils.*;
import com.bpms.sys.entity.ext.*;
import com.google.common.collect.Maps;
import com.bpms.sys.dao.EnterpriseDao;
import com.bpms.sys.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业信息服务类
 */
@Service
public class EnterpriseService extends SysBaseService<EnterpriseExt> {
    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DeptService deptService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;



    @Override
    public EnterpriseDao getDao() {
        return enterpriseDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(EnterpriseExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(EnterpriseExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected EnterpriseExt saveBefore(EnterpriseExt entity) {
        //编辑的场合
        if (entity.getUid() != null) {
            EnterpriseExt enterprise = this.findOne(entity.getUid());
            //企业名称变更场合
            if (!StringUtils.equals(entity.getEnterpriseName(), enterprise.getEnterpriseName())) {
                //变更部门名称
                this.updateDeptInfo(entity);
            }
        }
        return entity;
    }

    /**
     * 修改部门信息
     *
     * @param enterprise 企业实体
     */
    private void updateDeptInfo(EnterpriseExt enterprise) {
        //根据企业UID
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.enterprise_uid", enterprise.getUid());
        List<DeptExt> deptList = this.deptService.search(condition);
        if (CollectionUtils.isNotEmpty(deptList)) {
            DeptExt dept = deptList.get(0);
            //设置部门名称  企业名称 = 部门名称
            dept.setDeptName(enterprise.getEnterpriseName());
            dept.setDeptFullName(enterprise.getEnterpriseName());
            this.deptService.getDao().save(dept);
        }
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected EnterpriseExt saveAfter(EnterpriseExt entity) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.enterprise_uid", entity.getUid());
        List<DeptExt> deptList = this.deptService.search(condition);
        //add new dept
        if (CollectionUtils.isEmpty(deptList)) {

            DeptExt dept = new DeptExt();
            dept.setUid(UniqueUtils.getUID());
            dept.setEnterpriseUid(entity.getUid());
            dept.setDeptName(entity.getEnterpriseName());
            dept.setDeptFullName(entity.getEnterpriseName());
            dept.setDeptClass(2);//分公司
            dept.setDispSeq(1);
            this.deptService.save(dept);

            //add new user
            UserExt user = new UserExt();
            user.setUid(UniqueUtils.getUID());
            user.setEnterpriseUid(entity.getUid());
            user.setDeptUid(dept.getUid());
            user.setUserCd("sa");
            //登录密码
            user.setPassword(Digests.shaPassword256("sa"));
            //设置DES密码
            user.setDesPassword(DesUtils.encrypt("sa"));
            user.setPlainPassword("sa");
            user.setUserName(entity.getEnterpriseName() + "管理者");

            user.setUserNumber("1");//工号，必须？
            user.setUserMail("admin@bpms.com");
            user.setUserPhone("13345678910");
            //设置为系统注册
            user.setRegSystem(1);
            userService.save(user);

            //角色
            List<RoleExt> roleList = roleService.search(new HashMap<>());

            for(RoleExt role:roleList){
                UserRoleExt userRole = new UserRoleExt();
                userRole.setUid(UniqueUtils.getUID());
                userRole.setEnterpriseUid(entity.getUid());
                userRole.setUserUid(user.getUid());
                userRole.setRoleUid(role.getUid());
                userRoleService.save(userRole);
            }


        }




        return entity;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(String ids) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(String ids) {
        return true;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(EnterpriseExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(EnterpriseExt entity) {
        return true;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        //同步 启用/停用 企业下的所有用户
        Map<String, Object> columnSetting = Maps.newHashMap();
        columnSetting.put("record_status", recordStatus);
        columnSetting.put("update_user", this.getCurrentUser().getUserUid());

        Map<String, Object> condition = Maps.newHashMap();
        condition.put("enterprise_uid", uid);
        userDao.executeUpdate(columnSetting, condition);

        return true;
    }

    /**
     * 取得登录用户所属企业的根部门信息
     *
     * @return 企业根部门信息实体
     */
    public DeptExt getEnterpriseRootDept() {
        return this.getEnterpriseRootDept(this.getCurrentUser().getEnterpriseUid());
    }

    /**
     * 取得指定企业的根部门信息
     *
     * @param enterpriseUid 企业UID
     * @return 企业根部门信息实体
     */
    public DeptExt getEnterpriseRootDept(Object enterpriseUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.enterpriseUid", enterpriseUid);
        condition.put("main.deptClass", 0);
        List<DeptExt> deptList = this.deptService.search(DeptExt.class, condition);
        if (CollectionUtils.isNotEmpty(deptList)) {
            return deptList.get(0);
        }
        return new DeptExt();
    }

    public List<DropdownEntity> getUserSelect(){
        List<DropdownEntity> dropdownEntityList = new ArrayList<>();
        List<EnterpriseExt> list = this.search(new HashMap<>());
        for (EnterpriseExt ext : list) {
            int recordStatus = ext.getRecordStatus();
            String userStatus = StringUtils.EMPTY;
            //如果是停用的人， 名字后面加上停用
            if (recordStatus == 8) {
                continue;
            }
            DropdownEntity dropdownEntity = new DropdownEntity();
            dropdownEntity.setSubCd(ext.getUid().toString());
            dropdownEntity.setSubName(ext.getEnterpriseName());
            dropdownEntityList.add(dropdownEntity);
        }
        return dropdownEntityList;
    }
}