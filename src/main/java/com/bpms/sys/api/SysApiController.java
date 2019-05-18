package com.bpms.sys.api;

import com.bpms.core.dao.DummyDao;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.ext.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统平台API接口控制器类
 */
@Controller
@RequestMapping("/sys/api")
public class SysApiController {

    @Autowired
    private DummyDao dummyDao;

    /**
     * 获取指定大分类对应的数据字典列表
     *
     * @param mainCd 大分类编码
     * @return 指定大分类对应的数据字典列表JSON字符串
     */
    @ResponseBody
    @RequestMapping(value = "getDictionaryByCd", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getDictionaryByCd(@RequestParam(required = true) String mainCd) {
        try {
            //查询数据
            String sql = String.format("SELECT * FROM sys_dictionary WHERE main_cd = '%s' ", mainCd);

            List<DictionaryExt> dictList = this.dummyDao.search(DictionaryExt.class, sql);
            //获取数据失败
            if (dictList == null) {
                return JsonUtils.toJSON(AjaxResult.createFailResult("指定数据不存在，请确认传入的大分类编码是否正确。"));
            }

            //获取数据成功
            AjaxResult ajaxResult = AjaxResult.createSuccessResult();
            ajaxResult.setContent(dictList);

            return JsonUtils.toJSON(ajaxResult);
        } catch (Exception e) {
            throw new ServiceException("字典数据获取失败，请与系统管理员联系。");
        }
    }

    /**
     * 获取指定表中的数据
     *
     * @param tableName    表名
     * @param recordStatus 记录状态
     * @return 实体集合JSON字符串
     */
    @ResponseBody
    @RequestMapping(value = "getTableList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public <E> String getTableList(@RequestParam(required = true) String tableName, @RequestParam(required = true, defaultValue = "") String recordStatus) {
        try {
            List<E> list = this.searchTableList(tableName, recordStatus);
            //获取数据失败
            if (list == null) {
                return JsonUtils.toJSON(AjaxResult.createFailResult("非法请求，请与系统管理员联系。"));
            }

            //获取数据成功
            AjaxResult ajaxResult = AjaxResult.createSuccessResult();
            ajaxResult.setContent(list);

            return JsonUtils.toJSON(ajaxResult);
        } catch (Exception e) {
            throw new ServiceException("数据获取失败，请与系统管理员联系。");
        }
    }

    /**
     * 查询表数据
     *
     * @param tableName    表名
     * @param recordStatus 记录状态
     * @return 实体集合
     */
    private <E> List<E> searchTableList(String tableName, String recordStatus) {
        Class cls;
        //部门
        if (StringUtils.equalsIgnoreCase("sys_dept", tableName)) {
            cls = DeptExt.class;
        }
        //职位
        else if (StringUtils.equalsIgnoreCase("sys_position", tableName)) {
            cls = PositionExt.class;
        }
        //用户
        else if (StringUtils.equalsIgnoreCase("sys_user", tableName)) {
            cls = UserExt.class;
        }
        //数据字典
        else if (StringUtils.equalsIgnoreCase("sys_dictionary", tableName)) {
            cls = DictionaryExt.class;
        }
        //系统角色
        else if (StringUtils.equalsIgnoreCase("sys_role", tableName)) {
            cls = RoleExt.class;
        }
        //用户角色
        else if (StringUtils.equalsIgnoreCase("sys_user_role", tableName)) {
            cls = UserRoleExt.class;
        }
        //系统表
        else if (StringUtils.equalsIgnoreCase("sys_application", tableName)) {
            cls = ApplicationExt.class;
        }
        else {
            return null;
        }
        //查询数据
        String sql = String.format("SELECT * FROM %s ", tableName.toLowerCase());
        if (StringUtils.isNotEmpty(recordStatus)) {
            sql += "WHERE record_status = " + recordStatus;
        }

        return this.dummyDao.search(cls, sql);
    }
}