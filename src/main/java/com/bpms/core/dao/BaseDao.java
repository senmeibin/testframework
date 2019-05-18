package com.bpms.core.dao;

import com.bpms.core.entity.CoreEntity;
import com.bpms.core.entity.DatabaseMetaData;
import com.bpms.core.entity.DropdownEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseDao<T extends CoreEntity, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * 取得实体类名
     *
     * @return 实体类名
     */
    Class getEntityClass();

    /**
     * 根据条件集合更新指定字段集合的值（更新字段自动拼装、检索条件自动拼装）
     *
     * @param columnSetting 更新字段集合
     * @param condition     更新条件集合
     * @return 更新记录数
     */
    int executeUpdate(Map<String, Object> columnSetting, Map<String, Object> condition);

    /**
     * 根据字段集合自动新增记录（新增记录的字段自动拼装）
     *
     * @param columnSetting 新增字段集合
     * @return 更新记录数
     */
    int executeInsert(Map<String, Object> columnSetting);

    /**
     * 批量插入数据
     *
     * @param list 对应实体的列表
     * @return 插入件数
     */
    int batchInsert(List<T> list);

    /**
     * 批量更新数据
     *
     * @param list 对应实体的列表
     * @return 更新件数
     */
    int batchUpdate(List<T> list);

    /**
     * 执行更新
     *
     * @param sql       更新SQL文
     * @param condition 条件
     * @return 更新记录数
     */
    int executeUpdate(String sql, Map<String, Object> condition);

    /**
     * 执行更新
     *
     * @param sql 更新SQL文
     * @return 更新记录数
     */
    int executeUpdate(String sql);

    /**
     * 记录状态更新
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 更新记录数
     */
    int updateRecordStatus(String uid, String recordStatus);

    /**
     * 根据指定的字段名称及值删除数据
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 删除记录数
     */
    int delete(String fieldName, String fieldValue);

    /**
     * 根据指定的字段名称及值删除数据
     *
     * @param tableName  表名称
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 删除记录数
     */
    int delete(String tableName, String fieldName, String fieldValue);

    /**
     * 查询记录件数
     * [注：根据记录件数查询专用SQL文查询记录件数]
     *
     * @param sql       记录件数查询专用SQL
     * @param condition 原始检索条件集合
     * @return 记录件数
     */
    int searchRecordCount(String sql, Map<String, Object> condition);

    /**
     * 分页查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    <E> Page<E> search(Class<E> cls, String sql, Map<String, Object> condition, PageRequest pageRequest);

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
    <E> Page<E> search(Class<E> cls, String sql, Map<String, Object> condition, PageRequest pageRequest, int recordCount);

    /**
     * 分页查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    Page<T> search(String sql, Map<String, Object> condition, PageRequest pageRequest);

    /**
     * 单页查询
     *
     * @param cls       实体类
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @return 实体对象列表
     */
    <E> List<E> search(Class<E> cls, String sql, Map<String, Object> condition);

    /**
     * 单页查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    <E> List<E> search(Class<E> cls, String sql, Map<String, Object> condition, int recordLimit);

    /**
     * 单页查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @return 实体对象列表
     */
    <E> List<E> search(String sql, Map<String, Object> condition);

    /**
     * 单页查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    <E> List<E> search(String sql, Map<String, Object> condition, int recordLimit);

    /**
     * 单页查询
     *
     * @param cls 实体类
     * @param sql 查询SQL文
     * @return 实体对象列表
     */
    <E> List<E> search(Class<E> cls, String sql);

    /**
     * 单页查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    <E> List<E> search(Class<E> cls, String sql, int recordLimit);

    /**
     * 单页查询
     *
     * @param sql 查询SQL文
     * @return 实体对象列表
     */
    <E> List<E> search(String sql);

    /**
     * 单页查询
     *
     * @param sql         查询SQL文
     * @param recordLimit 记录数限制
     * @return 实体对象列表
     */
    <E> List<E> search(String sql, int recordLimit);

    /**
     * 数据查询
     *
     * @param sql 查询SQL文
     * @return JDBC原始查询结果
     */
    List getResultList(String sql);

    /**
     * 数据查询
     *
     * @param sql         查询SQL文
     * @param recordLimit 记录数限制
     * @return JDBC原始查询结果
     */
    List getResultList(String sql, int recordLimit);

    /**
     * 数据查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @return JDBC原始查询结果
     */
    List getResultList(String sql, Map<String, Object> condition);

    /**
     * 数据查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param recordLimit 记录数限制
     * @return JDBC原始查询结果
     */
    List getResultList(String sql, Map<String, Object> condition, int recordLimit);

    /**
     * 数据查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param sort        排序字段
     * @param recordLimit 记录数限制
     * @return JDBC原始查询结果
     */
    List getResultList(String sql, Map<String, Object> condition, Sort sort, int recordLimit);

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
    Boolean isDuplication(String pkName, String pkValue, String relationFieldName, String relationFieldValue, String targetFieldName, String targetFieldValue, Boolean recordStatusIsActive);

    /**
     * 取得指定表指定字段的最大编码值
     *
     * @param fieldName 字段名称
     * @param prefix    前缀
     * @return 最大编码值
     */
    String getMaxCode(String fieldName, String prefix);

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
    List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField, Map<String, Object> condition, String orderBy);

    /**
     * 根据指定的字段名称及值取得符合记录的记录数
     *
     * @param tableName  表名称
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return 删除记录数
     */
    int countReferenceRecords(String tableName, String fieldName, String fieldValue);

    /**
     * 获取当前数据库下所有的表名
     *
     * @return 当前数据库下所有的表名列表
     */
    List<String> getAllTableNames();

    /**
     * 根据指定的表名称获取表元数据信息
     *
     * @param tableName 表名称
     * @return 表元数据一览
     */
    List<DatabaseMetaData> getDatabaseMetaData(String tableName);

    /**
     * 判断指定表中是否存在指定的字段？
     *
     * @param tableName  表名称
     * @param columnName 字段名称
     * @return true：存在指定的字段
     */
    boolean fieldExistsInTable(String tableName, String columnName);

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
    <E> List<E> getResultListByFieldValue(Class<E> cls, String tableName, String fieldName, String fieldValue, boolean recordStatusIsActive, int recordLimit);
}