package com.bpms.core.sync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.activemq.consts.JMSConsts;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.core.dao.DummyDao;
import com.bpms.core.entity.BaseEntity;
import com.bpms.core.entity.CoreEntity;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.dao.UserDao;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.SyncEntityService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 同步数据服务类
 */
@Service
public class SyncDataService {
    private Logger log = LoggerFactory.getLogger(SyncDataService.class);

    @Autowired
    private ActiveMQSenderService activeMQSenderService;

    @Autowired
    private DummyDao dummyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SyncEntityService syncEntityService;

    /**
     * 是否是UDC服务器
     */
    @Value("${udc.server.enable:false}")
    private boolean isUdcServer;

    @Value("${activemq.jms.receiverClientId:TOPIC_SERVER_UDC}")
    private String udcServerClientId;

    /**
     * 是否订阅UDC主题（如多个应用服务连接同一DB时，只需主应用服务订阅UDC主题即可）
     */
    @Value("${activemq.jms.subscribe.topic:true}")
    private boolean subscribeTopic;

    /**
     * 同步数据
     *
     * @param jsonString JSON字符数据
     */
    public void syncData(String jsonString) {
        if (!subscribeTopic) {
            return;
        }

        //转换为JSON字符串
        JSONObject jsonObject = new JSONObject(jsonString);

        //SQL为空则为实体类型
        if (jsonObject.opt("SQL") == null) {
            //获取实体类型
            String entityClass = jsonObject.getString("entityClass");
            if (StringUtils.isNotEmpty(entityClass)) {
                try {
                    Class<? extends BaseEntity> cla = (Class<? extends BaseEntity>) Class.forName(entityClass);
                    BaseEntity obj = JsonUtils.parseJSON(jsonString, cla);
                    //用户信息同步的场合
                    if (obj instanceof UserExt) {
                        UserExt user = userDao.findOne(obj.getUid());
                        if (user != null) {
                            //最后【登录时间&登录次数&主题】各个系统保持不变
                            UserExt syncUser = ((UserExt) obj);
                            syncUser.setLastLoginTime(user.getLastLoginTime());
                            syncUser.setLoginCount(user.getLoginCount());
                            syncUser.setThemeName(user.getThemeName());
                        }
                    }
                    //保存实体类数据
                    dummyDao.save(obj);
                } catch (Exception e) {
                    log.error("同步数据失败。", e);
                }
            }
        }
        //不为空，则执行SQL语句
        else {
            Object conditionObject = jsonObject.get("SQL_CONDITION");
            if (conditionObject == null) {
                dummyDao.executeUpdate(jsonObject.getString("SQL"), null);
            }
            else {
                JSONObject conditionJson = (JSONObject) conditionObject;
                Map<String, Object> condition = new HashMap<>();
                Iterator it = conditionJson.keys();
                // 遍历jsonObject数据，添加到Map对象
                while (it.hasNext()) {
                    String key = String.valueOf(it.next());
                    condition.put(key, conditionJson.get(key));
                }
                dummyDao.executeUpdate(jsonObject.getString("SQL"), condition);
            }

        }
    }

    /**
     * 推送同步数据
     *
     * @param entity 实体类
     */
    public void pushSyncDataTopic(CoreEntity entity) {
        //UDC服务器，发送同步数据
        if (this.isUdcServer && !entity.isDirectPush()) {
            this.directPushSyncDataTopic(entity);
        }
    }

    /**
     * 直接推送同步数据
     *
     * @param entity 实体类
     */
    public void directPushSyncDataTopic(CoreEntity entity) {
        //用户信息推送场合
        if (entity instanceof UserExt) {
            //外包员工无需同步【外包员工regSystem值为2】
            if (((UserExt) entity).getRegSystem() != null && ((UserExt) entity).getRegSystem() == 2) {
                return;
            }
        }

        //UDC服务器，发送同步数据
        if (activeMQSenderService.activeMQEnabled() && this.isSyncData(entity.getClass().getName())) {
            try {
                //加入子类实体class信息
                entity.setEntityClass(entity.getClass().getName());
                //是否APP直接推送到UDC的数据
                entity.setDirectPush(true);
                //推送
                String jsonStr = JsonUtils.toJSON(entity);
                if (log.isDebugEnabled()) {
                    log.info("同步推送数据", jsonStr);
                }
                activeMQSenderService.pushTopic(JMSConsts.JMS_TOPIC_NAME_SYNC, jsonStr);
            } catch (JsonProcessingException e) {
                log.error("转换JSON失败，无法推送同步信息。", e);
            }
        }
    }

    /**
     * 推送同步数据
     *
     * @param entityClass 实体类名
     * @param sql         主键UID
     * @param condition
     */
    public void pushSyncDataTopic(String entityClass, String sql, Map<String, Object> condition) {
        //UDC服务器，发送同步数据
        if (activeMQSenderService.activeMQEnabled() && this.isUdcServer && this.isSyncData(entityClass)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("SQL", sql);
            jsonObject.put("SQL_CONDITION", condition);
            activeMQSenderService.pushTopic(JMSConsts.JMS_TOPIC_NAME_SYNC, jsonObject.toString());
        }
    }

    /**
     * 是否需要同步数据
     *
     * @param entityClass 操作的实体类
     * @return 是否同步
     */
    public boolean isSyncData(String entityClass) {
        return syncEntityService.isExistsEntityClassPath(entityClass);
    }

    /**
     * 是否为UDC服务器
     *
     * @return 是否服务器
     */
    public boolean isUdcServer() {
        return this.isUdcServer;
    }

    /**
     * 获取UDC服务器ClientID
     *
     * @return
     */
    public String getUdcServerClientId() {
        if (this.isUdcServer) {
            return udcServerClientId;
        }
        return StringUtils.EMPTY;
    }
}