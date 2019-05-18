<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/operationlog/OperationLogList.js?${version}"></script>

<title>操作日志一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper OperationLogList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">操作日志一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>操作日期(起)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$from_search" data-alias-table="main" type='text' class="form-control" data-title="操作日期(起)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>操作日期(止)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$to_search" data-alias-table="main" type='text' class="form-control" data-title="操作日期(止)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>应用名称</label>
                    <select id="appCode" data-alias-table="main" class="form-control" data-title="应用名称">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>模块名称</label>
                    <input id="moduleName" data-alias-table="main" type="text" maxlength="20" class="form-control" data-title="模块名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>方法名称</label>
                    <input id="functionName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="方法名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>访问路径</label>
                    <input id="url" data-alias-table="main" type="text" maxlength="128" class="form-control" data-title="访问路径"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>请求参数</label>
                    <input id="parameters" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="请求参数"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>操作者</label>
                    <input id="userName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="操作者"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属部门</label>
                    <ctag:ComboCheckTree valueDomId="deptUid" textDomId="deptName" aliasTable="operation_user" parentInstance="SysApp.Sys.OperationLogListIns" dataTitle="所属部门" searchMode="in"
                                         idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" dataUrl="${ctx}/sys/dept/getAllDeptList"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.OperationLogListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_OperationLog}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_OperationLog}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_OperationLog}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function OperationLogList_Page_Load() {
        SysApp.Sys.OperationLogListIns = new SysApp.Sys.OperationLogList();
        var instance = SysApp.Sys.OperationLogListIns;

        instance.selfInstance = "SysApp.Sys.OperationLogListIns";
        instance.controller = "${ctx}/sys/operationlog/";
        instance.clientID = "OperationLogList";
        instance.tableName = "sys_operation_log";
        instance.detailInstance = SysApp.Sys.OperationLogDetailIns;
        instance.userDetailInstance = "SysApp.Sys.UserDetailIns";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        OperationLogList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/operationlog/OperationLogPopupDetail.jsp" %>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/user/UserPopupDetail.jsp" %>