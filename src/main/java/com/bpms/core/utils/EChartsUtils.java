package com.bpms.core.utils;

import com.google.common.collect.Maps;
import com.bpms.core.entity.BaseReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EChartsUtils {

    /**
     * 将查询结果转换为饼状图专用数据结构
     *
     * @param reportList 数据汇总结果
     * @return 饼状图数据集合
     */
    public static Map<String, Object> convertEChartsPieList(List<BaseReport> reportList) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Object> categories = new ArrayList<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(reportList)) {
            for (BaseReport report : reportList) {
                Map<String, Object> pieMap = Maps.newHashMap();
                //分类
                categories.add(report.getCategory());
                //注：饼状图的数据必须满足name/value数据结构要求，
                //此处专门将category/seriesValue1转换成name/value数据结构
                pieMap.put("name", report.getCategory());

                //系列值1
                pieMap.put("value", report.getSeriesValue1());

                dataList.add(pieMap);
            }
        }

        //系列数据设定
        resultMap.put("categories", categories);
        //数据设定
        resultMap.put("data", dataList);

        return resultMap;
    }

    /**
     * 将查询结果转换为饼状图专用数据结构
     *
     * @param list 数据汇总结果
     * @return 饼状图数据集合
     */
    public static Map<String, Object> convertEChartsPieMap(List<HashMap> list) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Object> categories = new ArrayList<>();
        List<Map<String, Object>> dataList = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (HashMap map : list) {
                Map<String, Object> pieMap = new HashMap();
                //分类
                categories.add(map.get("category"));

                //注：饼状图的数据必须满足name/value数据结构要求，
                //此处专门将category/seriesValue1转换成name/value数据结构
                pieMap.put("name", map.get("category"));

                //系列值1
                pieMap.put("value", map.get("seriesValue1"));

                dataList.add(pieMap);
            }
        }

        //系列数据设定
        resultMap.put("categories", categories);
        //数据设定
        resultMap.put("data", dataList);

        return resultMap;
    }
}
