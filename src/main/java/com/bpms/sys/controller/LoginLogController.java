package com.bpms.sys.controller;

import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.entity.ext.LoginLogExt;
import com.bpms.sys.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.Objects;

/**
 * 登录日志控制器类
 */
@Controller
@RequestMapping("/sys/loginlog")
public class LoginLogController extends SysBaseController<LoginLogExt> {
    /**
     * Service对象
     */
    @Autowired
    private LoginLogService loginLogService;

    /**
     * 取得Service对象
     */
    @Override
    public LoginLogService getService() {
        return loginLogService;
    }

    /**
     * 取得默认排序字段[子类可覆盖]
     *
     * @return 默认排序字段
     */
    @Override
    public Sort getDefaultSort() {
        // 默认按登录日期排序
        return new Sort(Sort.Direction.DESC, "insertDate");
    }

    /**
     * 登录统计
     */
    @RequestMapping(value = {"report"}, method = RequestMethod.GET)
    public String reportList(Model model) {
        this.initCustomSetting(model, "SysApp.Sys.LoginLogReportIns");
        return "sys/loginlog/LoginLogReport";
    }

    /**
     * 重写导出方法
     *
     * @param conditionJson 查询条件JSON字符串
     * @param colList       列信息JSON字符串
     * @param fileName      文件名称
     * @param extensionName 文件扩展名称
     * @return
     */
    @Override
    public ResponseEntity<byte[]> export(String conditionJson, String colList, String fileName, @RequestParam(defaultValue = "xlsx") String extensionName) {

        //取得查询条件
        Map<String, Object> condition = this.getCondition(conditionJson);

        //取得标准查询SQL文
        String sql = getService().getSQL("sys/LoginLog/searchLoginCountByDay");

        //日期范围参数
        condition.put(":beginDate", SearchConditionUtils.getConditionValue(condition, "beginDate"));
        condition.put(":endDate", SearchConditionUtils.getConditionValue(condition, "endDate"));

        //数据查询
        List<LoginLogExt> searchResult = this.search(sql, condition);

        try {
            return this.exportExcel(searchResult, colList, fileName, extensionName);
        } catch (Exception e) {
            throw new ServiceException("导出异常");
        }
    }

    /**
     * 按天统计系统用户登录次数
     *
     * @param pagerJson     分页JSON字符串
     * @param conditionJson 查询条件JSON字符串
     * @return 一览数据集
     * @throws IOException
     * @throws IllegalAccessException
     */
    @ResponseBody
    @RequestMapping(value = "/reportSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "检索")
    public Page<LoginLogExt> reportSearch(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        //取得查询条件
        Map<String, Object> condition = this.getCondition(conditionJson);

        //日期范围参数
        condition.put(":beginDate", SearchConditionUtils.getConditionValue(condition, "beginDate"));
        condition.put(":endDate", SearchConditionUtils.getConditionValue(condition, "endDate"));

        //保存最新的检索条件
        this.setSessionSearchCondition(conditionJson);
        this.setSessionSearchPager(pagerJson);

        Map<String, String> pager = getPager(pagerJson);
        if (StringUtils.isEmpty(Objects.toString(pager.get("sortColumn")))) {
            pager.put("sortColumn", "login_days");
            pager.put("sortMode", "desc");
        }
        //取得标准查询SQL文
        String sql = getService().getSQL("sys/LoginLog/searchLoginCountByDay");

        //数据查询
        return this.search(sql, condition, this.getPageRequest(pager));
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
        //登录类型
        dicMap.put("loginType", this.getDictionaryList("SYS002"));
        //登录结果
        dicMap.put("loginResult", this.getDictionaryList("SYS005"));

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
