package com.bpms.core.utils;

import com.bpms.core.consts.CmnConsts;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 动态条件查询实用类
 *
 * @author Fuxuefa
 */
public class SearchConditionUtils {
    /**
     * 是否需要自定义拼接SQL条件
     *
     * @param condition 检索条件
     * @return true：需要自定义拼接SQL条件
     */
    public static boolean isNeedPrepareSql(Map<String, Object> condition) {
        boolean prepareSQLFlag = true;
        if (CollectionUtils.isNotEmpty(condition)) {
            //主要用于inner，view等内嵌一览时，根据隐含条件检索即可，无需附加其他检索条件
            String notNeedPrepareSqlFlag = (String) SearchConditionUtils.getConditionValue(condition, "notNeedPrepareSql");
            if (StringUtils.isNotEmpty(notNeedPrepareSqlFlag)) {
                prepareSQLFlag = false;
            }
        }
        return prepareSQLFlag;
    }

    /**
     * 动态绑定查询条件
     *
     * @param condition  原始条件集合
     * @param bindParams 需要绑定的条件集合
     * @return where字符串
     */
    public static String appendCondition(Map<String, Object> condition, Map<String, Object> bindParams) {
        return appendCondition(condition, bindParams, false);
    }

    /**
     * 动态绑定查询条件
     *
     * @param condition       原始条件集合
     * @param bindParams      需要绑定的条件集合
     * @param requireWhereKey 是否需要在条件字符串中附加WHERE关键字
     * @return where字符串
     */
    public static String appendCondition(Map<String, Object> condition, Map<String, Object> bindParams, Boolean requireWhereKey) {
        //条件集合为空的场合，处理中止
        if (condition == null || condition.isEmpty()) {
            if (requireWhereKey) {
                return " WHERE 1 = 1 ";
            }
            else {
                return StringUtils.EMPTY;
            }
        }

        StringBuilder conditionBuilder = new StringBuilder();

        //对检索条件KEY进行循环
        for (String key : condition.keySet()) {
            //検索条件值
            Object dbValue = condition.get(key);

            //条件直接绑定的场合
            if (key.indexOf(":") == 0) {
                //过滤冒号符号用户DB条件绑定
                bindParams.put(key.substring(1), dbValue);
                continue;
            }

            boolean isCamelKey = false;
            if (key.endsWith(CmnConsts.CAMEL_FIELD_FLAG)) {
                isCamelKey = true;
                key = StringUtils.removeEnd(key, CmnConsts.CAMEL_FIELD_FLAG);
            }

            //检索条件值存在的场合
            if (dbValue != null) {
                //字符为空的场合
                if (dbValue instanceof String && StringUtils.isEmpty(dbValue.toString())) {
                    continue;
                }

                //字段名称
                String dbField = key;

                //参数名称
                String bindParamName = dbField.replaceAll("\\.", "_");

                //检索模式指定的場合
                if (key.indexOf("$") > 0) {
                    String[] conditionKey = key.split("\\$");

                    //字段名称
                    dbField = conditionKey[0];

                    //查询模式
                    String searchMode = conditionKey[1].toLowerCase();

                    //非驼峰字段的场合，将驼峰字段转换为下划线连接的数据库字段
                    if (!isCamelKey) {
                        //多字段OR查询的场合
                        if (isOrSearch(searchMode)) {
                            //按逗号分隔查询字段
                            String[] dbFields = dbField.split(",");
                            dbField = StringUtils.EMPTY;
                            //循环转换后重新按逗号拼接查询字段
                            for (String s : dbFields) {
                                if (StringUtils.isEmpty(dbField)) {
                                    dbField = DaoUtils.convertAttributeNameToColumnName(s);
                                }
                                else {
                                    dbField += "," + DaoUtils.convertAttributeNameToColumnName(s);
                                }
                            }
                        }
                        else {
                            dbField = DaoUtils.convertAttributeNameToColumnName(dbField);
                        }
                    }

                    //参数名称
                    bindParamName = dbField.replaceAll("\\.", "_");

                    //条件無視的場合
                    if (StringUtils.equals(searchMode, "ignore_search")) {
                        continue;
                    }

                    //相等查询
                    if (isEqualSearch(searchMode)) {
                        //创建相等查询条件
                        createEqualSearchCondition(conditionBuilder, dbField, dbValue, bindParamName, bindParams);
                    }
                    //OR查询
                    else if (isOrSearch(searchMode)) {
                        //创建OR查询条件
                        createOrSearchCondition(conditionBuilder, dbField, dbValue, bindParamName, bindParams);
                    }
                    //不等查询
                    else if (isNotEqualSearch(searchMode)) {
                        conditionBuilder.append(String.format(" AND %s != :%s", dbField, bindParamName));
                        bindParams.put(bindParamName, dbValue);
                    }
                    //部分一致 /前方一致/後方一致
                    else if (isLikeSearch(searchMode)) {
                        conditionBuilder.append(String.format(" AND %s LIKE :%s", dbField, bindParamName));
                        bindParams.put(bindParamName, getLikeValue(searchMode, dbValue));
                    }
                    //不包含查询
                    else if (isNotLikeSearch(searchMode)) {
                        conditionBuilder.append(String.format(" AND %s NOT LIKE :%s", dbField, bindParamName));
                        bindParams.put(bindParamName, getLikeValue(searchMode, dbValue));
                    }
                    //範囲検索（大于/大于等于）
                    else if (isGreatThanSearch(searchMode) || isGreatThanEqualSearch(searchMode)) {
                        bindParamName = bindParamName + "_from";

                        //大于
                        if (isGreatThanSearch(searchMode)) {
                            conditionBuilder.append(String.format(" AND %s > :%s", dbField, bindParamName));
                        }
                        else {
                            conditionBuilder.append(String.format(" AND %s >= :%s", dbField, bindParamName));
                        }

                        bindParams.put(bindParamName, dbValue);
                    }
                    //範囲検索（小于/小于等于）
                    else if (isLessThanSearch(searchMode) || isLessThanEqualSearch(searchMode)) {
                        bindParamName = bindParamName + "_to";

                        //小于
                        if (isLessThanSearch(searchMode)) {
                            conditionBuilder.append(String.format(" AND %s < :%s", dbField, bindParamName));
                        }
                        else {
                            conditionBuilder.append(String.format(" AND %s <= :%s", dbField, bindParamName));
                        }

                        //日期类型的场合
                        if (dbValue.toString().length() <= 10 && DateUtils.isDate(dbValue.toString())) {
                            dbValue = dbValue + " 23:59:59";
                        }

                        bindParams.put(bindParamName, dbValue);
                    }
                    //数组范围查询
                    else if (isINSearch(searchMode) || isNotINSearch(searchMode)) {
                        Object[] values = null;
                        if (dbValue instanceof ArrayList || dbValue instanceof List) {
                            values = ((List) dbValue).toArray();
                        }
                        else if (dbValue instanceof Object[]) {
                            values = (Object[]) dbValue;
                        }
                        else if (dbValue instanceof String) {
                            values = dbValue.toString().split(",");
                            //过滤所有空格
                            for (int i = 0; i < values.length; i++) {
                                values[i] = ((String) values[i]).trim();
                            }
                        }
                        if (isINSearch(searchMode)) {
                            conditionBuilder.append(String.format(" AND %s", bindInCondition(true, bindParamName, dbField, values, bindParams)));
                        }
                        else {
                            conditionBuilder.append(String.format(" AND %s", bindInCondition(false, bindParamName, dbField, values, bindParams)));
                        }
                    }
                    //加密查询
                    else if (isEncryptionSearch(searchMode)) {
                        //创建相等查询条件
                        createEqualSearchCondition(conditionBuilder, dbField, AESUtils.encrypt((String) dbValue), bindParamName, bindParams);
                    }
                }
                //完全一致的場合
                else {
                    //非驼峰字段的场合，将驼峰字段转换为下划线连接的数据库字段
                    if (!isCamelKey) {
                        dbField = DaoUtils.convertAttributeNameToColumnName(dbField);
                    }

                    //创建相等查询条件
                    createEqualSearchCondition(conditionBuilder, dbField, dbValue, bindParamName, bindParams);
                }
            }
        }

        String where = conditionBuilder.toString();

        //追加WHERE关键字
        if (requireWhereKey && !StringUtils.isEmpty(where.trim())) {
            where = " WHERE " + trimFirstAndKey(where);
        }

        return where;
    }

