package com.bpms.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 金额处理通用类
 */
public class AmountUtils {
    public static final Pattern PATTERN_NUMERIC = Pattern.compile("-?[0-9]+.?[0-9]*");

    /**
     * 匹配整数
     */
    public static final Pattern PATTERN_INTEGER = Pattern.compile("^-?[1-9]\\d*$");

    /**
     * 匹配正整数
     */
    public static final Pattern PATTERN_POSITIVE_INTEGER = Pattern.compile("^[1-9]\\d*$");

    /**
     * 匹配负整数
     */
    public static final Pattern PATTERN_NEGATIVE_INTEGER = Pattern.compile("^-[1-9]\\d*$");

    /**
     * 匹配非负整数（正整数和0）
     */
    public static final Pattern PATTERN_NOT_NEGATIVE_INTEGER = Pattern.compile("[1-9]\\d*|0$");

    /**
     * 匹配非正整数（负整数和0）
     */
    public static final Pattern PATTERN_NOT_POSITIVE_INTEGER = Pattern.compile("^-[1-9]\\d*|0$");

    /**
     * 金额相加
     *
     * @param oldAmount 原金额
     * @param addAmount 本次增加金额
     * @return 相加后金额
     */
    private static BigDecimal addAmount(BigDecimal oldAmount, BigDecimal addAmount) {
        BigDecimal amount1 = (oldAmount == null ? new BigDecimal(0) : oldAmount);
        BigDecimal amount2 = (addAmount == null ? new BigDecimal(0) : addAmount);
        return amount1.add(amount2);
    }

    /**
     * 金额相加
     *
     * @param amounts 金额
     * @return 相加后金额
     */
    public static BigDecimal addAmount(BigDecimal... amounts) {
        BigDecimal returnAmount = new BigDecimal(0);
        for (BigDecimal amount : amounts) {
            returnAmount = addAmount(returnAmount, amount);
        }
        return returnAmount;
    }

    /**
     * 金额相减
     *
     * @param oldAmount      原金额
     * @param subtractAmount 本次减少金额
     * @return 相减后金额
     */
    public static BigDecimal subtractAmount(BigDecimal oldAmount, BigDecimal subtractAmount) {
        BigDecimal amount1 = (oldAmount == null ? new BigDecimal(0) : oldAmount);
        BigDecimal amount2 = (subtractAmount == null ? new BigDecimal(0) : subtractAmount);
        return amount1.subtract(amount2);
    }

    /**
     * 金额相减
     *
     * @param amounts 金额
     * @return 相减后金额
     */
    public static BigDecimal subtractAmount(BigDecimal oldAmount, BigDecimal... amounts) {
        for (BigDecimal amount : amounts) {
            oldAmount = subtractAmount(oldAmount, amount);
        }
        return oldAmount;
    }

    /**
     * 金额相乘金额
     *
     * @param amounts 金额值
     * @return 相乘后的金额
     */
    public static BigDecimal multiplyAmount(BigDecimal... amounts) {
        BigDecimal returnAmount = new BigDecimal(0);
        for (BigDecimal amount : amounts) {
            returnAmount = multiplyAmount(returnAmount, amount);
        }
        return returnAmount;
    }

    /**
     * 金额相乘金额
     *
     * @param amount1 金额1
     * @param amount2 金额2
     * @return 相乘后的金额
     */
    public static BigDecimal multiplyAmount(BigDecimal amount1, BigDecimal amount2) {
        amount1 = (amount1 == null ? new BigDecimal(0) : amount1);
        amount2 = (amount2 == null ? new BigDecimal(0) : amount2);
        //避免公式计算得到的值类似 14.090000000000002
        return AmountUtils.roundupBigDecimal(amount1.multiply(amount2));
    }

    /**
     * 金额取相反数 （如果为null, 还是null)
     *
     * @param amount 原金额
     * @return 金额的相反数
     */
    public static BigDecimal negateAmount(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        return amount.negate();
    }

    /**
     * 金额取绝对值 （如果为null, 还是null)
     *
     * @param amount 原金额
     * @return 金额的绝对值
     */
    public static BigDecimal absAmount(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        return amount.abs();
    }

    /**
     * 整形取相反数 （如果为null, 还是null)
     *
     * @param intValue 原整形值
     * @return 金额的相反数
     */
    public static Integer negateInteger(Integer intValue) {
        if (intValue == null) {
            return null;
        }
        return intValue * (-1);
    }


