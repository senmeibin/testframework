package com.bpms.cmn.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bpms.core.entity.CoreEntity;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.UserService;
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
import com.bpms.cmn.entity.ext.CustomerUserExt;
import com.bpms.cmn.service.CustomerUserService;
import com.bpms.cmn.controller.CmnBaseController;

/**
 * 客户用户所属控制器类
 */
@Controller(value = "CmnCustomerUserController")
@RequestMapping("/cmn/customeruser")
public class CustomerUserController extends CmnBaseController<CustomerUserExt> {
    /**
     * Service对象
     */
    @Autowired
    private CustomerUserService customerUserService;

    @Autowired
    private UserService userService;

    /**
     * 取得Service对象
     */
    @Override
    public CustomerUserService getService() {
        return customerUserService;
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
     * 查询客户未归属用户列表
     *
     * @param pagerJson     分页JSON字符串
     * @param conditionJson 查询条件JSON字符串
     * @return 一览数据集
     * @throws IOException
     * @throws IllegalAccessException
     */
    @ResponseBody
    @RequestMapping(value = "/searchNoCustomerUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "检索")
    public Page<UserExt> searchNoCustomerUser(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        //取得查询条件
        Map<String, Object> condition = this.getCondition(conditionJson);

        //保存最新的检索条件
        this.setSessionSearchCondition(conditionJson);
        this.setSessionSearchPager(pagerJson);

        //取得标准查询SQL文
        String sql = getService().getSQL("/cmn/CustomerUser/searchNoCustomerUser");
        sql = sql.replaceAll("@customerUid", (String) SearchConditionUtils.getConditionValue(condition, "customerUid"));
        //数据查询
        return this.userService.search(sql, condition, this.getPageRequest(this.getPager(pagerJson)));
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

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 添加客户所属用户(支持批量和单条)
     *
     * @param uids        用户集合
     * @param customerUid 客户UID
     * @return AjaxResult对象
     */
    @RequestMapping(value = "addCustomerUserByCustomer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "添加客户所属用户")
    @ResponseBody
    public AjaxResult addCustomerUserByCustomer(@RequestParam String uids, @RequestParam String customerUid) {
        this.getService().addCustomerUserByCustomer(uids, customerUid);
        return AjaxResult.createSuccessResult("用户添加成功。");
    }

    /**
     * 添加用户的合同客户(支持批量和单条)
     *
     * @param uids    客户集合
     * @param userUid 用户UID
     * @return AjaxResult对象
     */
    @RequestMapping(value = "addCustomerUserByUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "添加用户的合同客户")
    @ResponseBody
    public AjaxResult addCustomerUserByUser(@RequestParam String uids, @RequestParam String userUid) {
        this.getService().addCustomerUserByUser(uids, userUid);
        return AjaxResult.createSuccessResult("客户添加成功。");
    }
}
