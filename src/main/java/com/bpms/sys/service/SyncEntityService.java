package com.bpms.sys.service;

import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.sys.dao.SyncEntityDao;
import com.bpms.sys.entity.ext.SyncEntityExt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步实体类服务类
 */
@Service
public class SyncEntityService extends SysBaseService<SyncEntityExt> {

    /**
     * 缓存KEY前缀
     */
    public static final String CACHE_PREFIX_KEY = "SYNC_ENTITY_CACHE";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SyncEntityDao syncEntityDao;

    @Override
    public SyncEntityDao getDao() {
        return syncEntityDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(SyncEntityExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(SyncEntityExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SyncEntityExt saveBefore(SyncEntityExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SyncEntityExt saveAfter(SyncEntityExt entity) {
        //删除缓存数据
        this.deleteCache(entity);

        return entity;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(String ids) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(String ids) {
        String[] uids = ids.split(",");

        // 循环删除缓存
        for (String uid : uids) {
            this.deleteCache(this.findOne(uid));
        }
        return true;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(SyncEntityExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(SyncEntityExt entity) {
        //删除缓存数据
        this.deleteCache(entity);

        return true;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        //删除缓存数据
        this.deleteCache(this.findOne(uid));
        return true;
    }

    /**
     * 分页查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    @Override
    public Page<SyncEntityExt> search(Class<SyncEntityExt> cls, String sql, Map<String, Object> condition, PageRequest pageRequest) {
        Page<SyncEntityExt> list = super.search(cls, sql, condition, pageRequest);
        //如果查询条件为空且列表为空，则新增一条 同步对象实体类记录
        if (list.getTotalElements() == 0 && StringUtils.isEmpty((String) SearchConditionUtils.getConditionValue(condition, "main.entityClassName$partial_search"))) {
            SyncEntityExt entity = new SyncEntityExt();
            entity.setEntityClassName("同步对象实体类");
            entity.setEntityClassPath(SyncEntityExt.class.getName());
            this.getDao().save(entity);

            return super.search(cls, sql, condition, pageRequest);
        }
        return list;
    }

    /**
     * 是否存在实体类路径
     *
     * @param entityClassPath 实体类路径
     * @return 是否存在
     */
    public boolean isExistsEntityClassPath(String entityClassPath) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, entityClassPath);
        String value = redisCacheManager.get(String.class, cacheKey);

        if (StringUtils.isEmpty(value)) {
            //根据应用编号及参数名称取得参数实体对象
            SyncEntityExt syncEntity = this.findByEntityClassPath(entityClassPath);

            if (syncEntity == null) {
                return false;
            }
            //放入缓存
            redisCacheManager.set(cacheKey, "true");

            return true;
        }

        return true;
    }

    /**
     * 根据实体类路径查找数据
     *
     * @param entityClassPath 实体类路径
     * @return 同步实体类数据
     */
    private SyncEntityExt findByEntityClassPath(String entityClassPath) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.entityClassPath", entityClassPath);
        List<SyncEntityExt> list = this.search(condition);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    /**
     * 删除缓存数据
     *
     * @param entity 参数实体对象
     */
    private void deleteCache(SyncEntityExt entity) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, entity.getEntityClassPath());

        //删除redis数据
        redisCacheManager.delete(cacheKey);
    }

    /**
     * 或许报名下拉框数据
     *
     * @return
     */
    public List<DropdownEntity> getPackagesDropdownEntityList() {
        List<DropdownEntity> list = new ArrayList<>();

        List<String> packageList = new ArrayList<>();
        //获取系统下的实体类
        packageList.addAll(this.getClassName("com.bpms.sys.entity.ext", false));
        //获取共通下的实体类
        packageList.addAll(this.getClassName("com.bpms.cmn.entity.ext", false));

        for (String className : packageList) {
            if (className.endsWith("Ext")) {
                DropdownEntity dropdownEntity = new DropdownEntity();
                dropdownEntity.setSubCd(className);
                dropdownEntity.setSubName(className);
                list.add(dropdownEntity);
            }
        }

        return list;
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName  包名
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private List<String> getClassName(String packageName, boolean childPackage) {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            if (type.equals("file")) {
                fileNames = getClassNameByFile(url.getPath(), null, childPackage);
            }
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath     文件路径
     * @param className    类名集合
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
                }
            }
            else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }
}