    /**
     * 是否是大于查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isGreatThanSearch(String searchMode) {
        return StringUtils.equals(searchMode, ">") || StringUtils.equals(searchMode, "gt_search");
    }

    /**
     * 是否是大于等于查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isGreatThanEqualSearch(String searchMode) {
        return StringUtils.equals(searchMode, ">=") || StringUtils.equals(searchMode, "from_search") || StringUtils.equals(searchMode, "gte_search");
    }

    /**
     * 是否是小于查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isLessThanSearch(String searchMode) {
        return StringUtils.equals(searchMode, "<") || StringUtils.equals(searchMode, "lt_search");
    }

    /**
     * 是否是小于等于查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isLessThanEqualSearch(String searchMode) {
        return StringUtils.equals(searchMode, "<=") || StringUtils.equals(searchMode, "to_search") || StringUtils.equals(searchMode, "lte_search");
    }

    /**
     * 是否是IN查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isINSearch(String searchMode) {
        return StringUtils.equals(searchMode, "in") || StringUtils.equals(searchMode, "in_search") || StringUtils.equals(searchMode, "array_search");
    }

    /**
     * 是否是NOTIN查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isNotINSearch(String searchMode) {
        return StringUtils.equals(searchMode, "not_in_search") || StringUtils.equals(searchMode, "not_in");
    }

    /**
     * 是否是模糊查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isLikeSearch(String searchMode) {
        return StringUtils.equals(searchMode, "like_search") || StringUtils.equals(searchMode, "like_search") || StringUtils.equals(searchMode, "partial_search") || StringUtils.equals(searchMode, "prefix_search")
                || StringUtils.equals(searchMode, "suffix_search");
    }

    /**
     * 是否是不包含查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isNotLikeSearch(String searchMode) {
        return StringUtils.equals(searchMode, "not_like");
    }

    /**
     * 是否是不等查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isNotEqualSearch(String searchMode) {
        return StringUtils.equals(searchMode, "!=") || StringUtils.equals(searchMode, "<>") || StringUtils.equals(searchMode, "not_equal_search") || StringUtils.equals(searchMode, "not_eq_search");
    }

    /**
     * 是否是相等查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isEqualSearch(String searchMode) {
        return StringUtils.equals(searchMode, "==") || StringUtils.equals(searchMode, "=") || StringUtils.equals(searchMode, "eq_search") || StringUtils.equals(searchMode, "equal_search");
    }

    /**
     * 是否是OR查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isOrSearch(String searchMode) {
        return StringUtils.equals(searchMode, "or");
    }

    /**
     * 是否加密查询
     *
     * @param searchMode 查询模式
     * @return true:是
     */
    public static Boolean isEncryptionSearch(String searchMode) {
        return StringUtils.equals(searchMode, "encryption");
    }

