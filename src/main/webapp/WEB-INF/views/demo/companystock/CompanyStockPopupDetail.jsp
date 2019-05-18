<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyStockDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">企业</label>
            <label class="col-lg-4 control-label content-label" id="companyName"></label>
            <label class="col-lg-2 control-label">股东类型</label>
            <label class="col-lg-4 control-label content-label" id="shareholderTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">股东名称</label>
            <label class="col-lg-4 control-label content-label" id="shareholderName"></label>
            <label class="col-lg-2 control-label">证件类型</label>
            <label class="col-lg-4 control-label content-label" id="certificateTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">上市公司</label>
            <label class="col-lg-4 control-label content-label" id="listedCompanyName"></label>
            <label class="col-lg-2 control-label">入选千人计划</label>
            <label class="col-lg-4 control-label content-label" id="thousandsPeoplePlanName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">所占股份</label>
            <label class="col-lg-4 control-label content-label" id="sharesProportion"></label>
            <label class="col-lg-2 control-label">投资总额(万元)</label>
            <label class="col-lg-4 control-label content-label" id="totalInvestment" data-money="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">境外公司或外籍</label>
            <label class="col-lg-4 control-label content-label" id="overseasCompanyName"></label>
            <label class="col-lg-2 control-label">外资部门所占股份总和(%)</label>
            <label class="col-lg-4 control-label content-label" id="totalShareForeignCapital"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">上市企业所占股份总和(%)</label>
            <label class="col-lg-4 control-label content-label" id="totalSharesListedCompany"></label>
            <label class="col-lg-2 control-label">风险投资（基金）公司</label>
            <label class="col-lg-4 control-label content-label" id="fundCompanyName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">第一投资时间</label>
            <label class="col-lg-4 control-label content-label" id="firstInvestmentDate" data-date="true"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companystock/CompanyStockDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyStockDetail_Page_Load() {
        SysApp.Demo.CompanyStockDetailIns = new SysApp.Demo.CompanyStockDetail();
        var instance = SysApp.Demo.CompanyStockDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyStockDetailIns";
        instance.clientID = "CompanyStockDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companystock/";
        instance.init();
    }

    CompanyStockDetail_Page_Load();
    //]]>
</script>