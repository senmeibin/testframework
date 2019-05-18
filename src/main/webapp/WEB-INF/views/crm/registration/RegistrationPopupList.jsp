<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/registration/RegistrationPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content RegistrationPopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="报名记录一览"></ctag:ModalHeader>

    <div class="modal-body">
        <button id="btnAdd" type="button" class="btn btn-primary">
            <i class="fa fa-plus"></i>新增报名记录
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
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Registration}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Registration}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/registration/RegistrationPopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/registration/RegistrationPopupDetail.jsp" %>

<script type="text/javascript">
    //<![CDATA[
    function RegistrationPopupList_Page_Load() {
        SysApp.Crm.RegistrationPopupListIns = new SysApp.Crm.RegistrationPopupList();
        var instance = SysApp.Crm.RegistrationPopupListIns;

        instance.selfInstance = "SysApp.Crm.RegistrationPopupListIns";
        instance.clientID = "RegistrationPopupList";
        instance.tableName = "crm_registration";
        instance.inputInstance = SysApp.Crm.RegistrationInputIns;
        instance.detailInstance = SysApp.Crm.RegistrationDetailIns;
        instance.controller = "${ctx}/crm/registration/";
        instance.entry = "${entry}";

        instance.init();
    }

    $(function () {
        RegistrationPopupList_Page_Load();
    });
    //]]>
</script>