package com.bpms.sys.controller;

import com.bpms.sys.entity.ext.PositionExt;
import com.bpms.sys.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * 职位控制器类
 */
@Controller
@RequestMapping("/sys/position")
public class PositionController extends SysBaseController<PositionExt> {
    /**
     * Service对象
     */
    @Autowired
    private PositionService positionService;

    /**
     * 取得Service对象
     */
    @Override
    public PositionService getService() {
        return positionService;
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
    }
}
