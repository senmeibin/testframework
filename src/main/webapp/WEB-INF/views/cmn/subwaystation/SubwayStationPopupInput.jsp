<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SubwayStationInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">地铁线路号</label>
            <div class="col-sm-9">
                <input id="subwayUid" type="hidden" class="form-control required" data-title="地铁线路UID"/>
                <input id="subwayNo" type="text" class="form-control required" data-title="地铁线路号" maxlength="64" readonly data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="subwayNo_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">地铁站名</label>
            <div class="col-sm-9">
                <input id="stationName" type="text" class="form-control required" data-title="地铁站名" allow-clear="true" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="stationName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">商圈区域</label>
            <div class="col-sm-9">
                <input id="circleArea" type="text" class="form-control" data-title="商圈区域" maxlength="64" allow-clear="true" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="circleArea_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <div class="col-sm-9">
                <input id="dispSeq" type="text" class="form-control required" data-title="表示顺序" maxlength="5" allow-clear="true" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true" style="width: 350px;"/>
                <label id="dispSeq_Error" class="validator-error"/>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_SubwayStation}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SubwayStation}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/subwaystation/SubwayStationInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SubwayStationInput_Page_Load() {
        SysApp.Cmn.SubwayStationInputIns = new SysApp.Cmn.SubwayStationInput();
        var instance = SysApp.Cmn.SubwayStationInputIns;
        instance.selfInstance = "SysApp.Cmn.SubwayStationInputIns";
        instance.clientID = "SubwayStationInput";
        instance.controller = "${ctx}/cmn/subwaystation/";

        instance.init();
    }

    SubwayStationInput_Page_Load();
    //]]>
</script>