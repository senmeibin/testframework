package com.bpms.cmn.service;

import com.bpms.cmn.dao.CustomerDao;
import com.bpms.cmn.dao.MainCustomerDao;
import com.bpms.cmn.entity.ext.CustomerExt;
import com.bpms.cmn.entity.ext.MainCustomerExt;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.HttpUtils;
import com.bpms.core.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主客户服务类
 */
@Service(value = "CmnMainCustomerService")
public class MainCustomerService extends CmnBaseService<MainCustomerExt> {
    @Autowired
    private MainCustomerDao mainCustomerDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    public MainCustomerDao getDao() {
        return mainCustomerDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(MainCustomerExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(MainCustomerExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected MainCustomerExt saveBefore(MainCustomerExt entity) {
        //true:数据重复/false:数据不重复
        boolean isDuplication = this.getDao().isDuplication("uid", entity.getUid(), "", "", "main_customer_name", entity.getMainCustomerName(), true);
        if (isDuplication) {
            throw new ServiceValidationException("主客户名称已经存在，请重新输入。");
        }
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected MainCustomerExt saveAfter(MainCustomerExt entity) {
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
        // 分割UID
        String[] uids = ids.split(",");
        // 循环删除
        for (String uid : uids) {
            Map map = new HashMap<>();
            map.put("mainCustomerUid", uid);
            //根据客户名称获取有效的合同信息是否存在
            List list = this.customerDao.search(CustomerExt.class, this.getSQL("cmn/customer/search"), map);
            if (list != null && list.size() > 0) {
                throw new ServiceValidationException("该主客户已被使用，不能被删除。");
            }
        }

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
    protected Boolean deleteBefore(MainCustomerExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(MainCustomerExt entity) {
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
     * @param mainCustomerExtList 需要插入DB列表
     */
    @Transactional
    public void saveMainCustomerData(List<MainCustomerExt> mainCustomerExtList) {
        int rowIndex = 0;
        for (MainCustomerExt entity : mainCustomerExtList) {
            rowIndex = rowIndex + 1;
            //按客户名称判断该客户是否已经存在
            Map<String, Object> map = new HashMap<>();
            //客户名称 设为检索条件
            map.put("main.mainCustomerName", entity.getMainCustomerName());
            //根据结算单自动求和各金额
            List<MainCustomerExt> list = this.mainCustomerDao.search(MainCustomerExt.class, this.getSQL("cmn/mainCustomer/search"), map);
            if (list != null && list.size() > 0) {
                MainCustomerExt dbEntity = list.get(0);
                //主客户简称
                dbEntity.setMainCustomerAbbreviation(entity.getMainCustomerAbbreviation());
                //联系人
                dbEntity.setContactName(entity.getContactName());
                //联系电话
                dbEntity.setContactTelephone(entity.getContactTelephone());
                //客户概况
                dbEntity.setIntroduction(entity.getIntroduction());
                //备注
                dbEntity.setRemark(entity.getRemark());
                //表示顺
                dbEntity.setDispSeq(rowIndex);
                this.mainCustomerDao.save(dbEntity);
            }
            //不存在的话，进行插入
            else {
                //表示顺
                entity.setDispSeq(rowIndex);
                this.mainCustomerDao.save(entity);
            }
        }
    }

    /**
     * 根据主客户名称获取客户信息list
     *
     * @param mainCustomerName 主客户名称
     * @return 客户list
     */
    public List<MainCustomerExt> getMainCustomers(String mainCustomerName) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.mainCustomerName$like_search", mainCustomerName);
        return this.search(MainCustomerExt.class, this.getSQL("cmn/mainCustomer/getMainCustomers"), condition);
    }

    /**
     * 根据主客户UID导入主客户信息
     *
     * @param mainCustomerUid 主客户UID
     */
    public void importMainCustomerInfo(String mainCustomerUid) {
        //验证主客户信息是否存在
        MainCustomerExt mainCustomer = this.getDetail(mainCustomerUid);
        //如果存在，直接返回
        if (mainCustomer != null) {
            return;
        }

        //调用接口参数
        Map<String, Object> params = new HashMap<>();
        params.put("uid", mainCustomerUid);

        //API获取主客户信息
        String result = HttpUtils.doPost(this.parameterService.getValue(AppCodeConsts.APP_COMMON, CmnConsts.DATA_IMPORT_CMN_API) + "getTableRow?tableName=cmn_main_customer", params);

        //转换API获取结果
        AjaxResult ajaxRequest = JsonUtils.parseJSON(result, AjaxResult.class);

        //调用接口成功场合
        if (ajaxRequest.isSuccess()) {
            //实体转换
            mainCustomer = JsonUtils.parseJSON(ajaxRequest.getContent().toString(), MainCustomerExt.class);
            //转换成功场合
            if (mainCustomer != null) {
                //添加主客户信息
                this.save(mainCustomer);
            }
        }
    }
}