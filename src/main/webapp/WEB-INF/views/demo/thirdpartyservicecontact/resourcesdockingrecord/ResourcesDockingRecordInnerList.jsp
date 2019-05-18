<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/thirdpartyservicecontact/resourcesdockingrecord/ResourcesDockingRecordInnerList.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="ResourcesDockingRecordInnerList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input type="hidden" id="thirdPartyServiceUid" data-alias-table="main" value="${thirdPartyServiceContactUid}" data-search-mode="="/>
    </form>
    <input type="hidden" id="jsonListData" value="${jsonDataList_ResourcesDockingRecord}"/>
    <%--上次检索条件--%>
    <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ResourcesDockingRecord}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ResourcesDockingRecord}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ResourcesDockingRecordInnerList_Page_Load() {
        SysApp.Demo.ResourcesDockingRecordInnerListIns = new SysApp.Demo.ResourcesDockingRecordInnerList();
        var instance = SysApp.Demo.ResourcesDockingRecordInnerListIns;

        instance.selfInstance = "SysApp.Demo.ResourcesDockingRecordInnerListIns";
        instance.controller = "${ctx}/demo/resourcesdockingrecord/";
        instance.inputUrl = "${ctx}/demo/resourcesdockingrecord/input";
        instance.clientID = "ResourcesDockingRecordInnerList";
        instance.tableName = "demo_resources_docking_record";
        instance.init();
    }

    $(function () {
        ResourcesDockingRecordInnerList_Page_Load();
    });
    //]]>
</script>