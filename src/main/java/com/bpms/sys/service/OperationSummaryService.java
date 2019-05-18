package com.bpms.sys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.utils.*;
import com.bpms.sys.dao.OperationSummaryDao;
import com.bpms.sys.entity.ext.OperationSummaryExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * 操作日志汇总服务类
 */
@Service
public class OperationSummaryService extends SysBaseService<OperationSummaryExt> {
    @Autowired
    private OperationSummaryDao operationSummaryDao;

    @Override
    public OperationSummaryDao getDao() {
        return operationSummaryDao;
    }

    /**
     * sql拼接
     *
     * @param sql       SQL文
     * @param condition 检索条件
     * @return
     */
    @Override
    protected String prepareSQL(String sql, Map<String, Object> condition) {
        String exceptOperationLogPath = parameterService.getValue(AppCodeConsts.APP_COMMON, "EXCEPT_OPERATION_LOG_PATH");
        if (StringUtils.isEmpty(exceptOperationLogPath)) {
            return super.prepareSQL(sql, condition);
        }

        List<String> pathList = Lists.newArrayList(StringUtils.split(exceptOperationLogPath, "|"));

        //是否包含忽略URL
        boolean includeIgnoreUrl = Objects.equals(SearchConditionUtils.getConditionValue(condition, "includeIgnoreUrl"), "1");
        if (!includeIgnoreUrl) {
            condition.put("url$not_in", StringUtils.join(pathList, ","));
        }
        return super.prepareSQL(sql, condition);
    }

    /**
     * 重写检索方法
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return
     */
    @Override
    public Page<OperationSummaryExt> search(Class<OperationSummaryExt> cls, String sql, Map<String, Object> condition, PageRequest pageRequest) {
        Page<OperationSummaryExt> page = super.search(cls, sql, condition, pageRequest);
        //忽略路径
        List<String> pathList = getIgnorePathList();
        for (OperationSummaryExt operationSummary : page) {
            if (pathList.contains(operationSummary.getUrl())) {
                operationSummary.setIsIgnore(1);
            }
        }
        return page;
    }

    /**
     * 取得忽略路径
     *
     * @return 忽略路径集合
     */
    private List<String> getIgnorePathList() {
        String exceptOperationLogPath = parameterService.getValue(AppCodeConsts.APP_COMMON, "EXCEPT_OPERATION_LOG_PATH", "/");
        return Lists.newArrayList(StringUtils.split(exceptOperationLogPath, "|"));
    }

    /**
     * 添加忽略路径
     *
     * @param url URL路径
     */
    public void addIgnoreUrl(String url) {
        List<String> pathList = getIgnorePathList();
        pathList.add(url);
        parameterService.setValue(AppCodeConsts.APP_COMMON, "EXCEPT_OPERATION_LOG_PATH", StringUtils.join(pathList, "|"), "忽略路径", 0);
    }

    /**
     * 删除忽略路径
     *
     * @param url URL路径
     */
    public void removeIgnoreUrl(String url) {
        List<String> pathList = getIgnorePathList();
        pathList.remove(url);
        parameterService.setValue(AppCodeConsts.APP_COMMON, "EXCEPT_OPERATION_LOG_PATH", StringUtils.join(pathList, "|"), "忽略路径", 0);
    }


