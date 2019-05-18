<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content DatabaseProcessOperationLogDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">进程号</label>
            <label class="col-sm-9 control-label content-label" id="processId"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">用户</label>
            <label class="col-sm-9 control-label content-label" id="userName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">主机地址</label>
            <label class="col-sm-9 control-label content-label" id="hostName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">数据库名</label>
            <label class="col-sm-9 control-label content-label" id="databaseName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">执行等待时间</label>
            <label class="col-sm-9 control-label content-label" id="executeWaitTime"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">执行信息</label>
            <label class="col-sm-9 control-label content-label" id="executeInformation" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/databaseprocessoperationlog/DatabaseProcessOperationLogDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function DatabaseProcessOperationLogDetail_Page_Load() {
        SysApp.Sys.DatabaseProcessOperationLogDetailIns = new SysApp.Sys.DatabaseProcessOperationLogDetail();
        var instance = SysApp.Sys.DatabaseProcessOperationLogDetailIns;
        instance.selfInstance = "SysApp.Sys.DatabaseProcessOperationLogDetailIns";
        instance.clientID = "DatabaseProcessOperationLogDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/sys/databaseprocessoperationlog/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    DatabaseProcessOperationLogDetail_Page_Load();
    //]]>
</script>