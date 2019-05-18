<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/permission/PermissionList.js?${version}"></script>

<title>权限一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper PermissionList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">权限一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增权限
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>应用名称</label>
                    <select id="appCode" data-alias-table="main" class="form-control" data-title="应用名称">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>权限类型</label>
                    <select id="typeCd" data-alias-table="main" class="form-control" data-title="权限类型">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>权限名称</label>
                    <input id="permissionName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="权限名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>域</label>
                    <input id="domain" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="域"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>目标集合</label>
                    <input id="targets" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="目标集合"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>行为集合</label>
                    <input id="actions" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="行为集合"/>
                </div>
                <div class="clear-both dashed-line">
                </div>
                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <shiro:hasRole name="DataExport">
                        <button id="btnExport" type="button" class="btn btn-primary">
                            <i class="fa fa-cloud-download"></i>导出
                        </button>
                    </shiro:hasRole>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.PermissionListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Permission}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Permission}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Permission}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function PermissionList_Page_Load() {
        SysApp.Sys.PermissionListIns = new SysApp.Sys.PermissionList();
        var instance = SysApp.Sys.PermissionListIns;

        instance.selfInstance = "SysApp.Sys.PermissionListIns";
        instance.controller = "${ctx}/sys/permission/";
        instance.clientID = "PermissionList";
        instance.tableName = "sys_permission";
        instance.inputInstance = SysApp.Sys.PermissionInputIns;
        instance.detailInstance = SysApp.Sys.PermissionDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        PermissionList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/permission/PermissionPopupInput.jsp" %>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/permission/PermissionPopupDetail.jsp" %>