    /**
     * 根据URL路径查找实体对象
     *
     * @param url URL路径
     * @return 实体
     */
    public OperationSummaryExt findByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }

        Map<String, Object> condition = Maps.newHashMap();
        condition.put("url", url);
        List<OperationSummaryExt> list = this.getDao().search(this.getSearchSQL(condition), condition);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 更新指定URL的访问次数累加一
     *
     * @param url URL路径
     */
    private void executeAddCount(String url) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("url", url);
        condition.put("updateUser", getCurrentUserId());
        condition.put("updateDate", DateUtils.getNowDateString(DateUtils.DATE_TIME_FORMAT));
        getDao().executeUpdate(getSQL("sys/OperationSummary/executeAddCount"), condition);
    }

    /**
     * 获取跟踪路径
     *
     * @param request http请求
     * @return 路径
     */
    private String getTraceUrl(HttpServletRequest request) {
        String url;
        //不记录非法请求 ajax请求
        if (request.getRequestURI() == null || request.getContextPath() == null || HttpUtils.isAjaxRequest(request)) {
            return null;
        }
        else {
            url = StringUtils.replace(request.getRequestURI(), request.getContextPath(), StringUtils.EMPTY);

            //路径中包含特殊符号的场合，视为无效路径
            if (url.indexOf("{") > 0 || url.indexOf("}") > 0 || url.indexOf("$") > 0 || url.indexOf("%") > 0) {
                return null;
            }

            //URL合并处理
            url = this.mergeUrl(url);

            if (StringUtils.isEmpty(url) || StringUtils.equals(url, "/")) {
                return null;
            }
            //除外URL的场合
            else if (this.isExceptUrl(url)) {
                return null;
            }
        }
        //URL最长支持128位长度
        if (StringUtils.length(url) > 128) {
            url = url.substring(0, 128);
        }
        return url;
    }

    /**
     * 页面浏览量跟踪统计
     *
     * @param request HttpServletRequest
     */
    @Async
    public Future<Integer> tracePageViewCount(HttpServletRequest request) {
        try {
            String url = this.getTraceUrl(request);

            //路径不能为空
            if (StringUtils.isNotEmpty(url)) {
                OperationSummaryExt entity = this.findByUrl(url);
                if (entity == null) {
                    //不存在的路径新增
                    entity = new OperationSummaryExt();
                    entity.setUrl(url);
                    entity.setCount(1);
                    save(entity);
                    return AsyncResult.forValue(entity.getCount());
                }
                else {
                    //存在路径的访问量 + 1
                    this.executeAddCount(url);
                    return AsyncResult.forValue(entity.getCount() + 1);
                }
            }
        }
        catch (Exception ex) {
            log.error("页面浏览量跟踪统计异常：" + ex.getMessage(), ex);
        }
        return AsyncResult.forValue(0);
    }

    /**
     * 是否是除外URL？
     *
     * @param url URL路径
     * @return true：除外URL
     */
    private boolean isExceptUrl(String url) {
        //操作日志写入除外路径
        String exceptOperationLogPath = parameterService.getValue(AppCodeConsts.APP_COMMON, "EXCEPT_OPERATION_LOG_PATH", "/");
        //除外路径存在的场合
        String[] paths = StringUtils.split(exceptOperationLogPath, "|");
        for (String path : paths) {
            //当前访问路径 == 除外路径的 场合
            if (StringUtils.equals(path, url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * URL合并处理
     *
     * @param url 待处理的url
     * @return 处理过的url
     */
    private String mergeUrl(String url) {
        //portal/dashboard;jsessionid=C1EEB5FAB400507CB00C1DCFDB80B8D2特例化处理
        if (StringUtils.contains(url, ";")) {
            url = url.split(";")[0];
        }

        //去掉末尾斜杠
        if (StringUtils.endsWith(url, "/")) {
            url = StringUtils.substring(url, 0, url.length() - 1);
        }

        //list入口为默认入口 记录默认入口
        if (StringUtils.endsWith(url, "/list")) {
            url = StringUtils.substring(url, 0, url.length() - 5);
        }

        //url分组
        String[] urlList = StringUtils.split(url, "/");
        if (urlList.length > 3) {
            url = "/" + urlList[0] + "/" + urlList[1] + "/" + urlList[2];
        }

        return url;
    }

    /**
     * 取得系统所有画面平均访问次数
     *
     * @return 平均访问次数
     */
    public int getAverageCount() {
        String cacheKey = "CACHE_SYS_OPERATION_SUMMARY_AVERAGE_COUNT";
        Integer averageCount = redisCacheManager.get(Integer.class, cacheKey);
        if (averageCount == null) {
            List<String> pathList = getIgnorePathList();
            Map<String, Object> condition = Maps.newHashMap();
            condition.put("url$not_in", StringUtils.join(pathList, ","));
            List<OperationSummaryExt> result = search(OperationSummaryExt.class, getSQL("sys/OperationSummary/getAverageCount"), condition);
            averageCount = result.get(0).getAverageCount();
            //每30分钟更新一次缓存
            int minutes = parameterService.getIntValue(AppCodeConsts.APP_COMMON, "CACHE_SYS_OPERATION_SUMMARY_AVERAGE_COUNT_EXPIRE", 30);
            redisCacheManager.set(cacheKey, averageCount, minutes);
        }
        return averageCount;
    }
}