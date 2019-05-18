package com.bpms.sys.service;

import com.bpms.sys.dao.DatabaseProcessOperationLogDao;
import com.bpms.sys.entity.ext.DatabaseProcessOperationLogExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据库进程操作日志服务类
 */
@Service
public class DatabaseProcessOperationLogService extends SysBaseService<DatabaseProcessOperationLogExt> {
    @Autowired
    private DatabaseProcessOperationLogDao databaseProcessOperationLogDao;

    @Override
    public DatabaseProcessOperationLogDao getDao() {
        return databaseProcessOperationLogDao;
    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected DatabaseProcessOperationLogExt saveBefore(DatabaseProcessOperationLogExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected DatabaseProcessOperationLogExt saveAfter(DatabaseProcessOperationLogExt entity) {
        //杀死选中的进程
        getDao().executeUpdate(String.format("KILL %s", entity.getProcessId()), null);

        return entity;
    }
}