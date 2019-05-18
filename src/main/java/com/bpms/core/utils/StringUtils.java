package com.bpms.core.utils;


import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 手机号正则表达式
     */
    public static final Pattern PATTERN_MOBILE = Pattern.compile("^((13)|(14)|(15)|(17)|(18)|(19))\\d{9}$");


    /**
     * 邮件正则表达式
     */
    public static final Pattern PATTERN_EMAIL = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5_.-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z]+)+$");

    /**
     * HTML正则表达式
     */
    public static final Pattern PATTERN_HTML = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);

    /**
     * 手机号码中间4位模糊化处理
     *
     * @param mobile 手机号码
     * @return 模糊后的手机号码
     */
    public static String dimMobile(String mobile) {
        //只处理非空且长度为11位的手机号码
        if (isNotEmpty(mobile) && mobile.length() == 11) {
            mobile = StringUtils.substring(mobile, 0, 3) + "****" + StringUtils.substring(mobile, 7, 11);
        }

        return mobile;
    }

    /**
     * 去除字符串中按指定分隔符间的重复字符
     * 例：  333，444，333  返回  333,444
     *
     * @param originalString 原始字符串
     * @param separatorChar  分隔符
     * @return 去重后字符串
     */
    public static String removeDuplicateString(String originalString, String separatorChar) {
        if (isEmpty(originalString) || isEmpty(separatorChar)) {
            return originalString;
        }
        String[] strArray = originalString.split(separatorChar);
        if (strArray.length <= 1) {
            return StringUtils.removeEnd(originalString, separatorChar);
        }
        Set<String> strSet = new LinkedHashSet<String>();
        for (String str : strArray) {
            strSet.add(str);
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strSet) {
            sb.append(str);
            sb.append(separatorChar);
        }
        return StringUtils.removeEnd(sb.toString(), separatorChar);
    }

    /**
     * 去掉HTML标签
     *
     * @param htmlStr html字符文本
     * @return 去掉HTML标签的文本
     */
    public static String removeHTMLTag(String htmlStr) {
        if (StringUtils.isEmpty(htmlStr)) {
            return StringUtils.EMPTY;
        }

        Matcher matcher = PATTERN_HTML.matcher(htmlStr);
        //过滤html标签
        return matcher.replaceAll("");
    }

    /**
     * 是否是手机号码判断
     *
     * @param mobile 手机号码
     * @return true：合法手机号码
     */
    public static boolean isMobileNo(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }

        if (mobile.length() != 11) {
            return false;
        }

        Matcher m = PATTERN_MOBILE.matcher(mobile);
        return m.matches();
    }

    /**
     * 是否是邮箱判断
     * 支持中文邮箱
     *
     * @param email 邮箱
     * @return true：合法邮箱
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }

        Matcher m = PATTERN_EMAIL.matcher(email);
        return m.matches();
    }

    /**
     * 用替换字符（replaceString）替换掉指定字符串（content）中的被替换字符（sourceString）
     *
     * @param content       需替换字符串
     * @param sourceString  被替换字符串
     * @param replaceString 替换字符串
     * @return 替换后字符串
     */
    public static String replaceAll(String content, String sourceString, String replaceString) {
        //需替换字符串 直接返回
        if (StringUtils.isEmpty(content)) {
            return content;
        }

        //被替换字符串 为空直接返回
        if (StringUtils.isEmpty(sourceString)) {
            return content;
        }

        if (StringUtils.isEmpty(replaceString)) {
            replaceString = StringUtils.EMPTY;
        }

        return content.replaceAll(sourceString, replaceString);
    }
}
