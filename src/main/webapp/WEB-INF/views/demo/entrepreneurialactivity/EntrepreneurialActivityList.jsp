<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/entrepreneurialactivity/EntrepreneurialActivityList.js?${version}"></script>

<title>创业活动一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper EntrepreneurialActivityList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">创业活动一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增创业活动
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
                    <label>活动主题</label>
                    <input id="activityTopic" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="活动主题"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>会务角色</label>
                    <select id="conferenceRoleCd" data-alias-table="main" class="form-control" data-title="会务角色">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>服务类型</label>
                    <select id="serviceTypeCd" data-alias-table="main" class="form-control" data-title="服务类型">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>活动类型</label>
                    <select id="activityTypeCd" data-alias-table="main" class="form-control" data-title="活动类型">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>活动开始时间(起)</label>
                    <ctag:CalendarSelect id="startTime$from_search" title="活动开始时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>活动开始时间(止)</label>
                    <ctag:CalendarSelect id="startTime$to_search" title="活动开始时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>参与导师</label>
                    <input id="tutorName" data-alias-table="tutor" type="text" maxlength="32" class="form-control" data-title="参与导师"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>负责人</label>
                    <input id="personInChargeName" data-alias-table="person" type="text" maxlength="32" class="form-control" data-title="负责人"/>
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_EntrepreneurialActivity}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_EntrepreneurialActivity}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_EntrepreneurialActivity}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_EntrepreneurialActivity}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_EntrepreneurialActivity}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function EntrepreneurialActivityList_Page_Load() {
        SysApp.Demo.EntrepreneurialActivityListIns = new SysApp.Demo.EntrepreneurialActivityList();
        var instance = SysApp.Demo.EntrepreneurialActivityListIns;

        instance.selfInstance = "SysApp.Demo.EntrepreneurialActivityListIns";
        instance.controller = "${ctx}/demo/entrepreneurialactivity/";
        instance.clientID = "EntrepreneurialActivityList";
        instance.tableName = "demo_entrepreneurial_activity";
        instance.inputUrl = "${ctx}/demo/entrepreneurialactivity/input";
        instance.detailInstance = SysApp.Demo.EntrepreneurialActivityDetailIns;

        instance.init();
    }

    $(function () {
        EntrepreneurialActivityList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/entrepreneurialactivity/EntrepreneurialActivityPopupDetail.jsp" %>
<%@ include file="/WEB-INF/views/demo/tutor/TutorPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Demo.EntrepreneurialActivityListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.EntrepreneurialActivityListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.EntrepreneurialActivityListIns"/>