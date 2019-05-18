package com.bpms.sys.controller;

import com.bpms.core.exception.ServiceException;
import com.bpms.sys.entity.ext.DatabaseProcessOperationLogExt;
import com.bpms.sys.service.DatabaseProcessOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库进程操作日志控制器类
 */
@Controller
@RequestMapping("/sys/databaseprocessoperationlog")
public class DatabaseProcessOperationLogController extends SysBaseController<DatabaseProcessOperationLogExt> {
    /**
     * Service对象
     */
    @Autowired
    private DatabaseProcessOperationLogService databaseProcessOperationLogService;

    /**
     * 取得Service对象
     */
    @Override
    public DatabaseProcessOperationLogService getService() {
        return databaseProcessOperationLogService;
    }

    @Override
    public String getListJsp() {
        return "sys/databaseprocessoperationlog/DatabaseManager";
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

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 查询数据库实时进程信息
     *
     * @param pagerJson     页面JSON
     * @param conditionJson 条件
     * @return 处理中进程实体一览
     * @throws IOException
     * @throws IllegalAccessException
     * @throws ServiceException
     */
    @ResponseBody
    @RequestMapping(value = "/searchProcessInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "查询")
    public Page<DatabaseProcessOperationLogExt> searchProcessInfo(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        // 取得查询条件
        Map<String, Object> condition = this.getCondition(conditionJson);
        // 保存最新的检索条件
        this.setSessionSearchCondition(conditionJson);
        this.setSessionSearchPager(pagerJson);
        // 查询sql
        String sql = this.getService().getSQL("/sys/DatabaseProcessOperationLog/searchProcessInfo");

        // 数据查询
        return this.databaseProcessOperationLogService.search(DatabaseProcessOperationLogExt.class, sql, condition, this.getPageRequest(this.getPager(pagerJson)));
    }

    /**
     * kill 数据库进程
     *
     * @param inputJson Json字符串
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "/killProcess", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "保存")
    public Object killProcess(@RequestParam String inputJson) {
        //输入画面InputJson字符串解析成BaseEntity对象
        DatabaseProcessOperationLogExt entity = (DatabaseProcessOperationLogExt) this.parseInputJson(inputJson);

        //数据库进程操作日志数据保存处理
        getService().save(entity);

        return this.setSaveResult(entity);
    }

    /**
     * 取得默认排序字段[子类可覆盖]
     *
     * @return 默认排序字段
     */
    @Override
    public Sort getDefaultSort() {
        // 默认按UID排序
        return new Sort(Sort.Direction.DESC, "execute_wait_time");
    }
}