    /**
     * 取得模糊查询条件值
     *
     * @param searchMode 模糊查询模式
     * @param value      原值
     * @return 模糊查询值
     */
    public static String getLikeValue(String searchMode, Object value) {
        //値の無害化
        String likeValue = escapeSpecialCharacters(value);
        switch (searchMode) {
            //前方一致
            case "prefix_search":
                likeValue = likeValue + "%";
                break;
            //後方一致
            case "suffix_search":
                likeValue = "%" + likeValue;
                break;
            //部分一致
            case "partial_search":
                likeValue = "%" + likeValue + "%";
                break;
            case "like_search":
                likeValue = "%" + likeValue + "%";
                break;
            case "not_like":
                likeValue = "%" + likeValue + "%";
                break;
            default:
                break;
        }
        return likeValue;
    }

    /**
     * SQL特殊字符转义处理
     *
     * @param value 查询条件值
     * @return 转义后的字符串
     */
    public static String escapeSpecialCharacters(Object value) {
        String safeValue = value.toString().trim();

        safeValue = safeValue.replace("%", "\\%");
        safeValue = safeValue.replace("_", "\\_");
        return safeValue;
    }

    /**
     * 过滤Where条件中的第一个AND关键字
     *
     * @param where 过滤前where条件
     * @return 过滤后的where条件
     */
    public static String trimFirstAndKey(String where) {
        //Where条件为空的场合
        if (StringUtils.isEmpty(where)) {
            return StringUtils.EMPTY;
        }

        where = where.trim();

        if (StringUtils.isEmpty(where)) {
            return StringUtils.EMPTY;
        }

        //过滤第一个AND关键字
        if (where.toUpperCase().indexOf("AND ") == 0) {
            return where.substring(3);
        }
        return where;
    }