    /**
     * 返回BigDecimal 对应的数字 （传入值为null时 ，返回0）
     *
     * @param number BigDecimal 数字
     * @return BigDecimal 对应的数字
     */
    public static double getDoubleValue(BigDecimal number) {
        double dblNumber = 0;
        try {
            if (number != null) {
                dblNumber = number.doubleValue();
            }
        }
        catch (NumberFormatException nfe) {
            dblNumber = 0;
        }
        return dblNumber;
    }


    /**
     * 数字保留二位小数 （四舍五入）
     *
     * @param originalOigDecimal 处理数字
     * @param roundingMode       四舍五入模式
     * @return 保留二位小数
     * @see #ROUND_UP
     * @see #ROUND_DOWN
     * @see #ROUND_CEILING
     * @see #ROUND_FLOOR
     * @see #ROUND_HALF_UP
     * @see #ROUND_HALF_DOWN
     * @see #ROUND_HALF_EVEN
     * @see #ROUND_UNNECESSARY
     */
    public static BigDecimal roundupBigDecimal(BigDecimal originalOigDecimal, int roundingMode) {
        return roundupBigDecimal(originalOigDecimal, roundingMode, 2);
    }

    /**
     * 数字保留二位小数 （四舍五入）
     *
     * @param originalOigDecimal 处理数字
     * @return 保留二位小数
     */
    public static BigDecimal roundupBigDecimal(BigDecimal originalOigDecimal) {
        return roundupBigDecimal(originalOigDecimal, BigDecimal.ROUND_HALF_UP, 2);
    }

    /**
     * 数字保留指定位小数
     *
     * @param originalOigDecimal 处理数字
     * @param roundingMode       模式
     * @param precision          精度
     * @return 指定位小数
     */
    public static BigDecimal roundupBigDecimal(BigDecimal originalOigDecimal, int roundingMode, int precision) {
        BigDecimal destBigDecimal = null;
        if (originalOigDecimal != null) {
            destBigDecimal = originalOigDecimal.setScale(precision, roundingMode);
        }
        return destBigDecimal;
    }

    /**
     * 金额相除，数字保留指定位小数 （四舍五入）
     *
     * @param dividend  被除数
     * @param divisor   除数
     * @param precision 精度
     * @return 保留指定位小数的商
     */
    public static BigDecimal divideAmount(BigDecimal dividend, BigDecimal divisor, int precision) {
        return divideAmount(dividend, divisor, precision, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 金额相除，数字保留指定位小数 （四舍五入）
     *
     * @param dividend     被除数
     * @param divisor      除数
     * @param precision    精度
     * @param roundingMode 四舍五入模式
     * @return 保留指定位小数的商
     */
    public static BigDecimal divideAmount(BigDecimal dividend, BigDecimal divisor, int precision, int roundingMode) {
        if (dividend == null || divisor == null) {
            return new BigDecimal("0");
        }
        //如果除数为0 ， 返回0
        if (divisor.compareTo(new BigDecimal(0)) == 0) {
            return new BigDecimal("0");
        }
        BigDecimal divideAmount = dividend.divide(divisor, precision, roundingMode);
        //避免公式计算得到的值类似 14.090000000000002
        return AmountUtils.roundupBigDecimal(divideAmount, roundingMode, precision);
    }

    /**
     * 返回金额
     *
     * @param amount 字符串金额
     * @return 结果值
     */
    public static BigDecimal convertAmount(String amount) {
        //传入字符串为空返回0
        if (StringUtils.isEmpty(amount)) {
            return BigDecimal.ZERO;
        }
        //避免公式计算得到的值类似 14.090000000000002
        return AmountUtils.roundupBigDecimal(new BigDecimal(amount));
    }

    /**
     * 返回金额 (如果不是数字的字符串返回0）
     *
     * @param amount 字符串金额
     * @return 结果值
     */
    public static BigDecimal convertAmount2(String amount) {
        //传入字符串为空或者不是数字时，返回0
        if (StringUtils.isEmpty(amount) || !isNumeric(amount)) {
            return BigDecimal.ZERO;
        }
        //避免公式计算得到的值类似 14.090000000000002
        return AmountUtils.roundupBigDecimal(new BigDecimal(amount));
    }

    /**
     * 二个整数相加
     *
     * @param oldInteger 原数值
     * @param addInteger 增加额
     * @return 结果值
     */
    public static Integer addInteger(Integer oldInteger, Integer addInteger) {
        Integer addInteger1 = oldInteger == null ? 0 : oldInteger;
        Integer addInteger2 = addInteger == null ? 0 : addInteger;
        return addInteger1 + addInteger2;
    }

    /**
     * 整数相加
     *
     * @param integers 整数
     * @return 相加后整数
     */
    public static Integer addInteger(Integer... integers) {
        Integer returnInteger = 0;
        for (Integer integer : integers) {
            returnInteger = addInteger(returnInteger, integer);
        }
        return returnInteger;
    }

    /**
     * 二个整数相减
     *
     * @param oldInteger      原数值
     * @param subtractInteger 减数
     * @return 结果值
     */
    public static Integer subtractInteger(Integer oldInteger, Integer subtractInteger) {
        Integer addInteger1 = oldInteger == null ? 0 : oldInteger;
        Integer addInteger2 = subtractInteger == null ? 0 : subtractInteger;
        return addInteger1 - addInteger2;
    }

    /**
     * 字符类型转换为BigDecimal类型
     *
     * @param amountStr 金额字符串
     * @return 金额值
     */
    public static BigDecimal string2BigDecimal(String amountStr) {
        if (StringUtils.isEmpty(amountStr)) {
            return new BigDecimal(0);
        }
        return new BigDecimal(amountStr);
    }

    /**
     * 二个金额比较（如果金额为null， 设定为0 进行比较）
     *
     * @param compareAmount1 比较金额1
     * @param compareAmount2 比较金额2
     * @return （1: 比较金额1 > 比较金额2,  0: 比较金额1 = 比较金额2 , -1 : 比较金额1 < 比较金额2)
     */
    public static int compareAmount(BigDecimal compareAmount1, BigDecimal compareAmount2) {
        compareAmount1 = (compareAmount1 == null ? new BigDecimal(0) : compareAmount1);
        compareAmount2 = (compareAmount2 == null ? new BigDecimal(0) : compareAmount2);

        return compareAmount1.compareTo(compareAmount2);
    }

    /**
     * 金额格式化  以逗号间隔 千分位
     *
     * @param amount 金额值
     * @return 格式化后的金额值
     */
    public static String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return StringUtils.EMPTY;
        }
        return formatAmount(amount.doubleValue());
    }

