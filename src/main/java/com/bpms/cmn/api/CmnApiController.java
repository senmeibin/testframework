package com.bpms.cmn.api;

import com.bpms.cmn.entity.CmnBaseEntity;
import com.bpms.cmn.entity.ext.*;
import com.bpms.cmn.service.*;
import com.bpms.core.dao.DummyDao;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 共通管理API接口控制器类
 */
@Controller
@RequestMapping("/cmn/api")
public class CmnApiController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private MainCustomerService mainCustomerService;

    @Autowired
    private SignCompanyService signCompanyService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CityService cityService;

    @Autowired
    private DummyDao dummyDao;

    /**
     * 根据UID获取指定表中的明细数据
     *
     * @param tableName 表名
     * @param uid       表主键UID
     * @return 实体JSON字符串
     */
    @ResponseBody
    @RequestMapping(value = "getTableRow", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getTableRow(@RequestParam(required = true) String tableName, @RequestParam(required = true) String uid) {
        try {
            //获取数据成功
            CmnBaseEntity resultEntity;
            //主客户
            if (StringUtils.equalsIgnoreCase("cmn_main_customer", tableName)) {
                resultEntity = mainCustomerService.getDetail(uid);
            }
            //合同客户
            else if (StringUtils.equalsIgnoreCase("cmn_customer", tableName)) {
                resultEntity = customerService.getDetail(uid);
            }
            //签单公司
            else if (StringUtils.equalsIgnoreCase("cmn_sign_company", tableName)) {
                resultEntity = signCompanyService.getDetail(uid);
            }
            //大区
            else if (StringUtils.equalsIgnoreCase("cmn_area", tableName)) {
                resultEntity = areaService.getDetail(uid);
            }
            //城市
            else if (StringUtils.equalsIgnoreCase("cmn_city", tableName)) {
                resultEntity = cityService.getDetail(uid);
            }
            else {
                return JsonUtils.toJSON(AjaxResult.createFailResult("非法请求，请与系统管理员联系。"));
            }

            //获取数据失败
            if (resultEntity == null) {
                return JsonUtils.toJSON(AjaxResult.createFailResult("数据获取失败，无法获取指定记录。"));
            }

            //获取数据成功
            return JsonUtils.toJSON(AjaxResult.createSuccessResult("数据获取成功", JsonUtils.toJSON(resultEntity)));
        } catch (Exception e) {
            throw new ServiceException("数据获取失败，请与系统管理员联系。");
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
        //主客户
        if (StringUtils.equalsIgnoreCase("cmn_main_customer", tableName)) {
            cls = MainCustomerExt.class;
        }
        //合同客户
        else if (StringUtils.equalsIgnoreCase("cmn_customer", tableName)) {
            cls = CustomerExt.class;
        }
        //签单公司
        else if (StringUtils.equalsIgnoreCase("cmn_sign_company", tableName)) {
            cls = SignCompanyExt.class;
        }
        //大区
        else if (StringUtils.equalsIgnoreCase("cmn_area", tableName)) {
            cls = AreaExt.class;
        }
        //城市
        else if (StringUtils.equalsIgnoreCase("cmn_city", tableName)) {
            cls = CityExt.class;
        }
        //地区信息
        else if (StringUtils.equalsIgnoreCase("cmn_region", tableName)) {
            cls = RegionExt.class;
        }
        else {
            return null;
        }
        //查询数据
        String sql = String.format("SELECT * FROM %s ", tableName.toLowerCase());
        if (StringUtils.isNotEmpty(recordStatus)) {
            sql += "WHERE record_status = " + recordStatus;
        }

        return dummyDao.search(cls, sql);
    }
}