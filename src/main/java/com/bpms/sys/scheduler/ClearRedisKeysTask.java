package com.bpms.sys.scheduler;

import com.bpms.core.cache.RedisCacheManager;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.scheduler.BaseTask;
import com.bpms.core.scheduler.IBaseTask;
import com.bpms.core.utils.SchedulerUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时清理redis key
 */
@Component
public class ClearRedisKeysTask extends BaseTask implements IBaseTask {

    @Autowired
    private RedisCacheManager redisCacheManager;

    /**
     * 定时清理redis key
     */
    @Override
    public void execute() {
        //非任务处理服务器的场合、处理中止
        if (!this.isTaskServer) {
            return;
        }

        //是否启用redis 定时清理任务  true:清理；false:不清理；默认是false
        String enableClear = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "CLEAR_REDIS_KEYS_ENABLE", "false");
        if (StringUtils.equals(enableClear, "true")) {
            //redis key 数量达到指定的数量时进行清理 默认80000
            Integer redisLimitSize = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "CLEAR_REDIS_KEYS_LIMIT_SIZE", 80000);

            //锁定执行记录
            ExecuteRecordExt executeRecord = this.lockExecute(this.getClass().getName(), "execute", "redis key定时清理", "系统管理", "系统管理", SchedulerUtils.FUNCTION_TYPE_TASK, "每隔10分钟定时清理redis key");

            //锁定失败的场合、处理中止
            if (executeRecord == null || !executeRecord.isLockSuccess()) {
                return;
            }

            long startTime = System.currentTimeMillis();
            log.info("定时清理redis key[开始]");

            boolean isException = false;
            try {
                Long redisKeyCount = redisCacheManager.dbSize();
                if (redisKeyCount > redisLimitSize) {
                    Long deleteCount = redisCacheManager.deleteAll();
                    //新增执行日志
                    this.insertExecuteLog(executeRecord, String.format("redisKeyCount：%s；allowRedisKeyCount：%s；deleteCount：%s", redisKeyCount, redisLimitSize, deleteCount), 1, null, null, deleteCount.intValue());
                    log.info("共清理{}条Redis记录。", deleteCount);
                }
                else {
                    log.info("只有{}条Redis记录，无需清理。", redisKeyCount);
                }
            } catch (Exception e) {
                isException = true;
                //新增执行异常日志
                this.insertExecuteLog(executeRecord, null, -1, "EXCEPTION", e.getMessage(), 0);
            } finally {
                //释放执行记录上的锁
                this.unlockExecute(executeRecord, isException);
                log.info("定时清理redis key[结束]-耗时：{}毫秒", System.currentTimeMillis() - startTime);
            }
        }
    }
}
