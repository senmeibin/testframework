package com.bpms.demo.service;

import com.bpms.core.exception.ServiceValidationException;
import com.bpms.demo.dao.EmployeeDao;
import com.bpms.demo.entity.ext.EmployeeExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 人员信息表服务类
 */
@Service
public class EmployeeService extends DemoBaseService<EmployeeExt> {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public EmployeeDao getDao() {
        return employeeDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(EmployeeExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(EmployeeExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected EmployeeExt saveBefore(EmployeeExt entity) {
        //用户名重复性校验
        if (this.isDuplication("uid", entity.getUid(), "userCd", entity.getUserCd(), false)) {
            throw new ServiceValidationException(String.format("指定的用户名(%s)已存在，请重新输入。", entity.getUserCd()));
        }
        //工号重复性校验
        if (this.isDuplication("uid", entity.getUid(), "userNumber", entity.getUserNumber(), false)) {
            throw new ServiceValidationException(String.format("指定的工号(%s)已存在，请重新输入。", entity.getUserNumber()));
        }
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected EmployeeExt saveAfter(EmployeeExt entity) {
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
    protected Boolean deleteBefore(EmployeeExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(EmployeeExt entity) {
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
}