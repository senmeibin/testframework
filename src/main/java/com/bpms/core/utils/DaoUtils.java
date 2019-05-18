package com.bpms.core.utils;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.bpms.core.consts.LogConsts.DAO_MARKER;

public class DaoUtils {
    private static Logger log = LoggerFactory.getLogger(DaoUtils.class);

    /**
     * 实体属性名 -> 数据库字段名转换缓存Map
     */
    private static Map<String, String> attributeNameToColumnNameCache = new HashMap<>();

    /**
     * 实体属性名向数据库字段名转换
     *
     * @param attributeName 实体属性名
     * @return 数据库字段名
     */
    @SuppressWarnings("deprecation")
    public static String convertAttributeNameToColumnName(String attributeName) {
        if (StringUtils.isEmpty(attributeName)) {
            return attributeName;
        }

        //映射关系已存在的场合，直接返回缓存中的映射关系
        if (attributeNameToColumnNameCache.containsKey(attributeName)) {
            return attributeNameToColumnNameCache.get(attributeName);
        }

        String columnName;
        // 别名存在的场合
        if (attributeName.indexOf(".") > 0) {
            String[] cols = attributeName.split("\\.");
            columnName = cols[0] + "." + ImprovedNamingStrategy.INSTANCE.columnName(cols[1]);
        }
        else {
            columnName = ImprovedNamingStrategy.INSTANCE.columnName(attributeName);
        }

        synchronized (attributeNameToColumnNameCache) {
            if (!attributeNameToColumnNameCache.containsKey(attributeName)) {
                //缓存映射关系
                attributeNameToColumnNameCache.put(attributeName, columnName);
            }
        }
        return columnName;
    }

    /**
     * 数据库字段名向实体属性名转换
     *
     * @param columnName 数据库字段名
     * @return 实体属性名
     */
    public static String convertColumnNameToAttributeName(String columnName) {
        if (StringUtils.isEmpty(columnName)) {
            return columnName;
        }
        if (columnName.indexOf("_") == -1) {
            return columnName;
        }

        String[] infos = columnName.split("_");
        String attributeName = StringUtils.EMPTY;
        for (String v : infos) {
            if (StringUtils.isEmpty(attributeName)) {
                attributeName += v;
            }
            else {
                attributeName += v.substring(0, 1).toUpperCase() + v.substring(1);
            }
        }
        return attributeName;
    }

    /**
     * 绑定查询参数
     *
     * @param query      查询对象
     * @param bindParams 绑定参数集合
     */
    public static void bindQueryParams(Query query, Map<String, Object> bindParams) {
        //参数为空的场合，处理中止
        if (Objects.isNull(bindParams)) {
            return;
        }

        //循环进行参数绑定
        try {
            for (String key : bindParams.keySet()) {
                if (key.startsWith(":")) {
                    query.setParameter(key.substring(1), bindParams.get(key));
                }
                else {
                    query.setParameter(key, bindParams.get(key));
                }
            }
        }
        catch (Exception e) {
            throw new ServiceException("条件参数错误" + e.getMessage());
        }
    }

    /**
     * 追加排序字段
     *
     * @param sql  查询SQL文
     * @param sort 排序对象
     * @return 查询SQL文
     */
    public static String appendSortField(String sql, Sort sort) {
        if (sort == null) {
            return sql;
        }
        String sortField = getSortField(sort);
        //排序字段不为空的场合
        if (!StringUtils.isEmpty(sortField)) {
            //字段排序
            sql = String.format("%s ORDER BY %s", sql, getSortField(sort));
        }
        return sql;
    }

    /**
     * 取得排序字段
     *
     * @param sort 排序对象
     * @return 排序字段
     */
    public static String getSortField(Sort sort) {
        //字段排序
        String sortField = StringUtils.EMPTY;
        //排序字段不为空的场合
        if (sort != null) {
            for (Sort.Order order : sort) {
                String columnName = order.getProperty();
                //驼峰命名字段、过滤驼峰标识位
                if (order.getProperty().endsWith(CmnConsts.CAMEL_FIELD_FLAG)) {
                    columnName = StringUtils.removeEnd(columnName, CmnConsts.CAMEL_FIELD_FLAG);
                }
                //非驼峰命名字段，实体属性名向数据库字段名转换
                else {
                    columnName = DaoUtils.convertAttributeNameToColumnName(columnName);
                }

                sortField += columnName + " " + order.getDirection().name() + ",";
            }
            //去掉末尾逗号
            sortField = StringUtils.substring(sortField, 0, sortField.length() - 1);
        }
        return sortField;
    }

