package com.bpms.core.cache;

import com.bpms.core.sql.SqlReader;
import com.bpms.core.utils.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.dao.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * redis Cache管理类
 */
@Component
@SuppressWarnings("unchecked,unused")
public class RedisCacheManager {

    /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 默认缓存名称 必须指定
     */
    @Value("${redis.systemCode}")
    private String defaultCacheName;

    /**
     * 缓存分隔符
     */
    @Value("${redis.cachePrefix}")
    private String cachePrefix;

    /**
     * redis 缓存模板
     */
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * redis利用标识位
     */
    @Value("${redis.enable:false}")
    private boolean redisEnable;

    /**
     * 生命周期
     */
    @Value("${redis.cacheSeconds:1800}")
    private Integer redisCacheSeconds;

    /**
     * 系统启动时自动清除DSQL缓存
     */
    @PostConstruct
    private void deleteDSQLCache() {
        synchronized (this) {
            //取出所有前缀为DSQL key的内容
            Set<String> keySet = keys(SqlReader.CACHE_PREFIX_KEY + "*");
            //删除相应的key
            delete(keySet);

            log.info(String.format("系统启动，自动清除DSQL缓存，共清除%s条DSQL缓存。", keySet.size()));
        }
    }

    /**
     * RedisKey修复
     *
     * @param cacheName 缓存名称
     * @param key       原始RedisKey
     * @return 修复后的RedisKey
     */
    private String prepareKey(String cacheName, String key) {
        //RedisKey中包含缓存名称的场合，过滤缓存名称
        if (StringUtils.startsWithIgnoreCase(key, cacheName + this.cachePrefix)) {
            key = key.substring((cacheName + this.cachePrefix).length());
        }
        return key;
    }

    /**
     * 该属性对应的值
     *
     * @param key 属性key
     * @return 该属性对应的值
     */
    public Object get(String key) {
        //使用默认前缀
        return get(defaultCacheName, key);
    }

    /**
     * 该属性对应的值
     *
     * @param cacheName 缓存名称/前缀
     * @param key       属性key
     * @return 该属性对应的值
     */
    public Object get(String cacheName, String key) {
        Object result = null;
        if (redisEnable) {
            key = this.prepareKey(cacheName, key);
            long startTime = System.currentTimeMillis();
            result = redisTemplate.opsForValue().get(key);
            this.tracePerformance(key, startTime);
        }
        return result;
    }

    /**
     * 该属性对应的值 （根据指定类型）
     *
     * @param cls 返回值的类型
     * @param key 属性key
     * @param <E> 返回值的类型
     * @return 该属性对应的值 （根据指定类型）
     */
    public <E> E get(Class<E> cls, String key) {
        return get(cls, defaultCacheName, key);
    }

    /**
     * 该属性对应的值 （根据指定类型）
     *
     * @param cls 返回值的类型
     * @param key 属性key
     * @param <E> 返回值的类型
     * @return 该属性对应的值 （根据指定类型）
     */
    public <E> E get(Class<E> cls, String cacheName, String key) {
        E result = null;
        if (redisEnable) {
            long startTime = System.currentTimeMillis();
            key = this.prepareKey(cacheName, key);
            result = (E) redisTemplate.opsForValue().get(cacheName + cachePrefix + key);
            this.tracePerformance(key, startTime);
        }
        return result;
    }

    /**
     * 该属性对应的值 （根据指定类型）的列表
     *
     * @param cls 返回值的类型
     * @param key 属性key
     * @param <E> 返回值的类型
     * @return 该属性对应的值 （根据指定类型）
     */
    public <E> List<E> getList(Class<E> cls, String key) {
        return getList(cls, defaultCacheName, key);
    }

    /**
     * 该属性对应的值 （根据指定类型）的列表
     *
     * @param cls 返回值的类型
     * @param key 属性key
     * @param <E> 返回值的类型
     * @return 该属性对应的值 （根据指定类型）
     */
    public <E> List<E> getList(Class<E> cls, String cacheName, String key) {
        List<E> result = null;
        if (redisEnable) {
            long startTime = System.currentTimeMillis();
            key = this.prepareKey(cacheName, key);
            result = (List<E>) redisTemplate.opsForValue().get(cacheName + cachePrefix + key);
            this.tracePerformance(key, startTime);
        }
        return result;
    }

