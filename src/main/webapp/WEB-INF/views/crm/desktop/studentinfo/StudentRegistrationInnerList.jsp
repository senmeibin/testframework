<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/StudentRegistrationInnerList.js?${version}"></script>
<div class="StudentRegistrationInnerList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input type="hidden" id="studentUid" data-allow-clear="false" data-search-mode="="/>
    </form>
    <div id="divList" class="margin-bottom-space">

    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function StudentRegistrationInnerList_Page_Load() {
        SysApp.Crm.StudentRegistrationInnerListIns = new SysApp.Crm.StudentRegistrationInnerList();
        var instance = SysApp.Crm.StudentRegistrationInnerListIns;

        instance.selfInstance = "SysApp.Crm.StudentRegistrationInnerListIns";
        instance.controller = "${ctx}/crm/registration/";
        instance.clientID = "StudentRegistrationInnerList";
        instance.inputInstance = SysApp.Crm.RegistrationInputIns;
        instance.detailInstance = SysApp.Crm.RegistrationDetailIns;
        instance.init();
    }

    $(function () {
        StudentRegistrationInnerList_Page_Load();
    });
    //]]>
</script>