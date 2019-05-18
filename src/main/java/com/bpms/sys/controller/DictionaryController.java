package com.bpms.sys.controller;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.excel.ExcelUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.entity.excel.DictionaryExcelEntity;
import com.bpms.sys.entity.ext.AttachmentImportExt;
import com.bpms.sys.entity.ext.DictionaryExt;
import com.bpms.sys.service.DictionaryService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
 * 数据字典控制器类
 */
@Controller
@RequestMapping("/sys/dictionary")
public class DictionaryController extends SysBaseController<DictionaryExt> {
    /**
     * Service对象
     */
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 取得Service对象
     */
    @Override
    public DictionaryService getService() {
        return dictionaryService;
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
     * 取得默认排序字段[子类可覆盖]
     *
     * @return 默认排序字段
     */
    @Override
    public Sort getDefaultSort() {
        //默认按UID排序
        return new Sort(Sort.Direction.ASC, "main.main_cd").and(new Sort(Sort.Direction.ASC, "main.sub_cd"));
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
        AttachmentImportExt attachment = new AttachmentImportExt();
        String message;
        try {

            Map<String, List<DictionaryExcelEntity>> result = ExcelUtils.read(DictionaryExcelEntity.class, file.getInputStream());
            if (result.isEmpty()) {
                message = "导入数据为空。";
                redirectAttributes.addFlashAttribute("error", message);
                attachment.setImportResult("导入失败。");
            }
            else {
                //List<Map>转换为List<Entity>
                List<DictionaryExt> dictionaryList = this.convertListToDictionaryList(result);

                // 字典数据保存处理
                for (DictionaryExt dictionaryExt : dictionaryList) {
                    DictionaryExt dbDictionary = dictionaryService.findByMainCdAndSubCd(dictionaryExt);

                    //已有数据进行更新
                    if (dbDictionary != null) {
                        dictionaryExt.setUid(dbDictionary.getUid());
                    }
                    dictionaryExt.setRecordStatus(Integer.valueOf(CmnConsts.RECORD_STATUS_ACTIVE));
                    dictionaryService.save(dictionaryExt);
                }
                message = "数据字典导入成功，共导入" + dictionaryList.size() + "条数据。";
                redirectAttributes.addFlashAttribute("notice", message);
                attachment.setImportResult("导入成功。");
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            message = "数据字典导入失败，请确认数据字典文件格式是否与模板文件一致。";
            redirectAttributes.addFlashAttribute("error", message);
            attachment.setImportResult("导入失败。");
        }

        attachment.setModuleName("数据字典导入");
        attachment.setImportResultDetailMessage(message);

        this.saveAttachment(file, attachment);

        //防止重复提交，画面Redirect处理
        return String.format("redirect:%s/importInit", this.getRequestMappingPath());
    }

    /**
     * 转换List
     */
    private List<DictionaryExt> convertListToDictionaryList(Map<String, List<DictionaryExcelEntity>> list) {
        Object[] keys = list.keySet().toArray();
        //删除【字典数据】以外的所有数据
        for (Object key : keys) {
            if (!StringUtils.equals(key.toString(), "字典数据")) {
                list.remove(key);
            }
        }
        List<DictionaryExt> dest = new ArrayList<>();
        List<DictionaryExcelEntity> src = new ArrayList<>();
        Collection<List<DictionaryExcelEntity>> values = list.values();
        values.forEach(src::addAll);
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        for (DictionaryExcelEntity entity : src) {
            DictionaryExt dictionary = new DictionaryExt();
            beanMapper.map(entity, dictionary);
            dest.add(dictionary);
        }
        return dest;
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
}
