package com.bpms.demo.service;

import com.bpms.core.mail.SendMailService;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.UserService;
import com.bpms.demo.dao.ServiceTrackingDao;
import com.bpms.demo.entity.ext.ServiceTrackingExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业服务跟踪服务类
 */
@Service
public class ServiceTrackingService extends DemoBaseService<ServiceTrackingExt> {
    @Autowired
    private ServiceTrackingDao serviceTrackingDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SendMailService sendMailService;

    @Override
    public ServiceTrackingDao getDao() {
        return serviceTrackingDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ServiceTrackingExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ServiceTrackingExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ServiceTrackingExt saveBefore(ServiceTrackingExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ServiceTrackingExt saveAfter(ServiceTrackingExt entity) {
        //02:待走访 且存在下次走访人
        if (StringUtils.equals(entity.getVisitStatusCd(), "02") && StringUtils.isNotEmpty(entity.getNextTrackingUserUid())) {
            //获取人员信息(邮件)
            UserExt userExt = userService.findOne(entity.getNextTrackingUserUid());
            if (userExt != null && StringUtils.isNotEmpty(userExt.getUserMail()) && entity.getNextTrackingTime() != null && entity.getNextTrackingTime().after(DateUtils.getNowDate())) {
                sendMailService.send("TUS", "企业服务跟踪提醒", String.format("您需要在%s对%s进行下一步服务跟踪。其中当前需求为：%s", DateUtils.format(entity.getNextTrackingTime(), "yyyy年MM月dd日"), entity.getCompanyName(), entity.getBusinessRequirements()), userExt.getUserMail());
            }
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
    protected Boolean deleteBefore(ServiceTrackingExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ServiceTrackingExt entity) {
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