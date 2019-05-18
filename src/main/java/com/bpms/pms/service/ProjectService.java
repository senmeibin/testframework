package com.bpms.pms.service;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.DropdownEntity;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bpms.core.service.BaseService;
import com.bpms.pms.dao.ProjectDao;
import com.bpms.pms.entity.ext.ProjectExt;
import com.bpms.pms.service.PmsBaseService;

import java.util.List;
import java.util.Map;

/**
 * 项目服务类
 */
@Service
public class ProjectService extends PmsBaseService<ProjectExt> {
    @Autowired
    private ProjectDao projectDao;

    @Override
    public ProjectDao getDao() {
        return projectDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ProjectExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ProjectExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected ProjectExt saveBefore(ProjectExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected ProjectExt saveAfter(ProjectExt entity) {
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
    protected Boolean deleteBefore(ProjectExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     * 
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ProjectExt entity) {
        return true;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     * 
     * @param uid 主键UID
     * @param recordStatus 记录状态
     * 
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     * 
     * @param uid 主键UID
     * @param recordStatus 记录状态
     * 
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        return true;
    }

    /**
     * 取得项目一览
     * 注：项目下拉选择专用方法
     *
     * @return 项目一览
     */
    @Transactional(readOnly = true)
    public List<DropdownEntity> getDropdownProject() {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("recordStatus", CmnConsts.RECORD_STATUS_ACTIVE);
        return getDropdownList("pms_project", "uid", "title", condition,"uid desc ");
    }
}