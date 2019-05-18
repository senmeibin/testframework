<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/StudentFollowupInnerList.js?${version}"></script>
<div class="StudentFollowupInnerList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input type="hidden" id="studentUid" data-allow-clear="false" data-search-mode="="/>
    </form>
    <div id="divList" class="margin-bottom-space">

    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function StudentFollowupInnerList_Page_Load() {
        SysApp.Crm.StudentFollowupInnerListIns = new SysApp.Crm.StudentFollowupInnerList();
        var instance = SysApp.Crm.StudentFollowupInnerListIns;

        instance.selfInstance = "SysApp.Crm.StudentFollowupInnerListIns";
        instance.controller = "${ctx}/crm/followup/";
        instance.clientID = "StudentFollowupInnerList";
        instance.inputInstance = SysApp.Crm.FollowupInputIns;
        instance.detailInstance = SysApp.Crm.FollowupDetailIns;
        instance.init();
    }

    $(function () {
        StudentFollowupInnerList_Page_Load();
    });
    //]]>
</script>