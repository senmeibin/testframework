package com.bpms.core.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.redis.ClusterStateFailureException;
import org.springframework.data.redis.ExceptionTranslationStrategy;
import org.springframework.data.redis.PassThroughExceptionTranslationStrategy;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.convert.Converters;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.data.redis.connection.jedis.JedisSentinelConnection;
import org.springframework.data.util.DirectFieldAccessFallbackBeanWrapper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Connection factory creating <a href="http://github.com/xetorthio/jedis">Jedis</a> based connections.<br/>
 * 增加redis起用标识位，在初始化时判断是否执行代码<br/>
 * 在public RedisConnection getConnection() 这个方法中，<b>直接返回集群的链接，所以非集群请直接使用原版JedisConnectionFactory</b>
 *
 * @author Costin Leau
 * @author Thomas Darimont
 * @author Christoph Strobl
 * @author Mark Paluch
 * @author Fu Jian
 * @see org.springframework.data.redis.connection.jedis.JedisConnectionFactory
 */
public class JedisConnectionFactory implements InitializingBean, DisposableBean, RedisConnectionFactory {

    private final static Log log = LogFactory.getLog(org.springframework.data.redis.connection.jedis.JedisConnectionFactory.class);
    private static final ExceptionTranslationStrategy EXCEPTION_TRANSLATION = new PassThroughExceptionTranslationStrategy(
            JedisConverters.exceptionConverter());

    private static final Method SET_TIMEOUT_METHOD;
    private static final Method GET_TIMEOUT_METHOD;

    static {

        // We need to configure Jedis socket timeout via reflection since the method-name was changed between releases.
        Method setTimeoutMethodCandidate = ReflectionUtils.findMethod(JedisShardInfo.class, "setTimeout", int.class);
        if (setTimeoutMethodCandidate == null) {
            // Jedis V 2.7.x changed the setTimeout method to setSoTimeout
            setTimeoutMethodCandidate = ReflectionUtils.findMethod(JedisShardInfo.class, "setSoTimeout", int.class);
        }
        SET_TIMEOUT_METHOD = setTimeoutMethodCandidate;

        Method getTimeoutMethodCandidate = ReflectionUtils.findMethod(JedisShardInfo.class, "getTimeout");
        if (getTimeoutMethodCandidate == null) {
            getTimeoutMethodCandidate = ReflectionUtils.findMethod(JedisShardInfo.class, "getSoTimeout");
        }

        GET_TIMEOUT_METHOD = getTimeoutMethodCandidate;
    }

    /**
     * redis起用标识位
     */
    @Value("${redis.enable:false}")
    private boolean redisEnable;
    private JedisShardInfo shardInfo;
    private String hostName = "localhost";
    private int port = Protocol.DEFAULT_PORT;
    private int timeout = Protocol.DEFAULT_TIMEOUT;
    private String password;
    private boolean usePool = true;
    private boolean useSsl = false;
    private redis.clients.util.Pool<Jedis> pool;
    private JedisPoolConfig poolConfig = new JedisPoolConfig();
    private int dbIndex = 0;
    private String clientName;
    private boolean convertPipelineAndTxResults = true;
    private RedisSentinelConfiguration sentinelConfig;
    private RedisClusterConfiguration clusterConfig;
    private JedisCluster cluster;
    private ClusterCommandExecutor clusterCommandExecutor;

    /**
     * Constructs a new <code>JedisConnectionFactory</code> instance with default settings (default connection pooling, no
     * shard information).
     */
    public JedisConnectionFactory() {
    }

    /**
     * Constructs a new <code>JedisConnectionFactory</code> instance. Will override the other connection parameters passed
     * to the factory.
     *
     * @param shardInfo shard information
     */
    public JedisConnectionFactory(JedisShardInfo shardInfo) {
        this.shardInfo = shardInfo;
    }

    /**
     * Constructs a new <code>JedisConnectionFactory</code> instance using the given pool configuration.
     *
     * @param poolConfig pool configuration
     */
    public JedisConnectionFactory(JedisPoolConfig poolConfig) {
        this((RedisSentinelConfiguration) null, poolConfig);
    }

    /**
     * Constructs a new {@link org.springframework.data.redis.connection.jedis.JedisConnectionFactory} instance using the given {@link JedisPoolConfig} applied to
     * {@link JedisSentinelPool}.
     *
     * @param sentinelConfig
     * @since 1.4
     */
    public JedisConnectionFactory(RedisSentinelConfiguration sentinelConfig) {
        this(sentinelConfig, null);
    }

