<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content MailLogDetail-MainContent" style="width: 1100px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">邮件主题</label>
            <label class="col-lg-6 control-label content-label" id="subject"></label>
            <label class="col-lg-1 control-label">应用名称</label>
            <label class="col-lg-2 control-label content-label" id="appName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">发送邮件地址</label>
            <label class="col-lg-6 control-label content-label" id="mailFrom"></label>
            <label class="col-lg-1 control-label">发送重试次数</label>
            <label class="col-lg-2 control-label content-label" id="retryCount"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">接收地址(To)</label>
            <label class="col-lg-10 control-label content-label" id="mailTo"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">接收地址(Cc)</label>
            <label class="col-lg-10 control-label content-label" id="mailCc"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">发送时间</label>
            <label class="col-lg-2 control-label content-label" id="sendDate" data-datetime="true"></label>
            <label class="col-lg-2 control-label">发送结果</label>
            <label class="col-lg-2 control-label content-label" id="sendResult"></label>
            <label class="col-lg-1 control-label">错误消息</label>
            <label class="col-lg-2 control-label content-label" id="errorMessage"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label"></label>
            <div class="col-lg-10">
                <iframe id="iframeMessage" width="870" frameborder="0" height="300"></iframe>
            </div>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/maillog/MailLogDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function MailLogDetail_Page_Load() {
        SysApp.Sys.MailLogDetailIns = new SysApp.Sys.MailLogDetail();
        var instance = SysApp.Sys.MailLogDetailIns;
        instance.selfInstance = "SysApp.Sys.MailLogDetailIns";
        instance.clientID = "MailLogDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/sys/maillog/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    MailLogDetail_Page_Load();
    //]]>
</script>