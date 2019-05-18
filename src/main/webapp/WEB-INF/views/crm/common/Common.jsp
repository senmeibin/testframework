<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/crm/crm.css?${version}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/Common.js?${version}"></script>
<script type="text/javascript">
    //部门数据管理权限
    SysApp.Crm.isDeptDataRange = ${subject.hasRole("DeptDataRange")};

    //分公司数据管理权限
    SysApp.Crm.isCompanyDataRange = ${subject.hasRole("CompanyDataRange")};

    //大区数据管理权限
    SysApp.Crm.isAreaDataRange = ${subject.hasRole("AreaDataRange")};

    //集团数据管理权限
    SysApp.Crm.isGroupDataRange = ${subject.hasRole("GroupDataRange")};

    //系统管理权限
    SysApp.Crm.isSystemManagement = ${subject.hasRole("SystemManagement")};

    //ITsupport管理权限
    SysApp.Crm.isItSupport = ${subject.hasRole("ItSupport")};

    //数据编辑管理权限
    SysApp.Crm.isDataEdit = ${subject.hasRole("DataEdit")};

    //金额精度[金额精度到分， 保留二位小数]
    window.amountPrecision = 2;
</script>