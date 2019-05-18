package com.bpms.sys.scheduler;


import com.bpms.core.scheduler.BaseTask;
import com.bpms.core.scheduler.IBaseTask;
import com.bpms.core.utils.SchedulerUtils;
import com.bpms.sys.dao.TableSpaceOptimizeLogDao;
import com.bpms.sys.entity.ext.ExecuteRecordExt;
import com.bpms.sys.service.TableSpaceOptimizeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * 定时优化数据库表空间
 */
@Component
public class TableSpaceOptimizeTask extends BaseTask implements IBaseTask {
    @Autowired
    private TableSpaceOptimizeLogDao tableSpaceOptimizeLogDao;

    @Autowired
    private TableSpaceOptimizeLogService tableSpaceOptimizeLogService;

    /**
     * 对表空间进行优化回收空间
     */
    @Override
    @Transactional
    public void execute() {
        //非任务处理服务器的场合、处理中止
        if (!this.isTaskServer) {
            return;
        }

        //锁定执行记录
        ExecuteRecordExt executeRecord = this.lockExecute(this.getClass().getName(), "execute", "对表空间进行优化回收空间", "系统管理", "系统管理", SchedulerUtils.FUNCTION_TYPE_TASK, "对表空间进行优化回收空间");

        //锁定失败的场合、处理中止
        if (executeRecord == null || !executeRecord.isLockSuccess()) {
            return;
        }

        long startTime = System.currentTimeMillis();
        log.info("定时优化数据库表空间[开始]");

        boolean isException = false;
        try {
            //获取数据库下所有的表
            List<String> tables = tableSpaceOptimizeLogDao.getAllTableNames();

            StringBuilder optimizeTables = new StringBuilder();

            for (String table : tables) {
                //表空间优化处理
                this.tableSpaceOptimizeLogService.optimizeTable(table);

                //执行sql的内容
                optimizeTables.append(String.format("OPTIMIZE TABLE %s;\\n", table));
            }

            //新增执行日志
            this.insertExecuteLog(executeRecord, optimizeTables.toString(), 1, null, "表空间优化完成。", 0);
        } catch (Exception e) {
            //新增执行异常日志
            this.insertExecuteLog(executeRecord, null, -1, "EXCEPTION", e.getMessage(), 0);
            isException = true;
        } finally {
            //释放执行记录上的锁
            this.unlockExecute(executeRecord, isException);
            log.info("定时优化数据库表空间[结束]-耗时：{}毫秒", System.currentTimeMillis() - startTime);
        }
    }
}

