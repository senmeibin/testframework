<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/meetingrecord/MeetingRecordList.js?${version}"></script>

<title>会议记录一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper MeetingRecordList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">会议记录一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增会议记录
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
                    <label>会议主题</label>
                    <input id="subject" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="会议主题"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>会议时间(起)</label>
                    <ctag:CalendarSelect id="meetingTime$from_search" title="会议时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>会议时间(止)</label>
                    <ctag:CalendarSelect id="meetingTime$to_search" title="会议时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>会议地点</label>
                    <input id="meetingPlace" data-alias-table="main" type="text" maxlength="128" class="form-control" data-title="会议地点"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>与会人员</label>
                    <input id="attendees" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="与会人员"/>
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_MeetingRecord}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_MeetingRecord}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_MeetingRecord}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_MeetingRecord}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_MeetingRecord}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function MeetingRecordList_Page_Load() {
        SysApp.Demo.MeetingRecordListIns = new SysApp.Demo.MeetingRecordList();
        var instance = SysApp.Demo.MeetingRecordListIns;

        instance.selfInstance = "SysApp.Demo.MeetingRecordListIns";
        instance.controller = "${ctx}/demo/meetingrecord/";
        instance.inputUrl = "${ctx}/demo/meetingrecord/input";
        instance.clientID = "MeetingRecordList";
        instance.tableName = "demo_meeting_record";
        instance.detailInstance = SysApp.Demo.MeetingRecordDetailIns;

        instance.init();
    }

    $(function () {
        MeetingRecordList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/meetingrecord/MeetingRecordPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Demo.MeetingRecordListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.MeetingRecordListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.MeetingRecordListIns"/>