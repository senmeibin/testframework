<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content PotentialCustomerContactDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">客户</label>
            <label class="col-lg-3 control-label content-label" id="companyName"></label>
            <label class="col-lg-1 control-label">联络人（职位）</label>
            <label class="col-lg-3 control-label content-label" id="contactPerson"></label>
            <label class="col-lg-1 control-label">联络方式</label>
            <label class="col-lg-3 control-label content-label" id="contactInfo"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">项目简介</label>
            <label class="col-lg-3 control-label content-label" id="projectDescription"></label>
            <label class="col-lg-1 control-label">公司需求</label>
            <label class="col-lg-3 control-label content-label" id="companyDemand"></label>
            <label class="col-lg-1 control-label">建议及判断</label>
            <label class="col-lg-3 control-label content-label" id="recommendationsJudgments"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">项目时间节点规划</label>
            <label class="col-lg-3 control-label content-label" id="projectTimeNode"></label>
            <label class="col-lg-1 control-label">计划</label>
            <label class="col-lg-3 control-label content-label" id="plan"></label>
            <label class="col-lg-1 control-label">联络时间</label>
            <label class="col-lg-3 control-label content-label" id="contactTime" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">结果</label>
            <label class="col-lg-3 control-label content-label" id="result"></label>
            <label class="col-lg-1 control-label">备注</label>
            <label class="col-lg-3 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/potentialcustomercontact/PotentialCustomerContactDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PotentialCustomerContactDetail_Page_Load() {
        SysApp.Demo.PotentialCustomerContactDetailIns = new SysApp.Demo.PotentialCustomerContactDetail();
        var instance = SysApp.Demo.PotentialCustomerContactDetailIns;
        instance.selfInstance = "SysApp.Demo.PotentialCustomerContactDetailIns";
        instance.clientID = "PotentialCustomerContactDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/potentialcustomercontact/";
        instance.init();
    }

    PotentialCustomerContactDetail_Page_Load();
    //]]>
</script>