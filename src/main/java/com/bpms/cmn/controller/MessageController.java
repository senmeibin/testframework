package com.bpms.cmn.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.cmn.entity.ext.MessageExt;
import com.bpms.cmn.service.MessageService;
import com.bpms.core.entity.AjaxResult;
import com.bpms.sys.entity.ext.UserExt;
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
import java.util.List;
import java.util.Map;

/**
 * 消息推送管理表控制器类
 */
@Controller(value = "CmnMessageController")
@RequestMapping("/cmn/message")
public class MessageController extends CmnBaseController<MessageExt> {
    /**
     * Service对象
     */
    @Autowired
    private MessageService messageService;

    /**
     * 取得Service对象
     */
    @Override
    public MessageService getService() {
        return messageService;
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
    @RequestMapping(value = "input", method = RequestMethod.GET)
    public String input(Model model, @RequestParam(required = false) String uid) throws IllegalAccessException, IOException, InstantiationException {
        this.setUseUEditor(true);
        String path = super.input(model, uid);
        this.getInputEntity().setSendUserUids(this.messageService.getSendSelectedUserUids(this.getInputEntity()));
        //重置Entity到Model中
        this.setInputEntityToModel(model);
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
        //消息类型
        dicMap.put("messageTypeCd", this.getDictionaryList("CMN001"));
        //重要度
        dicMap.put("importanceDegreeCd", this.getDictionaryList("CMN002"));
        //发送区分
        dicMap.put("sendTypeCd", this.getDictionaryList("CMN004"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
        //服务器时间
        model.addAttribute("serviceTime", System.currentTimeMillis());
    }

    /**
     * 取所有在职人员信息
     *
     * @return 在职人员列表JSON字符串
     */
    @RequestMapping(value = "getAllUserList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserExt> getAllUserList() throws JsonProcessingException {
        return this.messageService.getAllUserList();
    }


    /**
     * 获取未读件数
     *
     * @return 角色列表JSON字符串
     */
    @RequestMapping(value = "getUnreadCount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult getUnreadCount() throws JsonProcessingException {
        return AjaxResult.createSuccessResult("1", "未读件数获取成功。", this.messageService.getUnreadCount());
    }

    /**
     * 复制消息
     *
     * @return
     * @throws JsonParseException
     */
    @RequestMapping(value = "copyMessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult copyMessage(@RequestParam(required = true) String uid) throws JsonParseException {
        return this.messageService.copyMessage(uid);
    }
}