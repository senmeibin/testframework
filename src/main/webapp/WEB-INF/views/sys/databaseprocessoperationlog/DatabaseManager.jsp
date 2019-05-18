<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/databaseprocessoperationlog/DatabaseManagerTab.js?${version}"></script>

<title>数据库进程管理</title>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">数据库进程管理</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <div id="DatabaseManager">
                <ul id="DatabaseManager_Tabs" class="tmv-customize">
                    <div style="width: 530px;" id="DatabaseManager_Tabs_LiContainer">
                        <li id="DatabaseManager_Tab0" class="active"><span>数据库进程信息</span></li>
                        <li id="DatabaseManager_Tab1" class="enableout"><span>数据库进程操作日志</span></li>
                        <em></em>
                    </div>
                </ul>

                <div id="DatabaseManager_Views" class="tmv-content">

                    <div id="DatabaseManager_View0">
                        <%@include file="/WEB-INF/views/sys/databaseprocessoperationlog/DatabaseProcessInfoList.jsp" %>
                    </div>

                    <div id="DatabaseManager_View1">
                        <%@include file="/WEB-INF/views/sys/databaseprocessoperationlog/DatabaseProcessOperationLogList.jsp" %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function DatabaseManagerTab_Page_Load() {
        SysApp.Sys.DatabaseManagerTabIns = new SysApp.Sys.DatabaseManagerTab();
        var instance = SysApp.Sys.DatabaseManagerTabIns;
        instance.selfInstance = "SysApp.Sys.DatabaseManagerTabIns";
        instance.controller = "${ctx}/sys/databaseprocessoperationlog/";
        instance.clientID = "DatabaseManagerTab";
        instance.tabNum = "${empty tabNum ?0:tabNum}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }
    $(function () {
        DatabaseManagerTab_Page_Load();
    })
    //]]>
</script>