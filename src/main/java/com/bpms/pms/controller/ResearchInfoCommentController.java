package com.bpms.pms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.controller.BaseController;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.entity.BaseEntity;
import com.bpms.core.service.BaseService;
import com.bpms.pms.entity.ext.ResearchInfoCommentExt;
import com.bpms.pms.service.ResearchInfoCommentService;
import com.bpms.pms.controller.PmsBaseController;

/**
 * 市场调研评论控制器类
 */
@Controller
@RequestMapping("/pms/researchinfocomment")
public class ResearchInfoCommentController extends PmsBaseController<ResearchInfoCommentExt> {
    /**
     * Service对象
     */
    @Autowired
    private ResearchInfoCommentService researchInfoCommentService;

    /**
     * 取得Service对象
     */
    @Override
    public ResearchInfoCommentService getService() {
        return researchInfoCommentService;
    }
    
    /**
     * 数据一览画面初期化
     *
     * @param model Model对象
     *
     * @return 数据一览JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = { "list", "" }, method = RequestMethod.GET)
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
        this.researchInfoCommentService.initOptionList(model);
    }
}
