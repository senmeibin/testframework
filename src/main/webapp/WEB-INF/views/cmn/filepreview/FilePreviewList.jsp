<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="uri" value="${pageContext.request.requestURI}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <title>${systemName}-文件一览</title>
    <%--全局资源文件--%>
    <%@ include file="/WEB-INF/views/cmn/GlobalResource.jsp" %>
</head>
<body class="full-width">

<%--共通消息处理--%>
<%@ include file="/WEB-INF/views/cmn/CmnMessage.jsp" %>
<%--画面内容区域--%>
<div id="main-content">
    <script type="text/javascript" src="${staticContentsServer}/static/js/cmn/filepreview/FilePreviewList.js?${version}"></script>
    <title>文件一览</title>
    <div class="wrapper FilePreviewList-MainContent">
        <div class="panel">
            <div class="panel-heading">
                <em></em>
                <div class="panel-title">文件一览</div>
                <ctag:PageViewIcon></ctag:PageViewIcon>
            </div>
            <div class="panel-body">
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <div>
                    <div style="float: right;">
                        <form id="MainForm" class="margin-top-space" style="display: none;">
                            <input id="relationUid" data-alias-table="main" type="hidden" data-search-mode="in" value="${empty relationUid ? 'notExists' : relationUid}" data-allow-clear="false"/>

                        </form>
                    </div>
                    <div style="clear: both;">
                    </div>
                </div>
                <div id="divList" style="width: 100%">
                </div>
            </div>
        </div>
        <br/>
    </div>

    <script type="text/javascript">
        //<![CDATA[
        function FilePreviewList_Page_Load() {
            SysApp.Cmn.FilePreviewListIns = new SysApp.Cmn.FilePreviewList();
            var instance = SysApp.Cmn.FilePreviewListIns;
            instance.selfInstance = "SysApp.Cmn.FilePreviewListIns";
            instance.controller = "${ctx}/cmn/filepreview/";
            instance.clientID = "FilePreviewList";
            instance.tableName = "sys_attachment";
            instance.init();
        }

        FilePreviewList_Page_Load();
        //]]>
    </script>
</div>

<%--画面尾部区域--%>
<%@ include file="/WEB-INF/views/cmn/Footer.jsp" %>

</body>
</html>


