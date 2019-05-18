package com.bpms.core.security;

import com.google.common.collect.Lists;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.utils.Digests;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.entity.ext.RoleExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.RoleService;
import com.bpms.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.stream.Collectors;

public class ShiroDbRealm extends CasRealm {
    /**
     * 是否是Shiro模拟用户登录
     */
    @Value("${shiro.simulation.user.login:false}")
    protected boolean shiroSimulationUserLogin;

    /**
     * 是否启用CAS统一认证中心
     */
    @Value("${shiro.cas.enable:false}")
    protected boolean shiroCasEnable;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        if (shiroCasEnable) {
            super.doGetAuthenticationInfo(authcToken);
        }

        String userName = (String) authcToken.getPrincipal();

        if (StringUtils.isEmpty(userName)) {
            return null;
        }

        UserExt user = userService.findByUserCd(userName);

        //用户账号不存在的场合，判断是否是使用手机号码登录
        if (user == null && StringUtils.length(userName) == 11 && StringUtils.isNumeric(userName) && userName.startsWith("1")) {
            //根据手机号码查找用户、并使用该手机号码对应的用户账号进行登录
            user = userService.findByMobile(userName);
            if (user != null) {
                userName = user.getUserCd();
            }
            ((CasToken) authcToken).setUserId(userName);
        }

        if (user == null || Objects.equals(user.getRecordStatus(), Integer.valueOf(CmnConsts.RECORD_STATUS_DELETE))) {
            throw new AccountException("用户名或密码错误。");
        }
        // 启用CAS统一认证中心后相关校验在CAS中校验
        else if (Objects.equals(user.getRecordStatus(), Integer.valueOf(CmnConsts.RECORD_STATUS_STOP))) {
            throw new LockedAccountException("账号已停用，请与系统管理员联系。");
        }
        else if (Objects.equals(user.getAccountLock(), CmnConsts.ACCOUNT_LOCK)) {
            throw new LockedAccountException("账号已锁定，请与系统管理员联系。");
        }
        else if (!validateLoginTime(user.getStartLoginTime(), user.getEndLoginTime())) {
            throw new DisabledAccountException("账号不在登录许可时间范围内，请与系统管理员联系。");
        }

        //初始化指定用户的组织信息
        userService.initDeptInfo(user);

        //只有正式用户登录的场合，才需要以下相关逻辑处理
        if (!shiroSimulationUserLogin) {
            Map<String, Object> condition = new HashMap<>();
            condition.put(":userUid", user.getUid());
            userService.getDao().executeUpdate(this.userService.getSQL("sys/User/updateLoginCountAndTime"), condition);
        }

        if (shiroCasEnable) {
            return new SimpleAuthenticationInfo(new ShiroUser(user, ((CasToken) authcToken).getHost()), authcToken.getCredentials(), user.getUserName());
        }
        else {
            return new SimpleAuthenticationInfo(new ShiroUser(user, ((CasToken) authcToken).getHost()), Digests.shaPassword256(user.getUserCd()) + user.getPassword(), user.getUserName());
        }
    }

    /**
     * 校验登录时间是否在有效期内
     *
     * @param startLoginTime 开始时间
     * @param endLoginTime   结束时间
     * @return 是否在有效期内
     */
    private Boolean validateLoginTime(Date startLoginTime, Date endLoginTime) {
        Long startTime = startLoginTime == null ? Long.MIN_VALUE : startLoginTime.getTime();
        Long endTime = endLoginTime == null ? Long.MAX_VALUE : endLoginTime.getTime();
        Long currentTime = System.currentTimeMillis();
        return startTime <= currentTime && endTime >= currentTime;
    }

    /**
     * 授权查询回调函数,进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        List<String> roles = Lists.newArrayList();

        //Shiro模拟用户登录的场合、直接返回
        if (shiroSimulationUserLogin) {
            //模拟用户登录的场合、补全角色权限信息
            roles.add("shiroSimulationUserLogin");
            shiroUser.setRoles(roles);
            return info;
        }

        //用户当前所有权限
        List<String> permissions = userService.getCurrentUserPermissions();

        //用户当前所有角色
        List<RoleExt> roleList = roleService.findByUserUid(shiroUser.getUserUid());
        roles.addAll(roleList.stream().map(RoleExt::getRoleCode).collect(Collectors.toList()));
        info.addStringPermissions(permissions);

        //设置应用的默认角色
        for (RoleExt role : roleList) {
            if (!roles.contains(role.getAppCode())) {
                roles.add(role.getAppCode());
            }
        }

        shiroUser.setRoles(roles);

        info.addRoles(roles);
        //返回授权信息
        return info;
    }
}
