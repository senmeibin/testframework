package com.bpms.demo.controller;

import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.EmployeeExt;
import com.bpms.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 人员信息表控制器类
 */
@Controller
@RequestMapping("/demo/employee")
public class EmployeeController extends DemoBaseController<EmployeeExt> {
    /**
     * Service对象
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * 取得Service对象
     */
    @Override
    public EmployeeService getService() {
        return employeeService;
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
        //性别
        dicMap.put("sexCd", this.getDictionaryList("SYS004"));
        //民族
        dicMap.put("ethnicityCd", this.getDictionaryList("DEMO_028"));
        //政治面貌
        dicMap.put("policyCd", this.getDictionaryList("DEMO_029"));
        //学历
        dicMap.put("educationCd", this.getDictionaryList("DEMO_030"));
        //学位
        dicMap.put("educationalDegreeCd", this.getDictionaryList("DEMO_031"));
        //血型
        dicMap.put("bloodTypeCd", this.getDictionaryList("DEMO_032"));
        //婚姻状况
        dicMap.put("maritalStatusCd", this.getDictionaryList("DEMO_033"));
        //人员状态
        dicMap.put("statusCd", this.getDictionaryList("DEMO_034"));
        //基地信息
        dicMap.put("baseUid", this.getDropdownList("demo_base_info", "uid", "base_name"));
        //职位
        dicMap.put("positionUid", this.getDropdownList("sys_position", "uid", "position_name"));
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
        if (this.isAddMode()) {
            //主键
            this.getInputEntity().setUid(UniqueUtils.getUID());
        }
        model.addAttribute("employeeUid", this.getInputEntity().getUid());
    }
}
