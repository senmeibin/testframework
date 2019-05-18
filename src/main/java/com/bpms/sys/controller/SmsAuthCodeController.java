package com.bpms.sys.controller;

import com.bpms.core.controller.BaseController;
import com.bpms.sys.entity.ext.SmsAuthCodeExt;
import com.bpms.sys.service.SmsAuthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信验证码控制器类
 */
@Controller
@RequestMapping("/sys/smsauthcode")
public class SmsAuthCodeController extends BaseController<SmsAuthCodeExt> {
    /**
     * Service对象
     */
    @Autowired
    private SmsAuthCodeService smsAuthCodeService;

    /**
     * 取得Service对象
     */
    @Override
    public SmsAuthCodeService getService() {
        return smsAuthCodeService;
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

        //验证码类型
        dicMap.put("authCodeType", this.getDictionaryList("SYS016"));

        //是否已验证
        dicMap.put("validated", this.getDictionaryList("SYS017"));
        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
