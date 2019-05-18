<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SmsLogDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应用名称</label>
            <label class="col-sm-8 control-label content-label" id="appName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">短信内容</label>
            <label class="col-sm-8 control-label content-label" id="message" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">短信供应商</label>
            <label class="col-sm-8 control-label content-label" id="vendor"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">手机号码</label>
            <label class="col-sm-8 control-label content-label" id="mobile" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">发送时间</label>
            <label class="col-sm-8 control-label content-label" id="sendDate" data-datetime="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">发送结果</label>
            <label class="col-sm-8 control-label content-label" id="sendResult"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">错误消息</label>
            <label class="col-sm-8 control-label content-label" id="errorMessage"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/smslog/SmsLogDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SmsLogDetail_Page_Load() {
        SysApp.Sys.SmsLogDetailIns = new SysApp.Sys.SmsLogDetail();
        var instance = SysApp.Sys.SmsLogDetailIns;
        instance.selfInstance = "SysApp.Sys.SmsLogDetailIns";
        instance.clientID = "SmsLogDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/sys/smslog/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    SmsLogDetail_Page_Load();
    //]]>
</script>