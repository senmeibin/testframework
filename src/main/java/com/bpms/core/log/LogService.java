package com.bpms.core.log;

import com.bpms.activemq.consts.JMSConsts;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.security.ShiroUser;
import com.bpms.core.utils.JsonUtils;
import com.bpms.core.utils.RequestUtils;
import com.bpms.sys.entity.OperationLog;
import com.bpms.sys.service.ParameterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 写操作日志service
 */
@Service
public class LogService {
    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 是否记录操作日志(默认记录)
     */
    @Value("${write.operation.log.enable:true}")
    protected boolean isWriteOperationLog;
    /**
     * ActiveMQ发送队列Service
     */
    @Autowired
    private ActiveMQSenderService activeMQSenderService;
    /**
     * 写操作日志service
     */
    @Autowired
    private LogCoreService logCoreService;
    @Autowired
    private ParameterService parameterService;

    /**
     * 写操作日志
     *
     * @param request request请求
     */
    public void writeOperationLog(HttpServletRequest request) {
        if (!this.isWriteOperationLog) {
            return;
        }

        //请求路径信息
        String[] requestPathInfo = RequestUtils.getRequestPathInfo(request);

        //方法名
        String methodName = RequestUtils.getMethodName(requestPathInfo);

        //日志记录除外方法过滤
        if (StringUtils.equals(methodName, "search") || StringUtils.equals(methodName, "detail") || StringUtils.equals(methodName, "list") || StringUtils.equals(methodName, "input") || StringUtils.equals(methodName, "default") ||
                StringUtils.isEmpty(methodName) || StringUtils.startsWithIgnoreCase(methodName, "get") || StringUtils.startsWithIgnoreCase(methodName, "init")) {
            return;
        }

        //请求URI
        String requestUri = request.getRequestURI().replaceFirst(request.getContextPath(), StringUtils.EMPTY);

        //操作日志写入除外路径
        String exceptOperationLogPath = parameterService.getValue(AppCodeConsts.APP_COMMON, "EXCEPT_OPERATION_LOG_PATH");
        //除外路径存在的场合
        if (StringUtils.isNotEmpty(exceptOperationLogPath)) {
            String[] paths = StringUtils.split(exceptOperationLogPath, "|");
            for (String path : paths) {
                //当前访问路径 == 除外路径的 场合
                if (StringUtils.equals(path, requestUri)) {
                    return;
                }
            }
        }

        ShiroUser userInfo = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //认证用户的场合
        if (userInfo != null) {
            this.writeOperationLog(userInfo.getUserUid(), userInfo.getUserCd(), userInfo.getUserName(), RequestUtils.getAppCode(requestPathInfo), RequestUtils.getModuleName(requestPathInfo), methodName, RequestUtils.getParameters(request), requestUri, null, null);
        }
        //非认证用户(API访问的场合)
        else if (RequestUtils.isApiAccess(requestUri)) {
            this.writeOperationLog(CmnConsts.API_USER_UID, "apiuser", "API用户", RequestUtils.getAppCode(requestPathInfo), RequestUtils.getModuleName(requestPathInfo), methodName, RequestUtils.getParameters(request), requestUri, null, null);
        }
    }

    /**
     * 写操作日志
     *
     * @param userId     用户UID
     * @param userCd     用户CD
     * @param userName   用户名
     * @param appCode    应用app
     * @param moduleName 模块名
     * @param methodName 方法名
     * @param parameters 参数
     * @param requestUri 路径
     * @param logSource  日志来源
     * @param logType    日志类型
     */
    public void writeOperationLog(String userId, String userCd, String userName, String appCode, String moduleName, String methodName, String parameters, String requestUri, String logSource, String logType) {
        try {
            //启用ActiveMQ
            if (activeMQSenderService.activeMQEnabled()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("已启用ActiveMQ服务，向日志队列推送日志消息。");
                }
                OperationLog operationLog = new OperationLog();
                operationLog.setUserUid(userId);
                operationLog.setUserCd(userCd);
                operationLog.setUserName(userName);
                operationLog.setAppCode(appCode);
                operationLog.setFunctionName(methodName);
                operationLog.setModuleName(moduleName);
                operationLog.setParameters(parameters);
                operationLog.setUrl(requestUri);
                operationLog.setLogSource(logSource);
                operationLog.setLogType(logType);
                activeMQSenderService.pushQueue(JMSConsts.JMS_QUEUE_NAME_LOG, JsonUtils.toJSON(operationLog));
            }
            //不启用ActiveMQ
            else {
                if (logger.isDebugEnabled()) {
                    logger.debug("没有启用ActiveMQ服务，直接使用日志接口写日志。");
                }
                this.syncWriteOperationLog(userId, userCd, userName, appCode, moduleName, methodName, parameters, requestUri, logSource, logType);
            }
        } catch (Exception e) {
            logger.error("日志记录错误", e);
        }
    }

    /**
     * 同步写操作日志
     *
     * @param userId     用户UID
     * @param userCd     用户CD
     * @param userName   用户名
     * @param appCode    应用app
     * @param moduleName 模块名
     * @param methodName 方法名
     * @param parameters 参数
     * @param requestUri 路径
     * @param logSource  日志来源
     * @param logType    日志类型
     */
    public void syncWriteOperationLog(String userId, String userCd, String userName, String appCode, String moduleName, String methodName, String parameters, String requestUri, String logSource, String logType) {
        this.logCoreService.writeOperationLog(userId, userCd, userName, appCode, moduleName, methodName, parameters, requestUri, logSource, logType);
    }
}
