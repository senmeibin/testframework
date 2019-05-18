package com.bpms.sys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.entity.BaseEntity;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.sys.dao.DeptDao;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.entity.ext.UserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门服务类
 */
@Service
public class DeptService extends SysBaseService<DeptExt> {
    @Autowired
    private DeptDao deptDao;

    @Autowired
    private UserService userService;

    @Override
    public DeptDao getDao() {
        return deptDao;
    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected DeptExt saveBefore(DeptExt entity) {
        //部门名称重复性校验
        if (this.isDuplication("uid", entity.getUid(), "parentDeptUid", entity.getParentDeptUid(), "deptName", entity.getDeptName(), true)) {
            throw new ServiceValidationException(String.format("指定的部门名称(%s)已存在，请重新输入。", entity.getDeptName()));
        }
        //部门新增的场合，检验是否跨组织添加
        if (StringUtils.isEmpty(entity.getUid())) {
            Map<String, Object> condition = new HashMap<>();
            condition.put("main.uid", entity.getParentDeptUid());
            List<DeptExt> dept = this.search(DeptExt.class, this.getSearchSQL(condition), condition);
            //如果当前部门的部门层级与上级部门层级相差不等于1，则不允许添加
            if (entity.getDeptClass() - dept.get(0).getDeptClass() != 1) {
                throw new ServiceValidationException("不允许跨部门层级添加部门，请重新确认。");
            }
        }

        //数据添加模式 或者部门CODE为空的场合
        if (this.isAddMode(entity) || StringUtils.isEmpty(entity.getDeptCode())) {
            // 部门编号自动编号
            entity.setDeptCode(this.getMaxCode("dept_code", "D", 6));
        }
        if (entity.getDispSeq() == null) {
            entity.setDispSeq(9999);
        }
        //重新设置部门全称
        entity.setDeptFullName(entity.getDeptName());
        fillDeptFullName(entity, entity.getParentDeptUid());
        //重设部门所属分公司
        setCompanyDeptUid(entity);
        return entity;
    }

    /**
     * 重设部门所属分公司
     */
    private void setCompanyDeptUid(DeptExt entity) {
        //如果部门层级大于2（部门及以下），则赋予所属分公司的
        if (entity.getDeptClass() > 2) {
            //递归获取当前部门的所属分公司
            Map<String, Object> condition = new HashMap<>();
            resetCompanyDeptUid(entity, entity.getParentDeptUid(), condition);
        }
    }

    /**
     * 递归重置部门的所属分公司
     *
     * @param entity        组织实体
     * @param parentDeptUid 上级部门
     * @param condition     condition
     */
    private void resetCompanyDeptUid(DeptExt entity, String parentDeptUid, Map<String, Object> condition) {
        //获取父部门
        condition.clear();
        condition.put("main.uid", parentDeptUid);
        List<DeptExt> deptList = this.search(DeptExt.class, this.getSearchSQL(condition), condition);
        if (CollectionUtils.isNotEmpty(deptList)) {
            //如果已经为最高层，则跳出递归
            if (deptList.get(0).getDeptClass() != 0) {
                if (deptList.get(0).getDeptClass() == 2) {
                    entity.setCompanyDeptUid(deptList.get(0).getUid());
                    //编辑状态下执行下级组织变更所属分公司
                    if (StringUtils.isNotEmpty(entity.getUid())) {
                        //获取当前部门的下级组织
                        List<String> deptExtList = getSubDeptList(entity.getUid());
                        //如果有下级组织，则更新下级组织的所属分公司
                        if (deptExtList.size() > 1) {
                            String list = SearchConditionUtils.getInValue(deptExtList);
                            String sql = this.getSQL("sys/Dept/resetCompanyDeptUid").replaceAll("@dept_uid", deptList.get(0).getUid()).replaceAll("@dept_sub_uid", list);
                            this.getDao().executeUpdate(sql);
                        }
                    }
                }
                else {
                    resetCompanyDeptUid(entity, deptList.get(0).getParentDeptUid(), condition);
                }
            }
        }
    }

    /**
     * 重新设置部门全称
     */
    protected void fillDeptFullName(DeptExt entity, String parentUid) {
        if (StringUtils.isNotEmpty(parentUid) && !StringUtils.equals(parentUid, CmnConsts.ROOT_DEPT_UID)) {
            DeptExt parent = deptDao.findOne(parentUid);
            entity.setDeptFullName(parent.getDeptName() + "-" + entity.getDeptFullName());
            fillDeptFullName(entity, parent.getParentDeptUid());
        }
    }

    /**
     * 重建指定部门下所有子部门的表示顺序
     *
     * @param parentDeptUid 父部门UID
     */
    private void reIndexDispSeq(String parentDeptUid) {
        //取得所有同级目录下的部门后重建表示顺序
        List<DeptExt> deptList = deptDao.findByParentDeptUid(parentDeptUid);

        for (int i = 0; i < deptList.size(); i++) {
            DeptExt dept = deptList.get(i);
            dept.setDispSeq(i * 10);
        }

        deptDao.save(deptList);
    }

    @Override
    protected Boolean deleteBefore(String ids) {
        // 分割UID
        String[] uids = ids.split(",");

        for (String uid : uids) {
            DeptExt dept = deptDao.findOne(uid);
            //部门不存在
            if (dept == null) {
                throw new ServiceValidationException("您所操作的部门不存在。");
            }
            //下属部门存在的场合
            else if (deptDao.findByParentDeptUid(uid).size() > 0) {
                throw new ServiceValidationException(String.format("您所操作的部门 [%s] 存在下属部门，无法删除。", dept.getDeptName()));
            }
            //下属人员存在的场合
            else {
                List<UserExt> belongUser = userService.findByDeptUid(uid);
                if (belongUser.size() > 0) {
                    List userName = new ArrayList();
                    for (UserExt userExt : belongUser) {
                        userName.add(userExt.getUserName());
                    }
                    throw new ServiceValidationException(String.format("您所操作的部门 [%s] 存在下属人员，无法删除。<br/>%s", dept.getDeptName(), StringUtils.join(userName.toArray(), "，")));
                }
            }
        }
        return true;
    }

    /**
     * 交换两条数据的排序字段
     */
    @Transactional
    public void switchDispSeq(String sourceUid, String targetUid) {
        //取出数据
        DeptExt source = findOne(sourceUid);
        DeptExt target = findOne(targetUid);

        if (!Objects.equals(source.getParentDeptUid(), target.getParentDeptUid())) {
            throw new ServiceValidationException("非同级部门，无法移动。");
        }

        //重建指定部门下所有子部门的表示顺序
        reIndexDispSeq(source.getParentDeptUid());

        source = findOne(sourceUid);
        target = findOne(targetUid);
        if (log.isDebugEnabled()) {
            log.debug("索引交换前 src {} : {}", source.getDeptName(), source.getDispSeq());
            log.debug("索引交换前 target {} : {}", target.getDeptName(), target.getDispSeq());
        }

        //交换src和target的dispSeq
        int temp = source.getDispSeq();
        source.setDispSeq(target.getDispSeq());
        target.setDispSeq(temp);
        if (log.isDebugEnabled()) {
            log.debug("索引交换后 src {} : {}", source.getDeptName(), source.getDispSeq());
            log.debug("索引交换后 target {} : {}", target.getDeptName(), target.getDispSeq());
        }

        //保存
        save(source);
        save(target);
    }

    /**
     * 将非树状结构部门List转换为树状结构部门List
     *
     * @param depts 非树状结构的部门List
     * @return 树状结构的部门List
     */
    public List<DeptExt> prepareDeptTree(List<DeptExt> depts) {
        //空数据 直接返回
        if (depts.size() == 0) {
            return depts;
        }
        //排序后集合
        List<DeptExt> result = Lists.newArrayList();

        //取得所有顶级部门
        List<DeptExt> topDepts = getTopDepts(depts);

        //生成树
        for (DeptExt first : topDepts) {
            result.add(first);
            result.addAll(findSubDept(first, depts, 1));
        }
        return result;
    }

    /**
     * 无上级部门的部门为顶级部门
     *
     * @param depts 部门集合
     * @return 所有顶级部门
     */
    private List<DeptExt> getTopDepts(List<DeptExt> depts) {
        List<DeptExt> result = Lists.newArrayList();

        result.addAll(depts.stream().filter(deptExt -> StringUtils.isEmpty(deptExt.getParentDeptUid())).collect(Collectors.toList()));
        return result;
    }

    /**
     * 递归返回下属部门
     *
     * @param parentDept 上级部门
     * @param depts      非树状结构的部门List
     * @param level      层级
     * @return 树状结构的部门List
     */
    private List<DeptExt> findSubDept(DeptExt parentDept, List<DeptExt> depts, int level) {
        List<DeptExt> result = Lists.newArrayList();
        List<DeptExt> deptList = Lists.newArrayList(depts);
        deptList.stream().filter(dept -> dept != null && StringUtils.equals(parentDept.getUid(), dept.getParentDeptUid())).forEach(dept -> {
            dept.setDeptName(StringUtils.repeat("#", level * 2) + dept.getDeptName());
            result.add(dept);
            if (depts.contains(dept)) {
                depts.remove(dept);
            }
            if (depts.size() > 0) {
                List<DeptExt> subs = findSubDept(dept, depts, level + 1);
                if (subs.size() > 0) {
                    result.addAll(subs);
                }
            }
        });
        return result;
    }

    /**
     * 取得部门一览（全部部门）
     * 注：部门下拉选择专用方法
     *
     * @return 部门一览
     */
    public List<DropdownEntity> getDropdownDept() {
        return this.getDropdownDept(null);
    }

    /**
     * 取得部门一览（指定部门层级的部门）
     * 注：部门下拉选择专用方法
     *
     * @param deptClass 部门class
     * @return 部门一览
     */
    @Transactional(readOnly = true)
    public List<DropdownEntity> getDropdownDept(Integer deptClass) {
        return this.getDropdownDept("", deptClass);
    }

    /**
     * 取得部门一览（指定部门层级的部门）
     * 注：部门下拉选择专用方法
     *
     * @param rootUid   父级部门uid （为空时取全部）
     * @param deptClass 部门class
     * @return 部门一览
     */
    @Transactional(readOnly = true)
    public List<DropdownEntity> getDropdownDept(String rootUid, Integer deptClass) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("recordStatus", CmnConsts.RECORD_STATUS_ACTIVE);
        if (StringUtils.isNotEmpty(rootUid)) {
            condition.put("parent_dept_uid", rootUid);
        }
        if (deptClass != null) {
            //部门层级(0：集团/1：大区/2：分公司/3：部门)
            condition.put("deptClass", deptClass.toString());
        }
        return getDropdownList("sys_dept", "uid", "dept_full_name", condition);
    }