    /**
     * 取得SQL文中第一个SELECT关键字的位置
     *
     * @param sql SQL文
     * @return SELECT关键字的位置
     */
    public static int getSelectKeyIndex(String sql) {
        int selectIndex1 = sql.toUpperCase().indexOf("SELECT\n");
        int selectIndex2 = sql.toUpperCase().indexOf("SELECT \n");
        if (selectIndex1 > -1 && selectIndex2 == -1) {
            return selectIndex1;
        }
        else if (selectIndex1 == -1 && selectIndex2 > -1) {
            return selectIndex2;
        }
        else if (selectIndex1 > -1 && selectIndex2 > -1) {
            return Math.min(selectIndex1, selectIndex2);
        }

        return sql.toUpperCase().indexOf("SELECT");
    }

    /**
     * 取得SQL文中第一个FROM关键字的位置
     *
     * @param sql SQL文
     * @return FROM关键字的位置
     */
    public static int getFromKeyIndex(String sql) {
        int fromIndex1 = sql.toUpperCase().indexOf(" FROM\n");
        int fromIndex2 = sql.toUpperCase().indexOf(" FROM \n");
        if (fromIndex1 > -1 && fromIndex2 == -1) {
            return fromIndex1;
        }
        else if (fromIndex1 == -1 && fromIndex2 > -1) {
            return fromIndex2;
        }
        else if (fromIndex1 > -1 && fromIndex2 > -1) {
            return Math.min(fromIndex1, fromIndex2);
        }

        return sql.toUpperCase().indexOf(" FROM");
    }

    /**
     * 动态查询条件拼接/占位符置换处理
     *
     * @param jdbcUrl          JDBC驱动程序
     * @param condition        原始检索条件集合
     * @param sql              原sql
     * @param dynamicCondition 动态条件
     * @return 重新拼装的sql
     */
    public static String resetSql(String jdbcUrl, Map<String, Object> condition, String sql, String dynamicCondition) {
        //动态插入条件【%s为老版动态条件占位符、${dynamicCondition}为新版动态条件占位符、推荐使用${dynamicCondition}占位符】
        if (sql.indexOf("%s") > 0 || sql.indexOf("${dynamicCondition}") > 0) {
            sql = sql.replace("%s", dynamicCondition);
            sql = sql.replace("${dynamicCondition}", dynamicCondition);
        }
        //自定义动态sql拼装的场合 多表复杂(complex)查询时在Service层拼装动态查询条件
        else if (sql.indexOf("${customCondition}") > 0) {
            sql = StringUtils.remove(sql, "${customCondition}");
        }
        //标准查询的场合，在sql后直接拼接动态查询条件
        else {
            //动态绑定查询条件
            sql = sql + dynamicCondition;
        }

        //其他非标准占位符替换
        sql = sql.replaceAll("\\$\\{[a-zA-Z0-9]*\\}", StringUtils.EMPTY);

        //非法占位符存在的场合，抛出异常
        if (sql.contains("${")) {
            throw new ServiceException("DSQL文件中存在非法的占位符（占位符请采用驼峰命名规则，如：${customCondition}）。" + sql);
        }

        Object forUpdate = SearchConditionUtils.getConditionValue(condition, "forUpdate");
        //锁表操作标志位存在的场合
        if (forUpdate != null && (boolean) forUpdate) {
            if (Hibernates.isMysql(jdbcUrl)) {
                sql = sql + " FOR UPDATE";
            }
            else if (Hibernates.isSqlServer(jdbcUrl)) {
                //DSQL脚本中实现
            }
        }

        return sql;
    }

