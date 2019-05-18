<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyAdditionalDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">企业</label>
            <label class="col-lg-4 control-label content-label" id="companyName"></label>
            <label class="col-lg-1 control-label">证券代码</label>
            <label class="col-lg-4 control-label content-label" id="stockCode"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">上市时间</label>
            <label class="col-lg-4 control-label content-label" id="listedTime" data-date="true"></label>
            <label class="col-lg-1 control-label">上市市场</label>
            <label class="col-lg-4 control-label content-label" id="listedTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">总股本</label>
            <label class="col-lg-4 control-label content-label" id="totalShare"></label>
            <label class="col-lg-1 control-label">年底市值(万元)</label>
            <label class="col-lg-4 control-label content-label" id="yearEndMarketValue" data-money="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">退市时间</label>
            <label class="col-lg-4 control-label content-label" id="delistingDate" data-date="true"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyadditional/CompanyAdditionalDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyAdditionalDetail_Page_Load() {
        SysApp.Demo.CompanyAdditionalDetailIns = new SysApp.Demo.CompanyAdditionalDetail();
        var instance = SysApp.Demo.CompanyAdditionalDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyAdditionalDetailIns";
        instance.clientID = "CompanyAdditionalDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companyadditional/";
        instance.init();
    }

    CompanyAdditionalDetail_Page_Load();
    //]]>
</script>