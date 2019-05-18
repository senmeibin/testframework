package com.bpms.core.utils;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bpms.cmn.utility.ApplicationPropertiesUtils;
import com.bpms.core.entity.CoreEntity;
import com.bpms.core.entity.EncryptionColumn;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.dozer.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 实体通用类
 */
public class EntityUtils {
    /**
     * 字段加密是否启用
     */
    private static final boolean ENCRYPTION_ENABLE;
    private static Logger log = LoggerFactory.getLogger(EntityUtils.class);
    /**
     * BeanMap实例缓存Map
     */
    private static Map<String, BeanMap> BEAN_MAP_CACHE = new HashMap<>();

    /**
     * Entity属性字段缓存Map
     */
    private static Map<String, Field> ENTITY_FIELD_CACHE = new HashMap<>();

    static {
        //类型注册转化器->日期
        DateConverter dateConverter = new DateConverter(null);
        dateConverter.setPatterns(new String[]{DateUtils.DATE_FORMAT, DateUtils.DATE_TIME_FORMAT});
        ConvertUtils.register(dateConverter, Date.class);
        //类型注册转化器->BigDecimal
        ConvertUtils.register(new BigDecimalConverter(new BigDecimal(0)), BigDecimal.class);
        ENCRYPTION_ENABLE = ApplicationPropertiesUtils.isEnable("encryption.field.enable");
    }

    /**
     * 取得实体类名称[不带package路径]
     *
     * @return 实体类名称
     */
    public static String getEntityName(Class cls) {
        String name = cls.getSimpleName();

        //EntityExt的场合，过滤Ext后缀
        if (name.endsWith("Ext")) {
            return cls.getSimpleName().substring(0, name.length() - 3);
        }
        return name;
    }

