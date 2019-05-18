<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/resourcesdockingrecord/ResourcesDockingRecordList.js?${version}"></script>

<title>入孵企业资源对接记录一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ResourcesDockingRecordList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">入孵企业资源对接记录一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAddDock" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增对接记录
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>入孵企业</label>
                    <input id="companyName" data-alias-table="company" type="text" maxlength="32" class="form-control" data-title="入孵企业"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>对接时间(起)</label>
                    <ctag:CalendarSelect id="dockingDate$from_search" title="对接时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>对接时间(止)</label>
                    <ctag:CalendarSelect id="dockingDate$to_search" title="对接时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>对接人</label>
                    <input id="userName" data-alias-table="docking_person" type="text" maxlength="32" class="form-control" data-title="对接人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>对接机构</label>
                    <input id="companyName" data-alias-table="third_party" type="text" maxlength="32" class="form-control" data-title="对接机构"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>对接机构联系人</label>
                    <input id="contactName" data-alias-table="third_party_contacts" type="text" maxlength="32" class="form-control" data-title="对接机构联系人"/>
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_ResourcesDockingRecord}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ResourcesDockingRecord}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ResourcesDockingRecord}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_ResourcesDockingRecord}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_ResourcesDockingRecord}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ResourcesDockingRecordList_Page_Load() {
        SysApp.Demo.ResourcesDockingRecordListIns = new SysApp.Demo.ResourcesDockingRecordList();
        var instance = SysApp.Demo.ResourcesDockingRecordListIns;

        instance.selfInstance = "SysApp.Demo.ResourcesDockingRecordListIns";
        instance.controller = "${ctx}/demo/resourcesdockingrecord/";
        instance.inputUrl = "${ctx}/demo/resourcesdockingrecord/input";
        instance.clientID = "ResourcesDockingRecordList";
        instance.tableName = "demo_resources_docking_record";
        instance.detailInstance = SysApp.Demo.ResourcesDockingRecordDetailIns;
        instance.companyDetailInstance = SysApp.Demo.CompanyInformationDetailIns;
        instance.init();
    }

    $(function () {
        ResourcesDockingRecordList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/resourcesdockingrecord/companyinformation/CompanyInformationPopupList.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/resourcesdockingrecord/ResourcesDockingRecordPopupDetail.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/companyinformation/CompanyInformationPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Demo.ResourcesDockingRecordListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.ResourcesDockingRecordListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.ResourcesDockingRecordListIns"/>