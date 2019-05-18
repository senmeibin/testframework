<%@ page language="java" pageEncoding="UTF-8" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ExecuteLogDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>
    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">功能模块</label>
            <label class="col-sm-4 control-label content-label" id="functionCode" style="word-wrap: break-word;"></label>
            <label class="col-sm-1 control-label">功能名称</label>
            <label class="col-sm-5 control-label content-label" id="functionName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">功能类型</label>
            <label class="col-sm-4 control-label content-label" id="functionTypeName"></label>
            <label class="col-sm-1 control-label">来源系统</label>
            <label class="col-sm-5 control-label content-label" id="sourceSystemName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">目标系统</label>
            <label class="col-sm-4 control-label content-label" id="destSystemName"></label>
            <label class="col-sm-1 control-label">执行成功数</label>
            <label class="col-sm-5 control-label content-label" id="executeRecordCount"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">执行内容</label>
            <label class="col-sm-9 control-label content-label" id="executeContent" data-textarea="true" style="height:300px;overflow:auto;overflow-x: hidden"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">结果编码</label>
            <label class="col-sm-4 control-label content-label" id="resultCode"></label>
            <label class="col-sm-1 control-label">结果消息</label>
            <label class="col-sm-5 control-label content-label" id="resultMessage"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">执行结果</label>
            <label class="col-sm-4 control-label content-label" id="result"></label>
            <label class="col-sm-1 control-label">执行时间</label>
            <label class="col-sm-5 control-label content-label" id="insertDate"></label>
        </div>
    </div>
    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/executelog/ExecuteLogDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ExecuteLogDetail_Page_Load() {
        SysApp.Sys.ExecuteLogDetailIns = new SysApp.Sys.ExecuteLogDetail();
        var instance = SysApp.Sys.ExecuteLogDetailIns;
        instance.selfInstance = "SysApp.Sys.ExecuteLogDetailIns";
        instance.clientID = "ExecuteLogDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/sys/executelog/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    ExecuteLogDetail_Page_Load();
    //]]>
</script>