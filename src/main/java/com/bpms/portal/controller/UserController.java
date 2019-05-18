package com.bpms.portal.controller;

import com.google.common.collect.Maps;
import com.bpms.core.controller.BaseController;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.service.BaseService;
import com.bpms.core.sync.SyncDataService;
import com.bpms.core.utils.DesUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.consts.DictConsts;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.entity.ext.UserInfoExt;
import com.bpms.sys.service.RoleService;
import com.bpms.sys.service.UserInfoService;
import com.bpms.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

/**
 * 用户控制器类
 */
@Controller(value = "PortalUserController")
@RequestMapping("/portal/user")
public class UserController extends BaseController<UserExt> {
    /**
     * Service对象
     */
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SyncDataService syncDataService;

    /**
     * 取得Service对象
     */
    @Override
    public BaseService<UserExt> getService() {
        return userService;
    }

    /**
     * 修改密码页面
     */
    @RequestMapping(value = "passwordChange", method = RequestMethod.GET)
    public String initPasswordChange(Model model) throws IllegalAccessException, IOException, InstantiationException {
        super.initInputPage(model);
        return "portal/user/PasswordChange";
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public AjaxResult changePassword(@RequestParam String oldPwd, String newPwd, String confirmPwd) {
        if (this.userService.changePassword(oldPwd, newPwd, confirmPwd)) {
            return AjaxResult.createSuccessResult("密码修改成功。");
        }
        else {
            return AjaxResult.createSuccessResult("密码修改失败。");
        }
    }

    /**
     * 个人设置初期化
     *
     * @param model Model
     * @return 个人设置JSP页面
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    @RequestMapping(value = "profileSetting", method = RequestMethod.GET)
    public String initProfileSetting(Model model) throws IllegalAccessException, IOException, InstantiationException {
        UserExt user = userService.getDetail(getCurrentUserId());
        user.getUserInfo().setMailPassword(StringUtils.EMPTY);
        model.addAttribute("jsonInputEntity_" + this.getEntityName(), this.toJSON(user, true));

        // 下拉框选项内容做成
        this.initOptionList(model);

        return "portal/user/ProfileSetting";
    }

    /**
     * 个人设置保存处理
     *
     * @param inputJson Json字符串
     * @return AjaxResult
     */
    @RequestMapping(value = "/saveProfile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object saveProfile(@RequestParam String inputJson) {
        UserExt entity = (UserExt) this.parseInputJson(inputJson);

        UserExt targetEntity = userService.findOne(getCurrentUserId());
        //设置基本信息
        targetEntity.setUserName(entity.getUserName());
        targetEntity.setSexCd(entity.getSexCd());
        targetEntity.setUserMail(entity.getUserMail());
        targetEntity.setUserPhone(entity.getUserPhone());
        //设置扩展信息
        targetEntity.setUserEnName(entity.getUserEnName());
        targetEntity.setBirthday(entity.getBirthday());
        targetEntity.setUserAddress(entity.getUserAddress());
        targetEntity.setUserExtensionNumber(entity.getUserExtensionNumber());
        targetEntity.setUserFaxphone(entity.getUserFaxphone());
        targetEntity.setUserZipcode(entity.getUserZipcode());

        //手机号码重复性校验(改为同样手机号的，状态必须不一致，譬如改过部门的人，手机号还是同一个，但是他们一个停用一个启用，所以不check重复）
        if (this.userService.isDuplication("uid", targetEntity.getUid(), "userPhone", targetEntity.getUserPhone(), true)) {
            throw new ServiceValidationException(String.format("指定用户名(%s)的手机号码(%s)已存在，请重新输入。", entity.getUserCd(), entity.getUserPhone()));
        }

        userService.getDao().save(targetEntity);

        //直接推送同步数据 个人设置信息
        syncDataService.directPushSyncDataTopic(targetEntity);

        //邮箱密码有输入的场合
        if (StringUtils.isNotEmpty(entity.getUserInfo().getMailPassword()) || StringUtils.isNotEmpty(entity.getUserInfo().getQq()) || StringUtils.isNotEmpty(entity.getUserInfo().getWeixin())) {
            UserInfoExt userInfo = userInfoService.findOne(getCurrentUserId());
            //用户扩展信息不存在的场合
            if (userInfo == null || StringUtils.isEmpty(userInfo.getUid())) {
                userInfo = new UserInfoExt();
                userInfo.setUid(getCurrentUserId());
            }

            if (StringUtils.isNotEmpty(entity.getUserInfo().getMailPassword())) {
                userInfo.setMailPassword(DesUtils.encrypt(entity.getUserInfo().getMailPassword()));
            }

            userInfo.setQq(entity.getUserInfo().getQq());
            userInfo.setWeixin(entity.getUserInfo().getWeixin());
            this.userInfoService.getDao().save(userInfo);
            //直接推送UDC同步数据
            syncDataService.directPushSyncDataTopic(userInfo);
        }

        return AjaxResult.createSuccessResult(entity.getUid(), "个人信息保存成功。", entity);
    }

    /**
     * 主题设置初期化
     *
     * @param model Model
     * @return 主题设置JSP页面
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    @RequestMapping(value = "themeSetting", method = RequestMethod.GET)
    public String initThemeSetting(Model model) throws IllegalAccessException, IOException, InstantiationException {
        return "portal/user/ThemeSetting";
    }

    /**
     * 主题设置保存处理
     *
     * @param themeName 主题名称
     * @return AjaxResult
     */
    @RequestMapping(value = "/saveTheme", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object saveTheme(@RequestParam String themeName) {
        UserExt entity = userService.findOne(getCurrentUserId());
        entity.setThemeName(themeName);
        userService.getDao().save(entity);

        //直接推送同步数据 主题设置信息
        syncDataService.directPushSyncDataTopic(entity);

        this.getCurrentUser().setThemeName(themeName);

        return AjaxResult.createSuccessResult(entity.getUid(), "主题设置成功。", entity);
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

        //性别
        dicMap.put("sexCd", this.getDictionaryList(DictConsts.DICT_SEX));

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}
