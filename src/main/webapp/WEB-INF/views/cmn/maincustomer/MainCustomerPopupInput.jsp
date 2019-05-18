<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content MainCustomerInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <div class="form-group" style="display: none">
            <div>
                <input id="uid" type="hidden">
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">主客户名称</label>
            <div class="col-sm-9">
                <input id="mainCustomerName" type="text" class="form-control required duplication" data-title="主客户名称" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="mainCustomerName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">主客户简称</label>
            <div class="col-sm-9">
                <input id="mainCustomerAbbreviation" type="text" class="form-control" data-title="主客户简称" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="customerAbbreviation_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">联系人</label>
            <div class="col-sm-9">
                <input id="contactName" type="text" class="form-control" data-title="联系人" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="contactName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">联系电话</label>
            <div class="col-sm-9">
                <input id="contactTelephone" type="text" class="form-control" data-title="联系电话" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="contactTelephone_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">客户概况</label>
            <div class="col-sm-9">
                <textarea id="introduction" rows="3" cols="30" class="form-control" data-title="客户概况" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                <label id="introduction_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <div class="col-sm-9">
                <input id="dispSeq" type="text" class="form-control" data-title="表示顺序" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true" style="width: 350px;"/>
                <label id="dispSeq_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_MainCustomer}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_MainCustomer}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/maincustomer/MainCustomerInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function MainCustomerInput_Page_Load() {
        SysApp.Cmn.MainCustomerInputIns = new SysApp.Cmn.MainCustomerInput();
        var instance = SysApp.Cmn.MainCustomerInputIns;
        instance.selfInstance = "SysApp.Cmn.MainCustomerInputIns";
        instance.controller = "${ctx}/cmn/maincustomer/";
        instance.clientID = "MainCustomerInput";

        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    MainCustomerInput_Page_Load();
    //]]>
</script>