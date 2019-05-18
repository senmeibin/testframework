<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/subwaystation/SubwayStationList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SubwayStationPopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="地铁站信息一览"></ctag:ModalHeader>

    <div class="modal-body">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <input id="subwayUid" data-alias-table="main" type="hidden" maxlength="32" class="form-control" data-title="地铁线路UID"/>
            <input id="subwayNo" data-alias-table="main" data-search-mode="ignore_search" type="hidden" data-title="地铁线路号"/>
            <input id="provinceName" data-alias-table="main" data-search-mode="ignore_search" type="hidden" data-title="省份"/>
            <input id="cityName" data-alias-table="main" data-search-mode="ignore_search" type="hidden" data-title="城市"/>
            <div class="col-md-2 form-group">
                <label>地铁站名</label>
                <input id="stationName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="地铁站名"/>
            </div>
            <div class="col-md-2 form-group">
                <label>商圈区域</label>
                <input id="circleArea" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="商圈区域"/>
            </div>
            <div class="col-md-2 form-group" style="width: 370px;">
                <label>
                    &nbsp;
                </label>
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
                    &nbsp;&nbsp;&nbsp;
                    <button id="btnInsertStation" type="button" class="btn btn-primary">
                        <i class="fa fa-plus"></i>增加地铁站
                    </button>
                </div>
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_SubwayStation}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SubwayStation}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function SubwayStationPopupList_Page_Load() {
        SysApp.Cmn.SubwayStationPopupListIns = new SysApp.Cmn.SubwayStationList();
        var instance = SysApp.Cmn.SubwayStationPopupListIns;

        instance.selfInstance = "SysApp.Cmn.SubwayStationPopupListIns";
        instance.clientID = "SubwayStationPopupList";
        instance.tableName = "cmn_subway_station";
        instance.inputInstance = SysApp.Cmn.SubwayStationInputIns;
        instance.detailInstance = SysApp.Cmn.SubwayStationDetailIns;
        instance.controller = "${ctx}/cmn/subwaystation/";

        instance.init();
    }

    $(function () {
        SubwayStationPopupList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/cmn/subwaystation/SubwayStationPopupInput.jsp" %>