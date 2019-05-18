package com.bpms.cmn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.ext.CustomSettingExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 自定义设置控制器类
 */
@Controller(value = "CmnCustomSettingController")
@RequestMapping("/cmn/customsetting")
public class CustomSettingController extends com.bpms.sys.controller.CustomSettingController {
    /**
     * 根据pageInstance 画面标识、settingType 设置类型查询实体详情
     *
     * @param pageInstance 画面标识
     * @param settingType  设置类型
     * @return 实体对象JSON字符串
     */
    @RequestMapping(value = "/getHelpDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getHelpDetail(@RequestParam String pageInstance, @RequestParam Integer settingType) throws JsonProcessingException {
        List<CustomSettingExt> list = getService().getCustomSetting(pageInstance, settingType, null);
        if (CollectionUtils.isNotEmpty(list)) {
            return JsonUtils.toJSON(list.get(0));
        }
        return StringUtils.EMPTY;
    }
}
