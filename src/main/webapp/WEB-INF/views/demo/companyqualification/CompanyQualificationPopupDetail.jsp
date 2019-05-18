<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyQualificationDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">企业名称</label>
            <label class="col-lg-4 control-label content-label" id="companyName"></label>
            <label class="col-lg-1 control-label">认定机构</label>
            <label class="col-lg-4 control-label content-label" id="certificationAuthority"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">编号</label>
            <label class="col-lg-4 control-label content-label" id="serialNumber"></label>
            <label class="col-lg-1 control-label">认定年份</label>
            <label class="col-lg-4 control-label content-label" id="certificationYear"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">复核/验收年份</label>
            <label class="col-lg-4 control-label content-label" id="acceptanceYear"></label>
            <label class="col-lg-1 control-label">资助金额(万元)</label>
            <label class="col-lg-4 control-label content-label" id="subsidyAmount" data-money="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyqualification/CompanyQualificationDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyQualificationDetail_Page_Load() {
        SysApp.Demo.CompanyQualificationDetailIns = new SysApp.Demo.CompanyQualificationDetail();
        var instance = SysApp.Demo.CompanyQualificationDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyQualificationDetailIns";
        instance.clientID = "CompanyQualificationDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companyqualification/";
        instance.init();
    }

    CompanyQualificationDetail_Page_Load();
    //]]>
</script>