package com.bpms.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.demo.entity.ext.TutorExt;
import com.bpms.demo.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导师表控制器类
 */
@Controller
@RequestMapping("/demo/tutor")
public class TutorController extends DemoBaseController<TutorExt> {
    /**
     * Service对象
     */
    @Autowired
    private TutorService tutorService;

    /**
     * 取得Service对象
     */
    @Override
    public TutorService getService() {
        return tutorService;
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
        //基地信息
        dicMap.put("baseUid", this.getDropdownList("demo_base_info", "uid", "base_name"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 指定SQL的检索条件分页排序查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @param pager     分页排序对象
     * @return 一览数据集
     */
    @Override
    public Page<TutorExt> search(String sql, Map<String, Object> condition, PageRequest pager) {
        return super.search(sql, condition, pager);
    }

    /**
     * 数据导出
     *
     * @param conditionJson 查询条件JSON字符串
     * @param colList       列信息JSON字符串
     * @param fileName      文件名称
     * @param extensionName 文件扩展名称
     * @return 字节流
     */
    @Override
    @RequestMapping(value = "export", method = RequestMethod.POST, name = "导出")
    public ResponseEntity<byte[]> export(@RequestParam String conditionJson, @RequestParam String colList, @RequestParam String fileName, @RequestParam(defaultValue = "xlsx") String extensionName) {
        try {
            //取得查询条件
            Map<String, Object> condition = this.getCondition(conditionJson);

            //取得分页条件
            PageRequest pageRequest = this.getPageRequest(this.getPager(this.getSessionSearchPager()));

            //取得标准查询SQL文
            String sql = getService().getSearchSQL(null);

            List<TutorExt> list = getService().search(getEntityClass(), sql, condition, pageRequest.getSort());

            //将联系方式最后四位隐藏
            if (CollectionUtils.isNotEmpty(list)) {
                for (TutorExt entity : list) {
                    this.hiddenContactInfoSuffix(entity);
                }
            }

            return this.exportExcel(list, colList, fileName, extensionName);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 将联系方式最后四位隐藏
     *
     * @param tutorExt 实体对象
     */
    private void hiddenContactInfoSuffix(TutorExt tutorExt) {
        if (StringUtils.isNotEmpty(tutorExt.getContactInfo()) && tutorExt.getContactInfo().length() > 4) {
            tutorExt.setContactInfo(StringUtils.substring(tutorExt.getContactInfo(), 0, tutorExt.getContactInfo().length() - 4) + "****");
        }
    }

    /**
     * 查询导师列表数据
     *
     * @param baseUid 所属基地UID
     * @return 查询导师列表JSON字符串
     */
    @RequestMapping(value = "getTutorTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TutorExt> getTutorTree(@RequestParam(required = false) String baseUid) throws JsonProcessingException {
        return this.tutorService.getTutorTree(baseUid);
    }
}