    /**
     * 创建相等查询条件
     *
     * @param conditionBuilder SQL条件字符集
     * @param dbField          DB字段
     * @param dbValue          条件值
     * @param bindParamName    绑定参数名称
     * @param bindParams       需要绑定的条件集合
     */
    private static void createEqualSearchCondition(StringBuilder conditionBuilder, String dbField, Object dbValue, String bindParamName, Map<String, Object> bindParams) {
        //NULL查询的场合
        if (StringUtils.equals(dbValue.toString().toUpperCase(), "NULL") || StringUtils.equals(dbValue.toString().toUpperCase(), "IS NULL")) {
            //日期类型字段的场合
            if (StringUtils.contains(dbField.toLowerCase(), "date")) {
                conditionBuilder.append(String.format(" AND %s IS NULL ", dbField));
            }
            //其他数据类型字段的场合
            else {
                conditionBuilder.append(String.format(" AND (%s IS NULL OR %s = '') ", dbField, dbField));
            }
        }
        //NOT NULL查询的场合
        else if (StringUtils.equals(dbValue.toString().toUpperCase(), "NOT NULL") || StringUtils.equals(dbValue.toString().toUpperCase(), "IS NOT NULL")) {
            //日期类型字段的场合
            if (StringUtils.contains(dbField.toLowerCase(), "date")) {
                conditionBuilder.append(String.format(" AND %s IS NOT NULL ", dbField));
            }
            //其他数据类型字段的场合
            else {
                conditionBuilder.append(String.format(" AND (%s IS NOT NULL AND %s != '') ", dbField, dbField));
            }
        }
        //值相等查询
        else {
            conditionBuilder.append(String.format(" AND %s = :%s", dbField, bindParamName));
            bindParams.put(bindParamName, dbValue);
        }
    }

    /**
     * 创建OR查询条件
     *
     * @param conditionBuilder SQL条件字符集
     * @param dbField          DB字段
     * @param dbValue          条件值
     * @param bindParamName    绑定参数名称
     * @param bindParams       需要绑定的条件集合
     */
    private static void createOrSearchCondition(StringBuilder conditionBuilder, String dbField, Object dbValue, String bindParamName, Map<String, Object> bindParams) {
        String[] dbFields = StringUtils.split(dbField, ",");
        //只有一个字段的场合
        if (dbFields.length == 1) {
            conditionBuilder.append(String.format(" AND %s = :%s", dbField, bindParamName));
            bindParams.put(bindParamName, dbValue);
        }
        else {
            for (int i = 0; i < dbFields.length; i++) {
                String bindOrParamName = dbFields[i].replace(".", "_") + "_or_search_" + i;
                //第一个字段
                if (i == 0) {
                    conditionBuilder.append(String.format(" AND (%s LIKE :%s", dbFields[i], bindOrParamName));
                }
                //最后一个字段
                else if (i == dbFields.length - 1) {
                    conditionBuilder.append(String.format(" OR %s LIKE :%s)", dbFields[i], bindOrParamName));
                }
                //任意中间字段
                else {
                    conditionBuilder.append(String.format(" OR %s LIKE :%s", dbFields[i], bindOrParamName));
                }
                //绑定参数值
                bindParams.put(bindOrParamName, getLikeValue("partial_search", dbValue));
            }
        }
    }

