package com.bpms.core.sql;

import com.bpms.core.cache.RedisCacheManager;
import com.bpms.core.exception.ServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import freemarker.cache.SoftCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SQL模板工具类
 */
@Component
public class SqlReader {
    /**
     * DSQL Redis缓存前缀
     */
    public static final String CACHE_PREFIX_KEY = "DSQL_CACHE";

    /**
     * DSQL文件路径字典表
     */
    private static final Map<String, String> DSQL_FILE_MAP = new HashMap<>();

    /**
     * redis Cache管理类
     */
    @Autowired
    private RedisCacheManager redisCacheManager;

    private Configuration config;

    /**
     * DSQL使用Redis缓存(true：默认使用/false：不使用）
     */
    @Value("${dsql.redis.cache.enable:true}")
    private boolean dsqlRedisCacheEnable;

    /**
     * JDBC驱动程序
     */
    @Value("${jdbc.driver:mysql}")
    private String jdbcUrl;

    /**
     * 系统编号（非必须）
     */
    @Value("${system.code:}")
    private String systemCode;

    @PostConstruct
    public void init() throws IOException {
        config = new Configuration(Configuration.VERSION_2_3_23);
        config.setCacheStorage(new SoftCacheStorage());
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        File dsqlRootPath = ResourceUtils.getFile(this.getDSQLPath());

        //初期化加载DSQL所有文件到字典表中
        this.initDsqlFileList(dsqlRootPath, dsqlRootPath.getPath());

        //设置模板文件根目录
        config.setDirectoryForTemplateLoading(dsqlRootPath);
    }

    /**
     * 取得DSQL文件路径
     *
     * @return DSQL文件路径
     */
    private String getDSQLPath() {
        String dbType;

        // 根据jdbc url判断数据库类型
        if (StringUtils.contains(this.jdbcUrl, "mysql")) {
            dbType = "mysql";
        }
        else if (StringUtils.contains(this.jdbcUrl, "oracle")) {
            dbType = "oracle";
        }
        else if (StringUtils.contains(this.jdbcUrl, "sqlserver")) {
            dbType = "sqlserver";
        }
        else {
            throw new IllegalArgumentException("Unknown Database of " + this.jdbcUrl);
        }

        return String.format("classpath:%s/com/bpms/", dbType);
    }

    /**
     * 初期化加载DSQL所有文件到字典表中
     *
     * @param file     DSQL文件目录
     * @param rootPath DSQL文件根目录
     */
    private void initDsqlFileList(File file, String rootPath) {
        //取得目录下所有xml文件
        Collection<File> files = FileUtils.listFiles(file, new String[]{"xml"}, true);
        for (File temp : files) {
            //滤出所有dsql文件
            if (StringUtils.endsWithIgnoreCase(temp.getName(), "dsql.xml")) {
                String filePath = temp.getPath();

                //dsql根目录过滤
                String key = StringUtils.removeStartIgnoreCase(filePath, rootPath);

                //dsql.xml后缀过滤
                key = StringUtils.removeEndIgnoreCase(key, "dsql.xml");

                //CentOS路径分割符过滤
                key = key.replace("/", "_");

                //Windows路径分割符过滤
                key = key.replace("\\", "_");

                //过滤第一个下划线
                key = StringUtils.removeStart(key, "_");

                DSQL_FILE_MAP.put(key.toLowerCase(), StringUtils.removeStartIgnoreCase(filePath, rootPath));
            }
        }
    }

    /**
     * 取得DSQL缓存KEY
     *
     * @param sqlAbsName SQL节点路径(模块/功能/节点，如：sys/position/search)
     * @return 缓存KEY
     */
    private String getCacheKey(String sqlAbsName) {
        if (StringUtils.isEmpty(this.systemCode)) {
            return String.format("%s_%s", CACHE_PREFIX_KEY, sqlAbsName.toUpperCase().replaceAll("/", "_"));
        }
        else {
            return String.format("%s_%s_%s", CACHE_PREFIX_KEY, this.systemCode, sqlAbsName.toUpperCase().replaceAll("/", "_"));
        }
    }

    /**
     * 读取文件
     *
     * @param sqlAbsName SQL节点路径(模块/功能/节点，如：sys/position/search)
     * @return SQL节点内容
     */
    public String read(String sqlAbsName) {
        //SQL节点值
        String nodeValue = "";

        //DSQL缓存KEY
        String cacheKey = this.getCacheKey(sqlAbsName);

        //DSQL使用Redis缓存的场合
        if (dsqlRedisCacheEnable) {
            nodeValue = redisCacheManager.get(String.class, cacheKey);
        }
        if (StringUtils.isEmpty(nodeValue)) {
            // 路径合法性校验
            if (StringUtils.isNotEmpty(sqlAbsName) && StringUtils.contains(sqlAbsName, "/")) {
                // 取得文件路径(模块/功能，如：sys/position)
                String path = StringUtils.substring(sqlAbsName, 0, StringUtils.lastOrdinalIndexOf(sqlAbsName, "/", 1));

                // 以SQL节点路径为Key取得DSQL实际文件全路径
                path = DSQL_FILE_MAP.get(StringUtils.removeStart(path.toLowerCase().replace("/", "_"), "_"));

                // DSQL文件不存在的场合，抛出异常
                if (StringUtils.isEmpty(path)) {
                    throw new ServiceException(String.format("DSQL模板文件不存在（DSQL路径：{%s}）。", sqlAbsName));
                }

                JsonNode jsonNode;
                try {
                    Template template = config.getTemplate(path, "UTF-8");

                    XmlMapper xmlMapper = new XmlMapper();
                    jsonNode = xmlMapper.readTree(template.toString());
                } catch (IOException ex) {
                    throw new ServiceException(String.format("DSQL模板文件解析错误（DSQL路径：{%s})。\n{%s}", sqlAbsName, ex.getMessage()));
                }

                try {
                    // 取得SQL节点名称(如：search)
                    String node = StringUtils.substring(sqlAbsName, StringUtils.lastOrdinalIndexOf(sqlAbsName, "/", 1) + 1);

                    nodeValue = jsonNode.get(node).asText();

                    //DSQL使用Redis缓存的场合
                    if (dsqlRedisCacheEnable) {
                        redisCacheManager.set(cacheKey, nodeValue);
                    }
                } catch (Exception ex) {
                    throw new ServiceException(String.format("DSQL节点不存在(DSQL路径：{%s})。\n{%s}", sqlAbsName, ex.getMessage()));
                }
            }
            else {
                throw new ServiceException(String.format("DSQL模板路径参数不符合规范要求(DSQL路径：{%s})。", sqlAbsName));
            }
        }
        return nodeValue;
    }
}
