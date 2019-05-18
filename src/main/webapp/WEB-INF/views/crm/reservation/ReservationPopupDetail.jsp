<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ReservationDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">学员姓名</label>
            <label class="col-sm-9 control-label content-label" id="studentName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">所属校区</label>
            <label class="col-sm-9 control-label content-label" id="reservationBelongCampusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程顾问</label>
            <label class="col-sm-9 control-label content-label" id="reservationBelongConsultantUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约日期</label>
            <label class="col-sm-9 control-label content-label" id="reservationDate" data-datetime="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约校区</label>
            <label class="col-sm-9 control-label content-label" id="reservationCampusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约班级</label>
            <label class="col-sm-9 control-label content-label" id="reservationCampusClassName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约目的</label>
            <label class="col-sm-9 control-label content-label" id="purposeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预约内容</label>
            <label class="col-sm-9 control-label content-label" id="contents" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">是否到访</label>
            <label class="col-sm-9 control-label content-label" id="isVisited"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">是否报名</label>
            <label class="col-sm-9 control-label content-label" id="isRegistration"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">添加日期</label>
            <label class="col-sm-9 control-label content-label" id="insertDate" data-datetime="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/reservation/ReservationDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ReservationDetail_Page_Load() {
        SysApp.Crm.ReservationDetailIns = new SysApp.Crm.ReservationDetail();
        var instance = SysApp.Crm.ReservationDetailIns;
        instance.selfInstance = "SysApp.Crm.ReservationDetailIns";
        instance.clientID = "ReservationDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/reservation/";
        instance.init();
    }

    ReservationDetail_Page_Load();
    //]]>
</script>