package com.bpms.crm.service;

import com.bpms.core.dao.BaseDao;
import com.bpms.core.dao.DummyDao;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.crm.bean.DashboardDataInfo;
import com.bpms.sys.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CRM工作台服务类
 */
@Service(value = "CrmDesktopService")
public class DesktopService extends CrmBaseService {

    @Autowired
    private DummyDao dummyDao;

    @Autowired
    private UserService userService;

    @Override
    public BaseDao getDao() {
        return dummyDao;
    }

    /**
     * CR专员仪表盘数据
     *
     * @param userUid CR专员UID
     * @return 仪表盘数据实体
     */
    public DashboardDataInfo getDashboardData(String userUid) {
        if (StringUtils.isEmpty(userUid)) {
            throw new ServiceException("CR专员UID不能为空。");
        }

        Map<String, Object> condition = Maps.newHashMap();
        condition.put(":userUid", userUid);
        condition.put(":monthDate", DateUtils.getFirstDayOfMonth(DateUtils.getNowDate()));
        condition.put(":currentDate", DateUtils.parseDate(DateUtils.format(DateUtils.getNowDate(), DateUtils.DATE_FORMAT)));
        List<DashboardDataInfo> dashboardDatumInfos = dummyDao.search(DashboardDataInfo.class, getSQL("crm/Desktop/getDashboardData"), condition);
        if (CollectionUtils.isNotEmpty(dashboardDatumInfos)) {
            return dashboardDatumInfos.get(0);
        }
        else {
            return new DashboardDataInfo();
        }
    }

}