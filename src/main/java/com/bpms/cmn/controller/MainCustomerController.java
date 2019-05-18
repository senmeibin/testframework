package com.bpms.cmn.controller;

import com.bpms.cmn.entity.excel.MainCustomerExcelEntity;
import com.bpms.cmn.entity.ext.MainCustomerExt;
import com.bpms.cmn.service.MainCustomerService;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.excel.ExcelUtils;
import com.bpms.core.utils.EntityUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import java.util.*;

/**
 * 主客户控制器类
 */
@Controller(value = "CmnMainCustomerController")
@RequestMapping("/cmn/maincustomer")
public class MainCustomerController extends CmnBaseController<MainCustomerExt> {
    /**
     * Service对象
     */
    @Autowired
    private MainCustomerService mainCustomerService;

    /**
     * 取得Service对象
     */
    @Override
    public MainCustomerService getService() {
        return mainCustomerService;
    }

    /**
     * 取得默认排序字段（按表示顺序, 客户名称排序）
     *
     * @return 默认排序字段
     */
    @Override
    public Sort getDefaultSort() {
        // 默认按表示顺序, 客户名称排序
        return new Sort(Sort.Direction.ASC, "dispSeq").and(new Sort(Sort.Direction.ASC, "mainCustomerName"));
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

            Map<String, List<MainCustomerExcelEntity>> result = ExcelUtils.read(MainCustomerExcelEntity.class, file.getInputStream());
            if (result.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "导入主客户数据为空");
            }
            else {
                //List<Map>转换为List<Entity>
                List<MainCustomerExt> mainCustomerExtList = this.convertListToMainCustomer(result);

                //数据写入
                this.mainCustomerService.saveMainCustomerData(mainCustomerExtList);

                redirectAttributes.addFlashAttribute("notice", "主客户数据导入成功，共导入" + mainCustomerExtList.size() + "条数据。");
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "主客户数据导入失败，请确认主客户数据文件格式是否与模板文件一致。");
        }

        //防止重复提交，画面Redirect处理
        return String.format("redirect:%s/importInit", this.getRequestMappingPath());
    }

    /**
     * 根据主客户名称获取客户信息list
     *
     * @param mainCustomerName 主客户名称
     * @return 带value的客户list
     */
    @RequestMapping(value = "getMainCustomers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult getMainCustomers(@RequestParam String mainCustomerName) {
        AjaxResult ajaxResult = new AjaxResult();

        List<MainCustomerExt> list = this.mainCustomerService.getMainCustomers(mainCustomerName);
        List<Map<String, String>> listMap = new ArrayList<>();
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        for (MainCustomerExt mainCustomer : list) {
            Map<String, String> map = new HashMap<>();
            beanMapper.map(mainCustomer, map);
            map.put("label", mainCustomer.getMainCustomerName());
            listMap.add(map);
        }
        ajaxResult.setContent(listMap);
        return ajaxResult;
    }

    /**
     * 把excel 的列表转换为数据实体列表
     *
     * @param list excel 的列表
     * @return 数据实体列表
     */
    private List<MainCustomerExt> convertListToMainCustomer(Map<String, List<MainCustomerExcelEntity>> list) {
        List<MainCustomerExt> dest = new ArrayList<>();
        List<MainCustomerExcelEntity> src = new ArrayList<>();
        Collection<List<MainCustomerExcelEntity>> values = list.values();
        values.forEach(src::addAll);
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        for (MainCustomerExcelEntity entity : src) {
            MainCustomerExt mainCustomerExt = new MainCustomerExt();
            beanMapper.map(entity, mainCustomerExt);
            dest.add(mainCustomerExt);
        }
        return dest;
    }

    /**
     * 设定保存处理结果
     *
     * @param entity 实体对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult setSaveResult(MainCustomerExt entity) {
        AjaxResult result = AjaxResult.createSuccessResult(EntityUtils.getPkValue(entity), "数据保存成功。", entity);
        //如果是合同页面过来的新增， 重新赋值主客户下拉框
        //上级部门一览【数据保存成功后，需要重置上级部门下拉选择列表对象】
        result.setContent(this.getDropdownList("cmn_main_customer", "uid", "main_customer_name"));
        result.setPk((String) EntityUtils.getPkValue(entity));

        return result;
    }
}
