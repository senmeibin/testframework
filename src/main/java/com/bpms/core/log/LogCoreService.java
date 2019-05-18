package com.bpms.core.log;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.utils.EmojiFilterUtils;
import com.bpms.sys.dao.OperationLogDao;
import com.bpms.sys.entity.ext.ApplicationExt;
import com.bpms.sys.entity.ext.OperationLogExt;
import com.bpms.sys.service.ApplicationService;
import com.bpms.sys.service.ParameterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class LogCoreService {
    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 应用模块Service
     */
    @Autowired
    private ApplicationService applicationService;

    /**
     * 参数配置Service
     */
    @Autowired
    private ParameterService parameterService;

    /**
     * 操作日志dao
     */
    @Autowired
    private OperationLogDao operationLogDao;

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
        //应用编号为空的场合，处理中止
        if (StringUtils.isEmpty(appCode)) {
            return;
        }

        //非法应用编号的场合，处理中止
        if (appCode.length() > 16) {
            return;
        }

        //操作日志功能有效标志(ON：有效/OFF：无效)
        String writeOperationLogEnable = this.parameterService.getValue(appCode, "WRITE_OPERATION_LOG_ENABLE", "ON");

        //操作日志功能未开启的场合，忽略写入操作日志
        if (!StringUtils.equals(writeOperationLogEnable, "ON")) {
            return;
        }

        //取出应用数据
        ApplicationExt application = applicationService.findByAppCode(appCode);

        //应用不存在的场合
        if (application == null) {
            return;
        }

        if (methodName.length() > 64) {
            if (logger.isDebugEnabled()) {
                logger.error("方法名的长度超过系统规定的32个字符以内的命名规范(FunctionName:{})。", methodName);
            }
            return;
        }

        OperationLogExt logEntity = new OperationLogExt();

        //用户UID
        logEntity.setUserUid(userId);
        //用户名
        logEntity.setUserCd(userCd);
        //用户名称
        logEntity.setUserName(userName);
        //应用编号
        logEntity.setAppCode(appCode);
        //模块名称
        logEntity.setModuleName(moduleName);
        //方法名称
        logEntity.setFunctionName(methodName);
        //访问参数(绘文字过滤)
        logEntity.setParameters(EmojiFilterUtils.filterEmoji(parameters));
        //访问URI
        logEntity.setUrl(requestUri);
        //记录状态
        logEntity.setRecordStatus(Integer.valueOf(CmnConsts.RECORD_STATUS_ACTIVE));
        //日志来源
        logEntity.setLogSource(logSource);
        //日志类型
        logEntity.setLogType(logType);
        //记录
        logEntity.setInsertUser(userId);
        logEntity.setUpdateUser(userId);
        //保存
        operationLogDao.save(logEntity);
    }
}