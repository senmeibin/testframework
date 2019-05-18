package com.bpms.crm.service;

import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.StringUtils;
import com.bpms.crm.dao.RegistrationDao;
import com.bpms.crm.dao.StudentDao;
import com.bpms.crm.entity.ext.RegistrationExt;
import com.bpms.crm.entity.ext.StudentExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报名记录服务类
 */
@Service
public class RegistrationService extends CrmBaseService<RegistrationExt> {
    @Autowired
    private RegistrationDao registrationDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CampusConsultantService campusConsultantService;

    @Override
    public RegistrationDao getDao() {
        return registrationDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(RegistrationExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(RegistrationExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected RegistrationExt saveBefore(RegistrationExt entity) {
        String campusUid = this.campusConsultantService.getBelongCampusUid(entity.getRegistrationBelongConsultantUserUid());
        if (entity.isAddMode()) {
            if (StringUtils.isNotEmpty(campusUid)) {
                entity.setRegistrationBelongCampusUid(campusUid);
            }
            else {
                throw new ServiceException(String.format("您所选择的课程顾问[%s]没有所属校区，请联系系统管理员。", entity.getRegistrationBelongConsultantUserName()));
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
    protected RegistrationExt saveAfter(RegistrationExt entity) {
        if (entity.isAddMode()) {
            StudentExt student = this.studentDao.findOne(entity.getStudentUid());
            student.setRegistrationCount(student.getRegistrationCount() + 1);
            this.studentDao.save(student);
        }
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
        StudentExt student = this.studentDao.findOne(this.findOne(ids).getStudentUid());
        student.setRegistrationCount(student.getRegistrationCount() - 1);
        this.studentDao.save(student);
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
    protected Boolean deleteBefore(RegistrationExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(RegistrationExt entity) {
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