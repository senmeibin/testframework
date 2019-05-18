package com.bpms.sys.service;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.sys.dao.CounterDao;
import com.bpms.sys.entity.ext.CounterExt;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计数器管理服务类
 */
@Service
public class CounterService extends SysBaseService<CounterExt> {
    @Autowired
    private CounterDao counterDao;

    @Override
    public CounterDao getDao() {
        return counterDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CounterExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CounterExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CounterExt saveBefore(CounterExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CounterExt saveAfter(CounterExt entity) {
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
        return true;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(CounterExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CounterExt entity) {
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
        return true;
    }

    /**
     * 根据条件获取最新的计数器
     *
     * @param typeCd        计数器类型
     * @param groupCounter1 分组条件1
     * @param groupCounter2 分组条件2
     * @param groupCounter3 分组条件3
     * @param groupCounter4 分组条件4
     * @param groupCounter5 分组条件5
     * @return 最新计数器
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized Integer getCounter(String typeCd, String groupCounter1, String groupCounter2, String groupCounter3, String groupCounter4, String groupCounter5) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("typeCd", typeCd);
        condition.put("groupCounter1", groupCounter1);
        condition.put("groupCounter2", groupCounter2);
        condition.put("groupCounter3", groupCounter3);
        condition.put("groupCounter4", groupCounter4);
        condition.put("groupCounter5", groupCounter5);

        //锁表操作
        condition.put("forUpdate$ignore_search", true);

        return this.getCounter(condition);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized Integer getCounter(String typeCd, String groupCounter1) {
        return this.getCounter(typeCd, groupCounter1, null, null, null, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized Integer getCounter(String typeCd, String groupCounter1, String groupCounter2) {
        return this.getCounter(typeCd, groupCounter1, groupCounter2, null, null, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized Integer getCounter(String typeCd, String groupCounter1, String groupCounter2, String groupCounter3) {
        return this.getCounter(typeCd, groupCounter1, groupCounter2, groupCounter3, null, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized Integer getCounter(String typeCd, String groupCounter1, String groupCounter2, String groupCounter3, String groupCounter4) {
        return this.getCounter(typeCd, groupCounter1, groupCounter2, groupCounter3, groupCounter4, null);
    }

    /**
     * 根据条件获取最新的计数器
     *
     * @param condition 条件集合
     * @return 最新计数器
     */
    private Integer getCounter(Map<String, Object> condition) {
        //根据condition条件获取当前list
        List<CounterExt> counterList = this.search(CounterExt.class, this.getSearchSQL(condition), condition);

        //初始化计数器对象
        CounterExt counter = new CounterExt();

        //如果获取不到list
        if (counterList.size() == 0) {
            //计数器类型
            counter.setTypeCd(ObjectUtils.defaultIfNull(SearchConditionUtils.getConditionValue(condition, "typeCd"), "").toString());

            //分组条件1
            counter.setGroupCounter1(ObjectUtils.defaultIfNull(SearchConditionUtils.getConditionValue(condition, "groupCounter1"), "").toString());
            //分组条件2
            counter.setGroupCounter2(ObjectUtils.defaultIfNull(SearchConditionUtils.getConditionValue(condition, "groupCounter2"), "").toString());
            //分组条件3
            counter.setGroupCounter3(ObjectUtils.defaultIfNull(SearchConditionUtils.getConditionValue(condition, "groupCounter3"), "").toString());
            //分组条件4
            counter.setGroupCounter4(ObjectUtils.defaultIfNull(SearchConditionUtils.getConditionValue(condition, "groupCounter4"), "").toString());
            //分组条件5
            counter.setGroupCounter5(ObjectUtils.defaultIfNull(SearchConditionUtils.getConditionValue(condition, "groupCounter5"), "").toString());

            //当前计数值
            counter.setCounterNumber(1);
        }
        //如果存在计数器对象
        else {
            counter = counterList.get(0);
            //当前计数+1
            int count = counter.getCounterNumber() + 1;
            counter.setCounterNumber(count);
        }
        //默认记录状态为1
        counter.setRecordStatus(Integer.parseInt(CmnConsts.RECORD_STATUS_ACTIVE));
        //保存计数器对象
        this.save(counter);
        return counter.getCounterNumber();
    }
}