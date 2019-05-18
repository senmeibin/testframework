package com.bpms.sys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bpms.cmn.service.CmnUserService;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.JsonUtils;
import com.bpms.sys.entity.ext.PermissionExt;
import com.bpms.sys.entity.ext.RoleExt;
import com.bpms.sys.service.PermissionService;
import com.bpms.sys.service.RoleService;
import com.bpms.sys.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器类
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends SysBaseController<RoleExt> {
    /**
     * Service对象
     */
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CmnUserService cmnUserService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 取得Service对象
     */
    @Override
    public RoleService getService() {
        return roleService;
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
        //初始化数据导入标志位
        this.initDataImportFlag(model);

        return super.list(model);
    }

    /**
     * 初始化InputEntity实体对象[后处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    @Override
    protected void initInputEntityAfter(Model model, String uid) {
        //编辑的场合，设置既有角色权限
        if (this.isEditMode()) {
            this.getInputEntity().setPermissionList(permissionService.findByRole(this.getInputEntity().getUid()));

            List<String> userUids = userRoleService.findByRole(this.getInputEntity().getUid());
            //用户存在场合
            if (userUids.size() > 0) {
                this.getInputEntity().setUserUids(String.join(",", userUids));
            }
        }
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

        //应用名称
        dicMap.put("appCode", this.getDropdownList("sys_application", "app_code", "app_name"));

        Map<String, List<PermissionExt>> permissionMap = Maps.newHashMap();
        List<PermissionExt> permissionList = permissionService.search(PermissionExt.class, permissionService.getSearchSQL(null));
        for (PermissionExt permission : permissionList) {
            if (permissionMap.containsKey(permission.getAppCode())) {
                permissionMap.get(permission.getAppCode()).add(permission);
            }
            else {
                permissionMap.put(permission.getAppCode(), Lists.newArrayList(permission));
            }
        }
        //权限
        dicMap.put("permissionList", permissionMap);

        //查询条件
        Map<String, Object> condition = new HashMap<>();
        //排除外部员工
        condition.put("users.reg_system$!=", 2);
        List<DropdownEntity> userList = this.roleService.getUserSelect(condition);
        //用户列表
        dicMap.put("userUids", userList);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 取所有角色信息
     *
     * @return 角色列表JSON字符串
     */
    @RequestMapping(value = "getAllRoleList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RoleExt> getAllRoleList() throws JsonProcessingException {
        return this.roleService.getAllRoleList();
    }

    /**
     * 取得默认排序字段[子类可覆盖]
     *
     * @return 默认排序字段
     */
    @Override
    public Sort getDefaultSort() {
        // 默认按应用编号/表示顺序排序
        return new Sort(Sort.Direction.ASC, "appCode,dispSeq");
    }

    /**
     * 批量保存表示顺序
     *
     * @param itemJson 保存信息
     * @return AjaxResult
     */
    @ResponseBody
    @RequestMapping(value = "batchSaveDispSeq", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult batchSaveDispSeq(String itemJson) {
        List<RoleExt> roleList = JsonUtils.parseJSON(itemJson, new TypeReference<List<RoleExt>>() {
        });
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (RoleExt role : roleList) {
                RoleExt entity = roleService.getDetail(role.getUid());
                entity.setDispSeq(role.getDispSeq());
                roleService.getDao().save(entity);
            }
        }
        return AjaxResult.createSuccessResult();
    }

    @Override
    public Sort getForceSortField() {
        return new Sort("appCode,dispSeq");
    }
}
