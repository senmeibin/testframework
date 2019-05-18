package com.bpms.core.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * HTTP请求通用处理方法
 */
public class RequestUtils {

    /**
     * 从请求路径中取得应用编号
     *
     * @param requestPathInfo 请求路径
     * @return 应用编号
     */
    public static String getAppCode(String[] requestPathInfo) {
        return StringUtils.upperCase(requestPathInfo[0]);
    }

    /**
     * 取得请求路径信息
     *
     * @param request HttpServletRequest
     * @return 路径信息数组
     */
    public static String[] getRequestPathInfo(HttpServletRequest request) {
        String ctx = request.getContextPath();
        String uri = request.getRequestURI();

        if (!StringUtils.isEmpty(ctx)) {
            uri = uri.replaceAll(ctx, StringUtils.EMPTY);
        }

        //过滤最前面的“/”
        if (StringUtils.startsWith(uri, "/")) {
            uri = StringUtils.substring(uri, 1, uri.length());
        }

        return uri.split("/");
    }

    /**
     * 取得访问参数
     *
     * @param request HttpServletRequest
     * @return 访问参数
     */
    public static String getParameters(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        List<Object> result = Lists.newArrayList();

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            //减少参数长度
            if (name.endsWith("Json")) {
                String value = request.getParameter(name);
                try {
                    result.add(name + ": " + JsonUtils.toJSON(JsonUtils.parseJSON(value, Map.class), false));
                } catch (Exception e) {
                    result.add(name + ": " + request.getParameter(name));
                }
            }
            else {
                result.add(name + ": " + request.getParameter(name));
            }
        }

        return Arrays.toString(result.toArray());
    }

    /**
     * 从请求路径中取得方法名称
     *
     * @param requestPathInfo 请求路径
     * @return 方法名称
     */
    public static String getMethodName(String[] requestPathInfo) {
        //默认方法
        if (requestPathInfo.length < 3) {
            return StringUtils.EMPTY;
        }
        return requestPathInfo[2];
    }

    /**
     * 从请求路径中取得功能模块
     *
     * @param requestPathInfo 请求路径
     * @return 功能模块
     */
    public static String getModuleName(String[] requestPathInfo) {
        //默认方法
        if (requestPathInfo.length < 2) {
            return StringUtils.EMPTY;
        }
        String moduleName = StringUtils.lowerCase(requestPathInfo[1]);

        if (moduleName.length() > 32) {
            moduleName = moduleName.substring(0, 32);
        }

        return moduleName;
    }

    /**
     * 是否是Api访问
     *
     * @param requestUri 请求URI
     * @return true：是
     */
    public static boolean isApiAccess(String requestUri) {
        if (StringUtils.indexOf(requestUri, "/api/") >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否是共通访问
     *
     * @param requestUri 请求URI
     * @return true：是
     */
    public static boolean isCommonAccess(String requestUri) {
        if (StringUtils.indexOf(requestUri, "/cmn/") >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否是WEB页面访问
     *
     * @param requestUri 请求URI
     * @return true：是
     */
    public static boolean isWebAccess(String requestUri) {
        if (StringUtils.indexOf(requestUri, "/index") >= 0 || StringUtils.indexOf(requestUri, "/web/") >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否是Error画面访问
     *
     * @param requestUri 请求URI
     * @return true：是
     */
    public static boolean isErrorAccess(String requestUri) {
        if (StringUtils.indexOf(requestUri, "/error/") >= 0) {
            return true;
        }
        return false;
    }
}
