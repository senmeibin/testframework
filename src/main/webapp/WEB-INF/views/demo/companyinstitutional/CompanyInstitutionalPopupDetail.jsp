<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyInstitutionalDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">企业</label>
            <label class="col-lg-4 control-label content-label" id="companyName"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">控股母公司</label>
            <label class="col-lg-4 control-label content-label" id="parentCompanyName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">纳税人识别号</label>
            <label class="col-lg-4 control-label content-label" id="taxpayerIdentificationNo"></label>
        </div>
        <div class="form-group form-inline">

            <label class="col-lg-2 control-label">地址</label>
            <label class="col-lg-4 control-label content-label" id="address"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">经营范围（营业执照）</label>
            <label class="col-lg-8 control-label content-label" id="businessScope"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinstitutional/CompanyInstitutionalDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyInstitutionalDetail_Page_Load() {
        SysApp.Demo.CompanyInstitutionalDetailIns = new SysApp.Demo.CompanyInstitutionalDetail();
        var instance = SysApp.Demo.CompanyInstitutionalDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyInstitutionalDetailIns";
        instance.clientID = "CompanyInstitutionalDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companyinstitutional/";
        instance.init();
    }

    CompanyInstitutionalDetail_Page_Load();
    //]]>
</script>