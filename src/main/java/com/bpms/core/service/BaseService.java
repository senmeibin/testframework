package com.bpms.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.core.cache.RedisCacheManager;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.dao.BaseDao;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.entity.BaseEntity;
import com.bpms.core.entity.CoreEntity;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.EntityUtils;
import com.bpms.core.utils.JsonUtils;
import com.bpms.core.utils.SearchConditionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.persistence.MappedSuperclass;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

/**
 * Service处理基本类
 */
@SuppressWarnings("unchecked")
@MappedSuperclass
public abstract class BaseService<T extends CoreEntity> extends CoreService {
    /**
     * 缓存管理类
     */
    @Autowired
    public RedisCacheManager redisCacheManager;

    /**
     * 取得DAO实例的对象
     *
     * @return BaseDao
     */
    @SuppressWarnings("rawtypes")
    public abstract BaseDao getDao();

    /**
     * 取得实体类名
     *
     * @return 实体类
     */
    @SuppressWarnings("rawtypes")
    public Class getEntityClass() {
        return this.getDao().getEntityClass();
    }

    /**
     * 取得实体类名称[不带package路径]
     *
     * @return 实体类名称
     */
    public String getEntityName() {
        return EntityUtils.getEntityName(this.getEntityClass());
    }

    /**
     * 根据主键查询一条记录
     *
     * @param pkValue 主键值
     * @return 实体对象
     */
    public <ID extends Serializable> T findOne(ID pkValue) {
        return (T) getDao().findOne(pkValue);
    }

    /**
     * 数据保存处理
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    @Transactional
    public T save(T entity) {
        //数据保存前处理
        entity = saveBefore(entity);

        //数据验证处理
        validate(entity);

        //数据保存处理
        T savedEntity = (T) getDao().save(entity);

        //主键值设定
        EntityUtils.setPkValue(savedEntity, entity);

        //数据保存后处理
        saveAfter(entity);

        return entity;
    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    protected T saveBefore(T entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    protected T saveAfter(T entity) {
        return entity;
    }

    /**
     * 是否是数据添加模式
     *
     * @param entity 实体对象
     * @return true:添加模式
     */
    protected Boolean isAddMode(BaseEntity entity) {
        if (entity == null) {
            return true;
        }

        Object pkValue = EntityUtils.getPkValue(entity);
        if (pkValue == null) {
            return true;
        }
        if (pkValue instanceof Long && ((Long) pkValue).longValue() == 0) {
            return true;
        }
        if (pkValue instanceof Integer && ((Integer) pkValue) == 0) {
            return true;
        }
        if (pkValue instanceof BigInteger && ((BigInteger) pkValue).intValue() == 0) {
            return true;
        }
        if (pkValue instanceof String && StringUtils.isEmpty((String) pkValue)) {
            return true;
        }
        if (entity.isAddMode()) {
            return true;
        }

        return false;
    }

    /**
     * 是否是数据编辑模式
     *
     * @param entity 实体对象
     * @return true:编辑模式
     */
    protected boolean isEditMode(BaseEntity entity) {
        return !this.isAddMode(entity);
    }

    /**
     * 验证处理
     *
     * @param entity 实体对象
     */
    protected void validate(T entity) {

        //验证前处理
        this.validateBefore(entity);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        Validator validator = factory.getValidator();

        //数据验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);

        String result = StringUtils.EMPTY;
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            result = result + constraintViolation.getMessage() + "\n";
        }

        //验证失败的场合
        if (StringUtils.isNotEmpty(result)) {
            throw new ServiceException(result);
        }

