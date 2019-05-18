package com.bpms.core.datasource;

/**
 * 使用ThreadLocal技术来记录当前线程中的数据源的key
 */
public class DynamicDataSourceHolder {
    /**
     * 使用ThreadLocal记录当前线程的数据源key
     */
    public static final ThreadLocal<String> HOLDER = new ThreadLocal<String>();

    /**
     * 写库对应的数据源key
     */
    private static final String MASTER = "master";
    /**
     * 读库对应的数据源key
     */
    private static final String SLAVE = "slave";

    /**
     * 设置数据源key
     *
     * @param key 数据源key
     */
    public static void putDataSourceKey(String key) {
        HOLDER.set(key);
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public static String getDataSource() {
        return HOLDER.get();
    }

    /**
     * 标记写库
     */
    public static void markMaster() {
        putDataSourceKey(MASTER);
    }

    /**
     * 标记读库
     */
    public static void markSlave() {
        putDataSourceKey(SLAVE);
    }
}