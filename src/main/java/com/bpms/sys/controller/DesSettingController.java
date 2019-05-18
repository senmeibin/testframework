package com.bpms.sys.controller;

import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.DesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 字符串加密/解密控制器类
 */
@Controller
@RequestMapping("/sys/dessetting")
public class DesSettingController {
    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException, ServiceException {
        return "/sys/dessetting/DesSetting";
    }

    /**
     * 加密/解密操作
     *
     * @param type       01 加密  02 解密
     * @param encryptStr 加密字符串
     * @param decryptStr 解密字符串
     * @return 加密结果
     */
    @RequestMapping(value = "encryptOrDecryptOperation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult encryptOrDecryptOperation(@RequestParam(required = true) String type, @RequestParam(required = false) String decryptStr, @RequestParam(required = false) String encryptStr) {
        if (!StringUtils.isBlank(type)) {
            if (type.equals("01")) {
                return AjaxResult.createSuccessResult("加密成功。", DesUtils.encrypt(encryptStr));
            }
            else if (type.equals("02")) {
                return AjaxResult.createSuccessResult("解密成功。", DesUtils.decrypt(decryptStr));
            }
        }
        return AjaxResult.createFailResult("非法调用。");
    }
}