package com.bpms.sys.controller;

import com.bpms.sys.service.EnterpriseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.AccessUtils;
import com.bpms.core.utils.EntityUtils;
import com.bpms.sys.consts.DictConsts;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.service.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器类
 */
@Controller
@RequestMapping("/sys/dept")
public class DeptController extends SysBaseController<DeptExt> {
    @Autowired
    private EnterpriseService enterpriseService;
    /**
     * Service对象
     */
    @Autowired
    private DeptService deptService;

    /**
     * 取得Service对象
     */
    @Override
    public DeptService getService() {
        return deptService;
    }

    /**
     * 部门树形一览
     *
     * @param model Model对象
     * @return 部门树形JSP页面
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String deptTreeList(Model model) throws IllegalAccessException, IOException, InstantiationException {
        //初始化数据导入标志位
        this.initDataImportFlag(model);

        //默认根部门UID
        model.addAttribute("rootDeptUid", CmnConsts.ROOT_DEPT_UID);

        String s = getRequest().getParameter("enterpriseUid");
        if(!StringUtils.isEmpty(s)){
            model.addAttribute("enterpriseUid", s);
            //root dept检索
            Map<String, Object> condition = new HashMap<>();
            condition.put("main.parent_dept_uid", "IS NULL");
            condition.put("main.enterprise_uid", s);

            List<DeptExt> list = this.deptService.search(DeptExt.class, this.deptService.getSQL("/sys/Dept/getAllDeptList"),condition);
            model.addAttribute("rootDeptUid", list.get(0).getUid());
        }

        super.list(model);

        return "sys/dept/DeptTreeList";
    }

    /**
     * 一览数据查询【无分页】
     *
     * @param pagerJson     分页JSON字符串
     * @param conditionJson 查询条件JSON字符串
     * @return 一览数据集
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Override
    @RequestMapping(value = "searchNoPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> searchNoPage(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        // 取得查询条件
        Map<String, Object> condition = getCondition(conditionJson);

        // 保存最新的检索条件
        this.setSessionSearchCondition(conditionJson);
        this.setSessionSearchPager(pagerJson);

        // 取得标准查询SQL文
        String sql = getService().getSearchSQL(null);

        // 数据查询
        return this.deptService.prepareDeptTree(search(sql, condition));
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
     * 设定保存处理结果
     *
     * @param entity 实体对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult setSaveResult(DeptExt entity) {
        AjaxResult result = AjaxResult.createSuccessResult(EntityUtils.getPkValue(entity), "数据保存成功。", entity);

        //上级部门一览【数据保存成功后，需要重置上级部门下拉选择列表对象】
        result.setContent(this.getDropdownList("sys_dept", "uid", "dept_full_name"));

        return result;
    }

    /**
     * 上移/下移操作
     *
     * @param sourceUid 源部门UID
     * @param targetUid 目标部门UID
     * @return AjaxResult
     */
    @RequestMapping(value = "moveData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult moveData(@RequestParam String sourceUid, @RequestParam String targetUid) {
        deptService.switchDispSeq(sourceUid, targetUid);
        return AjaxResult.createSuccessResult();
    }

