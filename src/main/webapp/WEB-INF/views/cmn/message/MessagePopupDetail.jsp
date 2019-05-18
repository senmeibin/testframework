<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content MessageDetail-MainContent" style="width: 1200px;display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <!--普通消息显示-->
    <div class="modal-body form-horizontal" id="normalMessage">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">消息类型</label>
            <label class="col-sm-9 control-label content-label" id="messageTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">消息标题</label>
            <label class="col-sm-9 control-label content-label" id="messageTitle"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">消息内容</label>
            <label class="col-sm-9 control-label content-label" id="messageContent" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">重要度</label>
            <label class="col-sm-9 control-label content-label" id="importanceDegreeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">发送区分</label>
            <label class="col-sm-9 control-label content-label" id="sendTypeName"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>
    <!--富文本显示-->
    <div id="richMessage" style="display: none">
        <div class="text-center" style="border-bottom: 1px dotted #ccc;margin: 5px; padding: 5px 0 10px;">
            <h2 style="margin-top: 10px;color: #000;font-weight: bold;"><span id="messageTitleRich"></span></h2>
            <h5 style="margin-top: 10px;color: #bbb;font-size: 12px;">发布时间：<span id="insertDate"></span></h5>
        </div>
        <div style="padding: 0 20px;">
            <iframe id="messageContentIframe" style="width: 100%;height: 460px;border: none;"></iframe>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/message/MessageDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function MessageDetail_Page_Load() {
        SysApp.Cmn.MessageDetailIns = new SysApp.Cmn.MessageDetail();
        var instance = SysApp.Cmn.MessageDetailIns;
        instance.selfInstance = "SysApp.Cmn.MessageDetailIns";
        instance.clientID = "MessageDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/cmn/message/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    MessageDetail_Page_Load();
    //]]>
</script>