<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/role/RoleList.js?${version}"></script>

<title>角色一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper RoleList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">角色一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增角色
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
                    <label>角色编号</label>
                    <input id="roleCode" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="角色编号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>角色名称</label>
                    <input id="roleName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="角色名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>角色描述</label>
                    <input id="description" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="角色描述"/>
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
                    <button id="btnBatchSaveDispSeq" type="button" class="btn btn-primary" style="margin-left: 30px;">
                        <i class="fa fa-save"></i>批量保存表示顺序
                    </button>
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.RoleListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Role}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Role}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Role}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function RoleList_Page_Load() {
        SysApp.Sys.RoleListIns = new SysApp.Sys.RoleList();
        var instance = SysApp.Sys.RoleListIns;

        instance.selfInstance = "SysApp.Sys.RoleListIns";
        instance.controller = "${ctx}/sys/role/";
        instance.inputUrl = "${ctx}/sys/role/input";
        instance.clientID = "RoleList";
        instance.tableName = "sys_role";
        instance.detailInstance = SysApp.Sys.RoleDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        RoleList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/role/RolePopupDetail.jsp" %>