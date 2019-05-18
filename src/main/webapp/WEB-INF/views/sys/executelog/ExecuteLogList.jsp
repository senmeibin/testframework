<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/executelog/ExecuteLogList.js?${version}"></script>

<title>执行日志一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ExecuteLogList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">执行日志一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>执行时间(起)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$from_search" data-alias-table="main" type='text' class="form-control required" data-title="执行时间(起)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>执行时间(止)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$to_search" data-alias-table="main" type='text' class="form-control required" data-title="执行时间(止)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>功能模块</label>
                    <input id="functionCode" data-alias-table="main" type="text" maxlength="64" class="form-control required" data-title="功能模块"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>功能名称</label>
                    <input id="functionName" data-alias-table="main" type="text" maxlength="128" class="form-control" data-title="功能名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>功能类型</label>
                    <select id="functionTypeCd" data-alias-table="main" class="form-control" data-title="功能类型">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>来源系统</label>
                    <input id="sourceSystemName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="来源系统"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>目标系统</label>
                    <input id="destSystemName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="目标系统"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>结果编码</label>
                    <input id="resultCode" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="结果编码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>结果消息</label>
                    <input id="resultMessage" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="结果消息"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>执行结果</label>
                    <select id="result" data-alias-table="main" class="form-control" data-title="功能类型">
                        <option value="">请选择</option>
                        <option value="1">成功</option>
                        <option value="-1">失败</option>
                    </select>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.ExecuteLogListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_ExecuteLog}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ExecuteLog}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ExecuteLog}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ExecuteLogList_Page_Load() {
        SysApp.Sys.ExecuteLogListIns = new SysApp.Sys.ExecuteLogList();
        var instance = SysApp.Sys.ExecuteLogListIns;

        instance.selfInstance = "SysApp.Sys.ExecuteLogListIns";
        instance.controller = "${ctx}/sys/executelog/";
        instance.clientID = "ExecuteLogList";
        instance.tableName = "sys_execute_log";
        instance.detailInstance = SysApp.Sys.ExecuteLogDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        ExecuteLogList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细执行日志表画面--%>
<%@ include file="/WEB-INF/views/sys/executelog/ExecuteLogPopupDetail.jsp" %>
