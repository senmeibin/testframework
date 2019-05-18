package com.bpms.cmn.controller;

import com.bpms.cmn.entity.excel.CustomerExcelEntity;
import com.bpms.cmn.entity.ext.CustomerExt;
import com.bpms.cmn.service.CustomerService;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.excel.ExcelUtils;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.core.utils.UniqueUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同客户控制器类
 */
@Controller(value = "CmnCustomerController")
@RequestMapping("/cmn/customer")
public class CustomerController extends CmnBaseController<CustomerExt> {
    /**
     * Service对象
     */
    @Autowired
    private CustomerService customerService;

    /**
     * 取得Service对象
     */
    @Override
    public CustomerService getService() {
        return customerService;
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
     * 查询用户未归属客户列表
     *
     * @param pagerJson     分页JSON字符串
     * @param conditionJson 查询条件JSON字符串
     * @return 一览数据集
     * @throws IOException
     * @throws IllegalAccessException
     */
    @ResponseBody
    @RequestMapping(value = "/searchNoCustomerUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "检索")
    public Page<CustomerExt> searchNoCustomerUser(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        //取得查询条件
        Map<String, Object> condition = this.getCondition(conditionJson);

        //保存最新的检索条件
        this.setSessionSearchCondition(conditionJson);
        this.setSessionSearchPager(pagerJson);

        //取得标准查询SQL文
        String sql = getService().getSQL("/cmn/Customer/searchNoCustomerUser");
        sql = sql.replaceAll("@userUid", (String) SearchConditionUtils.getConditionValue(condition, "userUid"));
        //数据查询
        return this.search(sql, condition, this.getPageRequest(this.getPager(pagerJson)));
    }

    /**
     * 数据输入画面初期化
     *
     * @param model Model对象
     * @param uid   主键UID
     * @return 数据输入JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = "input", method = RequestMethod.GET, name = "新增/编辑")
    public String input(Model model, @RequestParam(required = false) String uid) throws IllegalAccessException, IOException, InstantiationException {
        String path = super.input(model, uid);
        //添加模式的场合
        if (this.isAddMode()) {
            uid = UniqueUtils.getUID();
            // UID预设置
            this.getInputEntity().setUid(uid);
            //UID
            model.addAttribute("uid", uid);
            //设置数据模型JSON字符
            this.setInputEntityToModel(model);
        }
        return path;
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
        //性质
        dicMap.put("propertyCd", this.getDictionaryList("CM033"));
        //规模
        dicMap.put("scaleCd", this.getDictionaryList("CM034"));
        //客户来源
        dicMap.put("customerSourceCd", this.getDictionaryList("CM035"));
        //所属行业
        dicMap.put("industryCd", this.getDictionaryList("CM001"));
        //经营类型
        dicMap.put("businessTypeCd", this.getDictionaryList("CM036"));
        //客户等级
        dicMap.put("customerLevelCd", this.getDictionaryList("CM037"));
        //客户状态
        dicMap.put("customerStatusCd", this.getDictionaryList("CM038"));
        //主客户选择
        dicMap.put("mainCustomerUid", this.getDropdownList("cmn_main_customer", "uid", "main_customer_name"));

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 数据导入页面初期化
     *
     * @return 数据导入页面
     */
    @RequestMapping(value = "importInit", method = RequestMethod.GET)
    public String importInit() {

        return this.getImportJsp();
    }

    /**
     * 根据xls执行上传数据
     *
     * @param file               附件文件
     * @param redirectAttributes RedirectAttributes对象
     * @return 数据导入页面
     */
    @RequestMapping(value = "importPost", method = RequestMethod.POST)
    public String importPost(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {

            Map<String, List<CustomerExcelEntity>> result = ExcelUtils.read(CustomerExcelEntity.class, file.getInputStream());
            if (result.isEmpty() || result.get("合同客户数据").size() <= 0) {
                redirectAttributes.addFlashAttribute("error", "导入合同客户数据为空");
            }
            else {
                //List<Map>转换为List<Entity>
                List<CustomerExt> customerExtList = this.convertListToCustomer((List<CustomerExcelEntity>) result.get("合同客户数据"));

                //数据写入
                this.customerService.saveCustomerData(customerExtList);

                redirectAttributes.addFlashAttribute("notice", "合同客户数据导入成功，共导入" + customerExtList.size() + "条数据。");
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "合同客户数据导入失败，请确认主客户数据文件格式是否与模板文件一致。");
        }

        //防止重复提交，画面Redirect处理
        return String.format("redirect:%s/importInit", this.getRequestMappingPath());
    }

    /**
     * 把excel 的列表转换为数据实体列表
     *
     * @param list excel 合同数据客户的列表
     * @return 数据实体列表
     */
    private List<CustomerExt> convertListToCustomer(List<CustomerExcelEntity> list) {
        List<CustomerExt> dest = new ArrayList<>();
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        for (CustomerExcelEntity entity : list) {
            CustomerExt customer = new CustomerExt();
            beanMapper.map(entity, customer);
            if (StringUtils.isNotBlank(customer.getCustomerName())) {
                dest.add(customer);
            }
        }
        return dest;
    }

    /**
     * 根据客户名称获取客户信息一览
     * [注：AutoComplete专用]
     *
     * @param customerName 客户名称
     * @return 带Value的客户一览
     */
    @RequestMapping(value = "autoCompleteCustomerList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult autoCompleteUserList(@RequestParam String customerName) {
        AjaxResult ajaxResult = new AjaxResult();

        if (StringUtils.isEmpty(customerName) || StringUtils.isEmpty(customerName.trim())) {
            return ajaxResult;
        }

        Map<String, Object> condition = new HashMap<>();
        condition.put("main.customerName$partial_search", customerName.trim());

        List<CustomerExt> list = this.getService().search(condition);
        List<Map<String, String>> listMap = new ArrayList<>();
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        for (CustomerExt customer : list) {
            Map<String, String> map = new HashMap<>();
            beanMapper.map(customer, map);
            map.put("label", customer.getCustomerName());
            listMap.add(map);
        }
        ajaxResult.setContent(listMap);
        return ajaxResult;
    }
}
