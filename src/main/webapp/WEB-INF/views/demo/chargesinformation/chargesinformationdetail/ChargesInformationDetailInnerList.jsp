<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/chargesinformation/chargesinformationdetail/ChargesInformationDetailInnerList.js?${version}"></script>
<style type="text/css">
    .tb-cell-content {
        overflow: inherit !important;
    }
</style>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="ChargesInformationDetailInnerList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <%--企业收费信息UID--%>
        <input type="hidden" id="chargesInformationUid" data-alias-table="main" value="${chargesInformationUid}" data-search-mode="="/>
    </form>
    <%--收款方式--%>
    <select id="chargesTypeCd" style="display: none;"></select>
    <input type="hidden" id="jsonListData" value="${jsonDataList_ChargesInformationDetail}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ChargesInformationDetail}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ChargesInformationDetailInnerList_Page_Load() {
        SysApp.Demo.ChargesInformationDetailInnerListIns = new SysApp.Demo.ChargesInformationDetailInnerList();
        var instance = SysApp.Demo.ChargesInformationDetailInnerListIns;

        instance.selfInstance = "SysApp.Demo.ChargesInformationDetailInnerListIns";
        instance.controller = "${ctx}/demo/chargesinformationdetail/";
        instance.inputUrl = "${ctx}/demo/chargesinformationdetail/input";
        instance.clientID = "ChargesInformationDetailInnerList";
        instance.tableName = "demo_charges_information_detail";

        instance.init();
    }

    $(function () {
        ChargesInformationDetailInnerList_Page_Load();
    });
    //]]>
</script>

