<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CustomerDetail-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-1 control-label">客户名称</label>
            <label class="col-sm-3 control-label content-label" id="customerName"></label>
            <label class="col-sm-1 control-label">客户简称</label>
            <label class="col-sm-3 control-label content-label" id="customerAbbreviation"></label>
            <label class="col-sm-1 control-label">主客户名称</label>
            <label class="col-sm-3 control-label content-label" id="mainCustomerName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-1 control-label">开票抬头</label>
            <label class="col-sm-3 control-label content-label" id="invoiceTitle"></label>
            <label class="col-sm-1 control-label">所属行业</label>
            <label class="col-sm-3 control-label content-label" id="industryName"></label>
            <label class="col-sm-1 control-label">开票注册地址</label>
            <label class="col-sm-3 control-label content-label" id="invoiceRegisteredAddress"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-1 control-label">开票电话</label>
            <label class="col-sm-3 control-label content-label" id="invoiceTelephone"></label>
            <label class="col-sm-1 control-label">开票开户行</label>
            <label class="col-sm-3 control-label content-label" id="invoiceBank"></label>
            <label class="col-sm-1 control-label">开票帐号</label>
            <label class="col-sm-3 control-label content-label" id="invoiceAccountNo"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-1 control-label">开票税号</label>
            <label class="col-sm-3 control-label content-label" id="invoiceTaxNo"></label>
            <label class="col-sm-1 control-label">备注</label>
            <label class="col-sm-7 control-label content-label" id="remark" data-textarea="true"></label>
        </div>

        <div class="clear-both dashed-line">
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-1 control-label">用户列表</label>
            <div class="col-sm-11" style="width:800px;padding-top: 5px;">
                <div id="userList" style="padding-top: 2px;"></div>
            </div>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/customer/CustomerDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CustomerDetail_Page_Load() {
        SysApp.Cmn.CustomerDetailIns = new SysApp.Cmn.CustomerDetail();
        var instance = SysApp.Cmn.CustomerDetailIns;
        instance.selfInstance = "SysApp.Cmn.CustomerDetailIns";
        instance.clientID = "CustomerDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/cmn/customer/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    CustomerDetail_Page_Load();
    //]]>
</script>