package com.bpms.sys.service;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.utils.ScanPackageUtils;
import com.bpms.sys.dao.UrlRoleDao;
import com.bpms.sys.entity.UrlRoleTree;
import com.bpms.sys.entity.ext.UrlRoleExt;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签服务类
 */
@Service
public class UrlRoleService extends SysBaseService<UrlRoleExt> {

    /**
     * 缓存KEY前缀
     */
    private static final String CACHE_PREFIX_KEY = "CACHE_URLROLE";

    /**
     * 是否校验URI访问权限
     */
    @Value("${check.urlRole:false}")
    private boolean checkUrlRole;

    @Autowired
    private UrlRoleDao urlRoleDao;

    @Autowired
    private ParameterService parameterService;

    @Override
    public UrlRoleDao getDao() {
        return urlRoleDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(UrlRoleExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(UrlRoleExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected UrlRoleExt saveBefore(UrlRoleExt entity) {
        List<UrlRoleTree> allUrlRoleTree = this.getAllUrlList();

        String url = entity.getUrl();
        //父访问路径并且覆盖子访问路径场合
        if (url.contains("*") && entity.getIsCover()) {
            //条件Map
            Map<String, Object> condition = new HashMap<>();

            //将父访问路径*去除
            String parentUrl = url.replace("*", "");

            //循环处理每个访问路径 找出对应的子访问路径 赋予相同的权限
            for (UrlRoleTree urlRoleTree : allUrlRoleTree) {
                String childUrl = urlRoleTree.getUrlStr();
                //子访问路径场合 (加'/'为了兼容list的空串配置情况）
                if ((childUrl + "/").contains(parentUrl) && !StringUtils.equals(url, childUrl)) {
                    //查找子访问路径是否有配置
                    condition.put("url$=", childUrl);
                    List<UrlRoleExt> urlRoles = this.search(UrlRoleExt.class, this.getSearchSQL(condition), condition);
                    //子访问路径有权限配置场合
                    if (urlRoles.size() > 0) {
                        UrlRoleExt urlRole = urlRoles.get(0);
                        //父访问路径的角色权限配置
                        String[] parentRoles = entity.getRoles().split(",");
                        String childRole = urlRole.getRoles();
                        //循环处理父权限
                        for (String parentRole : parentRoles) {
                            //子访问路径未有该角色配置场合
                            if (!urlRole.getRoles().contains(parentRole)) {
                                childRole += parentRole + ",";
                            }
                        }
                        //删除的权限 一并删除
                        for (String parentDeleteRole : entity.getDeleteRoleCodes()) {
                            childRole = childRole.replace(parentDeleteRole + ",", "");
                        }
                        //保存权限
                        urlRole.setRoles(childRole);
                        this.getDao().save(urlRole);
                    }
                    //子访问未配置权限场合
                    else {
                        UrlRoleExt urlRole = new UrlRoleExt();
                        urlRole.setRoles(entity.getRoles());
                        urlRole.setUrl(childUrl);
                        urlRole.setDescription(urlRoleTree.getName());
                        this.getDao().save(urlRole);
                    }
                }
            }
        }
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected UrlRoleExt saveAfter(UrlRoleExt entity) {
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
    protected Boolean deleteBefore(UrlRoleExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(UrlRoleExt entity) {
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
     * 根据访问路径获取对应的访问权限
     *
     * @param url 访问路径
     * @return 访问权限
     */
    private String getUrlRoles(String url) {
        //缓存key
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, url.toUpperCase());
        String urlRoles = redisCacheManager.get(String.class, cacheKey);
        //无缓存场合
        if (StringUtils.isEmpty(urlRoles)) {
            Map<String, Object> condition = new HashMap<>();
            condition.put("url$=", url);
            //访问路径
            List<UrlRoleExt> urlRoleList = this.getDao().search(UrlRoleExt.class, this.getSearchSQL(condition), condition);
            if (urlRoleList.size() > 0) {
                //缓存角色集合
                redisCacheManager.set(cacheKey, urlRoleList.get(0).getRoles());
                return urlRoleList.get(0).getRoles();
            }
        }

        return urlRoles;
    }

    /**
     * 校验是否有访问权限(未配置默认为可访问）
     *
     * @param requestUri 访问路径
     * @return true：有权限，false;无权限
     */
    public boolean checkUrlRole(String requestUri) {
        //默认有权限
        boolean flag = true;

        //不校验访问权限场合
        if (!this.checkUrlRole) {
            return flag;
        }

        //系统管理权限优先
        if (SecurityUtils.getSubject().hasRole("SystemManagement") || SecurityUtils.getSubject().hasRole("ItSupport")) {
            return flag;
        }

        //获取访问路径对应的权限集合
        String roles = this.getUrlRoles(requestUri);
        //有配置场合
        if (StringUtils.isNotEmpty(roles)) {
            //默认改为无访问权限
            flag = false;

            //循环判断当前用户是否有权限
            for (String role : roles.split(",")) {
                //只要有其中一种权限 即认为有访问权限
                if (SecurityUtils.getSubject().hasRole(role)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 删除缓存数据
     *
     * @param entity 参数实体对象
     */
    private void deleteCache(UrlRoleExt entity) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, entity.getUrl().toUpperCase());

        //删除redis数据
        redisCacheManager.delete(cacheKey);
    }

    /**
     * 获取指定的模块路径
     *
     * @return 指定模块路径URL集合
     */
    public List<UrlRoleTree> getAllUrlList() {
        //获取参数配置的扫描包
        String scanPackage = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "SCAN_CONTROLLER_PACKAGE", "com.bpms.sys.controller");

        return scanPackages(scanPackage);
    }

    /**
     * 扫描指定Packages下所有Controller的访问路径
     *
     * @param scanPackages 需要扫描的Packages
     * @return 访问路径存储list
     */
    private List<UrlRoleTree> scanPackages(String scanPackages) {
        List<UrlRoleTree> urlRoleTreesList = new ArrayList<>();

        //是否缓存指定Package的访问URL地址
        String useCache = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "CACHE_CONTROLLER_PACKAGE_URL", "false");

        for (String scanPackage : scanPackages.split(",")) {
            String cacheKey = "CONTROLLER_URL_CACHE_" + scanPackage.toUpperCase();

            if (StringUtils.equals(useCache, "true")) {
                List<UrlRoleTree> urlRoleTreeList = (List<UrlRoleTree>) this.redisCacheManager.get(cacheKey);
                if (CollectionUtils.isNotEmpty(urlRoleTreeList)) {
                    urlRoleTreesList.addAll(urlRoleTreeList);
                    continue;
                }
            }

            try {
                List<UrlRoleTree> urlRoleTreeList = this.scanPackage(scanPackage);

                if (StringUtils.equals(useCache, "true")) {
                    this.redisCacheManager.set(cacheKey, urlRoleTreeList);
                }
                urlRoleTreesList.addAll(urlRoleTreeList);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
        return urlRoleTreesList;
    }

    /**
     * 扫描指定Package下所有Controller的访问路径
     *
     * @param scanPackage 需要扫描的Package
     * @return 访问路径存储list
     * @throws Exception
     */
    private List<UrlRoleTree> scanPackage(String scanPackage) throws Exception {
        //存储访问路径信息
        List<UrlRoleTree> urlRoleTreeList = new ArrayList<>();

        //获取扫描的类名
        List<String> classNames = ScanPackageUtils.getFullyQualifiedClassNameList(scanPackage);
        //存储模块map
        Map<String, String> moduleMap = new HashMap<>();

        //循环处理controller的类
        for (String className : classNames) {
            //获取类上的注解
            Annotation[] annotationsClass = Class.forName(className).getAnnotations();
            //循环处理类上面的注解
            for (Annotation annotation : annotationsClass) {
                //注解类型为RequestMapping场合
                if (annotation instanceof RequestMapping) {
                    //RequestMapping注解的value值
                    String controllerUrl = ((RequestMapping) annotation).value()[0];
                    //获取到模块名
                    String moduleName = controllerUrl.split("/")[1];
                    //模块map中不存在该模块名场合
                    if (!moduleMap.containsKey(moduleName)) {
                        UrlRoleTree urlRoleTree = new UrlRoleTree();
                        //设置模块URL
                        urlRoleTree.setUrlStr("/" + moduleName + "/*");
                        //设置上级模块
                        urlRoleTree.setParentModule("");
                        //设置模块名称
                        urlRoleTree.setName(moduleName);
                        //存储到访问路径集合中
                        urlRoleTreeList.add(urlRoleTree);
                        //存储模块map
                        moduleMap.put(moduleName, moduleName);
                    }
                    //RequestMapping注解的name值
                    String controllerName = ((RequestMapping) annotation).name();
                    UrlRoleTree urlRoleTree = new UrlRoleTree();
                    //name不为空场合
                    if (StringUtils.isNotEmpty(controllerName)) {
                        //设置路径描述
                        urlRoleTree.setName(controllerUrl + "(" + controllerName + ")");
                    }
                    //name为空场合
                    else {
                        urlRoleTree.setName(controllerUrl);
                    }
                    //设置上级模块名
                    urlRoleTree.setParentModule("/" + moduleName + "/*");
                    //设置模块URL
                    urlRoleTree.setUrlStr(controllerUrl + "/*");
                    //存储到访问路径集合中
                    urlRoleTreeList.add(urlRoleTree);
                    //循环处理类方法
                    for (Method method : Class.forName(className).getMethods()) {
                        Annotation[] annotationMethods = method.getAnnotations();
                        //循环处理类方法上的注解
                        for (Annotation annotationMethod : annotationMethods) {
                            //注解类型为RequestMapping场合
                            if (annotationMethod instanceof RequestMapping) {
                                //根据指定Controller方法路径组装UrlRoleTree实体对象
                                convertRequestMappingToUrlRoleTree(urlRoleTreeList, annotationMethod, controllerUrl, controllerName);
                            }
                        }
                    }
                }
            }
        }
        return urlRoleTreeList;
    }

    /**
     * 根据指定Controller方法路径组装UrlRoleTree实体对象
     *
     * @param urlRoleTreeList  UrlRoleTree列表
     * @param annotationMethod 具体方法的注解
     * @param controllerUrl    Controller全局RequestMapping的Value属性
     * @param controllerName   Controller全局RequestMapping的Name属性
     */
    private void convertRequestMappingToUrlRoleTree(List<UrlRoleTree> urlRoleTreeList, Annotation annotationMethod, String controllerUrl, String controllerName) {
        //获取方法上的URL(可配置多个）
        for (String methodUrl : ((RequestMapping) annotationMethod).value()) {
            //过滤syncData和validateDuplication
            if (StringUtils.equals("validateDuplication", methodUrl) || StringUtils.equals("syncData", methodUrl)) {
                continue;
            }
            //首字母不是'/'场合 添加'/'
            if (!methodUrl.startsWith("/")) {
                methodUrl = "/" + methodUrl;
            }

            //尾字母是'/'场合(针对list的空串配置情况） 去掉尾字母的'/'
            if (methodUrl.lastIndexOf("/") == methodUrl.length() - 1) {
                methodUrl = methodUrl.substring(0, methodUrl.length() - 1);
            }

            //获取RequestMapping注解的name属性值
            String methodName = ((RequestMapping) annotationMethod).name();
            UrlRoleTree methodUrlRole = new UrlRoleTree();

            //name不为空场合
            if (StringUtils.isNotEmpty(controllerName)) {
                //设置路径描述
                methodUrlRole.setName(controllerUrl + methodUrl + "(" + controllerName + "-" + methodName + ")");

            }
            //name为空场合
            else {
                methodUrlRole.setName(controllerUrl + methodUrl);
            }
            //设置上级模块名
            methodUrlRole.setParentModule(controllerUrl + "/*");
            //设置访问路径
            methodUrlRole.setUrlStr(controllerUrl + methodUrl);
            //存储到访问路径集合中
            urlRoleTreeList.add(methodUrlRole);
        }
    }
}