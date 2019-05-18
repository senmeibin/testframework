package com.bpms.sys.controller;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.SmsConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.ext.SmsAccount;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 短信设定控制器
 */
@Controller
@RequestMapping("/sys/smsaccount")
public class SmsAccountController extends ParameterController {
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
        return this.getRequestMappingPath() + "/SmsAccountInput";
    }

    // 初期化编辑画面
    @Override
    public void initInputPage(Model model, String uid) throws IllegalAccessException, IOException, InstantiationException {
        //获取供应商
        String vendor = this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_VENDOR);
        //获取短信URL
        String url = this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_URL);
        //获取短信账号
        String account = this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_ACCOUNT);

        SmsAccount smsAccount = new SmsAccount();
        smsAccount.setVendor(vendor);
        smsAccount.setUrl(url);
        smsAccount.setAccount(account);
        // 设置数据模型JSON字符
        model.addAttribute("jsonInputEntity_SmsAccount", this.toJSON(smsAccount, true));
    }

    /**
     * 数据保存处理
     *
     * @param inputJson Json字符串
     * @return 处理结果
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "/saveSmsAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object save(@RequestParam String inputJson) {
        //输入画面InputJson字符串解析成BaseEntity对象
        SmsAccount smsAccount = (SmsAccount) JsonUtils.parseJSON(inputJson, SmsAccount.class);
        ;
        this.parameterService.setSmsAccount(smsAccount);
        return AjaxResult.createSuccessResult("数据保存成功。");
    }
}
