package com.bpms.core.datasource;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Value;

/**
 * 定义数据源的AOP切面，该类控制了使用Master还是Slave数据库。
 * <p>
 * 采用方法名匹配的原则，以query,find,get,search,is开头方法用Slave，其它用Master。
 * 具体配置在applicationContext.xml中进行
 */
public class DataSourceInterceptor {
    /**
     * 默认slave的方法名前缀
     */
    private static final String[] DEFAULT_SLAVE_METHOD_START = new String[]{"query", "find", "get", "search", "is"};

    /**
     * 配置的slave的方法名前缀
     */
    private String[] slaveMethodStart;

    @Value("${master.slave.database.enable}")
    private Boolean enabled;

    /**
     * 在进入Service方法之前执行
     *
     * @param point 切面对象
     */
    public void before(JoinPoint point) {
        //没有启用直接标记为主数据库
        if (!isEnabled()) {
            // 标记为写库
            DynamicDataSourceHolder.markMaster();
            return;
        }

        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        // 采用方法名匹配方式
        boolean isSlave = isSlave(methodName);
        if (isSlave) {
            // 标记为读库
            DynamicDataSourceHolder.markSlave();
        }
        else {
            // 标记为写库
            DynamicDataSourceHolder.markMaster();
        }
    }

    /**
     * 判断是否启用主从数据库
     *
     * @return true：启用
     */
    private Boolean isEnabled() {
        return this.enabled == null ? false : enabled;
    }

    /**
     * 判断是否为读库
     *
     * @param methodName 方法名
     * @return true：从数据库
     */
    private Boolean isSlave(String methodName) {
        // 方法名以query、find、get开头的方法名走从库
        return StringUtils.startsWithAny(methodName, getSlaveMethodStart());
    }

    /**
     * 获取读取slave的方法名前缀
     *
     * @return slave方法名前缀数组
     */
    public String[] getSlaveMethodStart() {
        if (this.slaveMethodStart == null || this.slaveMethodStart.length == 0) {
            // 没有指定，使用默认
            return DEFAULT_SLAVE_METHOD_START;
        }
        return slaveMethodStart;
    }

    /**
     * 设置slave的方法名前缀
     *
     * @param slaveMethodStart 指定slave的方法名前缀
     */
    public void setSlaveMethodStart(String[] slaveMethodStart) {
        this.slaveMethodStart = slaveMethodStart;
    }
}