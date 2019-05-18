package com.bpms.sys.service;

import com.bpms.core.consts.CmnConsts;
import com.bpms.sys.dao.TableSpaceOptimizeLogDao;
import com.bpms.sys.entity.ext.TableSpaceOptimizeLogExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 表空间优化日志服务类
 */
@Service
public class TableSpaceOptimizeLogService extends SysBaseService<TableSpaceOptimizeLogExt> {
    @Autowired
    private TableSpaceOptimizeLogDao tableSpaceOptimizeLogDao;

    @Override
    public TableSpaceOptimizeLogDao getDao() {
        return tableSpaceOptimizeLogDao;
    }

    /**
     * 表空间优化处理
     *
     * @param tableName 表名称
     */
    public void optimizeTable(String tableName) {
        TableSpaceOptimizeLogExt tableSpaceOptimizeLog = new TableSpaceOptimizeLogExt();
        BigDecimal beforeSize = new BigDecimal("0.00");
        BigDecimal afterSize = new BigDecimal("0.00");
        BigInteger recordCount = new BigInteger("0");

        //获取回收表之前数据的大小
        String tableSizeSql = String.format("SELECT TRUNCATE(data_length / 1024 / 1024, 2) AS dataSize FROM information_schema.tables " +
                "WHERE TABLE_SCHEMA = (SELECT DATABASE()) AND TABLE_NAME = '%s'", tableName);

        List list = tableSpaceOptimizeLogDao.getResultList(tableSizeSql, null);
        if (list.size() > 0) {
            beforeSize = (BigDecimal) ((Map) list.get(0)).get("dataSize");
        }

        //执行优化
        tableSpaceOptimizeLogDao.executeUpdate(String.format("OPTIMIZE TABLE %s ", tableName), null);

        //获取此表的条数
        list = tableSpaceOptimizeLogDao.getResultList(String.format("SELECT COUNT(*) as recordCount FROM %s", tableName), null);
        if (list.size() > 0) {
            recordCount = (BigInteger) ((Map) list.get(0)).get("recordCount");
        }
        //获取优化之后的表空间的大小
        list = tableSpaceOptimizeLogDao.getResultList(tableSizeSql, null);
        if (list.size() > 0) {
            afterSize = (BigDecimal) ((Map) list.get(0)).get("dataSize");
        }

        //写入表空间优化执行日志
        tableSpaceOptimizeLog.setLogicName(tableName);
        tableSpaceOptimizeLog.setPhysicalName(tableName);
        tableSpaceOptimizeLog.setOptimizeBeforeSize(beforeSize);
        tableSpaceOptimizeLog.setOptimizeAfterSize(afterSize);
        tableSpaceOptimizeLog.setRecordCount(recordCount.intValue());
        tableSpaceOptimizeLog.setInsertUser(CmnConsts.TASK_USER_UID);
        tableSpaceOptimizeLog.setUpdateUser(CmnConsts.TASK_USER_UID);
        tableSpaceOptimizeLog.setInsertDate(new Date());
        tableSpaceOptimizeLog.setUpdateDate(new Date());
        tableSpaceOptimizeLog.setRemark("对表空间进行优化回收。");
        tableSpaceOptimizeLogDao.save(tableSpaceOptimizeLog);
    }
}