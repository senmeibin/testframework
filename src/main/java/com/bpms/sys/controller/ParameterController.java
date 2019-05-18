package com.bpms.sys.controller;

import com.bpms.core.excel.ExcelUtils;
import com.bpms.sys.entity.excel.ParameterExcelEntity;
import com.bpms.sys.entity.ext.ParameterExt;
import com.bpms.sys.service.ParameterService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

/**
 * 参数配置控制器类
 */
@Controller
@RequestMapping("/sys/parameter")
public class ParameterController extends SysBaseController<ParameterExt> {
    /**
     * Service对象
     */
    @Autowired
    protected ParameterService parameterService;

    /**
     * 取得Service对象
     */
    @Override
    public ParameterService getService() {
        return parameterService;
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
        //系统编号
        dicMap.put("systemCode", this.getDictionaryList("SYS998"));

        //应用名称
        dicMap.put("appCode", this.getDropdownList("sys_application", "app_code", "app_name"));

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

            Map<String, List<ParameterExcelEntity>> map = ExcelUtils.read(ParameterExcelEntity.class, file.getInputStream());
            if (map.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "导入参数配置数据为空");
            }
            else {
                //List<Map>转换为List<Entity>
                List<ParameterExt> parameterExtList = this.convertListToMainCustomer(map);

                //数据写入
                this.parameterService.saveParameterListData(parameterExtList);

                redirectAttributes.addFlashAttribute("notice", "参数配置数据导入成功，共导入" + parameterExtList.size() + "条数据。");
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "参数配置数据导入失败，请确认参数配置数据文件格式是否与模板文件一致。");
        }

        //防止重复提交，画面Redirect处理
        return String.format("redirect:%s/importInit", this.getRequestMappingPath());
    }

    /**
     * 把excel 的列表转换为数据实体列表
     *
     * @param list excel 的列表
     * @return 数据实体列表
     */
    private List<ParameterExt> convertListToMainCustomer(Map<String, List<ParameterExcelEntity>> list) {
        List<ParameterExt> dest = new ArrayList<>();
        List<ParameterExcelEntity> src = new ArrayList<>();
        Collection<List<ParameterExcelEntity>> values = list.values();
        values.forEach(src::addAll);
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        for (ParameterExcelEntity entity : src) {
            ParameterExt parameterExt = new ParameterExt();
            beanMapper.map(entity, parameterExt);
            dest.add(parameterExt);
        }
        return dest;
    }
}
