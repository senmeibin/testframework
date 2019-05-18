package com.bpms.sys.service;

import com.bpms.sys.dao.PositionDao;
import com.bpms.sys.entity.ext.PositionExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 职位服务类
 */
@Service
public class PositionService extends SysBaseService<PositionExt> {
    @Autowired
    private PositionDao positionDao;

    @Override
    public PositionDao getDao() {
        return positionDao;
    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected PositionExt saveBefore(PositionExt entity) {
        // 数据添加模式的场合
        if (this.isAddMode(entity)) {
            // 职位编号自动编号
            entity.setPositionCode(this.getMaxCode("position_code", "P", 6));
        }
        return entity;
    }
}