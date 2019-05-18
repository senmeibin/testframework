package com.bpms.cmn.service;

import com.google.common.collect.Maps;
import com.bpms.cmn.dao.SignCompanyDao;
import com.bpms.cmn.entity.ext.SignCompanyExt;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.HttpUtils;
import com.bpms.core.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签单公司服务类
 */
@Service(value = "CmnSignCompanyService")
public class SignCompanyService extends CmnBaseService<SignCompanyExt> {
    @Autowired
    private SignCompanyDao signCompanyDao;

    @Override
    public SignCompanyDao getDao() {
        return signCompanyDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(SignCompanyExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(SignCompanyExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SignCompanyExt saveBefore(SignCompanyExt entity) {
        //true:数据重复/false:数据不重复
        boolean isDuplication = this.getDao().isDuplication("uid", entity.getUid(), "", "", "company_name", entity.getCompanyName(), true);
        if (isDuplication) {
            throw new ServiceValidationException("公司名称已经存在，请重新输入。");
        }
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SignCompanyExt saveAfter(SignCompanyExt entity) {
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
    protected Boolean deleteBefore(SignCompanyExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(SignCompanyExt entity) {
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
     * 根据签单公司UID导入签单公司信息
     *
     * @param signCompanyUid 签单公司UID
     */
    public void importSignCompanyInfo(String signCompanyUid) {
        //验证签单公司信息是否存在
        SignCompanyExt signCompany = this.getDetail(signCompanyUid);
        //如果存在，直接返回
        if (signCompany != null) {
            return;
        }

        //调用接口参数
        Map<String, Object> params = new HashMap<>();
        params.put("uid", signCompanyUid);

        //API获取签单公司信息
        String result = HttpUtils.doPost(this.parameterService.getValue(AppCodeConsts.APP_COMMON, CmnConsts.DATA_IMPORT_CMN_API) + "getTableRow?tableName=cmn_sign_company", params);

        //转换API获取结果
        AjaxResult ajaxRequest = JsonUtils.parseJSON(result, AjaxResult.class);

        //调用接口成功场合
        if (ajaxRequest.isSuccess()) {
            //实体转换
            signCompany = JsonUtils.parseJSON(ajaxRequest.getContent().toString(), SignCompanyExt.class);
            //转换成功场合
            if (signCompany != null) {
                //添加签单公司信息
                this.save(signCompany);
            }
        }
    }

    /**
     * 取所有签单公司
     *
     * @return 签单公司列表
     */
    public List<SignCompanyExt> getAllCompanyList() {
        List<SignCompanyExt> companyList;
        String cacheKey = "CACHE_CMN_SIGN_COMPANY";
        companyList = redisCacheManager.getList(SignCompanyExt.class, cacheKey);
        if (companyList == null) {
            companyList = search(Maps.newHashMap());
            redisCacheManager.set(cacheKey, companyList);
        }
        return companyList;
    }
}