package com.bpms.core.dao;

import com.bpms.cmn.utility.ApplicationPropertiesUtils;
import com.bpms.core.AppContext;
import com.bpms.core.cache.RedisCacheManager;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.CoreEntity;
import com.bpms.core.entity.DatabaseMetaData;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.entity.EncryptionColumn;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.sql.SqlReader;
import com.bpms.core.sync.SyncDataService;
import com.bpms.core.utils.*;
import com.bpms.sys.service.ParameterService;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;

import static com.bpms.core.utils.EntityUtils.getEntityListByMap;

/**
 * DAO 基类实现
 */
@Component
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T extends CoreEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseDao<T, ID> {
    private static Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);

    private static String TRUE_STRING = "true";

    /**
     * ParameterService对象
     */
    private ParameterService parameterService;

    /**
     * 实体管理器
     */
    private EntityManager entityManager;

    /**
     * Redis缓存管理器
     */
    private RedisCacheManager redisCacheManager;

    /**
     * 同步数据服务类
     */
    private SyncDataService syncDataService;

    /**
     * 字段加密是否启用
     */
    private boolean encryptionEnable;

    /**
     * SqlReader对象实例
     */
    protected SqlReader sqlReader;

    /**
     * JDBC驱动程序
     */
    private String jdbcUrl;

    public BaseDaoImpl(JpaEntityInformation<?, Serializable> entityInformation, EntityManager entityManager) {
        super((JpaEntityInformation<T, ?>) entityInformation, entityManager);
        this.entityManager = entityManager;
        this.parameterService = SpringUtils.getBean(ParameterService.class);
        this.redisCacheManager = SpringUtils.getBean(RedisCacheManager.class);
        this.syncDataService = SpringUtils.getBean(SyncDataService.class);
        this.sqlReader = SpringUtils.getBean(SqlReader.class);

        this.encryptionEnable = ApplicationPropertiesUtils.isEnable("encryption.field.enable");
        this.jdbcUrl = ApplicationPropertiesUtils.getValue("jdbc.driver");
    }

    /**
     * 取得实体类名
     *
     * @return 实体类名
     */
    @Override
    public Class getEntityClass() {
        return super.getDomainClass();
    }

    @Override
    public List<T> findAll() {
        //系统性能考虑，findAll方法禁用
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public void delete(ID id) {
        this.updateRecordStatus(id.toString(), CmnConsts.RECORD_STATUS_DELETE);
    }

    @Transactional
    public T save(T entity) {
        //删除当前表的缓存数据
        this.deleteMasterDataCache(entity);

        //是否APP直接推送到UDC的数据 - DB更新前的
        boolean isDirectPush = entity.isDirectPush();

        //对字段加密处理
        this.encryptionField(entity);

        entity = super.save(entity);

        //将DB更新前的值回执，发送同步数据
        entity.setDirectPush(isDirectPush);
        //UDC服务器，发送同步数据
        syncDataService.pushSyncDataTopic(entity);

        return entity;
    }

    /**
     * 对字段加密处理
     *
     * @param entity 实体类
     */
    private void encryptionField(T entity) {
        //没有启用字段加密，则不处理
        if (!encryptionEnable) {
            return;
        }
        //获取所有的字段信息
        Field[] fields = FieldUtils.getAllFields(entity.getClass());
        //循环字段
        for (Field field : fields) {
            String fieldName = field.getName();
            //判断是否存在加密列
            EncryptionColumn encryptionColumn = field.getAnnotation(EncryptionColumn.class);
            if (encryptionColumn != null) {
                try {
                    String value = (String) PropertyUtils.getProperty(entity, fieldName);
                    if (StringUtils.isNotEmpty(value)) {
                        PropertyUtils.setProperty(entity, fieldName, AESUtils.encrypt(value));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除当前表的缓存数据
     */
    private void deleteMasterDataCache(T entity) {
        //取得物理表名称
        String tableName = entity.getClass().getAnnotation(javax.persistence.Table.class).name();

        this.deleteMasterDataCache(tableName);
    }

    /**
     * 删除当前表的缓存数据
     *
     * @param tableName 物理表名称
     */
    private void deleteMasterDataCache(String tableName) {
        String cacheKey = String.format("CACHE_%s", tableName.toUpperCase());

        //取出所有符合前缀的RedisKey
        Set<String> keySet = redisCacheManager.keys(cacheKey + "*");

        if (CollectionUtils.isNotEmpty(keySet)) {
            //删除相应的key
            redisCacheManager.delete(keySet);
            if (log.isInfoEnabled()) {
                log.info(String.format("%s表相关Redis缓存被清空%s条缓存记录。", tableName, keySet.size()));
            }
        }
    }

    /**
     * 记录状态更新
     *
     * @param pkValue      主键值
     * @param recordStatus 记录状态
     * @return 更新记录数
     */
    @Override
    @Transactional
    public int updateRecordStatus(String pkValue, String recordStatus) {
        //取得物理表名称
        String tableName = getDomainClass().getAnnotation(javax.persistence.Table.class).name();

        //删除当前表的缓存数据
        this.deleteMasterDataCache(tableName);

        String pkName = EntityUtils.getPkName(this.getDomainClass());

        //发行SQL文
        String sql = String.format("UPDATE %s SET record_status = :recordStatus, update_date = NOW() WHERE uid = :uid", tableName);
        if (Hibernates.isSqlServer(this.jdbcUrl)) {
            sql = String.format("UPDATE %s SET record_status = :recordStatus, update_date = GETDATE() WHERE uid = :uid", tableName);
        }
        else if (Hibernates.isOracle(this.jdbcUrl)) {

        }

        //兼容非UID主键字段
        if (!StringUtils.equals(pkName, "uid")) {
            if (StringUtils.equals(recordStatus, "9")) {
                recordStatus = "2";
            }
            sql = String.format("UPDATE %s SET deleted = :recordStatus, modifyDate = NOW() WHERE %s = :uid", tableName, pkName);
            if (Hibernates.isSqlServer(this.jdbcUrl)) {
                sql = String.format("UPDATE %s SET deleted = :recordStatus, modifyDate = GETDATE() WHERE %s = :uid", tableName, pkName);
            }
            else if (Hibernates.isOracle(this.jdbcUrl)) {

            }
        }

        //绑定参数
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("uid", pkValue);
        condition.put("recordStatus", recordStatus);

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        int result = -1;
        boolean isException = false;
        try {
            result = executeUpdate(sql, condition);
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(result, sql, startTime, isException, condition);
        }

        return result;
    }

    /**
     * 根据条件集合更新指定字段集合的值（更新字段自动拼装、检索条件自动拼装）
     *
     * @param columnSetting 更新字段集合
     * @param condition     更新条件集合
     * @return 更新记录数
     */
    @Override
    @Autowired
    @Transactional
    public int executeUpdate(Map<String, Object> columnSetting, Map<String, Object> condition) {
        if (CollectionUtils.isEmpty(columnSetting)) {
            throw new ServiceException("更新字段不能为空。");
        }

        Map<String, Object> bindParams = Maps.newHashMap();
        //取得实体表名
        String tableName = getDomainClass().getAnnotation(javax.persistence.Table.class).name();

        //取得设置列
        String settingSql = StringUtils.EMPTY;
        for (String column : columnSetting.keySet()) {
            String bindParamName = String.format("update_field_%s", column);
            if (StringUtils.isEmpty(settingSql)) {
                settingSql += column + " = :" + bindParamName;
            }
            else {
                settingSql += ", " + column + " = :" + bindParamName;
            }
            bindParams.put(bindParamName, columnSetting.get(column));
        }
        //取得条件并绑定条件参数
        String conditionSql = SearchConditionUtils.appendCondition(condition, bindParams, true);

        //拼装sql
        String sql = String.format("UPDATE %s SET update_date = NOW(), %s %s", tableName, settingSql, conditionSql);
        if (Hibernates.isSqlServer(this.jdbcUrl)) {
            sql = String.format("UPDATE %s SET update_date = GETDATE(), %s %s", tableName, settingSql, conditionSql);
        }
        else if (Hibernates.isOracle(this.jdbcUrl)) {

        }

        //发行SQL文
        Query query = this.entityManager.createNativeQuery(sql);

        //绑定参数
        DaoUtils.bindQueryParams(query, bindParams);

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        int result = -1;
        boolean isException = false;
        try {
            result = query.executeUpdate();
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(result, sql, startTime, isException, condition);
        }

        return result;
    }

    /**
     * 根据字段集合自动新增记录（新增记录的字段自动拼装）
     *
     * @param columnSetting 新增字段集合
     * @return 更新记录数
     */
    @Override
    @Autowired
    @Transactional(rollbackFor = Exception.class)
    public int executeInsert(Map<String, Object> columnSetting) {
        if (CollectionUtils.isEmpty(columnSetting)) {
            throw new ServiceException("新增记录字段不能为空。");
        }

        Map<String, Object> bindParams = Maps.newHashMap();
        //取得实体表名
        String tableName = getDomainClass().getAnnotation(javax.persistence.Table.class).name();

        String fields = StringUtils.EMPTY;
        String values = StringUtils.EMPTY;

        for (String column : columnSetting.keySet()) {
            String bindParamName = String.format("insert_field_%s", column);
            if (StringUtils.isEmpty(values)) {
                fields = column;
                values = ":" + bindParamName;
            }
            else {
                fields += ", " + column;
                values += ", :" + bindParamName;
            }
            bindParams.put(bindParamName, columnSetting.get(column));
        }

        //拼装sql
        String sql = String.format("INSERT INTO %s(%s, insert_date, update_date, record_status) VALUE (%s, NOW(), NOW(), 1)", tableName, fields, values);
        if (Hibernates.isSqlServer(this.jdbcUrl)) {
            sql = String.format("INSERT INTO %s(%s, insert_date, update_date, record_status) VALUE (%s, GETDATE(), GETDATE(), 1)", tableName, fields, values);
        }
        else if (Hibernates.isOracle(this.jdbcUrl)) {

        }

        //发行SQL文
        Query query = this.entityManager.createNativeQuery(sql);

        //绑定参数
        DaoUtils.bindQueryParams(query, bindParams);

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        int result = -1;
        boolean isException = false;
        try {
            result = query.executeUpdate();
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(result, sql, startTime, isException);
        }

        return result;
    }

    /**
     * 执行更新语句
     *
     * @param sql sql
     * @return 更新记录数
     */
    @Override
    @Transactional
    public int executeUpdate(String sql) {
        return this.executeUpdate(sql, null);
    }

    /**
     * 执行更新语句
     *
     * @param sql       sql
     * @param condition 自定义条件
     * @return 更新记录数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int executeUpdate(String sql, Map<String, Object> condition) {
        //发行SQL文
        Query query = this.entityManager.createNativeQuery(sql);

        //绑定参数
        DaoUtils.bindQueryParams(query, condition);

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        boolean isException = false;
        int result = -1;
        try {
            result = query.executeUpdate();
            //UDC服务器，发送同步数据
            this.syncDataService.pushSyncDataTopic(getDomainClass().getName(), sql, condition);
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(result, sql, startTime, isException, condition);
        }
        return result;
    }

    /**
     * 批量插入数据
     *
     * @param list 对应实体的列表
     * @return 插入件数
     */
    @Override
    @Transactional
    public int batchInsert(List<T> list) {
        //每次批量insert 件数， 未配置设定为100
        int batchInsertCount = ApplicationPropertiesUtils.getIntValue("jdbc.batchinsert.onetime.count");
        if (batchInsertCount == 0) {
            batchInsertCount = 100;
        }
        int insertCount = 0;
        if (log.isInfoEnabled()) {
            log.info("本次每回插入件数：{}", batchInsertCount);
        }
        for (int i = 0; i < list.size(); i++) {
            this.entityManager.persist(list.get(i));
            insertCount++;
            //每n（设置的每回insert数量）条或者最后一次不足n条时flush一次
            if (i % batchInsertCount == 0 || i == (list.size() - 1)) {
                this.entityManager.flush();
                this.entityManager.clear();
            }
        }
        return insertCount;
    }

    /**
     * 批量更新数据
     *
     * @param list 对应实体的列表
     * @return 更新件数
     */
    @Override
    @Transactional
    public int batchUpdate(List<T> list) {
        //每次批量insert 件数， 未配置设定为100
        int batchInsertCount = ApplicationPropertiesUtils.getIntValue("jdbc.batchinsert.onetime.count");
        if (batchInsertCount == 0) {
            batchInsertCount = 100;
        }
        int updateCount = 0;
        if (log.isInfoEnabled()) {
            log.info("本次每回更新件数：{}", batchInsertCount);
        }
        for (int i = 0; i < list.size(); i++) {
            this.entityManager.merge(list.get(i));
            updateCount++;
            //每n（设置的每回update数量）条或者最后一次不足n条时flush一次
            if (i % batchInsertCount == 0 || i == (list.size() - 1)) {
                this.entityManager.flush();
                this.entityManager.clear();
            }
        }
        return updateCount;
    }

    /**
     * 根据指定的字段名称及值删除数据
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 删除记录数
     */
    @Override
    @Transactional
    public int delete(String fieldName, String fieldValue) {
        //取得物理表名称
        String tableName = getDomainClass().getAnnotation(javax.persistence.Table.class).name();

        return this.delete(tableName, fieldName, fieldValue);
    }

    /**
     * 根据指定的字段名称及值删除数据
     *
     * @param tableName  表名称
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 删除记录数
     */
    @Override
    @Transactional
    public int delete(String tableName, String fieldName, String fieldValue) {
        //删除当前表的缓存数据
        this.deleteMasterDataCache(tableName);

        //发行SQL文
        String sql = String.format("DELETE FROM %s WHERE %s = :%s", tableName, fieldName, fieldName);

        //绑定参数
        Map<String, Object> map = Maps.newHashMap();
        map.put(fieldName, fieldValue);

        return executeUpdate(sql, map);
    }

    /**
     * 单页查询
     *
     * @param cls 实体类
     * @param sql 查询SQL文
     * @return 实体对象列表
     */
    @Override
    public <E> List<E> search(Class<E> cls, String sql) {
        return search(cls, sql, null);
    }

    /**
     * 单页查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    @Override
    public <E> List<E> search(Class<E> cls, String sql, int recordLimit) {
        return search(cls, sql, null, recordLimit);
    }

    /**
     * 单页查询
     *
     * @param sql 查询SQL文
     * @return 实体对象列表
     */
    @Override
    public <E> List<E> search(String sql) {
        return search(sql, null);
    }

    /**
     * 单页查询
     *
     * @param sql         查询SQL文
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    @Override
    public <E> List<E> search(String sql, int recordLimit) {
        return search(sql, null, recordLimit);
    }

    /**
     * 单页查询
     *
     * @param cls       实体类
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @return 实体对象列表
     */
    @Override
    public <E> List<E> search(Class<E> cls, String sql, Map<String, Object> condition) {
        return EntityUtils.getEntityListByMap(this.getResultList(sql, condition), cls);
    }

    /**
     * 单页查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    @Override
    public <E> List<E> search(Class<E> cls, String sql, Map<String, Object> condition, int recordLimit) {
        return EntityUtils.getEntityListByMap(this.getResultList(sql, condition, recordLimit), cls);
    }

    /**
     * 单页查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @return 实体对象列表
     */
    @Override
    public <E> List<E> search(String sql, Map<String, Object> condition) {
        return EntityUtils.getEntityListByMap(this.getResultList(sql, condition), this.getDomainClass());
    }

    /**
     * 单页查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    @Override
    public <E> List<E> search(String sql, Map<String, Object> condition, int recordLimit) {
        return EntityUtils.getEntityListByMap(this.getResultList(sql, condition, recordLimit), this.getDomainClass());
    }

    /**
     * 查询记录件数
     * [注：根据记录件数查询专用SQL文查询记录件数]
     *
     * @param sql       记录件数查询专用SQL
     * @param condition 原始检索条件集合
     * @return 记录件数
     */
    @Override
    public int searchRecordCount(String sql, Map<String, Object> condition) {
        //查询最大记录数限制
        int searchMaxRecordCountLimit = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "SEARCH_MAX_RECORD_COUNT_LIMIT", 10000);

        //需要绑定的条件集合
        Map<String, Object> bindParams = new HashMap<>();

        String dynamicCondition = SearchConditionUtils.appendCondition(condition, bindParams);

        //动态插入条件【%s为老版动态条件占位符、${dynamicCondition}为新版动态条件占位符、推荐使用${dynamicCondition}占位符】
        sql = DaoUtils.resetSql(this.jdbcUrl, condition, sql, dynamicCondition);

        int recordCount;

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        HttpServletRequest request = AppContext.getRequest();
        Boolean onlySearchButtonClickCountRecord = false;
        if (request != null) {
            //只有检索按钮按下才统计记录件数
            onlySearchButtonClickCountRecord = BooleanUtils.toBoolean(request.getParameter("onlySearchButtonClickCountRecord"));
        }

        boolean isException = false;
        try {
            //只有检索按钮按下才统计记录件数
            if (onlySearchButtonClickCountRecord) {
                //是否是检索按钮按下
                boolean isSearchButtonClick = BooleanUtils.toBoolean(request.getParameter("isSearchButtonClick"));
                //JS实例变量名称
                String selfInstance = request.getParameter("selfInstance");

                //检索按钮按下查询
                if (isSearchButtonClick) {
                    //取得记录件数
                    recordCount = this.searchRecordCount(sql, bindParams, searchMaxRecordCountLimit);
                    //将记录件数设置到Session中
                    request.getSession().setAttribute(selfInstance, recordCount);
                }
                else {
                    recordCount = (int) request.getSession().getAttribute(selfInstance);
                }
            }
            else {
                //取得记录件数
                recordCount = this.searchRecordCount(sql, bindParams, searchMaxRecordCountLimit);
            }
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(-1, sql, startTime, isException, condition);
        }
        return recordCount;
    }

    /**
     * 查询记录件数
     *
     * @param sql                       记录件数查询专用SQL
     * @param bindParams                需要绑定的条件集合
     * @param searchMaxRecordCountLimit 查询最大记录数限制
     * @return 记录件数
     */
    private int searchRecordCount(String sql, Map<String, Object> bindParams, int searchMaxRecordCountLimit) {
        Query query = this.entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        //绑定查询参数
        DaoUtils.bindQueryParams(query, bindParams);

        //总记录数结果
        List countResultList = query.getResultList();
        //默认统计数为返回记录数大小
        int recordCount = countResultList.size();
        if (recordCount == 1) {
            Map countResultMap = ((Map) countResultList.get(0));
            //标准返回单条统计结果则取字段record_count中的值
            if (countResultMap.containsKey("record_count")) {
                Object count = countResultMap.get("record_count");
                //根据不同的数据库JDBC接口返回数据类型自动识别
                if (count instanceof BigInteger) {
                    recordCount = ((BigInteger) count).intValue();
                }
                else if (count instanceof Integer) {
                    recordCount = (Integer) count;
                }
            }
        }
        //超出检索结果集大小范围的场合 抛出异常
        if (recordCount > searchMaxRecordCountLimit) {
            throw new ServiceValidationException(String.format("根据指定的查询条件检索出<b>%s</b>条记录，<br/>已超过限定的 <b>%s</b> 条记录，<br/>请输入更严格的查询条件。", recordCount, searchMaxRecordCountLimit));
        }

        return recordCount;
    }

    /**
     * 分页排序查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    @Override
    public <E> Page<E> search(Class<E> cls, String sql, Map<String, Object> condition, PageRequest pageRequest) {
        return this.search(cls, sql, condition, pageRequest, -1);
    }

    /**
     * 分页排序查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @param recordCount 记录件数
     * @return 分页实体对象列表
     */
    @Override
    public <E> Page<E> search(Class<E> cls, String sql, Map<String, Object> condition, PageRequest pageRequest, int recordCount) {
        if (recordCount == 0) {
            return new PageImpl<>(new ArrayList<>(), pageRequest, recordCount);
        }

        //查询最大记录数限制
        int searchMaxRecordCountLimit = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "SEARCH_MAX_RECORD_COUNT_LIMIT", 10000);

        //查询最大分页记录数限制
        int searchMaxPageSizeLimit = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "SEARCH_MAX_PAGE_SIZE_LIMIT", 2000);

        //分页对象为空的场合(默认最大取得100条记录)
        if (pageRequest == null) {
            pageRequest = new PageRequest(0, 100);
        }

        //需要绑定的条件集合
        Map<String, Object> bindParams = new HashMap<>();

        String dynamicCondition = SearchConditionUtils.appendCondition(condition, bindParams);

        //动态插入条件【%s为老版动态条件占位符、${dynamicCondition}为新版动态条件占位符、推荐使用${dynamicCondition}占位符】
        sql = DaoUtils.resetSql(this.jdbcUrl, condition, sql, dynamicCondition);

        int pageSize = pageRequest.getPageSize();

        //无需分页的场合，最大查询5000件
        if (pageRequest.getPageSize() == Integer.MAX_VALUE) {
            pageSize = searchMaxPageSizeLimit;
        }

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        //需要分页的场合
        if (pageRequest.getPageSize() != Integer.MAX_VALUE && recordCount == -1) {
            HttpServletRequest request = AppContext.getRequest();
            Boolean onlySearchButtonClickCountRecord = false;
            if (request != null) {
                //只有检索按钮按下才统计记录件数
                onlySearchButtonClickCountRecord = BooleanUtils.toBoolean(request.getParameter("onlySearchButtonClickCountRecord"));
            }

            //只有检索按钮按下才统计记录件数
            if (onlySearchButtonClickCountRecord) {
                //是否是检索按钮按下
                boolean isSearchButtonClick = BooleanUtils.toBoolean(request.getParameter("isSearchButtonClick"));
                //JS实例变量名称
                String selfInstance = request.getParameter("selfInstance");

                //检索按钮按下查询
                if (isSearchButtonClick) {
                    //取得记录件数
                    recordCount = this.searchRecordCount(sql, condition, bindParams, searchMaxRecordCountLimit);
                    //将记录件数设置到Session中
                    request.getSession().setAttribute(selfInstance, recordCount);
                }
                else {
                    recordCount = (int) request.getSession().getAttribute(selfInstance);
                }
            }
            else {
                //取得记录件数
                recordCount = this.searchRecordCount(sql, condition, bindParams, searchMaxRecordCountLimit);
            }

            if (recordCount == 0) {
                //DSQL性能跟踪
                DaoUtils.tracePerformance(-1, sql, startTime, false, condition);

                return new PageImpl<>(new ArrayList<>(), pageRequest, recordCount);
            }
        }

        //开始位置
        int start = pageRequest.getPageNumber() * pageSize;

        //SQL分页查询
        sql = getPagerSQL(condition, pageRequest, sql, start, pageSize);

        //分页查询
        Query query = this.entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        int size = -1;
        Page<E> page;
        boolean isException = false;
        try {
            //设置查询参数
            DaoUtils.bindQueryParams(query, bindParams);
            List<HashMap> list = query.getResultList();

            //无需分页的场合
            if (pageRequest.getPageSize() == Integer.MAX_VALUE) {
                recordCount = list.size();
            }

            page = new PageImpl<>(getEntityListByMap(list, cls), pageRequest, recordCount);
            size = list.size();
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(size, sql, startTime, isException, condition);
        }

        //返回分页结果
        return page;
    }

    /**
     * 取得分页查询SQL
     *
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @param sql         SQL文
     * @param start       记录开始数
     * @param pageSize    每页显示记录数
     * @return 分页查询SQL
     */
    private String getPagerSQL(Map<String, Object> condition, PageRequest pageRequest, String sql, int start, int pageSize) {
        String includeOrderQuery = (String) SearchConditionUtils.getConditionValue(condition, "includeOrderQuery");

        //MYSQL数据库的场合
        if (Hibernates.isMysql(this.jdbcUrl)) {
            if (!StringUtils.equalsIgnoreCase(includeOrderQuery, TRUE_STRING)) {
                //追加排序字段
                sql = DaoUtils.appendSortField(sql, pageRequest.getSort());
            }

            return String.format("%s LIMIT %d,%d", sql, start, pageSize);
        }
        //SQLServer数据库的场合
        else if (Hibernates.isSqlServer(this.jdbcUrl)) {
            String sortField = DaoUtils.getSortField(pageRequest.getSort());

            //默认按UID倒序排序
            if (StringUtils.isEmpty(sortField)) {
                sortField = "uid DESC";
            }

            //SQLServer ROW_NUMBER() 默认从1开始
            int beginRowNum = start + 1;
            int endRowNum = start + pageSize;

            return String.format("SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY %s) AS row_num, * FROM (%s) AS T1) AS T WHERE row_num >= %s AND row_num <= %s ORDER BY %s", sortField, sql, beginRowNum, endRowNum, sortField);
        }

        return sql;
    }

    /**
     * 分页排序查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    @Override
    public Page<T> search(String sql, Map<String, Object> condition, PageRequest pageRequest) {
        return this.search(this.getDomainClass(), sql, condition, pageRequest);
    }

    /**
     * 根据查询SQL文查询记录件数
     *
     * @param sql                       查询SQL文
     * @param condition                 原始检索条件集合
     * @param bindParams                需要绑定的条件集合
     * @param searchMaxRecordCountLimit 查询最大记录数限制
     * @return 记录件数
     */
    private int searchRecordCount(String sql, Map<String, Object> condition, Map<String, Object> bindParams, int searchMaxRecordCountLimit) {
        String includeGroupQuery = (String) SearchConditionUtils.getConditionValue(condition, "includeGroupQuery");
        String cntSql;

        //列表查询包含分组查询的场合，记录数查询SQL需要将原始SQL作为视图进行查询
        if (StringUtils.equalsIgnoreCase(includeGroupQuery, TRUE_STRING)) {
            cntSql = String.format("SELECT COUNT(1) AS record_count FROM (%s) AS V", sql);
        }
        else {
            //第一个SELECT关键字位置
            int selectIndex = DaoUtils.getSelectKeyIndex(sql);
            //第一个FROM关键字位置（注：必须是以空格开始的FROM）
            int fromIndex = DaoUtils.getFromKeyIndex(sql);

            //拼装件数查询SQL文
            cntSql = String.format("%s COUNT(1) AS record_count %s", sql.substring(0, selectIndex + "SELECT".length()), sql.substring(fromIndex));
        }
        Query query = this.entityManager.createNativeQuery(cntSql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        //绑定查询参数
        DaoUtils.bindQueryParams(query, bindParams);

        //总记录数结果
        List countResultList = query.getResultList();
        //默认统计数为返回记录数大小
        int recordCount = countResultList.size();
        if (countResultList.size() == 1) {
            //标准返回单条统计结果则取字段record_count中的值
            Map resultMap = (Map) countResultList.get(0);
            Object count = resultMap.get("record_count");
            //根据不同的数据库JDBC接口返回数据类型自动识别
            if (count instanceof BigInteger) {
                recordCount = ((BigInteger) count).intValue();
            }
            else if (count instanceof Integer) {
                recordCount = (Integer) count;
            }
        }
        //超出检索结果集大小范围的场合 抛出异常
        if (recordCount > searchMaxRecordCountLimit) {
            throw new ServiceValidationException(String.format("根据指定的查询条件检索出<b>%s</b>条记录，<br/>已超过限定的 <b>%s</b> 条记录，<br/>请输入更严格的查询条件。", recordCount, searchMaxRecordCountLimit));
        }

        return recordCount;
    }

    /**
     * 数据查询
     *
     * @param sql 查询SQL文
     * @return JDBC原始查询结果
     */
    @Override
    public List getResultList(String sql) {
        //查询最大记录数限制
        int searchMaxRecordCountLimit = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "SEARCH_MAX_RECORD_COUNT_LIMIT", 10000);
        return this.getResultList(sql, null, searchMaxRecordCountLimit);
    }

    /**
     * 数据查询
     *
     * @param sql         查询SQL文
     * @param recordLimit 记录数限制
     * @return JDBC原始查询结果
     */
    @Override
    public List getResultList(String sql, int recordLimit) {
        return this.getResultList(sql, null, recordLimit);
    }

    /**
     * 数据查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @return JDBC原始查询结果
     */
    @Override
    public List getResultList(String sql, Map<String, Object> condition) {
        //查询最大记录数限制
        int searchMaxRecordCountLimit = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "SEARCH_MAX_RECORD_COUNT_LIMIT", 10000);
        return this.getResultList(sql, condition, searchMaxRecordCountLimit);
    }

    /**
     * 数据查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return JDBC原始查询结果
     */
    @Override
    public List getResultList(String sql, Map<String, Object> condition, int recordLimit) {
        return this.getResultList(sql, condition, null, recordLimit);
    }

    /**
     * 数据查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param sort        排序字段
     * @param recordLimit 记录数限制
     * @return JDBC原始查询结果
     */
    @Override
    public List getResultList(String sql, Map<String, Object> condition, Sort sort, int recordLimit) {
        if (recordLimit > 10000) {
            //为防止恶意程序加载整个数据库表中的数据，系统框架强制限定SQL最大查询记录数
            int searchMaxRecordCountLimit = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "SEARCH_MAX_RECORD_COUNT_LIMIT", 10000);

            if (recordLimit > searchMaxRecordCountLimit) {
                recordLimit = searchMaxRecordCountLimit;
                log.warn(String.format("超过查询最大记录数限制（一次最多查询%s条记录），系统自动设置为最大查询%s条记录。", searchMaxRecordCountLimit, searchMaxRecordCountLimit));
            }
        }

        //需要绑定的条件集合
        Map<String, Object> bindParams = new HashMap<>();

        String dynamicCondition = SearchConditionUtils.appendCondition(condition, bindParams);

        //动态插入条件【%s为老版动态条件占位符、${dynamicCondition}为新版动态条件占位符、推荐使用${dynamicCondition}占位符】
        sql = DaoUtils.resetSql(this.jdbcUrl, condition, sql, dynamicCondition);

        String includeOrderQuery = (String) SearchConditionUtils.getConditionValue(condition, "includeOrderQuery");
        if (!StringUtils.equalsIgnoreCase(includeOrderQuery, TRUE_STRING)) {
            //追加排序字段
            sql = DaoUtils.appendSortField(sql, sort);
        }

        //MYSQL数据库的场合
        if (Hibernates.isMysql(this.jdbcUrl)) {
            //强制限定最大查询记录数(不包含FOR UPDATE/LIMIT关键字）
            if (!this.containSpecialKeywords(sql)) {
                sql += String.format(" LIMIT %d, %d", 0, recordLimit);
            }
            if (StringUtils.endsWithIgnoreCase(sql, "FOR UPDATE")) {
                sql = sql.replace("FOR UPDATE", String.format("LIMIT %d, %d FOR UPDATE", 0, recordLimit));
            }
        }
        //SQLServer数据库的场合
        else if (Hibernates.isSqlServer(this.jdbcUrl)) {
            //强制限定最大查询记录数(不包含TOP/ROW_NUMBER关键字）
            if (!this.containSpecialKeywords(sql)) {
                //第一个SELECT关键字位置
                int selectIndex = DaoUtils.getSelectKeyIndex(sql);

                //查询最大记录数限制
                sql = String.format("SELECT TOP %s %s", recordLimit, sql.substring(selectIndex + "SELECT".length()));
            }
        }
        Query query = this.entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        boolean isException = false;
        List resultList = new ArrayList();
        try {
            //绑定查询参数
            DaoUtils.bindQueryParams(query, bindParams);

            resultList = query.getResultList();
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(resultList.size(), sql, startTime, isException, condition);
        }

        return resultList;
    }

    /**
     * 指定表中指定字段值重复性校验
     *
     * @param pkName               主键字段名称
     * @param pkValue              主键字段值
     * @param relationFieldName    关联外键字段名称
     * @param relationFieldValue   关联外键字段值
     * @param targetFieldName      被校验目标字段名称
     * @param targetFieldValue     被校验目标字段值
     * @param recordStatusIsActive 数据是否有效
     * @return true:数据重复/false:数据不重复
     */
    @Override
    public Boolean isDuplication(String pkName, String pkValue, String relationFieldName, String relationFieldValue, String targetFieldName, String targetFieldValue, Boolean recordStatusIsActive) {
        //取得物理表名称
        String tableName = getDomainClass().getAnnotation(javax.persistence.Table.class).name();

        if (StringUtils.isEmpty(pkValue)) {
            pkValue = null;
        }

        //实体属性名转换为数据库列名
        if (!this.fieldExistsInTable(tableName, pkName)) {
            pkName = DaoUtils.convertAttributeNameToColumnName(pkName);
        }
        if (!this.fieldExistsInTable(tableName, targetFieldName)) {
            targetFieldName = DaoUtils.convertAttributeNameToColumnName(targetFieldName);
        }
        if (!this.fieldExistsInTable(tableName, relationFieldName)) {
            relationFieldName = DaoUtils.convertAttributeNameToColumnName(relationFieldName);
        }

        //校验SQL文
        String sql = String.format("SELECT %s FROM %s WHERE %s = :targetFieldValue AND (%s <> :pkValue OR :pkValue IS NULL)", pkName, tableName, targetFieldName, pkName);

        //有效数据条件
        if (recordStatusIsActive) {
            sql = String.format("%s AND (record_status = 1 OR record_status = 8) ", sql);
        }

        //关联外键字段存在的场合
        if (!StringUtils.isEmpty(relationFieldName)) {
            sql += String.format(" AND %s = :relationFieldValue ", relationFieldName);
        }

        Query query = this.entityManager.createNativeQuery(sql);

        query.setParameter("targetFieldValue", targetFieldValue);
        query.setParameter("pkValue", pkValue);

        //关联外键字段存在的场合
        if (!StringUtils.isEmpty(relationFieldName)) {
            query.setParameter("relationFieldValue", relationFieldValue);
        }

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        int size = -1;
        boolean result;
        boolean isException = false;
        try {
            size = query.getResultList().size();
            result = (size != 0);
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(size, sql, startTime, isException);
        }

        return result;
    }

    /**
     * 取得指定表指定字段的最大编码值(注：适用于后台Master等小规模数据表)
     *
     * @param fieldName 字段名称
     * @param prefix    前缀
     * @return 最大编码值
     */
    @Override
    public String getMaxCode(String fieldName, String prefix) {
        //取得物理表名称
        String tableName = getDomainClass().getAnnotation(javax.persistence.Table.class).name();

        String sql = String.format("SELECT MAX(%s) max_value FROM %s ", fieldName, tableName);

        //前缀属性存在的场合
        if (StringUtils.isNotEmpty(prefix)) {
            sql = String.format("%s WHERE %s LIKE '%s'", sql, fieldName, prefix + "%");
        }

        Query query = this.entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        List<?> list;
        int size = -1;
        boolean isException = false;
        try {
            //返回结果
            list = query.getResultList();
            size = list.size();

            if (size > 0 && ((Map) list.get(0)).get("max_value") != null) {
                return ((Map) list.get(0)).get("max_value").toString();
            }
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(size, sql, startTime, isException);
        }

        return StringUtils.EMPTY;
    }

    /**
     * 取得指定表、指定字段数据列表【下拉框内容填充专用】
     *
     * @param tableName  表名称
     * @param valueField 下拉框值字段
     * @param textField  下拉框文本字段
     * @param condition  检索条件
     * @param orderBy    排序条件
     * @return 数据列表
     */
    @Override
    public List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField, Map<String, Object> condition, String orderBy) {
        //需要绑定的条件集合
        Map<String, Object> bindParams = new HashMap<>();

        //查询SQL文
        String sql = this.sqlReader.read("sys/Framework/getDropdownList");
        sql = String.format(sql, DaoUtils.concatFields(this.jdbcUrl, valueField), DaoUtils.concatFields(this.jdbcUrl, textField), tableName);

        //动态绑定查询条件
        sql = sql + SearchConditionUtils.appendCondition(condition, bindParams, true);

        if (StringUtils.isNotEmpty(orderBy)) {
            sql += " ORDER BY " + orderBy;
        }

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();
        List<DropdownEntity> list = new ArrayList<>();
        boolean isException = false;
        try {
            Query query = this.entityManager.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            //绑定查询参数
            DaoUtils.bindQueryParams(query, bindParams);

            //返回结果
            list = getEntityListByMap(query.getResultList(), DropdownEntity.class);
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(list.size(), sql, startTime, isException, condition);
        }
        return list;
    }

    /**
     * 根据指定的字段名称及值取得符合记录的记录数
     *
     * @param tableName  表名称
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 删除记录数
     */
    @Override
    public int countReferenceRecords(String tableName, String fieldName, String fieldValue) {
        //发行SQL文
        String sql = String.format("SELECT COUNT(1) record_count FROM %s WHERE %s = :%s AND record_status = " + CmnConsts.RECORD_STATUS_ACTIVE, tableName, fieldName, fieldName);

        //deleted字段存在的场合
        if (fieldExistsInTable(tableName, "deleted")) {
            sql = String.format("SELECT COUNT(1) record_count FROM %s WHERE %s = :%s AND deleted = " + CmnConsts.RECORD_STATUS_ACTIVE, tableName, fieldName, fieldName);
        }

        //绑定参数
        Map<String, Object> condition = Maps.newHashMap();
        condition.put(fieldName, fieldValue);

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();

        List list = new ArrayList();
        boolean isException = false;
        try {
            list = executeQuery(sql, condition);

            Object count = ((Map) list.get(0)).get("record_count");
            //根据不同的数据库JDBC接口返回数据类型自动识别
            if (count instanceof BigInteger) {
                return ((BigInteger) count).intValue();
            }
            else if (count instanceof Integer) {
                return (Integer) count;
            }
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(list.size(), sql, startTime, isException, condition);
        }

        return 0;
    }

    /**
     * 执行查询语句
     *
     * @param sql       sql
     * @param condition 自定义条件
     * @return 查询记录集
     */
    private List executeQuery(String sql, Map<String, Object> condition) {
        //发行SQL文
        Query query = this.entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        //绑定参数
        DaoUtils.bindQueryParams(query, condition);

        //SQL执行开始时间
        Date startTime = DateUtils.getNowDate();
        List list = new ArrayList();
        boolean isException = false;
        try {
            list = query.getResultList();
        }
        catch (Exception e) {
            isException = true;
            throw e;
        }
        finally {
            //DSQL性能跟踪
            DaoUtils.tracePerformance(list.size(), sql, startTime, isException, condition);
        }
        return list;
    }

    /**
     * 获取当前数据库下所有的表名
     *
     * @return 当前数据库下所有的表名列表
     */
    @Override
    public List<String> getAllTableNames() {
        List<String> list = new ArrayList<>();
        //查询当前数据库中所有的表名
        List<Map<String, String>> tables = this.getResultList(this.sqlReader.read("sys/Framework/getAllTableNames"), null);
        //封装为list
        for (Map<String, String> map : tables) {
            list.addAll(map.values());
        }
        return list;
    }

    /**
     * 根据指定的表名称获取表元数据信息
     *
     * @param tableName 表名称
     * @return 指定的表名称的表元数据信息
     */
    @Override
    public List<DatabaseMetaData> getDatabaseMetaData(String tableName) {
        return this.search(DatabaseMetaData.class, String.format(this.sqlReader.read("sys/Framework/getDatabaseMetaData"), tableName));
    }

    /**
     * 判断指定表中是否存在指定的字段？
     *
     * @param tableName  表名称
     * @param columnName 字段名称
     * @return true：存在指定的字段
     */
    @Override
    public boolean fieldExistsInTable(String tableName, String columnName) {
        String sql = String.format(this.sqlReader.read("sys/Framework/fieldExistsInTable"), tableName, columnName);

        return this.getResultList(sql).size() == 1 ? true : false;
    }

    /**
     * 根据指定表名称、关联字段名称及字段值查询列表数据
     *
     * @param cls                  实体类
     * @param tableName            表名称
     * @param fieldName            字段名称
     * @param fieldValue           字段值
     * @param recordStatusIsActive 数据有效性
     * @param recordLimit          记录数限制
     * @return 实体对象列表
     */
    @Override
    public <E> List<E> getResultListByFieldValue(Class<E> cls, String tableName, String fieldName, String fieldValue, boolean recordStatusIsActive, int recordLimit) {
        //发行SQL文
        String sql = String.format("SELECT * FROM %s WHERE %s = :%s ", tableName, fieldName, fieldName);

        //绑定参数
        Map<String, Object> condition = Maps.newHashMap();
        condition.put(fieldName, fieldValue);

        //有效数据条件
        if (recordStatusIsActive) {
            sql = String.format("%s AND (record_status = 1 OR record_status = 8) ", sql);
        }

        return EntityUtils.getEntityListByMap(this.getResultList(sql, condition, recordLimit), cls);
    }

    /**
     * 判断sql 中是否包含某些特殊字符串（FOR UPDATE， LIMIT， SHOW TABLES）
     *
     * @param sql sql文
     * @return （true: 包含，  false：不包含）
     */
    private boolean containSpecialKeywords(String sql) {
        if (StringUtils.isEmpty(sql)) {
            return false;
        }

        if (Hibernates.isMysql(this.jdbcUrl)) {
            return (StringUtils.contains(sql, "FOR UPDATE") || StringUtils.contains(sql, "LIMIT") || StringUtils.contains(sql, "SHOW TABLES"));
        }
        else if (Hibernates.isSqlServer(this.jdbcUrl)) {
            return (StringUtils.contains(sql, "ROW_NUMBER") || StringUtils.contains(sql, " TOP "));
        }

        return false;
    }
}