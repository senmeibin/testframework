<%@ page language="java" pageEncoding="UTF-8" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content MainCustomerDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">主客户名称</label>
            <label class="col-sm-9 control-label content-label" id="mainCustomerName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">主客户简称</label>
            <label class="col-sm-9 control-label content-label" id="mainCustomerAbbreviation"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">联系人</label>
            <label class="col-sm-9 control-label content-label" id="contactName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">联系电话</label>
            <label class="col-sm-9 control-label content-label" id="contactTelephone"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">客户概况</label>
            <label class="col-sm-9 control-label content-label" id="introduction" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <label class="col-sm-9 control-label content-label" id="dispSeq"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/maincustomer/MainCustomerDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function MainCustomerDetail_Page_Load() {
        SysApp.Cmn.MainCustomerDetailIns = new SysApp.Cmn.MainCustomerDetail();
        var instance = SysApp.Cmn.MainCustomerDetailIns;
        instance.selfInstance = "SysApp.Cmn.MainCustomerDetailIns";
        instance.controller = "${ctx}/cmn/maincustomer/";
        instance.clientID = "MainCustomerDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    MainCustomerDetail_Page_Load();
    //]]>
</script>