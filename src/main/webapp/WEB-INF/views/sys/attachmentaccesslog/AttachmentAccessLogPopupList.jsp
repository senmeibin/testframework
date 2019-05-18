<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/Common.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/attachmentaccesslog/AttachmentAccessLogPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content AttachmentAccessLogPopupList-MainContent" style="width: 1000px; display: none;">
    <ctag:ModalHeader modalTitle="附件访问日志一览"></ctag:ModalHeader>

    <div class="modal-body">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <input type="hidden" id="attachmentUid" data-alias-table="main" data-search-mode="="/>
            <input type="hidden" id="accessType" data-alias-table="main" data-search-mode="="/>
        </form>
        <div id="divList" style="width: 100%;height: 380px;">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function AttachmentAccessLogPopupList_Page_Load() {
        SysApp.Sys.AttachmentAccessLogPopupListIns = new SysApp.Sys.AttachmentAccessLogPopupList();
        var instance = SysApp.Sys.AttachmentAccessLogPopupListIns;

        instance.selfInstance = "SysApp.Sys.AttachmentAccessLogPopupListIns";
        instance.clientID = "AttachmentAccessLogPopupList";
        instance.tableName = "sys_attachment_access_log";
        instance.controller = "${ctx}/sys/attachmentaccesslog/";
        instance.entry = "${entry}";

        instance.init();
    }

    AttachmentAccessLogPopupList_Page_Load();
    //]]>
</script>

