package com.bpms.pms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bpms.cmn.service.CmnUserService;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.utils.UniqueUtils;
import com.bpms.sys.service.DeptService;
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
import com.bpms.pms.entity.ext.CompanyInfoExt;
import com.bpms.pms.service.CompanyInfoService;
import com.bpms.pms.controller.PmsBaseController;

/**
 * 公司资讯控制器类
 */
@Controller
@RequestMapping("/pms/companyinfo")
public class CompanyInfoController extends PmsBaseController<CompanyInfoExt> {
    /**
     * Service对象
     */
    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private CmnUserService cmnUserService;

    /**
     * 取得Service对象
     */
    @Override
    public CompanyInfoService getService() {
        return companyInfoService;
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
        dicMap.put("companyInfoCd", this.getDictionaryList("PMS001"));

        List<DropdownEntity> userList = this.cmnUserService.getUserSelect(DropdownEntity.class, false, null);
        //负责人
        dicMap.put("chargeUserUid", userList);

        //负责部门
        dicMap.put("chargeDeptUid", deptService.getDropdownDept(CmnConsts.ROOT_DEPT_UID, 3));

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
