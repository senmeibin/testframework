<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/loginlog/LoginLogPopupList.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content LoginLogPopupList-MainContent" style="width: 1100px; display: none;">
    <ctag:ModalHeader modalTitle="登录日志"/>

    <div class="modal-body">
        <form id="MainForm">
            <input id="userUid" data-alias-table="main" data-search-mode="=" type="hidden"/>
            <input id="loginResult" data-alias-table="main" data-search-mode="=" type="hidden" value="1"/>
        </form>
        <div id="divList" style="width: 100%">
        </div>
    </div>
    <input type="hidden" id="jsonListData"/>

    <div class="modal-footer">
        <button type="button" class="btn btn-default" id="btnClose"><i class="fa fa-remove"></i>关闭</button>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function LoginLogPopupListIns_Page_Load() {
        SysApp.Sys.LoginLogPopupListIns = new SysApp.Sys.LoginLogPopupList();
        var instance =  SysApp.Sys.LoginLogPopupListIns;
        instance.popupPosition = "top";
        instance.selfInstance = "SysApp.Sys.LoginLogPopupListIns";
        instance.controller = "${ctx}/sys/loginlog/";
        instance.clientID = "LoginLogPopupList";
        instance.init();
    }
    LoginLogPopupListIns_Page_Load();
    //]]>
</script>