    /**
     * 取得部门一览（指定部门层级的部门）
     * 注：部门下拉选择专用方法
     *
     * @param rootUid     父级部门uid （为空时取全部）
     * @param valueFields 指定下拉框的value filed （多个时以  | 和 - 分隔）
     * @param deptClass   部门class
     * @return 部门一览
     */
    @Transactional(readOnly = true)
    public List<DropdownEntity> getDropdownDept(String rootUid, String valueFields, Integer deptClass) {
        return this.getDropdownDept(rootUid, valueFields, deptClass, true);
    }

    /**
     * 取得部门一览（指定部门层级的部门）
     * 注：部门下拉选择专用方法
     *
     * @param parentDeptUids    父级部门uid集合（为空时取全部，多个时半角逗号分隔）
     * @param valueFields       指定下拉框的value filed （多个时以 | 或 - 分隔）
     * @param deptClass         部门class
     * @param onlyGetActiveData 是否只取得启用数据（true：是/false：包含停用数据）
     * @return 部门一览
     */
    @Transactional(readOnly = true)
    public List<DropdownEntity> getDropdownDept(String parentDeptUids, String valueFields, Integer deptClass, boolean onlyGetActiveData) {
        return getDropdownDept(parentDeptUids, valueFields, "dept_full_name", deptClass, onlyGetActiveData);
    }

