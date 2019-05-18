<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/sys/sys.css?${version}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/Common.js?${version}"></script>
<script type="text/javascript">
    //是否是UDC服务器
    SysApp.Sys.isUdcServer = ${isUdcServer};

    //系统管理权限
    SysApp.Sys.isSystemManagement = ${subject.hasRole("SystemManagement")};

    //ITSupport管理权限
    SysApp.Sys.isItSupport = ${subject.hasRole("ItSupport")};
</script>