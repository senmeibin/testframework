package com.bpms.sys.service;

import com.google.common.collect.Maps;
import com.bpms.core.consts.CmnConsts;
import com.bpms.sys.dao.ColumnSettingDao;
import com.bpms.sys.entity.ext.ColumnSettingExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 显示列设置服务类
 */
@Service
public class ColumnSettingService extends SysBaseService<ColumnSettingExt> {
    @Autowired
    private ColumnSettingDao columnSettingDao;

    @Override
    public ColumnSettingDao getDao() {
        return columnSettingDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ColumnSettingExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ColumnSettingExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ColumnSettingExt saveBefore(ColumnSettingExt entity) {
        //设置默认值
        entity.setUserUid(getCurrentUser().getUserUid());

        entity.setRecordStatus(Integer.valueOf(CmnConsts.RECORD_STATUS_ACTIVE));

        if (entity.getSettingType() == 0 || entity.getSettingType() == null) {
            entity.setSettingType(CmnConsts.COLUMN_SETTING_TYPE_PAGE);
        }

        //按用户uid 画面code和设置类型查找
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("userUid", entity.getUserUid());
        condition.put("pageInstance", entity.getPageInstance());
        condition.put("settingType", entity.getSettingType());

        //更新操作  删除+新增
        columnSettingDao.executeUpdate(getSQL("sys/columnsetting/delete"), condition);

        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected ColumnSettingExt saveAfter(ColumnSettingExt entity) {
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
    protected Boolean deleteBefore(ColumnSettingExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ColumnSettingExt entity) {
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
     * 取得登录用户指定页面的列设置信息
     *
     * @param pageInstance 列表画面JS实例对象字符串
     * @return 显示列设置信息
     */
    public String getColumnSetting(String pageInstance) {
        return getColumnSetting(pageInstance, 1);
    }

    /**
     * 取得登录用户指定页面的列设置信息
     *
     * @param pageInstance 列表画面JS实例对象字符串
     * @param settingType  设置类型
     * @return 显示列设置信息
     */
    public String getColumnSetting(String pageInstance, int settingType) {
        if (StringUtils.isEmpty(pageInstance)) {
            return StringUtils.EMPTY;
        }

        if (this.getCurrentUser() == null) {
            return StringUtils.EMPTY;
        }

        Map<String, Object> condition = Maps.newHashMap();
        condition.put("userUid", getCurrentUserId());
        condition.put("pageInstance", pageInstance);
        condition.put("settingType", settingType);
        List<ColumnSettingExt> list = this.search(ColumnSettingExt.class, this.getSearchSQL(condition), condition);
        if (list.size() == 0) {
            return StringUtils.EMPTY;
        }
        else {
            return list.get(0).getParameters();
        }
    }
}