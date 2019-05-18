package com.bpms.sys.service;

import com.bpms.cmn.utility.ApplicationPropertiesUtils;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.consts.MailConsts;
import com.bpms.core.consts.SmsConsts;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.DesUtils;
import com.bpms.sys.dao.ParameterDao;
import com.bpms.sys.entity.MailAccount;
import com.bpms.sys.entity.ext.ParameterExt;
import com.bpms.sys.entity.ext.SmsAccount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数配置服务类
 */
@Service
public class ParameterService extends SysBaseService<ParameterExt> {
    /**
     * 缓存KEY前缀
     */
    public static final String CACHE_PREFIX_KEY = "PARAMETER_CACHE";

    /**
     * 空值标识常量
     */
    private static final String NULL_VALUE = "(NULL_VALUE)";

    /**
     * 系统编号（非必须）
     */
    private String systemCode;

    @Autowired
    private ParameterDao parameterDao;

    @Override
    public ParameterDao getDao() {
        return parameterDao;
    }

    /**
     * 系统启动时自动更新系统版本号
     */
    @PostConstruct
    private void updateVersion() {
        synchronized (this) {
            systemCode = ApplicationPropertiesUtils.getValue("system.code");
            ParameterExt parameter = this.findByAppCodeAndName(StringUtils.EMPTY, AppCodeConsts.APP_COMMON, "VERSION");
            if (parameter == null) {
                return;
            }
            String nowDate = DateUtils.getNowDateString(CmnConsts.DATE_TIME_FORMAT_SHORT);
            log.info(String.format("系统启动，自动更新系统版本号为：%s。", nowDate));
            parameter.setValue(nowDate);
            this.save(parameter);
        }
    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ParameterExt saveBefore(ParameterExt entity) {
        Map<String, Object> condition = new HashMap<>();
        if (StringUtils.isEmpty(entity.getSystemCode())) {
            condition.put("main.systemCode", "IS NULL");
        }
        else {
            condition.put("main.systemCode", entity.getSystemCode());
        }
        condition.put("main.appCode", entity.getAppCode());
        condition.put("main.name", entity.getName());
        List<ParameterExt> list = this.search(condition);

        String message = String.format("指定的参数名称(%s)已存在，请重新输入。", entity.getName());

        //参数名称重复性校验（系统编号、应用编号、参数名称 必须唯一）
        if (list.size() == 0) {
            return entity;
        }
        else if (list.size() > 1) {
            throw new ServiceValidationException(message);
        }
        else {
            if (StringUtils.isEmpty(entity.getUid())) {
                throw new ServiceValidationException(message);
            }
            else if (!StringUtils.equals(list.get(0).getUid(), entity.getUid())) {
                throw new ServiceValidationException(message);
            }
        }

        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ParameterExt saveAfter(ParameterExt entity) {
        //删除缓存数据
        this.deleteCache(entity);

        return entity;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(String ids) {
        String[] uids = ids.split(",");

        // 循环删除缓存
        for (String uid : uids) {
            this.deleteCache(this.findOne(uid));
        }

        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ParameterExt entity) {
        //删除缓存数据
        this.deleteCache(entity);

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
        //删除缓存数据
        this.deleteCache(this.findOne(uid));

        return true;
    }

    /**
     * 根据应用编号和参数名称查找参数配置【字符值】
     *
     * @param appCode 应用编号
     * @param name    参数名称
     * @return 参数配置值
     */
    public String getValue(String appCode, String name) {
        return this.getValue(appCode, name, StringUtils.EMPTY);
    }

    /**
     * 根据应用编号和参数名称查找参数值【字符值】
     *
     * @param appCode      应用编号
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return 参数配置值
     */
    @Transactional(readOnly = true)
    public String getValue(String appCode, String name, String defaultValue) {
        //系统编号未配置的场合、根据【应用编号、参数名称】查找参数值
        if (StringUtils.isEmpty(systemCode)) {
            String value = this.getParameterValue(StringUtils.EMPTY, appCode, name);

            if (StringUtils.equals(value, NULL_VALUE)) {
                value = defaultValue;
                redisCacheManager.set(this.getCacheKey(StringUtils.EMPTY, appCode, name), value);
            }
            return value;
        }
        else {
            //优先根据【系统编号、应用编号、参数名称】查找参数值
            String value = this.getParameterValue(systemCode, appCode, name);

            //参数值为空的场合，再根据【应用编号、参数名称】查找参数值
            if (StringUtils.equals(value, NULL_VALUE)) {
                value = this.getParameterValue(StringUtils.EMPTY, appCode, name);
                if (StringUtils.equals(value, NULL_VALUE)) {
                    value = defaultValue;
                    redisCacheManager.set(this.getCacheKey(StringUtils.EMPTY, appCode, name), value);
                }
            }
            return value;
        }
    }

    /**
     * 根据系统编号、应用编号和参数名称查找参数值【字符值】
     *
     * @param systemCode 系统编号
     * @param appCode    应用编号
     * @param name       参数名称
     * @return 参数值
     */
    private String getParameterValue(String systemCode, String appCode, String name) {
        String cacheKey = this.getCacheKey(systemCode, appCode, name);

        String value = redisCacheManager.get(String.class, cacheKey);

        if (StringUtils.isEmpty(value)) {
            //根据应用编号及参数名称取得参数实体对象
            ParameterExt parameter = this.findByAppCodeAndName(systemCode, appCode, name);

            if (parameter == null) {
                value = NULL_VALUE;
            }
            else {
                value = parameter.getValue();
            }
            redisCacheManager.set(cacheKey, value);
        }
        return value;
    }

    /**
     * 取得Redis Cache Key
     *
     * @param systemCode 系统编号
     * @param appCode    应用编号
     * @param name       参数名称
     * @return Redis Cache Key
     */
    private String getCacheKey(String systemCode, String appCode, String name) {
        String cacheKey = String.format("%s_%s_%s", CACHE_PREFIX_KEY, appCode, name);
        if (StringUtils.isNotEmpty(systemCode)) {
            cacheKey = String.format("%s_%s_%s_%s", CACHE_PREFIX_KEY, systemCode, appCode, name);
        }
        return cacheKey;
    }

    /**
     * 根据应用编号和参数名称查找参数配置【整数值】
     *
     * @param appCode      应用编号
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return 参数配置值
     */
    public int getIntValue(String appCode, String name, int defaultValue) {
        String value = this.getValue(appCode, name);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            log.error(String.format("参数配置错误(应用编号:%s/参数名称:%s/参数值:%s)；错误消息：%s", appCode, name, value, ex.getMessage()));
            return defaultValue;
        }
    }

    /**
     * 删除缓存数据
     *
     * @param entity 参数实体对象
     */
    private void deleteCache(ParameterExt entity) {
        String cacheKey = this.getCacheKey(entity.getSystemCode(), entity.getAppCode(), entity.getName());
        //删除redis数据
        redisCacheManager.delete(cacheKey);
    }

    /**
     * 设定邮件参数
     *
     * @param mailAccount 邮件参数类
     */
    @Transactional
    public void setMailAccount(MailAccount mailAccount) {
        //smtp不为空 更新
        if (StringUtils.isNotEmpty(mailAccount.getSmtp())) {
            this.setValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_SMTP, mailAccount.getSmtp(), "邮件SMTP服务地址", 1);
        }
        //服务器端口不为空 更新
        if (StringUtils.isNotEmpty(mailAccount.getPort())) {
            this.setValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_PORT, mailAccount.getPort(), "邮件服务器端口", 1);
        }
        //发件人不为空 更新
        if (StringUtils.isNotEmpty(mailAccount.getSendUser())) {
            this.setValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_SEND_USER, mailAccount.getSendUser(), "邮件发件人", 1);
        }
        //发送账号不为空 更新
        if (StringUtils.isNotEmpty(mailAccount.getUserName())) {
            this.setValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_USER_ACCOUNT, mailAccount.getUserName(), "邮件发送账号", 1);
        }
        //密码不为空 更新
        if (StringUtils.isNotEmpty(mailAccount.getPassword())) {
            //密码进行加密处理
            this.setValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_USER_PASSWORD, DesUtils.encrypt(mailAccount.getPassword()), "邮件发送账号密码", 1);
        }
    }

    /**
     * 设定短信参数
     *
     * @param smsAccount 短信参数类
     */
    @Transactional
    public void setSmsAccount(SmsAccount smsAccount) {
        //供应商不为空 更新
        if (StringUtils.isNotEmpty(smsAccount.getVendor())) {
            this.setValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_VENDOR, smsAccount.getVendor(), "短信供应商", 1);
        }
        //短信url不为空 更新
        if (StringUtils.isNotEmpty(smsAccount.getUrl())) {
            this.setValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_URL, smsAccount.getUrl(), "短信接口地址", 1);
        }
        //短信账号不为空 更新
        if (StringUtils.isNotEmpty(smsAccount.getAccount())) {
            this.setValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_ACCOUNT, smsAccount.getAccount(), "短信接口账号", 1);
        }
        //密码不为空 更新
        if (StringUtils.isNotEmpty(smsAccount.getPassword())) {
            //密码进行加密处理
            this.setValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_PASSWORD, DesUtils.encrypt(smsAccount.getPassword()), "短信接口密码", 1);
        }
    }

    /**
     * 参数设置
     *
     * @param appCode     应用编号
     * @param name        参数名称
     * @param value       参数值
     * @param description 参数描述
     * @param regSystem   系统注册
     */
    @Transactional
    public void setValue(String appCode, String name, String value, String description, Integer regSystem) {
        //根据应用编号及参数名称取得参数实体对象
        ParameterExt parameter = this.findByAppCodeAndName(StringUtils.EMPTY, appCode, name);
        //不存在参数配置
        if (parameter == null) {
            parameter = new ParameterExt();
        }
        parameter.setAppCode(appCode);
        parameter.setValue(value);
        parameter.setDescription(description);
        parameter.setName(name);
        parameter.setRegSystem(regSystem);
        this.save(parameter);
    }

    /**
     * 根据应用编号及参数名称取得参数实体对象
     *
     * @param systemCode 系统编号
     * @param appCode    应用编号
     * @param name       参数名称
     * @return 参数配置实体对象
     */
    private ParameterExt findByAppCodeAndName(String systemCode, String appCode, String name) {
        Map<String, Object> condition = new HashMap<>();

        if (StringUtils.isEmpty(systemCode)) {
            condition.put("main.systemCode", "IS NULL");
        }
        else {
            condition.put("main.systemCode", systemCode);
        }
        condition.put("main.appCode", appCode);
        condition.put("main.name", name);
        //取启用状态的 (1:有效）
        condition.put("main.recordStatus", "1");
        List<ParameterExt> parameterList = this.search(condition, 1);

        if (CollectionUtils.isEmpty(parameterList)) {
            return null;
        }

        return parameterList.get(0);
    }

    /**
     * 取得公司名称
     *
     * @return 公司名称
     */
    public String getCompanyName() {
        return this.getValue(AppCodeConsts.APP_COMMON, "COMPANY_NAME", "XXX集团");
    }

    /**
     * 取得系统名称
     *
     * @return 系统名称
     */
    public String getSystemName() {
        return this.getValue(AppCodeConsts.APP_COMMON, "SYSTEM_NAME", "IMS管理平台");
    }

    /**
     * 取得系统版本号
     *
     * @return 版本号
     */
    public String getVersion() {
        return this.getValue(AppCodeConsts.APP_COMMON, "VERSION", "1");
    }

    /**
     * 取得调试模式
     *
     * @return 版本号
     */
    public String getDebugMode() {
        return this.getValue(AppCodeConsts.APP_COMMON, "DEBUG_MODE", "0");
    }

    /**
     * 取得服务器地址
     *
     * @return 服务器地址
     */
    public String getServerAddress() {
        return this.getValue(AppCodeConsts.APP_COMMON, "SERVER_ADDRESS");
    }

    /**
     * 把Excel的导入数据写入DB中，名称相同的就更新否则就新建
     *
     * @param parameterList 需要插入DB列表
     */
    @Transactional
    public void saveParameterListData(List<ParameterExt> parameterList) {
        int rowIndex = 0;
        for (ParameterExt entity : parameterList) {
            rowIndex = rowIndex + 1;
            //查询是否存在
            Map<String, Object> condition = new HashMap<>();
            condition.put("main.appCode", entity.getAppCode());
            condition.put("main.name", entity.getName());
            List<ParameterExt> existsParameterList = this.getDao().search(ParameterExt.class, this.getSearchSQL(condition), condition);
            //存在
            if (CollectionUtils.isNotEmpty(existsParameterList)) {
                ParameterExt existsParameterExt = existsParameterList.get(0);
                //参数描述
                existsParameterExt.setAppCode(entity.getAppCode());
                //参数描述
                existsParameterExt.setDescription(entity.getDescription());
                //参数值
                existsParameterExt.setValue(entity.getValue());
                //备注
                existsParameterExt.setRemark(entity.getRemark());
            }
            //不存在
            else {
                this.getDao().save(entity);
            }
        }
    }
}