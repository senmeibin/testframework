<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ApplicationDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应用编号</label>
            <label class="col-sm-9 control-label content-label" id="appCode"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应用名称</label>
            <label class="col-sm-9 control-label content-label" id="appName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">有效日期</label>
            <label class="col-sm-9 control-label content-label" id="validDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <label class="col-sm-9 control-label content-label" id="dispSeq"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应用版本</label>
            <label class="col-sm-9 control-label content-label" id="version"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">维护开始日期</label>
            <label class="col-sm-9 control-label content-label" id="mainteStartDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">维护结束日期</label>
            <label class="col-sm-9 control-label content-label" id="mainteEndDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">维护内容</label>
            <label class="col-sm-9 control-label content-label" id="mainteContent" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/application/ApplicationDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ApplicationDetail_Page_Load() {
        SysApp.Sys.ApplicationDetailIns = new SysApp.Sys.ApplicationDetail();
        var instance = SysApp.Sys.ApplicationDetailIns;
        instance.selfInstance = "SysApp.Sys.ApplicationDetailIns";
        instance.controller = "${ctx}/sys/application/";
        instance.clientID = "ApplicationDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    ApplicationDetail_Page_Load();
    //]]>
</script>