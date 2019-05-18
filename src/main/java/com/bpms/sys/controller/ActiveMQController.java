package com.bpms.sys.controller;

import com.bpms.activemq.consts.JMSConsts;
import com.bpms.activemq.service.ActiveMQSenderService;
import com.bpms.core.entity.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Calendar;

/**
 * activemq管理类
 */
@Controller
@RequestMapping("/sys/activemq")
public class ActiveMQController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActiveMQSenderService senderService;

    /**
     * activemq画面初期化
     *
     * @param model Model对象
     * @return activemq管理画面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @RequestMapping(value = "input", method = RequestMethod.GET)
    public String input(Model model) throws IllegalAccessException, IOException, InstantiationException {
        model.addAttribute("activemqEnable", senderService.activeMQEnabled());
        return "sys/activemq/ActiveMQInput";
    }

    /**
     * 消息推送
     *
     * @param sendType   发送消息的类型
     * @param jsonObject 消息json数据
     * @return AjaxResult
     */
    @RequestMapping(value = "pushQueue", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult pushQueue(@RequestParam String sendType, @RequestParam String jsonObject) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            long startTime = Calendar.getInstance().getTimeInMillis();

            //发送邮件
            if (StringUtils.equals("mail", sendType)) {
                senderService.pushQueue(JMSConsts.JMS_QUEUE_NAME_EMAIL, jsonObject);
            }
            //发送短信
            else if (StringUtils.equals("sms", sendType)) {
                senderService.pushQueue(JMSConsts.JMS_QUEUE_NAME_SMS, jsonObject);
            }
            else {
                ajaxResult.setContent("请求异常，发送消息的类型错误。");
                return ajaxResult;
            }

            long endTime = Calendar.getInstance().getTimeInMillis();
            ajaxResult.setContent(String.format("推送成功(耗时：%s毫秒)，可在日志管理中查看发送的日志信息。", endTime - startTime));
            if (logger.isDebugEnabled()) {
                logger.debug("ActiveMQ队列请求时间：{}毫秒。", endTime - startTime);
            }
        } catch (UncategorizedJmsException ex) {
            ajaxResult.setContent("发送失败。ActiveMQ服务异常或未启动。");
        } catch (Exception ex) {
            ajaxResult.setContent(ex.getMessage());
        }
        return ajaxResult;
    }
}