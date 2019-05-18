package com.bpms.demo.controller;

import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.WeeklyReportExt;
import com.bpms.demo.service.WeeklyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 周报控制器类
 */
@Controller
@RequestMapping("/demo/weeklyreport")
public class WeeklyReportController extends DemoBaseController<WeeklyReportExt> {
    /**
     * Service对象
     */
    @Autowired
    private WeeklyReportService weeklyReportService;

    /**
     * 周报明细控制器类
     */
    @Autowired
    private WeeklyReportDetailController weeklyReportDetailController;

    /**
     * 取得Service对象
     */
    @Override
    public WeeklyReportService getService() {
        return weeklyReportService;
    }

    /**
     * 数据一览画面初期化
     *
     * @param model Model对象
     * @return 数据一览JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException {
        return super.list(model);
    }

    /**
     * 初始化下拉框选项内容
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    @Override
    public void initOptionList(Model model) throws IllegalAccessException, IOException {
        Map<String, Object> dicMap = new HashMap<>();

        weeklyReportDetailController.initOptionList(model);
        //基地信息
        dicMap.put("baseUid", this.getDropdownList("demo_base_info", "uid", "base_name"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 初始化InputEntity实体对象[后处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    @Override
    protected void initInputEntityAfter(Model model, String uid) {
        WeeklyReportExt entity = this.getInputEntity();
        if (this.isAddMode()) {
            entity.setUid(UniqueUtils.getUID());
            //填写人
            entity.setUserUid(this.getCurrentUserId());
            entity.setUserName(this.getCurrentUser().getUserName());
            //填写时间
            entity.setFillTimeStart(DateUtils.getFirstDayOfWeek(new Date()));
            entity.setFillTimeEnd(DateUtils.getLastDayOfWeek(new Date()));
            //新增时初始周报表明细数据列表
            weeklyReportDetailController.initWeeklyReportDetailList(model);
        }
        model.addAttribute("weeklyReportUid", this.getInputEntity().getUid());
    }

    /**
     * 数据导出
     *
     * @param uid 周报实体UID
     * @return 字节流
     */
    @RequestMapping(value = "exportReport", method = RequestMethod.POST, name = "导出周报")
    public ResponseEntity<byte[]> exportReport(@RequestParam String uid) {
        try {
            //fileName文件名，body字节流
            Map<String, Object> data = weeklyReportService.exportReport(uid);
            //fileName文件名，body字节流
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.add("Content-Disposition", "attachment;filename=" + this.convertDownloadFileName((String) data.get("fileName")));
            return new ResponseEntity<>((byte[]) data.get("body"), headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("导出报表出错：", e);
            throw new ServiceException(e.getMessage());
        }
    }
}