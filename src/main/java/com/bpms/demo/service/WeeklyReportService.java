package com.bpms.demo.service;

import com.bpms.core.AppContext;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.DateUtils;
import com.bpms.demo.dao.WeeklyReportDao;
import com.bpms.demo.entity.ext.WeeklyReportDetailExt;
import com.bpms.demo.entity.ext.WeeklyReportExt;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 周报服务类
 */
@Service
public class WeeklyReportService extends DemoBaseService<WeeklyReportExt> {
    @Autowired
    private WeeklyReportDao weeklyReportDao;

    /**
     * 周报明细服务类
     */
    @Autowired
    private WeeklyReportDetailService weeklyReportDetailService;

    @Override
    public WeeklyReportDao getDao() {
        return weeklyReportDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(WeeklyReportExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(WeeklyReportExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected WeeklyReportExt saveBefore(WeeklyReportExt entity) {
        List<WeeklyReportDetailExt> detailExtList = entity.getWeeklyReportDetailList();
        if (CollectionUtils.isEmpty(detailExtList)) {
            throw new ServiceValidationException("周报明细数据不能为空，请刷新页面重新操作。");
        }

        //删除旧数据
        this.weeklyReportDetailService.getDao().delete("weekly_report_uid", entity.getUid());

        //保存明细数据
        for (WeeklyReportDetailExt detailExt : detailExtList) {
            //周报主键
            detailExt.setWeeklyReportUid(entity.getUid());
            //保存
            this.weeklyReportDetailService.getDao().save(detailExt);
        }
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected WeeklyReportExt saveAfter(WeeklyReportExt entity) {
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
    protected Boolean deleteBefore(WeeklyReportExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(WeeklyReportExt entity) {
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
     * 数据导出
     *
     * @param uid 周报实体UID
     * @return 文件名，字节流
     */
    public Map<String, Object> exportReport(String uid) throws IOException {
        //返回值
        Map<String, Object> map = new HashMap<>();

        //获取数据
        WeeklyReportExt entity = this.getDetail(uid);

        if (entity == null) {
            throw new ServiceValidationException("数据异常，请刷新页面后重新操作。");
        }

        //获取明细数据
        List<WeeklyReportDetailExt> detailList = weeklyReportDetailService.getWeeklyReportDetailList(entity.getUid());

        //获取文件位置
        String ctxPath = AppContext.getRealPath() + "template/demo/WeeklyReportTemplate.xlsx";

        //读取数据流
        InputStream stream = new FileInputStream(ctxPath);
        XSSFWorkbook workBook = new XSSFWorkbook(stream);

        XSSFSheet sheet = workBook.getSheetAt(0);

        //获取标题行
        XSSFRow titleRow = sheet.getRow(1);
        //写入姓名
        titleRow.getCell(2).setCellValue(entity.getUserName());
        //时间
        titleRow.getCell(5).setCellValue(DateUtils.format(entity.getFillTimeStart()));

        for (int i = 4; i <= 10; i++) {
            //行
            XSSFRow row = sheet.getRow(i);
            //数据
            WeeklyReportDetailExt detailExt = detailList.get(i - 4);
            //已完成工作
            row.getCell(2).setCellValue(detailExt.getCompletedWork());
            //未完成
            row.getCell(3).setCellValue(detailExt.getUnfinishedWork());
            //开心的事
            row.getCell(4).setCellValue(detailExt.getHappyThing());
        }

        //周总结
        sheet.getRow(11).getCell(2).setCellValue(entity.getSummary());

        //写文件
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workBook.write(os);
        IOUtils.closeQuietly(os);

        map.put("fileName", "个人工作周报-" + entity.getUserName() + "-" + DateUtils.format(entity.getFillTimeStart(), "yyMMdd") + ".xlsx");
        map.put("body", os.toByteArray());
        return map;
    }
}