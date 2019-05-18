package com.bpms.core.entity;

import com.bpms.core.utils.SpringUtils;
import com.bpms.core.utils.StringUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class BaseEntityAuditingListener {

    /**
     * 插入数据前处理
     *
     * @param entity 实体
     */
    @PrePersist
    public void touchForCreate(BaseEntity entity) {
        UserAuditorAware auditorAware = SpringUtils.getBean(UserAuditorAware.class);

        //InsertDate强制设定的场合
        if (entity.isInsertDateForceSetting()) {
            entity.setInsertDate(new Date());
            entity.setUpdateDate(new Date());
            entity.setInsertUser(auditorAware.getCurrentAuditor());
            entity.setUpdateUser(auditorAware.getCurrentAuditor());
        }
        else {
            if (entity.getInsertDate() == null) {
                entity.setInsertDate(new Date());
            }
            if (entity.getUpdateDate() == null) {
                entity.setUpdateDate(new Date());
            }
            if (StringUtils.isEmpty(entity.getInsertUser())) {
                entity.setInsertUser(auditorAware.getCurrentAuditor());
            }
            if (StringUtils.isEmpty(entity.getUpdateUser())) {
                entity.setUpdateUser(auditorAware.getCurrentAuditor());
            }
        }
    }

    /**
     * 更新数据前处理
     *
     * @param entity 实体
     */
    @PreUpdate
    public void touchForUpdate(BaseEntity entity) {
        entity.setUpdateDate(new Date());
        UserAuditorAware auditorAware = SpringUtils.getBean(UserAuditorAware.class);
        entity.setUpdateUser(auditorAware.getCurrentAuditor());
    }
}
