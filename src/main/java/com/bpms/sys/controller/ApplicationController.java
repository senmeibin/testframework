package com.bpms.sys.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.ext.ApplicationExt;
import com.bpms.sys.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * 应用模块控制器类
 */
@Controller
@RequestMapping("/sys/application")
public class ApplicationController extends SysBaseController<ApplicationExt> {
    /**
     * Service对象
     */
    @Autowired
    private ApplicationService applicationService;

    /**
     * 取得Service对象
     */
    @Override
    public ApplicationService getService() {
        return applicationService;
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
        //初始化数据导入标志位
        this.initDataImportFlag(model);

        return super.list(model);
    }

    /**
     * 批量保存表示顺序
     *
     * @param itemJson 保存信息
     * @return AjaxResult
     */
    @ResponseBody
    @RequestMapping(value = "batchSaveDispSeq", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult batchSaveDispSeq(String itemJson) {
        List<ApplicationExt> appList = JsonUtils.parseJSON(itemJson, new TypeReference<List<ApplicationExt>>() {
        });
        if (CollectionUtils.isNotEmpty(appList)) {
            for (ApplicationExt app : appList) {
                ApplicationExt entity = applicationService.findOne(app.getUid());
                entity.setDispSeq(app.getDispSeq());
                applicationService.getDao().save(entity);
            }
        }
        return AjaxResult.createSuccessResult();
    }

    @Override
    public Sort getDefaultSort() {
        return new Sort("dispSeq");
    }

    @Override
    public Sort getForceSortField() {
        return new Sort("dispSeq");
    }
}
