<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content PaymentDetail-MainContent" style="width: 1000px; display: none;">
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
            <label class="col-sm-9 control-label content-label" id="paymentBelongCampusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程顾问</label>
            <label class="col-sm-9 control-label content-label" id="paymentBelongConsultantUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">缴费日期</label>
            <label class="col-sm-9 control-label content-label" id="paymentDate" data-datetime="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">收据序号</label>
            <label class="col-sm-9 control-label content-label" id="receiptNumber"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">付款人</label>
            <label class="col-sm-9 control-label content-label" id="payer"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">付款方式</label>
            <label class="col-sm-9 control-label content-label" id="paymentMethodName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">费用科目</label>
            <label class="col-sm-9 control-label content-label" id="itemTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应付金额</label>
            <label class="col-sm-9 control-label content-label" id="payableAmount"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">实收金额</label>
            <label class="col-sm-9 control-label content-label" id="paymentAmount"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">收费情况</label>
            <label class="col-sm-9 control-label content-label" id="paymentDetails"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">收款人</label>
            <label class="col-sm-9 control-label content-label" id="payeeUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">退款金额</label>
            <label class="col-sm-9 control-label content-label" id="refundAmount"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">退款原因</label>
            <label class="col-sm-9 control-label content-label" id="refundReason"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">退款人</label>
            <label class="col-sm-9 control-label content-label" id="refundUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/payment/PaymentDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PaymentDetail_Page_Load() {
        SysApp.Crm.PaymentDetailIns = new SysApp.Crm.PaymentDetail();
        var instance = SysApp.Crm.PaymentDetailIns;
        instance.selfInstance = "SysApp.Crm.PaymentDetailIns";
        instance.clientID = "PaymentDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/payment/";
        instance.init();
    }

    PaymentDetail_Page_Load();
    //]]>
</script>