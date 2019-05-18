package com.bpms.core;

import com.bpms.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

public class AppContext {
    /**
     * 日志输出对象
     */
    protected static final Logger log = LoggerFactory.getLogger(AppContext.class.getClass());

    /**
     * 取得HttpSession实例对象
     *
     * @return HttpSession实例对象
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 取得HttpRequest实例对象
     *
     * @return HttpRequest实例对象
     */
    public static HttpServletRequest getRequest() {
        if (getRequestAttributes() == null) {
            return null;
        }
        else {
            return getRequestAttributes().getRequest();
        }
    }

    /**
     * 取得HttpResponse实例对象
     *
     * @return HttpResponse实例对象
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 取得ServletRequestAttributes实例对象
     *
     * @return ServletRequestAttributes实例对象
     */
    private static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 是否是HTTP请求
     *
     * @return true：HTTP请求
     */
    public static boolean isHttpRequest() {
        ServletRequestAttributes servletRequestAttributes = getRequestAttributes();
        return !(servletRequestAttributes == null || servletRequestAttributes.getRequest() == null);
    }

    /**
     * 取得应用ContextPath
     *
     * @return contextPath
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    /**
     * 取得应用程序部署文件绝对路径
     *
     * @return 应用程序绝对文件
     */
    public static String getRealPath() {
        return getRequest().getServletContext().getRealPath("/");
    }


    /**
     * 取得应用程序部署文件绝对路径
     * 注：非http访问时(譬如夜间job)，取得应用程序路径
     *
     * @return 应用程序绝对文件
     */
    public static String getAppPath() {
        String rootPath = AppContext.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (log.isDebugEnabled()) {
            log.debug("rootPath = " + rootPath);
        }
        //路径sample  /E:/RR_CM/03_Coding/target/RR_CM-1.0.0/WEB-INF/classes/
        //得到去掉 WEB-INF/classes/ 的路径    E:/RR_CM/03_Coding/target/RR_CM-1.0.0/
        rootPath = rootPath.substring(1, rootPath.length() - "WEB-INF/classes/".length());
        //linux 环境下
        if (StringUtils.equals(File.separator, "/") && !rootPath.startsWith("/")) {
            rootPath = "/" + rootPath;
        }
        if (log.isDebugEnabled()) {
            log.debug("去除WEB-INF/classes/ 后的rootPath = " + rootPath);
        }
        return rootPath;
    }
}
