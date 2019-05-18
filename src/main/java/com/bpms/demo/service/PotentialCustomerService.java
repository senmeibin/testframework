package com.bpms.demo.service;

import com.bpms.core.entity.AjaxResult;
import com.bpms.demo.dao.PotentialCustomerDao;
import com.bpms.demo.entity.ext.CompanyInformationExt;
import com.bpms.demo.entity.ext.PotentialCustomerExt;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 招商潜在投资企业服务类
 */
@Service
public class PotentialCustomerService extends DemoBaseService<PotentialCustomerExt> {
    /**
     * 企业基本信息服务类
     */
    @Autowired
    private CompanyInformationService companyInformationService;

    @Autowired
    private PotentialCustomerDao potentialCustomerDao;

    @Override
    public PotentialCustomerDao getDao() {
        return potentialCustomerDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(PotentialCustomerExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(PotentialCustomerExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected PotentialCustomerExt saveBefore(PotentialCustomerExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected PotentialCustomerExt saveAfter(PotentialCustomerExt entity) {
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
    protected Boolean deleteBefore(PotentialCustomerExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(PotentialCustomerExt entity) {
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
     * 转入孵化企业信息中
     *
     * @param uid 工资发放UID
     * @return
     */
    @Transactional
    @RequestMapping(value = "turnCompany", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult turnCompany(@RequestParam String uid) {
        //根据UID获取数据
        PotentialCustomerExt entity = this.getDao().findOne(uid);

        CompanyInformationExt companyInformationExt = new CompanyInformationExt();

        //复制相关信息
        try {
            BeanUtils.copyProperties(companyInformationExt, entity);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.createFailResult("复制数据失败，请与系统管理员联系检查日志。");
        }
        //置空UID
        companyInformationExt.setUid(null);
        //入孵状态
        companyInformationExt.setIncubationStateCd("01");

        //保存新的数据
        this.companyInformationService.save(companyInformationExt);
        //删除当前数据
        this.delete(uid);

        return AjaxResult.createSuccessResult("转入成功", companyInformationExt.getUid());
    }
}