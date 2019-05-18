package com.bpms.crm.utility;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 报表辅助类
 */
public class ReportUtils {

    /**
     * 组装图例项Map
     *
     * @param LegendMap         图例项Map
     * @param defaultAxisMap    缺省X轴项Map
     * @param currentLegendItem 当前图例项
     * @param currentAxisItem   X轴项
     * @param aXisValue         X轴项对应值
     */
    public static void makeLegendItemMap(Map<String, Object> LegendMap, LinkedHashMap<String, Object> defaultAxisMap, String currentLegendItem, String currentAxisItem, BigDecimal aXisValue) {
        //根据统计维度组装
        if (LegendMap.containsKey(currentLegendItem)) {
            LinkedHashMap currentAxisItemMap = (LinkedHashMap) LegendMap.get(currentLegendItem);
            //补充月份对应统计值
            currentAxisItemMap.put(currentAxisItem, aXisValue);
        }
        //按照年份组装series
        else {
            LinkedHashMap currentAxisItemMap = new LinkedHashMap<>(defaultAxisMap);
            currentAxisItemMap.put(currentAxisItem, aXisValue);
            //月份对应统计值
            LegendMap.put(currentLegendItem, currentAxisItemMap);
        }
    }
}
