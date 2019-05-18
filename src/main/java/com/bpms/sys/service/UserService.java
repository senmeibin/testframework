package com.bpms.sys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bpms.cmn.entity.ext.RegionExt;
import com.bpms.cmn.entity.ext.SignCompanyExt;
import com.bpms.cmn.service.CustomerService;
import com.bpms.cmn.service.RegionService;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.mail.SendMailService;
import com.bpms.core.security.ShiroDbRealm;
import com.bpms.core.sms.SendSmsService;
import com.bpms.core.sync.SyncDataService;
import com.bpms.core.utils.*;
import com.bpms.sys.consts.SysConsts;
import com.bpms.sys.dao.*;
import com.bpms.sys.entity.ext.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 用户服务类
 */
@Service
public class UserService extends SysBaseService<UserExt> {
    /**
     * 城市用户缓存KEY前缀
     */
    private static final String CACHE_PREFIX_CITY_USER_NO_KEY = "CACHE_CMN_CITY_USER_NO";

    @Autowired
    private ShiroDbRealm shiroDbRealm;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DeptManageDao deptManageDao;

    @Autowired
    private CityManageDao cityManageDao;

    @Autowired
    private SignCompanyManageDao signCompanyManageDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CustomerService customerService;

    @Override
    public UserDao getDao() {
        return userDao;
    }

    /**
     * 根据工号查询用户
     *
     * @param userNumber 工号
     * @return 用户详细
     */
    @Transactional(readOnly = true)
    public UserExt findByUserNumber(String userNumber) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.userNumber", userNumber);

        List<UserExt> list = this.search(UserExt.class, this.getSearchSQL(condition), condition);

        if (list.size() > 0) {
            UserExt userExt = list.get(0);
            //获取用户关联信息
            userExt.setUserInfo(this.userInfoService.getDetail(userExt.getUid()));
            return userExt;
        }

