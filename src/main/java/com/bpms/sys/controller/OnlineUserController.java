package com.bpms.sys.controller;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.DateUtils;
import com.bpms.sys.entity.ext.OnlineUserExt;
import com.bpms.sys.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
 * 在线用户控制器类
 */
@Controller
@RequestMapping("/sys/onlineuser")
public class OnlineUserController extends SysBaseController<OnlineUserExt> {
    /**
     * Service对象
     */
    @Autowired
    private OnlineUserService onlineUserService;

    /**
     * 取得Service对象
     */
    @Override
    public OnlineUserService getService() {
        return onlineUserService;
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
     * 强制退出处理
     *
     * @param uid 用户UID(逗号分隔)
     * @return AjaxResult
     */
    @RequestMapping(value = "forceLogout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult forceLogout(@RequestParam String uid) {
        // 强制退出处理
        this.onlineUserService.forceLogout(uid);

        return AjaxResult.createSuccessResult("强制退出处理成功。");
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
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Override
    public Page<OnlineUserExt> search(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        // 取得查询条件
        Map<String, Object> condition = this.getCondition(conditionJson);

        // 会话超时时间
        int sessionTimeout = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "SESSION_TIMOUT", 30);

        // 只取有效的会话
        condition.put("main.updateDate$>=", DateUtils.format(DateUtils.addMinutes(DateUtils.getNowDate(), (0 - sessionTimeout)), CmnConsts.DATE_TIME_FORMAT));

        // 取得标准查询SQL文
        String sql = getService().getSearchSQL(null);

        // 数据查询
        return this.search(sql, condition, this.getPageRequest(this.getPager(pagerJson)));
    }
}
