package com.bpms.sys.controller;

import com.google.common.collect.Lists;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.service.BaseService;
import com.bpms.sys.entity.DurableTopicSubscribers;
import com.bpms.sys.service.SyncAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
 * 同步项目管理控制器类
 */
@Controller
@RequestMapping("/sys/syncapp")
public class SyncAppController extends SysBaseController<DurableTopicSubscribers> {

    @Autowired
    private SyncAppService syncAppService;

    /**
     * 取得Service对象
     */
    @Override
    public BaseService getService() {
        return null;
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

    @Override
    public void initListPage(Model model) throws IllegalAccessException, IOException, ServiceException {
        String conditionJson = this.getSessionSearchCondition();

        // 设置一览JSON数据
        model.addAttribute("jsonDataList_" + this.getEntityName(), this.toJSON(syncAppService.getDurableTopicSubscribersList(), true));

        // 上次检索条件[一览画面检索条件恢复用]
        model.addAttribute("jsonSearchCondition_" + this.getEntityName(), this.escapeHtml(conditionJson));
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
        Map<String, Object> dicMap = new HashMap<String, Object>();

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 一览数据标准分页排序查询
     *
     * @param pagerJson     分页JSON字符串
     * @param conditionJson 查询条件JSON字符串
     * @return 一览数据集
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Override
    public Page<DurableTopicSubscribers> search(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        List<DurableTopicSubscribers> entities = Lists.newArrayList();

        entities.addAll(syncAppService.getDurableTopicSubscribersList());

        Page<DurableTopicSubscribers> page = new PageImpl<>(entities, this.getPageRequest(this.getPager(pagerJson)), 1);

        return page;
    }

    /**
     * 数据保存处理
     *
     * @param inputJson Json字符串
     * @return 处理结果
     */
    @Override
    public Object save(@RequestParam String inputJson) {
        //输入画面InputJson字符串解析成BaseEntity对象
        DurableTopicSubscribers entity = (DurableTopicSubscribers) this.parseInputJson(inputJson);
        //创建持久订阅者
        return syncAppService.createDurableSubscriber(entity);
    }

    /**
     * 销毁持久订阅者
     *
     * @param clientId       订阅者UID
     * @param subscriberName 订阅者名称
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "/destroy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "销毁")
    public AjaxResult destroyDurableSubscriber(@RequestParam String clientId, @RequestParam String subscriberName) {
        return syncAppService.destroyDurableSubscriber(clientId, subscriberName);
    }

    /**
     * 取得实体类名[子类可覆盖]
     *
     * @return 实体类
     */
    @Override
    protected Class getEntityClass() {
        return DurableTopicSubscribers.class;
    }

    /**
     * 取得实体类名称[不带package路径]
     *
     * @return 实体类名称
     */
    @Override
    protected String getEntityName() {
        return "SyncApp";
    }
}
