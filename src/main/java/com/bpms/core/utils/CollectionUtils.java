package com.bpms.core.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.*;

/**
 * Collections工具集.
 */
public class CollectionUtils {
    /**
     * 判断是否为空.
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection collection) {
        return (collection == null) || collection.isEmpty();
    }

    /**
     * 判断是否为空.
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Map map) {
        return (map == null) || map.isEmpty();
    }

    /**
     * 判断是否为空.
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断是否为空.
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * 去除list中重复的数据
     *
     * @param list 数据列表
     * @return 去重后的数据列表
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 将List转换为MAP<List>
     *
     * @param list     待转换的list
     * @param property 指定的属性
     * @param <T>      类
     * @return 转换的MAP
     */
    public static <T> Map<String, List<T>> convertListToMap(List<T> list, String... property) {
        return convertListToMap(new HashMap<>(), list, null, property);
    }

    /**
     * 将List转换为MAP<List>
     *
     * @param map      转换后的MAP
     * @param list     待转换的list
     * @param property 指定的属性
     * @param <T>      类
     * @return 转换的MAP
     */
    public static <T> Map<String, List<T>> convertListToMap(Map<String, List<T>> map, List<T> list, String formatPattern, String... property) {
        //判断是否为空
        if (CollectionUtils.isEmpty(list) && (property == null || property.length == 0)) {
            return map;
        }

        //判断传入的MAP是否为空
        if (map == null) {
            map = new HashMap<>();
        }

        //循环处理
        for (T entity : list) {
            //获取属性值作为键值
            String key = "";
            for (String p : property) {
                try {
                    Object v = PropertyUtils.getProperty(entity, p);
                    if (v instanceof Date) {
                        key = DateUtils.format((Date) v, formatPattern);
                    }
                    else {
                        key += (String) v;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }

            //根据KEY获取List列表
            List<T> mapList = map.get(key);
            if (CollectionUtils.isEmpty(mapList)) {
                //不存在则新增
                mapList = new ArrayList<>();
            }
            //加入到List中
            mapList.add(entity);
            //放入到Map中
            map.put(key, mapList);
        }
        return map;
    }
}