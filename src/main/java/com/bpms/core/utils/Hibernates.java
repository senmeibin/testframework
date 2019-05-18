package com.bpms.core.utils;

import com.bpms.core.dao.SqlServerCustomDialect;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.PostgreSQL82Dialect;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Hibernates {

    /**
     * 从DataSoure中取出connection, 根据connection的metadata中的jdbcUrl判断Dialect类型. 仅支持Oracle, H2, MySql,
     * PostgreSql, SQLServer，如需更多数据库类型，请仿照此类自行编写。
     *
     * @param dataSource 数据源
     * @return Dialect类型
     */
    public static String getDialect(DataSource dataSource) {
        String jdbcUrl = getJdbcUrlFromDataSource(dataSource);

        // 根据jdbc url判断dialect
        if (StringUtils.contains(jdbcUrl, ":h2:")) {
            return H2Dialect.class.getName();
        }
        else if (StringUtils.contains(jdbcUrl, ":mysql:")) {
            return MySQL5InnoDBDialect.class.getName();
        }
        else if (StringUtils.contains(jdbcUrl, ":oracle:")) {
            return Oracle10gDialect.class.getName();
        }
        else if (StringUtils.contains(jdbcUrl, ":postgresql:")) {
            return PostgreSQL82Dialect.class.getName();
        }
        else if (StringUtils.contains(jdbcUrl, ":sqlserver:")) {
            return SqlServerCustomDialect.class.getName();
        }
        else {
            throw new IllegalArgumentException("Unknown Database of " + jdbcUrl);
        }
    }

    /**
     * 从DataSource中取得JDBC URL地址
     *
     * @param dataSource 数据源
     * @return JDBC URL地址
     */
    private static String getJdbcUrlFromDataSource(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                throw new IllegalStateException("Connection returned by DataSource [" + dataSource + "] was null");
            }
            return connection.getMetaData().getURL();
        } catch (SQLException e) {
            throw new RuntimeException("Could not get database url", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    /**
     * 是否是MySQL数据库
     *
     * @param jdbcUrl JDBC驱动程序
     * @return true：MySQL数据库
     */
    public static boolean isMysql(String jdbcUrl) {
        if (StringUtils.contains(jdbcUrl, "mysql")) {
            return true;
        }
        return false;
    }

    /**
     * 是否是SQLServer数据库
     *
     * @param jdbcUrl JDBC驱动程序
     * @return true：SQLServer数据库
     */
    public static boolean isSqlServer(String jdbcUrl) {
        if (StringUtils.contains(jdbcUrl, "sqlserver")) {
            return true;
        }
        return false;
    }

    /**
     * 是否是Oracle数据库
     *
     * @param jdbcUrl JDBC驱动程序
     * @return true：Oracle数据库
     */
    public static boolean isOracle(String jdbcUrl) {
        if (StringUtils.contains(jdbcUrl, "oracle")) {
            return true;
        }
        return false;
    }

    /**
     * 是否是Postgresql数据库
     *
     * @param jdbcUrl JDBC驱动程序
     * @return true：Postgresql数据库
     */
    public static boolean isPostgresql(String jdbcUrl) {
        if (StringUtils.contains(jdbcUrl, "postgresql")) {
            return true;
        }
        return false;
    }

    /**
     * 是否是H2数据库
     *
     * @param jdbcUrl JDBC驱动程序
     * @return true：H2数据库
     */
    public static boolean isH2(String jdbcUrl) {
        if (StringUtils.contains(jdbcUrl, "h2")) {
            return true;
        }
        return false;
    }
}
