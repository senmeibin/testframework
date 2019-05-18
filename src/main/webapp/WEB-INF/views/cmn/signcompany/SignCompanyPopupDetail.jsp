<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SignCompanyDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">公司名称</label>
            <label class="col-sm-9 control-label content-label" id="companyName"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">公司地址</label>
            <label class="col-sm-9 control-label content-label" id="companyAddr"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">公司电话</label>
            <label class="col-sm-9 control-label content-label" id="companyTelephone"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">开户行</label>
            <label class="col-sm-9 control-label content-label" id="bankName"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">账号</label>
            <label class="col-sm-9 control-label content-label" id="bankNumber"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">纳税人识别号</label>
            <label class="col-sm-9 control-label content-label" id="identificationNumber"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">开票分公司</label>
            <label class="col-sm-9 control-label content-label" id="invoiceCompanyName"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">公司编码</label>
            <label class="col-sm-9 control-label content-label" id="companyCode"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">公司编号</label>
            <label class="col-sm-9 control-label content-label" id="companyNumber"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <label class="col-sm-9 control-label content-label" id="dispSeq"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/signcompany/SignCompanyDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SignCompanyDetail_Page_Load() {
        SysApp.Cmn.SignCompanyDetailIns = new SysApp.Cmn.SignCompanyDetail();
        var instance = SysApp.Cmn.SignCompanyDetailIns;
        instance.selfInstance = "SysApp.Cmn.SignCompanyDetailIns";
        instance.controller = "${ctx}/cmn/signcompany/";
        instance.clientID = "SignCompanyDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    SignCompanyDetail_Page_Load();
    //]]>
</script>