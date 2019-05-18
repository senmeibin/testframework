<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/visitorrecords/VisitorRecordsList.js?${version}"></script>

<title>来访记录一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper VisitorRecordsList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">来访记录一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增来访记录
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>所属基地</label>
                    <input id="baseName" data-alias-table="base" type="text" maxlength="32" class="form-control" data-title="所属基地"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>来访单位</label>
                    <input id="visitingUnits" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="来访单位"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>来访时间(起)</label>
                    <ctag:CalendarSelect id="visitingTime$from_search" title="来访时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>来访时间(止)</label>
                    <ctag:CalendarSelect id="visitingTime$to_search" title="来访时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>接待人</label>
                    <input id="receptionistName" data-alias-table="receptionist" type="text" maxlength="32" class="form-control" data-title="接待人"/>
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
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_VisitorRecords}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_VisitorRecords}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_VisitorRecords}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function VisitorRecordsList_Page_Load() {
        SysApp.Demo.VisitorRecordsListIns = new SysApp.Demo.VisitorRecordsList();
        var instance = SysApp.Demo.VisitorRecordsListIns;

        instance.selfInstance = "SysApp.Demo.VisitorRecordsListIns";
        instance.controller = "${ctx}/demo/visitorrecords/";
        instance.clientID = "VisitorRecordsList";
        instance.tableName = "demo_visitor_records";
        instance.inputInstance = SysApp.Demo.VisitorRecordsInputIns;

        instance.init();
    }

    $(function () {
        VisitorRecordsList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/demo/visitorrecords/VisitorRecordsPopupInput.jsp" %>

<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.VisitorRecordsListIns"/>