<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/followup/FollowupPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content FollowupPopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="跟进记录一览"></ctag:ModalHeader>

    <div class="modal-body">
        <button id="btnAdd" type="button" class="btn btn-primary">
            <i class="fa fa-plus"></i>新增跟进记录
        </button>
        <div class="clear-both dashed-line">
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <input id="studentUid" data-alias-table="main" data-allow-clear="false" type="hidden"/>
        </form>
        <%--学员基本信息--%>
        <i class="fa fa-vcard" style="font-size: 18px;"></i>&nbsp;<span id="studentInfo"></span>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Followup}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Followup}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/followup/FollowupPopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/followup/FollowupPopupDetail.jsp" %>

<script type="text/javascript">
    //<![CDATA[
    function FollowupPopupList_Page_Load() {
        SysApp.Crm.FollowupPopupListIns = new SysApp.Crm.FollowupPopupList();
        var instance = SysApp.Crm.FollowupPopupListIns;

        instance.selfInstance = "SysApp.Crm.FollowupPopupListIns";
        instance.clientID = "FollowupPopupList";
        instance.tableName = "crm_followup";
        instance.inputInstance = SysApp.Crm.FollowupInputIns;
        instance.detailInstance = SysApp.Crm.FollowupDetailIns;
        instance.controller = "${ctx}/crm/followup/";
        instance.entry = "${entry}";

        instance.init();
    }

    $(function () {
        FollowupPopupList_Page_Load();
    });
    //]]>
</script>