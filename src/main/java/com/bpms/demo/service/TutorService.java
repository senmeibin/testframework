package com.bpms.demo.service;

import com.bpms.demo.dao.TutorDao;
import com.bpms.demo.entity.ext.BaseInfoExt;
import com.bpms.demo.entity.ext.TutorExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导师表服务类
 */
@Service
public class TutorService extends DemoBaseService<TutorExt> {

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private TutorDao tutorDao;

    @Override
    public TutorDao getDao() {
        return tutorDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(TutorExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(TutorExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected TutorExt saveBefore(TutorExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected TutorExt saveAfter(TutorExt entity) {
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
    protected Boolean deleteBefore(TutorExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(TutorExt entity) {
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
     * 查询导师列表数据
     *
     * @param baseUid 所属基地UID
     * @return 查询导师列表JSON字符串
     */
    public List<TutorExt> getTutorTree(String baseUid) {
        List<TutorExt> result = new ArrayList<>();

        Map<String, Object> condition = new HashMap<>();
        condition.put("main.base_uid", baseUid);
        List<TutorExt> list = this.search(condition);

        //获取所有基地信息
        condition.clear();
        condition.put("uid", baseUid);
        List<BaseInfoExt> baseList = baseInfoService.search(condition);

        for (BaseInfoExt baseInfoExt : baseList) {
            TutorExt tutorExt = new TutorExt();
            tutorExt.setUid(baseInfoExt.getUid());
            tutorExt.setTutorName(baseInfoExt.getBaseName());
            result.add(tutorExt);
        }
        result.addAll(list);
        return result;
    }
}