    @Override
    public Sort getDefaultSort() {
        return new Sort(Sort.Direction.ASC, "dispSeq");
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
        //部门类别
        dicMap.put("deptClass", this.getDictionaryList(DictConsts.DICT_DEPT_TYPE));

        //部门一览
        dicMap.put("parentDeptUid", this.getDropdownList("sys_dept", "uid", "dept_full_name"));

        dicMap.put("treeEnterpriseUid", this.enterpriseService.getUserSelect());

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 取所有部门信息[部门ComboTree选择专用方法]
     *
     * @return 部门列表JSON字符串
     */
    @RequestMapping(value = "getAllDeptList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> getAllDeptList() throws JsonProcessingException {
        //取得查询条件
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.enterprise_uid", "IS NULL");

        String s = getRequest().getParameter("enterpriseUid");
        if(!StringUtils.isEmpty(s)){
            condition.put("main.enterprise_uid", s);
        }
        return this.deptService.search(DeptExt.class, this.deptService.getSQL("/sys/Dept/getAllDeptList"),condition);
    }

    /**
     * 取所有dept_class <= 2 的部门信息 （去掉dept_class = 3 的部门）[部门ComboTree选择专用方法]
     *
     * @return 部门列表JSON字符串
     */
    @RequestMapping(value = "getAllCompanyList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> getAllCompanyList() throws JsonProcessingException {
        String recordStatus = getRequest().getParameter("recordStatus");

        //取得查询条件
        Map<String, Object> condition = new HashMap<>();
        if(!StringUtils.isEmpty(recordStatus)){
            condition.put("main.recordStatus", recordStatus);
        }
        //只检索 0 ： 集团， 1：大区， 2:分公司，  3：部门的除外
        condition.put("main.deptClass$<=", "2");
        return this.deptService.search(DeptExt.class, this.deptService.getSQL("/sys/Dept/getAllDeptList"), condition);
    }

    /**
     * 获取管辖分公司列表
     *
     * @return 部门列表JSON字符串
     */
    @RequestMapping(value = "getManageCompanyList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> getManageCompanyList() throws JsonProcessingException {
        String recordStatus = getRequest().getParameter("recordStatus");

        //系统管理员、技术支持、集团权限的场合，无需任何条件限制
        if (AccessUtils.isSystemManagement() || AccessUtils.isItSupport() || AccessUtils.isGroupDataRange()) {
            return getAllCompanyList();
        }

        Map<String, Object> map = new HashMap<>();
        //大区权限的场合，取得大区下属分公司
        if (AccessUtils.isAreaDataRange()) {
            map.put("main.parentDeptUid", getCurrentUser().getBelongAreaDeptUid());
        }
        //分公司权限的场合，取得自己所属分公司
        else {
            map.put("main.uid", getCurrentUser().getBelongCompanyDeptUid());
        }
        if(!StringUtils.isEmpty(recordStatus)){
            map.put("main.recordStatus", recordStatus);
        }
        List<DeptExt> subDeptList = deptService.search(DeptExt.class, map);
        List<String> areaUidList = new ArrayList<>();
        areaUidList.add(getCurrentUser().getBelongAreaDeptUid());

        //管辖分公司
        if (StringUtils.isNotEmpty(getCurrentUser().getDeptManageUids())) {
            map.clear();
            if(!StringUtils.isEmpty(recordStatus)){
                map.put("main.recordStatus", recordStatus);
            }
            map.put("main.uid$in", getCurrentUser().getDeptManageUids());
            List<DeptExt> manageCompanyList = deptService.search(DeptExt.class, map);
            //获取管辖分公司的大区UID
            for (DeptExt manageCompany : manageCompanyList) {
                areaUidList.add(manageCompany.getParentDeptUid());
            }
            subDeptList.addAll(manageCompanyList);
        }

        //获取大区
        map.clear();
        if(!StringUtils.isEmpty(recordStatus)){
            map.put("main.recordStatus", recordStatus);
        }
        map.put("main.uid$in", areaUidList);
        List<DeptExt> areaDeptList = deptService.search(DeptExt.class, map);
        subDeptList.addAll(areaDeptList);

        return subDeptList;
    }

    /**
     * 取所有dept_class <= 1 的大区信息（去掉dept_class = 3 的部门 ,以及 2的分公司）[部门ComboTree选择专用方法]
     *
     * @return 部门列表JSON字符串
     */
    @RequestMapping(value = "getAllAreaList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> getAllAreaList() throws JsonProcessingException {
        //取得查询条件
        Map<String, Object> condition = new HashMap<>();
        //只检索 0：集团/1：大区/2：分公司/3：部门的除外
        condition.put("main.deptClass$<=", "1");
        return this.deptService.search(DeptExt.class, this.deptService.getSQL("/sys/Dept/getAllDeptList"), condition);
    }

    /**
     * 根据用戶角色 或 部门父节点uid 查询部门列表
     *
     * @param parentUids 父节点uid
     * @return 部门列表list
     */
    @RequestMapping(value = "getDeptListByRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> getDeptListByRole(@RequestParam(required = false) String parentUids) {
        //取得查询条件
        Map<String, Object> condition = new HashMap<>();
        //取得查询条件
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isNotEmpty(parentUids)) {
            condition.put("main.parent_dept_uid$in", parentUids);
            map.put("main.uid$in", parentUids);
        }
        else {
            //管理员和集团权限
            if (AccessUtils.isGroupDataRange() || AccessUtils.isSystemManagement() || AccessUtils.isItSupport()) {
                //获取集团下的所有组织（所有大区）
                condition.put("main.parent_dept_uid", CmnConsts.ROOT_DEPT_UID);
                map.put("main.uid$in", CmnConsts.ROOT_DEPT_UID);
            }
            //大区权限
            else if (AccessUtils.isAreaDataRange()) {
                //获取大区下的所有组织（所有分公司）
                condition.put("main.parent_dept_uid", this.getCurrentUser().getBelongAreaDeptUid());
                map.put("main.uid$in", this.getCurrentUser().getBelongAreaDeptUid());
            }
            //分公司权限
            else if (AccessUtils.isCompanyDataRange()) {
                //获取分公司下的所有组织（所有部门）
                condition.put("main.parent_dept_uid", this.getCurrentUser().getBelongCompanyDeptUid() == null ? this.getCurrentUser().getDeptUid() : this.getCurrentUser().getBelongCompanyDeptUid());
                map.put("main.uid$in", this.getCurrentUser().getBelongCompanyDeptUid() == null ? this.getCurrentUser().getDeptUid() : this.getCurrentUser().getBelongCompanyDeptUid());
            }
            //部门权限
            else {
                condition.put("main.parent_dept_uid", this.getCurrentUser().getDeptUid());
                map.put("main.uid$in", this.getCurrentUser().getDeptUid());
            }
        }

        //查询父节点自身信息
        List<DeptExt> deptExtList = this.deptService.search(DeptExt.class, this.deptService.getSQL("/sys/Dept/getAllDeptList"), map);
        //查询子节点信息
        deptExtList.addAll(this.deptService.search(DeptExt.class, this.deptService.getSQL("/sys/Dept/getAllDeptList"), condition));

        return deptExtList;
    }

