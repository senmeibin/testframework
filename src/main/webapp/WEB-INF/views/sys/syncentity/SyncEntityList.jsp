<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/syncentity/SyncEntityList.js?${version}"></script>

<title>同步实体类一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper SyncEntityList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">同步对象一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>
                &nbsp;新增同步实体类
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>类名称</label>
                    <input id="entityClassName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="类名称"/>
                </div>
                <div class="clear-both dashed-line">
                </div>

                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>
                        &nbsp;查询
                    </button>
                    <shiro:hasRole name="DataExport">
                        <button id="btnExport" type="button" class="btn btn-primary">
                            <i class="fa fa-cloud-download"></i>
                            &nbsp;导出
                        </button>
                    </shiro:hasRole>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>
                        &nbsp;清空
                    </button>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_SyncEntity}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_SyncEntity}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SyncEntity}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function SyncEntityList_Page_Load() {
        SysApp.Sys.SyncEntityListIns = new SysApp.Sys.SyncEntityList();
        var instance = SysApp.Sys.SyncEntityListIns;

        instance.selfInstance = "SysApp.Sys.SyncEntityListIns";
        instance.controller = "${ctx}/sys/syncentity/";
        instance.clientID = "SyncEntityList";
        instance.tableName = "sys_sync_entity";
        instance.inputInstance = SysApp.Sys.SyncEntityInputIns;

        instance.init();
    }

    $(function () {
        SyncEntityList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/syncentity/SyncEntityPopupInput.jsp" %>

<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/syncentity/operationlog/OperationLogList.jsp" %>

<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Sys.SyncEntityListIns"/>

