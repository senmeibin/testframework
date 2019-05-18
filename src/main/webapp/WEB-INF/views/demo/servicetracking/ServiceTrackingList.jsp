<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/servicetracking/ServiceTrackingList.js?${version}"></script>

<title>企业服务跟踪一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ServiceTrackingList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">企业服务跟踪一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增企业服务跟踪
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
                    <label>走访企业</label>
                    <input id="companyName" data-alias-table="company" type="text" maxlength="32" class="form-control" data-title="走访企业"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>走访人</label>
                    <input id="visitUserName" data-alias-table="visit_user" type="text" maxlength="32" class="form-control" data-title="走访人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>企业拜访人</label>
                    <input id="visitor" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="企业拜访人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>下次跟踪时间(起)</label>
                    <ctag:CalendarSelect id="nextTrackingTime$from_search" title="下次跟踪时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>下次跟踪时间(止)</label>
                    <ctag:CalendarSelect id="nextTrackingTime$to_search" title="下次跟踪时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>下次跟踪服务人</label>
                    <input id="nextTrackingUserName" data-alias-table="next_tracking_user" type="text" maxlength="32" class="form-control" data-title="下次跟踪服务人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>走访时间(起)</label>
                    <ctag:CalendarSelect id="visitTime$from_search" title="走访时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>走访时间(止)</label>
                    <ctag:CalendarSelect id="visitTime$to_search" title="走访时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>走访状态</label>
                    <select id="visitStatusCd" data-alias-table="main" class="form-control" data-title="走访状态">
                        <option value="">请选择</option>
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
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_ServiceTracking}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ServiceTracking}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ServiceTracking}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_ServiceTracking}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_ServiceTracking}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ServiceTrackingList_Page_Load() {
        SysApp.Demo.ServiceTrackingListIns = new SysApp.Demo.ServiceTrackingList();
        var instance = SysApp.Demo.ServiceTrackingListIns;

        instance.selfInstance = "SysApp.Demo.ServiceTrackingListIns";
        instance.controller = "${ctx}/demo/servicetracking/";
        instance.clientID = "ServiceTrackingList";
        instance.tableName = "demo_service_tracking";
        instance.inputUrl = "${ctx}/demo/servicetracking/input";
        instance.detailInstance = SysApp.Demo.ServiceTrackingDetailIns;

        instance.init();
    }

    $(function () {
        ServiceTrackingList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/servicetracking/ServiceTrackingPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Demo.ServiceTrackingListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.ServiceTrackingListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.ServiceTrackingListIns"/>