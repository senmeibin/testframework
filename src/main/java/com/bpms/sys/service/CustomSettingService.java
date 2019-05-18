package com.bpms.sys.service;

import com.google.common.collect.Maps;
import com.bpms.cmn.service.CmnBaseService;
import com.bpms.sys.dao.CustomSettingDao;
import com.bpms.sys.entity.ext.CustomSettingExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义设置服务类
 */
@Service
@SuppressWarnings("unchecked")
public class CustomSettingService extends CmnBaseService<CustomSettingExt> {
    @Autowired
    private CustomSettingDao customSettingDao;

    @Override
    public CustomSettingDao getDao() {
        return customSettingDao;
    }

    @Override
    protected CustomSettingExt saveBefore(CustomSettingExt entity) {
        entity.setUserUid(getCurrentUserId());

        //设置类型 = 【3：每页显示记录数自定义设置】 的场合，重置UID主键
        //注：每个人每个画面只能设置唯一一条记录
        if (entity.getSettingType() == 3) {
            List<CustomSettingExt> list = this.getCustomSetting(entity.getPageInstance(), 3);
            if (!list.isEmpty()) {
                entity.setUid(list.get(0).getUid());
            }
        }

        return super.saveBefore(entity);
    }

    /**
     * 根据画面JS对象实例及设置类型取出指定用户配置记录 按最后更新时间排序
     *
     * @param pageInstance 调用画面JS实例对象字符串
     * @param settingType  设置类型（1：列表显示列自定义设置2：列表查询条件自定义设置3：每页显示记录数自定义设置4：画面帮助内容设定）
     * @param userUid      用户UID
     * @return 配置实体对象一览
     */
    public List<CustomSettingExt> getCustomSetting(String pageInstance, int settingType, String userUid) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("userUid", userUid);
        condition.put("pageInstance", pageInstance);
        condition.put("settingType", settingType);
        return this.search(CustomSettingExt.class, condition, new Sort(Sort.Direction.DESC, "updateDate"));
    }

    /**
     * 根据画面JS对象实例及设置类型取出当前用户配置记录 按最后更新时间排序
     *
     * @param pageInstance 调用画面JS实例对象字符串
     * @param settingType  设置类型（1：列表显示列自定义设置2：列表查询条件自定义设置3：每页显示记录数自定义设置4：画面帮助内容设定）
     * @return 配置实体对象一览
     */
    public List<CustomSettingExt> getCustomSetting(String pageInstance, int settingType) {
        if (this.getCurrentUser() == null) {
            return new ArrayList<>();
        }

        return this.getCustomSetting(pageInstance, settingType, getCurrentUserId());
    }
}