    /**
     * 多条件绑定查询(IN查询/NOT IN查询)
     *
     * @param isIn          是否IN查询
     * @param bindParamName 参数名称
     * @param dbField       字段名称
     * @param values        条件值集合
     * @param bindParams    需要绑定的条件集合
     * @return IN条件字符串
     */
    private static String bindInCondition(Boolean isIn, String bindParamName, String dbField, Object[] values, Map<String, Object> bindParams) {
        //值为空的场合
        if (values == null) {
            return StringUtils.EMPTY;
        }

        int length = values.length;
        //条件未指定的场合
        if (length == 0) {
            return StringUtils.EMPTY;
        }

        if (length == 1) {
            bindParams.put(bindParamName, values[0]);
            if (isIn) {
                return String.format("%s = :%s", dbField, bindParamName);
            }
            else {
                return String.format("%s != :%s", dbField, bindParamName);
            }
        }

        StringBuilder parameterStr = new StringBuilder();

        String paramFormat = StringUtils.EMPTY;
        if (isIn) {
            paramFormat = "%s IN(%s) ";
        }
        else {
            paramFormat = "%s NOT IN(%s) ";
        }

        //指定されたパラメータ配列数分、パラメータ文字列を連番を付与して作成する
        for (int i = 0; i < length; i++) {
            if (length > 1000) {
                //SQLServerの制限回避のため1000件以上の場合直接SQLを埋め込む
                String safeValue = values[i].toString().replaceAll("'", "''");
                parameterStr.append("'").append(safeValue).append("'").append(",");
                continue;
            }

            String key = bindParamName + "_" + i;
            parameterStr.append(":").append(bindParamName).append("_").append(i).append(",");

            bindParams.put(key, values[i]);
        }

        String params = parameterStr.toString();

        //去掉末尾逗号
        params = StringUtils.substring(params, 0, params.length() - 1);

        //パラメータ文字列を返す
        return String.format(paramFormat, dbField, params);
    }

    /**
     * 取得检索条件的值
     *
     * @param condition 检索条件集合
     * @param key       检索条件KEY
     * @return 检索条件值
     */
    public static Object getConditionValue(Map<String, Object> condition, String key) {
        return getConditionData(condition, key)[1];
    }

    /**
     * 查询记录数统计SQL路径设置
     *
     * @param condition 检索条件集合
     * @param path      SQL路径(例:cm/contract/searchForRecordCount)
     */
    public static void addRecordCountSQLPath(Map<String, Object> condition, String path) {
        if (CollectionUtils.isEmpty(condition)) {
            return;
        }
        condition.put("recordCountSQLPath$ignore_search", path);
    }

    /**
     * 取得检索条件的值（KEY兼容下划线命名字段及驼峰命名字段）
     * 如：KEY为user_uid，优先匹配user_uid的值，如果不存在会再次匹配userUid的值
     * 如：KEY为userUid，优先匹配userUid的值，如果不存在会再次匹配user_uid的值
     *
     * @param condition 检索条件集合
     * @param key       检索条件KEY
     * @return 检索条件值
     */
    public static Object getAllConditionValue(Map<String, Object> condition, String key) {
        Object value = getConditionData(condition, key)[1];
        if (value != null && StringUtils.isNotEmpty(value.toString())) {
            return value;
        }

        //带下滑线的数据库字段的场合
        if (key.indexOf("_") != -1) {
            key = DaoUtils.convertColumnNameToAttributeName(key);
            value = getConditionData(condition, key)[1];
            if (value != null && StringUtils.isNotEmpty(value.toString())) {
                return value;
            }
        }
        //驼峰属性名称的场合
        else if (isCamelAttributeName(key)) {
            key = DaoUtils.convertAttributeNameToColumnName(key);
            value = getConditionData(condition, key)[1];
            if (value != null && StringUtils.isNotEmpty(value.toString())) {
                return value;
            }
        }
        return null;
    }

    /**
     * 从检索条件集合中移除指定KEY对应的检索条件
     *
     * @param condition 检索条件集合
     * @param key       检索条件KEY
     */
    public static void removeCondition(Map<String, Object> condition, String key) {
        if ((condition == null) || StringUtils.isEmpty(key)) {
            return;
        }
        //获取key对应的完整key（aliasTable + key + mode + camelFlag）
        String completeKey = (String) getConditionData(condition, key)[0];
        if (StringUtils.isNotEmpty(completeKey)) {
            condition.remove(completeKey);
        }
    }

