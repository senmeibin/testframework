package com.bpms.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class UniqueUtils {
    private static long counter = 1;

    /**
     * 取得唯一UID（yyyyMMddHHmmssSSS + 15位序列号）
     *
     * @return 唯一UID
     */
    synchronized public static String getUID() {
        Date now = new Date();
        String id = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
        String count = StringUtils.leftPad(String.valueOf(getCount()), 15, "0");
        return id + count;
    }

    /**
     * 内部计数器
     *
     * @return 次数
     */
    private static long getCount() {
        synchronized (UniqueUtils.class) {
            if (counter < 0) {
                counter = 0;
            }
            return counter++;
        }
    }
}