    /**
     * 取得部门一览（指定部门层级的部门）
     * 注：部门下拉选择专用方法
     *
     * @param parentDeptUids    父级部门uid集合（为空时取全部，多个时半角逗号分隔）
     * @param valueFields       指定下拉框的value filed （多个时以 | 或 - 分隔）
     * @param deptClass         部门class
     * @param onlyGetActiveData 是否只取得启用数据（true：是/false：包含停用数据）
     * @return 部门一览
     */
    @Transactional(readOnly = true)
    public List<DropdownEntity> getDropdownDept(String parentDeptUids, String valueFields, String textFields, Integer deptClass, boolean onlyGetActiveData) {
        Map<String, Object> condition = Maps.newHashMap();
        //只取有效数据
        if (onlyGetActiveData) {
            condition.put("recordStatus", CmnConsts.RECORD_STATUS_ACTIVE);
        }
        else {
            condition.put("recordStatus$in", CmnConsts.RECORD_STATUS_ACTIVE + "," + CmnConsts.RECORD_STATUS_STOP);
        }
        //父级部门uid集合 条件设定
        if (StringUtils.isNotEmpty(parentDeptUids)) {
            condition.put("parent_dept_uid$in", parentDeptUids);
        }
        if (deptClass != null) {
            //部门层级(0：集团/1：大区/2：分公司/3：部门)
            condition.put("deptClass", deptClass.toString());
        }
        return getDropdownList("sys_dept", valueFields, textFields, condition, "parent_dept_uid");
    }

