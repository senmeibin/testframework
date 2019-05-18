package com.bpms.sys.service;

import com.google.common.collect.Maps;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.utils.DateUtils;
import com.bpms.sys.dao.OnlineUserDao;
import com.bpms.sys.entity.ext.OnlineUserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 在线用户服务类
 */
@Service
public class OnlineUserService extends SysBaseService<OnlineUserExt> {
    /**
     * 缓存KEY前缀
     */
    public static final String CACHE_PREFIX_KEY = "ONLINE_USER_CACHE";

    @Autowired
    private OnlineUserDao onlineUserDao;

    @Override
    public OnlineUserDao getDao() {
        return onlineUserDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(OnlineUserExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(OnlineUserExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected OnlineUserExt saveBefore(OnlineUserExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected OnlineUserExt saveAfter(OnlineUserExt entity) {
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
    protected Boolean deleteBefore(OnlineUserExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(OnlineUserExt entity) {
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
        return true;
    }

    /**
     * 取得在线用户信息
     *
     * @param userUid 用户UID
     * @return 在线用户实体对象
     */
    public OnlineUserExt getOnlineUser(String userUid) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, userUid);

        //取得redis数据
        OnlineUserExt entity = redisCacheManager.get(OnlineUserExt.class, cacheKey);

        if (entity == null) {
            entity = this.findOne(userUid);

            redisCacheManager.set(cacheKey, entity);
        }

        return entity;
    }

    /**
     * 在线用户状态更新
     *
     * @param sessionId 会话ID
     * @param userUid   用户UID
     * @param userCd    用户名
     * @param userName  用户名称
     * @param ip        登录IP
     */
    @Transactional
    public void updateSession(String sessionId, String userUid, String userCd, String userName, String ip) {
        OnlineUserExt onlineUser = new OnlineUserExt();
        onlineUser.setUid(userUid);
        onlineUser.setSessionId(sessionId);
        onlineUser.setUserCd(userCd);
        onlineUser.setUserName(userName);
        onlineUser.setRemoteIp(ip);
        onlineUser.setNetbiosMachineName("");
        onlineUser.setRecordStatus(1);

        save(onlineUser);

        // 更新缓存数据
        this.updateCache(userUid, 1);
    }

    /**
     * 在线用户状态更新
     *
     * @param userUid 用户UID
     */
    @Transactional
    public int updateSession(String userUid) {
        String sessionTimeoutEnable = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "ONLINE_SESSION_TIMEOUT_ENABLE", "ON");

        //会话超时功能关闭的场合，处理中止
        if (!StringUtils.equals(sessionTimeoutEnable, "ON")) {
            return 0;
        }

        // 发行SQL文
        String sql = "UPDATE sys_online_user SET update_date = :update_date WHERE uid = :uid";

        // 绑定参数
        Map<String, Object> map = Maps.newHashMap();
        map.put("uid", userUid);
        map.put("update_date", new Date());

        // 更新缓存数据
        this.updateCache(userUid, 1);

        return this.onlineUserDao.executeUpdate(sql, map);
    }

    /**
     * 强制退出处理(根据ID集合强制退出)
     *
     * @param ids ID集合
     */
    @Transactional
    public void forceLogout(String ids) {
        // 分割UID
        String[] uids = ids.split(",");

        // 循环强制退出
        for (String uid : uids) {
            // 同步更新缓存数据
            this.updateCache(uid, 9);

            getDao().delete(uid);
        }
    }

    /**
     * 更新缓存数据
     *
     * @param userUid 用户UID
     */
    private void updateCache(String userUid, Integer recordStatus) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, userUid);

        //取得redis数据
        OnlineUserExt entity = redisCacheManager.get(OnlineUserExt.class, cacheKey);

        if (entity == null) {
            entity = this.findOne(userUid);
        }

        //设置记录状态
        entity.setRecordStatus(recordStatus);

        //设置最后更新时间
        entity.setUpdateDate(DateUtils.getNowDate());

        redisCacheManager.set(cacheKey, entity);
    }
}