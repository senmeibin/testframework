package com.bpms.crm.service;

import com.bpms.core.dao.BaseDao;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.crm.bean.SchedulerInfo;
import com.bpms.crm.dao.StudentDao;
import com.bpms.crm.entity.ext.StudentExt;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SchedulerService extends CrmBaseService<StudentExt> {
    @Autowired
    private StudentDao studentDao;

    @Override
    public BaseDao getDao() {
        return this.studentDao;
    }

    /**
     * 检索我的日程数据
     *
     * @param consultantUserUid 课程顾问UID
     * @param dayOffset         与当前时间的偏移量
     * @param dayScope          日程的天数
     */
    public Map<String, List<SchedulerInfo>> searchScheduler(String consultantUserUid, int dayOffset, int dayScope) {
        //结果
        Map<String, List<SchedulerInfo>> result = Maps.newLinkedHashMap();

        //当天日期无时间信息
        Date today = DateUtils.parseDate(DateUtils.getNowDateString());
        //开始日期
        Date beginDate = DateUtils.addDate(today, dayOffset);
        //结束日期
        Date endDate = DateUtils.addDate(beginDate, dayScope);

        //初始化要显示的日期集合
        for (int i = 0; i < dayScope; i++) {
            result.put(DateUtils.format(DateUtils.addDate(beginDate, i)), Lists.newArrayList());
        }

        //查询跟进提醒记录
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("followup.followupBelongConsultantUserUid", consultantUserUid);
        condition.put("followup.nextFollowupDate$>=", DateUtils.format(beginDate) + " 00:00:00");
        condition.put("followup.nextFollowupDate$<", DateUtils.format(endDate) + " 00:00:00");
        List<SchedulerInfo> list = this.search(SchedulerInfo.class, getSQL("crm/Scheduler/searchFollowupScheduler"), condition);

        for (SchedulerInfo schedulerInfo : list) {
            String scheduleDate = DateUtils.format(schedulerInfo.getScheduleDate());
            if (StringUtils.isNotEmpty(scheduleDate)) {
                //按天分组提醒记录
                if (result.get(scheduleDate) != null) {
                    result.get(scheduleDate).add(schedulerInfo);
                }
            }
        }
        return result;
    }
}