    /**
     * 部门列表转部门ID列表
     *
     * @param deptList 部门列表
     * @return 部门UID列表
     */
    public List<String> deptToString(List<DeptExt> deptList) {
        List<String> result = Lists.newArrayList();
        if (deptList != null) {
            //用uid 标识部门组织
            result.addAll(deptList.stream().map(BaseEntity::getUid).collect(Collectors.toList()));
        }
        return result;
    }

    /**
     * 获取下级组织UID 无递归
     *
     * @param parentDeptUid 上级组织UID一览
     */
    public List<String> getSubDeptNoTree(String parentDeptUid) {
        //定义查询参数
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("main.parent_dept_uid", parentDeptUid);

        return deptToString(super.search(DeptExt.class, this.getSearchSQL(condition), condition));
    }

    /**
     * 获取下级组织UID
     *
     * @param parentDeptUid 上级组织UID一览
     */
    public List<String> getSubDeptList(String parentDeptUid) {
        List<String> list = new ArrayList<>();

        //追加父部门为第一个元素
        list.add(parentDeptUid);

        //查询出所有组织
        List<DeptExt> allDeptList = getDao().search(DeptExt.class, this.getSearchSQL(null));

        String[] parentDeptList = parentDeptUid.split(",");
        for (String parentUid : parentDeptList) {
            if (StringUtils.isEmpty(parentUid)) {
                continue;
            }

            //递归获取下级组织
            this.getSubDeptList(allDeptList, parentUid, list);
        }

        return list;
    }

    /**
     * 获取指定的某一层级的部门列表
     *
     * @param parentDeptUid 上级组织UID
     * @param deptClass     部门等级
     */
    public List<String> getSpecifyDeptList(String parentDeptUid, int deptClass) {
        List<String> list = new ArrayList<>();
        List<String> subDeptList = this.getSubDeptList(parentDeptUid);
        if (CollectionUtils.isNotEmpty(subDeptList)) {
            Map<String, Object> condition = Maps.newHashMap();
            condition.put("main.uid$in", subDeptList);
            condition.put("main.dept_class", deptClass);
            //查询出所有组织
            List<DeptExt> deptList = getDao().search(DeptExt.class, this.getSearchSQL(condition), condition);

            for (DeptExt deptExt : deptList) {
                //递归获取下级组织
                list.add(deptExt.getUid());
            }
        }

        return list;
    }

    /**
     * 递归获取下级组织
     *
     * @param allDeptList   所有组织
     * @param parentDeptUid 上级部门ID
     * @return
     */
    private List<String> getSubDeptList(List<DeptExt> allDeptList, String parentDeptUid, List<String> list) {
        for (DeptExt dept : allDeptList) {
            //遍历出父UID等于参数UID，添加进集合
            if (parentDeptUid.equals(dept.getParentDeptUid())) {
                //递归遍历下级部门
                getSubDeptList(allDeptList, dept.getUid(), list);
                list.add(dept.getUid());
            }
        }
        return list;
    }

    /**
     * 获取上级组织UID
     *
     * @param childDeptUid 下级组织的uid
     */
    public List<String> getParentDeptList(String childDeptUid) {
        List<String> list = new ArrayList<>();

        //查询出所有组织
        List<DeptExt> allDepts = getDao().search(DeptExt.class, this.getSearchSQL(null));

        //递归获取上级组织
        return getParentDeptList(allDepts, childDeptUid, list);
    }

    /**
     * 递归获取上级组织
     *
     * @param allDepts      所有组织
     * @param chilidDeptUid 上级部门ID
     * @return
     */
    public List<String> getParentDeptList(List<DeptExt> allDepts, String chilidDeptUid, List<String> list) {
        //防止db数据遭到恶意破坏后出现死循环
        if (list.size() > 100) {
            return list;
        }

        for (DeptExt dept : allDepts) {
            //遍历出UID等于参数UID，添加进集合
            if (chilidDeptUid.equals(dept.getUid())) {
                //存在上级部门
                if (StringUtils.isNotEmpty(dept.getParentDeptUid())) {
                    list.add(dept.getParentDeptUid());
                    //递归遍历出上级部门
                    getParentDeptList(allDepts, dept.getParentDeptUid(), list);
                }
            }
        }
        return list;
    }

