<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/thirdpartyservicecontact/thirdpartyservicecontacts/ThirdPartyServiceContactsInnerList.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="ThirdPartyServiceContactsInnerList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <%--企业收费信息UID--%>
        <input type="hidden" id="thirdPartyServiceUid" data-alias-table="main" value="${thirdPartyServiceContactUid}" data-search-mode="="/>
    </form>
    <input type="hidden" id="jsonListData" value="${jsonDataList_ThirdPartyServiceContacts}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ThirdPartyServiceContacts}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ThirdPartyServiceContactsInnerList_Page_Load() {
        SysApp.Demo.ThirdPartyServiceContactsInnerListIns = new SysApp.Demo.ThirdPartyServiceContactsInnerList();
        var instance = SysApp.Demo.ThirdPartyServiceContactsInnerListIns;

        instance.selfInstance = "SysApp.Demo.ThirdPartyServiceContactsInnerListIns";
        instance.controller = "${ctx}/demo/thirdpartyservicecontacts/";
        instance.clientID = "ThirdPartyServiceContactsInnerList";
        instance.tableName = "demo_third_party_service_contacts";

        instance.init();
    }

    $(function () {
        ThirdPartyServiceContactsInnerList_Page_Load();
    });
    //]]>
</script>

