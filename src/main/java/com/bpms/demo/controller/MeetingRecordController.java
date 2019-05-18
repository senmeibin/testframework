package com.bpms.demo.controller;

import com.bpms.core.utils.UniqueUtils;
import com.bpms.demo.entity.ext.MeetingRecordExt;
import com.bpms.demo.service.MeetingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 会议记录控制器类
 */
@Controller
@RequestMapping("/demo/meetingrecord")
public class MeetingRecordController extends DemoBaseController<MeetingRecordExt> {
    /**
     * Service对象
     */
    @Autowired
    private MeetingRecordService meetingRecordService;

    /**
     * 取得Service对象
     */
    @Override
    public MeetingRecordService getService() {
        return meetingRecordService;
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
     * 初始化InputEntity实体对象[后处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    @Override
    protected void initInputEntityAfter(Model model, String uid) {
        MeetingRecordExt entity = this.getInputEntity();
        if (this.isAddMode()) {
            entity.setUid(UniqueUtils.getUID());
            //记录人
            entity.setRecorderUid(this.getCurrentUserId());
            entity.setRecorderName(this.getCurrentUser().getUserName());
        }
        model.addAttribute("meetingRecordUid", this.getInputEntity().getUid());
    }
}
