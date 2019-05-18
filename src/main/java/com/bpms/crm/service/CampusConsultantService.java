package com.bpms.crm.service;

import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.crm.dao.CampusConsultantDao;
import com.bpms.crm.entity.CampusConsultant;
import com.bpms.crm.entity.ext.CampusConsultantExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 校区顾问服务类
 */
@Service
public class CampusConsultantService extends CrmBaseService<CampusConsultantExt> {
    @Autowired
    private CampusConsultantDao campusConsultantDao;

    @Override
    public CampusConsultantDao getDao() {
        return campusConsultantDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CampusConsultantExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CampusConsultantExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CampusConsultantExt saveBefore(CampusConsultantExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CampusConsultantExt saveAfter(CampusConsultantExt entity) {
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
    protected Boolean deleteBefore(CampusConsultantExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CampusConsultantExt entity) {
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
     * 取得校区顾问一览
     *
     * @param campusUid 校区UID
     * @return 校区顾问一览
     */
    public List<CampusConsultantExt> getCampusConsultantList(String campusUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("campus_uid", campusUid);
        return this.search(condition);
    }

    /**
     * 取得校区顾问数组
     *
     * @param campusUid 校区UID
     * @return 校区顾问数组
     */
    public String[] getCampusConsultantArray(String campusUid) {
        List<CampusConsultantExt> list = this.getCampusConsultantList(campusUid);
        String consultantUserUids = StringUtils.EMPTY;
        String consultantUserNames = StringUtils.EMPTY;
        for (CampusConsultantExt e : list) {
            consultantUserUids += e.getConsultantUserUid() + ",";
            consultantUserNames += e.getConsultantUserName() + ",";
        }

        return new String[]{StringUtils.removeEnd(consultantUserUids, ","), StringUtils.removeEnd(consultantUserNames, ",")};
    }

    /**
     * 根据课程顾问UID取得所属校区UID
     *
     * @param consultantUserUid 课程顾问UID
     * @return 所属校区UID
     */
    public String getBelongCampusUid(String consultantUserUid) {
        List<CampusConsultant> list = this.getResultListByFieldValue(CampusConsultant.class, "crm_campus_consultant", "consultant_user_uid", consultantUserUid);

        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0).getCampusUid();
        }
        return StringUtils.EMPTY;
    }
}