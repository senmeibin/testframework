<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SyncAppInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">同步项目/订阅者ID</label>
            <div class="col-sm-9">
                <input id="clientId" type="text" class="form-control" data-title="与配置文件中activemq.jms.receiverClientId一致" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                <label id="clientId_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">项目名称/订阅者名称</label>
            <div class="col-sm-9">
                <input id="subscriberName" type="text" class="form-control" data-title="与配置文件中activemq.jms.receiverClientName一致" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                <label id="subscriberName_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_SyncApp}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SyncApp}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/syncapp/SyncAppInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SyncAppInput_Page_Load() {
        SysApp.Sys.SyncAppInputIns = new SysApp.Sys.SyncAppInput();
        var instance = SysApp.Sys.SyncAppInputIns;
        instance.selfInstance = "SysApp.Sys.SyncAppInputIns";
        instance.clientID = "SyncAppInput";
        instance.controller = "${ctx}/sys/syncapp/";

        instance.init();
    }

    SyncAppInput_Page_Load();
    //]]>
</script>