package com.bpms.sys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.core.cache.RedisCacheManager;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.sql.SqlReader;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.service.DictionaryService;
import com.bpms.sys.service.ParameterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * 缓存控制器类
 */
@Controller
@RequestMapping("/sys/cache")
public class RedisController {
    @Autowired
    private RedisCacheManager redisCacheManager;

    /**
     * redis缓存管理画面初期化
     *
     * @param model Model对象
     * @return redis缓存管理画面
     */
    @RequestMapping(value = "redisCacheInput", method = RequestMethod.GET)
    public String redisCacheInput(Model model) {
        Long redisKeyCount = redisCacheManager.dbSize();
        model.addAttribute("redisKeyCount", redisKeyCount);
        if (redisKeyCount == null) {
            model.addAttribute("redisKeyListHtml", "Redis服务暂未启用，请与系统管理员联系。");
            model.addAttribute("redisKeyCount", 0);
        }
        else if (redisKeyCount <= 1000) {
            Set<String> redisKeyList = redisCacheManager.keys(null);
            String redisKeyListHtml = StringUtils.EMPTY;
            for (Object key : redisKeyList) {
                redisKeyListHtml += key + "<br/>";
            }
            model.addAttribute("redisKeyListHtml", redisKeyListHtml);
        }
        return "sys/cache/RedisCacheInput";
    }

    /**
     * 清除redis缓存key
     *
     * @param deleteType 删除类型
     * @param redisKey   删除的key内容
     * @param isLike     是否需要模糊匹配（1：要， 其他：不要）
     * @return redis缓存管理删除结果消息
     */
    @RequestMapping(value = "redisCacheDelete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult redisCacheDelete(@RequestParam String deleteType, @RequestParam(required = false) String redisKey, @RequestParam(required = false) String isLike) {
        AjaxResult ajaxResult = new AjaxResult();
        long deleteCount = 0;
        String infoMessage = "";
        //清除参数缓存
        if (StringUtils.equals("1", deleteType)) {
            infoMessage = "已清除系统参数的缓存.";
            //取出所有前缀为parameter key的内容
            Set<String> keySet = redisCacheManager.keys(ParameterService.CACHE_PREFIX_KEY + "*");
            //删除相应的key
            redisCacheManager.delete(keySet);
            deleteCount = keySet.size();
        }
        //清除字典缓存
        else if (StringUtils.equals("2", deleteType)) {
            infoMessage = "已清除系统数据字典的缓存。";
            //取出所有前缀为数据字典 key的内容
            Set<String> keySet = redisCacheManager.keys(DictionaryService.CACHE_PREFIX_KEY + "*");
            //删除相应的key
            redisCacheManager.delete(keySet);
            deleteCount = keySet.size();
        }
        //清除DSQL缓存
        else if (StringUtils.equals("3", deleteType)) {
            infoMessage = "已清除DSQL缓存。";
            //取出所有前缀为DSQL key的内容
            Set<String> keySet = redisCacheManager.keys(SqlReader.CACHE_PREFIX_KEY + "*");
            //删除相应的key
            redisCacheManager.delete(keySet);
            deleteCount = keySet.size();
        }
        //清除指定缓存
        else if (StringUtils.equals("4", deleteType)) {
            infoMessage = "已清除指定RedisKey的缓存。";
            //模糊匹配时
            if (StringUtils.equals("1", isLike)) {
                //取出所有前缀为指定redis key的内容
                Set<String> keySet = redisCacheManager.keys(redisKey + "*");
                //删除相应的key
                redisCacheManager.delete(keySet);
                deleteCount = keySet.size();
            }
            //精准匹配时
            else {
                //删除相应的key
                redisCacheManager.delete(redisKey);
                deleteCount = 1;
            }
        }
        //清除全部
        else if (StringUtils.equals("5", deleteType)) {
            infoMessage = "已清除系统中的所有缓存。";
            //删除redis服务器上所有的本系统的key
            deleteCount = redisCacheManager.deleteAll();
        }
        ajaxResult.setContent(infoMessage + "（清除件数：" + deleteCount + " 件)");
        return ajaxResult;
    }

    /**
     * 根据key读取redis缓存数据
     *
     * @param redisKey 读取的key内容
     * @return redis缓存数据结果
     */
    @RequestMapping(value = "redisCacheRead", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult redisCacheRead(@RequestParam String redisKey) {
        AjaxResult ajaxResult = new AjaxResult();
        long startTime = System.currentTimeMillis();
        //获取缓存数据
        Object redisObject = redisCacheManager.get(String.class, redisKey);
        long spendingTime = System.currentTimeMillis() - startTime;
        //未读到数据
        if (redisObject == null) {
            ajaxResult.setResult(-1);
            ajaxResult.setMessage("指定RedisKey的缓存数据不存在。");
        }
        //读取到后转换为JSON
        else {
            try {
                ajaxResult.setResult(1);
                ajaxResult.setContent(String.format("耗时：%s毫秒\r\n<br/>数据：%s", spendingTime, JsonUtils.toJSON(redisObject)));
            } catch (JsonProcessingException e) {
                ajaxResult.setResult(-1);
                ajaxResult.setMessage("转换JSON出错。");
            }
        }
        return ajaxResult;
    }
}
