package com.bpms.sys.controller;

import com.google.common.collect.Maps;
import com.bpms.cmn.service.CustomerService;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.entity.CoreEntity;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.core.utils.UniqueUtils;
import com.bpms.sys.consts.DictConsts;
import com.bpms.sys.entity.ext.RoleExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.RoleService;
import com.bpms.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器类
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends SysBaseController<UserExt> {
    /**
     * Service对象
     */
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CustomerService customerService;

    /**
     * 取得Service对象
     */
    @Override
    public UserService getService() {
        return userService;
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
     * 指定SQL的检索条件分页排序查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @param pager     分页排序对象
     * @return 一览数据集
     */
    @Override
    public Page<UserExt> search(String sql, Map<String, Object> condition, PageRequest pager) {
        Page<UserExt> pageList = getService().search(this.getEntityClass(), sql, condition, pager);
        for (CoreEntity entity : pageList) {
            //将手机号码最后四位隐藏
            this.hiddenUserPhoneSuffix((UserExt) entity);
        }
        return pageList;
    }

    /**
     * 数据输入画面初期化
     *
     * @param model Model对象
     * @param uid   主键UID
     * @return 数据输入JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = "input", method = RequestMethod.GET, name = "新增/编辑")
    public String input(Model model, @RequestParam(required = false) String uid) throws IllegalAccessException, IOException, InstantiationException {
        String path = super.input(model, uid);
        //添加模式的场合
        if (this.isAddMode()) {
            uid = UniqueUtils.getUID();
            // UID预设置
            this.getInputEntity().setUid(uid);
            //UID
            model.addAttribute("uid", uid);
            //设置数据模型JSON字符
            this.setInputEntityToModel(model);
        }
        return path;
    }

    /**
     * 数据导出
     *
     * @param conditionJson 查询条件JSON字符串
     * @param colList       列信息JSON字符串
     * @param fileName      文件名称
     * @param extensionName 文件扩展名称
     * @return 字节流
     */
    @Override
    @RequestMapping(value = "export", method = RequestMethod.POST, name = "导出")
    public ResponseEntity<byte[]> export(@RequestParam String conditionJson, @RequestParam String colList, @RequestParam String fileName, @RequestParam(defaultValue = "xlsx") String extensionName) {
        try {
            //取得查询条件
            Map<String, Object> condition = this.getCondition(conditionJson);

            //取得分页条件
            PageRequest pageRequest = this.getPageRequest(this.getPager(this.getSessionSearchPager()));

            //取得标准查询SQL文
            String sql = getService().getSearchSQL(condition);

            List<UserExt> list = getService().search(getEntityClass(), sql, condition, pageRequest.getSort());

            //将手机号码最后四位隐藏
            if (CollectionUtils.isNotEmpty(list)) {
                for (UserExt entity : list) {
                    this.hiddenUserPhoneSuffix(entity);
                }
            }

            return this.exportExcel(list, colList, fileName, extensionName);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 初始化InputEntity实体对象[后处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    @Override
    protected void initInputEntityAfter(Model model, String uid) {
        //编辑的场合，设置既有用户角色
        if (this.isEditMode()) {
            this.getInputEntity().setRoleList(this.roleService.findByUserUid(this.getInputEntity().getUid()));
        }
        else {
            //将手机号码最后四位隐藏
            this.hiddenUserPhoneSuffix(this.getInputEntity());
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
        //字典
        Map<String, Object> dicMap = Maps.newHashMap();

        dicMap.put("sexCd", this.getDictionaryList(DictConsts.DICT_SEX));

        //职位一栏
        dicMap.put("positionUid", this.getDropdownList("sys_position", "uid", "position_name"));

        List<RoleExt> list = this.roleService.getRoleList();

        //角色列表
        dicMap.put("roleList", list);

        List<DropdownEntity> dropdownList = new ArrayList<>();

        for (RoleExt role : list) {
            DropdownEntity dropdownEntity = new DropdownEntity();
            dropdownEntity.setSubCd(role.getUid());
            dropdownEntity.setSubName(role.getRoleName());
            dropdownList.add(dropdownEntity);
        }

        //角色下拉选择
        dicMap.put("roleUid", dropdownList);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 根据岗位取得用户
     *
     * @param positionUids 职位主键集合(逗号分隔)
     * @return AjaxResult
     */
    @RequestMapping(value = "findByPositionUid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult findByPositionUid(@RequestParam String positionUids) {
        // 删除处理
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("main.positionUid$in", positionUids);
        List<UserExt> result = search(getService().getSearchSQL(condition), condition);

        return AjaxResult.createSuccessResult("数据查询成功。", result);
    }

    /**
     * 根据人员UID取得用户详细信息
     *
     * @param uid 人员UID
     * @return 用户详情实体对象
     */
    @RequestMapping(value = "getDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Override
    public UserExt getDetail(@RequestParam String uid) {
        UserExt userExt = this.userService.getDetail(uid);
        userExt.setRoleList(this.roleService.findByUserUid(uid));
        userExt.setCustomerList(this.customerService.findByUserUid(uid));
        return userExt;
    }

    /**
     * 将手机号码最后四位隐藏
     *
     * @param userExt 用户实体对象
     */
    protected void hiddenUserPhoneSuffix(UserExt userExt) {
        if (StringUtils.isNotEmpty(userExt.getUserPhone()) && userExt.getUserPhone().length() > 4) {
            userExt.setUserPhone(StringUtils.substring(userExt.getUserPhone(), 0, userExt.getUserPhone().length() - 4) + "****");
        }
    }
}
