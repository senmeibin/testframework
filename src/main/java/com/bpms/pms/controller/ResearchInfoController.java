package com.bpms.pms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bpms.core.utils.UniqueUtils;
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
import com.bpms.pms.entity.ext.ResearchInfoExt;
import com.bpms.pms.service.ResearchInfoService;
import com.bpms.pms.controller.PmsBaseController;
import com.bpms.pms.service.ProjectService;

/**
 * 市场调研控制器类
 */
@Controller
@RequestMapping("/pms/researchinfo")
public class ResearchInfoController extends PmsBaseController<ResearchInfoExt> {
    /**
     * Service对象
     */
    @Autowired
    private ResearchInfoService researchInfoService;

    /**
     * Service对象
     */
    @Autowired
    private ProjectService projectService;

    /**
     * 取得Service对象
     */
    @Override
    public ResearchInfoService getService() {
        return researchInfoService;
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
        Map<String, Object> dicMap = new HashMap<>();
        //类别
        dicMap.put("researchInfoCd", this.getDictionaryList("PMS002"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));

        //项目
        dicMap.put("projectUid", projectService.getDropdownProject());

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
        if (this.getInputEntity().isAddMode()) {
            this.getInputEntity().setUid(UniqueUtils.getUID());
            model.addAttribute("uid", this.getInputEntity().getUid());
        }
    }
}
