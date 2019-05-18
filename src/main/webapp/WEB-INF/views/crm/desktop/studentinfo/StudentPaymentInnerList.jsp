<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/StudentPaymentInnerList.js?${version}"></script>
<div class="StudentPaymentInnerList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input type="hidden" id="studentUid" data-allow-clear="false" data-search-mode="="/>
    </form>
    <div id="divList" class="margin-bottom-space">

    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function StudentPaymentInnerList_Page_Load() {
        SysApp.Crm.StudentPaymentInnerListIns = new SysApp.Crm.StudentPaymentInnerList();
        var instance = SysApp.Crm.StudentPaymentInnerListIns;

        instance.selfInstance = "SysApp.Crm.StudentPaymentInnerListIns";
        instance.controller = "${ctx}/crm/payment/";
        instance.clientID = "StudentPaymentInnerList";
        instance.inputInstance = SysApp.Crm.PaymentInputIns;
        instance.detailInstance = SysApp.Crm.PaymentDetailIns;
        instance.init();
    }

    $(function () {
        StudentPaymentInnerList_Page_Load();
    });
    //]]>
</script>