    /**
     * Constructs a new {@link org.springframework.data.redis.connection.jedis.JedisConnectionFactory} instance using the given {@link JedisPoolConfig} applied to
     * {@link JedisSentinelPool}.
     *
     * @param sentinelConfig
     * @param poolConfig     pool configuration. Defaulted to new instance if {@literal null}.
     * @since 1.4
     */
    public JedisConnectionFactory(RedisSentinelConfiguration sentinelConfig, JedisPoolConfig poolConfig) {
        this.sentinelConfig = sentinelConfig;
        this.poolConfig = poolConfig != null ? poolConfig : new JedisPoolConfig();
    }

    /**
     * Constructs a new {@link org.springframework.data.redis.connection.jedis.JedisConnectionFactory} instance using the given {@link RedisClusterConfiguration} applied
     * to create a {@link JedisCluster}.
     *
     * @param clusterConfig
     * @since 1.7
     */
    public JedisConnectionFactory(RedisClusterConfiguration clusterConfig) {
        this.clusterConfig = clusterConfig;
    }

    /**
     * Constructs a new {@link org.springframework.data.redis.connection.jedis.JedisConnectionFactory} instance using the given {@link RedisClusterConfiguration} applied
     * to create a {@link JedisCluster}.
     *
     * @param clusterConfig
     * @since 1.7
     */
    public JedisConnectionFactory(RedisClusterConfiguration clusterConfig, JedisPoolConfig poolConfig) {
        this.clusterConfig = clusterConfig;
        this.poolConfig = poolConfig;
    }

    /**
     * Returns a Jedis instance to be used as a Redis connection. The instance can be newly created or retrieved from a
     * pool.
     *
     * @return Jedis instance ready for wrapping into a {@link RedisConnection}.
     */
    protected Jedis fetchJedisConnector() {
        try {

            if (usePool && pool != null) {
                return pool.getResource();
            }

            Jedis jedis = new Jedis(getShardInfo());
            // force initialization (see Jedis issue #82)
            jedis.connect();

            potentiallySetClientName(jedis);
            return jedis;
        } catch (Exception ex) {
            throw new RedisConnectionFailureException("Cannot get Jedis connection", ex);
        }
    }

