package com.bpms.crm.controller;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.service.BaseService;
import com.bpms.crm.consts.CrmConsts;
import com.bpms.crm.service.DesktopService;
import com.bpms.crm.utility.AccessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * CRM工作台控制器专用类
 */
@Controller(value = "CrmDesktopController")
@RequestMapping(value = "/crm/desktop", name = "我的工作台")
public class DesktopController extends CrmBaseController {
    @Autowired
    private DesktopService desktopService;

    @Autowired
    private StudentController studentController;

    @Override
    public BaseService getService() {
        return null;
    }

    /**
     * 获取仪表盘数据
     */
    @ResponseBody
    @RequestMapping(value = "getDashboardData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult getDashboardData() {
        return AjaxResult.createSuccessResult();
//        long start = System.currentTimeMillis();
//        DashboardDataInfo dashboardDataInfo = desktopService.getDashboardData(getCurrentUserId());
//        return AjaxResult.createSuccessResult(String.format("仪表盘数据获取成功（耗时：%s毫秒）。", (System.currentTimeMillis() - start)), dashboardDataInfo);
    }

    /**
     * 我的工作台页面
     *
     * @param model Model对象
     * @return 我的工作台页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @RequestMapping(value = {"init"}, method = RequestMethod.GET, name = "我的工作台菜单跳转")
    public String init(Model model) throws IllegalAccessException, IOException {
        //工作台默认刷新时间参数设置 默认 5分钟（300000毫秒）
        Integer desktopRefreshTime = this.parameterService.getIntValue(CrmConsts.APP_CRM, "DESKTOP_REFRESH_TIME", 300000);
        model.addAttribute("desktopRefreshTime", desktopRefreshTime);

        //服务器地址
        String serverAddress = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "SERVER_ADDRESS", "http://rp.bpms.com");
        model.addAttribute("serverAddress", serverAddress);

        //学员详情最大打开数量
        Integer studentOpenMax = this.parameterService.getIntValue(CrmConsts.APP_CRM, "STUDENT_OPEN_MAX", 5);
        model.addAttribute("studentOpenMax", studentOpenMax);

        this.initOptionList(model);

        //技术支持、管理员、CR管理员、CRLeader场合
        if (AccessUtils.isItSupport() || AccessUtils.isSystemManagement() || AccessUtils.isCRAdmin() || AccessUtils.isCRLeader()) {
            model.addAttribute("showCourseConsultantTree", "1");
        }
        else {
            model.addAttribute("showCourseConsultantTree", "0");
        }

        this.setUseJqueryUI(true);

        //工作手册附件关联UID
        model.addAttribute("workManualRelationUid", "20180101010000000111000000000001");
        return "crm/desktop/Desktop";
    }

    /**
     * 重写List方法，重定向到我的工作台页面
     *
     * @param model Model对象
     * @return 我的工作台页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException {
        return init(model);
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
        this.studentController.initOptionList(model);
    }
}
