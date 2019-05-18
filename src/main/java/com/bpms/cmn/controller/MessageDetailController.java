package com.bpms.cmn.controller;

import com.bpms.cmn.entity.ext.MessageDetailExt;
import com.bpms.cmn.service.MessageDetailService;
import com.bpms.cmn.service.MessageService;
import com.bpms.core.entity.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息推送管理明细表控制器类
 */
@Controller(value = "CmnMessageDetailController")
@RequestMapping("/cmn/messagedetail")
public class MessageDetailController extends CmnBaseController<MessageDetailExt> {
    /**
     * Service对象
     */
    @Autowired
    private MessageDetailService messageDetailService;

    @Autowired
    private MessageService messageService;

    /**
     * 取得Service对象
     */
    @Override
    public MessageDetailService getService() {
        return messageDetailService;
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
        //是否已读
        dicMap.put("readTypeCd", this.getDictionaryList("CMN003"));
        //重要度
        dicMap.put("importanceDegreeCd", this.getDictionaryList("CMN002"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 数据已读处理
     *
     * @param uids 消息推送管理明细表UID集合
     * @return AjaxResult
     */
    @RequestMapping(value = "setMessageReaded", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult setMessageReaded(@RequestParam String uids) {
        // 已读处理
        this.messageDetailService.setMessageReaded(uids);

        return AjaxResult.createSuccessResult(uids, "操作成功。", this.messageService.getUnreadCount());
    }
}
