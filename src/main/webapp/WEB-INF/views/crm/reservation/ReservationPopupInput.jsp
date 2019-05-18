<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ReservationInput-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">学员姓名</label>
            <div class="col-sm-9">
                <input id="studentUid" type="hidden"/>
                <input id="studentName" type="text" class="form-control required" data-title="学员姓名" readonly style="width: 500px;"/>
                <label id="studentUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程顾问</label>
            <div class="col-sm-9">
                <select id="reservationBelongConsultantUserUid" class="form-control required" data-title="课程顾问" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="reservationBelongConsultantUserUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约日期</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="reservationDate" title="预约日期" showValidateError="true" width="120px"></ctag:CalendarSelect>
                <ctag:TimeSelect id="reservationTime" title="预约日期" clientID="FollowupInput" fromHour="7" toHour="22" showMinute="true" stepMinute="5" width="44px"></ctag:TimeSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约校区</label>
            <div class="col-sm-9">
                <select id="reservationCampusUid" class="form-control required" data-title="预约校区" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="reservationCampusUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约班级</label>
            <div class="col-sm-9">
                <select id="reservationCampusClassUid" class="form-control" data-title="预约班级" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="reservationCampusClassUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约目的</label>
            <div class="col-sm-9">
                <select id="purposeCd" class="form-control" data-title="预约目的" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="purposeCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约内容</label>
            <div class="col-sm-9">
                <textarea id="contents" rows="5" cols="30" class="form-control required" data-title="预约内容" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                <label id="contents_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">是否到访</label>
            <div class="col-sm-9">
                <select id="isVisited" class="form-control" data-title="是否到访" style="width: 500px;">
                    <option value="">请选择</option>
                    <option value="0">未到访</option>
                    <option value="1">已到访</option>
                </select>
                <label id="isVisited_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">是否报名</label>
            <div class="col-sm-9">
                <select id="isRegistration" class="form-control" data-title="是否报名" style="width: 500px;">
                    <option value="">请选择</option>
                    <option value="0">未报名</option>
                    <option value="1">已报名</option>
                </select>
                <label id="isRegistration_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Reservation}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Reservation}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/reservation/ReservationInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ReservationInput_Page_Load() {
        SysApp.Crm.ReservationInputIns = new SysApp.Crm.ReservationInput();
        var instance = SysApp.Crm.ReservationInputIns;
        instance.selfInstance = "SysApp.Crm.ReservationInputIns";
        instance.clientID = "ReservationInput";
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/reservation/";

        instance.init();
    }

    ReservationInput_Page_Load();
    //]]>
</script>