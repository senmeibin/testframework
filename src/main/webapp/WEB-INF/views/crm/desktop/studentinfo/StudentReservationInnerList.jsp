<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/StudentReservationInnerList.js?${version}"></script>
<div class="StudentReservationInnerList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input type="hidden" id="studentUid" data-allow-clear="false" data-search-mode="="/>
    </form>
    <div id="divList" class="margin-bottom-space">

    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function StudentReservationInnerList_Page_Load() {
        SysApp.Crm.StudentReservationInnerListIns = new SysApp.Crm.StudentReservationInnerList();
        var instance = SysApp.Crm.StudentReservationInnerListIns;

        instance.selfInstance = "SysApp.Crm.StudentReservationInnerListIns";
        instance.controller = "${ctx}/crm/reservation/";
        instance.clientID = "StudentReservationInnerList";
        instance.inputInstance = SysApp.Crm.ReservationInputIns;
        instance.detailInstance = SysApp.Crm.ReservationDetailIns;
        instance.init();
    }

    $(function () {
        StudentReservationInnerList_Page_Load();
    });
    //]]>
</script>