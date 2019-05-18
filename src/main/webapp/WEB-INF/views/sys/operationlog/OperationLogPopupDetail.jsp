<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content OperationLogDetail-MainContent" style="width: 1100px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">应用名称</label>
            <label class="col-sm-4 control-label content-label" id="appName"></label>
            <label class="col-sm-2 control-label">模块名</label>
            <label class="col-sm-4 control-label content-label" id="moduleName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">方法名</label>
            <label class="col-sm-4 control-label content-label" id="functionName"></label>
            <label class="col-sm-2 control-label">访问路径</label>
            <label class="col-sm-4 control-label content-label" id="url"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">请求参数</label>
            <label class="col-sm-9 control-label content-label" id="parameters" style="word-break: break-all;height: 250px;overflow-y:auto;"></label>
        </div>

        <div class="form-group form-inline" style="display: none;">
            <label class="col-sm-2 control-label">日志来源</label>
            <label class="col-sm-4 control-label content-label" id="logSource"></label>
            <label class="col-sm-2 control-label">日志类型</label>
            <label class="col-sm-4 control-label content-label" id="logType"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/operationlog/OperationLogDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function OperationLogDetail_Page_Load() {
        SysApp.Sys.OperationLogDetailIns = new SysApp.Sys.OperationLogDetail();
        var instance = SysApp.Sys.OperationLogDetailIns;
        instance.selfInstance = "SysApp.Sys.OperationLogDetailIns";
        instance.controller = "${ctx}/sys/operationlog/";
        instance.clientID = "OperationLogDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    OperationLogDetail_Page_Load();
    //]]>
</script>