    /**
     * Post process a newly retrieved connection. Useful for decorating or executing initialization commands on a new
     * connection. This implementation simply returns the connection.
     *
     * @param connection
     * @return processed connection
     */
    protected JedisConnection postProcessConnection(JedisConnection connection) {
        return connection;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {
        if (!redisEnable) {
            return;
        }
        if (shardInfo == null) {
            shardInfo = new JedisShardInfo(hostName, port);

            if (StringUtils.hasLength(password)) {
                shardInfo.setPassword(password);
            }

            if (timeout > 0) {
                setTimeoutOn(shardInfo, timeout);
            }
        }

        if (usePool && clusterConfig == null) {
            this.pool = createPool();
        }

        if (clusterConfig != null) {
            this.cluster = createCluster();
        }
    }

    private redis.clients.util.Pool<Jedis> createPool() {

        if (isRedisSentinelAware()) {
            return createRedisSentinelPool(this.sentinelConfig);
        }
        return createRedisPool();
    }

    /**
     * Creates {@link JedisSentinelPool}.
     *
     * @param config
     * @return
     * @since 1.4
     */
    protected redis.clients.util.Pool<Jedis> createRedisSentinelPool(RedisSentinelConfiguration config) {
        return new JedisSentinelPool(config.getMaster().getName(), convertToJedisSentinelSet(config.getSentinels()),
                getPoolConfig() != null ? getPoolConfig() : new JedisPoolConfig(), getTimeoutFrom(getShardInfo()),
                getShardInfo().getPassword(), Protocol.DEFAULT_DATABASE, clientName);
    }

    /**
     * Creates {@link JedisPool}.
     *
     * @return
     * @since 1.4
     */
    protected Pool<Jedis> createRedisPool() {

        return new JedisPool(getPoolConfig(), getShardInfo().getHost(), getShardInfo().getPort(),
                getTimeoutFrom(getShardInfo()), getShardInfo().getPassword(), Protocol.DEFAULT_DATABASE, clientName, useSsl);
    }

    private JedisCluster createCluster() {

        JedisCluster cluster = createCluster(this.clusterConfig, getPoolConfig());
        JedisClusterTopologyProvider topologyProvider = new JedisClusterTopologyProvider(cluster);
        this.clusterCommandExecutor = new ClusterCommandExecutor(topologyProvider,
                new JedisClusterNodeResourceProvider(cluster, topologyProvider), EXCEPTION_TRANSLATION);
        return cluster;
    }

    /**
     * Creates {@link JedisCluster} for given {@link RedisClusterConfiguration} and {@link GenericObjectPoolConfig}.
     *
     * @param clusterConfig must not be {@literal null}.
     * @param poolConfig    can be {@literal null}.
     * @return
     * @since 1.7
     */
    protected JedisCluster createCluster(RedisClusterConfiguration clusterConfig, GenericObjectPoolConfig poolConfig) {

        Assert.notNull(clusterConfig, "Cluster configuration must not be null!");

        Set<HostAndPort> hostAndPort = new HashSet<HostAndPort>();
        for (RedisNode node : clusterConfig.getClusterNodes()) {
            hostAndPort.add(new HostAndPort(node.getHost(), node.getPort()));
        }

        int redirects = clusterConfig.getMaxRedirects() != null ? clusterConfig.getMaxRedirects().intValue() : 5;

        return StringUtils.hasText(getPassword())
                ? new JedisCluster(hostAndPort, timeout, timeout, redirects, password, poolConfig)
                : new JedisCluster(hostAndPort, timeout, redirects, poolConfig);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() {
        if (usePool && pool != null) {
            try {
                pool.destroy();
            } catch (Exception ex) {
                log.warn("Cannot properly close Jedis pool", ex);
            }
            pool = null;
        }
        if (cluster != null) {
            try {
                cluster.close();
            } catch (Exception ex) {
                log.warn("Cannot properly close Jedis cluster", ex);
            }
            try {
                clusterCommandExecutor.destroy();
            } catch (Exception ex) {
                log.warn("Cannot properly close cluster command executor", ex);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.connection.RedisConnectionFactory#getConnection()
     */
    @Override
    public RedisConnection getConnection() {
        //if (cluster != null) {
        return getClusterConnection();
        //}

        /*Jedis jedis = fetchJedisConnector();
        JedisConnection connection = (usePool ? new JedisConnection(jedis, pool, dbIndex, clientName)
                : new JedisConnection(jedis, null, dbIndex, clientName));
        connection.setConvertPipelineAndTxResults(convertPipelineAndTxResults);
        return postProcessConnection(connection);*/
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.connection.RedisConnectionFactory#getClusterConnection()
     */
    @Override
    public RedisClusterConnection getClusterConnection() {

        if (cluster == null) {
            throw new InvalidDataAccessApiUsageException("Cluster is not configured!");
        }
        return new JedisClusterConnection(cluster, clusterCommandExecutor);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.dao.support.PersistenceExceptionTranslator#translateExceptionIfPossible(java.lang.RuntimeException)
     */
    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return EXCEPTION_TRANSLATION.translate(ex);
    }

    /**
     * Returns the Redis hostName.
     *
     * @return the hostName.
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the Redis hostName.
     *
     * @param hostName the hostName to set.
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Returns whether to use SSL.
     *
     * @return use of SSL.
     * @since 1.8
     */
    public boolean isUseSsl() {
        return useSsl;
    }

    /**
     * Sets whether to use SSL.
     *
     * @param useSsl {@literal true} to use SSL.
     * @since 1.8
     */
    public void setUseSsl(boolean useSsl) {
        this.useSsl = useSsl;
    }

    /**
     * Returns the password used for authenticating with the Redis server.
     *
     * @return password for authentication.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password used for authenticating with the Redis server.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the port used to connect to the Redis instance.
     *
     * @return the Redis port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port used to connect to the Redis instance.
     *
     * @param port the Redis port.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns the shardInfo.
     *
     * @return the shardInfo.
     */
    public JedisShardInfo getShardInfo() {
        return shardInfo;
    }

    /**
     * Sets the shard info for this factory.
     *
     * @param shardInfo the shardInfo to set.
     */
    public void setShardInfo(JedisShardInfo shardInfo) {
        this.shardInfo = shardInfo;
    }

    /**
     * Returns the timeout.
     *
     * @return the timeout.
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets the timeout.
     *
     * @param timeout the timeout to set.
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Indicates the use of a connection pool.
     *
     * @return the use of connection pooling.
     */
    public boolean getUsePool() {
        return usePool;
    }

    /**
     * Turns on or off the use of connection pooling.
     *
     * @param usePool the usePool to set.
     */
    public void setUsePool(boolean usePool) {
        this.usePool = usePool;
    }

    /**
     * Returns the poolConfig.
     *
     * @return the poolConfig
     */
    public JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

    /**
     * Sets the pool configuration for this factory.
     *
     * @param poolConfig the poolConfig to set.
     */
    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    /**
     * Returns the index of the database.
     *
     * @return the database index.
     */
    public int getDatabase() {
        return dbIndex;
    }

    /**
     * Sets the index of the database used by this connection factory. Default is 0.
     *
     * @param index database index.
     */
    public void setDatabase(int index) {
        Assert.isTrue(index >= 0, "invalid DB index (a positive index required)");
        this.dbIndex = index;
    }

    /**
     * Returns the client name.
     *
     * @return the client name.
     * @since 1.8
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the client name used by this connection factory. Defaults to none which does not set a client name.
     *
     * @param clientName the client name.
     * @since 1.8
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Specifies if pipelined results should be converted to the expected data type. If false, results of
     * {@link JedisConnection#closePipeline()} and {@link JedisConnection#exec()} will be of the type returned by the
     * Jedis driver.
     *
     * @return Whether or not to convert pipeline and tx results.
     */
    @Override
    public boolean getConvertPipelineAndTxResults() {
        return convertPipelineAndTxResults;
    }

    /**
     * Specifies if pipelined results should be converted to the expected data type. If false, results of
     * {@link JedisConnection#closePipeline()} and {@link JedisConnection#exec()} will be of the type returned by the
     * Jedis driver.
     *
     * @param convertPipelineAndTxResults Whether or not to convert pipeline and tx results.
     */
    public void setConvertPipelineAndTxResults(boolean convertPipelineAndTxResults) {
        this.convertPipelineAndTxResults = convertPipelineAndTxResults;
    }

    /**
     * @return true when {@link RedisSentinelConfiguration} is present.
     * @since 1.4
     */
    public boolean isRedisSentinelAware() {
        return sentinelConfig != null;
    }

    /* (non-Javadoc)
     * @see org.springframework.data.redis.connection.RedisConnectionFactory#getSentinelConnection()
     */
    @Override
    public RedisSentinelConnection getSentinelConnection() {

        if (!isRedisSentinelAware()) {
            throw new InvalidDataAccessResourceUsageException("No Sentinels configured");
        }

        return new JedisSentinelConnection(getActiveSentinel());
    }

    private Jedis getActiveSentinel() {

        Assert.notNull(this.sentinelConfig, "SentinelConfig must not be null!");

        for (RedisNode node : this.sentinelConfig.getSentinels()) {

            Jedis jedis = new Jedis(node.getHost(), node.getPort());

            if (jedis.ping().equalsIgnoreCase("pong")) {

                potentiallySetClientName(jedis);
                return jedis;
            }
        }

        throw new InvalidDataAccessResourceUsageException("No Sentinel found");
    }

    private Set<String> convertToJedisSentinelSet(Collection<RedisNode> nodes) {

        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.emptySet();
        }

        Set<String> convertedNodes = new LinkedHashSet<String>(nodes.size());
        for (RedisNode node : nodes) {
            if (node != null) {
                convertedNodes.add(node.asString());
            }
        }
        return convertedNodes;
    }

    private void potentiallySetClientName(Jedis jedis) {

        if (StringUtils.hasText(clientName)) {
            jedis.clientSetname(clientName);
        }
    }

    private void setTimeoutOn(JedisShardInfo shardInfo, int timeout) {
        ReflectionUtils.invokeMethod(SET_TIMEOUT_METHOD, shardInfo, timeout);
    }

    private int getTimeoutFrom(JedisShardInfo shardInfo) {
        return (Integer) ReflectionUtils.invokeMethod(GET_TIMEOUT_METHOD, shardInfo);
    }


    /**
     * Jedis specific implementation of {@link ClusterTopologyProvider}.
     *
     * @author Christoph Strobl
     * @since 1.7
     */
    static class JedisClusterTopologyProvider implements ClusterTopologyProvider {

        private final Object lock = new Object();
        private final JedisCluster cluster;
        private long time = 0;
        private ClusterTopology cached;

        /**
         * Create new {@link JedisClusterConnection.JedisClusterTopologyProvider}.s
         *
         * @param cluster
         */
        public JedisClusterTopologyProvider(JedisCluster cluster) {
            this.cluster = cluster;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.connection.ClusterTopologyProvider#getTopology()
         */
        @Override
        public ClusterTopology getTopology() {

            if (cached != null && time + 100 > System.currentTimeMillis()) {
                return cached;
            }

            Map<String, Exception> errors = new LinkedHashMap<String, Exception>();

            for (Map.Entry<String, JedisPool> entry : cluster.getClusterNodes().entrySet()) {

                Jedis jedis = null;

                try {
                    jedis = entry.getValue().getResource();

                    time = System.currentTimeMillis();
                    Set<RedisClusterNode> nodes = Converters.toSetOfRedisClusterNodes(jedis.clusterNodes());

                    synchronized (lock) {
                        cached = new ClusterTopology(nodes);
                    }
                    return cached;
                } catch (Exception ex) {
                    errors.put(entry.getKey(), ex);
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Exception> entry : errors.entrySet()) {
                sb.append(String.format("\r\n\t- %s failed: %s", entry.getKey(), entry.getValue().getMessage()));
            }
            throw new ClusterStateFailureException(
                    "Could not retrieve cluster information. CLUSTER NODES returned with error." + sb.toString());
        }
    }

    /**
     * Jedis specific implementation of {@link ClusterNodeResourceProvider}.
     *
     * @author Christoph Strobl
     * @author Mark Paluch
     * @since 1.7
     */
    static class JedisClusterNodeResourceProvider implements ClusterNodeResourceProvider {

        private final JedisCluster cluster;
        private final ClusterTopologyProvider topologyProvider;
        private final JedisClusterConnectionHandler connectionHandler;

        /**
         * Creates new {@link JedisClusterConnection.JedisClusterNodeResourceProvider}.
         *
         * @param cluster          must not be {@literal null}.
         * @param topologyProvider must not be {@literal null}.
         */
        JedisClusterNodeResourceProvider(JedisCluster cluster, ClusterTopologyProvider topologyProvider) {

            this.cluster = cluster;
            this.topologyProvider = topologyProvider;

            if (cluster != null) {

                PropertyAccessor accessor = new DirectFieldAccessFallbackBeanWrapper(cluster);
                this.connectionHandler = accessor.isReadableProperty("connectionHandler")
                        ? (JedisClusterConnectionHandler) accessor.getPropertyValue("connectionHandler") : null;
            }
            else {
                this.connectionHandler = null;
            }
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.connection.ClusterNodeResourceProvider#getResourceForSpecificNode(org.springframework.data.redis.connection.RedisClusterNode)
         */
        @Override
        @SuppressWarnings("unchecked")
        public Jedis getResourceForSpecificNode(RedisClusterNode node) {

            Assert.notNull(node, "Cannot get Pool for 'null' node!");

            JedisPool pool = getResourcePoolForSpecificNode(node);
            if (pool != null) {
                return pool.getResource();
            }

            Jedis connection = getConnectionForSpecificNode(node);

            if (connection != null) {
                return connection;
            }

            throw new IllegalStateException(String.format("Node %s is unknown to cluster", node));
        }

        private JedisPool getResourcePoolForSpecificNode(RedisClusterNode node) {

            Map<String, JedisPool> clusterNodes = cluster.getClusterNodes();
            if (clusterNodes.containsKey(node.asString())) {
                return clusterNodes.get(node.asString());
            }

            return null;
        }

        private Jedis getConnectionForSpecificNode(RedisClusterNode node) {

            RedisClusterNode member = topologyProvider.getTopology().lookup(node);

            if (member != null && connectionHandler != null) {
                return connectionHandler.getConnectionFromNode(new HostAndPort(member.getHost(), member.getPort()));
            }

            return null;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.connection.ClusterNodeResourceProvider#returnResourceForSpecificNode(org.springframework.data.redis.connection.RedisClusterNode, java.lang.Object)
         */
        @Override
        public void returnResourceForSpecificNode(RedisClusterNode node, Object client) {
            ((Jedis) client).close();
        }
    }
}