<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/weeklyreport/WeeklyReportList.js?${version}"></script>

<title>周报一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper WeeklyReportList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">周报一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增周报
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
                    <label>人员姓名</label>
                    <input id="userName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="人员姓名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>填写时间(起)</label>
                    <ctag:CalendarSelect id="fillTimeStart$from_search" title="填写时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>填写时间(止)</label>
                    <ctag:CalendarSelect id="fillTimeStart$to_search" title="填写时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="clear-both dashed-line">
                </div>

                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_WeeklyReport}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_WeeklyReport}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_WeeklyReport}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function WeeklyReportList_Page_Load() {
        SysApp.Demo.WeeklyReportListIns = new SysApp.Demo.WeeklyReportList();
        var instance = SysApp.Demo.WeeklyReportListIns;

        instance.selfInstance = "SysApp.Demo.WeeklyReportListIns";
        instance.controller = "${ctx}/demo/weeklyreport/";
        instance.inputUrl = "${ctx}/demo/weeklyreport/input";
        instance.clientID = "WeeklyReportList";
        instance.tableName = "demo_weekly_report";
        instance.detailInstance = SysApp.Demo.WeeklyReportDetailIns;
        instance.exportReportMethod = "exportReport";
        instance.currentUser = "${loginUser.userUid}";

        instance.init();
    }

    $(function () {
        WeeklyReportList_Page_Load();
    });
    //]]>
</script>

<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.WeeklyReportListIns"/>