    /**
     * Redis性能跟踪
     *
     * @param key       属性key
     * @param startTime 开始时间
     */
    private void tracePerformance(String key, long startTime) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("RedisKey（%s）查询时间：%s ms", key, System.currentTimeMillis() - startTime));
        }
    }

    /**
     * 设置数据
     *
     * @param key   对应RedisKey
     * @param value 数据对象
     */
    public void set(String key, Object value) {
        this.set(key, value, true);
    }

    /**
     * 设置数据(缓存过期时间)
     *
     * @param key        属性key
     * @param value      数据对象
     * @param needExpire (true: 从配置文件取过期时间， false:不过期）
     */
    public void set(String key, Object value, boolean needExpire) {
        if (redisEnable) {
            long startTime = System.currentTimeMillis();
            if (needExpire) {
                redisTemplate.opsForValue().set(defaultCacheName + cachePrefix + key, value, redisCacheSeconds, TimeUnit.SECONDS);
            }
            else {
                redisTemplate.opsForValue().set(defaultCacheName + cachePrefix + key, value);
            }
            this.tracePerformance(key, startTime);
        }
    }

    /**
     * 得到当前数据库key数量
     * 如果为空 则db未连接
     *
     * @return key数量
     */
    public Long dbSize() {
        Long result = 0L;
        if (redisEnable) {
            result = (long) redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.dbSize();
                }
            });
        }
        return result;
    }

    /**
     * 根据传入的key的pattern 得到redis 的key 组合
     *
     * @param keyPattern redis key pattern
     * @return redis 的key 组合
     */
    public Set<String> keys(String keyPattern) {
        Set<String> sortedKeyList = new TreeSet();
        if (redisEnable) {
            //如果传入参数为空， 返回所有的key
            if (StringUtils.isEmpty(keyPattern)) {
                keyPattern = defaultCacheName + "*";
            }
            else if (!StringUtils.contains(keyPattern, cachePrefix)) {
                keyPattern = defaultCacheName + cachePrefix + keyPattern;
            }
            sortedKeyList.addAll(redisTemplate.keys(keyPattern));
        }
        return sortedKeyList;
    }

    /**
     * 删除redis的数据
     *
     * @param keySet redis数据的key set
     */
    public void delete(Set<String> keySet) {
        if (CollectionUtils.isNotEmpty(keySet)) {
            //从keys取出的redisKey 是完全的key， 不需要加systemCode前缀
            keySet.forEach(this::delete);
        }
    }

    /**
     * 删除redis的数据
     *
     * @param key redis数据的key值
     */
    public void delete(String key) {
        if (redisEnable) {
            delete(defaultCacheName, key);
        }
    }

    /**
     * 删除redis的数据
     *
     * @param cacheName 缓存名/前缀
     * @param key       redis数据的key值
     */
    public void delete(String cacheName, String key) {
        if (redisEnable && StringUtils.isNotEmpty(key)) {
            if (StringUtils.startsWithIgnoreCase(key, cacheName + cachePrefix)) {
                redisTemplate.delete(key);
            }
            else {
                redisTemplate.delete(cacheName + cachePrefix + key);
            }
        }
    }

    /**
     * 清除所有数据
     *
     * @return 清除数据前key总数
     */
    public long deleteAll() {
        long result = 0;
        if (redisEnable) {
            result = (long) redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    long dbSize = connection.dbSize();
                    connection.flushDb();
                    return dbSize;
                }
            });
        }
        return result;
    }

    /**
     * 设置数据（带过期时间）
     *
     * @param key           属性key
     * @param value         数据对象
     * @param expireMinutes 过期时间 单位（分钟）
     */
    public void set(String key, Object value, int expireMinutes) {
        if (redisEnable) {
            long startTime = System.currentTimeMillis();
            redisTemplate.opsForValue().set(defaultCacheName + cachePrefix + key, value, expireMinutes, TimeUnit.MINUTES);
            this.tracePerformance(key, startTime);
        }
    }
}