    /**
     * 根据用戶角色 或 部门父节点uid 查询部门列表 【无权限过滤】
     *
     * @param parentUids 父节点uid
     * @return 部门列表list
     */
    @RequestMapping(value = "getDeptList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> getDeptList(@RequestParam(required = false) String parentUids) {
        //取得查询条件
        Map<String, Object> condition = new HashMap<>();
        //取得查询条件
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isNotEmpty(parentUids)) {
            condition.put("main.parent_dept_uid$in", parentUids);
            map.put("main.uid$in", parentUids);
        }
        else {
            //获取集团下的所有组织（所有大区）
            condition.put("main.parent_dept_uid", CmnConsts.ROOT_DEPT_UID);
            map.put("main.uid$in", CmnConsts.ROOT_DEPT_UID);
        }

        //查询父节点自身信息
        List<DeptExt> deptExtList = this.deptService.search(DeptExt.class, this.deptService.getSQL("/sys/Dept/getAllDeptList"), map);
        //查询子节点信息
        deptExtList.addAll(this.deptService.search(DeptExt.class, this.deptService.getSQL("/sys/Dept/getAllDeptList"), condition));

        return deptExtList;
    }

    /**
     * 根据权限获取管辖分公司列表
     *
     * @return 部门列表
     */
    @RequestMapping(value = "getCompanyListByRoleCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DeptExt> getCompanyListByRoleCode(String roleCodes) {
        return this.deptService.getCompanyListByRoleCode(roleCodes);
    }
}
