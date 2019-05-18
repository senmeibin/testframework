<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyIntellectualDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">企业名称</label>
            <label class="col-lg-4 control-label content-label" id="companyName"></label>
            <label class="col-lg-2 control-label">知识产权类型</label>
            <label class="col-lg-4 control-label content-label" id="intellectualPropertyName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">发生时间</label>
            <label class="col-lg-4 control-label content-label" id="occurrenceDate" data-date="true"></label>
            <label class="col-lg-2 control-label">名称</label>
            <label class="col-lg-4 control-label content-label" id="name"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">编号</label>
            <label class="col-lg-4 control-label content-label" id="serialNumber"></label>
            <label class="col-lg-2 control-label">状态</label>
            <label class="col-lg-4 control-label content-label" id="stateName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">是否有效</label>
            <label class="col-lg-4 control-label content-label" id="effectiveName"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyintellectual/CompanyIntellectualDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyIntellectualDetail_Page_Load() {
        SysApp.Demo.CompanyIntellectualDetailIns = new SysApp.Demo.CompanyIntellectualDetail();
        var instance = SysApp.Demo.CompanyIntellectualDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyIntellectualDetailIns";
        instance.clientID = "CompanyIntellectualDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companyintellectual/";
        instance.init();
    }

    CompanyIntellectualDetail_Page_Load();
    //]]>
</script>