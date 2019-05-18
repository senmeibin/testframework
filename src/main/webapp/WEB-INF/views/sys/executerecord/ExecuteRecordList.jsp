<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/executerecord/ExecuteRecordList.js?${version}"></script>

<title>执行记录一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ExecuteRecordList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">执行记录一览（<c:if test="${isTaskServer == 'on'}"><span style="color:#3CC457">${taskServerRemark}</span></c:if><c:if test="${isTaskServer == 'off'}"><span style="color:#ff0000">${taskServerRemark}</span></c:if>）</div>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>功能模块</label>
                    <input id="functionCode" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="功能模块"/>
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
                    <label>任务状态</label>
                    <select id="functionStatusCd" data-alias-table="main" class="form-control" data-title="任务状态">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>最后执行时间(起)</label>
                    <div class='input-group datetime-picker'>
                        <input id="lastExecuteDate$from_search" data-alias-table="main" type='text' class="form-control" data-title="最后执行时间(起)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>最后执行时间(止)</label>
                    <div class='input-group datetime-picker'>
                        <input id="lastExecuteDate$to_search" data-alias-table="main" type='text' class="form-control" data-title="最后执行时间(止)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>来源系统</label>
                    <input id="sourceSystemName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="来源系统"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>目标系统</label>
                    <input id="destSystemName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="目标系统"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.ExecuteRecordListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_ExecuteRecord}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ExecuteRecord}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ExecuteRecord}"/>
            <%--自定义列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_ExecuteRecord}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ExecuteRecordList_Page_Load() {
        SysApp.Sys.ExecuteRecordListIns = new SysApp.Sys.ExecuteRecordList();
        var instance = SysApp.Sys.ExecuteRecordListIns;

        instance.selfInstance = "SysApp.Sys.ExecuteRecordListIns";
        instance.controller = "${ctx}/sys/executerecord/";
        instance.clientID = "ExecuteRecordList";
        instance.tableName = "sys_execute_record";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        ExecuteRecordList_Page_Load();
    });
    //]]>
</script>
<ctag:ColumnSettingPopup pageInstance="SysApp.Sys.ExecuteRecordListIns" width="1000px"/>
<%--POPUP详细执行日志表画面--%>
<%@ include file="/WEB-INF/views/sys/executelog/ExecuteLogPopupList.jsp" %>