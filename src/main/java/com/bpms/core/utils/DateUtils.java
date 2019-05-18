package com.bpms.core.utils;

import com.bpms.core.consts.CmnConsts;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    /**
     * 日期格式
     */
    public static final String DATE_FORMAT = CmnConsts.DATE_FORMAT;

    /**
     * 日期时间格式
     */
    public final static String DATE_TIME_FORMAT = CmnConsts.DATE_TIME_FORMAT;

    /**
     * 是否是日期格式的字符串
     *
     * @param date 日期格式的字符串
     * @return true:是日期字符串
     */
    public static boolean isDate(String date) {
        try {
            return parseDateStrictly(date, CmnConsts.DATE_FORMAT) != null;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 是否是日期格式的字符串
     *
     * @param date       日期格式的字符串
     * @param format     日期格式
     * @param isStrictly 是否严格校验
     *                   true：2015/13/01 不是合法日期
     *                   false：2015/13/01 是合法日期，日期自动转为 2016/01/01
     * @return true：是日期字符串
     */
    public static boolean isDate(String date, String format, boolean isStrictly) {
        try {
            //日期严格check
            if (isStrictly) {
                return parseDateStrictly(date, format) != null;
            }
            else {
                return parseDate(date, format) != null;
            }
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 日期型格式化成字符型
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return StringUtils.EMPTY;
        }

        SimpleDateFormat formatter;
        if (StringUtils.isEmpty(format)) {
            formatter = new SimpleDateFormat(CmnConsts.DATE_FORMAT);
        }
        else {
            formatter = new SimpleDateFormat(format);
        }
        return formatter.format(date);
    }

    /**
     * 日期型格式化成字符型
     */
    public static String format(Date date) {
        return format(date, null);
    }

    /**
     * 取当前时间
     *
     * @return 当前时间对象
     */
    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 取当前时间
     *
     * @return 当前时间字符串
     */
    public static String getNowDateString(String patten) {
        SimpleDateFormat formatter = new SimpleDateFormat(patten);
        return formatter.format(getNowDate());
    }

    /**
     * 取当前时间
     *
     * @return 当前时间字符串
     */
    public static String getNowDateString() {
        return getNowDateString(CmnConsts.DATE_FORMAT);
    }

    /**
     * 返回指定日期的待周几格式的字符串
     *
     * @param strDate yyyy/MM/dd 格式日期
     * @return yyyy/MM/dd(周几)
     */
    public static String getDateWeekString(String strDate) {
        String[] weekDays = {"（日）", "（一）", "（二）", "（三）", "（四）", "（五）", "（六）"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.parseDate(strDate));
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
            week = 0;
        }
        return strDate + weekDays[week];
    }

    /**
     * 时间戳转日期
     *
     * @param timestamp 时间戳
     * @return 日期
     */
    public static Date getDateToTimestamp(Long timestamp) {
        return getDateToTimestamp(timestamp, null);
    }

    /**
     * 时间戳转日期
     *
     * @param timestamp   时间戳
     * @param defaultDate 转换失败时的默认值
     * @return 日期
     */
    public static Date getDateToTimestamp(Long timestamp, Date defaultDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(CmnConsts.DATE_TIME_FORMAT);
            return format.parse(format.format(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return defaultDate;
    }

    /**
     * 获取输入月份的最后一天
     *
     * @param date 输入日期
     * @return 日期
     */
    public static Date getLastDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取每年的最后一天
     *
     * @param date 输入日期
     * @return 日期
     */
    public static Date getLastDayOfYear(Date date) {
        if (date == null) {
            return null;
        }
        String strDate = DateUtils.format(date, DATE_FORMAT);
        String firstDayOfYear = strDate.substring(0, 4) + "/12" + "/31";
        return DateUtils.parseDate(firstDayOfYear, DATE_FORMAT);
    }

    /**
     * 获取输入月份的第一天
     *
     * @param date 输入日期
     * @return 日期
     */
    public static Date getFirstDayOfMonth(Date date) {
        if (date != null) {
            String strDate = DateUtils.format(date, DATE_FORMAT);
            String firstDayOfMonth = strDate.substring(0, 7) + "/01";
            return DateUtils.parseDate(firstDayOfMonth, DATE_FORMAT);
        }
        else {
            return null;
        }
    }

    /**
     * 获取每年的第一天
     *
     * @param date 输入日期
     * @return 日期
     */
    public static Date getFirstDayOfYear(Date date) {
        if (date != null) {
            String strDate = DateUtils.format(date, DATE_FORMAT);
            String firstDayOfYear = strDate.substring(0, 4) + "/01" + "/01";
            return DateUtils.parseDate(firstDayOfYear, DATE_FORMAT);
        }
        else {
            return null;
        }
    }

    /**
     * 获取输入月份次月的第一天
     *
     * @param date 输入日期
     * @return 日期
     */
    public static Date getFirstDayOfNextMonth(Date date) {
        if (date != null) {
            //获取下个月日期（如果是1/30或者1/31 时， 根据当前是否闰年会得到2/28 或者2/29)
            Date nextMonthDay = DateUtils.addMonths(date, 1);
            String strDate = DateUtils.format(nextMonthDay, DATE_FORMAT);
            String firstDayOfMonth = strDate.substring(0, 7) + "/01";
            return DateUtils.parseDate(firstDayOfMonth, DATE_FORMAT);
        }
        else {
            return null;
        }
    }

    /**
     * 获取输入日期所在周的第一天，周一为第一天
     *
     * @param date 输入日期
     * @return 周一的日期
     */
    public static Date getFirstDayOfWeek(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    /**
     * 获取输入日期所在周的最后一天，周日为最后一天
     *
     * @param date 输入日期
     * @return 周一的日期
     */
    public static Date getLastDayOfWeek(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            addWeeks(cal.getTime(), 1);
            return addWeeks(cal.getTime(), 1);
        }
        else {
            return null;
        }
    }

    /**
     * 指定日期添加天数
     *
     * @param date 日期
     * @param days 天数（正数表示取指定日期后的日期/负数表示取指定日期前的日期）
     * @return 添加天数后的日期
     */
    public static Date addDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + days);
        return cal.getTime();
    }

    public static Date parseDate(String str, String... parsePatterns) {
        try {
            return parseDate(str, (Locale) null, parsePatterns);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 字符串日期转换成Date
     *
     * @param srcDate 字符串日期
     * @return Date日期
     */
    public static Date parseDate(String srcDate) {
        return parseDate(srcDate, "yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss.SSS", "dd/MM/yyyy hh:mm:ss a", "MM-dd hh:mm:ss", "yyyy/MM/dd", "yyyy/MM", "yyyy-MM-dd", "yyyy-MM", "yyyy年MM月dd日", "yyyy年MM月", "yyyy年", "yyyy");
    }

    /**
     * 根据年龄取得出生日期
     *
     * @param age         年龄
     * @param isYearFirst 是否为年初 true 年初 1月1日 false 年终 12月31日
     * @return 出生日期
     */
    public static Date getDateByAge(Integer age, boolean isYearFirst) {
        if (age == null) {
            return null;
        }
        if (age < 0) {
            throw new IllegalArgumentException("年龄不能为负数。");
        }
        Calendar calendar = Calendar.getInstance();
        if (isYearFirst) {
            calendar.set(calendar.get(Calendar.YEAR) - age, Calendar.JANUARY, 1);
        }
        else {
            calendar.set(calendar.get(Calendar.YEAR) - age, Calendar.DECEMBER, 31);
        }
        return calendar.getTime();
    }

    /**
     * 根据出生年月获取年龄
     *
     * @param birthDate 生日
     * @return 年龄
     */
    public static Integer getAge(Date birthDate) {

        if (birthDate == null) {
            return 0;
        }

        Date now = new Date();

        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");

        String birthYear = formatYear.format(birthDate);
        String nowYear = formatYear.format(now);

        String birthMonth = formatMonth.format(birthDate);
        String nowMonth = formatMonth.format(now);

        String birthDay = formatDay.format(birthDate);
        String nowDay = formatDay.format(now);

        //计算年份差距
        int age = Integer.parseInt(nowYear) - Integer.parseInt(birthYear);

        // 如果未到出生月份，则年龄 - 1
        if (nowMonth.compareTo(birthMonth) < 0) {
            age -= 1;
        }
        //如果月份相同，但未到日期，则年龄 -1
        else if (nowMonth.compareTo(birthMonth) == 0 && nowDay.compareTo(birthDay) < 0) {
            age -= 1;
        }

        if (age < 0) {
            age = 0;
        }

        return age;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param fromDate 开始时间
     * @param toDate   结束时间
     * @return 相差天数
     */
    public static int getDifferentDays(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null) {
            return 0;
        }

        fromDate = parseDate(format(fromDate, DATE_FORMAT));

        toDate = parseDate(format(toDate, DATE_FORMAT));

        Long betweenDays = Math.abs(fromDate.getTime() - toDate.getTime()) / (1000 * 60 * 60 * 24);

        return betweenDays.intValue();
    }

    /**
     * 取得两个月份之间的月份数
     *
     * @param fromDate 开始时间
     * @param toDate   结束时间
     * @return 相差月份数
     */
    public static int getDifferentMonth(String fromDate, String toDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(parseDate(fromDate));
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(parseDate(toDate));
        return (toCalendar.get(Calendar.YEAR) - fromCalendar.get(Calendar.YEAR)) * 12 + (toCalendar.get(Calendar.MONTH) - fromCalendar.get(Calendar.MONTH));
    }

    /**
     * 判断时间是否相等
     *
     * @param date1 待比较日期1
     * @param date2 待比较日期2
     * @return 时间是否相等
     */
    public static boolean equals(Date date1, Date date2) {
        long timestamp1 = date1 == null ? 0 : date1.getTime();
        long timestamp2 = date2 == null ? 0 : date2.getTime();
        return timestamp1 == timestamp2;
    }

    /**
     * 指定日期的星期M为所在月份第几个星期M
     *
     * @param date 待计算的日期
     * @return 指定日期的星期数为当月第几次
     */
    public static int indexOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //指定日期的星期数
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        Calendar first = Calendar.getInstance();
        first.setTime(getFirstDayOfMonth(date));
        int index = 0;
        //从日期的月初到指定日期循环
        for (; !first.after(calendar); first.add(Calendar.DAY_OF_MONTH, 1)) {
            if (first.get(Calendar.DAY_OF_WEEK) == week) {
                index++;
            }
        }
        return index;
    }
}