package com.bpms.cmn.service;

import com.google.common.collect.Maps;
import com.bpms.cmn.dao.CustomerDao;
import com.bpms.cmn.entity.ext.CustomerExt;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.HttpUtils;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同客户服务类
 */
@Service(value = "CmnCustomerService")
public class CustomerService extends CmnBaseService<CustomerExt> {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerUserService customerUserService;

    @Override
    public CustomerDao getDao() {
        return customerDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CustomerExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CustomerExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CustomerExt saveBefore(CustomerExt entity) {
        //true:数据重复/false:数据不重复
        boolean isDuplication = this.getDao().isDuplication("uid", entity.getUid(), "", "", "customerName", entity.getCustomerName(), true);
        if (isDuplication) {
            throw new ServiceValidationException("客户名称已经存在，请重新输入。");
        }
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CustomerExt saveAfter(CustomerExt entity) {
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
    protected Boolean deleteBefore(CustomerExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CustomerExt entity) {
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
     * 把excel的导入写书写入db， 名称相同的就更新， 否则就新建
     *
     * @param customerExtList 需要插入DB列表
     */
    @Transactional
    public void saveCustomerData(List<CustomerExt> customerExtList) {
        for (CustomerExt entity : customerExtList) {
            //按客户名称判断该客户是否已经存在
            Map<String, Object> map = new HashMap<>();
            //客户名称 设为检索条件
            map.put("main.customerName", entity.getCustomerName());
            //根据结算单自动求和各金额
            List<CustomerExt> list = this.customerDao.search(CustomerExt.class, this.getSQL("cmn/customer/search"), map);
            if (list != null && list.size() > 0) {
                CustomerExt dbEntity = list.get(0);
                //客户简称
                dbEntity.setCustomerAbbreviation(entity.getCustomerAbbreviation());
                //客户地址
                dbEntity.setAddress(entity.getAddress());
                //联系电话
                dbEntity.setTelephone(entity.getTelephone());
                //开票抬头
                dbEntity.setInvoiceTitle(entity.getInvoiceTitle());
                //开票地址
                dbEntity.setInvoiceRegisteredAddress(entity.getInvoiceRegisteredAddress());
                //开票电话
                dbEntity.setInvoiceTelephone(entity.getInvoiceTelephone());
                //开户行
                dbEntity.setInvoiceBank(entity.getInvoiceBank());
                //开户账号
                dbEntity.setInvoiceAccountNo(entity.getInvoiceAccountNo());
                //开户税号
                dbEntity.setInvoiceTaxNo(entity.getInvoiceTaxNo());
                //所属行业代码
                dbEntity.setIndustryCd(entity.getIndustryCd());
                //备注
                dbEntity.setRemark(entity.getRemark());
                this.customerDao.save(dbEntity);
            }
            //不存在的话，进行插入
            else {
                //客户编号
                //entity.setCode();
                this.customerDao.save(entity);
            }
        }
    }

    /**
     * 根据合同客户UID导入合同客户信息
     *
     * @param customerUid 合同客户UID
     */
    public void importCustomerInfo(String customerUid) {
        //验证合同客户信息是否存在
        CustomerExt customer = this.getDetail(customerUid);
        //如果存在，直接返回
        if (customer != null) {
            return;
        }

        //调用接口参数
        Map<String, Object> params = new HashMap<>();
        params.put("uid", customerUid);

        //API获取合同客户信息
        String result = HttpUtils.doPost(this.parameterService.getValue(AppCodeConsts.APP_COMMON, CmnConsts.DATA_IMPORT_CMN_API) + "getTableRow?tableName=cmn_customer", params);

        //转换API获取结果
        AjaxResult ajaxRequest = JsonUtils.parseJSON(result, AjaxResult.class);

        //调用接口成功场合
        if (ajaxRequest.isSuccess()) {
            //实体转换
            customer = JsonUtils.parseJSON(ajaxRequest.getContent().toString(), CustomerExt.class);
            //转换成功场合
            if (customer != null) {
                //添加合同客户信息
                this.save(customer);
            }
        }
    }

    /**
     * 根据用户UID取得用户所属客户一览
     *
     * @param userUid 用户UID
     * @return 角色一览
     */
    public List<CustomerExt> findByUserUid(String userUid) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("userUid", userUid);
        return search(CustomerExt.class, getSQL("cmn/customer/findByUserUid"), condition);
    }

    @Override
    public Page<CustomerExt> search(Class<CustomerExt> cls, String sql, Map<String, Object> condition, PageRequest pageRequest) {
        Page<CustomerExt> result = super.search(cls, sql, condition, pageRequest);

        //补全用户列表
        for (CustomerExt customer : result) {
            customer.setUserList(customerUserService.findUserByCustomerUid(customer.getUid()));
        }

        return result;
    }
}