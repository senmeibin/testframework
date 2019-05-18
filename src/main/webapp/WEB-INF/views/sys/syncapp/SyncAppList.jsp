<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/syncapp/SyncAppList.js?${version}"></script>

<title>同步项目一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper SyncAppList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">同步项目一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>
                &nbsp;新增同步项目
            </button>
            <button id="btnRefresh" type="button" class="btn btn-primary">
                <i class="fa fa-refresh"></i>
                &nbsp;刷新列表
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_SyncApp}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_SyncApp}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SyncApp}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function SyncAppList_Page_Load() {
        SysApp.Sys.SyncAppListIns = new SysApp.Sys.SyncAppList();
        var instance = SysApp.Sys.SyncAppListIns;

        instance.selfInstance = "SysApp.Sys.SyncAppListIns";
        instance.controller = "${ctx}/sys/syncapp/";
        instance.clientID = "SyncAppList";
        instance.tableName = "sys_sync_project";
        instance.inputInstance = SysApp.Sys.SyncAppInputIns;
        instance.listUrl = "${ctx}/sys/syncapp/list";

        instance.init();
    }

    $(function () {
        SyncAppList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/syncapp/SyncAppPopupInput.jsp" %>
