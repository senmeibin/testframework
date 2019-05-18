package com.bpms.sys.controller;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.MailConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.MailAccount;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 邮箱设定控制器
 */
@Controller
@RequestMapping("/sys/mailaccount")
public class MailAccountController extends ParameterController {
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
        //设置数据输入画面全局标识变量
        this.setInputPage(true);
        this.setListPage(false);
        // 初期化编辑画面
        this.initInputPage(model, uid);
        // 返回数据输入JSP页面
        return this.getRequestMappingPath() + "/MailAccountInput";
    }

    /**
     * 初期化编辑画面
     */
    @Override
    public void initInputPage(Model model, String uid) throws IllegalAccessException, IOException, InstantiationException {
        //获取stmp服务器
        String smtp = this.parameterService.getValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_SMTP);
        //获取端口
        String port = this.parameterService.getValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_PORT);
        //获取发件人
        String sendUser = this.parameterService.getValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_SEND_USER);
        //获取用户名
        String userName = this.parameterService.getValue(AppCodeConsts.APP_COMMON, MailConsts.MAIL_USER_ACCOUNT);

        MailAccount mailAccount = new MailAccount();
        mailAccount.setSmtp(smtp);
        mailAccount.setPort(port);
        mailAccount.setSendUser(sendUser);
        mailAccount.setUserName(userName);
        // 设置数据模型JSON字符
        model.addAttribute("jsonInputEntity_MailAccount", this.toJSON(mailAccount, true));
    }

    /**
     * 数据保存处理
     *
     * @param inputJson Json字符串
     * @return 处理结果
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "/saveMailAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object save(@RequestParam String inputJson) {
        //输入画面InputJson字符串解析成BaseEntity对象
        MailAccount mailAccount = (MailAccount) JsonUtils.parseJSON(inputJson, MailAccount.class);
        ;
        this.parameterService.setMailAccount(mailAccount);
        return AjaxResult.createSuccessResult("数据保存成功。");
    }
}
