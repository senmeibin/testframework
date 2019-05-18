package com.bpms.crm.controller;

import com.bpms.core.entity.AjaxResult;
import com.bpms.crm.entity.ext.StudentAssignLogExt;
import com.bpms.crm.service.StudentAssignLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
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
 * 学员分配日志控制器类
 */
@Controller
@RequestMapping("/crm/studentassignlog")
public class StudentAssignLogController extends CrmBaseController<StudentAssignLogExt> {
    /**
     * Service对象
     */
    @Autowired
    private StudentAssignLogService studentAssignLogService;

    /**
     * 取得Service对象
     */
    @Override
    public StudentAssignLogService getService() {
        return studentAssignLogService;
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
        //划转类型
        dicMap.put("assignTypeCd", this.getDictionaryList("CRM019"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 根据学员UID获取划转记录【TS使用】
     *
     * @param studentUid 学员UID
     * @return 划转记录
     */
    @ResponseBody
    @RequestMapping(value = "getStudentAssignLogList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult getStudentAssignLogList(@RequestParam String studentUid) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("main.student_uid", studentUid);

        if (StringUtils.isEmpty(studentUid)) {
            return AjaxResult.createFailResult("非法访问，学员UID不能为空。");
        }

        List<StudentAssignLogExt> list = this.studentAssignLogService.search(conditionMap, new Sort(Sort.Direction.DESC, "main.insert_date"));
        //最多返回最近10条划转记录
        return AjaxResult.createSuccessResult("获取划转记录成功", list);
    }
}