        //验证后处理
        this.validateAfter(entity);
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    protected void validateBefore(T entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    protected void validateAfter(T entity) {

    }

    /**
     * 数据删除处理
     *
     * @param entity 实体对象
     */
    @Transactional
    public void delete(T entity) {
        //数据删除前处理
        if (!this.deleteBefore(entity)) {
            return;
        }

        //数据删除处理
        getDao().delete(EntityUtils.getPkValue(entity));

        //数据删除后处理
        this.deleteAfter(entity);
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    protected Boolean deleteBefore(T entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    protected Boolean deleteAfter(T entity) {
        return true;
    }

    /**
     * 数据删除处理(根据ID集合删除)
     *
     * @param ids ID集合
     */
    @Transactional
    public void delete(String ids) {
        //数据删除前处理
        if (!this.deleteBefore(ids)) {
            return;
        }

        //分割UID
        String[] uids = ids.split(",");
        //循环删除
        for (String uid : uids) {
            getDao().delete(uid);
        }

        //数据删除后处理
        this.deleteAfter(ids);
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    protected Boolean deleteBefore(String ids) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    protected Boolean deleteAfter(String ids) {
        return true;
    }

    /**
     * 根据指定的字段名称及值删除数据
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 删除记录数
     */
    @Transactional
    public int delete(String fieldName, String fieldValue) {
        //数据删除前处理
        if (!this.deleteBefore(fieldName, fieldValue)) {
            return -1;
        }

        int result = this.getDao().delete(fieldName, fieldValue);

        //数据删除后处理
        if (!this.deleteAfter(fieldName, fieldValue)) {
            return -1;
        }

        return result;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 处理结果
     */
    protected Boolean deleteBefore(String fieldName, String fieldValue) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 处理结果
     */
    protected Boolean deleteAfter(String fieldName, String fieldValue) {
        return true;
    }

    /**
     * 查询记录件数
     * [注：根据记录件数查询专用SQL文查询记录件数]
     *
     * @param sql       记录件数查询专用SQL
     * @param condition 原始检索条件集合
     * @return 记录件数
     */
    public int searchRecordCount(String sql, Map<String, Object> condition) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, condition);

        return getDao().searchRecordCount(sql, condition);
    }

    /**
     * 分页查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @param recordCount 记录件数
     * @return 分页实体对象列表
     */
    public Page<T> search(Class<T> cls, String sql, Map<String, Object> condition, PageRequest pageRequest, int recordCount) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, condition);

        return getDao().search(cls, sql, condition, pageRequest, recordCount);
    }

    /**
     * 分页查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    public Page<T> search(Class<T> cls, String sql, Map<String, Object> condition, PageRequest pageRequest) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, condition);

        String recordCountSQLPath = (String) SearchConditionUtils.getConditionValue(condition, "recordCountSQLPath");

        if (StringUtils.isNotEmpty(recordCountSQLPath)) {
            //查询记录件数
            int recordCount = this.searchRecordCount(this.getSQL(recordCountSQLPath), condition);
            //数据查询
            return getDao().search(cls, sql, condition, pageRequest, recordCount);
        }
        else {
            //数据查询
            return getDao().search(cls, sql, condition, pageRequest);
        }
    }

    /**
     * 分页查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    public Page<T> search(String sql, Map<String, Object> condition, PageRequest pageRequest) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, condition);

        return getDao().search(sql, condition, pageRequest);
    }

    /**
     * 分页查询(返回Map对象，非页面实体时使用）
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    public Page<Map> searchMap(String sql, Map<String, Object> condition, PageRequest pageRequest) {
        //SQL条件自定义拼接处理
        sql = this.prepareSQL(sql, condition);

        Page<Map> pageDataList = getDao().search(Map.class, sql, condition, pageRequest);

        //处理自身业务数据逻辑
        return this.processPageData(pageDataList, condition);
    }

    /**
     * 分页查询(返回Map对象，非页面实体时使用）
     *
     * @param sql                 查询SQL文
     * @param condition           原始检索条件集合
     * @param pageRequest         分页排序对象
     * @param maxRecordCountLimit 最大查询件数
     * @return 分页实体对象列表
     */
    public Page<Map> searchMap(String sql, Map<String, Object> condition, PageRequest pageRequest, int maxRecordCountLimit) {
        //SQL条件自定义拼接处理
        sql = this.prepareSQL(sql, condition);

        Page<Map> pageDataList = getDao().search(Map.class, sql, condition, pageRequest, maxRecordCountLimit);

        //处理自身业务数据逻辑
        return this.processPageData(pageDataList, condition);
    }

    /**
     * 各Service 重载用，处理自身特殊逻辑
     *
     * @param pageDataList 取得页面的数据
     * @param condition    查询条件
     * @return 处理过后的页面数据
     */
    public Page<Map> processPageData(Page<Map> pageDataList, Map<String, Object> condition) {
        return pageDataList;
    }

    /**
     * 列表查询
     *
     * @param cls       实体类
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @param sort      排序对象
     * @return 分页实体对象列表
     */
    public List<T> search(Class<T> cls, String sql, Map<String, Object> condition, Sort sort) {
        PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE, sort);

        return this.search(cls, sql, condition, pageRequest).getContent();
    }