    /**
     * 将查询结果转换为bean list
     * 注：考虑sql查询返回的深度也为1 目前支持的bean深度也为1 so 级联entity 目前不在考虑范围
     *
     * @param listMap map集合
     * @param cls     类
     * @param <E>     bean类型
     * @return 转换后的结果
     */
    public static <E> List getEntityListByMap(List<HashMap> listMap, Class<E> cls) {
        if (cls == Map.class) {
            return listMap;
        }

        List<E> target = Lists.newArrayList();

        // 列表对象为空的场合，处理终止
        if (listMap.size() == 0) {
            return target;
        }

        if (cls == String.class) {
            target.addAll(listMap.stream().map(map -> (E) map.values().iterator().next()).collect(Collectors.toList()));
        }
        else {
            long startTime = System.currentTimeMillis();
            // 取得第一条记录的字段列表集合
            Set<String> fieldSets = listMap.get(0).keySet();

            // 字段名称->属性名称转换处理(不带下划线的字段不做转换)
            HashMap<String, String> propertyMap = Maps.newHashMap();
            fieldSets.stream().filter(keyName -> !propertyMap.containsKey(keyName)).forEach(keyName ->
                    propertyMap.put(keyName, (keyName.contains("_") ? CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, keyName) : keyName))
            );

            // 对List<Map>进行循环，每个Map转换成Entity对象
            for (HashMap map : listMap) {
                // 实例化entity类
                E entity = getEntityInstance(cls);

                // 实例化失败的场合，处理跳过
                if (entity == null) {
                    continue;
                }

                // 数值类型的场合，直接将取得的值加入到TargetList中
                if (entity instanceof Number) {
                    Object v = initEntityByMap(map, propertyMap, entity);
                    if (v != null) {
                        target.add((E) v);
                    }
                }
                else {
                    // 根据Map键值对设置实体对象值
                    initEntityByMap(map, propertyMap, entity);
                    target.add(entity);
                }
            }

            if (log.isDebugEnabled()) {
                log.debug(String.format("%s处理时间：%s ms 记录数：%s  字段数：%s", "EntityUtils.getEntityListByMap", (System.currentTimeMillis() - startTime), listMap.size(), fieldSets.size()));
            }
        }
        return target;
    }

    /**
     * 将Map键值对象转换为实体对象
     *
     * @param map 键值Map对象
     * @param cls 实体类名
     * @param <E> 实体类类型
     * @return 实体对象
     */
    public static <E> E getEntityByMap(HashMap map, Class<E> cls) {
        // Map对象为空的场合，处理终止
        if (map.size() == 0) {
            return null;
        }

        long startTime = System.currentTimeMillis();
        // 取得第一条记录的字段列表集合
        Set<String> fieldSets = map.keySet();

        // 字段名称->属性名称转换处理(不带下划线的字段不做转换)
        HashMap<String, String> propertyMap = Maps.newHashMap();
        fieldSets.stream().filter(keyName -> !propertyMap.containsKey(keyName)).forEach(keyName ->
                propertyMap.put(keyName, (keyName.contains("_") ? CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, keyName) : keyName))
        );

        // 实例化entity类
        E entity = getEntityInstance(cls);

        // 实例化失败的场合，处理终止
        if (entity == null) {
            return null;
        }

        // 根据Map键值对设置实体对象值
        initEntityByMap(map, propertyMap, entity);

        if (log.isDebugEnabled()) {
            log.debug(String.format("%s处理时间：%s ms 字段数：%s", "EntityUtils.getEntityListByMap", (System.currentTimeMillis() - startTime), fieldSets.size()));
        }
        return entity;
    }

    /**
     * 根据Entity实体对象取得BeanMap实例对象
     *
     * @param entity 实体对象
     * @return BeanMap实例对象
     */
    private static synchronized BeanMap getBeanMapInstance(Object entity) {
        String key = entity.getClass().getName();
        if (BEAN_MAP_CACHE.containsKey(key)) {
            return BEAN_MAP_CACHE.get(key);
        }
        else {
            BeanMap beanMap = BeanMap.create(entity);
            BEAN_MAP_CACHE.put(key, beanMap);
            return beanMap;
        }
    }

    /**
     * 根据Entity实体对象取得指定属性名称对应的实体对象字段
     *
     * @param entity       实体对象
     * @param propertyName 属性名称
     * @return Entity属性字段
     */
    public static synchronized Field getEntityField(Object entity, String propertyName) {
        String key = entity.getClass().getName() + "." + propertyName;
        if (ENTITY_FIELD_CACHE.containsKey(key)) {
            return ENTITY_FIELD_CACHE.get(key);
        }
        else {
            //注：Field取得性能比较 ReflectionUtils > FieldUtils，优先使用ReflectionUtils来取得Field
            //Field field = FieldUtils.getField(entity.getClass(), propertyName, true);
            Field field = ReflectionUtils.getFieldFromBean(entity.getClass(), propertyName);
            ENTITY_FIELD_CACHE.put(key, field);
            return field;
        }
    }

    /**
     * 根据Map键值对设置实体对象值
     *
     * @param map         键值Map对象
     * @param propertyMap 属性集合
     * @param entity      实体对象
     */
    private static Object initEntityByMap(HashMap map, HashMap<String, String> propertyMap, Object entity) {
        BeanMap beanMap = getBeanMapInstance(entity);

        //对查询列表字段进行循环，每个Map转换成Entity对象
        for (String keyName : propertyMap.keySet()) {
            //根据字段名称取得属性名称
            String propertyName = propertyMap.get(keyName);
            if (StringUtils.equals(propertyName, "addMode")) {
                continue;
            }
            if (StringUtils.equals(propertyName, "editMode")) {
                continue;
            }
            Object v = null;
            try {
                v = map.getOrDefault(keyName, null);
                //值为空的场合，处理跳过
                if (v == null) {
                    continue;
                }

                //数值类型的场合，直接返回值
                if (entity instanceof Number) {
                    return v;
                }

                //字符串的场合，特殊字符过滤
                if (v instanceof String) {
                    v = filterSpecialCharacters((String) v);
                }

                //字段加密启用的场合
                if (ENCRYPTION_ENABLE) {
                    Field field = getEntityField(entity, propertyName);
                    //字段不存在的场合，处理跳过
                    if (field == null) {
                        continue;
                    }

                    //判断是否为加密字段
                    if (field.getAnnotation(EncryptionColumn.class) != null) {
                        v = AESUtils.decrypt((String) v);
                    }
                }

                //注：属性设置性能比较 BeanMap > PropertyUtils > BeanUtils，优先使用BeanMap来设置属性值
                //PropertyUtils.setProperty(entity, propertyName, v);
                //BeanUtils.setProperty(entity, propertyName, v);
                beanMap.put(entity, propertyName, v);
            } catch (Exception e) {
                try {
                    //数据类型不匹配时，属性值再设定
                    BeanUtils.setProperty(entity, propertyName, v);
                } catch (Exception ex) {
                    log.warn("{}映射字段{}异常:{}", entity.getClass().getName(), keyName, e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * 特殊字符过滤
     *
     * @param input 过滤前字符串
     * @return 过滤后字符串
     */
    private static String filterSpecialCharacters(String input) {
        //【注1】历史遗留问题，回车换行转换，后续可删除
        input = input.replace("\\n", "\n");

        //【注2】不可见特殊符号过滤，不过滤会导致Javascript脚本异常
        input = StringUtils.remove(input, "\u2028");
        return input;
    }

    /**
     * Entity实例化处理
     *
     * @param cls Entity类
     * @return Entity实例
     */
    private static <E> E getEntityInstance(Class<E> cls) {
        // 实例化entity类
        E entity = null;
        try {
            if (cls == Integer.class) {
                entity = (E) Integer.valueOf(0);
            }
            else {
                entity = cls.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.warn("实例化异常:{}", e.getMessage());
        }
        return entity;
    }

    /**
     * 取得主键名称
     *
     * @param entity 实体对象
     * @param <T>    泛型类
     * @return 主键名称
     */
    public static <T extends CoreEntity> String getPkName(T entity) {
        if (entity == null) {
            return StringUtils.EMPTY;
        }

        //获取所有的字段信息
        Field[] fields = FieldUtils.getAllFields(entity.getClass());
        //循环字段
        for (Field field : fields) {
            String fieldName = field.getName();
            //判断是否是主键字段
            Id idColumn = field.getAnnotation(Id.class);
            if (idColumn != null) {
                return fieldName;
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * 取得主键名称
     *
     * @param entity 实体类
     * @return 主键名称
     */
    public static String getPkName(Class entity) {
        if (entity == null) {
            return StringUtils.EMPTY;
        }

        //获取所有的字段信息
        Field[] fields = FieldUtils.getAllFields(entity);
        //循环字段
        for (Field field : fields) {
            String fieldName = field.getName();
            //判断是否是主键字段
            Id idColumn = field.getAnnotation(Id.class);
            if (idColumn != null) {
                return fieldName;
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * 取得主键值
     *
     * @param entity 实体对象
     * @param <T>    泛型类
     * @return 主键值
     */
    public static <T extends CoreEntity> Object getPkValue(T entity) {
        try {
            return PropertyUtils.getProperty(entity, getPkName(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 取得主键值
     *
     * @param entity 实体对象
     * @param pkName 主键值
     * @param <T>    泛型类
     * @return 主键值
     */
    public static <T extends CoreEntity> Object getPkValue(T entity, String pkName) {
        try {
            return PropertyUtils.getProperty(entity, pkName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 主键值设定
     *
     * @param srcEntity  源实体对象
     * @param destEntity 目标实体对象
     * @param <T>        泛型类
     */
    public static <T extends CoreEntity> void setPkValue(T srcEntity, T destEntity) {
        String pkName = getPkName(srcEntity);
        try {
            PropertyUtils.setProperty(destEntity, pkName, getPkValue(srcEntity, pkName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}