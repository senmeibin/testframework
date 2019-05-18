package com.bpms.sys.service;

import com.bpms.sys.dao.SensitiveDataExportLogDao;
import com.bpms.sys.entity.ext.SensitiveDataExportLogExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 敏感数据导出日志服务类
 */
@Service
public class SensitiveDataExportLogService extends SysBaseService<SensitiveDataExportLogExt> {
    @Autowired
    private SensitiveDataExportLogDao sensitiveDataExportLogDao;

    @Override
    public SensitiveDataExportLogDao getDao() {
        return sensitiveDataExportLogDao;
    }
}