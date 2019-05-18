<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/reservation/ReservationPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ReservationPopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="预约记录一览"></ctag:ModalHeader>

    <div class="modal-body">
        <button id="btnAdd" type="button" class="btn btn-primary">
            <i class="fa fa-plus"></i>新增预约记录
        </button>
        <div class="clear-both dashed-line">
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <input id="studentUid" data-alias-table="main" data-allow-clear="false" type="hidden"/>
        </form>
        <%--学员基本信息--%>
        <i class="fa fa-vcard" style="font-size: 18px;"></i>&nbsp;<span id="studentInfo"></span>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Reservation}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Reservation}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/reservation/ReservationPopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/reservation/ReservationPopupDetail.jsp" %>

<script type="text/javascript">
    //<![CDATA[
    function ReservationPopupList_Page_Load() {
        SysApp.Crm.ReservationPopupListIns = new SysApp.Crm.ReservationPopupList();
        var instance = SysApp.Crm.ReservationPopupListIns;

        instance.selfInstance = "SysApp.Crm.ReservationPopupListIns";
        instance.clientID = "ReservationPopupList";
        instance.tableName = "crm_reservation";
        instance.inputInstance = SysApp.Crm.ReservationInputIns;
        instance.detailInstance = SysApp.Crm.ReservationDetailIns;
        instance.controller = "${ctx}/crm/reservation/";
        instance.entry = "${entry}";

        instance.init();
    }

    $(function () {
        ReservationPopupList_Page_Load();
    });
    //]]>
</script>