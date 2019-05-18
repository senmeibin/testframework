<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/message/messageuser/MessageUserPopupList.js?${version}"></script>
<div id="ctlFrame" class="modal-content MessageUserPopupList-MainContent" style="width: 1300px;display: none;">
    <ctag:ModalHeader modalTitle="人员一览"></ctag:ModalHeader>
    <div class="modal-body">
        <form id="MainForm">
            <%--消息推送管理表UID--%>
            <input type="hidden" id="messageUid" data-alias-table="main" data-search-mode="="/>
            <%--是否已读--%>
            <input type="hidden" id="readTypeCd" data-alias-table="main" data-search-mode="="/>
        </form>
        <div id="divList" style="width: 100%;">
        </div>
    </div>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function MessageUserPopupList_Page_Load() {
        SysApp.Cmn.MessageUserPopupListIns = new SysApp.Cmn.MessageUserPopupList();
        var instance = SysApp.Cmn.MessageUserPopupListIns;

        instance.selfInstance = "MessageUserPopupListIns";
        instance.clientID = "MessageUserPopupList";
        instance.tableName = "cmn_message_detail";
        instance.controller = "${ctx}/cmn/messagedetail/";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    MessageUserPopupList_Page_Load();
    //]]>
</script>