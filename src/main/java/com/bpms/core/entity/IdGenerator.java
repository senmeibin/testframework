package com.bpms.core.entity;

import com.bpms.core.utils.UniqueUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class IdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        BaseEntity entity = (BaseEntity) object;

        //实体对象为空或者UID不存在的场合、重新采番UID
        if (entity == null || StringUtils.isEmpty(entity.getUid())) {
            return UniqueUtils.getUID();
        }

        return entity.getUid();
    }
}