    /**
     * 保存分公司ID
     *
     * @param deptUid 部门表选中行的UID
     * @param cityId  选中行填充的城市ID
     * @return AjaxResult
     */
    public AjaxResult saveCityId(String deptUid, String cityId) {
        //验证分公司ID是否已经存在
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.companyId", cityId);
        condition.put("main.uid$<>", deptUid);
        List<DeptExt> deptList = search(DeptExt.class, getSearchSQL(condition), condition);
        //定义返回参数
        AjaxResult ajaxResult = new AjaxResult();
        //分公司ID不存在场景
        if (deptList.size() == 0) {
            //查询出部门信息
            DeptExt dept = findOne(deptUid);
            //保存数据
            save(dept);
            //保存成功返回1
            ajaxResult.setResult(1);
        }
        //分公司ID存在场景
        else {
            //分公司ID已存在 返回-2
            ajaxResult.setResult(-2);
            return ajaxResult;
        }
        return ajaxResult;
    }

    /**
     * 获取上级部门UID
     * 注：只返回上级部门UID 如：传入部门UID，返回分公司UID
     *
     * @param deptUid 部门UID
     * @return 上级部门UID
     */
    public String getParentDeptUid(String deptUid) {
        String parentDeptUid = "UNKNOWN";
        if (StringUtils.isEmpty(deptUid)) {
            return parentDeptUid;
        }
        DeptExt dept = this.deptDao.findOne(deptUid);
        if (dept != null) {
            parentDeptUid = dept.getParentDeptUid() == null || StringUtils.equals("", dept.getParentDeptUid()) ? "UNKNOWN" : dept.getParentDeptUid();
        }

        return parentDeptUid;
    }

    /**
     * 获取分公司UID
     *
     * @param deptUid 部门UID（3/4层级部门）
     * @return 分公司UID
     */
    public String getCompanyDeptUid(String deptUid) {
        String companyDeptUid = "UNKNOWN";
        if (StringUtils.isEmpty(deptUid)) {
            return companyDeptUid;
        }
        DeptExt dept = this.deptDao.findOne(deptUid);
        if (dept != null) {
            companyDeptUid = dept.getCompanyDeptUid() == null || StringUtils.equals("", dept.getCompanyDeptUid()) ? "UNKNOWN" : dept.getCompanyDeptUid();
        }

        return companyDeptUid;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        DeptExt dept = this.findOne(uid);
        //停用处理时，判断相关数据
        if (StringUtils.equals(recordStatus, CmnConsts.RECORD_STATUS_STOP)) {

            //部门不存在
            if (dept == null) {
                throw new ServiceValidationException("您所操作的部门不存在。");
            }
            //下属部门存在的场合
            else if (deptDao.findByParentDeptUid(uid).size() > 0) {
                throw new ServiceValidationException(String.format("您所操作的部门 [%s] 存在下属部门，无法停用。", dept.getDeptName()));
            }
            //启用中下属人员存在的场合
            else {
                List<UserExt> belongUser = userService.findValidUserByDeptUid(uid);
                if (belongUser.size() > 0) {
                    List userName = new ArrayList();
                    for (UserExt userExt : belongUser) {
                        userName.add(userExt.getUserName());
                    }
                    throw new ServiceValidationException(String.format("您所操作的部门 [%s] 存在启用中的下属人员，无法停用。<br/>%s", dept.getDeptName(), StringUtils.join(userName.toArray(), "，")));
                }
            }

            //设置为 8 ：停用
            dept.setRecordStatus(8);

            //保存处理
            this.getDao().save(dept);
        }

        return true;
    }

    /**
     * 根据用户角色权限获取分公司列表
     *
     * @param roleCodes 权限CODE
     * @return 所有部门集合
     */
    public List<DeptExt> getCompanyListByRoleCode(String roleCodes) {
        if (StringUtils.isEmpty(roleCodes)) {
            throw new ServiceException("权限代码不能为空。");
        }
        List<String> uidList = Lists.newArrayList();

        Map<String, Object> condition = Maps.newHashMap();
        condition.put("role.roleCode$in", roleCodes);
        List<DeptExt> deptList = search(getSQL("sys/Dept/searchDeptByRoleCode"), condition);
        for (DeptExt dept : deptList) {
            if (!uidList.contains(dept.getUid())) {
                uidList.add(dept.getUid());
            }
            if (!uidList.contains(dept.getCompanyDeptUid())) {
                uidList.add(dept.getCompanyDeptUid());
            }
            if (!uidList.contains(dept.getAreaDeptUid())) {
                uidList.add(dept.getAreaDeptUid());
            }
        }
        String deptUids = StringUtils.join(uidList.toArray(new String[]{}), ",");
        Map<String, Object> map = new HashMap<>();
        map.put("main.uid$in", deptUids);
        map.put("main.deptClass$<=", "2");
        return search(map);
    }
}