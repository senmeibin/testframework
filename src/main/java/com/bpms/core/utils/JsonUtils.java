package com.bpms.core.utils;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.bpms.core.JsonMapper;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class JsonUtils {
    /**
     * 字符串解析成对应的对象
     *
     * @param inputJson 需要解析的json字符串
     * @param t         泛型对象的class
     * @param <T>       泛型对象
     * @return 泛型对象
     */
    public static <T> T parseJSON(String inputJson, Class<T> t) {
        if (StringUtils.isNotEmpty(inputJson) && StringUtils.equals(t.getName(), AjaxResult.class.getName())) {
            if (inputJson.indexOf("SYSTEM_401_FLAG") >= 0) {
                AjaxResult ajaxResult = AjaxResult.createFailResult("401-没有访问权限或操作权限。");
                return (T) ajaxResult;
            }
            else if (inputJson.indexOf("SYSTEM_404_FLAG") >= 0) {
                AjaxResult ajaxResult = AjaxResult.createFailResult("404-您所访问的页面不存在。");
                return (T) ajaxResult;
            }
            else if (inputJson.indexOf("SYSTEM_500_FLAG") >= 0) {
                AjaxResult ajaxResult = AjaxResult.createFailResult("500-发生系统错误，请与系统管理员联系。");
                return (T) ajaxResult;
            }
            else if (inputJson.indexOf("SYSTEM_ERROR_FLAG") >= 0) {
                AjaxResult ajaxResult = AjaxResult.createFailResult("发生系统错误，请与系统管理员联系。");
                return (T) ajaxResult;
            }
            else if (inputJson.indexOf("<!DOCTYPE html>") >= 0) {
                AjaxResult ajaxResult = AjaxResult.createFailResult("未知错误，服务不可用。");
                return (T) ajaxResult;
            }
        }

        JsonMapper jsonMapper = new JsonMapper();
        try {
            return jsonMapper.readValue(inputJson, t);
        }
        catch (IOException e) {
            throw new ServiceException("非法数据提交:" + e.getMessage());
        }
    }

    /**
     * @param inputJson    需要解析的json字符串
     * @param valueTypeRef 引用类型
     * @param <T>          泛型对象
     * @return 泛型对象
     */
    public static <T> T parseJSON(String inputJson, TypeReference valueTypeRef) {
        if (StringUtils.isEmpty(inputJson)) {
            return null;
        }
        JsonMapper jsonMapper = new JsonMapper();
        try {
            return jsonMapper.readValue(inputJson, valueTypeRef);
        }
        catch (IOException e) {
            throw new ServiceException("非法数据提交:" + e.getMessage());
        }
    }

    /**
     * 对象->Json字符串转化
     *
     * @param obj 对象实例
     * @return Json字符串
     * @throws JsonProcessingException
     */
    public static String toJSON(Object obj) throws JsonProcessingException {
        return toJSON(obj, true);
    }

    /**
     * 对象->Json字符串转化
     *
     * @param obj           对象实例
     * @param isIncludeNull json 字符串 是否包含null和空 ； true:包含；false:不包含
     * @return Json字符串
     * @throws JsonProcessingException
     */
    public static String toJSON(Object obj, boolean isIncludeNull) throws JsonProcessingException {
        JsonMapper jsonMapper = new JsonMapper();
        if (!isIncludeNull) {
            //不包含
            jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        }
        return jsonMapper.writeValueAsString(obj);
    }

    /**
     * 不附带自定义序列化转换json
     *
     * @param obj 对象实例
     * @return Json字符串
     */
    public static String toJSONWithNoCustomerSerialization(Object obj) throws IOException {
        String json = JSON.toJSONString(obj);
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return jsonMapper.writeValueAsString(jsonMapper.readTree(json));
    }
}
