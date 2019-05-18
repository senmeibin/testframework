<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ResourcesDockingRecordDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">入孵企业</label>
            <label class="col-lg-4 control-label content-label" id="companyName"></label>
            <label class="col-lg-1 control-label">对接时间</label>
            <label class="col-lg-4 control-label content-label" id="dockingDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">对接人</label>
            <label class="col-lg-4 control-label content-label" id="dockingPersonName"></label>
            <label class="col-lg-1 control-label">数量</label>
            <label class="col-lg-4 control-label content-label" id="quantity"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">对接机构</label>
            <label class="col-lg-4 control-label content-label" id="thirdPartyServiceName"></label>
            <label class="col-lg-1 control-label">对接联系人</label>
            <label class="col-lg-4 control-label content-label" id="thirdPartyServiceContactsName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">对接内容</label>
            <label class="col-lg-8 control-label content-label" id="dockingContent"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">跟踪</label>
            <label class="col-lg-8 control-label content-label" id="following"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/resourcesdockingrecord/ResourcesDockingRecordDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ResourcesDockingRecordDetail_Page_Load() {
        SysApp.Demo.ResourcesDockingRecordDetailIns = new SysApp.Demo.ResourcesDockingRecordDetail();
        var instance = SysApp.Demo.ResourcesDockingRecordDetailIns;
        instance.selfInstance = "SysApp.Demo.ResourcesDockingRecordDetailIns";
        instance.clientID = "ResourcesDockingRecordDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/resourcesdockingrecord/";
        instance.init();
    }

    ResourcesDockingRecordDetail_Page_Load();
    //]]>
</script>