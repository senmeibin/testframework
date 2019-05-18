package com.bpms.demo.service;

import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.demo.dao.ChargesInformationDao;
import com.bpms.demo.entity.ext.ChargesInformationDetailExt;
import com.bpms.demo.entity.ext.ChargesInformationExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 企业收费信息服务类
 */
@Service
public class ChargesInformationService extends DemoBaseService<ChargesInformationExt> {
    @Autowired
    private ChargesInformationDao chargesInformationDao;

    /**
     * 企业收费明细服务类
     */
    @Autowired
    private ChargesInformationDetailService chargesInformationDetailService;

    @Override
    public ChargesInformationDao getDao() {
        return chargesInformationDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ChargesInformationExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ChargesInformationExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ChargesInformationExt saveBefore(ChargesInformationExt entity) {
        List<ChargesInformationDetailExt> detailExtList = entity.getChargesInformationDetailList();
        if (CollectionUtils.isEmpty(detailExtList)) {
            throw new ServiceValidationException("收费明细数据不能为空，请刷新页面重新操作。");
        }
        //删除旧数据
        this.chargesInformationDetailService.getDao().delete("charges_information_uid", entity.getUid());
        //按照收费时间排序
        Collections.sort(detailExtList, new Comparator<ChargesInformationDetailExt>() {
            @Override
            public int compare(ChargesInformationDetailExt detailExt1, ChargesInformationDetailExt detailExt2) {
                return detailExt1.getChargesDate().compareTo(detailExt2.getChargesDate());
            }
        });

        //回写第一次收费日期
        entity.setFirstChargeDate(detailExtList.get(0).getChargesDate());
        //回写应收日期
        detailExtList.get(0).setDueChargesDate(detailExtList.get(0).getChargesDate());

        //获取收费方式(01按月 02按季度 03按年)
        int month = StringUtils.equals("03", entity.getChargesTypeCd()) ? 12 : StringUtils.equals("02", entity.getChargesTypeCd()) ? 3 : 1;

        //回写其他数据的 应收费日期
        if (detailExtList.size() > 1) {
            for (int i = 1, len = detailExtList.size(); i < len; i++) {
                ChargesInformationDetailExt detailExt = detailExtList.get(i);
                detailExt.setDueChargesDate(DateUtils.addMonths(entity.getFirstChargeDate(), month * i));
            }
        }

        //保存明细数据
        for (ChargesInformationDetailExt detailExt : detailExtList) {
            this.chargesInformationDetailService.getDao().save(detailExt);
        }

        //设置下次收费时间(最后一次收取费用的一个收费周期)
        entity.setNextReceiptDate(DateUtils.addMonths(detailExtList.get(detailExtList.size() - 1).getDueChargesDate(), month));

        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ChargesInformationExt saveAfter(ChargesInformationExt entity) {
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
    protected Boolean deleteBefore(ChargesInformationExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ChargesInformationExt entity) {
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
     * SQL条件自定义拼接处理
     *
     * @param sql       SQL文
     * @param condition 检索条件
     * @return SQL文
     */
    protected String prepareSQL(String sql, Map<String, Object> condition) {
        //期限查询
        String queryMode = (String) SearchConditionUtils.getConditionValue(condition, "queryMode");

        StringBuilder sb = new StringBuilder();

        //超期
        if (StringUtils.equals("0", queryMode)) {
            sb.append("AND main.next_receipt_date < CURDATE() ");
        }
        //7天内到期
        else if (StringUtils.equals("1", queryMode)) {
            sb.append("AND DATEDIFF(main.next_receipt_date,CURDATE()) <= 7 AND main.next_receipt_date >= CURDATE()");
        }
        //15天内到期
        else if (StringUtils.equals("2", queryMode)) {
            sb.append("AND DATEDIFF(main.next_receipt_date,CURDATE()) <= 15 AND main.next_receipt_date >= CURDATE()");
        }

        sql = sql.replace("${dynamicCondition}", sb.toString());
        return sql;
    }
}