    /**
     * 列表查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @param sort      排序对象
     * @return 分页实体对象列表
     */
    public List<T> search(String sql, Map<String, Object> condition, Sort sort) {
        PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE, sort);

        return this.search(sql, condition, pageRequest).getContent();
    }

    /**
     * 列表查询
     *
     * @param cls       实体类
     * @param condition 原始检索条件集合
     * @param sort      排序对象
     * @return 实体对象列表
     */
    public List<T> search(Class<T> cls, Map<String, Object> condition, Sort sort) {
        return search(cls, getSearchSQL(condition), condition, sort);
    }

    /**
     * 列表查询
     *
     * @param condition 原始检索条件集合
     * @param sort      排序对象
     * @return 实体对象列表
     */
    public List<T> search(Map<String, Object> condition, Sort sort) {
        return search(getSearchSQL(condition), condition, sort);
    }

    /**
     * 列表查询
     *
     * @param cls       实体类
     * @param condition 原始检索条件集合
     * @return 实体对象列表
     */
    public List<T> search(Class<T> cls, Map<String, Object> condition) {
        return search(cls, getSearchSQL(condition), condition);
    }

    /**
     * 列表查询
     *
     * @param cls         实体类
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    public List<T> search(Class<T> cls, Map<String, Object> condition, int recordLimit) {
        return search(cls, getSearchSQL(condition), condition, recordLimit);
    }

    /**
     * 列表查询
     *
     * @param condition 原始检索条件集合
     * @return 实体对象列表
     */
    public List<T> search(Map<String, Object> condition) {
        return search(getSearchSQL(condition), condition);
    }

    /**
     * 列表查询
     *
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    public List<T> search(Map<String, Object> condition, int recordLimit) {
        return search(getSearchSQL(condition), condition, recordLimit);
    }

    /**
     * 列表查询
     *
     * @param cls       实体类
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @return 实体对象列表
     */
    public <E> List<E> search(Class<E> cls, String sql, Map<String, Object> condition) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, condition);

        return getDao().search(cls, sql, condition);
    }

    /**
     * 列表查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    public <E> List<E> search(Class<E> cls, String sql, Map<String, Object> condition, int recordLimit) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, condition);

        return getDao().search(cls, sql, condition, recordLimit);
    }

    /**
     * 列表查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @return 实体对象列表
     */
    public List<T> search(String sql, Map<String, Object> condition) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, condition);

        return getDao().search(sql, condition);
    }

    /**
     * 列表查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    public List<T> search(String sql, Map<String, Object> condition, int recordLimit) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, condition);

        return getDao().search(sql, condition, recordLimit);
    }

    /**
     * 列表查询
     *
     * @param cls 实体类
     * @param sql 查询SQL文
     * @return 实体对象列表
     */
    public List<T> search(Class<T> cls, String sql) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, null);

        return getDao().search(cls, sql, null);
    }

    /**
     * 列表查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    public List<T> search(Class<T> cls, String sql, int recordLimit) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, null);

        return getDao().search(cls, sql, null, recordLimit);
    }

    /**
     * 列表查询
     *
     * @param sql 查询SQL文
     * @return 实体对象列表
     */
    public List<T> search(String sql) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, null);

        return getDao().search(sql, null);
    }

    /**
     * 列表查询
     *
     * @param sql         查询SQL文
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    public List<T> search(String sql, int recordLimit) {
        //SQL条件自定义拼接处理
        sql = this.needPrepareSQL(sql, null);

        return getDao().search(sql, null, recordLimit);
    }

    /**
     * 记录状态更新
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 更新记录数
     */
    @Transactional
    public int updateRecordStatus(String uid, String recordStatus) {
        //记录状态更新前处理
        if (!this.updateRecordStatusBefore(uid, recordStatus)) {
            return -1;
        }

        //记录状态更新
        int result = getDao().updateRecordStatus(uid, recordStatus);

        //记录状态更新后处理
        if (!this.updateRecordStatusAfter(uid, recordStatus)) {
            return -1;
        }

        return result;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
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
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        return true;
    }

    /**
     * 指定表中指定字段值重复性校验
     *
     * @param pkName           主键字段名称
     * @param pkValue          主键字段值
     * @param targetFieldName  被校验目标字段名称
     * @param targetFieldValue 被校验目标字段值
     * @return true:数据重复/false:数据不重复
     */
    @Transactional
    public Boolean isDuplication(String pkName, String pkValue, String targetFieldName, String targetFieldValue) {
        return this.getDao().isDuplication(pkName, pkValue, null, null, targetFieldName, targetFieldValue, true);
    }

    /**
     * 指定表中指定字段值重复性校验
     *
     * @param pkName               主键字段名称
     * @param pkValue              主键字段值
     * @param targetFieldName      被校验目标字段名称
     * @param targetFieldValue     被校验目标字段值
     * @param recordStatusIsActive 数据是否有效
     * @return true:数据重复/false:数据不重复
     */
    @Transactional
    public Boolean isDuplication(String pkName, String pkValue, String targetFieldName, String targetFieldValue, Boolean recordStatusIsActive) {
        return this.getDao().isDuplication(pkName, pkValue, null, null, targetFieldName, targetFieldValue, recordStatusIsActive);
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
    @Transactional
    public Boolean isDuplication(String pkName, String pkValue, String relationFieldName, String relationFieldValue, String targetFieldName, String targetFieldValue, Boolean recordStatusIsActive) {
        return this.getDao().isDuplication(pkName, pkValue, relationFieldName, relationFieldValue, targetFieldName, targetFieldValue, recordStatusIsActive);
    }

    /**
     * 取得指定表指定字段的最大编码值(注：适用于后台Master等小规模数据表)
     *
     * @param fieldName 字段名称
     * @param prefix    前缀
     * @param length    长度
     * @return 最大编码值
     */
    @Transactional
    public String getMaxCode(String fieldName, String prefix, int length) {
        String code = this.getDao().getMaxCode(fieldName, prefix);
        if (StringUtils.isEmpty(code)) {
            return prefix + StringUtils.leftPad("1", length, '0');
        }

        if (StringUtils.isNotEmpty(prefix)) {
            code = code.replace(prefix, StringUtils.EMPTY);
        }

        Integer max = 0;

        if (StringUtils.isNumeric(code)) {
            max = Integer.parseInt(code);
        }
        max = max + 1;

        return prefix + StringUtils.leftPad(max.toString(), length, '0');
    }

    /**
     * 取得查询SQL文
     *
     * @param condition 检索条件
     * @return 查询SQL文
     */
    public String getSearchSQL(Map<String, Object> condition) {
        return sqlReader.read(getSearchSQLPath(condition));
    }

    /**
     * 根据检索条件中的notNeedPrepareSql标志位，判断是否需要自定义拼接SQL条件
     *
     * @param sql       查询SQL文
     * @param condition 检索条件
     * @return SQL文
     */
    private String needPrepareSQL(String sql, Map<String, Object> condition) {
        //检索条件自定义处理
        this.prepareCondition(condition);

        //必输条件校验
        this.validateMustCondition(condition);

        //需要自定义拼接SQL条件的场合
        if (SearchConditionUtils.isNeedPrepareSql(condition)) {
            sql = this.prepareSQL(sql, condition);
        }
        return sql;
    }

    /**
     * 检索条件自定义处理
     *
     * @param condition 检索条件
     */
    protected void prepareCondition(Map<String, Object> condition) {

    }

    /**
     * SQL条件自定义拼接处理
     *
     * @param sql       SQL文
     * @param condition 检索条件
     * @return SQL文
     */
    protected String prepareSQL(String sql, Map<String, Object> condition) {
        return sql;
    }

    /**
     * 取得标准查询SQL节点路径(例:cm/contract/search)
     * (子类根据具体业务场景Override该方法，如报表画面的DSQL在report目录下，具体实现为cm/report/TrainingReport/search)
     *
     * @return 查询SQL节点路径
     */
    public String getSearchSQLPath(Map<String, Object> condition) {
        String[] names = this.getEntityClass().getName().split("\\.");
        //应用名称
        String appName = names[2];
        //二级模块子目录
        String subDir = StringUtils.EMPTY;
        //二级模块子目录指定的场合
        if (names.length == 7) {
            subDir = "/" + names[5];
        }
        return String.format("%s%s/%s/search", appName, subDir, this.getEntityName());
    }

    /**
     * 自定义查询下拉框【下拉框内容填充专用】
     *
     * @param sqlPath   查询sql路径
     * @param condition 检索条件
     * @return 数据列表
     */
    public List<DropdownEntity> getDropdownList(String sqlPath, Map<String, Object> condition) {
        return this.getDao().search(DropdownEntity.class, getSQL(sqlPath), condition);
    }

    /**
     * 取得指定表、指定字段数据列表【下拉框内容填充专用】
     *
     * @param tableName  表名称
     * @param valueField 下拉框值字段
     * @param textField  下拉框文本字段
     * @param condition  检索条件
     * @return 数据列表
     */
    public List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField, Map<String, Object> condition) {
        return this.getDropdownList(tableName, valueField, textField, condition, null);
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
    public List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField, Map<String, Object> condition, String orderBy) {
        String cacheKey = this.getMasterDataCacheKey(tableName, valueField, textField, condition, orderBy);

        List<DropdownEntity> value = redisCacheManager.getList(DropdownEntity.class, cacheKey);

        if (value == null) {
            value = this.getDao().getDropdownList(tableName, valueField, textField, condition, orderBy);
            redisCacheManager.set(cacheKey, value);
        }
        return value;
    }

    /**
     * 根据条件字段组装Redis缓存主键
     *
     * @param tableName  表名称
     * @param valueField 下拉框值字段
     * @param textField  下拉框文本字段
     * @param condition  检索条件
     * @param orderBy    排序条件
     * @return Redis缓存主键
     */
    private String getMasterDataCacheKey(String tableName, String valueField, String textField, Map<String, Object> condition, String orderBy) {
        String cacheKey = String.format("CACHE_%s_%s_%s", tableName.toUpperCase(), valueField.toUpperCase(), textField.toUpperCase());

        if (CollectionUtils.isNotEmpty(condition)) {
            Collection temp = condition.values();

            String values = "";
            for (Object o : temp) {
                if (StringUtils.isEmpty(values)) {
                    values = o.toString();
                }
                else {
                    values += o + "_";
                }
            }
            values = "[" + values + "]";

            temp = condition.keySet();
            String keys = "";
            for (Object o : temp) {
                if (StringUtils.isEmpty(keys)) {
                    keys = o.toString();
                }
                else {
                    keys += o + "_";
                }
            }
            keys = "[" + keys + "]";

            cacheKey = cacheKey + "_" + keys.toUpperCase() + "_" + values.toUpperCase();
        }

        if (StringUtils.isNotEmpty(orderBy)) {
            cacheKey = cacheKey + "_" + orderBy;
        }
        return cacheKey;
    }

    /**
     * 取得指定表、指定字段数据列表【下拉框内容填充专用】
     *
     * @param tableName  表名称
     * @param valueField 下拉框值字段
     * @param textField  下拉框文本字段
     * @return 数据列表
     */
    public List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField) {
        return this.getDropdownList(tableName, valueField, textField, StringUtils.EMPTY);
    }

    /**
     * 取得指定表、指定字段数据列表【下拉框内容填充专用】
     *
     * @param tableName  表名称
     * @param valueField 下拉框值字段
     * @param textField  下拉框文本字段
     * @param orderBy    排序条件
     * @return 数据列表
     */
    public List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField, String orderBy) {
        Map<String, Object> condition = new HashMap<>();

        //record_status字段存在的场合
        if (this.getDao().fieldExistsInTable(tableName, "record_status")) {
            //取得有效与停止的数据
            condition.put("record_status$in", String.format("%s,%s", CmnConsts.RECORD_STATUS_ACTIVE, CmnConsts.RECORD_STATUS_STOP));
        }
        else {
            //取得有效的数据
            condition.put("deleted", CmnConsts.RECORD_STATUS_ACTIVE);
        }

        return this.getDropdownList(tableName, valueField, textField, condition, orderBy);
    }

    /**
     * 取得指定UID的详细信息
     *
     * @param pkValue 主键值
     * @return 实体详细
     */
    public T getDetail(Object pkValue) {
        Map<String, Object> condition = new HashMap<>();

        if (pkValue == null) {
            return null;
        }

        if (pkValue instanceof String && StringUtils.isEmpty((String) pkValue)) {
            return null;
        }

        condition.put("main." + EntityUtils.getPkName(this.getEntityClass()).toLowerCase(), pkValue);

        //根据查询条件获取List
        List<T> list = this.search(this.getSearchSQL(condition), condition);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 是否是根据主键进行详细信息查询？
     *
     * @param condition 检索条件集合
     * @return true：详细信息查询
     */
    public boolean isDetailSearch(Map<String, Object> condition) {
        if (CollectionUtils.isEmpty(condition)) {
            return false;
        }

        if (condition.size() > 1) {
            return false;
        }

        String uid = (String) SearchConditionUtils.getConditionValue(condition, "main." + EntityUtils.getPkName(this.getEntityClass()).toLowerCase());
        //只包含main.uid主键查询的场合，返回true
        if (StringUtils.isNotEmpty(uid) && uid.length() == 32) {
            return true;
        }
        return false;
    }

    /**
     * 接口数据同步处理
     *
     * @param tableName   表名称
     * @param entityClass 实体类名
     * @return true:同步成功/false:同步失败
     */
    @Transactional
    public boolean syncData(String tableName, Class entityClass) {
        //同步数据(json字符串)
        String result = this.getTableList(tableName);

        if (StringUtils.isEmpty(result)) {
            log.error("数据同步失败：DATA_IMPORT_XXX_API同步参数配置错误或者配置的服务器未启动。");
            return false;
        }
        AjaxResult ajaxResult = JsonUtils.parseJSON(result, AjaxResult.class);

        if (ajaxResult.isSuccess()) {
            List<HashMap> list = (List<HashMap>) ajaxResult.getContent();

            if (list == null || list.size() == 0) {
                return true;
            }

            List<T> entityList = EntityUtils.getEntityListByMap(list, entityClass);

            for (T entity : entityList) {
                //数据覆盖保存
                this.getDao().save(entity);
            }
            return true;
        }
        log.error("同步接口调用失败，失败描述：" + ajaxResult.getMessage());
        return false;
    }

    /**
     * 数据库表内容同步接口调用
     *
     * @param tableName 逻辑表名称
     * @return JSON字符串
     */
    protected String getTableList(String tableName) {
        throw new ServiceException("请在模块BaseService中实现getTableList方法。");
    }

    /**
     * 校验必须的查询条件
     *
     * @param condition 查询条件
     */
    protected void validateMustCondition(Map<String, Object> condition) {

    }

    /**
     * 校验查询条件必须存在在查询条件集合中
     *
     * @param condition    原始检索条件集合
     * @param validateKeys 校验的查询条件
     */
    public void checkConditionKeyInSearchCondition(Map<String, Object> condition, String... validateKeys) {
        //查询条件为空场合
        if (CollectionUtils.isEmpty(condition)) {
            throw new ServiceException("查询条件为空，禁止查询数据。");
        }

        if (validateKeys == null) {
            throw new ServiceException("查询条件异常，禁止查询数据。");
        }

        //校验结果
        Boolean validateResult = false;
        //循环处理原始检索条件
        for (String validateKey : validateKeys) {
            //原始查询条件等于校验查询条件场合
            String conditionValue = (String) SearchConditionUtils.getConditionValue(condition, validateKey);
            //校验查询条件值非空场合
            if (StringUtils.isNotEmpty(conditionValue)) {
                validateResult = true;
                break;
            }
        }

        if (!validateResult) {
            if (log.isErrorEnabled()) {
                try {
                    log.error("查询条件异常，查询条件：{}", JsonUtils.toJSON(condition));
                } catch (JsonProcessingException ex) {
                    log.error("格式化异常");
                }
            }
            throw new ServiceException("查询条件异常，禁止查询数据。");
        }
    }

    /**
     * 获取多个数据字典名称
     *
     * @param dropdownCds  字典CD 多个用逗号分隔
     * @param dropdownList 数据字典集合
     * @return 显示名称集合 多个用逗号分隔
     */
    public String getMultipleSubName(String dropdownCds, List<DropdownEntity> dropdownList) {
        String subNames = StringUtils.EMPTY;
        Map<String, String> dropdownCdMap = new HashMap<>();

        //非空场合
        if (StringUtils.isNotEmpty(dropdownCds)) {
            for (DropdownEntity dropdown : dropdownList) {
                dropdownCdMap.put(dropdown.getSubCd(), dropdown.getSubName());
            }

            //循环处理
            for (String dropdownCd : dropdownCds.split(",")) {
                String subName = dropdownCdMap.get(dropdownCd);
                //包含数据字典场合
                subNames += "," + subName;
            }

            if (StringUtils.isNotEmpty(subNames)) {
                subNames = subNames.substring(1);
            }
        }
        return subNames;
    }

    /**
     * 根据指定表名称、关联字段名称及字段值查询列表数据
     *
     * @param cls        实体类
     * @param tableName  表名称
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 实体对象列表
     */
    public <E> List<E> getResultListByFieldValue(Class<E> cls, String tableName, String fieldName, String fieldValue) {
        return this.getDao().getResultListByFieldValue(cls, tableName, fieldName, fieldValue, true, 10000);
    }

    /**
     * 根据指定表名称、关联字段名称及字段值查询列表数据
     *
     * @param cls                  实体类
     * @param tableName            表名称
     * @param fieldName            字段名称
     * @param fieldValue           字段值
     * @param recordStatusIsActive 数据有效性
     * @return 实体对象列表
     */
    public <E> List<E> getResultListByFieldValue(Class<E> cls, String tableName, String fieldName, String fieldValue, boolean recordStatusIsActive) {
        return this.getDao().getResultListByFieldValue(cls, tableName, fieldName, fieldValue, recordStatusIsActive, 10000);
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
    public <E> List<E> getResultListByFieldValue(Class<E> cls, String tableName, String fieldName, String fieldValue, boolean recordStatusIsActive, int recordLimit) {
        return this.getDao().getResultListByFieldValue(cls, tableName, fieldName, fieldValue, recordStatusIsActive, recordLimit);
    }

    /**
     * 初始化下拉框选项内容
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void initOptionList(Model model) throws IllegalAccessException, IOException {
        Map<String, Object> dicMap = new HashMap<>();

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**********************************
     * 快捷方法定义
     **********************************/
    public String escapeHtml(String value) {
        return StringEscapeUtils.escapeHtml4(value);
    }

    public String toJSON(Object object) throws JsonProcessingException {
        return this.toJSON(object, false);
    }

    public String toJSON(Object object, boolean isEscapeHtml) throws JsonProcessingException {
        if (isEscapeHtml) {
            return this.escapeHtml(JsonUtils.toJSON(object));
        }
        return JsonUtils.toJSON(object);
    }
}
