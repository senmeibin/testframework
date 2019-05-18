package com.bpms.cmn.service;

import com.google.common.collect.Maps;
import com.bpms.cmn.dao.CustomerUserDao;
import com.bpms.cmn.entity.ext.CustomerUserExt;
import com.bpms.sys.entity.ext.UserExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 客户用户所属服务类
 */
@Service(value = "CmnCustomerUserService")
public class CustomerUserService extends CmnBaseService<CustomerUserExt> {
    @Autowired
    private CustomerUserDao customerUserDao;


    @Autowired
    private CustomerService customerService;

    @Override
    public CustomerUserDao getDao() {
        return customerUserDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CustomerUserExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CustomerUserExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CustomerUserExt saveBefore(CustomerUserExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CustomerUserExt saveAfter(CustomerUserExt entity) {
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
    protected Boolean deleteBefore(CustomerUserExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CustomerUserExt entity) {
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

    public List<UserExt> findUserByCustomerUid(String customerUid) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("customerUid", customerUid);
        return search(UserExt.class, getSQL("cmn/customeruser/findUserByCustomerUid"), condition);
    }

    /**
     * 保存客户所属用户
     *
     * @param uids        用户集合
     * @param customerUid 客户UID
     */
    @Transactional
    public void addCustomerUserByCustomer(String uids, String customerUid) {
        //循环保存结果
        for (String userUid : uids.split(",")) {
            saveCustomerUser(customerUid, userUid);
        }
    }

    /**
     * 保存用户所属客户
     *
     * @param uids    客户集合
     * @param userUid 用户UID
     */
    @Transactional
    public void addCustomerUserByUser(String uids, String userUid) {
        //循环保存结果
        for (String customerUid : uids.split(",")) {
            saveCustomerUser(customerUid, userUid);
        }
    }

    /**
     * @param customerUid 客户UID
     * @param userUid     用户UID
     */
    private void saveCustomerUser(String customerUid, String userUid) {
        CustomerUserExt customerUser = new CustomerUserExt();
        customerUser.setCustomerUid(customerUid);
        customerUser.setUserUid(userUid);
        this.save(customerUser);
    }
}