        return null;
    }

    /**
     * 根据登录CD查询用户
     *
     * @param userCd 用户CD
     * @return 用户详细
     */
    @Transactional(readOnly = true)
    public UserExt findByUserCd(String userCd) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.userCd", userCd);

        List<UserExt> list = this.search(UserExt.class, this.getSearchSQL(condition), condition);

        if (list.size() > 0) {
            UserExt userExt = list.get(0);
            //获取用户关联信息
            userExt.setUserInfo(this.userInfoService.getDetail(userExt.getUid()));
            return userExt;
        }

        return null;
    }

    /**
     * 根据手机号码查询用户
     *
     * @param mobile 手机号码
     * @return 用户详细
     */
    @Transactional(readOnly = true)
    public UserExt findByMobile(String mobile) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.userPhone", mobile);

        List<UserExt> list = this.search(UserExt.class, this.getSearchSQL(condition), condition);

        if (list.size() > 0) {
            UserExt userExt = list.get(0);
            //获取用户关联信息
            userExt.setUserInfo(this.userInfoService.getDetail(userExt.getUid()));
            return userExt;
        }

        return null;
    }

    /**
     * 取得指定UID的详细信息
     *
     * @param pkValue 主键值
     * @return 实体详细
     */
    @Override
    public UserExt getDetail(Object pkValue) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.uid", pkValue);
        //根据查询条件获取List
        List<UserExt> list = this.search(this.getSearchSQL(condition), condition);
        if (list.size() > 0) {
            UserExt user = list.get(0);
            //获取用户关联信息
            user.setUserInfo(this.userInfoService.getDetail(user.getUid()));
            return user;
        }
        return null;
    }

    /**
     * 取得指定部门下的所属用户列表
     *
     * @param deptUid 部门UID
     * @return 所属用户列表
     */
    public List<UserExt> findByDeptUid(String deptUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.deptUid", deptUid);

        return this.search(UserExt.class, this.getSearchSQL(condition), condition);
    }

    /**
     * 取得指定部门下的启用的所属用户列表
     *
     * @param deptUid 部门UID
     * @return 所属用户列表
     */
    public List<UserExt> findValidUserByDeptUid(String deptUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.deptUid", deptUid);
        condition.put("main.record_status", "1");

        return this.search(UserExt.class, this.getSearchSQL(condition), condition);
    }

    /**
     * 数据删除处理(根据ID集合删除)
     *
     * @param ids ID集合
     */
    @Override
    @Transactional
    public void delete(String ids) {
        UserExt entity = this.getDetail(ids);

        if (StringUtils.equals(entity.getUid(), this.getCurrentUser().getUserUid())) {
            throw new ServiceValidationException("登录中的用户禁止删除。");
        }

        if (StringUtils.equals(entity.getUid(), CmnConsts.ADMIN_USER_UID)) {
            throw new ServiceValidationException("系统管理员用户禁止删除。");
        }

        if (entity.getRegSystem() == 1) {
            throw new ServiceValidationException("系统默认注册用户禁止删除。");
        }

        //删除用户与角色的关联关系
        userRoleService.delete("user_uid", ids);

        //设置删除状态
        entity.setRecordStatus(Integer.parseInt(CmnConsts.RECORD_STATUS_DELETE));
        //为了UserCd能够重复利用，删除后的UserCd后连接删除时间戳
        entity.setUserCd(entity.getUserCd() + "^" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));

        //清空分机号码、用户坐席、用户坐席密码
        entity.setUserExtensionNumber(null);
        entity.setUserAgent(null);
        entity.setUserAgentPassword(null);

        //保存处理
        this.getDao().save(entity);
    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected UserExt saveBefore(UserExt entity) {
        //用户名重复性校验
        if (this.isDuplication("uid", entity.getUid(), "userCd", entity.getUserCd(), false)) {
            throw new ServiceValidationException(String.format("指定的用户名(%s)已存在，请重新输入。", entity.getUserCd()));
        }

        //手机号码重复性校验(改为同样手机号的，状态必须不一致，譬如改过部门的人，手机号还是同一个，但是他们一个停用一个启用，所以不check重复）
        if (this.isDuplication("uid", entity.getUid(), "userPhone", entity.getUserPhone(), true)) {
            throw new ServiceValidationException(String.format("指定用户名(%s)的手机号码(%s)已存在，请重新输入。", entity.getUserCd(), entity.getUserPhone()));
        }

        //工号校验
        if (StringUtils.isEmpty(entity.getUserNumber())) {
            throw new ServiceValidationException(String.format("指定的用户(%s)的工号不能为空，请重新输入。", entity.getUserCd()));
        }
        //重复性校验
        else {
            if (this.isDuplication("uid", entity.getUid(), "userNumber", entity.getUserNumber(), false)) {
                throw new ServiceValidationException(String.format("指定的工号(%s)已存在，请重新输入。", entity.getUserNumber()));
            }
        }

        if (StringUtils.isNotEmpty(entity.getUserExtensionNumber())) {
            //分机号码重复性校验
            if (this.isDuplication("uid", entity.getUid(), "userExtensionNumber", entity.getUserExtensionNumber())) {
                throw new ServiceValidationException(String.format("指定的分机号码(%s)已存在，请重新输入。", entity.getUserExtensionNumber()));
            }
        }

        //获取部门信息
        DeptExt dept = this.deptService.getDao().findOne(entity.getDeptUid());
        if (dept == null) {
            throw new ServiceValidationException(String.format("指定的部门(%s)不存在，请重新输入。", entity.getDeptUid()));
        }

        //手动登录的账号验证所属部门是否选择正确
        if (entity.getRegSystem() == 0 && dept.getDeptClass() != 3 && dept.getDeptClass() != 4) {
            throw new ServiceValidationException(String.format("人员只能添加到具体部门下，请重新选择。<br/>(不能直接将人员添加到集团、大区、分公司下)。", entity.getDeptUid()));
        }

        //添加模式的场合
        if (isAddMode(entity)) {
            //新规的场合，密码必须数据校验
            if (StringUtils.isEmpty(entity.getPlainPassword())) {
                throw new ServiceValidationException("请设定初期登录密码。");
            }
            entity.setForceChangePswd(CmnConsts.FORCE_CHANGE_PSWD_YES);
            entity.setPassword(Digests.shaPassword256(entity.getPlainPassword()));

            //设置DES密码
            entity.setDesPassword(DesUtils.encrypt(entity.getPlainPassword()));
        }
        //编辑模式的场合
        else if (isEditMode(entity)) {
            //明文密码未设定的场合，从DB中复原密码信息
            if (StringUtils.isEmpty(entity.getPlainPassword())) {
                UserExt src = this.getDetail(entity.getUid());

                //复原DB中的密码
                entity.setPassword(src.getPassword());
                entity.setDesPassword(src.getDesPassword());
            }
            else {
                //更新密码
                entity.setChangePswdDatetime(new Date());
                //登录密码
                entity.setPassword(Digests.shaPassword256(entity.getPlainPassword()));
                //设置DES密码
                entity.setDesPassword(DesUtils.encrypt(entity.getPlainPassword()));
            }
        }
        return entity;
    }

    @Override
    protected UserExt saveAfter(UserExt entity) {
        if (entity.getRoleList() != null) {
            //根据用户UID删除关联角色
            this.userRoleService.delete("user_uid", entity.getUid());

            List<UserRoleExt> userRoleList = Lists.newArrayList();
            //循环写入关联角色列表
            for (RoleExt role : entity.getRoleList()) {
                UserRoleExt userRole = new UserRoleExt();

                //用户UID
                userRole.setUserUid(entity.getUid());

                //角色UID
                userRole.setRoleUid(role.getUid());

                userRole.setRecordStatus(1);

                userRoleList.add(userRole);
            }
            this.userRoleService.getDao().save(userRoleList);

            //删除授权缓存
            if (shiroDbRealm.getAuthorizationCache() != null) {
                Set<Object> loginCache = shiroDbRealm.getAuthorizationCache().keys();
                boolean removeAuthorizationCacheSuccess = false;
                for (Object cache : loginCache) {
                    if (StringUtils.containsAny(cache.toString(), entity.getUserName())) {
                        shiroDbRealm.getAuthorizationCache().remove(cache);
                        removeAuthorizationCacheSuccess = true;
                    }
                }
                if (!removeAuthorizationCacheSuccess) {
                    log.warn("未能清除用户权限缓存信息，用户可能未登录，如登录，此处需调查原因，用户：{},登录列表：{}", entity.getUserName(), StringUtils.join(loginCache, ","));
                }
            }
        }

        //管辖部门处理
        //不论原来有无， 都先删除
        this.deptManageDao.delete("user_uid", entity.getUid());
        if (StringUtils.isNotEmpty(entity.getDeptManageUids())) {
            String[] deptUids = entity.getDeptManageUids().split(",");
            for (String deptUid : deptUids) {
                if (StringUtils.isEmpty(deptUid)) {
                    continue;
                }
                DeptManageExt deptManageExt = new DeptManageExt();
                deptManageExt.setUid(UniqueUtils.getUID());
                //部门uid
                deptManageExt.setDeptUid(deptUid);
                //人员uid
                deptManageExt.setUserUid(entity.getUid());
                this.deptManageDao.save(deptManageExt);
            }
        }

        //管辖城市处理
        //不论原来有无， 都先删除
        this.cityManageDao.delete("user_uid", entity.getUid());
        if (StringUtils.isNotEmpty(entity.getCityManageUids())) {
            String[] cityUids = entity.getCityManageUids().split(",");
            for (String cityUid : cityUids) {
                if (StringUtils.isEmpty(cityUid)) {
                    continue;
                }
                CityManageExt cityManageExt = new CityManageExt();
                cityManageExt.setUid(UniqueUtils.getUID());
                //城市uid
                cityManageExt.setCityUid(cityUid);
                //人员uid
                cityManageExt.setUserUid(entity.getUid());
                this.cityManageDao.save(cityManageExt);
            }
        }

        //管辖签单分公司处理
        //不论原来有无， 都先删除
        this.signCompanyManageDao.delete("user_uid", entity.getUid());
        if (StringUtils.isNotEmpty(entity.getSignCompanyManageUids())) {
            String[] signCompanyUids = StringUtils.split(entity.getSignCompanyManageUids(), ",");
            for (String signCompanyUid : signCompanyUids) {
                if (StringUtils.isEmpty(signCompanyUid)) {
                    continue;
                }
                SignCompanyManageExt signCompanyExt = new SignCompanyManageExt();
                signCompanyExt.setUid(UniqueUtils.getUID());
                //签单公司uid
                signCompanyExt.setSignCompanyUid(signCompanyUid);
                //人员uid
                signCompanyExt.setUserUid(entity.getUid());
                this.signCompanyManageDao.save(signCompanyExt);
            }
        }

        //保存用户扩展信息
        saveUserInfo(entity);

        return super.saveAfter(entity);
    }

    /**
     * 保存用户扩展信息
     *
     * @param entity 用户实体对象
     */
    private void saveUserInfo(UserExt entity) {
        //用户QQ 微信更新
        UserInfoExt userInfo = userInfoDao.findOne(entity.getUid());
        if (userInfo == null) {
            userInfo = new UserInfoExt();
            userInfo.setUid(entity.getUid());
        }

        userInfo.setQq(entity.getUserInfo().getQq());
        userInfo.setWeixin(entity.getUserInfo().getWeixin());

        userInfo.setExtSystemName1(entity.getUserInfo().getExtSystemName1());
        userInfo.setExtSystemAccount1(entity.getUserInfo().getExtSystemAccount1());
        userInfo.setExtSystemPassword1(DesUtils.encrypt(entity.getUserInfo().getExtSystemPassword1()));

        userInfo.setExtSystemName2(entity.getUserInfo().getExtSystemName2());
        userInfo.setExtSystemAccount2(entity.getUserInfo().getExtSystemAccount2());
        userInfo.setExtSystemPassword2(DesUtils.encrypt(entity.getUserInfo().getExtSystemPassword2()));

        userInfo.setExtSystemName3(entity.getUserInfo().getExtSystemName3());
        userInfo.setExtSystemAccount3(entity.getUserInfo().getExtSystemAccount3());
        userInfo.setExtSystemPassword3(DesUtils.encrypt(entity.getUserInfo().getExtSystemPassword3()));

        userInfo.setExtSystemName4(entity.getUserInfo().getExtSystemName4());
        userInfo.setExtSystemAccount4(entity.getUserInfo().getExtSystemAccount4());
        userInfo.setExtSystemPassword4(DesUtils.encrypt(entity.getUserInfo().getExtSystemPassword4()));

        userInfo.setExtSystemName5(entity.getUserInfo().getExtSystemName5());
        userInfo.setExtSystemAccount5(entity.getUserInfo().getExtSystemAccount5());
        userInfo.setExtSystemPassword5(DesUtils.encrypt(entity.getUserInfo().getExtSystemPassword5()));

        userInfoDao.save(userInfo);
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
        UserExt entity = this.getDetail(uid);

        //停用处理时，清空分机号码、用户坐席、用户坐席密码字段信息
        if (StringUtils.equals(recordStatus, CmnConsts.RECORD_STATUS_STOP)) {
            //停用处理时，清空分机号码、用户坐席、用户坐席密码字段信息
            entity.setUserExtensionNumber(null);
            entity.setUserAgent(null);
            entity.setUserAgentPassword(null);
            //设置为 8 ：停用
            entity.setRecordStatus(8);

            //保存处理
            this.getDao().save(entity);
        }

        return true;
    }

    /**
     * 初始化指定用户的组织信息
     * 包含【所属部门/所属分公司/所属大区/所属集团】等相关信息
     *
     * @param uid 用户UID
     * @return 用户实体(包含所有组织信息 ）
     */
    public UserExt initDeptInfo(String uid) {
        //查找用户信息
        UserExt user = this.getDetail(uid);

        //容错 如果传入的用户不存在返回当前uid的实体
        if (user == null) {
            user = new UserExt();
            user.setUid(uid);
            return user;
        }

        //初始化指定用户的组织信息
        return initDeptInfo(user);
    }

    /**
     * 初始化指定用户的组织信息
     * 包含【所属部门/所属分公司/所属大区/所属集团】等相关信息
     *
     * @param user 用户实体
     * @return 用户实体(包含所有组织信息 ）
     */
    public UserExt initDeptInfo(UserExt user) {
        //查找用户所属部门
        DeptExt dept = deptService.findOne(user.getDeptUid());

        //所属部门不存在的场合，处理中止
        if (dept == null) {
            return user;
        }

        user.setDeptName(dept.getDeptName());

        //递归设置用户【所属部门/所属分公司/所属大区/所属集团】等相关信息
        user = initUserDepts(user, dept);

        //获取该用户的管辖部门信息
        List<DeptExt> manageDepts = this.getManageDepts(user);
        //把自己的分公司作为默认管辖分公司
        if (user.getBelongCompanyDept() != null) {
            if (CollectionUtils.isEmpty(manageDepts)) {
                //把自己的分公司作为默认管辖分公司
                manageDepts = new ArrayList<>();
                manageDepts.add(user.getBelongCompanyDept());
            }
            else {
                manageDepts.add(user.getBelongCompanyDept());
            }
        }
        user.setManageDepts(manageDepts);

        //获取该用户的管辖城市信息
        List<RegionExt> manageCityList = this.getManageCityList(user);
        if (CollectionUtils.isNotEmpty(manageCityList)) {
            user.setManageCityList(manageCityList);
        }

        //获取该用户的管辖签单分公司信息
        user.setManageSignCompanyList(this.getManageSignCompanyList(user));
        return user;
    }

    /**
     * 获取当前用户的管辖部门信息
     *
     * @param userExt 当前用户
     * @return 当前用户的管辖部门
     */
    private List<DeptExt> getManageDepts(UserExt userExt) {
        //如果管辖部门uids 为空， 返回null
        if (StringUtils.isEmpty(userExt.getDeptManageUids())) {
            return null;
        }
        Map<String, Object> condition = new HashMap<>();
        condition.put("dept_manage.user_uid", userExt.getUid());
        List<DeptExt> deptExtList = this.deptService.search(this.deptService.getSQL("sys/Dept/getManageDeptList"), condition);
        return Lists.newArrayList(deptExtList);
    }

    /**
     * 获取当前用户的管辖城市信息
     *
     * @param userExt 当前用户
     * @return 当前用户的管辖城市
     */
    private List<RegionExt> getManageCityList(UserExt userExt) {
        //如果管辖部门uids 为空， 返回null
        if (StringUtils.isEmpty(userExt.getCityManageUids())) {
            return null;
        }
        Map<String, Object> condition = new HashMap<>();
        condition.put("city_manage.user_uid", userExt.getUid());
        List<RegionExt> cityList = this.regionService.search(this.regionService.getSQL("cmn/Region/getManageCityList"), condition);
        return Lists.newArrayList(cityList);
    }

    /**
     * 获取当前用户的管辖签单分公司信息
     *
     * @param userExt 当前用户
     * @return 当前用户的管辖签单分公司
     */
    private List<SignCompanyExt> getManageSignCompanyList(UserExt userExt) {
        //如果管辖签单分公司uids 为空， 返回null
        if (StringUtils.isEmpty(userExt.getSignCompanyManageUids())) {
            return null;
        }
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.uid$in", userExt.getSignCompanyManageUids());
        List<SignCompanyExt> companyList = this.signCompanyManageDao.search(SignCompanyExt.class, this.getSQL("cmn/SignCompany/search"), condition);
        return Lists.newArrayList(companyList);
    }

    /**
     * 递归设置用户【所属部门/所属分公司/所属大区/所属集团】等相关信息
     *
     * @param user 用户实体
     * @param dept 部门实体
     */
    private UserExt initUserDepts(UserExt user, DeptExt dept) {
        //父级部门 不为空的场合
        if (StringUtils.isNotEmpty(dept.getParentDeptUid())) {
            //追加部门实体到所属部门树集合
            user.addDeptToBelongDeptTree(dept);

            //如果用户直接挂靠在分公司的场合
            if (dept.getDeptClass() == SysConsts.DEPT_TYPE_COMPANY) {
                user.setBelongCompanyDept(dept);
            }

            //所在部门上级部门
            DeptExt parent = deptService.findOne(dept.getParentDeptUid());

            //上级部门为空的场合，处理中止
            if (parent == null) {
                return user;
            }

            //所属集团
            if (parent.getDeptClass() == SysConsts.DEPT_TYPE_GROUP) {
                user.setBelongGroupDept(parent);
            }
            //所属大区
            else if (parent.getDeptClass() == SysConsts.DEPT_TYPE_AREA) {
                user.setBelongAreaDept(parent);
            }
            //所属分公司
            else if (parent.getDeptClass() == SysConsts.DEPT_TYPE_COMPANY) {
                user.setBelongCompanyDept(parent);
            }
            return initUserDepts(user, parent);
        }
        return user;
    }

    /**
     * 取得当前用户所有权限集合
     *
     * @return 当前用户所有权限集合
     */
    public List<String> getCurrentUserPermissions() {
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("user_role.userUid", getCurrentUser().getUserUid());
        return userDao.search(String.class, getSQL("sys/user/getCurrentUserPermissions"), params);
    }

    @Override
    public Page<UserExt> search(Class<UserExt> cls, String sql, Map<String, Object> condition, PageRequest pageRequest) {
        Page<UserExt> result = super.search(cls, sql, condition, pageRequest);

        //补全角色权限、合同客户列表
        for (UserExt user : result) {
            user.setRoleList(roleService.findByUserUid(user.getUid()));
            user.setCustomerList(this.customerService.findByUserUid(user.getUid()));
        }

        return result;
    }

    /**
     * 修改密码处理
     *
     * @param oldPwd     旧密码
     * @param newPwd     新密码
     * @param confirmPwd 确认密码
     * @return true:处理成功
     */
    @Transactional
    public boolean changePassword(String oldPwd, String newPwd, String confirmPwd) {
        UserExt user = this.getDetail(getCurrentUserId());
        String oldPwd256 = Digests.shaPassword256(oldPwd);

        //新旧密码一致性验证
        if (StringUtils.equals(newPwd, oldPwd)) {
            throw new ServiceValidationException("新旧密码不能一致。");
        }
        //新密码一致性验证
        if (!StringUtils.equals(newPwd, confirmPwd)) {
            throw new ServiceValidationException("确认密码与更新密码不一致。");
        }
        //原密码一致性验证
        else if (!StringUtils.equals(user.getPassword(), oldPwd256)) {
            throw new ServiceValidationException("原密码错误。");
        }
        else {
            //进行修改密码和修改密码更新时间
            user.setPlainPassword(newPwd);
            user.setChangePswdDatetime(new Date());

            //设置强制密码变更状态【数据库中状态】
            user.setForceChangePswd(CmnConsts.FORCE_CHANGE_PSWD_NO);

            //设置强制密码变更状态【Session中状态】
            this.getCurrentUser().setForceChangePswd(CmnConsts.FORCE_CHANGE_PSWD_NO);

            //密码变更处理（DB直接更新）
            this.changePassword(user);
        }
        return true;
    }

    /**
     * 密码变更处理（DB直接更新）
     *
     * @param user 用户实体对象
     */
    private void changePassword(UserExt user) {
        Map<String, Object> columnSetting = new HashMap<>();
        //强制密码变更
        columnSetting.put("force_change_pswd", user.getForceChangePswd());
        //密码变更时间
        columnSetting.put("change_pswd_datetime", new Date());
        //登录密码
        columnSetting.put("password", Digests.shaPassword256(user.getPlainPassword()));
        //设置DES密码
        columnSetting.put("des_password", DesUtils.encrypt(user.getPlainPassword()));

        Map<String, Object> condition = new HashMap<>();
        condition.put("uid", user.getUid());
        this.getDao().executeUpdate(columnSetting, condition);

        //注：UDC数据同步前必须再次设置密码相关信息
        //更新密码
        user.setChangePswdDatetime(new Date());
        //登录密码
        user.setPassword(Digests.shaPassword256(user.getPlainPassword()));
        //设置DES密码
        user.setDesPassword(DesUtils.encrypt(user.getPlainPassword()));

        //直接推送UDC同步数据
        syncDataService.directPushSyncDataTopic(user);
    }

    /**
     * 重置用户密码并发送邮件
     *
     * @param templatePath 邮件模板路径
     * @param userCd       用户名
     * @param userMail     邮箱地址
     * @return 1：重置成功/-1：邮件发送失败/-2：账号密码不匹配
     * @throws IOException
     * @throws MessagingException
     */
    @Transactional
    public int resetPassword(String templatePath, String userCd, String userMail) throws IOException, MessagingException {
        //根据用户名取得用户详情
        UserExt user = this.findByUserCd(userCd);

        //判断userCd是否存在且与邮箱地址匹配
        if (user != null && userMail.equals(user.getUserMail()) && user.getRecordStatus() == 1) {
            //强制密码变更
            user.setForceChangePswd(CmnConsts.FORCE_CHANGE_PSWD_YES);

            //生成随机六位字符并加密（密码）
            String password = this.createRandomPassword();

            //设置用户明文密码[具体密码加密及DES密码在saveBefore方法中实现]
            user.setPlainPassword(password);

            //获取邮件模板内容
            String contents = this.getMailContents(templatePath, userCd, user.getUserName(), password);

            //系统名称
            String systemName = this.parameterService.getSystemName();

            //密码变更 发送邮件
            boolean result = this.sendMailService.send(AppCodeConsts.APP_AUTH, systemName + "-重置密码", contents, userMail);

            //邮件发送成功后，密码写入DB
            if (result) {
                //密码变更处理（DB直接更新）
                this.changePassword(user);
                return 1;
            }
            else {
                return -1;
            }
        }

        return -2;
    }

    /**
     * 重置用户密码并发送短信
     *
     * @param userCd 用户名
     * @return 1：重置成功/-1：短信发送失败/-2：账号密码不匹配
     * @throws IOException
     * @throws MessagingException
     */
    @Transactional
    public int resetPassword(String userCd) throws Exception {
        //根据用户名取得用户详情
        UserExt user = this.findByUserCd(userCd);
        if (user != null && user.getRecordStatus() == 1) {
            //强制密码变更
            user.setForceChangePswd(CmnConsts.FORCE_CHANGE_PSWD_YES);

            //生成随机六位字符并加密（密码）
            String password = this.createRandomPassword();

            //设置用户明文密码[具体密码加密及DES密码在saveBefore方法中实现]
            user.setPlainPassword(password);

            //系统名称
            String systemName = this.parameterService.getSystemName();

            //密码变更 发送短信
            boolean result = this.sendSmsService.send(AppCodeConsts.APP_AUTH, user.getUserPhone(), String.format("您的密码已被重置，新密码是：%s【%s】", password, systemName), StringUtils.EMPTY);

            //短信发送成功后，密码写入DB
            if (result) {
                //密码变更处理（DB直接更新）
                this.changePassword(user);
                return 1;
            }
            else {
                return -1;
            }
        }
        return -2;
    }

    /**
     * 获取邮件模板内容
     *
     * @param userCd   用户编号
     * @param userName 用户名称
     * @param password 密码
     * @return 邮件模板内容
     * @throws IOException
     */
    public String getMailContents(String templatePath, String userCd, String userName, String password) throws IOException {
        //读取文件信息
        String contents = FileUtils.readFileToString(new File(FileUtils.getRealPath(templatePath + "template\\auth\\mail\\RetrievePassword.html")), "UTF-8");

        //替换用户名称、用户名、服务器地址、密码、时间信息
        contents = StringUtils.replaceAll(contents, "#USER_NAME#", userName);
        contents = StringUtils.replaceAll(contents, "#USER_CD#", userCd);
        contents = StringUtils.replaceAll(contents, "#SERVER_ADDRESS#", this.parameterService.getServerAddress());
        contents = StringUtils.replaceAll(contents, "#PASS_WORD#", password);
        contents = StringUtils.replaceAll(contents, "#SYSTEM_DATE#", DateUtils.getNowDateString());
        contents = StringUtils.replaceAll(contents, "#SYSTEM_NAME#", this.parameterService.getSystemName());

        return contents;
    }

    /**
     * 生成6位字符数字随机密码
     *
     * @return String
     */
    private String createRandomPassword() {
        //生成数据所包含的字符
        String baseString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String baseNum = "0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        //生成随机数字
        for (int i = 0; i < 3; i++) {
            int number = random.nextInt(baseNum.length());
            sb.append(baseNum.charAt(number));
        }

        //生成随机字符
        for (int i = 0; i < 3; i++) {
            int number = random.nextInt(baseString.length());
            sb.append(baseString.charAt(number));
        }

        return sb.toString();
    }

    /**
     * SQL条件自定义拼接处理
     *
     * @param sql       SQL文
     * @param condition 检索条件
     * @return SQL文
     */
    @Override
    protected String prepareSQL(String sql, Map<String, Object> condition) {
        String roleUid = (String) SearchConditionUtils.getConditionValue(condition, "roleUid");

        StringBuilder sb = new StringBuilder("");
        //角色权限指定的场合，查询指定角色的用户
        if (StringUtils.isNotEmpty(roleUid)) {
            String[] roleArray = roleUid.split(",");
            sb.append(" AND EXISTS (SELECT 1 FROM sys_user_role user_role WHERE user_role.record_status = 1 AND user_role.user_uid = main.uid AND (");
            int i = 0;
            for (String role : roleArray) {
                if (i == 0) {
                    //第一个元素不加 or
                    sb.append(" user_role.role_uid = '" + role + "'");
                }
                else {
                    sb.append(" OR user_role.role_uid = '" + role + "'");
                }
                i++;
            }
            sb.append(")  LIMIT 1) ");
        }
        //角色权限指定的场合，查询指定角色的用户
        sql = String.format(sql, sb.toString());

        return sql;
    }

    /**
     * 根据考勤号查询用户
     *
     * @param attendanceNo 考勤号
     * @return 用户信息
     */
    public UserExt findByAttendanceNo(String attendanceNo) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.attendanceNo", attendanceNo);

        List<UserExt> list = this.search(UserExt.class, this.getSearchSQL(condition), condition);
        if (CollectionUtils.isNotEmpty(list)) {
            UserExt userExt = list.get(0);
            //获取用户关联信息
            userExt.setUserInfo(this.userInfoService.getDetail(userExt.getUid()));
            return userExt;
        }
        return null;
    }

    /**
     * 根据邮箱地址查询用户
     *
     * @param userMail 邮箱地址
     * @return 用户信息
     */
    public UserExt findByUserMail(String userMail) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.userMail", userMail);

        List<UserExt> list = this.search(UserExt.class, this.getSearchSQL(condition), condition);

        if (CollectionUtils.isNotEmpty(list)) {
            UserExt userExt = list.get(0);
            //获取用户关联信息
            userExt.setUserInfo(this.userInfoService.getDetail(userExt.getUid()));
            return userExt;
        }
        return null;
    }

    /**
     * 根据城市编码取得城市用户信息
     *
     * @param cityNo 城市编码
     * @return 城市用户信息
     */
    public UserExt getCityUserInfo(String cityNo) {
        String citySpecialAdmin = cityNo + "SpecialAdmin";
        String cityUserKey = CACHE_PREFIX_CITY_USER_NO_KEY + "_" + cityNo;
        UserExt user = redisCacheManager.get(UserExt.class, cityUserKey);
        if (user == null) {
            user = this.findByUserCd(citySpecialAdmin);
            if (user != null) {
                redisCacheManager.set(cityUserKey, user);
            }
        }
        return user;
    }

    /**
     * 取得邮箱密码
     *
     * @param uid   用户UID
     * @param isDes 是否DES加密
     * @return 邮箱密码
     */
    public String getMailPassword(String uid, boolean isDes) {
        return this.userInfoService.getMailPassword(uid, isDes);
    }
}