    /**
     * 金额格式化 以逗号间隔 千分位
     *
     * @param amount 金额值
     * @return 格式化后的金额值
     */
    public static String formatAmount(double amount) {
        if (amount == 0) {
            return StringUtils.EMPTY;
        }
        DecimalFormat df = new DecimalFormat(",###.00");
        return df.format(amount);
    }

    /**
     * 判断是否是数字， （包含正负号， 小数点均可）
     *
     * @param str 字符串
     * @return （数字： true/ 非数字 ：false)
     */
    public static boolean isNumeric(String str) {
        //为空或者以小数点结尾的 不是数字
        if (StringUtils.isEmpty(str) || str.endsWith(".")) {
            return false;
        }
        //[0-9]*            仅数字
        //^-?[0-9]+         带正负
        //-?[0-9]+.?[0-9]+  所有数字（含小数点）
        Matcher isNum = PATTERN_NUMERIC.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是指定类型的整数（为空返回 false)
     *
     * @param str         字符串
     * @param integerType 整数类型（0：整数/1：正整数/2：负整数/3：非正整数/4：非负整数）
     * @return （数字：true/ 非数字：false)
     */
    public static boolean isInteger(String str, int integerType) {
        //为空不是数字
        if (StringUtils.isEmpty(str)) {
            return false;
        }

        Matcher isInteger;
        switch (integerType) {
            //0:整数
            case 0:
                isInteger = PATTERN_INTEGER.matcher(str);
                break;
            //1:正整数
            case 1:
                isInteger = PATTERN_POSITIVE_INTEGER.matcher(str);
                break;
            //2:负整数
            case 2:
                isInteger = PATTERN_NEGATIVE_INTEGER.matcher(str);
                break;
            //3:非正整数
            case 3:
                isInteger = PATTERN_NOT_POSITIVE_INTEGER.matcher(str);
                break;
            //4:非负整数）
            case 4:
                isInteger = PATTERN_NOT_NEGATIVE_INTEGER.matcher(str);
                break;
            default:
                return false;

        }
        //判断是否匹配
        if (!isInteger.matches()) {
            return false;
        }
        return true;
    }
}