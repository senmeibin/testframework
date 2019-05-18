﻿<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/subwaystation/SubwayStationList.js?${version}"></script>

<title>地铁站信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper SubwayStationList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">地铁站信息一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增地铁站信息
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>地铁站名</label>
                    <input id="stationName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="地铁站名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>商圈区域</label>
                    <input id="circleArea" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="商圈区域"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Cmn.SubwayStationListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_SubwayStation}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_SubwayStation}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SubwayStation}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function SubwayStationList_Page_Load() {
        SysApp.Cmn.SubwayStationListIns = new SysApp.Cmn.SubwayStationList();
        var instance = SysApp.Cmn.SubwayStationListIns;

        instance.selfInstance = "SysApp.Cmn.SubwayStationListIns";
        instance.controller = "${ctx}/cmn/subwaystation/";
        instance.clientID = "SubwayStationList";
        instance.tableName = "cmn_subway_station";
        instance.pageSize = "${pageSize}";
        instance.inputInstance = SysApp.Cmn.SubwayStationInputIns;
        instance.detailInstance = SysApp.Cmn.SubwayStationDetailIns;

        instance.init();
    }

    $(function() {
        SubwayStationList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/cmn/subwaystation/SubwayStationPopupInput.jsp" %>