    /**
     * 是否是驼峰属性名称？
     *
     * @param name 属性名称
     * @return true：是驼峰属性名称
     */
    public static boolean isCamelAttributeName(String name) {
        StringBuilder buf = new StringBuilder(name);
        for (int i = 1; i < buf.length() - 1; ++i) {
            if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i)) && Character.isLowerCase(buf.charAt(i + 1))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取得检索条件的对象（0：key /1：value）
     *
     * @param condition 检索条件集合
     * @param key       检索条件KEY
     * @return 检索条件值
     */
    public static Object[] getConditionData(Map<String, Object> condition, String key) {
        Object[] conditionData = {StringUtils.EMPTY, null};
        if (condition == null || StringUtils.isEmpty(key) || condition.size() == 0) {
            return conditionData;
        }
        if (condition.containsKey(key)) {
            conditionData[0] = key;
            conditionData[1] = filterConditionNull(condition.get(conditionData[0]));
            return conditionData;
        }

        if (condition.containsKey(":" + key)) {
            conditionData[0] = ":" + key;
            conditionData[1] = filterConditionNull(condition.get(conditionData[0]));
            return conditionData;
        }

        String[] searchMode = {"", "or", "$=", "$==", "$!=", "$>", "$>=", "$<", "$<=", "$in", "$in_search", "$not_in", "$not_eq_search", "$not_equal_search", "$ignore_search", "$from_search", "$to_search", "$like_search", "$partial_search",
                "$prefix_search", "$suffix_search", "$eq_search", "$equal_search", "$array_search", "$gt_search", "$gte_search", "$lt_search", "$lte_search"};

        for (String mode : searchMode) {
            for (String k : condition.keySet()) {
                //驼峰字段标识位
                String camelFlag = k.endsWith(CmnConsts.CAMEL_FIELD_FLAG) ? CmnConsts.CAMEL_FIELD_FLAG : StringUtils.EMPTY;

                //无别名的场合
                if (k.indexOf(".") == -1) {
                    if (condition.containsKey(key + mode + camelFlag)) {
                        conditionData[0] = key + mode + camelFlag;
                        conditionData[1] = filterConditionNull(condition.get(conditionData[0]));
                        return conditionData;
                    }
                }
                else {
                    //表别名
                    String aliasTable = k.substring(0, k.indexOf(".") + 1);

                    if (key.startsWith(aliasTable)) {
                        if (condition.containsKey(key + mode + camelFlag)) {
                            conditionData[0] = key + mode + camelFlag;
                            conditionData[1] = filterConditionNull(condition.get(conditionData[0]));
                            return conditionData;
                        }
                    }
                    else {
                        if (condition.containsKey(aliasTable + key + mode + camelFlag)) {
                            conditionData[0] = aliasTable + key + mode + camelFlag;
                            conditionData[1] = filterConditionNull(condition.get(conditionData[0]));
                            return conditionData;
                        }
                    }
                }
            }
        }

        return conditionData;
    }

    /**
     * 查询条件值"null"字符串特殊过滤
     *
     * @param value 查询条件值
     * @return 过滤后的查询条件值
     */
    private static Object filterConditionNull(Object value) {
        if (value instanceof String && StringUtils.equals("null", (String) value)) {
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * 根据条件值集合，自动拼接成IN条件值
     *
     * @param list 条件值集合
     * @return IN条件值
     */
    public static String getInValue(List<String> list) {
        if (list == null) {
            return "'NOT_EXISTS'";
        }
        StringBuilder result = new StringBuilder();
        for (String param : list) {
            if (result.length() == 0) {
                result.append("'" + filterKeyword(param) + "'");
            }
            else {
                result.append(", '" + filterKeyword(param) + "'");
            }
        }
        return result.toString();
    }

    /**
     * 根据条件值集合，自动拼接成IN条件值
     *
     * @param params 条件值集合(逗号分隔)
     * @return IN条件值
     */
    public static String getInValue(String params) {
        if (StringUtils.isEmpty(params)) {
            return "'NOT_EXISTS'";
        }
        String[] list = params.split(",");
        StringBuilder result = new StringBuilder();
        for (String param : list) {
            if (result.length() == 0) {
                result.append("'" + filterKeyword(param) + "'");
            }
            else {
                result.append(", '" + filterKeyword(param) + "'");
            }
        }
        return result.toString();
    }

    /**
     * 过滤数据库查询关键字 防止sql注入
     *
     * @param params 参数
     * @return 替换之后的参数
     */
    public static String filterKeyword(String params) {
        if (StringUtils.isEmpty(params)) {
            return params;
        }
        return params.replaceAll("--", "").replaceAll("'", "").replaceAll("%", "").replaceAll("_", "");
    }
}
