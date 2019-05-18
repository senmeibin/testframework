package com.bpms.core.interceptor;

import com.bpms.cmn.service.FilePreviewService;
import com.bpms.cmn.service.MessageDetailService;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.log.LogService;
import com.bpms.core.security.ShiroUser;
import com.bpms.core.session.SessionService;
import com.bpms.core.sync.SyncDataService;
import com.bpms.core.utils.*;
import com.bpms.sys.entity.ext.ApplicationExt;
import com.bpms.sys.entity.ext.OnlineUserExt;
import com.bpms.sys.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * 系统拦截器
 */
@Component
public class SystemInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private OnlineUserService onlineUserService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private LogService logService;

    @Autowired
    private OperationSummaryService operationSummaryService;

    @Autowired
    private UrlRoleService urlRoleService;

    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private FilePreviewService filePreviewService;

    @Autowired
    private MessageDetailService messageDetailService;

    /**
     * 除外参数列表
     */
    @Value("${interceptor.parameter.except.list:}")
    private String parameterExceptList;

    /**
     * JDBC驱动程序
     */
    @Value("${jdbc.driver:mysql}")
    private String jdbcUrl;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String referer = request.getHeader("Referer");
        //解决Uploadify加载时自动执行列表检索
        if (!StringUtils.isEmpty(referer) && referer.indexOf("uploadify.swf?preventswfcaching") > 0) {
            return false;
        }

        //请求URI
        String requestUri = request.getRequestURI().replaceFirst(request.getContextPath(), StringUtils.EMPTY);

        //静态内容访问的场合
        if (HttpUtils.isStaticContentsAccess(requestUri)) {
            return true;
        }
        if (log.isDebugEnabled()) {
            log.debug("接受Request请求开始({})。", requestUri);
        }

        //是否是Ajax请求
        boolean isAjaxRequest = HttpUtils.isAjaxRequest(request);

        //初期化静态资源访问路径
        this.initStaticsContentsServerUrl(request);

        if (log.isDebugEnabled()) {
            //记录请求开始时间
            request.setAttribute("RequestStartTime", DateUtils.getNowDate());
        }

        //非Ajax请求的场合
        if (!isAjaxRequest) {
            //初始化系统参数
            this.initSystemParameter(request);

            //初始化Request参数
            this.initRequestParameter(request);
        }

        //初期化登录者信息
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //用户未登录的场合
        if (loginUser == null) {
            if (RequestUtils.isApiAccess(requestUri)) {
                //API访问的场合、写入系统操作日志
                this.logService.writeOperationLog(request);
            }
            return true;
        }

        //IP访问限制校验
        if (!checkAccessIp(loginUser, requestUri, request, response)) {
            return false;
        }

        //设置登录用户信息
        request.setAttribute("loginUser", loginUser);

        //追踪浏览日志
        Future<Integer> pageViewCount = null;

        //强制密码变更的场合
        if (Objects.equals(loginUser.getForceChangePswd(), CmnConsts.FORCE_CHANGE_PSWD_YES) && !StringUtils.startsWithIgnoreCase(requestUri, "/auth/forcechangepassword")) {
            WebUtils.getAndClearSavedRequest(request);
            WebUtils.issueRedirect(request, response, "/auth/forcechangepassword");
            return false;
        }
        else {
            //应用程序访问验证
            if (checkApplicationAccess(loginUser, request, response, requestUri) == false) {
                return false;
            }

            //页面浏览量跟踪统计功能有效的场合
            if (Objects.equals(this.parameterService.getValue(AppCodeConsts.APP_COMMON, "TRACE_PAGE_VIEW_ENABLED", "1"), "1")) {
                //页面浏览量累加
                pageViewCount = this.operationSummaryService.tracePageViewCount(request);
            }

            //写入系统操作日志
            this.logService.writeOperationLog(request);
        }

        //详细模式变量
        request.setAttribute("isDetail", StringUtils.equals(request.getParameter("detail"), "1") ? "true" : "false");

        if (log.isDebugEnabled()) {
            Date requestStartTime = (Date) request.getAttribute("RequestStartTime");
            log.debug(String.format("拦截处理时间：%s（%s）", (System.currentTimeMillis() - requestStartTime.getTime()), requestUri));

            //记录请求开始时间
            request.setAttribute("RequestStartTime", DateUtils.getNowDate());
        }

        //初期化消息提示功能标志位
        this.initMessageNotification(loginUser, isAjaxRequest, request);

        //页面浏览量跟踪统计功能有效 && 异步结果 > 0 的场合
        if (pageViewCount != null && pageViewCount.get() > 0) {
            request.setAttribute("pageViewCount", pageViewCount.get());
            int pageViewAverageCount = this.operationSummaryService.getAverageCount();
            int pageViewCompareCount = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "PAGE_VIEW_COMPARE_COUNT", 5);
            //比较值大于平均值会产生负数浏览量比较 自动调整平均值的50%
            if (pageViewCompareCount >= pageViewAverageCount) {
                pageViewCompareCount = pageViewAverageCount / 2;
            }
            //浏览平均量 不包含忽略的路径
            request.setAttribute("pageViewAverageCount", pageViewAverageCount);
            request.setAttribute("pageViewCompareCount", pageViewCompareCount);
        }
        return true;
    }

    /**
     * IP访问限制校验
     *
     * @param user       ShiroUser
     * @param requestUri 请求URI
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @return true：无限制/false：IP访问限制
     * @throws IOException
     */
    private boolean checkAccessIp(ShiroUser user, String requestUri, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //登录画面的场合
        if (requestUri.contains("/login")) {
            return true;
        }

        String accessIp = user.getAccessIp();
        //无限制的场合
        if (StringUtils.isEmpty(accessIp)) {
            return true;
        }

        //白名单IP的场合
        if (accessIp.contains(user.getIp())) {
            return true;
        }

        //服务器主机地址
        String[] hostAddressInfos = HostUtils.getHostAddress().split("\\.");

        //用户访问地址
        String[] userIpInfos = user.getIp().split("\\.");

        //局域网内部访问的场合[ip前两位匹配]
        if (StringUtils.equals(userIpInfos[0], hostAddressInfos[0]) && StringUtils.equals(userIpInfos[1], hostAddressInfos[1])) {
            return true;
        }

        //登录Logout处理
        SecurityUtils.getSubject().logout();

        //设置Error消息
        request.getSession().setAttribute("accessIpError", String.format("非法IP访问(%s)，请与系统管理员联系。", user.getIp()));

        //页面重定向
        WebUtils.issueRedirect(request, response, "/login");
        return false;
    }

    /**
     * 初期化消息提示功能标志位
     *
     * @param loginUser     ShiroUser
     * @param isAjaxRequest 是否是Ajax请求
     * @param request       HttpServletRequest
     */
    private void initMessageNotification(ShiroUser loginUser, boolean isAjaxRequest, HttpServletRequest request) {
        //Ajax请求的场合，消息通知功能无效化
        if (isAjaxRequest) {
            return;
        }

        //用户未登录的场合，消息通知功能无效化
        if (loginUser == null) {
            return;
        }

        Boolean isMobileAccess = HttpUtils.isMobileAccess(request);
        //手机访问的场合，消息通知功能无效化
        if (isMobileAccess == true) {
            return;
        }

        //消息通知功能未启用的场合，消息通知功能无效化
        String messageNotificationEnabled = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "MESSAGE_NOTIFICATION_ENABLED", "false");
        if (StringUtils.equals(messageNotificationEnabled, "false")) {
            return;
        }

        int messageCount = this.messageDetailService.getMessageCountByUserUid(loginUser.getUserUid());
        //无任何消息的场合，消息通知功能无效化
        if (messageCount == 0) {
            return;
        }

        //消息通知功能有效/无效标志位
        request.setAttribute("messageNotificationEnabled", messageNotificationEnabled);

        //消息推送功能有效/无效标志位
        request.setAttribute("messagePushEnabled", this.parameterService.getValue(AppCodeConsts.APP_COMMON, "MESSAGE_PUSH_ENABLED", "false"));
    }

    /**
     * 初始化Request参数
     *
     * @param request Request请求对象
     */
    private void initRequestParameter(HttpServletRequest request) {
        //设置请求字符串键值对到request对象中，方便JSP画面取用
        String queryString = request.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            String[] params = queryString.split("&");
            for (String param : params) {
                //非法键值的场合，处理跳过
                if (!param.contains("=")) {
                    continue;
                }

                String[] kv = param.split("=");

                //非法键值的场合，处理跳过
                if (kv.length != 2) {
                    continue;
                }

                //设置键值对到Request对象中
                request.setAttribute(kv[0], kv[1]);
            }
        }
    }

    /**
     * 初始化系统参数
     *
     * @param request Request请求对象
     */
    private void initSystemParameter(HttpServletRequest request) {
        if (!StringUtils.contains(parameterExceptList, "companyName")) {
            //公司名称
            request.setAttribute("companyName", this.parameterService.getCompanyName());
        }

        if (!StringUtils.contains(parameterExceptList, "systemName")) {
            //系统名称
            request.setAttribute("systemName", this.parameterService.getSystemName());
        }

        //系统版本号
        request.setAttribute("version", this.parameterService.getVersion());

        //服务器端系统日期
        request.setAttribute("systemDate", DateUtils.getNowDateString(CmnConsts.DATE_TIME_FORMAT));

        if (!StringUtils.contains(parameterExceptList, "helpService")) {
            //服务热线
            request.setAttribute("helpServiceHotline", this.parameterService.getValue(AppCodeConsts.APP_COMMON, "HELP_SERVICE_HOTLINE", "未开通"));
            //QQ帮助群
            request.setAttribute("helpServiceQQGroupNO", this.parameterService.getValue(AppCodeConsts.APP_COMMON, "HELP_SERVICE_QQGROUP_NO", "未开通"));
            request.setAttribute("helpServiceQQGroupURL", this.parameterService.getValue(AppCodeConsts.APP_COMMON, "HELP_SERVICE_QQGROUP_URL", "未开通"));
            request.setAttribute("helpServiceQQGroupQR", this.parameterService.getValue(AppCodeConsts.APP_COMMON, "HELP_SERVICE_QQGROUP_QR", "未开通"));
            //BBS论坛
            request.setAttribute("helpServiceBbsURL", this.parameterService.getValue(AppCodeConsts.APP_COMMON, "HELP_SERVICE_BBS_URL", "未开通"));
        }

        //安全认证主题
        request.setAttribute("subject", SecurityUtils.getSubject());

        //是否UDC服务器
        request.setAttribute("isUdcServer", this.syncDataService.isUdcServer());

        //是否MYSQL数据库
        request.setAttribute("isMysql", Hibernates.isMysql(this.jdbcUrl));
        //是否SQLServer数据库
        request.setAttribute("isSqlServer", Hibernates.isSqlServer(this.jdbcUrl));
        //是否Oracle数据库
        request.setAttribute("isOracle", Hibernates.isOracle(this.jdbcUrl));

        //是否开启附件预览
        request.setAttribute("attachmentPreviewEnable", this.filePreviewService.getAttachmentPreviewEnable());

        if (!StringUtils.contains(parameterExceptList, "fileUpload")) {
            //Office Online Server服务器域名
            request.setAttribute("officeOnlineServerDomain", this.parameterService.getValue(AppCodeConsts.APP_COMMON, "OFFICE_ONLINE_SERVER_DOMAIN"));

            //上传文件路径session中设置
            request.getSession().setAttribute("rootUploadPath", this.parameterService.getValue(AppCodeConsts.APP_COMMON, "FILE_UPLOAD_PATH", request.getServletContext().getRealPath("/")));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (log.isDebugEnabled()) {
            Date requestStartTime = (Date) request.getAttribute("RequestStartTime");
            if (requestStartTime != null) {
                String requestUri = request.getRequestURI();
                log.debug(String.format("业务处理时间：%s ms（%s）", (System.currentTimeMillis() - requestStartTime.getTime()), requestUri));
            }
        }
    }

    /**
     * 应用程序访问验证
     *
     * @param loginUser  登录用户
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @param requestUri 请求URI
     * @throws IOException
     * @throws ServletException
     */
    private boolean checkApplicationAccess(ShiroUser loginUser, HttpServletRequest request, HttpServletResponse response, String requestUri) throws IOException, ServletException {
        //登录/退出访问的场合
        if (this.isAuthAccess(requestUri)) {
            return true;
        }

        //Error画面访问的场合
        if (RequestUtils.isErrorAccess(requestUri)) {
            return true;
        }

        //Api访问的场合
        if (RequestUtils.isApiAccess(requestUri)) {
            return true;
        }

        //共通访问的场合
        if (RequestUtils.isCommonAccess(requestUri)) {
            return true;
        }

        //Web页面访问的场合
        if (RequestUtils.isWebAccess(requestUri)) {
            return true;
        }

        //强制退出的场合
        if (this.isForceLogout(loginUser)) {
            log.warn(String.format("【%s】用户会话超时，需要重新登录。", loginUser.getUserName()));
            response.sendRedirect("/logout");
            return false;
        }

        //在线用户状态更新
        this.sessionService.updateSession(loginUser.getUserUid());

        //信息门户访问 || Admin用户访问 的场合
        if (this.isPortalAccess(requestUri) || this.isAdminUserAccess(loginUser)) {
            return true;
        }

        //应用编号
        String appCode = RequestUtils.getAppCode(RequestUtils.getRequestPathInfo(request));

        //应用编号为空的场合，处理中止
        if (StringUtils.isEmpty(appCode)) {
            return true;
        }

        //取出应用数据
        ApplicationExt application = applicationService.findByAppCode(appCode);

        //应用不存在的场合[应用未开通]
        if (application == null) {
            response.sendRedirect("/error/accessForbidden?appCd=" + appCode);
            return false;
        }
        //应用程维护中的场合
        else if (this.isApplicationMaintenance(application)) {
            //重定向到维护画面
            response.sendRedirect("/error/applicationMaintenance?appCd=" + appCode);
            return false;
        }

        if (appCode.equals(AppCodeConsts.APP_SYS)) {
            String ajaxTime = request.getParameter("ajaxTime");

            //非Ajax请求 && 无系统管理权限 && 无技术支持权限的场合，重定向到Dashboard画面
            if (StringUtils.isEmpty(ajaxTime) && !AccessUtils.isSystemManagement() && !AccessUtils.isItSupport()) {
                response.sendRedirect("/error/accessForbidden?appCd=" + appCode);
                return false;
            }
        }

        //校验URI是否具有访问权限
        if (!this.urlRoleService.checkUrlRole(requestUri)) {
            response.sendRedirect("/error/401");
            return false;
        }

        return true;
    }

    /**
     * 应用是否维护中？
     *
     * @param application 应用实体
     * @return true：维护中
     * @throws IOException
     */
    private boolean isApplicationMaintenance(ApplicationExt application) throws IOException {
        //维护日期存在的场合
        if (application.getMainteStartDate() != null && application.getMainteEndDate() != null) {
            Date now = DateUtils.getNowDate();
            //维护中的场合
            if (now.compareTo(application.getMainteEndDate()) < 0 && now.compareTo(application.getMainteStartDate()) > 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否是登录/退出访问
     *
     * @param requestUri 请求URI
     * @return true：是登录/退出/false：非登录/退出
     */
    private boolean isAuthAccess(String requestUri) {
        return StringUtils.startsWithIgnoreCase(requestUri, "/login") || StringUtils.startsWithIgnoreCase(requestUri, "/logout") ||
                StringUtils.startsWithIgnoreCase(requestUri, "/forgetPassword") || StringUtils.startsWithIgnoreCase(requestUri, "/auth");
    }

    /**
     * 是否是信息门户访问
     *
     * @param requestUri 请求URI
     * @return true：是
     */
    private boolean isPortalAccess(String requestUri) {
        return StringUtils.startsWithIgnoreCase(requestUri, "/portal") || StringUtils.startsWithIgnoreCase(requestUri, "/desktop");
    }

    /**
     * 是否是Admin用户访问
     *
     * @param loginUser 登录用户
     * @return true：是
     */
    private boolean isAdminUserAccess(ShiroUser loginUser) {
        //系统管理员的场合
        return StringUtils.equals(loginUser.getUserUid(), CmnConsts.ADMIN_USER_UID);
    }

    /**
     * 是否强制退出？(会话超时/强制退出/用户不存在等)
     *
     * @param loginUser 登录用户
     * @return true：强制退出
     */
    private boolean isForceLogout(ShiroUser loginUser) {
        String sessionTimeoutEnable = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "ONLINE_SESSION_TIMEOUT_ENABLE", "ON");

        //会话超时功能关闭的场合，处理中止
        if (!StringUtils.equals(sessionTimeoutEnable, "ON")) {
            return false;
        }

        OnlineUserExt onlineUser = onlineUserService.getOnlineUser(loginUser.getUserUid());

        //在线用户表中指定的用户不存在的场合，输出错误日志
        if (onlineUser == null) {
            log.error(String.format("【%s】在 在线用户表中不存在。", loginUser.getUserName()));
        }

        //会话超时时间[分钟]
        int sessionTimeout = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "SESSION_TIMEOUT", 30);

        //用户不存在 || 强制退出 || 会话超时 的场合
        return onlineUser == null || onlineUser.getRecordStatus() == 9 || DateUtils.addMinutes(onlineUser.getUpdateDate(), sessionTimeout).compareTo(DateUtils.getNowDate()) < 0;
    }

    /**
     * 初期化静态资源访问路径
     *
     * @param request request请求
     */
    private void initStaticsContentsServerUrl(HttpServletRequest request) {
        //获取城市编号
        String cityCd = request.getParameter("cityCd");

        //获取静态资源访问地址
        String staticContentsServer = this.parameterService.getValue(AppCodeConsts.APP_COMMON, request.getServerName().toUpperCase().replaceAll("\\.", "_") + "_STATIC_CONTENTS_SERVER");

        //静态资源访问地址不为空场合
        if (StringUtils.isNotEmpty(staticContentsServer)) {
            //如果包含? 则是需要区域过滤
            if (staticContentsServer.contains("?")) {
                //按?分隔参数，第一个为访问地址 ，第二个参数为需要过滤的区域
                String[] params = staticContentsServer.split("\\?");

                //传递城市不为空场合
                if (StringUtils.isNotEmpty(cityCd)) {
                    //城市配置访问静态资源场合
                    if (params[1].contains(cityCd)) {
                        request.setAttribute("staticContentsServer", params[0]);
                    }
                    //城市未配置访问静态资源场合
                    else {
                        request.setAttribute("staticContentsServer", request.getContextPath());
                    }
                }
                //传递城市为空场合
                else {
                    request.setAttribute("staticContentsServer", params[0]);
                }
            }
            //未配置城市过滤场合
            else {
                request.setAttribute("staticContentsServer", staticContentsServer);
            }
        }
        //为空 默认访问本地
        else {
            request.setAttribute("staticContentsServer", request.getContextPath());
        }
    }
}
