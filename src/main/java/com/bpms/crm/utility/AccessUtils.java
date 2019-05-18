package com.bpms.crm.utility;

import com.bpms.core.security.ShiroUser;
import com.bpms.core.utils.SearchConditionUtils;
import com.bpms.core.utils.SpringUtils;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.service.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessUtils extends com.bpms.core.utils.AccessUtils {
    /**
     * 招聘专员权限
     */
    public static boolean isRecruitSpecialist() {
        return SecurityUtils.getSubject().hasRole("RecruitSpecialist");
    }

    /**
     * 项目经理权限
     */
    public static boolean isPmUser() {
        return SecurityUtils.getSubject().hasRole("PmUser");
    }

    /**
     * 项目总监权限
     */
    public static boolean isProjectDirector() {
        return SecurityUtils.getSubject().hasRole("ProjectDirector");
    }

    /**
     * 项目总监助理权限
     */
    public static boolean isProjectDirectorAssistant() {
        return SecurityUtils.getSubject().hasRole("ProjectDirectorAssistant");
    }

    /**
     * 项目助理
     */
    public static boolean isPmAssistant() {
        return SecurityUtils.getSubject().hasRole("PmAssistant");
    }

    /**
     * 数据分析专员权限
     */
    public static boolean isDataAnalyst() {
        return SecurityUtils.getSubject().hasRole("DataAnalyst");
    }

    /**
     * TS权限
     */
    public static boolean isTS() {
        return SecurityUtils.getSubject().hasRole("TS");
    }

    /**
     * TMK权限
     */
    public static boolean isTMK() {
        return SecurityUtils.getSubject().hasRole("TMK");
    }

    /**
     * 移动招聘数据分析
     */
    public static boolean isMobileDataAnalyst() {
        return SecurityUtils.getSubject().hasRole("MobileDataAnalyst");
    }

    /**
     * 移动招聘项目总监
     */
    public static boolean isMobileProjectDirector() {
        return SecurityUtils.getSubject().hasRole("MobileProjectDirector");
    }

    /**
     * 移动招聘项目经理
     */
    public static boolean isMobileProjectManager() {
        return SecurityUtils.getSubject().hasRole("MobileProjectManager");
    }

    /**
     * TMKLeader
     */
    public static boolean isTMKLeader() {
        return SecurityUtils.getSubject().hasRole("TMKLeader");
    }

    /**
     * 微信岗
     */
    public static boolean isWeChatUser() {
        return SecurityUtils.getSubject().hasRole("WeChatUser");
    }

    /****************************************CR招聘相关权限判断****************************************/
    /**
     * CR专员
     */
    public static boolean isCRSpecialist() {
        return SecurityUtils.getSubject().hasRole("CRSpecialist");
    }

    /**
     * CR学员分配专员
     */
    public static boolean isCRResumeAssignSpecialist() {
        return SecurityUtils.getSubject().hasRole("CRResumeAssignSpecialist");
    }

    /**
     * CRLeader
     */
    public static boolean isCRLeader() {
        return SecurityUtils.getSubject().hasRole("CRLeader");
    }

    /**
     * CR岗位分配专员
     */
    public static boolean isCRPositionAssignSpecialist() {
        return SecurityUtils.getSubject().hasRole("CRPositionAssignSpecialist");
    }

    /**
     * CR管理员
     */
    public static boolean isCRAdmin() {
        return SecurityUtils.getSubject().hasRole("CRAdmin");
    }

    /**
     * CR招聘（暂无菜单权限的设置，用于程序中区分招聘体系使用）
     */
    public static boolean isCRRecruit() {
        return SecurityUtils.getSubject().hasRole("CRRecruit");
    }


    /****************************************TS招聘相关权限判断****************************************/
    /**
     * TS专员
     */
    public static boolean isTSSpecialist() {
        return SecurityUtils.getSubject().hasRole("TSSpecialist");
    }

    /**
     * TS学员分配专员
     */
    public static boolean isTSResumeAssignSpecialist() {
        return SecurityUtils.getSubject().hasRole("TSResumeAssignSpecialist");
    }

    /**
     * TSLeader
     */
    public static boolean isTSLeader() {
        return SecurityUtils.getSubject().hasRole("TSLeader");
    }

    /**
     * TSManager
     */
    public static boolean isTSManager() {
        return SecurityUtils.getSubject().hasRole("TSManager");
    }

    /**
     * TS招聘（暂无菜单权限的设置，用于程序中区分招聘体系使用）
     */
    public static boolean isTSRecruit() {
        return SecurityUtils.getSubject().hasRole("TSRecruit");
    }

    /**
     * 数据范围检索条件初期化
     *
     * @param cls       实体类名
     * @param condition 检索条件
     */
    public static void initDataRangeCondition(Class cls, Map<String, Object> condition) {
        initDataRangeCondition(cls, condition, "companyUid", "deptUid");
    }

    /**
     * 数据范围检索条件初期化
     *
     * @param cls              实体类名
     * @param condition        检索条件
     * @param companyFieldName 大区分公司权限 表字段
     * @param deptFieldName    部门权限 表字段
     */
    public static void initDataRangeCondition(Class cls, Map<String, Object> condition, String companyFieldName, String deptFieldName) {
        if (StringUtils.isEmpty(companyFieldName)) {
            companyFieldName = "companyUid";
        }
        if (StringUtils.isEmpty(deptFieldName)) {
            deptFieldName = "deptUid";
        }
        //数据权限过滤指定的表别名
        String aliasTable = (String) condition.get("dataRangeAliasTable$ignore_search");

        //未指定数据权限表别名场景
        if (StringUtils.isEmpty(aliasTable)) {
            aliasTable = "main";
        }

        //无条件忽略数据范围权限的场合
        if (StringUtils.equals("true", (String) SearchConditionUtils.getConditionValue(condition, "ignoreDataRange"))) {
            return;
        }

        //系统管理员的场合，无需任何条件限制
        if (AccessUtils.isSystemManagement() || AccessUtils.isItSupport()) {
            return;
        }

        //所属分公司
        String companyUid = (String) SearchConditionUtils.getConditionValue(condition, "companyUid");

        ShiroUser currentUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //集团数据权限的场合【查看所有数据，无需设定任何条件】
        if (AccessUtils.isGroupDataRange()) {
            //无需设置条件，查看所有数据
        }
        //大区数据权限的场合【可查看自己大区内的分公司 + 管辖的分公司】
        else if (AccessUtils.isAreaDataRange() && ReflectionUtils.findField(cls, "areaUid") != null) {
            if (StringUtils.isEmpty(companyUid)) {
                condition.put(aliasTable + "." + companyFieldName + "$in", getManageCompanyUids(true));
            }
        }
        //分公司数据权限的场合
        else if (AccessUtils.isCompanyDataRange() && ReflectionUtils.findField(cls, "companyUid") != null) {
            if (StringUtils.isEmpty(companyUid)) {
                condition.put(aliasTable + "." + companyFieldName + "$in", getManageCompanyUids(false));
            }
        }
        //部门数据权限的场合
        else if (AccessUtils.isDeptDataRange() && ReflectionUtils.findField(cls, "deptUid") != null) {
            condition.put(aliasTable + "." + deptFieldName, currentUser.getDeptUid());
        }
        else {
            //所属大区字段不存在，处理中止
            if (ReflectionUtils.findField(cls, "areaUid") == null) {
                return;
            }
            //所属分公司字段不存在，处理中止
            if (ReflectionUtils.findField(cls, "companyUid") == null) {
                return;
            }
            //所属部门字段不存在，处理中止
            if (ReflectionUtils.findField(cls, "deptUid") == null) {
                return;
            }
            //默认只能查看自己的数据
            condition.put(aliasTable + ".insertUser", currentUser.getUserUid());
        }
    }

    /**
     * 获取管辖分公司UID集合
     *
     * @param isAreaDataRange 是否是大区权限
     * @return 管辖分公司UID集合
     */
    public static String getManageCompanyUids(boolean isAreaDataRange) {
        ShiroUser currentUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //大区权限的场合
        if (isAreaDataRange) {
            DeptService deptService = SpringUtils.getBean(DeptService.class);

            //取得大区的下属分公司
            Map<String, Object> map = new HashMap<>();
            map.put("main.parentDeptUid", currentUser.getBelongAreaDeptUid());
            List<DeptExt> subDeptList = deptService.search(DeptExt.class, map);
            List<String> companyUidList = new ArrayList<>();
            for (DeptExt dept : subDeptList) {
                companyUidList.add(dept.getUid());
            }

            //拼接管理分公司 + 大区所属分公司
            String companyUids = StringUtils.join(companyUidList, ',');
            if (StringUtils.isNotEmpty(currentUser.getDeptManageUids())) {
                companyUids = companyUids + "," + currentUser.getDeptManageUids();
            }
            return companyUids;
        }
        else {
            //所属分公司UID
            String companyUids = currentUser.getBelongCompanyDeptUid();

            //拼接管理分公司 + 所属分公司
            if (StringUtils.isNotEmpty(currentUser.getDeptManageUids())) {
                companyUids = companyUids + "," + currentUser.getDeptManageUids();
            }
            return companyUids;
        }
    }
}