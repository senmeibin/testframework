package com.bpms.crm.utility;


import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.StringUtils;
import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;

public class CrmUtils {
    /**
     * 通过手机号码 Map 取得手机号码拼接字符串信息
     *
     * @param mobileMap 手机号码 map集合
     * @return 手机号码拼接字符串信息
     */
    public static String getSendMobileInfo(Map<String, String> mobileMap) {
        String mobile = StringUtils.EMPTY;
        if (CollectionUtils.isEmpty(mobileMap)) {
            return mobile;
        }
        Iterator keys = mobileMap.keySet().iterator();
        while (keys.hasNext()) {
            mobile = mobileMap.get(keys.next().toString()).toString() + "," + mobile;
        }
        return mobile;
    }

    /**
     * 封装香草接口要传入的参数
     *
     * @param executeJson 要传入的json 字符串
     * @return 参数Map
     */
    public static Map<String, Object> packingParams(String executeJson) {
        //时间戳
        long timestamp = System.currentTimeMillis();
        //密钥
        String sign = SignUtils.createSign(timestamp);

        Map<String, Object> paramsMap = Maps.newHashMap();
        //封装时间戳参数
        paramsMap.put("timestamp", String.valueOf(timestamp));
        //封装密钥参数
        paramsMap.put("sign", sign);
        //封装json参数
        paramsMap.put("json", executeJson);
        return paramsMap;
    }

    /**
     * 根据传入的分隔符分割字符串成字符数组
     *
     * @param needSplitStr 需要分割的字符串
     * @param splitMark    分割符号
     * @return 返回 字符数组
     */
    public static String[] getSplitArray(String needSplitStr, String... splitMark) {
        for (String mark : splitMark) {
            String[] arrays = needSplitStr.split(mark);
            if (arrays == null || arrays.length <= 1) {
                continue;
            }
            else {
                return arrays;
            }
        }
        if (StringUtils.isNotEmpty(needSplitStr)) {
            return new String[]{needSplitStr};
        }
        return new String[]{};
    }

    /**
     * 验证邮箱
     *
     * @param email 邮箱
     * @return true 符合检查 false 不符合邮箱规范
     */
    public static boolean isEmail(String email) {
        boolean result = false;
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(email)) {
            //正则校验 N个字符+@+N个字符+(2-3个二级域名后缀+1-3个三级域名后缀)
            String regex = "^[a-zA-Z0-9_\\-\\\\.]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)";
            result = email.matches(regex);
        }
        return result;
    }
}
