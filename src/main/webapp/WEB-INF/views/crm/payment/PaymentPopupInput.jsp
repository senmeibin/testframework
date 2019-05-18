<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content PaymentInput-MainContent" style="width: 1000px; display: none;">
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
                <select id="paymentBelongConsultantUserUid" class="form-control required" data-title="课程顾问" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="paymentBelongConsultantUserUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">缴费日期</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="paymentDate" title="缴费日期" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
                <ctag:TimeSelect id="paymentTime" title="缴费时间" clientID="FollowupInput" fromHour="7" toHour="22" showMinute="true" stepMinute="5" width="44px"></ctag:TimeSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">收据序号</label>
            <div class="col-sm-9">
                <input id="receiptNumber" type="text" class="form-control required" data-title="收据序号" maxlength="32" data-rangelength="[0,32]" style="width: 500px;"/>
                <label id="receiptNumber_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">付款人</label>
            <div class="col-sm-9">
                <input id="payer" type="text" class="form-control required" data-title="付款人" maxlength="32" data-rangelength="[0,32]" style="width: 500px;"/>
                <label id="payer_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">付款方式</label>
            <div class="col-sm-9">
                <select id="paymentMethodCd" class="form-control required" data-title="付款方式" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="paymentMethodCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">费用科目</label>
            <div class="col-sm-9">
                <select id="itemTypeCd" class="form-control required" data-title="费用科目" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="itemTypeCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应付金额</label>
            <div class="col-sm-9">
                <input id="payableAmount" type="text" class="form-control" data-title="应付金额" maxlength="13" data-rangelength="[0,13]" data-range="[-9999999999,9999999999]" data-number="true" style="width: 500px;"/>
                <label id="payableAmount_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">实收金额</label>
            <div class="col-sm-9">
                <input id="paymentAmount" type="text" class="form-control required" data-title="实收金额" maxlength="13" data-rangelength="[0,13]" data-range="[-9999999999,9999999999]" data-number="true" style="width: 500px;"/>
                <label id="paymentAmount_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">收费情况</label>
            <div class="col-sm-9">
                <input id="paymentDetails" type="text" class="form-control" data-title="收费情况" maxlength="256" data-rangelength="[0,256]" style="width: 500px;"/>
                <label id="paymentDetails_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">收款人</label>
            <div class="col-sm-9">
                <select id="payeeUserUid" class="form-control required" data-title="收款人" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="payeeUserUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">退款金额</label>
            <div class="col-sm-9">
                <input id="refundAmount" type="text" class="form-control" data-title="退款金额" maxlength="13" data-rangelength="[0,13]" data-range="[-9999999999,9999999999]" data-number="true" style="width: 500px;"/>
                <label id="refundAmount_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">退款原因</label>
            <div class="col-sm-9">
                <input id="refundReason" type="text" class="form-control" data-title="退款原因" maxlength="256" data-rangelength="[0,256]" style="width: 500px;"/>
                <label id="refundReason_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">退款人</label>
            <div class="col-sm-9">
                <select id="refundUserUid" class="form-control" data-title="退款人" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="refundUserUid_Error" class="validator-error"></label>
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
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Payment}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Payment}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/payment/PaymentInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PaymentInput_Page_Load() {
        SysApp.Crm.PaymentInputIns = new SysApp.Crm.PaymentInput();
        var instance = SysApp.Crm.PaymentInputIns;
        instance.selfInstance = "SysApp.Crm.PaymentInputIns";
        instance.clientID = "PaymentInput";
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/payment/";

        instance.init();
    }

    PaymentInput_Page_Load();
    //]]>
</script>