    /**
     * 使用数据库专用函数进行多字段值拼接
     *
     * @param jdbcUrl   JDBC驱动程序
     * @param fieldName 字段名称
     * @return 拼接后的SQL字段
     */
    public static String concatFields(String jdbcUrl, String fieldName) {
        //字段名称中带竖线的场合
        if (fieldName.indexOf("|") > 0 || fieldName.indexOf("-") > 0) {
            String[] fields = {fieldName};
            String splitChar = null;

            if (fieldName.indexOf("|") > 0) {
                fields = fieldName.split("\\|");
                splitChar = "|";
            }
            else if (fieldName.indexOf("-") > 0) {
                fields = fieldName.split("-");
                splitChar = "-";
            }

            //MYSQL的场合
            if (Hibernates.isMysql(jdbcUrl)) {
                fieldName = concatMysqlFields(fields, splitChar);
            }
            //SQLServer的场合
            else if (Hibernates.isSqlServer(jdbcUrl)) {
                fieldName = concatSqlserverFields(fields, splitChar);
            }
            //Oracle的场合
            else if (Hibernates.isOracle(jdbcUrl)) {

            }
        }
        return fieldName;
    }

    /**
     * 使用SQLServer 加号(+)进行多字段值拼接
     *
     * @param fields    字段集合
     * @param splitChar 分割字符
     * @return 拼接结果字符串
     */
    private static String concatSqlserverFields(String[] fields, String splitChar) {
        String fieldName = StringUtils.EMPTY;
        //循环拼接字段
        for (String s : fields) {
            if (StringUtils.isEmpty(fieldName)) {
                fieldName = String.format("ISNULL(%s, '')", s);
            }
            else {
                fieldName += String.format(" + '%s' + ISNULL(%s, '')", splitChar, s);
            }
        }
        return fieldName;
    }

    /**
     * 使用MYSQL CONCAT_WS函数进行多字段值拼接
     *
     * @param fields    字段集合
     * @param splitChar 分割字符
     * @return 拼接结果字符串
     */
    private static String concatMysqlFields(String[] fields, String splitChar) {
        String fieldName = StringUtils.EMPTY;
        //循环拼接字段
        for (String s : fields) {
            if (StringUtils.isEmpty(fieldName)) {
                fieldName = String.format("IFNULL(%s, '')", s);
            }
            else {
                fieldName += String.format(", IFNULL(%s, '')", s);
            }
        }

        return String.format("CONCAT_WS('%s', %s)", splitChar, fieldName);
    }

    /**
     * DSQL性能跟踪
     *
     * @param resultSize  结果记录数
     * @param sql         执行sql
     * @param startTime   执行开始时间
     * @param isException 有无异常（true:有/false:无）
     */
    public static void tracePerformance(int resultSize, String sql, Date startTime, boolean isException) {
        tracePerformance(resultSize, sql, startTime, isException, null);
    }

    /**
     * DSQL性能跟踪
     *
     * @param resultSize 结果记录数
     * @param sql        执行sql
     * @param startTime  执行开始时间
     * @param condition  SQL参数
     */
    public static void tracePerformance(int resultSize, String sql, Date startTime, boolean isException, Map<String, Object> condition) {
        //发生异常sql 无条件输出到日志
        if (isException) {
            log.error("执行异常SQL：{}", sql);
            log.error("执行异常查询条件：{}", convertConditionToString(condition));
        }
        //无异常，只有debug 时输出日志
        else {
            if (log.isDebugEnabled()) {
                log.debug("执行SQL：{}", sql);
                log.debug("查询条件：{}", convertConditionToString(condition));
                log.debug("查询时间：{}ms", System.currentTimeMillis() - startTime.getTime());
            }
        }

        int outputLimit = 2000;
        //数据结果集超过2000条记录且无异常的场合
        if (resultSize > outputLimit && !isException) {
            log.info(DAO_MARKER, String.format("数据集超过%s条记录（%s）。", outputLimit, resultSize));
            log.info(DAO_MARKER, String.format("查询SQL：%s", sql));
            log.info(DAO_MARKER, String.format("查询条件：%s", DaoUtils.convertConditionToString(condition)));
            log.info(DAO_MARKER, String.format("查询时间：%sms\n\n", System.currentTimeMillis() - startTime.getTime()));
        }
    }

    /**
     * 将检索条件Map集合转化为Json字符串
     *
     * @param condition 检索条件集合
     * @return Json字符串
     */
    public static String convertConditionToString(Map<String, Object> condition) {
        if (condition != null) {
            try {
                return JsonUtils.toJSON(condition);
            }
            catch (Exception ex) {
            }
        }
        return "（空）";
    }
}
