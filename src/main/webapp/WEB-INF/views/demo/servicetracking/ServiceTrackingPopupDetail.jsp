<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ServiceTrackingDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">所属基地</label>
            <label class="col-lg-4 control-label content-label" id="baseName"></label>
            <label class="col-lg-2 control-label">走访企业</label>
            <label class="col-lg-4 control-label content-label" id="companyName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">走访人</label>
            <label class="col-lg-4 control-label content-label" id="visitUserName"></label>
            <label class="col-lg-2 control-label">企业拜访人</label>
            <label class="col-lg-4 control-label content-label" id="visitor"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">走访时间</label>
            <label class="col-lg-4 control-label content-label" id="visitTime" data-date="true"></label>
            <label class="col-lg-2 control-label">走访状态</label>
            <label class="col-lg-4 control-label content-label" id="visitStatusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">下次跟踪服务人</label>
            <label class="col-lg-4 control-label content-label" id="nextTrackingUser"></label>
            <label class="col-lg-2 control-label">下次跟踪时间</label>
            <label class="col-lg-4 control-label content-label" id="nextTrackingTime" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">企业需求</label>
            <label class="col-lg-8 control-label content-label" id="businessRequirements"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/servicetracking/ServiceTrackingDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ServiceTrackingDetail_Page_Load() {
        SysApp.Demo.ServiceTrackingDetailIns = new SysApp.Demo.ServiceTrackingDetail();
        var instance = SysApp.Demo.ServiceTrackingDetailIns;
        instance.selfInstance = "SysApp.Demo.ServiceTrackingDetailIns";
        instance.clientID = "ServiceTrackingDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/servicetracking/";
        instance.init();
    }

    ServiceTrackingDetail_Page_Load();
    //]]>
</script>