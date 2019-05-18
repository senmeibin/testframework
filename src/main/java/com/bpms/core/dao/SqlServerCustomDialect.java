package com.bpms.core.dao;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StringType;

import java.sql.Types;

/**
 * 自定义SqlServer方言类
 */
public class SqlServerCustomDialect extends SQLServer2012Dialect {
    public SqlServerCustomDialect() {
        super();

        //注册自定义类型
        registerHibernateType(Types.CHAR, StringType.INSTANCE.getName());
        registerHibernateType(Types.NVARCHAR, StringType.INSTANCE.getName());
        registerHibernateType(Types.LONGNVARCHAR, StringType.INSTANCE.getName());
    }
}
