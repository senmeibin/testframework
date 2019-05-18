<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/demo/tus.css?${version}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/Common.js?${version}"></script>
<script type="text/javascript">
    //部门数据管理权限
    SysApp.Demo.isDeptDataRange = ${subject.hasRole("DeptDataRange")};

    //分公司数据管理权限
    SysApp.Demo.isCompanyDataRange = ${subject.hasRole("CompanyDataRange")};

    //大区数据管理权限
    SysApp.Demo.isAreaDataRange = ${subject.hasRole("AreaDataRange")};

    //集团数据管理权限
    SysApp.Demo.isGroupDataRange = ${subject.hasRole("GroupDataRange")};

    //系统管理权限
    SysApp.Demo.isSystemManagement = ${subject.hasRole("SystemManagement")};

    //ITsupport管理权限
    SysApp.Demo.isItSupport = ${subject.hasRole("ItSupport")};

    //数据编辑管理权限
    SysApp.Demo.isDataEdit = ${subject.hasRole("DataEdit")};

    //金额精度[金额精度到分， 保留二位小数]
    window.amountPrecision = 2;
</script>