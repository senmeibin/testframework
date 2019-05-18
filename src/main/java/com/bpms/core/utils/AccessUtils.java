package com.bpms.core.utils;

import org.apache.shiro.SecurityUtils;

public class AccessUtils {
    /**
     * 系统管理权限
     */
    public static boolean isSystemManagement() {
        return SecurityUtils.getSubject().hasRole("SystemManagement");
    }

    /**
     * Saas平台管理
     */
    public static boolean isSaasManagement() {
        return SecurityUtils.getSubject().hasRole("SaasManagement");
    }

    /**
     * 基础数据管理
     */
    public static boolean isBasicDataManagement() {
        return SecurityUtils.getSubject().hasRole("BasicDataManagement");
    }

    /**
     * 技术支持
     */
    public static boolean isItSupport() {
        return SecurityUtils.getSubject().hasRole("ItSupport");
    }

    /**
     * 数据编辑权限
     */
    public static boolean isDataEdit() {
        return SecurityUtils.getSubject().hasRole("DataEdit");
    }

    /**
     * 数据查看权限
     */
    public static boolean isDataView() {
        return SecurityUtils.getSubject().hasRole("DataView");
    }

    /**
     * 数据导出权限
     */
    public static boolean isDataExport() {
        return SecurityUtils.getSubject().hasRole("DataExport");
    }

    /**
     * 部门数据管理权限
     */
    public static boolean isDeptDataRange() {
        return SecurityUtils.getSubject().hasRole("DeptDataRange");
    }

    /**
     * 分公司数据管理权限
     */
    public static boolean isCompanyDataRange() {
        return SecurityUtils.getSubject().hasRole("CompanyDataRange");
    }

    /**
     * 大区数据管理权限
     */
    public static boolean isAreaDataRange() {
        return SecurityUtils.getSubject().hasRole("AreaDataRange");
    }

    /**
     * 集团数据管理权限
     */
    public static boolean isGroupDataRange() {
        return SecurityUtils.getSubject().hasRole("GroupDataRange");
    }
}
