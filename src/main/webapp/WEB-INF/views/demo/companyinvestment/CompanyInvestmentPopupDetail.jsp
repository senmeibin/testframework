<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyInvestmentDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">企业名称</label>
            <label class="col-lg-4 control-label content-label" id="companyName"></label>
            <label class="col-lg-1 control-label">接收投资时间</label>
            <label class="col-lg-4 control-label content-label" id="investmentDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">投资人</label>
            <label class="col-lg-4 control-label content-label" id="investor"></label>
            <label class="col-lg-1 control-label">投资金额(万元)</label>
            <label class="col-lg-4 control-label content-label" id="investmentAmount" data-money="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">占股权比例(%)</label>
            <label class="col-lg-4 control-label content-label" id="proportionOfStock"></label>
            <label class="col-lg-1 control-label">账面估值(万元)</label>
            <label class="col-lg-4 control-label content-label" id="bookValuation" data-money="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">属于孵化基金</label>
            <label class="col-lg-4 control-label content-label" id="incubationFundName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinvestment/CompanyInvestmentDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyInvestmentDetail_Page_Load() {
        SysApp.Demo.CompanyInvestmentDetailIns = new SysApp.Demo.CompanyInvestmentDetail();
        var instance = SysApp.Demo.CompanyInvestmentDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyInvestmentDetailIns";
        instance.clientID = "CompanyInvestmentDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companyinvestment/";
        instance.init();
    }

    CompanyInvestmentDetail_Page_Load();
    //]]>
</script>